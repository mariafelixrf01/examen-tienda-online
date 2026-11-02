
package com.examen.tienda_online.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navegarCategoria(
            String categoria,
            String subcategoria) {

        // Esperar a que termine de cargar la página
        wait.until(webDriver ->
                ((org.openqa.selenium.JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        // Buscar y seleccionar la categoría Clothes
        By categoriaLocator = By.xpath(
                "//a[contains(normalize-space(.),'" + categoria + "')]"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(categoriaLocator)
        ).click();

        // Esperar un momento a que cargue la categoría
        wait.until(webDriver ->
                ((org.openqa.selenium.JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        // Diagnosticar los enlaces disponibles
        diagnosticarEnlaces();

        // IMPORTANTE:
        // Por ahora NO hacemos clic en Men.
        // Primero identificaremos el enlace correcto.
    }

    public void diagnosticarEnlaces() {

        System.out.println("\n========== DIAGNÓSTICO DE ENLACES ==========");
        System.out.println("URL: " + driver.getCurrentUrl());
        System.out.println("TÍTULO: " + driver.getTitle());

        List<WebElement> enlaces =
                driver.findElements(By.tagName("a"));

        for (WebElement enlace : enlaces) {

            String texto = enlace.getText().trim();
            String href = enlace.getAttribute("href");

            String informacion =
                    (texto + " " + href).toLowerCase();

            if (informacion.contains("clothes")
                    || informacion.contains("men")
                    || informacion.contains("clothing")) {

                System.out.println(
                        "Texto: [" + texto + "]"
                                + " | URL: [" + href + "]"
                                + " | Visible: " + enlace.isDisplayed()
                );
            }
        }

        System.out.println(
                "========== FIN DEL DIAGNÓSTICO =========="
        );
    }
    public boolean categoriaNoDisponible(String categoria) {

        By categoriaLocator = By.xpath(
                "//a[contains(translate(normalize-space(.), " +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', " +
                        "'abcdefghijklmnopqrstuvwxyz'), '" +
                        categoria.toLowerCase() + "')]"
        );

        boolean existe = driver.findElements(categoriaLocator)
                .stream()
                .anyMatch(WebElement::isDisplayed);

        System.out.println(
                "¿La categoría " + categoria + " está disponible?: " + existe
        );

        return !existe;
    }
}