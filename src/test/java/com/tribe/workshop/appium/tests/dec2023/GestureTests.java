package com.tribe.workshop.appium.tests.dec2023;

import com.github.javafaker.App;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

public class GestureTests {

    private static final Logger logger = LogManager.getLogger(GestureTests.class);
    static AppiumDriverLocalService appiumService;
    AndroidDriver driver;
    WebDriverWait wait;

    @BeforeSuite
    public void setupAppiumServer() {
        AppiumServiceBuilder appiumServerArguments = new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withIPAddress("127.0.0.1")
                .withTimeout(Duration.ofMinutes(5));

        appiumService = AppiumDriverLocalService.buildService(appiumServerArguments);

        // start appium server
        appiumService.start();
        logger.info("Appium Server Started At : " + appiumService.getUrl());
    }

    @AfterSuite
    public void stopAppiumServer() {
        logger.info("Stopping Appium Server");
        appiumService.stop();
    }

    @BeforeTest
    public void setupAppUnderTest() throws URISyntaxException, MalformedURLException {
        UiAutomator2Options uiAutomator2Options = new UiAutomator2Options()
                .setDeviceName("Mi A1")
                .setPlatformName("Android")
                .setPlatformVersion("13")
                .setAppPackage("com.vodqareactnative")
                .setAppActivity("com.vodqareactnative.MainActivity")
                //.setApp(SystemHelper.getUserDirectory().concat("/apps/VodQA.apk"))
                .setAutomationName("UiAutomator2");

        // start driver
        driver = new AndroidDriver(new URI(appiumService.getUrl().toString()).toURL(), uiAutomator2Options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        logger.info("Is App Installed : " + driver.isAppInstalled("com.vodqareactnative"));
        findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOG IN\")")).click();
    }

    @Test
    public void sliderGestureTest() {
        // navigate to slider screen
        findElement(AppiumBy.accessibilityId("slider1")).click();

        // perform gesture
        WebElement sliderElement = findElement(AppiumBy.accessibilityId("slider"));

        // center x cordinate for slider - X + (elementWidth/2)
        int centerXLocation = sliderElement.getLocation().getX() + (sliderElement.getSize().getWidth() / 2);
        // create finger object
        PointerInput firstFinger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");

        Sequence sliderSequence = new Sequence(firstFinger, 1)
                .addAction(firstFinger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), getCenterOfElement(sliderElement.getLocation(), sliderElement.getSize())))
                .addAction(firstFinger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(firstFinger, Duration.ofSeconds(1)))
                .addAction(firstFinger.createPointerMove(Duration.ofSeconds(2), PointerInput.Origin.viewport(), centerXLocation, sliderElement.getLocation().getY()))
                .addAction(firstFinger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singleton(sliderSequence));
    }

    @Test
    public void multiTouchIndividualSliderTest() {
        // navigate to slider screen
        findElement(AppiumBy.accessibilityId("slider1")).click();

        // perform gesture
        WebElement firstSliderElement = findElement(AppiumBy.accessibilityId("slider"));
        WebElement secondSliderElement = findElement(AppiumBy.accessibilityId("slider1"));

        // center x coordinate for slider - X + (elementWidth/2)
        int firstSliderCenterX = firstSliderElement.getLocation().getX() + (firstSliderElement.getSize().getWidth() / 2);
        int secondSliderCenterX = secondSliderElement.getLocation().getX() + (secondSliderElement.getSize().getWidth() / 2);
        // Create Finger event for first slider
        PointerInput firstFinger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");

        // Create sequence of actions for first slider
        Sequence firstSliderSequence = new Sequence(firstFinger, 1)
                .addAction(firstFinger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), getCenterOfElement(firstSliderElement.getLocation(), firstSliderElement.getSize())))
                .addAction(firstFinger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(firstFinger, Duration.ofSeconds(1)))
                .addAction(firstFinger.createPointerMove(Duration.ofSeconds(2), PointerInput.Origin.viewport(), firstSliderCenterX, firstSliderElement.getLocation().getY()))
                .addAction(firstFinger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Create Finger event for first slider
        PointerInput secondFinger = new PointerInput(PointerInput.Kind.TOUCH, "finger2");

        // Create sequence of actions for first slider
        Sequence secondSliderSequence = new Sequence(secondFinger, 1)
                .addAction(secondFinger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), getCenterOfElement(secondSliderElement.getLocation(), secondSliderElement.getSize())))
                .addAction(secondFinger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(secondFinger, Duration.ofSeconds(1)))
                .addAction(secondFinger.createPointerMove(Duration.ofMillis(800), PointerInput.Origin.viewport(), secondSliderCenterX, secondSliderElement.getLocation().getY()))
                .addAction(secondFinger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(firstSliderSequence, secondSliderSequence));
    }

    @Test
    public void multiTouchSliderTest() {
        // navigate to slider screen
        findElement(AppiumBy.accessibilityId("slider1")).click();

        // perform gesture
        WebElement firstSliderElement = findElement(AppiumBy.accessibilityId("slider"));
        WebElement secondSliderElement = findElement(AppiumBy.accessibilityId("slider1"));

        // center x coordinate for slider - X + (elementWidth/2)
        int firstSliderCenterX = firstSliderElement.getLocation().getX() + (firstSliderElement.getSize().getWidth() / 2);
        int secondSliderCenterX = secondSliderElement.getLocation().getX() + (secondSliderElement.getSize().getWidth() / 2);
        // Create Finger event for first slider
        PointerInput firstFinger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        // Create Finger event for first slider
        PointerInput secondFinger = new PointerInput(PointerInput.Kind.TOUCH, "finger2");

        // Create sequence of actions for first slider
        Sequence sliderSequence = new Sequence(firstFinger, 1)
                .addAction(firstFinger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), getCenterOfElement(firstSliderElement.getLocation(), firstSliderElement.getSize())))
                .addAction(secondFinger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), getCenterOfElement(secondSliderElement.getLocation(), secondSliderElement.getSize())))
                .addAction(firstFinger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(secondFinger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(firstFinger, Duration.ofSeconds(1)))
                .addAction(new Pause(secondFinger, Duration.ofSeconds(1)))
                .addAction(firstFinger.createPointerMove(Duration.ofSeconds(2), PointerInput.Origin.viewport(), firstSliderCenterX, firstSliderElement.getLocation().getY()))
                .addAction(secondFinger.createPointerMove(Duration.ofMillis(800), PointerInput.Origin.viewport(), secondSliderCenterX, secondSliderElement.getLocation().getY()))
                .addAction(firstFinger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(secondFinger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singleton(sliderSequence));
    }

    @Test
    public void dragAndDropTest() {
        findElement(AppiumBy.accessibilityId("dragAndDrop")).click();
        WebElement dragMeElement = findElement(AppiumBy.accessibilityId("dragMe"));
        WebElement dropMeElement = findElement(AppiumBy.accessibilityId("dropzone"));

        // find center location of drag Me & Drop Me
        Point centerXofDragMe = getCenterOfElement(dragMeElement.getLocation(), dragMeElement.getSize());
        Point centerXofDropMe = getCenterOfElement(dropMeElement.getLocation(), dropMeElement.getSize());

        PointerInput dragMeInput = new PointerInput(PointerInput.Kind.TOUCH, "finger1");

        Sequence dragAndDropSequence = new Sequence(dragMeInput, 1)
                .addAction(dragMeInput.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerXofDragMe))
                .addAction(dragMeInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(dragMeInput.createPointerMove(Duration.ofMillis(800), PointerInput.Origin.viewport(), centerXofDropMe))
                .addAction(dragMeInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singleton(dragAndDropSequence));
    }

    private WebElement findElement(By selector) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    private Point getCenterOfElement(Point location, Dimension size) {
        return new Point(location.getX() + size.getWidth() / 2,
                location.getY() + size.getHeight() / 2);
    }

    @AfterTest
    public void closeAppUnderTest() throws InterruptedException {
        Thread.sleep(5000);
        driver.terminateApp("com.vodqareactnative");
        driver.quit();
    }
}
