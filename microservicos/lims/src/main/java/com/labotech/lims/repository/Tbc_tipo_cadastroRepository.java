package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_tipo_cadastro;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Tbc_tipo_cadastro entity.
 */
@SuppressWarnings("unused")
public interface Tbc_tipo_cadastroRepository extends JpaRepository<Tbc_tipo_cadastro,Long> {
    @Query("select u from Tbc_tipo_cadastro u where u.nome like  %?1% and removido = ?2")
    Page<Tbc_tipo_cadastro> search(@Param("search") String search, @Param("removido") Boolean removido,Pageable pageable);

    @Query("select u from Tbc_tipo_cadastro u where removido = false")
    Page<Tbc_tipo_cadastro> findAll(Pageable pageable);

}
