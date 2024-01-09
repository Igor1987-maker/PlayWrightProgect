package applications;

import com.microsoft.playwright.*;
import helpers.BaseHelper;
import helpers.edHelpers.HomePageHelper;
import helpers.edHelpers.InboxHelper;
import helpers.edHelpers.LoginHelper;
import helpers.tmsHelpers.TmsHomePage;
import helpers.tmsHelpers.TmsInboxHelper;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.function.Consumer;

public class ApplicationManager {

    String typeOfBrowser;
    Properties properties;
    private static Playwright playwright;
    public static Browser browser;
    public static BrowserContext context;
    public Page page;

    BaseHelper baseHelper;
    HomePageHelper homePageHelper;
    LoginHelper loginHelper;
    InboxHelper inboxHelper;

    TmsInboxHelper tmsInboxHelper;
    TmsHomePage tmsHomePage;

    public TmsHomePage getTmsHomePage() {
        return tmsHomePage;
    }
    public TmsInboxHelper getTmsInboxHelper() {
        return tmsInboxHelper;
    }
    public HomePageHelper getHomePageHelper() {
        return homePageHelper;
    }
    public LoginHelper getLoginHelper() {
        return loginHelper;
    }

    public InboxHelper getInboxHelper() {
        return inboxHelper;
    }

    public BaseHelper getBaseHelper() {
        return baseHelper;
    }
    Consumer<Request> listener = null;


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

        int [] screenSize = getDimension();
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(screenSize[0], screenSize[1]));
        //context = browser.newContext();
        page = context.newPage();
        page.onRequest(request -> System.out.println("Request sent: " + request.url()));
        //page.onCrash();
        Consumer<Request> listener = request -> System.out.println("Request finished: " + request.url());
        page.onRequestFinished(listener);
        page.navigate("https://ed.engdis.com/QaExcellence#/login");
        page.setDefaultTimeout(60000);

        inboxHelper = new InboxHelper(page);
        loginHelper = new LoginHelper(page);
        tmsInboxHelper = new TmsInboxHelper(page);
        homePageHelper = new HomePageHelper(page);
        tmsHomePage = new TmsHomePage(page);

    }

    private int[] getDimension() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        System.out.println(width + " " + height);//1280 720

        return new int[]{width,height};
    }


    public void stop() {
        //page.offRequestFinished(listener);
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            context.close();
            playwright.close();
        }
    }
}
