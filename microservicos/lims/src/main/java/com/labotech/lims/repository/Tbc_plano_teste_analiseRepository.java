package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_plano_teste_analise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Tbc_plano_teste_analise entity.
 */
@SuppressWarnings("unused")
public interface Tbc_plano_teste_analiseRepository extends JpaRepository<Tbc_plano_teste_analise,Long> {

    @Query("select u from Tbc_plano_teste_analise u where u.tbc_plano_teste.id = ?1")
    Page<Tbc_plano_teste_analise> findAll(@Param("idPlanoTeste") Long idPlanoTeste, Pageable pageable);
    @Query("select u from Tbc_plano_teste_analise u where u.tbc_plano_teste.id = ?1")
    List<Tbc_plano_teste_analise> listAllPlanoTeste(@Param("idPlanoTeste") Long idPlanoTeste);
}
