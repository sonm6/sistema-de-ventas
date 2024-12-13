package com.uttt.spring.boot.laboratorio.app.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Dates {
	static String obtenerFechaHoraActual() {
        String formato = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
        LocalDateTime ahora = LocalDateTime.now();
        return formateador.format(ahora);
    }
}
