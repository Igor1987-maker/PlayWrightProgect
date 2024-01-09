package helpers;

import com.microsoft.playwright.Page;

import java.nio.file.Paths;
import java.util.Random;

public class BaseHelper {

    public Page page;
    public static String teacherName;

    public BaseHelper(Page page) {
        this.page = page;
    }


    public void makeScreenShot(Page page2) {
        String pathToScreenShot = "screenshots/screenshot"+new Random().nextInt(2000) +".png";
        page2.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(pathToScreenShot)));
    }


    public void logOutFromTMS(){
        page.frameLocator("#mainFrame").locator("//a[text()='Exit']").click();
        page.waitForLoadState();

    }


    public void closeAllNotifications() {
        boolean closeNotification = true;
        while (closeNotification) {
                try {
                    page.locator(".notificationsCenter_hideSlide").click();
                    page.waitForLoadState();
                    page.setDefaultTimeout(2000);
                }catch (Exception|AssertionError err){
                    closeNotification = false;
                    page.setDefaultTimeout(60000);
           }
       }
    }



}
