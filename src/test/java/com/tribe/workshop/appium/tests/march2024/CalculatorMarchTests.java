package com.tribe.workshop.appium.tests.march2024;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

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

    @AfterClass
    public void cleanUp() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }
}
