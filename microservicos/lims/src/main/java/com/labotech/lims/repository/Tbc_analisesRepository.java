package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_analises;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Tbc_analises entity.
 */
@SuppressWarnings("unused")
public interface Tbc_analisesRepository extends JpaRepository<Tbc_analises,Long> {

    @Query("select u from Tbc_analises u where u.nome like  %?1% and removido = ?2")
    Page<Tbc_analises> search(@Param("search") String search, @Param("removido") Boolean removido,Pageable pageable);

    @Query("select u from Tbc_analises u where removido = false")
    Page<Tbc_analises> findAll(Pageable pageable);


}
