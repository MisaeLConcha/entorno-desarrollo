package com.duoc.pporden.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItemCreateDTO {
    //para evitar proximo q estado sea listo antes, o "sdjhfsd" etc.
    @NotNull(message = "El idProducto es obligatorio")
    private Long idProducto;

    @NotNull(message = "La cantidad es obligatoria")
    private Integer cantidad;
}