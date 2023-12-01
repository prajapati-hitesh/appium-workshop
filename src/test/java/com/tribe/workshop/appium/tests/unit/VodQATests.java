package com.tribe.workshop.appium.tests.unit;

import com.tribe.workshop.appium.boilerplate.service.AppiumServer;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.stream.Collectors;

public class VodQATests {
    private static final Logger logger = LogManager.getLogger(VodQATests.class.getName());
    AppiumDriverLocalService appiumLocalService;
    String APPIUM_SERVER_URL;
    @BeforeSuite(alwaysRun = true)
    public void setupAppiumServer() {
         appiumLocalService= new AppiumServer().buildAppiumServerProcess("127.0.0.1");

        // start server
        appiumLocalService.start();

        APPIUM_SERVER_URL = String.valueOf(appiumLocalService.getUrl());
        logger.info("Appium Server Started At : " + APPIUM_SERVER_URL);
    }

    @Test(alwaysRun = true)
    public void setupDevice() throws MalformedURLException, URISyntaxException, InterruptedException {
        UiAutomator2Options uiAutomator2Options = new UiAutomator2Options()
                .setAutomationName("UiAutomator2")
                .setPlatformName("Android")
                .setPlatformVersion("13")
                .setAppPackage("com.vodqareactnative")
                .setAppActivity("com.vodqareactnative.MainActivity");

        AndroidDriver driver = new AndroidDriver(new URI(APPIUM_SERVER_URL).toURL(), uiAutomator2Options);

        try {
            Thread.sleep(2000);
            driver.findElement(AppiumBy.accessibilityId("username")).clear();
            driver.findElement(AppiumBy.accessibilityId("username")).sendKeys("admin");
            driver.findElement(AppiumBy.accessibilityId("password")).clear();
            driver.findElement(AppiumBy.accessibilityId("password")).sendKeys("admin");
            driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOG IN\")")).click();

            // navigate to drag & drop
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("dragAndDrop"))).click();

            WebElement dragMeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("dragMe")));
            WebElement dropMeElement = driver.findElement(AppiumBy.accessibilityId("dropzone"));
            Thread.sleep(2000);

            PointerInput fingerOne = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
            Sequence dragAndDropSequence = new Sequence(fingerOne, 1)
                    .addAction(fingerOne.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), dragMeElement.getLocation()))
                    .addAction(fingerOne.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                    .addAction(new Pause(fingerOne, Duration.ofMillis(500)))
                    .addAction(fingerOne.createPointerMove(Duration.ofMillis(900), PointerInput.Origin.viewport(), getCenterOfElement(dropMeElement.getLocation(), dropMeElement.getSize())))
                    .addAction(fingerOne.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(dragAndDropSequence));

        } finally {
            Thread.sleep(5000);
            driver.quit();
        }
    }

    private Point getCenterOfElement(Point location, Dimension size) {
        return new Point(location.getX() + size.getWidth() / 2,
                location.getY() + size.getHeight() / 2);
    }

    @AfterSuite(alwaysRun = true)
    public void stopAppiumServer() {
        appiumLocalService.stop();
    }
}
