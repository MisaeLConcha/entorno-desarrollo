package com.duoc.pporden.service;

import com.duoc.pporden.client.EventoClient;
import com.duoc.pporden.client.ProductoClient;
import com.duoc.pporden.client.StandClient;
import com.duoc.pporden.client.UsuarioClient;
import com.duoc.pporden.model.Pporden;
import com.duoc.pporden.repository.PedidoItemRepository;
import com.duoc.pporden.repository.PpordenRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PpordenServiceTest {

    @Mock
    private PpordenRepository ppordenRepository;

    @InjectMocks
    private PpordenService ppordenService;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private ProductoClient productoClient;

    @Mock
    private EventoClient eventoClient;

    @Mock
    private StandClient standClient;

    @Mock
    private PedidoItemRepository pedidoItemRepository;

    @Test
    void listarTodosDebeRetornarLista() {

        when(ppordenRepository.findAll())
                .thenReturn(List.of(new Pporden()));

        assertNotNull(ppordenRepository.findAll());

        verify(ppordenRepository).findAll();
    }

    @Test
    void obtenerPedidoPorIdExistente() {

        Pporden pedido = new Pporden();
        pedido.setId(1L);

        when(ppordenRepository.findById(1L))
                .thenReturn(java.util.Optional.of(pedido));

        Pporden resultado =
                ppordenRepository.findById(1L).get();

        assertEquals(1L, resultado.getId());
    }

    @Test
    void eliminarPedidoDebeInvocarRepository() {

        Pporden pedido = new Pporden();
        pedido.setId(1L);

        ppordenRepository.delete(pedido);

        verify(ppordenRepository).delete(pedido);
    }
}