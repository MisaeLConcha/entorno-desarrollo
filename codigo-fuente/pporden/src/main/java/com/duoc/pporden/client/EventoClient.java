package com.duoc.pporden.client;

import com.duoc.pporden.dto.EventoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "Evento-client",
        url = "${evento.service.url}"
)
public interface EventoClient {


    @GetMapping("/api/v2/eventos/{id}")
    EventoDTO getEventoById(@PathVariable("id") Long id);
}