package com.duoc.pporden.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {
    private Long idEvento;
    private String nombre;
    private String organizacionResponsable;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String ubicacion;
    private String estado;
    private Long idOrganizador;
} 