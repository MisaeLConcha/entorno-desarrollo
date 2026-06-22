package com.duoc.pporden.repository;

import com.duoc.pporden.model.Pporden;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PpordenRepositoryTest {

    @Autowired
    private PpordenRepository repository;

    @Test
    void guardarPedido() {

        Pporden pedido = new Pporden();
        pedido.setNorden("ORD001");

        Pporden guardado =
            repository.save(pedido);

        assertNotNull(guardado.getId());
    }

    @Test
    void buscarPorId() {

        Pporden pedido = new Pporden();
        pedido.setNorden("ORD001");

        Pporden guardado =
            repository.save(pedido);

        assertTrue(
            repository.findById(
                guardado.getId()
            ).isPresent()
        );
    }

    @Test
    void listarTodos() {

        repository.save(new Pporden());
        repository.save(new Pporden());

        List<Pporden> lista =
            repository.findAll();

        assertEquals(2, lista.size());
    }
}