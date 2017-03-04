package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_formulario_componentes;

import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tbc_formulario_componentes entity.
 */
@SuppressWarnings("unused")
public interface Tbc_formulario_componentesRepository extends JpaRepository<Tbc_formulario_componentes,Long> {
    @Query("select u from Tbc_formulario_componentes u where u.tbc_formulario.id = ?1")
    Page<Tbc_formulario_componentes> findAllForFormulario(@Param("id") Long id, Pageable pageable);

}
