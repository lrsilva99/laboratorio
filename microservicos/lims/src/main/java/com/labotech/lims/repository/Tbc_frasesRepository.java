package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_frases;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;


import java.util.List;


/**
 * Spring Data JPA repository for the Tbc_frases entity.
 */
@SuppressWarnings("unused")

public interface Tbc_frasesRepository extends JpaRepository<Tbc_frases,Long> {
    @Query("select u from Tbc_frases u where u.nome like  %?1% and removido = ?2")
    Page<Tbc_frases> search(@Param("search") String search, @Param("removido") Boolean removido,Pageable pageable);

    @Query("select u from Tbc_frases u where removido = false")
    Page<Tbc_frases> findAll(Pageable pageable);

}
