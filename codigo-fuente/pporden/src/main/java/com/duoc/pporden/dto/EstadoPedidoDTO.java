package com.duoc.pporden.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoPedidoDTO {

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}