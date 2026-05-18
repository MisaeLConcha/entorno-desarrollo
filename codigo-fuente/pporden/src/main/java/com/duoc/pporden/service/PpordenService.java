package com.duoc.pporden.service;

import com.duoc.pporden.model.PedidoItem;
import com.duoc.pporden.model.Pporden;
import com.duoc.pporden.repository.PedidoItemRepository;
import com.duoc.pporden.repository.PpordenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.pporden.dto.PpordenDTO;
import com.duoc.pporden.dto.PedidoItemDTO;
import com.duoc.pporden.dto.PpordenCreateDTO;

import com.duoc.pporden.client.UsuarioClient;
import com.duoc.pporden.client.ProductoClient;
import com.duoc.pporden.client.StandClient;
import com.duoc.pporden.client.EventoClient;
import com.duoc.pporden.dto.UsuarioDTO;
import com.duoc.pporden.dto.ProductoDTO;
import com.duoc.pporden.dto.StandDTO;
import com.duoc.pporden.dto.EventoDTO;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class PpordenService {

    @Autowired
    private PpordenRepository ppordenRepository;

    @Autowired
    private PedidoItemRepository pedidoItemRepository;
    @Autowired

    private UsuarioClient usuarioClient;
    @Autowired

    private ProductoClient productoClient;
    @Autowired

    private StandClient standClient;
    @Autowired
    
    private EventoClient eventoClient;
    public List<Pporden> listarTodos() {
    return ppordenRepository.findAll();
    }

    // crearPedido()
    public Pporden crearPedido(Pporden pedido) {

        // Validar evento activo (simulado por ahora)
        validarEventoActivo(pedido.getEventoId());

        pedido.setEstado("CREADO");
        pedido.setFechaCreacion(LocalDateTime.now());

        return ppordenRepository.save(pedido);
    }

    // agregarItemPedido()
    public Pporden agregarItemPedido(Long pedidoId, PedidoItem item) {

        Pporden pedido = obtenerPedidoPorId(pedidoId);

        if (!pedido.getEstado().equals("CREADO")) {
            throw new RuntimeException("No se pueden agregar items a un pedido que no está en estado CREADO");
        }

        item.setPedido(pedido);
        pedidoItemRepository.save(item);

        pedido.getItems().add(item);

        return ppordenRepository.save(pedido);
    }

    // eliminarItemPedido()
    public Pporden eliminarItemPedido(Long pedidoId, Long itemId) {

    Pporden pedido = obtenerPedidoPorId(pedidoId);

    PedidoItem item = pedidoItemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Item no encontrado"));

    pedido.getItems().remove(item);
    pedidoItemRepository.delete(item);

    return ppordenRepository.save(pedido);
}

    // confirmarPedido()
    public Pporden confirmarPedido(Long id) {

        Pporden pedido = obtenerPedidoPorId(id);

        if (pedido.getItems().isEmpty()) {
            throw new RuntimeException("No se puede confirmar un pedido sin items");
        }

        if (pedido.getEstado().equals("CONFIRMADO")) {
            throw new RuntimeException("El pedido ya está confirmado");
        }

        if (pedido.getEstado().equals("CANCELADO")) {
            throw new RuntimeException("No se puede confirmar un pedido cancelado");
        }

        pedido.setEstado("CONFIRMADO");

        return ppordenRepository.save(pedido);
    }

    // cancelarPedido()
    public Pporden cancelarPedido(Long id) {

        Pporden pedido = obtenerPedidoPorId(id);

        if (pedido.getEstado().equals("CANCELADO")) {
            throw new RuntimeException("El pedido ya está cancelado");
        }

        pedido.setEstado("CANCELADO");

        return ppordenRepository.save(pedido);
    }

    // obtenerPedidoPorId()
    public Pporden obtenerPedidoPorId(Long id) {

        return ppordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    // istarPedidosPorEvento()
    public List<Pporden> listarPedidosPorEvento(Long eventoId) {
        return ppordenRepository.findByEventoId(eventoId);
    }

    // validarEventoActivo()
    public void validarEventoActivo(Long eventoId) {
        if (eventoId == null || eventoId <= 0) { //mientras no tenemos eventos, es simulacion
            throw new RuntimeException("Evento no válido o inactivo");
        }

    }

    //eliminar pedidini
    public void eliminarPedido(Long id) {
    Pporden pedido = obtenerPedidoPorId(id);

    if (pedido.getEstado().equals("CONFIRMADO")) {
        throw new RuntimeException("No se puede eliminar un pedido confirmado");
    }

    // eliminar items desde la BD
    pedido.getItems().forEach(pedidoItemRepository::delete);
    // limpiar lista (opcional pero ordenado)
    pedido.getItems().clear();
    // ahora sí eliminar pedido
    ppordenRepository.delete(pedido);
    }

    //para pedir info al dto
    public PpordenDTO crearPedido(PpordenCreateDTO dto) {
    Pporden pedido = new Pporden();

    pedido.setNorden(dto.getNorden());
    pedido.setTipo(dto.getTipo());
    pedido.setEventoId(dto.getEventoId());
    pedido.setUsuarioId(dto.getUsuarioId());

    Pporden guardado = crearPedido(pedido);
    return convertirADTO(guardado);
    }

    public PpordenDTO obtenerPedidoDTO(Long id) {
        Pporden pedido = obtenerPedidoPorId(id);
        return convertirADTO(pedido);
    }

    private PpordenDTO convertirADTO(Pporden pedido) {
    List<PedidoItemDTO> itemsDTO = pedido.getItems()
        .stream()
        .map(item -> new PedidoItemDTO(
                item.getId(),
                item.getNombreProducto(),
                item.getCantidad(),
                item.getPrecio()
        ))
        .toList();

    return new PpordenDTO(
            pedido.getId(),
            pedido.getNorden(),
            pedido.getTipo(),
            pedido.getEstado(),
            pedido.getFechaCreacion(),
            pedido.getEventoId(),
            pedido.getUsuarioId(),
            itemsDTO
        );
    }
    
    //lista dto completo
    public List<PpordenDTO> listarTodosDTO() {
        return ppordenRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .toList();
    }

    //listar dto x evento
    public List<PpordenDTO> listarPedidosPorEventoDTO(Long eventoId) {
        return ppordenRepository.findByEventoId(eventoId)
            .stream()
            .map(this::convertirADTO)
            .toList();
    }

    public UsuarioDTO obtenerUsuario(Long id) {
        return usuarioClient.getUsuarioById(id);
    }

    public ProductoDTO obtenerProducto(Long id) {
        return productoClient.getProductoById(id);
    }

    public StandDTO obtenerStand(Long id) {
        return standClient.getStandById(id);
    }

    public EventoDTO obtenerEvento(Long id) {
        return eventoClient.getEventoById(id);
    }

}