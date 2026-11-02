package com.examen.tienda_online.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrecioUtils {

    public static BigDecimal convertir(String texto) {

        Pattern patron = Pattern.compile("(\\d+[.,]\\d{2})");
        Matcher matcher = patron.matcher(texto);

        if (matcher.find()) {
            String precio = matcher.group(1).replace(",", ".");
            return new BigDecimal(precio);
        }

        throw new IllegalArgumentException(
                "No se pudo convertir el precio: " + texto
        );
    }
}