import com.microsoft.playwright.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.awt.*;

public class TestBase {

    private static Playwright playwright;
    public static Browser browser;
    public static BrowserContext context;
    public Page page;
    @BeforeSuite
    public void setUp(){
        playwright = Playwright.create();
        final BrowserType.LaunchOptions options = new BrowserType.LaunchOptions();
        options.setHeadless(false);
        Browser browser = playwright.chromium().launch(options);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        System.out.println(width + " " + height);//1280 720

        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        //context = browser.newContext();
        page = context.newPage();
        page.navigate("https://ed.engdis.com/QaExcellence#/login");

    }

    @AfterSuite
    public void tearDown(){
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            context.close();
            playwright.close();
        }
    }



}
