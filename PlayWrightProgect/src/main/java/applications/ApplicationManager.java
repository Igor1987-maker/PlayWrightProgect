package applications;

import com.microsoft.playwright.*;
import helpers.BaseHelper;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationManager {

    String typeOfBrowser;
    Properties properties;
    private static Playwright playwright;
    public static Browser browser;
    public static BrowserContext context;
    public Page page;

    BaseHelper baseHelper;

    public ApplicationManager(String typeOfBrowser) {
        this.typeOfBrowser=typeOfBrowser;
        properties = new Properties();
    }


    public void init() throws IOException {

        String target = System.getProperty("target", "data");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

        /*if (browser.equals(BrowserType.CHROME)) {
            wd = new EventFiringWebDriver(new ChromeDriver());
        } else if (browser.equals(BrowserType.FIREFOX)) {
            wd = new EventFiringWebDriver(new FirefoxDriver());
        }*/

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

        baseHelper = new BaseHelper(page);


        //helper = new Helper(wd);
    }


    public void stop() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            context.close();
            playwright.close();
        }
    }
}
