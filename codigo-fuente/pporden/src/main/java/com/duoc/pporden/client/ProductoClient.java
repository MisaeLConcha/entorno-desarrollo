package com.duoc.pporden.client;

import com.duoc.pporden.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "producto-client",
        url = "${producto.service.url}"
)
public interface ProductoClient {

    @GetMapping("/api/v3/productos/{id}")
    ProductoDTO getProductoById(@PathVariable("id") Long id);
}