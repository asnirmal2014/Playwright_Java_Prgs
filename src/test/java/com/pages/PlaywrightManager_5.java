package com.pages;

import java.nio.file.Paths;
import java.util.regex.Pattern;
import com.microsoft.playwright.Browser;
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
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			Page page = browser.newPage();
			page.navigate("https://playwright.dev/");
			page.waitForTimeout(3000);
			System.out.println("Title: " + page.title());
			PlaywrightAssertions.assertThat(page).hasTitle(Pattern.compile("Fast and reliable end-to-end testing for modern web apps | Playwright"));
			
			page.screenshot(  new Page.ScreenshotOptions().setPath(Paths.get("PagePrint.png"))  );            // relative path for the file 'PagePrint.png'

			page.screenshot(  new Page.ScreenshotOptions().setPath(Paths.get("D:/screenshots/PagePrint.png"))  );  // absolute path for the file 'PagePrint.png'
		}
		catch (Exception e) 
        {
            e.printStackTrace();
        }
	}
}

/*
Taking Screenshot of the page:
------------------------------

page.screenshot(  new Page.ScreenshotOptions().setPath(Paths.get("PagePrint.png"))  );

page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("PagePrint.png")));
1. `page.screenshot(...)` captures a screenshot of the current page.
2. `new Page.ScreenshotOptions()` creates an options object where you configure how the screenshot is taken (path, full page, clip region, quality, etc.).
3. `.setPath(...)` tells Playwright where to save the file — and it also makes Playwright automatically write the image to disk for you.

Why `Paths.get("PagePrint.png")`?
`setPath()` doesn't accept a plain `String`. Its signature is:
ScreenshotOptions setPath(Path path)

So you must pass a `java.nio.file.Path` object. 
`Paths.get("PagePrint.png")` is the standard Java NIO way of creating one — it's a factory method that converts a path string into a `Path` instance.
You're right that we didn't give a directory — and that's fine:

- "PagePrint.png" is a relative path. 
  Java resolves it against the working directory of the process (wherever you launched the test from, e.g. the project root in Maven/Gradle runs).
- If you wanted a specific location, you'd write it as a path string:

Paths.get("screenshots/PagePrint.png")                  // relative, subfolder
Paths.get("/home/user/output/PagePrint.png")            // absolute (Linux/Mac)
Paths.get("C:\\screenshots\\PagePrint.png")             // absolute (Windows)

Equivalent long form:
Page.ScreenshotOptions options = new Page.ScreenshotOptions();
options.setPath(Paths.get("PagePrint.png"));
page.screenshot(options);

The single-line version is just method chaining. 
Bonus: `setPath()` also returns the options object, which is why chaining works.

Alternative — if you'd rather handle the bytes yourself (e.g. attach to a report), skip the path and use the return value:
byte[] image = page.screenshot();
Files.write(Paths.get("PagePrint.png"), image);
*/