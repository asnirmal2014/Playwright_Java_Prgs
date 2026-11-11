
package com.pages;

import java.util.regex.Pattern;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

public class PlaywrightManager_2
{
    public static void main(String[] args)
    {
        try (Playwright playwright = Playwright.create())
        {
        	// Launch the browser (Chromium, Firefox, or WebKit) and setHeadless(false) opens the actual browser UI so you can see it working
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(2000));

            try
            {
            	// Create a new Browser Context (acts like a fresh Incognito/Private window)
                BrowserContext context = browser.newContext();
                
                // Creates a new page in the browser context. (a tab in the browser)
                Page page = context.newPage();
            	
                //Page page = browser.newPage();
                page.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
                System.out.println("Title: " + page.title());
                PlaywrightAssertions.assertThat(page).hasTitle(Pattern.compile("OrangeHRM"));
                System.out.println("Page title assertion passed!");
            }
            finally
            {
                browser.close();
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
