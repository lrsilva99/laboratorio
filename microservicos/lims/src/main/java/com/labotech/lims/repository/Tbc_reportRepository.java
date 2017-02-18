package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_report;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;


import java.util.List;

/**
 * Spring Data JPA repository for the Tbc_report entity.
 */
@SuppressWarnings("unused")
public interface Tbc_reportRepository extends JpaRepository<Tbc_report,Long> {

    @Query("select u from Tbc_report u where u.nome like  %?1% and removido = ?2")
    Page<Tbc_report> search(@Param("search") String search, @Param("removido") Boolean removido,Pageable pageable);

    @Query("select u from Tbc_report u where removido = false")
    Page<Tbc_report> findAll(Pageable pageable);
}
