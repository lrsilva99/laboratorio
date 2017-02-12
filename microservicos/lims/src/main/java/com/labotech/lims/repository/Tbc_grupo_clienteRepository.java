package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_grupo_cliente;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;


import java.util.List;

/**
 * Spring Data JPA repository for the Tbc_grupo_cliente entity.
 */
@SuppressWarnings("unused")
public interface Tbc_grupo_clienteRepository extends JpaRepository<Tbc_grupo_cliente,Long> {
    @Query("select u from Tbc_grupo_cliente u where u.nome like  %?1% and removido = ?2")
    Page<Tbc_grupo_cliente> search(@Param("search") String search, @Param("removido") Boolean removido,Pageable pageable);

    @Query("select u from Tbc_grupo_cliente u where removido = false")
    Page<Tbc_grupo_cliente> findAll(Pageable pageable);

}
