package com.duoc.pporden.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long idProducto;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    private Boolean disponible;
    private Long idStand;
}