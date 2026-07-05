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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// Imports de Swagger (agregar estos)
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import java.util.List;

@Tag(name = "Orden", description = "Operaciones de gestión de Ordenes/Pedidos")
@RestController
@RequestMapping("/api/v3/orden") //versionamiento
public class PpordenController {

    @Autowired
    private PpordenService ppordenService;

    @Operation(summary = "Registrar nueva Orden")
    @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente")
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

    @Operation(summary = "Listar todas las ordenes existentes",
               description = "Retorna la lista completa de Ordenes registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    //listar todo
    @GetMapping
    public ResponseEntity<List<PpordenDTO>> listarTodos() {
        return ResponseEntity.ok(
            ppordenService.listarTodosDTO()
        );
    }

    @Operation(summary = "Buscar Orden por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido encontrada"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrada")
    })
    //buscar pedido x id
    @GetMapping("/{id}")
    public ResponseEntity<PpordenDTO> obtenerPedido(
        @Parameter(description = "ID único de el Pedido", required = true)
        @PathVariable Long id) {

        return ResponseEntity.ok(
            ppordenService.obtenerPedidoDTO(id)
        );
    }

    @Operation(summary = "Buscar evento por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Evento encontrado"),
        @ApiResponse(responseCode = "404", description = "Evento no encontrado")
    })
    //listar por evento
    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<List<PpordenDTO>>
    listarPorEvento(
        @Parameter(description = "ID único del evento de el Pedido", required = true)
        @PathVariable Long idEvento) {

        return ResponseEntity.ok(
                ppordenService.listarPedidosPorEventoDTO(idEvento)
        );
    }

    @Operation(summary = "Registrar items desde Productos")
    @ApiResponse(responseCode = "201", description = "Items creados exitosamente")
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

    @Operation(summary = "Buscar items por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "items encontrados"),
        @ApiResponse(responseCode = "404", description = "items no encontrados")
    })
    //buscar item x id
    @GetMapping("/items/{itemId}")
    public ResponseEntity<PedidoItemDTO>
        obtenerItem(
            @Parameter(description = "ID único de los items", required = true)
            @PathVariable Long itemId) {

        return ResponseEntity.ok(
            ppordenService.obtenerItemDTO(itemId)
        );
    }

    @Operation(summary = "Actualizar Pedido existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    //modificar estado
    @PutMapping("/{id}/estado")
    public ResponseEntity<PpordenDTO> cambiarEstado(
        @Parameter(description = "ID de la Orden a actualizar")
        @PathVariable Long id,
        @Valid @RequestBody EstadoPedidoDTO dto) {

        return ResponseEntity.ok(
            ppordenService.cambiarEstado(id, dto)
        );
    }

    @Operation(summary = "Eliminar items de la Orden")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    //eliminar items
    @DeleteMapping("/{pedidoId}/items/{itemId}")
    public ResponseEntity<PpordenDTO> eliminarItem(
        @Parameter(description = "ID del pedido e items a eliminar")
        @PathVariable Long pedidoId,
        @PathVariable Long itemId) {

        return ResponseEntity.ok(
            ppordenService.eliminarItemPedido(
                pedidoId,
                itemId
            )
        );
    }

    @Operation(summary = "Eliminar Pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    //elimnar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPedido(
        @Parameter(description = "ID de el Pedido a eliminar")
        @PathVariable Long id) {

        ppordenService.eliminarPedido(id);

        return ResponseEntity.ok(
            "Pedido eliminado correctamente"
        );
    }

    @Operation(summary = "Consultar en microservicio Usuario mediante ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    //consulta a ms usuario
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO>
    obtenerUsuario(
        @Parameter(description = "ID único de el Usuario", required = true)
        @PathVariable Long id) {

        return ResponseEntity.ok(
            ppordenService.obtenerUsuario(id)
        );
    }

    @Operation(summary = "Consultar en microservicio Productos mediante ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Productos encontrados"),
        @ApiResponse(responseCode = "404", description = "Productos no encontrados")
    })
    //consulta a ms productos
    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO>
    obtenerProducto(
        @Parameter(description = "ID único de los Productos", required = true)
        @PathVariable Long id) {

        return ResponseEntity.ok(
            ppordenService.obtenerProducto(id)
        );
    }

    @Operation(summary = "Consultar en microservicio Eventos mediante ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eventos encontrado"),
        @ApiResponse(responseCode = "404", description = "Evento no encontrado")
    })
    //consulta a ms eventos
    @GetMapping("/eventos/{id}")
    public ResponseEntity<EventoDTO>
    obtenerEvento(
        @Parameter(description = "ID único de el Evento", required = true)
        @PathVariable Long id) {

        return ResponseEntity.ok(
            ppordenService.obtenerEvento(id)
        );
    }

    @Operation(summary = "Consultar en microservicio Stands mediante ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Stand encontrado"),
        @ApiResponse(responseCode = "404", description = "Stands no encontrado")
    })
    //consulta a ms stands
    @GetMapping("/stands/{id}")
    public ResponseEntity<StandDTO>
    obtenerStand(
        @Parameter(description = "ID único de el Stand", required = true)
        @PathVariable Long id) {

        return ResponseEntity.ok(
            ppordenService.obtenerStand(id)
        );
    }

}