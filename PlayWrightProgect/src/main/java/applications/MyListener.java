package applications;


import helpers.BaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.ITestResult;

import java.util.Random;

public class MyListener implements ITestListener {

    Logger logger = LoggerFactory.getLogger(MyListener.class);
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Starting test" + result.getTestName());
        ITestListener.super.onTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.info(result.getTestName()+ " Failed because of the======================");
       /* ApplicationManager ap = new ApplicationManager(System.getProperty("browser", "Chrome"));
        ap.getHomePageHelper().makeScreenShot(page,"screenshots/screenshot"+new Random().nextInt(2000) +".png");*/
        ITestListener.super.onTestFailure(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("Starting test" + context.getName());
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
    }
}
