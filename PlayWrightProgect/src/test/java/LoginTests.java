import com.aventstack.extentreports.Status;
import com.microsoft.playwright.*;
import helpers.BaseHelper;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class LoginTests extends TestBase{

    @BeforeMethod
    public void onStart(Method m){
        //logger.info("Starting method "+m.getName());
        test = extent.createTest(m.getName());
        test.log(Status.INFO,"Starting the test "+m.getName());
    }

    @Test
    public void login() throws InterruptedException {
        String messageText = "Text to sent for teacher";

        test.log(Status.INFO,"Login with user ETC1");
            app.getLoginHelper().loginFromED("ETC1","12345");
            Page page2 = app.getHomePageHelper().openInboxPage();
            String teacherName = app.getInboxHelper().wrightMessageToTeacher(page2, messageText);

        test.log(Status.INFO,"Get all messages from 'Sent section'");
            List<Locator> messagesFromSentSection = app.getInboxHelper().getMessagesFromSentSection(teacherName);

        test.log(Status.INFO,"Verify that latest sent message stored");
            try {
                assertThat(messagesFromSentSection.get(0)).isVisible();
                assertNotNull(messagesFromSentSection.get(0), "Element is not found");
            }catch (Exception|AssertionError err){
                BaseHelper baseHelper = new BaseHelper(page2);
                baseHelper.makeScreenShot(page2);

            }

        app.getInboxHelper().closePage(page2);
        app.getHomePageHelper().logOut();
        app.getLoginHelper().loginFromED(teacherName,"12345");

        app.getTmsHomePage().closeAllNotifications();
        app.getTmsInboxHelper().goToInbox();

        String studentName = app.getTmsInboxHelper().choseClassAndVerifyMessageFromStudent("ExpiredTestClass");
        assertTrue(studentName.equalsIgnoreCase("ETC1"),"Student name incorrect or no message");

        String textMessage = app.getTmsInboxHelper().openLatestMessageAndReadContent();
        assertTrue(messageText.equals(textMessage),"text wasn't sent or it's not match");

        app.getTmsInboxHelper().logOutFromTMS();


    }


        @Test
        public void demoTest () {
          /*  Playwright playwright = Playwright.create();
            Browser browser = playwright.chromium().launch();
            context = browser.newContext(
                    new Browser.NewContextOptions()
                            .setHttpCredentials("admin", "admin")
            );
            Page page = context.newPage();
            page.navigate("https://the-internet.herokuapp.com/basic_auth");
            System.out.println(page.locator("h3").textContent());
            playwright.close();*/


        }


        @Test
        public void secondTest () {
            test.log(Status.INFO,"Second test");
            Playwright playwright = Playwright.create();
            BrowserType.LaunchOptions setHeadless = new BrowserType.LaunchOptions().setHeadless(false);
            Page page = playwright.chromium().launch(setHeadless).newPage();
            page.navigate("https://letcode.in/file");


            Download download = page.waitForDownload(() -> {
                page.locator("'Download Excel'").click();
            });
            System.out.println(download.path());
            System.out.println(download.url());
            System.out.println(download.failure());
            System.out.println(download.suggestedFilename());
            download.saveAs(Paths.get(download.suggestedFilename()));
            playwright.close();

            test.log(Status.PASS,"Passed");
        }

    @AfterMethod
    public void afterTest(){

    }

    }
