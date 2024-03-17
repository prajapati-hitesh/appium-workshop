package com.tribe.workshop.appium.tests.march2024;

import com.tribe.workshop.appium.helpers.SystemHelper;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;

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

    @Test
    public void dragAndDropTest() {
        // navigate to drag and drop screen
        findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Drag & Drop\")")).click();

        WebElement dragMeElement = findElement(AppiumBy.accessibilityId("dragMe"));
        WebElement dropMeElement = findElement(AppiumBy.accessibilityId("dropzone"));

        // create first finger event
        PointerInput firstFinger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");

        Sequence dragAndDropSequence = new Sequence(firstFinger, 1)
                .addAction(firstFinger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), getCenterOfElement(dragMeElement.getLocation(), dragMeElement.getSize())))
                .addAction(firstFinger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(firstFinger.createPointerMove(Duration.ofMillis(900), PointerInput.Origin.viewport(), getCenterOfElement(dropMeElement.getLocation(), dropMeElement.getSize())))
                .addAction(firstFinger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // execute actions
        driver.perform(Collections.singleton(dragAndDropSequence));
    }

    public Point getCenterOfElement(Point elementLocation, Dimension elementSize) {
        return new Point(elementLocation.getX() + (elementSize.getWidth() / 2),
                elementLocation.getY() + (elementSize.getHeight() / 2));
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
