package com.pages;

import java.nio.file.Paths;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.LoadState;

public class PlaywrightBasicTest_1 
{
    public static void main(String[] args) 
    {
        // 1. Create a Playwright instance (using try-with-resources to auto-close)
        try (Playwright playwright = Playwright.create()) 
        {
            // 2. Launch the browser (Chromium, Firefox, or WebKit)
            // setHeadless(false) opens the actual browser UI so you can see it working
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false) 
                    .setSlowMo(500)); // Slows down actions by 500ms so it's easier to watch
            
            // 3. Create a new Browser Context (acts like a fresh Incognito/Private window)
            BrowserContext context = browser.newContext();
            
            // 4. Create a new Page (a tab in the browser)
            Page page = context.newPage();

            System.out.println("Navigating to Bing...");
            // 5. Navigate to a URL
            page.navigate("https://www.bing.com");

            System.out.println("Searching for 'Playwright Java'...");
            // 6. Interact with the page using modern, resilient locators
            // We find the search box by its placeholder text and type into it
            Locator searchBox = page.getByPlaceholder("Search");
            searchBox.fill("Playwright Java");
            
            // Press Enter to search
            searchBox.press("Enter");

            // 7. Wait for the page to finish loading/network activity
            page.waitForLoadState(LoadState.NETWORKIDLE);
            
            System.out.println("Taking a screenshot...");
            // 8. Take a screenshot and save it to the project root
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("playwright_screenshot.png"))
                    .setFullPage(true)); // Captures the whole scrollable page

            System.out.println("Test completed successfully! Check 'playwright_screenshot.png'.");
            
            // Note: The try-with-resources block automatically closes the browser 
            // and playwright instances cleanly when it finishes.
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}