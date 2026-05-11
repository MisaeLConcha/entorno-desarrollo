package com.duoc.pporden.controller;

import com.duoc.pporden.model.PedidoItem;
import com.duoc.pporden.model.Pporden;
import com.duoc.pporden.service.PpordenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orden")
public class PpordenController {

    @Autowired
    private PpordenService ppordenService;

    // Crear pedido
    @PostMapping
    public Pporden crearPedido(@RequestBody Pporden pedido) {
        return ppordenService.crearPedido(pedido);
    }

    // Agregar item a pedido
    @PostMapping("/{id}/items")
    public Pporden agregarItemPedido(@PathVariable Long id, @RequestBody PedidoItem item) {
        return ppordenService.agregarItemPedido(id, item);
    }

    // Obtener pedido por ID
    @GetMapping("/{id}")
    public Pporden obtenerPedidoPorId(@PathVariable Long id) {
        return ppordenService.obtenerPedidoPorId(id);
    }

    // Listar pedidos por evento
    @GetMapping
    public List<Pporden> listarTodos() {
        return ppordenService.listarTodos();
    }
    @GetMapping("/evento/{eventoId}")
    public List<Pporden> listarPedidosPorEvento(@PathVariable Long eventoId) {
        return ppordenService.listarPedidosPorEvento(eventoId);
    }

    // Confirmar pedido
    @PutMapping("/{id}/confirmar")
    public Pporden confirmarPedido(@PathVariable Long id) {
        return ppordenService.confirmarPedido(id);
    }

    // Cancelar pedido
    @PutMapping("/{id}/cancelar")
    public Pporden cancelarPedido(@PathVariable Long id) {
        return ppordenService.cancelarPedido(id);
    }

    // Eliminar item del pedido
    @DeleteMapping("/{pedidoId}/items/{itemId}")
    public Pporden eliminarItemPedido(@PathVariable Long pedidoId, @PathVariable Long itemId) {
        return ppordenService.eliminarItemPedido(pedidoId, itemId);
    }

    // eliminar el pedido 
    @DeleteMapping("/{id}")
    public String eliminarPedido(@PathVariable Long id) {
        ppordenService.eliminarPedido(id);
        return "Pedido eliminado correctamente";
}
}