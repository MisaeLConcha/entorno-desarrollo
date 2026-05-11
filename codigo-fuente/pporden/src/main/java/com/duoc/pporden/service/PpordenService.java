package com.duoc.pporden.service;

import com.duoc.pporden.model.PedidoItem;
import com.duoc.pporden.model.Pporden;
import com.duoc.pporden.repository.PedidoItemRepository;
import com.duoc.pporden.repository.PpordenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PpordenService {

    @Autowired
    private PpordenRepository ppordenRepository;

    @Autowired
    private PedidoItemRepository pedidoItemRepository;

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

        // RestTemplate / WebClient → consultar microservicio de eventos para cdo tengamos cx con evento
    }

    //eliminar pedidini
    public void eliminarPedido(Long id) {

    Pporden pedido = obtenerPedidoPorId(id);

    if (pedido.getEstado().equals("CONFIRMADO")) {
        throw new RuntimeException("No se puede eliminar un pedido confirmado");
    }

    // 🔥 eliminar items desde la BD
    pedido.getItems().forEach(pedidoItemRepository::delete);

    // limpiar lista (opcional pero ordenado)
    pedido.getItems().clear();

    // ahora sí eliminar pedido
    ppordenRepository.delete(pedido);
}
}