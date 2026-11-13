package com.pages;

import java.nio.file.Paths;
import java.util.Arrays;                        
import java.util.regex.Pattern;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext; 
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

public class PlaywrightManager_5
{
	public static void main(String[] args) 
	{
		try (Playwright playwright = Playwright.create()) 
		{
			// Launch Chromium with the "--start-maximized" argument
			Browser browser = playwright.chromium().launch(
				new BrowserType.LaunchOptions()
					.setHeadless(false)
					.setArgs(Arrays.asList("--start-maximized")));
			
			// Create a BrowserContext with viewport set to 'null' 
			// This prevents Playwright from forcing a default viewport size (e.g., 1280x720)
			BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
			
			// Create a new page from the context and not directly from the browser
			Page page = context.newPage();
			
			page.navigate("https://playwright.dev/");
			page.waitForTimeout(3000);
			System.out.println("Title: " + page.title());
			
			PlaywrightAssertions.assertThat(page).hasTitle(Pattern.compile("Fast and reliable end-to-end testing for modern web apps | Playwright"));
			
			// Relative path for the file 'PagePrint.png'
			page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("PagePrint.png")));
			
			// Absolute path for the file 'PagePrint.png'
			page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("D:/screenshots/PagePrint.png")));
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
