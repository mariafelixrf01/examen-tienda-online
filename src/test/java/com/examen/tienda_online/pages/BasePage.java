package com.examen.tienda_online.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected WebElement esperarElemento(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    protected void hacerClic(By locator) {
        wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        ).click();
    }

    protected void escribir(By locator, String texto) {
        WebElement elemento = esperarElemento(locator);
        elemento.clear();
        elemento.sendKeys(texto);
    }

    protected String obtenerTexto(By locator) {
        return esperarElemento(locator).getText().trim();
    }

    protected boolean elementoVisible(By locator) {
        try {
            return esperarElemento(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}