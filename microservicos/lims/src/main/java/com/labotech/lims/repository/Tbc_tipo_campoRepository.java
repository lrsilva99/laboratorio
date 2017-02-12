package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_tipo_campo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;


import java.util.List;

/**
 * Spring Data JPA repository for the Tbc_tipo_campo entity.
 */
@SuppressWarnings("unused")
public interface Tbc_tipo_campoRepository extends JpaRepository<Tbc_tipo_campo,Long> {

    @Query("select u from Tbc_tipo_campo u where u.nome like  %?1% and removido = ?2")
    Page<Tbc_tipo_campo> search(@Param("search") String search, @Param("removido") Boolean removido,Pageable pageable);

    @Query("select u from Tbc_tipo_campo u where removido = false")
    Page<Tbc_tipo_campo> findAll(Pageable pageable);


}
