package com.examen.tienda_online.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Hooks {

    // URL principal de la tienda
    private static final String URL_TIENDA =
            "https://qalab.bensg.com/store/pe/";

    // Tiempo máximo de espera del DOM
    private static final int TIEMPO_ESPERA_SEGUNDOS = 30;

    // Tiempo máximo de navegación
    private static final int TIEMPO_CARGA_SEGUNDOS = 60;

    // Driver compartido con las clases Steps
    private static WebDriver driver;

    @Before
    public void iniciarNavegador() {

        System.out.println("==================================");
        System.out.println("INICIANDO AUTOMATIZACIÓN");
        System.out.println("==================================");

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--start-maximized");

        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        driver = new ChromeDriver(options);

        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(TIEMPO_CARGA_SEGUNDOS)
        );

        driver.manage().timeouts().implicitlyWait(Duration.ZERO);

        try {

            cargarPaginaPrincipal();

            esperarPaginaDisponible();

            System.out.println("URL ACTUAL: " + driver.getCurrentUrl());

            System.out.println("TÍTULO: " + driver.getTitle());

            System.out.println("NAVEGADOR INICIADO CORRECTAMENTE");

        } catch (RuntimeException e) {

            cerrarNavegador();

            throw e;
        }
    }

    // Navegar a la página principal
    private void cargarPaginaPrincipal() {

        try {

            System.out.println("Ingresando a la tienda...");

            driver.get(URL_TIENDA);

            System.out.println("Navegación realizada.");

        } catch (TimeoutException e) {

            System.out.println(
                    "Tiempo de carga excedido. Se verificará el DOM."
            );
        }
    }

    // Esperar hasta que exista el BODY
    private void esperarPaginaDisponible() {

        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(TIEMPO_ESPERA_SEGUNDOS)
        );

        try {

            wait.until(webDriver ->
                    Boolean.TRUE.equals(
                            ((JavascriptExecutor) webDriver)
                                    .executeScript(
                                            "return document.body !== null;"
                                    )
                    )
            );

            System.out.println(
                    "El BODY de la página está disponible."
            );

        } catch (TimeoutException e) {

            System.out.println("ERROR: No se encontró el BODY.");

            System.out.println(
                    "URL actual: " + driver.getCurrentUrl()
            );

            System.out.println(
                    "Título: " + driver.getTitle()
            );

            Object estado = ((JavascriptExecutor) driver)
                    .executeScript("return document.readyState;");

            System.out.println(
                    "Estado del documento: " + estado
            );

            throw new TimeoutException(
                    "No se encontró el BODY después de "
                            + TIEMPO_ESPERA_SEGUNDOS
                            + " segundos.",
                    e
            );
        }
    }

    // Cerrar navegador después de cada escenario
    @After
    public void cerrarNavegador() {

        if (driver != null) {

            driver.quit();

            driver = null;

            System.out.println(
                    "Navegador cerrado correctamente."
            );
        }
    }

    // Obtener el driver desde las clases Steps
    public static WebDriver getDriver() {

        return driver;
    }
}
