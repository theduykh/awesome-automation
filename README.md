# awesome-automation
An automation framework based on TestNG and Cucumber

## Setup
- Java JDK 8
- Maven 3
- Lombok & Cucumber plugin for IDE
- Selenium grid runs on http://localhost:4444/wd/hub/ with a Google Chrome node
- Allure commandline or any web service for publishing test report in `allure-report` folder
    - Allure install: https://docs.qameta.io/allure/#_installing_a_commandline
    - Web Server for Chrome: https://chrome.google.com/webstore/detail/web-server-for-chrome/ofhbbkphhbklhfoeikjpcbhemlocgigb

## Execute
Run command `mvn test`

## Report
- There will be a `test-result` folder created after the test is completed.
- Use Allure to generate reports by command `allure generate`
- Open the report in `test-report` by command  `allure open`
- Or simply run the command `allure serve`

## Structure
```
src
 ┣ main
 ┃ ┣ java
 ┃ ┃ ┗ org
 ┃ ┃ ┃ ┗ theduykh
 ┃ ┃ ┃ ┃ ┗ ata
 ┃ ┃ ┃ ┃ ┃ ┣ driver
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ AtaDriver.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ AtaDriverManager.java
 ┃ ┃ ┃ ┃ ┃ ┣ listener
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ AtaAllureMethodListener.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ AtaSuiteListener.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ AtaTestListener.java
 ┃ ┃ ┃ ┃ ┃ ┗ test
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ AtaBase.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ AtaCucumberStepContext.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ AtaCucumberStepDefinition.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ AtaPage.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ AtaTest.java
 ┃ ┗ resources
 ┃ ┃ ┣ META-INF
 ┃ ┃ ┃ ┣ services
 ┃ ┃ ┃ ┃ ┗ org.testng.ITestNGListener
 ┃ ┃ ┃ ┗ aop-ajc.xml
 ┃ ┃ ┗ log4j2.xml
 ┗ test
 ┃ ┣ java
 ┃ ┃ ┣ entities
 ┃ ┃ ┃ ┣ CartEntity.java
 ┃ ┃ ┃ ┣ ItemEntity.java
 ┃ ┃ ┃ ┗ ProductEntity.java
 ┃ ┃ ┣ pages
 ┃ ┃ ┃ ┣ CartPage.java
 ┃ ┃ ┃ ┣ HomePage.java
 ┃ ┃ ┃ ┗ ProductPage.java
 ┃ ┃ ┣ stepdefinition
 ┃ ┃ ┃ ┗ AmazonStepDefinition.java
 ┃ ┃ ┗ CucumberRunner.java
 ┃ ┗ resources
 ┃ ┃ ┣ features
 ┃ ┃ ┃ ┗ Amazon.feature
 ┃ ┃ ┗ cucumber.properties
```
