import applications.ApplicationManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Method;

import static com.microsoft.playwright.BrowserType.*;


public class TestBase {
    public static ApplicationManager app =
            new ApplicationManager(System.getProperty("browser", "Chrome"));
    ExtentReports extent;
    ExtentTest test;
    Logger logger = LoggerFactory.getLogger(TestBase.class);
  /*  private static Playwright playwright;
    public static Browser browser;
    public static BrowserContext context;
    public Page page;*/

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws IOException {
        app.init();
        //ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent-report.html");
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
        extent.attachReporter(spark);

        /*playwright = Playwright.create();
        final LaunchOptions options = new LaunchOptions();
        options.setHeadless(false);
        Browser browser = playwright.chromium().launch(options);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        System.out.println(width + " " + height);//1280 720

        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        //context = browser.newContext();
        page = context.newPage();
        page.navigate("https://ed.engdis.com/QaExcellence#/login");*/

    }


    @AfterSuite
    public void tearDown(){
        app.stop();
        extent.flush();
    }



}
