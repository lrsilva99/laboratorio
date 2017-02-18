package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_grupo_analise;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Tbc_grupo_analise entity.
 */
@SuppressWarnings("unused")
public interface Tbc_grupo_analiseRepository extends JpaRepository<Tbc_grupo_analise,Long> {
    @Query("select u from Tbc_grupo_analise u where u.nome like  %?1% and removido = ?2")
    Page<Tbc_grupo_analise> search(@Param("search") String search, @Param("removido") Boolean removido,Pageable pageable);

    @Query("select u from Tbc_grupo_analise u where removido = false")
    Page<Tbc_grupo_analise> findAll(Pageable pageable);

}
