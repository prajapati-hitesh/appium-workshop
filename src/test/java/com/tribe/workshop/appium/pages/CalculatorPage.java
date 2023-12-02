package com.tribe.workshop.appium.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CalculatorPage {
    AppiumDriver driver;
    WebDriverWait wait;
    By clearButton = AppiumBy.id("com.google.android.calculator:id/clr");
    By addButton = AppiumBy.id("com.google.android.calculator:id/op_add");
    By equalsButton = AppiumBy.id("com.google.android.calculator:id/eq");
    By resultLabel = AppiumBy.id("com.google.android.calculator:id/result_final");

    public CalculatorPage(AppiumDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public String add(int a, int b) {
        char[] firstNumberChars = String.valueOf(a).toCharArray();
        char[] secondNumberChars = String.valueOf(b).toCharArray();

        for(char each : firstNumberChars) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(String.valueOf(each)))).click();
        }

        // click on plus
        wait.until(ExpectedConditions.visibilityOfElementLocated(addButton)).click();
        for(char each : secondNumberChars) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(String.valueOf(each)))).click();
        }

        // equals
        wait.until(ExpectedConditions.visibilityOfElementLocated(equalsButton)).click();

        return wait.until(ExpectedConditions.visibilityOfElementLocated(resultLabel)).getText();
    }

    // create subtract function
    // create divide function
    // create multiply function
}
