package com.pages;

import java.nio.file.Paths;
import java.util.regex.Pattern;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

public class PlaywrightManager_4
{
	public static void main(String[] args) 
	{
		try (Playwright playwright = Playwright.create()) 
		{
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			Page page = browser.newPage();
			page.navigate("https://playwright.dev/");
			page.waitForTimeout(3000);
			System.out.println("Title :" + page.title());
			PlaywrightAssertions.assertThat(page).hasTitle(Pattern.compile("Fast and reliable end-to-end testing for modern web apps | Playwright"));
			page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));
		}
		catch (Exception e) 
        {
            e.printStackTrace();
        }
	}
}
