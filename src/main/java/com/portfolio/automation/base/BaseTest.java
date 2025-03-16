package com.portfolio.automation.base;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.portfolio.automation.factory.DriverFactory;
import com.portfolio.automation.utils.ReportManager;
import com.portfolio.automation.factory.ScreenshotUtil;

import java.lang.reflect.Method;

public class BaseTest {
    protected WebDriver driver;
    protected ExtentTest test; // ExtentTest instance for reporting

    @BeforeClass
    public void setup() {

        driver = DriverFactory.getDriver();
    }

    @BeforeMethod
    public void startTestReport(Method method) {
        driver = DriverFactory.getDriver(); // Ensure new driver instance after every test case.
        test = ReportManager.createTest(method.getName()); // Create report entry for each test
    }

    @AfterMethod
    public void captureScreenshotOnFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            String screenshotPath = ScreenshotUtil.takeScreenshot(driver, result.getName());
            test.fail("Test failed: " + result.getThrowable())
                    .addScreenCaptureFromPath(screenshotPath);
        } else {
            test.pass("Test passed successfully!");
        }

        DriverFactory.closeDriver(); // close it after every test case.

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}