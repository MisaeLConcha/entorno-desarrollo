package com.duoc.pporden.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoItemDTO {

    private Long id;

    private String nombreProducto;

    private Integer cantidad;

    private Double precio;
}