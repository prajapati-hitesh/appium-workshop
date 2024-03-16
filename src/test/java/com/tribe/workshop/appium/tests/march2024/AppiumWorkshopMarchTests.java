package com.tribe.workshop.appium.tests.march2024;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class AppiumWorkshopMarchTests {

    @Test
    public void myFirstTest() throws URISyntaxException, MalformedURLException, InterruptedException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setPlatformVersion("13")
                .setAppPackage("com.vodqareactnative")
                .setAppActivity("com.vodqareactnative.MainActivity")
                .setAutomationName("UiAutomator2")
                .setDeviceName("Mi A1");

        AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);

        Thread.sleep(5000);
        // get username element
        WebElement usernameElement = driver.findElement(AppiumBy.accessibilityId("username"));
        // clear text
        usernameElement.clear();
        // enter text
        usernameElement.sendKeys("admin");
        // get password element
        WebElement passwordElement = driver.findElement(AppiumBy.accessibilityId("password"));
        // clear text
        passwordElement.clear();
        // enter text
        passwordElement.sendKeys("admin");

        // click on login button
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOG IN\")")).click();
        Thread.sleep(5000);
        driver.quit();
    }
}
