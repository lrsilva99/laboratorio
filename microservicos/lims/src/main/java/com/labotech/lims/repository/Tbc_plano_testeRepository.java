package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_plano_teste;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;


import java.util.List;

/**
 * Spring Data JPA repository for the Tbc_plano_teste entity.
 */
@SuppressWarnings("unused")
public interface Tbc_plano_testeRepository extends JpaRepository<Tbc_plano_teste,Long> {
    @Query("select u from Tbc_plano_teste u where u.nome like  %?1% and removido = ?2")
    Page<Tbc_plano_teste> search(@Param("search") String search, @Param("removido") Boolean removido,Pageable pageable);

    @Query("select u from Tbc_plano_teste u where removido = false")
    Page<Tbc_plano_teste> findAll(Pageable pageable);

}
