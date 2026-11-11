package com.pages;

import java.util.regex.Pattern;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

public class PlaywrightManager_1 
{
	public static void main(String[] args) 
	{
		try (Playwright playwright = Playwright.create()) 
		{
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			Page page = browser.newPage();
			page.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
			page.waitForTimeout(3000);
			System.out.println("Title :" + page.title());
			PlaywrightAssertions.assertThat(page).hasTitle(Pattern.compile("OrangeHRM"));
		}
		catch (Exception e) 
        {
            e.printStackTrace();
        }
	}
}
