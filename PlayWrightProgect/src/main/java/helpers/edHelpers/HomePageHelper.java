package helpers.edHelpers;

import com.microsoft.playwright.Page;
import helpers.BaseHelper;

import static applications.ApplicationManager.context;

public class HomePageHelper extends BaseHelper {

    //Page page;
    public HomePageHelper(Page page) {
        super(page);
    }


    public Page openInboxPage() {
        Page page2 = context.waitForPage(() -> {
            page.locator("(//*[name()='svg'][@aria-label='navigate to Messages page'])[1]").click();; // Opens a new tab
        });
        page2.waitForLoadState();

        return page2;
    }

    public void logOut() {
        page.click("//li[contains(@class,'settingsMenu__personal')]");
        page.click("//li[contains(@class,'settingsMenu__listItem_logout')]");
        page.frameLocator("//div[@id='EdoFrameBoxContent']/iframe").locator("[name='btnOk']").click();
    }
}
