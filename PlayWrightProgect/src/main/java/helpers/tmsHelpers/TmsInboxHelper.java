package helpers.tmsHelpers;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import helpers.BaseHelper;

public class TmsInboxHelper extends BaseHelper {
    public TmsInboxHelper(Page page) {
        super(page);
    }

    @Override
    public void makeScreenShot(Page page2) {
        super.makeScreenShot(page2);
    }

    @Override
    public void logOutFromTMS() {
        super.logOutFromTMS();
    }

    public void goToInbox() {
        //switch to main frame and click on communications
        FrameLocator mainFrame = page.frameLocator("#mainFrame");
        mainFrame.locator("//a[text()='Communication']").click();

        //click on inbox and switch to tableFrame
        mainFrame.locator("//a[contains(text(),'Inbox')]").click();
    }

    public String choseClassAndVerifyMessageFromStudent(String className) {
        //chose the class
        FrameLocator mainFrame = page.frameLocator("#mainFrame");

        FrameLocator formframe = mainFrame.frameLocator("#FormFrame");
        formframe.locator("#SelectClass").selectOption(className);
        formframe.locator(".okButton2").click();

        //verify student name in latest message
        FrameLocator tableFrame = mainFrame.frameLocator("#tableFrame");
        Locator l = tableFrame.locator("//tbody[@id='tblBody']/tr[1]/td[5]");
        String studentName = l.innerText();
        studentName = studentName.split(" ")[0];

        return studentName;

    }

    public String openLatestMessageAndReadContent() {
        Page popup2 = page.waitForPopup(() -> {
            page.frameLocator("#mainFrame").frameLocator("#tableFrame").locator("//tbody[@id='tblBody']/tr[1]/td[1]/a").click();
        });
        popup2.waitForLoadState();
        String textMessage = popup2.locator("p").innerText();
        popup2.close();

        return textMessage;
    }

    /*public void logOutFromTMS() {
        page.frameLocator("#mainFrame").locator("//a[text()='Exit']").click();
        page.waitForLoadState();
    }*/
}
