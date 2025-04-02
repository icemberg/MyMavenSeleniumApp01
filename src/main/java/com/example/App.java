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
        try {
            // Create a unique temporary directory for user data
            Path tempUserDataDir = Files.createTempDirectory("chrome-user-data");

            // Initialize ChromeOptions
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox"); // Bypass OS security model
            

            // Initialize WebDriver with ChromeOptions
            WebDriver driver = new ChromeDriver(options);

            // Navigate to the website and maximize the window
            driver.get("https://www.saucedemo.com/");
            driver.manage().window().maximize();

            // Perform login
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            // Wait for 1 minute before quitting
            Thread.sleep(60000); // 60,000 milliseconds = 1 minute

            // Quit the browser
            driver.quit();

            // Clean up: Delete the temporary user data directory
            Files.walk(tempUserDataDir)
                .map(Path::toFile)
                .forEach(file -> {
                    if (!file.delete()) {
                        System.err.println("Failed to delete file: " + file.getAbsolutePath());
                    }
                });
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
