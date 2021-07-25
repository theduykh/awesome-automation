package org.theduykh.ata.listener;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.FixtureResult;
import io.qameta.allure.model.Stage;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResultContainer;
import io.qameta.allure.util.ResultsUtils;
import org.testng.*;
import org.theduykh.ata.driver.AtaDriver;
import org.theduykh.ata.driver.AtaDriverManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static io.qameta.allure.util.ResultsUtils.*;

/*
    This listener helps us to generate step method in Allure report
 */
public class AtaAllureMethodListener implements IInvokedMethodListener {

    private final ThreadLocal<String> currentExecutable = ThreadLocal
            .withInitial(() -> UUID.randomUUID().toString());

    private final AllureLifecycle lifecycle;
    private static final String ALLURE_UUID = "ALLURE_UUID";

    private final ThreadLocal<String> currentTestContainer = ThreadLocal
            .withInitial(() -> UUID.randomUUID().toString());

    private final ThreadLocal<Current> currentTestResult = ThreadLocal
            .withInitial(Current::new);

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<ITestClass, String> classContainerUuidStorage = new ConcurrentHashMap<>();

    public AtaAllureMethodListener(final AllureLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    public AtaAllureMethodListener() {
        this(Allure.getLifecycle());
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        ITestNGMethod testMethod = method.getTestMethod();
        ITestContext context = testResult.getTestContext();
        if (this.isSupportedConfigurationFixture(testMethod)) {
            this.ifSuiteFixtureStarted(context.getSuite(), testMethod);
            this.ifTestFixtureStarted(context, testMethod);
            this.ifClassFixtureStarted(testMethod);
            this.ifMethodFixtureStarted(testMethod);
        }

    }

    @Override
    public void afterInvocation(final IInvokedMethod method, final ITestResult testResult) {
        final ITestNGMethod testMethod = method.getTestMethod();
        if (isSupportedConfigurationFixture(testMethod)) {
            final String executableUuid = currentExecutable.get();
            currentExecutable.remove();
            if (testResult.isSuccess()) {
                getLifecycle().updateFixture(executableUuid, result -> result.setStatus(Status.PASSED));
            } else {
                getLifecycle().updateFixture(executableUuid, result -> result
                        .setStatus(getStatus(testResult.getThrowable()))
                        .setStatusDetails(getStatusDetails(testResult.getThrowable()).orElse(null)));
            }
            getLifecycle().stopFixture(executableUuid);

            if (testMethod.isBeforeMethodConfiguration() || testMethod.isAfterMethodConfiguration()) {
                final String containerUuid = currentTestContainer.get();
                validateContainerExists(getQualifiedName(testMethod), containerUuid);
                currentTestContainer.remove();
                getLifecycle().stopTestContainer(containerUuid);
                getLifecycle().writeTestContainer(containerUuid);
            }
        }
    }

    public AllureLifecycle getLifecycle() {
        return this.lifecycle;
    }

    private void ifClassFixtureStarted(final ITestNGMethod testMethod) {
        if (testMethod.isBeforeClassConfiguration()) {
            getClassContainer(testMethod.getTestClass())
                    .ifPresent(parentUuid -> startBefore(parentUuid, testMethod));
        }
        if (testMethod.isAfterClassConfiguration()) {
            getClassContainer(testMethod.getTestClass())
                    .ifPresent(parentUuid -> startAfter(parentUuid, testMethod));
        }
    }

    private void ifSuiteFixtureStarted(ISuite suite, ITestNGMethod testMethod) {
        if (testMethod.isBeforeSuiteConfiguration()) {
            this.startBefore(this.getUniqueUuid(suite), testMethod);
        }

        if (testMethod.isAfterSuiteConfiguration()) {
            this.startAfter(this.getUniqueUuid(suite), testMethod);
        }
    }

    private void ifTestFixtureStarted(final ITestContext context, final ITestNGMethod testMethod) {
        if (testMethod.isBeforeTestConfiguration()) {
            startBefore(getUniqueUuid(context), testMethod);
        }
        if (testMethod.isAfterTestConfiguration()) {
            startAfter(getUniqueUuid(context), testMethod);
        }
    }

    private void ifMethodFixtureStarted(final ITestNGMethod testMethod) {
        currentTestContainer.remove();
        Current current = currentTestResult.get();
        final FixtureResult fixture = getFixtureResult(testMethod);
        final String uuid = currentExecutable.get();
        if (testMethod.isBeforeMethodConfiguration()) {
            if (current.isStarted()) {
                currentTestResult.remove();
                current = currentTestResult.get();
            }
            getLifecycle().startPrepareFixture(createFakeContainer(testMethod, current), uuid, fixture);
        }

        if (testMethod.isAfterMethodConfiguration()) {
            getLifecycle().startTearDownFixture(createFakeContainer(testMethod, current), uuid, fixture);
        }
    }

    private void validateContainerExists(final String fixtureName, final String containerUuid) {
        if (Objects.isNull(containerUuid)) {
            throw new IllegalStateException(
                    "Could not find container for after method fixture " + fixtureName
            );
        }
    }

    private Optional<String> getClassContainer(final ITestClass clazz) {
        lock.readLock().lock();
        try {
            return Optional.ofNullable(classContainerUuidStorage.get(clazz));
        } finally {
            lock.readLock().unlock();
        }
    }

    private boolean isSupportedConfigurationFixture(ITestNGMethod testMethod) {
        return testMethod.isBeforeMethodConfiguration() || testMethod.isAfterMethodConfiguration() || testMethod.isBeforeTestConfiguration() || testMethod.isAfterTestConfiguration() || testMethod.isBeforeClassConfiguration() || testMethod.isAfterClassConfiguration() || testMethod.isBeforeSuiteConfiguration() || testMethod.isAfterSuiteConfiguration();
    }


    private void startBefore(String parentUuid, ITestNGMethod method) {
        String uuid = this.currentExecutable.get();
        this.getLifecycle().startPrepareFixture(parentUuid, uuid, this.getFixtureResult(method));
    }

    private void startAfter(String parentUuid, ITestNGMethod method) {
        String uuid = this.currentExecutable.get();
        this.getLifecycle().startTearDownFixture(parentUuid, uuid, this.getFixtureResult(method));
    }


    @SuppressWarnings("deprecation")
    private FixtureResult getFixtureResult(final ITestNGMethod method) {
        final FixtureResult fixtureResult = new FixtureResult()
                .withName(getMethodName(method))
                .withStart(System.currentTimeMillis())
                .withDescription(method.getDescription())
                .withStage(Stage.RUNNING);
        processDescription(getClass().getClassLoader(), method.getConstructorOrMethod().getMethod(), fixtureResult);
        return fixtureResult;
    }

    private String getMethodName(final ITestNGMethod method) {
        return firstNonEmpty(
                method.getDescription(),
                method.getMethodName(),
                getQualifiedName(method)).orElse("Unknown");
    }

    private String getQualifiedName(final ITestNGMethod method) {
        return method.getRealClass().getName() + "." + method.getMethodName();
    }

    private String getUniqueUuid(final IAttributes suite) {
        if (Objects.isNull(suite.getAttribute(ALLURE_UUID))) {
            suite.setAttribute(ALLURE_UUID, UUID.randomUUID().toString());
        }
        return Objects.toString(suite.getAttribute(ALLURE_UUID));
    }


    protected Status getStatus(final Throwable throwable) {
        return ResultsUtils.getStatus(throwable).orElse(Status.BROKEN);
    }


    private String createFakeContainer(final ITestNGMethod method, final Current current) {
        final String parentUuid = currentTestContainer.get();
        final TestResultContainer container = new TestResultContainer()
                .setUuid(parentUuid)
                .setName(getQualifiedName(method))
                .setStart(System.currentTimeMillis())
                .setDescription(method.getDescription())
                .setChildren(Collections.singletonList(current.getUuid()));
        getLifecycle().startTestContainer(container);
        return parentUuid;
    }

    private static class Current {
        private final String uuid;
        private CurrentStage currentStage;

        Current() {
            this.uuid = UUID.randomUUID().toString();
            this.currentStage = CurrentStage.BEFORE;
        }

        public void test() {
            this.currentStage = CurrentStage.TEST;
        }

        public void after() {
            this.currentStage = CurrentStage.AFTER;
        }

        public boolean isStarted() {
            return this.currentStage != CurrentStage.BEFORE;
        }

        public boolean isAfter() {
            return this.currentStage == CurrentStage.AFTER;
        }

        public String getUuid() {
            return uuid;
        }
    }

    private enum CurrentStage {
        BEFORE,
        TEST,
        AFTER
    }

}
