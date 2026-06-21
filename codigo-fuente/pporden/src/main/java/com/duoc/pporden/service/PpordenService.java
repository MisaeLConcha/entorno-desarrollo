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
    
    //listar todo()
    public List<PpordenDTO> listarTodosDTO() {
        return ppordenRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .toList();
    }
    // crearPedido()
    public PpordenDTO crearPedido(PpordenCreateDTO dto) {
        validarEventoActivo(dto.getIdEvento());
        UsuarioDTO usuario =
            usuarioClient.getUsuarioById(dto.getIdUsuario());

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
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
            return convertirADTO(guardado);
    }

    // agregarItemPedido()
    public PpordenDTO agregarItemPedido(Long pedidoId,PedidoItemCreateDTO dto) {
        Pporden pedido =
            obtenerPedidoPorId(pedidoId);
            
            if (!pedido.getEstado().equalsIgnoreCase("CREADO")) {
                throw new RuntimeException(
                    "Solo se pueden agregar items a pedidos en estado CREADO");
            }

            ProductoDTO producto =
                    productoClient.getProductoById(dto.getIdProducto());
            if (producto == null) {
                throw new RuntimeException("Producto no encontrado");
            }

            PedidoItem item = new PedidoItem();
            item.setIdProducto(dto.getIdProducto());
            item.setCantidad(dto.getCantidad());
            item.setPedido(pedido);
            pedidoItemRepository.save(item);
            pedido.getItems().add(item);
            ppordenRepository.save(pedido);
            return convertirADTO(pedido);
    }

    //obtener el pedido
    public PpordenDTO obtenerPedidoDTO(Long id) {
        return convertirADTO(
            obtenerPedidoPorId(id)
        );
    }

    //obtener el evento x id
    public List<PpordenDTO> listarPedidosPorEventoDTO(Long idEvento) {
        return ppordenRepository
            .findByIdEvento(idEvento)
            .stream()
            .map(this::convertirADTO)
            .toList();
    }
    //modificar el estado de la orden
    public PpordenDTO cambiarEstado(Long id,EstadoPedidoDTO dto) {
        Pporden pedido =
            obtenerPedidoPorId(id);

        pedido.setEstado(
            dto.getEstado().toUpperCase()
        );

        Pporden actualizado =
            ppordenRepository.save(pedido);
        return convertirADTO(actualizado);
    }

    //eliminar items de la orden
    public PpordenDTO eliminarItemPedido(Long pedidoId,Long itemId) {
        Pporden pedido =
            obtenerPedidoPorId(pedidoId);

        PedidoItem item =
            pedidoItemRepository.findById(itemId)
            .orElseThrow(() ->
            new RuntimeException("Item no encontrado"));

        pedido.getItems().remove(item);
        pedidoItemRepository.delete(item);
        ppordenRepository.save(pedido);
        return convertirADTO(pedido);
    }

    //eliminar la orden
    public void eliminarPedido(Long id) {
        Pporden pedido =
        obtenerPedidoPorId(id);

        pedido.getItems()
            .forEach(pedidoItemRepository::delete);

        pedido.getItems().clear();
        ppordenRepository.delete(pedido);
    }

    //obtener por items
    public PedidoItemDTO obtenerItemDTO(Long itemId) {
        PedidoItem item =
            pedidoItemRepository.findById(itemId)
                .orElseThrow(() ->
                    new RuntimeException("Item no encontrado"));

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
        EventoDTO evento =
            eventoClient.getEventoById(idEvento);

        if (evento == null) {
            throw new RuntimeException("Evento no encontrado");
        }
        if (!evento.getEstado()
            .equalsIgnoreCase("ACTIVO")) {
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
