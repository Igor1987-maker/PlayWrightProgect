package helpers.edHelpers;

import com.microsoft.playwright.Page;
import helpers.BaseHelper;

public class LoginHelper extends BaseHelper {
    //Page page;

    public LoginHelper(Page page) {
        super(page);
    }

    public void loginFromED(String name, String password) {
        page.fill("[name='userName']", name);
        page.fill("[name='password']", password);
        page.click("id=submit1");
        page.waitForLoadState();

    }
}
