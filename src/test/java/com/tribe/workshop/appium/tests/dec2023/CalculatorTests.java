package com.tribe.workshop.appium.tests.dec2023;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import com.tribe.workshop.appium.pages.CalculatorPage;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTests {

    private static final Logger logger = LogManager.getLogger(VodQATests.class);
    AppiumDriverLocalService appiumService;
    AndroidDriver driver;

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
                .setAppPackage("com.google.android.calculator")
                .setAppActivity("com.android.calculator2.Calculator")
                //.setApp(SystemHelper.getUserDirectory().concat("/apps/VodQA.apk"))
                .setAutomationName("UiAutomator2");

        // start driver
        driver = new AndroidDriver(new URI(appiumService.getUrl().toString()).toURL(), uiAutomator2Options);
    }

    @AfterTest
    public void closeAppUnderTest() throws InterruptedException {
        Thread.sleep(5000);
        driver.terminateApp("com.google.android.calculator");
        driver.quit();
    }

    @Test
    public void sumTest() {
        CalculatorPage cPage = new CalculatorPage(driver);

        String result = cPage.add(10, 10);
        System.out.println("Result Is : " + result);
        assertThat(result).isEqualTo("20");
    }
}
