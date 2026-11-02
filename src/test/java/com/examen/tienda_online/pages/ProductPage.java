package com.examen.tienda_online.pages;

import com.examen.tienda_online.utils.PrecioUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductPage extends BasePage {

    private final By productos =
            By.cssSelector("article.product-miniature");

    private final By precioProducto =
            By.cssSelector(".current-price");

    private final By campoCantidad =
            By.id("quantity_wanted");

    private final By botonAgregar =
            By.cssSelector("button.add-to-cart");

    private final By popupCarrito =
            By.id("blockcart-modal");

    private final By nombreProductoPopup =
            By.cssSelector("#blockcart-modal .product-name");

    private final By precioTotalPopup =
            By.cssSelector("#blockcart-modal .cart-content .value");

    private String nombreProducto;
    private BigDecimal precioUnitario;
    private BigDecimal totalEsperado;

    public ProductPage(WebDriver driver) {
        super(driver);
    }



    public void seleccionarPrimerProducto() {

        List<WebElement> lista = wait.until(
                org.openqa.selenium.support.ui.ExpectedConditions
                        .visibilityOfAllElementsLocatedBy(productos)
        );

        if (lista.isEmpty()) {
            throw new AssertionError(
                    "No se encontraron productos en la categoría"
            );
        }

        lista.get(0).click();

        // Esperar a que cargue la página del producto
        wait.until(webDriver ->
                ((org.openqa.selenium.JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        // Obtener nombre del producto
        nombreProducto = driver.findElement(
                By.cssSelector("h1")
        ).getText().trim();

        // Obtener precio unitario
        String precio = obtenerTexto(precioProducto);
        precioUnitario = PrecioUtils.convertir(precio);

        System.out.println("Producto seleccionado: " + nombreProducto);
        System.out.println("Precio unitario: " + precioUnitario);
    }

    public void agregarCantidad(int cantidad) {

        WebElement campo = wait.until(
                ExpectedConditions.elementToBeClickable(campoCantidad)
        );

        campo.click();
        campo.sendKeys(Keys.CONTROL + "a");
        campo.sendKeys(Keys.BACK_SPACE);
        campo.sendKeys(String.valueOf(cantidad));

        String cantidadIngresada = campo.getAttribute("value");

        System.out.println("Cantidad solicitada: " + cantidad);
        System.out.println("Cantidad ingresada: " + cantidadIngresada);

        if (!String.valueOf(cantidad).equals(cantidadIngresada)) {
            throw new AssertionError(
                    "La cantidad ingresada no coincide con la solicitada"
            );
        }

        totalEsperado = precioUnitario.multiply(
                BigDecimal.valueOf(cantidad)
        );

        System.out.println("Total esperado: " + totalEsperado);

        hacerClic(botonAgregar);

        esperarElemento(popupCarrito);
    }

    public boolean validarConfirmacionPopup() {

        WebElement popup = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("blockcart-modal")
                )
        );

        String textoPopup = popup.getText();

        System.out.println("===== TEXTO REAL DEL POPUP =====");
        System.out.println(textoPopup);
        System.out.println("===== FIN DEL POPUP =====");

        return textoPopup.toLowerCase()
                .contains(nombreProducto.toLowerCase());
    }

    public boolean validarTotalPopup() {

        WebElement popup = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("blockcart-modal")
                )
        );

        String textoPopup = popup.getText();

        System.out.println("DETALLE DEL POPUP:");
        System.out.println(textoPopup);

        Pattern patron = Pattern.compile(
                "(?i)Subtotal:\\s*S/\\s*(\\d+[.,]\\d{2})"
        );

        Matcher matcher = patron.matcher(textoPopup);

        if (!matcher.find()) {
            throw new AssertionError(
                    "No se encontró el subtotal en el popup"
            );
        }

        BigDecimal totalReal = PrecioUtils.convertir(matcher.group(1));

        System.out.println("Total esperado: " + totalEsperado);
        System.out.println("Total del popup: " + totalReal);

        return totalEsperado.compareTo(totalReal) == 0;
    }

    public BigDecimal getTotalEsperado() {
        return totalEsperado;
    }

    private BigDecimal convertirPrecio(String texto) {

        String limpio = texto
                .replaceAll("[^0-9,.]", "")
                .replace(",", ".");

        return new BigDecimal(limpio);
    }
}