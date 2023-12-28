import com.microsoft.playwright.*;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class DemoTest {


    @Test
    public void demoTest(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch();
        BrowserContext context = browser.newContext(
                new Browser.NewContextOptions()
                        .setHttpCredentials("admin", "admin")
        );
        Page page = context.newPage();
        page.navigate("https://the-internet.herokuapp.com/basic_auth");
        System.out.println(page.locator("h3").textContent());
        playwright.close();



    }


    @Test
    public void secondTest(){

        Playwright playwright = Playwright.create();
        BrowserType.LaunchOptions setHeadless = new BrowserType.LaunchOptions().setHeadless(false);
        Page page = playwright.chromium().launch(setHeadless).newPage();
        page.navigate("https://letcode.in/file");


        Download download = page.waitForDownload(()->{
            page.locator("'Download Excel'").click();
        });
        System.out.println(download.path());
        System.out.println(download.url());
        System.out.println(download.failure());
        System.out.println(download.suggestedFilename());
        download.saveAs(Paths.get(download.suggestedFilename()));
        playwright.close();
    }

}
