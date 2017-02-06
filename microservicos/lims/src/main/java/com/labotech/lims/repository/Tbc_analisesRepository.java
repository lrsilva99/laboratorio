package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_analises;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Tbc_analises entity.
 */
@SuppressWarnings("unused")
public interface Tbc_analisesRepository extends JpaRepository<Tbc_analises,Long> {

    @Query("select u from Tbc_analises u where u.nome like  %?1% and removido = false")
    Page<Tbc_analises> findAllSearch(@Param("search") String search, Pageable pageable);

}
