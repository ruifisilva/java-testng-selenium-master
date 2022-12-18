package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
//Selenium 3
//import org.openqa.selenium.remote.DesiredCapabilities;

public class TestNGTodo1 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {

        String hub = "@hub.lambdatest.com/wd/hub";

        //https://www.lambdatest.com/capabilities-generator/
        
        //Selenium 3
        /*
        String username = "ruifisilva";
        String authkey = "ISD6Xf1p1CF4qXiciA6FpEiyWzFQn0VRlRzdZu337GdGKujtGQ";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "86.0");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        //ltOptions.put("username", "ruifisilva");
        //ltOptions.put("authkey", "ISD6Xf1p1CF4qXiciA6FpEiyWzFQn0VRlRzdZu337GdGKujtGQ");
        ltOptions.put("visual", true);
        ltOptions.put("video", true);
        ltOptions.put("platformName", "Windows 10");
        ltOptions.put("network", true);
        ltOptions.put("project", "Untitled");
        ltOptions.put("console", "true");
        ltOptions.put("selenium_version", "3.13.0");
        capabilities.setCapability("terminal",true);
        capabilities.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), capabilities);
        */

        //Selenium 4
        String username = "ruifisilva";
        String accessKey = "ISD6Xf1p1CF4qXiciA6FpEiyWzFQn0VRlRzdZu337GdGKujtGQ";

        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("86.0");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", "ruifisilva");
        ltOptions.put("accessKey", "ISD6Xf1p1CF4qXiciA6FpEiyWzFQn0VRlRzdZu337GdGKujtGQ");
        ltOptions.put("visual", true);
        ltOptions.put("video", true);
        ltOptions.put("network", true);
        ltOptions.put("project", "Untitled");
        ltOptions.put("console", "true");
        ltOptions.put("selenium_version", "4.1.0");
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");
        browserOptions.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + hub), browserOptions);
    }

    /**
     * @throws InterruptedException
     */
    @Test(timeOut = 20000)
    public void basicTest() throws InterruptedException {
        System.out.println("Loading Url");

        //1. Navigate to https://www.labdatest.com
        driver.get("https://www.lambdatest.com/");

        //2. Perform an explicit wait till the time all elements in the DOM are available (Locator: cssSelector)
        //WebDriverWait wait = new WebDriverWait(driver, 10);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("*")));

        //3. Scroll to the webelement 'SEE ALL INTEGRATIONS'. (Locator: linktext)
        driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.linkText("SEE ALL INTEGRATIONS")));
        //scroll 100 pixeis up
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,-100)");

        //4. Click on the link and ensure it opens a new tab. (Locator: xpath)
        driver.findElement(By.xpath("//a[text()='See All Integrations']")).sendKeys(Keys.CONTROL , Keys.RETURN);

        //5. Save the window handles in a List. Print the window handles of the opened windows.
        //Saves the window handles in a list
        ArrayList<String> handles = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(handles.get(1));
        //Print the window handles
        System.out.println(handles.get(0));
        System.out.println(handles.get(1));

        //6. Verify URL with expected URL
        // Get the current URL of the page
        String currentUrl = driver.getCurrentUrl();
        // Set the expected URL
        String expectedUrl = "https://www.lambdatest.com/integrations";
        System.out.println(expectedUrl);
        // Verify that the current URL is the same as the expected URL
        Assert.assertEquals(currentUrl, expectedUrl);

        //7. Close the current window
        driver.close();


        Status = "passed";
        Thread.sleep(150);

        System.out.println("TestFinished");

    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
  }

