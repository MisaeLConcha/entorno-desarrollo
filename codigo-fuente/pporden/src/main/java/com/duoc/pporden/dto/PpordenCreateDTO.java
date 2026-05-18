package com.duoc.pporden.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PpordenCreateDTO {

    @NotBlank(message = "El número de orden es obligatorio")
    private String norden;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @NotNull(message = "El eventoId es obligatorio")
    private Long eventoId;

    @NotNull(message = "El usuarioId es obligatorio")
    private Long usuarioId;
}