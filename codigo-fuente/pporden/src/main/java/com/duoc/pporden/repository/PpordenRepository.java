package com.duoc.pporden.repository;

import com.duoc.pporden.model.Pporden;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PpordenRepository extends JpaRepository<Pporden, Long> {

    List<Pporden> findByEventoId(Long idEvento);
    List<Pporden> findByEstado(String estado);

}