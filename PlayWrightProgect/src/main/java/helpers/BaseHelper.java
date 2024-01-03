package helpers;

import com.microsoft.playwright.Page;

public class BaseHelper {

    Page page;

    public BaseHelper(Page page) {
        this.page = page;
    }


}
