
package com.examen.tienda_online.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class LoginPage extends BasePage {

    private final By campoCorreo = By.id("field-email");
    private final By campoClave = By.id("field-password");
    private final By botonLogin = By.id("submit-login");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void abrirFormularioLogin() {

        By enlaceLogin = By.cssSelector(
                "a[href*='/iniciar-sesion']"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(enlaceLogin)
        ).click();

        // Esperar a que se abra la página de acceso
        wait.until(
                ExpectedConditions.urlContains("/iniciar-sesion")
        );

        System.out.println("URL de login: " + driver.getCurrentUrl());
    }

    public void iniciarSesion(String usuario, String clave) {

        abrirFormularioLogin();

        escribir(campoCorreo, usuario);
        escribir(campoClave, clave);

        hacerClic(botonLogin);
    }

    public boolean loginExitoso() {

        return wait.until(
                ExpectedConditions.or(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.cssSelector(".account")
                        ),
                        ExpectedConditions.not(
                                ExpectedConditions.urlContains("/login")
                        )
                )
        );
    }
}
