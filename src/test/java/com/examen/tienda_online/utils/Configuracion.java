package com.examen.tienda_online.utils;

import java.io.InputStream;
import java.util.Properties;

public class Configuracion {

    private static final Properties propiedades = new Properties();

    static {
        try (InputStream archivo =
                     Configuracion.class.getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (archivo == null) {
                throw new RuntimeException(
                        "No se encontró config.properties"
                );
            }

            propiedades.load(archivo);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Error al cargar la configuración", e
            );
        }
    }

    public static String obtener(String clave) {
        String valor = System.getenv(clave.toUpperCase());

        if (valor == null || valor.isBlank()) {
            valor = propiedades.getProperty(clave);
        }

        if (valor == null || valor.isBlank()) {
            throw new RuntimeException(
                    "Falta configurar la propiedad: " + clave
            );
        }

        return valor.trim();
    }
}
