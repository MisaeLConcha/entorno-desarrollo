package com.duoc.pporden.controller;

import com.duoc.pporden.model.PedidoItem;
import com.duoc.pporden.model.Pporden;
import com.duoc.pporden.service.PpordenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.duoc.pporden.dto.UsuarioDTO;
import com.duoc.pporden.dto.ProductoDTO;
import com.duoc.pporden.dto.StandDTO;
import com.duoc.pporden.dto.EventoDTO;

import com.duoc.pporden.dto.PpordenDTO;
import com.duoc.pporden.dto.PedidoItemDTO;
import com.duoc.pporden.dto.PpordenCreateDTO;

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
    //POST con dto
    @PostMapping
    public ResponseEntity<PpordenDTO> crearPedido(
        @Valid @RequestBody PpordenCreateDTO dto) {

    PpordenDTO creado = ppordenService.crearPedido(dto);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(creado);
    }

    //GET con dto
    @GetMapping("/{id}")
    public ResponseEntity<PpordenDTO> obtenerPedido(@PathVariable Long id) {

    return ResponseEntity.ok(
            ppordenService.obtenerPedidoDTO(id)
    );
    }

    //
    @GetMapping("/api/v2/usuarios")
    public UsuarioDTO probarUsuario(@PathVariable Long id) {
        return ppordenService.obtenerUsuario(id);
    }

    @GetMapping("/api/v2/productos")
        public ProductoDTO probarProducto(@PathVariable Long id) {
    return ppordenService.obtenerProducto(id);
    }

    @GetMapping("/api/v2/eventos")
        public EventoDTO probarEvento(@PathVariable Long id) {
    return ppordenService.obtenerEvento(id);
    }





}