package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_instituicao;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;


import java.util.List;

/**
 * Spring Data JPA repository for the Tbc_instituicao entity.
 */
@SuppressWarnings("unused")
public interface Tbc_instituicaoRepository extends JpaRepository<Tbc_instituicao,Long> {
    @Query("select u from Tbc_instituicao u where u.nome like  %?1% and removido = ?2")
    Page<Tbc_instituicao> search(@Param("search") String search, @Param("removido") Boolean removido,Pageable pageable);

    @Query("select u from Tbc_instituicao u where removido = false")
    Page<Tbc_instituicao> findAll(Pageable pageable);

}
