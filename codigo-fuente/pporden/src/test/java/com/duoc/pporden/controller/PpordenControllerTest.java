package com.duoc.pporden.controller;

import com.duoc.pporden.dto.PpordenDTO;
import com.duoc.pporden.exception.GlobalExceptionHandler;
import com.duoc.pporden.exception.ResourceNotFoundException;
import com.duoc.pporden.dto.PpordenCreateDTO;
import com.duoc.pporden.service.PpordenService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PpordenControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PpordenService service;

    @InjectMocks
    private PpordenController controller;

    private final ObjectMapper mapper =
            new ObjectMapper();

    @BeforeEach
    void setUp() {

    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders
        .standaloneSetup(controller)
        .setControllerAdvice(new GlobalExceptionHandler())
        .build();
    }

    @Test
    void listarTodosDebeRetornar200() throws Exception {

        when(service.listarTodosDTO())
            .thenReturn(List.of());

        mockMvc.perform(get("/api/v3/orden"))
            .andExpect(status().isOk());
    }

    @Test
    void crearPedidoDebeRetornar201() throws Exception {

        PpordenDTO dto = new PpordenDTO();
        dto.setId(1L);

        when(service.crearPedido(any()))
            .thenReturn(dto);

        PpordenCreateDTO request =
            new PpordenCreateDTO(
                "ORD-001",
                "COMPRA",
                1L,
                1L
                );

        mockMvc.perform(post("/api/v3/orden")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void pedidoNoExisteDebeRetornar404() throws Exception {

        when(service.obtenerPedidoDTO(999L))
            .thenThrow(
                new ResourceNotFoundException("Pedido no encontrado")
            );

        mockMvc.perform(get("/api/v3/orden/999"))
            .andExpect(status().isNotFound());
    }
}