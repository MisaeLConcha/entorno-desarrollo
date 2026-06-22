package com.duoc.pporden.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class PpordenTest {

    @Test
    void constructorVacio() {

        Pporden pedido = new Pporden();
        assertNotNull(pedido);
    }

    @Test
    void settersYGetters() {

        Pporden pedido = new Pporden();

        pedido.setId(1L);
        pedido.setNorden("ORD001");
        pedido.setTipo("EVENTOSAC");
        pedido.setEstado("CREADO");

        assertEquals(1L, pedido.getId());
        assertEquals("ORD001", pedido.getNorden());
        assertEquals("EVENTOSAC", pedido.getTipo());
        assertEquals("CREADO", pedido.getEstado());
    }

    @Test
    void fechaCreacionCorrecta() {

        LocalDateTime ahora = LocalDateTime.now();
        Pporden pedido = new Pporden();
        pedido.setFechaCreacion(ahora);
        
        assertEquals(
            ahora,
            pedido.getFechaCreacion()
        );
    }
}