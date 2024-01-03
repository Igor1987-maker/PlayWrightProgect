import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class LoginTests extends TestBase{


    @Test
    public void login() {

        String messageText = "Text to sent for teacher";
        page.setDefaultTimeout(60000);

        page.fill("[name='userName']", "ETC1");
        page.fill("[name='password']", "12345");
        page.click("id=submit1");
        page.waitForLoadState();
        //page.setDefaultTimeout(30000);
        //click on inbox and switch to new page
        Page page2 = context.waitForPage(() -> {
            page.locator("(//*[name()='svg'][@aria-label='navigate to Messages page'])[1]").click();; // Opens a new tab
        });
        page2.waitForLoadState();

        //switch to frame and click on Write message
            FrameLocator contactYourTeacherFrame = page2.frameLocator(".oed__iframe");
            contactYourTeacherFrame.locator("//*[@id='mainAreaTD']/table/tbody/tr[2]/td/table/tbody/tr/td[2]/a").click();
            //page2.frameLocator(".oed__iframe").locator("//*[@id='mainAreaTD']/table/tbody/tr[2]/td/table/tbody/tr/td[2]/a").click();



        //clickCheckbox to chose teacher
            //page2.frameLocator(".oed__iframe").locator("//input[@name='check1']").check();
            Locator teacherCheckBox = contactYourTeacherFrame.locator("//input[@name='check1']");
            teacherCheckBox.check();
            String teacherName = teacherCheckBox.getAttribute("value");
            teacherName = teacherName.split(" ")[0];
        // listOfTeachers.locator("//input[@name='check1']").check();

        //switch to pop-up by clicking  on wright button
        Page popup = page2.waitForPopup(() -> {
            page2.frameLocator(".oed__iframe").locator("//*[@id='mainAreaTD']/form/div[1]/table/tbody/tr[2]/td[2]/div/table/tbody/tr/td/a").click();
           //page2.locator("//*[@id='mainAreaTD']/form/div[1]/table/tbody/tr[2]/td[2]/div/table/tbody/tr/td/a").click();
        });
        popup.waitForLoadState();


        //Fill subject and text to body
        FrameLocator fr = popup.frameLocator("#ReadWrite");
        fr.locator("#strsubject").fill("Subject");

        FrameLocator nestedFrame = fr.frameLocator("#editorTextArea_ifr");
        nestedFrame.locator("#tinymce").fill(messageText);

        //click to send message
        fr.locator(".featureBarBtn:first-child").click();

        //Verify message stored in Sent items section
            contactYourTeacherFrame.locator("//*[@id='mainAreaTD']/table/tbody/tr[2]/td/table/tbody/tr/td[3]/a").click();
            //Locator messageToTeacher = contactYourTeacherFrame.locator("//a[contains(text(),'"+teacherName+"')]");
            List<Locator> locators = contactYourTeacherFrame.locator("//a[contains(text(),'"+teacherName+"')]").all();
           // Frame frame = page2.frame(".oed__iframe");
           // List<ElementHandle> elementHandles =  frame.querySelectorAll("a[title*='IgorTeacher']");

        try {
                //Assert.assertTrue(contactYourTeacherFrame.locator("//a[contains(text(),'" + teacherName + "')]").isVisible(), "message not sent or not stored");
                assertThat(locators.get(0)).isVisible();
                assertNotNull(locators.get(0), "Element is not found");
            }catch (Exception|AssertionError err){
            String pathToScreenShot = "screenshots/screenshot"+new Random().nextInt(2000) +".png";
                page2.screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get(pathToScreenShot)));
                        //.setFullPage(true));
            }
            //popup.frame("ReadWrite").locator("#strsubject").fill("Subject");
            //popup.frame("ReadWrite").frameLocator("editorTextArea_ifr").locator("#tinymce").fill("Text to send");
            //popup.close();
            page2.close();

            //logOut
            page.click("//li[contains(@class,'settingsMenu__personal')]");
            page.click("//li[contains(@class,'settingsMenu__listItem_logout')]");
            page.frameLocator("//div[@id='EdoFrameBoxContent']/iframe").locator("[name='btnOk']").click();


// login as admin
        page.fill("[name='userName']", teacherName);
        page.fill("[name='password']", "12345");
        page.click("id=submit1");
        page.waitForLoadState();

        //close notification
        page.locator(".notificationsCenter_hideSlide").click();
        page.waitForLoadState();

        //switch to main frame and click on communications
        FrameLocator mainFrame = page.frameLocator("#mainFrame");
        mainFrame.locator("//a[text()='Communication']").click();

        //click on inbox and switch to tableFrame
        mainFrame.locator("//a[contains(text(),'Inbox')]").click();


        //chose the class
        FrameLocator formframe = mainFrame.frameLocator("#FormFrame");
        formframe.locator("#SelectClass").selectOption("ExpiredTestClass");
        formframe.locator(".okButton2").click();

        //verify student name in latest message
        FrameLocator tableFrame = mainFrame.frameLocator("#tableFrame");
        Locator l = tableFrame.locator("//tbody[@id='tblBody']/tr[1]/td[5]");
        String studentName = l.innerText();
        studentName = studentName.split(" ")[0];
        assertTrue(studentName.equalsIgnoreCase("ETC1"),"Student name incorrect or no message");

        //click on latest student message
           //tableFrame.locator("//tbody[@id='tblBody']/tr[1]/td[1]/a").click();

        Page popup2 = page.waitForPopup(() -> {
            page.frameLocator("#mainFrame").frameLocator("#tableFrame").locator("//tbody[@id='tblBody']/tr[1]/td[1]/a").click();
        });
        popup2.waitForLoadState();
        String textMessage = popup2.locator("p").innerText();
        assertTrue(messageText.equals(textMessage),"text wasn't sent or it's not match");
        popup2.close();


        //logOut from TMS
        mainFrame.locator("//a[text()='Exit']").click();
        page.waitForLoadState();




       /* Page popup2 = tableFrame.wait(() -> {

            //page2.locator("//*[@id='mainAreaTD']/form/div[1]/table/tbody/tr[2]/td[2]/div/table/tbody/tr/td/a").click();
        });
        popup2.waitForLoadState();*/
       /* tableFrame.locator("//tbody[@id='tblBody']/tr[1]/td[1]/a").click();
        BrowserContext popupContext = browser.newContext();
        Page popupPage = popupContext.pages().get(0);*/


    }


        @Test
        public void demoTest () {
            Playwright playwright = Playwright.create();
            Browser browser = playwright.chromium().launch();
            context = browser.newContext(
                    new Browser.NewContextOptions()
                            .setHttpCredentials("admin", "admin")
            );
            Page page = context.newPage();
            page.navigate("https://the-internet.herokuapp.com/basic_auth");
            System.out.println(page.locator("h3").textContent());
            playwright.close();


        }


        @Test
        public void secondTest () {

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
        }

    }
