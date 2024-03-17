package com.tribe.workshop.appium.tests.march2024;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

public class CalculatorMarchTests {
    private AndroidDriver driver;
    private WebDriverWait wait;

    AppiumDriverLocalService appiumService;

    @BeforeSuite
    public void startAppiumServer() {
        // Build server arguments
        AppiumServiceBuilder appiumServerArguments = new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withIPAddress("127.0.0.1")
                .withTimeout(Duration.ofMinutes(5));

        // Build a service with argument
        appiumService = AppiumDriverLocalService.buildService(appiumServerArguments);

        // start server
        appiumService.start();
    }

    @AfterSuite
    public void stopServer() {
        appiumService.stop();
    }
    @BeforeClass
    public void initializeDriver() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setPlatformVersion("13")
                .setAppPackage("com.google.android.calculator")
                .setAppActivity("com.android.calculator2.Calculator")
                .setAutomationName("UiAutomator2")
                .setDeviceName("Mi A1");

        driver = new AndroidDriver(appiumService.getUrl(), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }


    private WebElement findElement(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    @Test
    public void addTest() {
        // First value
        int a = 10;
        int b = 20;

        char[] aAsArray = String.valueOf(a).toCharArray(); // [1, 0]
        char[] bAsArray = String.valueOf(b).toCharArray(); // [2, 0]

        for(char each : aAsArray) {
            findElement(AppiumBy.accessibilityId(String.valueOf(each))).click();
        }

        // perform plus
        findElement(AppiumBy.accessibilityId("plus")).click();
        for(char each : bAsArray) {
            findElement(AppiumBy.accessibilityId(String.valueOf(each))).click();
        }

        // click equals
        findElement(AppiumBy.accessibilityId("equals")).click();

        // get the value of result
        WebElement resultElement = findElement(AppiumBy.id("com.google.android.calculator:id/result_final"));
        System.out.println("Final Result : " + resultElement.getText());
    }
    @AfterClass
    public void cleanUp() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }
}
