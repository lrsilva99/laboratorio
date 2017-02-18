package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_relatorio_ensaio;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;


import java.util.List;

/**
 * Spring Data JPA repository for the Tbc_relatorio_ensaio entity.
 */
@SuppressWarnings("unused")
public interface Tbc_relatorio_ensaioRepository extends JpaRepository<Tbc_relatorio_ensaio,Long> {
    @Query("select u from Tbc_relatorio_ensaio u where u.nome like  %?1% and removido = ?2")
    Page<Tbc_relatorio_ensaio> search(@Param("search") String search, @Param("removido") Boolean removido,Pageable pageable);

    @Query("select u from Tbc_relatorio_ensaio u where removido = false")
    Page<Tbc_relatorio_ensaio> findAll(Pageable pageable);

}
