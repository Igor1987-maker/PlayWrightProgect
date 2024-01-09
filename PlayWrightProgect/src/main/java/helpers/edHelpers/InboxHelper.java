package helpers.edHelpers;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import helpers.BaseHelper;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class InboxHelper extends BaseHelper {
    //Page page;

    FrameLocator contactYourTeacherFrame;
    public InboxHelper(Page page) {
        super(page);
    }

    public String wrightMessageToTeacher(Page page2, String messageText) {

        //page = page2;

        //switch to frame and click on Write message
        contactYourTeacherFrame = page2.frameLocator(".oed__iframe");
        contactYourTeacherFrame.locator("//*[@id='mainAreaTD']/table/tbody/tr[2]/td/table/tbody/tr/td[2]/a").click();

        //clickCheckbox to chose teacher
        Locator teacherCheckBox = contactYourTeacherFrame.locator("//input[@name='check1']");
        teacherCheckBox.check();
        teacherName = teacherCheckBox.getAttribute("value");
        teacherName = teacherName.split(" ")[0];

        //switch to pop-up by clicking  on wright button
        Page popup = page2.waitForPopup(() -> {
            page2.frameLocator(".oed__iframe").locator("//*[@id='mainAreaTD']/form/div[1]/table/tbody/tr[2]/td[2]/div/table/tbody/tr/td/a").click();
        });
        popup.waitForLoadState();

        //Fill subject and text to body
        FrameLocator fr = popup.frameLocator("#ReadWrite");
        fr.locator("#strsubject").fill("Subject");

        FrameLocator nestedFrame = fr.frameLocator("#editorTextArea_ifr");
        nestedFrame.locator("#tinymce").fill(messageText);

        //click to send message
        fr.locator(".featureBarBtn:first-child").click();

        return  teacherName;
    }

    public List<Locator> getMessagesFromSentSection(String teacherName) throws InterruptedException {
        contactYourTeacherFrame.locator("//*[@id='mainAreaTD']/table/tbody/tr[2]/td/table/tbody/tr/td[3]/a").click();
        Thread.sleep(3000);
        //contactYourTeacherFrame.wait(4000);
        List<Locator> locators = contactYourTeacherFrame.locator("//a[contains(text(),'"+teacherName+"')]").all();
        return locators;
    }

    public void closePage(Page newPage){
        if(newPage!=null) {
            newPage.close();
        }else {
            page.close();
        }
    }

}
