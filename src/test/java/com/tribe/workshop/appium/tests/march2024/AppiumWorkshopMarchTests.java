package com.tribe.workshop.appium.tests.march2024;

import com.tribe.workshop.appium.helpers.SystemHelper;
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

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AppiumWorkshopMarchTests {
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
    public void initializeDriver() throws MalformedURLException {
//        UiAutomator2Options options = new UiAutomator2Options()
//                .setPlatformName("Android")
//                .setPlatformVersion("13")
//                .setAppPackage("com.vodqareactnative")
//                .setAppActivity("com.vodqareactnative.MainActivity")
//                .setAutomationName("UiAutomator2")
//                .setDeviceName("Mi A1");

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setPlatformVersion("13")
                .setApp(SystemHelper.getUserDirectory().concat("/apps/VodQA.apk"))
                .setAutomationName("UiAutomator2")
                .setDeviceName("Mi A1");

        driver = new AndroidDriver(appiumService.getUrl(), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void loginTest() {
        // get username element
        WebElement usernameElement = findElement(AppiumBy.accessibilityId("username"));
        // clear text
        usernameElement.clear();
        // enter text
        usernameElement.sendKeys("admin");
        // get password element
        WebElement passwordElement = findElement(AppiumBy.accessibilityId("password"));
        // clear text
        passwordElement.clear();
        // enter text
        passwordElement.sendKeys("admin");

        // click on login button
        findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOG IN\")")).click();

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
