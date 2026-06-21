package com.duoc.pporden.controller;

import com.duoc.pporden.dto.UsuarioDTO;
import com.duoc.pporden.dto.ProductoDTO;
import com.duoc.pporden.dto.StandDTO;
import com.duoc.pporden.dto.EventoDTO;
import com.duoc.pporden.dto.PpordenDTO;
import com.duoc.pporden.dto.PedidoItemDTO;
import com.duoc.pporden.dto.PpordenCreateDTO;
import com.duoc.pporden.dto.EstadoPedidoDTO;
import com.duoc.pporden.dto.PedidoItemCreateDTO;

import com.duoc.pporden.service.PpordenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orden")
public class PpordenController {

    @Autowired
    private PpordenService ppordenService;

    //crear pedido
    @PostMapping
    public ResponseEntity<PpordenDTO> crearPedido(
            @Valid @RequestBody PpordenCreateDTO dto) {

        PpordenDTO creado =
            ppordenService.crearPedido(dto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(creado);
    }

    //listar todo
    @GetMapping
    public ResponseEntity<List<PpordenDTO>> listarTodos() {
        return ResponseEntity.ok(
            ppordenService.listarTodosDTO()
        );
    }

    //buscar pedido x id
    @GetMapping("/{id}")
    public ResponseEntity<PpordenDTO> obtenerPedido(
        @PathVariable Long id) {

        return ResponseEntity.ok(
            ppordenService.obtenerPedidoDTO(id)
        );
    }

    //listar por evento
    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<List<PpordenDTO>>
    listarPorEvento(
        @PathVariable Long idEvento) {

        return ResponseEntity.ok(
                ppordenService.listarPedidosPorEventoDTO(idEvento)
        );
    }

    //agregar items
    @PostMapping("/{pedidoId}/items")
    public ResponseEntity<PpordenDTO> agregarItem(
        @PathVariable Long pedidoId,
        @Valid @RequestBody PedidoItemCreateDTO dto) {

        return ResponseEntity.ok(
            ppordenService.agregarItemPedido(
                pedidoId,
                dto
            )
        );
    }

    //buscar item x id
    @GetMapping("/items/{itemId}")
    public ResponseEntity<PedidoItemDTO>
        obtenerItem(
            @PathVariable Long itemId) {

        return ResponseEntity.ok(
            ppordenService.obtenerItemDTO(itemId)
        );
    }

    //modificar estado
    @PutMapping("/{id}/estado")
    public ResponseEntity<PpordenDTO> cambiarEstado(
        @PathVariable Long id,
        @Valid @RequestBody EstadoPedidoDTO dto) {

        return ResponseEntity.ok(
            ppordenService.cambiarEstado(id, dto)
        );
    }

    //eliminar items
    @DeleteMapping("/{pedidoId}/items/{itemId}")
    public ResponseEntity<PpordenDTO> eliminarItem(
        @PathVariable Long pedidoId,
        @PathVariable Long itemId) {

        return ResponseEntity.ok(
            ppordenService.eliminarItemPedido(
                pedidoId,
                itemId
            )
        );
    }

    //elimnar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPedido(
        @PathVariable Long id) {

        ppordenService.eliminarPedido(id);

        return ResponseEntity.ok(
            "Pedido eliminado correctamente"
        );
    }

    //consulta a ms usuario
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO>
    obtenerUsuario(
        @PathVariable Long id) {

        return ResponseEntity.ok(
            ppordenService.obtenerUsuario(id)
        );
    }

    //consulta a ms productos
    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO>
    obtenerProducto(
        @PathVariable Long id) {

        return ResponseEntity.ok(
            ppordenService.obtenerProducto(id)
        );
    }

    //consulta a ms eventos
    @GetMapping("/eventos/{id}")
    public ResponseEntity<EventoDTO>
    obtenerEvento(
        @PathVariable Long id) {

        return ResponseEntity.ok(
            ppordenService.obtenerEvento(id)
        );
    }

    //consulta a ms stands
    @GetMapping("/stands/{id}")
    public ResponseEntity<StandDTO>
    obtenerStand(
        @PathVariable Long id) {

        return ResponseEntity.ok(
            ppordenService.obtenerStand(id)
        );
    }

}