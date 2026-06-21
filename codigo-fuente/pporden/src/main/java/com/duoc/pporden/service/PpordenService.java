package com.duoc.pporden.service;

import com.duoc.pporden.client.UsuarioClient;
import com.duoc.pporden.client.ProductoClient;
import com.duoc.pporden.client.StandClient;
import com.duoc.pporden.client.EventoClient;

import com.duoc.pporden.dto.PpordenDTO;
import com.duoc.pporden.dto.PedidoItemDTO;
import com.duoc.pporden.dto.PpordenCreateDTO;
import com.duoc.pporden.dto.UsuarioDTO;
import com.duoc.pporden.dto.ProductoDTO;
import com.duoc.pporden.dto.StandDTO;
import com.duoc.pporden.dto.EventoDTO;
import com.duoc.pporden.dto.EstadoPedidoDTO;
import com.duoc.pporden.dto.PedidoItemCreateDTO;

import com.duoc.pporden.model.PedidoItem;
import com.duoc.pporden.model.Pporden;

import com.duoc.pporden.repository.PedidoItemRepository;
import com.duoc.pporden.repository.PpordenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.duoc.pporden.exception.ResourceNotFoundException;
import com.duoc.pporden.exception.BadRequestException;
import com.duoc.pporden.exception.GlobalExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    //incluir logger
    private static final Logger log =
        LoggerFactory.getLogger(PpordenService.class);
    
    //listar todo()
    public List<PpordenDTO> listarTodosDTO() {
        log.info("Consultando listado completo de pedidos");
        return ppordenRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .toList();
    }
    // crearPedido()
    public PpordenDTO crearPedido(PpordenCreateDTO dto) {
        log.info(
            "Creando pedido. Numero={}, Evento={}, Usuario={}",
            dto.getNorden(),
            dto.getIdEvento(),
            dto.getIdUsuario()
        );
        validarEventoActivo(dto.getIdEvento());
        UsuarioDTO usuario =
            usuarioClient.getUsuarioById(dto.getIdUsuario());

        if (usuario == null) {
            log.warn(
                "Intento de crear pedido con usuario inexistente: {}",
                dto.getIdUsuario()
            );
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        Pporden pedido = new Pporden();
        pedido.setNorden(dto.getNorden());
        pedido.setTipo(dto.getTipo());
        pedido.setEstado("CREADO");
        pedido.setFechaCreacion(LocalDateTime.now());
        pedido.setIdEvento(dto.getIdEvento());
        pedido.setIdUsuario(dto.getIdUsuario());

        Pporden guardado =
            ppordenRepository.save(pedido);
            log.info(
                "Pedido creado correctamente con id {}",
                guardado.getId()
            );
            return convertirADTO(guardado);
    }

    // agregarItemPedido()
    public PpordenDTO agregarItemPedido(Long pedidoId,PedidoItemCreateDTO dto) {
        log.info(
            "Agregando producto {} al pedido {}",
            dto.getIdProducto(),
            pedidoId
        );
        Pporden pedido =
            obtenerPedidoPorId(pedidoId);
            
            if (!pedido.getEstado().equalsIgnoreCase("CREADO")) {
                log.warn(
                    "Intento de agregar item a pedido {} con estado {}",
                    pedidoId,
                    pedido.getEstado()
                );
                throw new RuntimeException(
                    "Solo se pueden agregar items a pedidos en estado CREADO");
            }

            ProductoDTO producto =
                    productoClient.getProductoById(dto.getIdProducto());
            if (producto == null) {
                log.warn(
                    "Producto no encontrado. Id={}",
                    dto.getIdProducto()
                );
                throw new ResourceNotFoundException("Producto no encontrado");
            }

            PedidoItem item = new PedidoItem();
            item.setIdProducto(dto.getIdProducto());
            item.setCantidad(dto.getCantidad());
            item.setPedido(pedido);
            pedidoItemRepository.save(item);
            pedido.getItems().add(item);
            ppordenRepository.save(pedido);
            log.info(
                "Producto agregado correctamente al pedido {}",
                pedidoId
            );
            return convertirADTO(pedido);
    }

    //obtener el pedido
    public PpordenDTO obtenerPedidoDTO(Long id) {
        log.info(
            "Consultando pedido con id={}",
            id
        );
        return convertirADTO(
            obtenerPedidoPorId(id)
        );
    }

    //obtener el evento x id
    public List<PpordenDTO> listarPedidosPorEventoDTO(Long idEvento) {
        log.info(
            "Consultando pedidos del evento {}",
            idEvento
        );
        return ppordenRepository
            .findByIdEvento(idEvento)
            .stream()
            .map(this::convertirADTO)
            .toList();
    }
    //modificar el estado de la orden
    public PpordenDTO cambiarEstado(Long id,EstadoPedidoDTO dto) {
        log.info(
            "Cambiando estado del pedido {} a {}",
            id,
            dto.getEstado()
        );
        Pporden pedido =
            obtenerPedidoPorId(id);

        pedido.setEstado(
            dto.getEstado().toUpperCase()
        );

        Pporden actualizado =
            ppordenRepository.save(pedido);
            log.info(
            "Estado actualizado correctamente para pedido {}",
            id
        );
        return convertirADTO(actualizado);
    }

    //eliminar items de la orden
    public PpordenDTO eliminarItemPedido(Long pedidoId,Long itemId) {
        log.info(
            "Eliminando item {} del pedido {}",
            itemId,
            pedidoId
        );
        Pporden pedido =
            obtenerPedidoPorId(pedidoId);

        PedidoItem item =
            pedidoItemRepository.findById(itemId)
            .orElseThrow(() ->
            new RuntimeException("Item no encontrado"));

        pedido.getItems().remove(item);
        pedidoItemRepository.delete(item);
        ppordenRepository.save(pedido);
        log.info(
            "Item {} eliminado correctamente",
            itemId
        );
        return convertirADTO(pedido);
    }

    //eliminar la orden
    public void eliminarPedido(Long id) {
        log.info(
            "Eliminando pedido {}",
            id
        );
        Pporden pedido =
        obtenerPedidoPorId(id);

        pedido.getItems()
            .forEach(pedidoItemRepository::delete);

        pedido.getItems().clear();
        ppordenRepository.delete(pedido);
        log.info(
        "Pedido {} eliminado correctamente",
            id
        );
    }

    //obtener por items
    public PedidoItemDTO obtenerItemDTO(Long itemId) {
        PedidoItem item =
            pedidoItemRepository.findById(itemId)
                .orElseThrow(() -> {
                    log.warn(
                    "Item no encontrado."
                );
                return new RuntimeException("Item no encontrado");
                });
        ProductoDTO producto =
            productoClient.getProductoById(
                item.getIdProducto()
            );

        return new PedidoItemDTO(
            item.getId(),
            item.getIdProducto(),
            producto,
            item.getCantidad()
        );
    }
    //obtener x feign usuarios
    public UsuarioDTO obtenerUsuario(Long id) {
        return usuarioClient.getUsuarioById(id);
    }

    //obtener x feign productos
    public ProductoDTO obtenerProducto(Long id) {
        return productoClient.getProductoById(id);
    }

    //obtener x feign eventos
    public EventoDTO obtenerEvento(Long id) {
        return eventoClient.getEventoById(id);
    }

    //obtener x feign stands
    public StandDTO obtenerStand(Long id) {
        return standClient.getStandById(id);
    }

    //get x id
    public Pporden obtenerPedidoPorId(Long id) {
        return ppordenRepository.findById(id)
        .orElseThrow(() ->
        new RuntimeException("Pedido no encontrado"));
    }

    //que existan eventosm
    public void validarEventoActivo(Long idEvento) {
        log.info(
            "Validando evento {}",
            idEvento
        );
        EventoDTO evento =
            eventoClient.getEventoById(idEvento);

        if (evento == null) {
            log.warn(
                "Evento no encontrado. Id={}",
                idEvento
            );
            throw new RuntimeException("Evento no encontrado");
        }
        if (!evento.getEstado()
            .equalsIgnoreCase("ACTIVO")) {
            log.warn(
                "Evento {} no está activo. Estado={}",
                idEvento,
                evento.getEstado()
            );
            throw new RuntimeException(
                "El evento no se encuentra activo");
        }
    }

    private PpordenDTO convertirADTO(Pporden pedido) {
        EventoDTO evento =
        eventoClient.getEventoById(
            pedido.getIdEvento()
        );

        UsuarioDTO usuario =
            usuarioClient.getUsuarioById(
                pedido.getIdUsuario()
            );

        List<PedidoItemDTO> itemsDTO =
            pedido.getItems()
                .stream()
                    .map(item -> {
                    ProductoDTO producto =
                        productoClient.getProductoById(
                            item.getIdProducto()
                        );
                        
                        return new PedidoItemDTO(
                            item.getId(),
                            item.getIdProducto(),
                            producto,
                            item.getCantidad()
                        );
                    })
                    .toList();

        return new PpordenDTO(
            pedido.getId(),
            pedido.getNorden(),
            pedido.getTipo(),
            pedido.getEstado(),
            pedido.getFechaCreacion(),
            pedido.getIdEvento(),
            pedido.getIdUsuario(),
            evento,
            usuario,
            itemsDTO
        );
    }
}
