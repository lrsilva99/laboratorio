package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_lab_tercerizado;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;


import java.util.List;

/**
 * Spring Data JPA repository for the Tbc_lab_tercerizado entity.
 */
@SuppressWarnings("unused")
public interface Tbc_lab_tercerizadoRepository extends JpaRepository<Tbc_lab_tercerizado,Long> {
    @Query("select u from Tbc_lab_tercerizado u where u.nome like  %?1% and removido = ?2")
    Page<Tbc_lab_tercerizado> search(@Param("search") String search, @Param("removido") Boolean removido,Pageable pageable);

    @Query("select u from Tbc_lab_tercerizado u where removido = false")
    Page<Tbc_lab_tercerizado> findAll(Pageable pageable);

}
