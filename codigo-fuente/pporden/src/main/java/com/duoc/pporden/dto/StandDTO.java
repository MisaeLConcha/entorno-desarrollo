package com.duoc.pporden.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandDTO {
    private Long idStand;
    private String nombre;
    private String tipoComida;
    private String descripcion;
    private String estado;
    private Long idEvento;
}