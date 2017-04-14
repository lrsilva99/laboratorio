package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_analises_componente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Tbc_analises_componente entity.
 */
@SuppressWarnings("unused")
public interface Tbc_analises_componenteRepository extends JpaRepository<Tbc_analises_componente,Long> {

    @Query("select u from Tbc_analises_componente u where u.tbc_analises.id = ?1")
    Page<Tbc_analises_componente> findAllForAnalises(@Param("id") Long id, Pageable pageable);

    @Query("select u from Tbc_analises_componente u where u.tbc_analises.id = ?1 order by u.linha")
    List<Tbc_analises_componente> findAllListForAnalise(@Param("id") Long id);
}
