package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class App {
    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox"); // Necessary for Linux/WSL2
        
        WebDriver driver = null;
        try {
            driver = new ChromeDriver(options);
            driver.get("https://www.saucedemo.com/");
            driver.manage().window().maximize();
            
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            
            Thread.sleep(60000); // Keep browser open for 1 minute
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
