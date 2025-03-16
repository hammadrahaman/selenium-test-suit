package com.portfolio.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

public class ReportManager implements ITestListener {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();



    // Initialize Report Before Tests
    @Override
    public void onStart(ITestContext context) {
        if (extent == null) {
            // Define the report directory path
            String reportDirPath = System.getProperty("user.dir") + "/src/main/reports";
            File reportDir = new File(reportDirPath);

            // Ensure the reports directory exists
            if (!reportDir.exists()) {
                reportDir.mkdirs(); // Create the directory if it doesn’t exist
            }

            // Define the report file path
            String reportFilePath = reportDirPath + "/TestReport.html";

            // Initialize Extent Reports
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFilePath);
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            System.out.println("Extent Report initialized at: " + reportFilePath);
        }
    }

    // Start a test in the report
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        testThread.set(test);
    }

    // Mark test as PASS
    @Override
    public void onTestSuccess(ITestResult result) {
        testThread.get().pass("Test Passed");
    }

    // Mark test as FAIL
    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = testThread.get();
        if (test != null) {
            test.fail("Test Failed: " + result.getThrowable());
        } else {
            System.out.println("ExtentTest is null for: " + result.getMethod().getMethodName());
        }
    }

    // Mark test as SKIPPED
    @Override
    public void onTestSkipped(ITestResult result) {
        testThread.get().skip("Test Skipped");
    }

    // Flush the report after all tests
    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Flushing Extent Report...");
        if (extent != null) {
            extent.flush();
        }
    }


    public static ExtentTest createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        testThread.set(test);
        return test;
    }

    public static ExtentTest getTest() {
        return testThread.get();
    }
}