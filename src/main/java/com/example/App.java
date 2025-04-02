package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        Path tempUserDataDir = null;
        WebDriver driver = null;
        try {
            // Create a unique temporary directory for Chrome user data
            tempUserDataDir = Files.createTempDirectory("chrome-user-data-");
            String userDataDir = tempUserDataDir.toString();

            // Set ChromeOptions with unique user-data-dir
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--user-data-dir=" + userDataDir);

            // Initialize WebDriver
            driver = new ChromeDriver(options);
            driver.get("https://www.saucedemo.com/");
            driver.manage().window().maximize();

            // Perform login
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            // Keep browser open for 1 minute
            Thread.sleep(60000);
        } catch (IOException e) {
            System.err.println("Failed to create temp directory: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Cleanup WebDriver
            if (driver != null) {
                driver.quit();
            }
            // Cleanup temporary directory
            if (tempUserDataDir != null) {
                try {
                    Files.walk(tempUserDataDir)
                         .map(Path::toFile)
                         .forEach(file -> {
                             if (!file.delete()) {
                                 System.err.println("Failed to delete: " + file.getAbsolutePath());
                             }
                         });
                } catch (IOException e) {
                    System.err.println("Failed to delete temp directory: " + e.getMessage());
                }
            }
        }
    }
}
