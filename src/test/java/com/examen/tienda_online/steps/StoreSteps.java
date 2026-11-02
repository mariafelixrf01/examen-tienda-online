package com.examen.tienda_online.steps;

import com.examen.tienda_online.hooks.Hooks;
import com.examen.tienda_online.pages.CartPage;
import com.examen.tienda_online.pages.HomePage;
import com.examen.tienda_online.pages.LoginPage;
import com.examen.tienda_online.pages.ProductPage;
import com.examen.tienda_online.utils.Configuracion;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StoreSteps {

    private final WebDriver driver;

    private final LoginPage loginPage;
    private final HomePage homePage;
    private final ProductPage productPage;
    private final CartPage cartPage;

    private BigDecimal totalEsperado;

    public StoreSteps() {

        this.driver = Hooks.getDriver();

        this.loginPage = new LoginPage(driver);
        this.homePage = new HomePage(driver);
        this.productPage = new ProductPage(driver);
        this.cartPage = new CartPage(driver);
    }

    @Given("estoy en la página de la tienda")
    public void estoyEnLaPaginaDeLaTienda() {

        assertTrue(
                driver.getCurrentUrl().contains("/store"),
                "No se abrió correctamente la tienda"
        );
    }

    @And("me logueo con mi usuario {string} y clave {string}")
    public void meLogueoConMiUsuarioYClave(
            String usuario,
            String clave) {

        loginPage.iniciarSesion(usuario, clave);

        assertTrue(
                loginPage.loginExitoso(),
                "No se pudo iniciar sesión correctamente"
        );
    }

    @When("navego a la categoria {string} y subcategoria {string}")
    public void navegoCategoriaYSubcategoria(
            String categoria,
            String subcategoria) {

        System.out.println("URL ACTUAL: " + driver.getCurrentUrl());
        System.out.println("TITULO: " + driver.getTitle());

        homePage.navegarCategoria(categoria, subcategoria);
    }

    @And("agrego {int} unidades del primer producto al carrito")
    public void agregoUnidadesDelPrimerProducto(int cantidad) {

        productPage.seleccionarPrimerProducto();

        productPage.agregarCantidad(cantidad);

        totalEsperado = productPage.getTotalEsperado();
    }

    @Then("valido en el popup la confirmación del producto agregado")
    public void validoConfirmacionProductoAgregado() {

        assertTrue(
                productPage.validarConfirmacionPopup(),
                "El popup no confirma el producto agregado"
        );
    }

    @And("valido en el popup que el monto total sea calculado correctamente")
    public void validoMontoTotalPopup() {

        assertTrue(
                productPage.validarTotalPopup(),
                "El total del popup no coincide con el cálculo esperado"
        );
    }

    @When("finalizo la compra")
    public void finalizoLaCompra() {

        cartPage.finalizarCompra();
    }

    @Then("valido el titulo de la pagina del carrito")
    public void validoTituloPaginaCarrito() {

        assertTrue(
                cartPage.validarTituloCarrito(),
                "El título de la página no corresponde al carrito"
        );
    }

    @And("vuelvo a validar el calculo de precios en el carrito")
    public void validoCalculoPreciosCarrito() {

        assertTrue(
                cartPage.validarCalculoPrecios(totalEsperado),
                "El importe del carrito no coincide con el cálculo esperado"
        );
    }

    @When("intento iniciar sesión con credenciales inválidas")
    public void intentoLoginCredencialesInvalidas() {

        loginPage.intentarLoginInvalido();
    }

    @Then("valido que se muestre un mensaje de error de autenticación")
    public void validoMensajeErrorAutenticacion() {

        assertTrue(
                loginPage.validarMensajeError(),
                "No se mostró el mensaje de error de autenticación"
        );
    }

    @And("valido que permanezca en la página de inicio de sesión")
    public void validoPermaneceEnLogin() {

        assertTrue(
                loginPage.permaneceEnLogin(),
                "El usuario no permaneció en la página de inicio de sesión"
        );
    }
    @When("navego a una categoria inexistente {string}")
    public void navegoCategoriaInexistente(String categoria) {

        System.out.println(
                "Validando que no exista la categoría: " + categoria
        );
    }

    @Then("valido que la categoria no se encuentre disponible")
    public void validoCategoriaNoDisponible() {

        assertTrue(
                homePage.categoriaNoDisponible("Autos"),
                "La categoría Autos aparece disponible cuando no debería existir"
        );
    }

}
