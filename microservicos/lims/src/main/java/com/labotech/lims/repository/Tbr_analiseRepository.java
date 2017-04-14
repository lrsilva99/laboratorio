package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbr_analise;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Tbr_analise entity.
 */
@SuppressWarnings("unused")
public interface Tbr_analiseRepository extends JpaRepository<Tbr_analise,Long> {
    @Query("select u from Tbr_analise u where u.tbr_amostra.id = ?1")
    Page<Tbr_analise> findAllForAmostra(@Param("id") Long id, Pageable pageable);

    @Query("select u from Tbr_analise u where u.tbr_amostra.id = ?1 and u.tbc_analises.tbc_grupo_analise.id <> 27  order by u.tbc_analises.tbc_grupo_analise.nome")
    List<Tbr_analise> findAllList(@Param("id") Long id);

}
