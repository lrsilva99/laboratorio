package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_frases_opcoes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Tbc_frases_opcoes entity.
 */
@SuppressWarnings("unused")
public interface Tbc_frases_opcoesRepository extends JpaRepository<Tbc_frases_opcoes,Long> {

    @Query("select u from Tbc_frases_opcoes u where u.tbc_frases.id = ?1")
    Page<Tbc_frases_opcoes> findAllForFrases(@Param("id") Long id, Pageable pageable);


}
