import applications.ApplicationManager;
import com.microsoft.playwright.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.awt.*;
import java.io.IOException;

import static com.microsoft.playwright.BrowserType.*;


public class TestBase {
    protected static ApplicationManager app =
            new ApplicationManager(System.getProperty("browser", "Chrome"));

  /*  private static Playwright playwright;
    public static Browser browser;
    public static BrowserContext context;
    public Page page;*/

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws IOException {
        app.init();
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

    }



}
