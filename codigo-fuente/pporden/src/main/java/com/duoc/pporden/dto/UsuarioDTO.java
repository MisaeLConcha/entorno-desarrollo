package com.duoc.pporden.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long idUsuario;
    private String nombre;
    private String correo;
    private String rol;
    private String estado;

}