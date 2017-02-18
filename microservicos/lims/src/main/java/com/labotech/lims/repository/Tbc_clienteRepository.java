package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Tbc_cliente entity.
 */
@SuppressWarnings("unused")
public interface Tbc_clienteRepository extends JpaRepository<Tbc_cliente,Long> {

    @Query("select u from Tbc_cliente u where u.nome like  %?1% and removido = ?2")
    Page<Tbc_cliente> search(@Param("search") String search, @Param("removido") Boolean removido,Pageable pageable);

    @Query("select u from Tbc_cliente u where removido = false")
    Page<Tbc_cliente> findAll(Pageable pageable);

}
