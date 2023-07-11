package org.theduykh.ata.listener;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.theduykh.ata.driver.AtaDriverManager;

public class AtaTestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        if (AtaDriverManager.getDriver() == null) {
            AtaDriverManager.init();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        AtaDriverManager.quit();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        AtaDriverManager.quit();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        AtaDriverManager.quit();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        AtaDriverManager.quit();
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        AtaDriverManager.quit();
    }

}
