package com.duoc.pporden.client;

import com.duoc.pporden.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "usuario-client",
        url = "${usuario.service.url}"
)
public interface UsuarioClient {

    @GetMapping("/api/v2/usuarios")
    UsuarioDTO getUsuarioById(@PathVariable("id") Long id);

}