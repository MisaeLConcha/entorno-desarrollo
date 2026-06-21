package com.duoc.pporden.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idProducto;
    private Integer cantidad;

    // Relación MANY TO ONE (muchos items pertenecen a un pedido)
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnore
    private Pporden pedido;
}