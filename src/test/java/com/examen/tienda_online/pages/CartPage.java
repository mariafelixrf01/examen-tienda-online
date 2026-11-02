package com.examen.tienda_online.pages;

import com.examen.tienda_online.utils.PrecioUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;

public class CartPage extends BasePage {

    private static final By BOTON_CONTINUAR_COMPRA =
            By.cssSelector("#blockcart-modal a.btn-primary");

    private static final By ENLACE_CARRITO =
            By.cssSelector("a[href*='controller=cart']");

    private static final By TITULO_CARRITO =
            By.tagName("h1");

    private static final By IMPORTE_SUBTOTAL =
            By.cssSelector("#cart-subtotal-products .value");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void finalizarCompra() {

        if (elementoVisible(BOTON_CONTINUAR_COMPRA)) {
            hacerClic(BOTON_CONTINUAR_COMPRA);
        } else {
            hacerClic(ENLACE_CARRITO);
        }

        esperarElemento(TITULO_CARRITO);
    }

    public boolean validarTituloCarrito() {

        String titulo = obtenerTexto(TITULO_CARRITO)
                .toLowerCase();

        return titulo.contains("carrito")
                || titulo.contains("cart");
    }

    public boolean validarCalculoPrecios(BigDecimal totalEsperado) {

        String importe = obtenerTexto(IMPORTE_SUBTOTAL);

        BigDecimal totalCarrito =
                PrecioUtils.convertir(importe);

        return totalCarrito.compareTo(totalEsperado) == 0;
    }
}
