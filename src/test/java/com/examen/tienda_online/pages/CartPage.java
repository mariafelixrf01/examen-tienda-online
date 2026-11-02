
package com.examen.tienda_online.pages;

import com.examen.tienda_online.utils.PrecioUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.math.BigDecimal;

public class CartPage extends BasePage {

    private final By botonContinuarCompra =
            By.cssSelector("#blockcart-modal a.btn-primary");

    private final By enlaceCarrito =
            By.cssSelector("a[href*='controller=cart']");

    private final By tituloCarrito =
            By.cssSelector("h1");

    private final By importeSubtotal =
            By.cssSelector("#cart-subtotal-products .value");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void finalizarCompra() {

        if (elementoVisible(botonContinuarCompra)) {
            hacerClic(botonContinuarCompra);
        } else {
            hacerClic(enlaceCarrito);
        }

        esperarElemento(tituloCarrito);
    }

    public boolean validarTituloCarrito() {

        String titulo = obtenerTexto(tituloCarrito)
                .toLowerCase()
                .trim();

        System.out.println("Título del carrito: " + titulo);

        return titulo.contains("carrito")
                || titulo.contains("cart");
    }

    public boolean validarCalculoPrecios(BigDecimal totalEsperado) {

        WebElement elemento = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        importeSubtotal
                )
        );

        String importe = elemento.getText();

        BigDecimal totalCarrito = PrecioUtils.convertir(importe);

        System.out.println("Total esperado: " + totalEsperado);
        System.out.println("Subtotal del carrito: " + totalCarrito);

        return totalEsperado.compareTo(totalCarrito) == 0;
    }
}