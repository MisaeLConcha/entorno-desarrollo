package com.duoc.pporden.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PpordenDTO {

    private Long id;

    private String norden;
    private String tipo;
    private String estado;

    private LocalDateTime fechaCreacion;

    private Long eventoId;
    private Long usuarioId;

    private List<PedidoItemDTO> items;
}