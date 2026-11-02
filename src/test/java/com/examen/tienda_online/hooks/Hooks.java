package com.examen.tienda_online.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Hooks {

    private static WebDriver driver;

    @Before
    public void iniciarNavegador() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        // Esperar solamente a que el DOM esté disponible
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        driver = new ChromeDriver(options);

        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(60));

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(0));

        try {
            driver.get("https://qalab.bensg.com/store");

        } catch (TimeoutException e) {
            System.out.println(
                    "La página tardó en cargar completamente. Verificando el DOM..."
            );
        }

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(30)
        );

        wait.until(
                ExpectedConditions.presenceOfElementLocated(By.tagName("body"))
        );

        System.out.println("URL ACTUAL: " + driver.getCurrentUrl());
        System.out.println("TITULO: " + driver.getTitle());
    }

    @After
    public void cerrarNavegador() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }
}