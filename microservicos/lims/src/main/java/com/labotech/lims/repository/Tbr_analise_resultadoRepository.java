package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbr_analise_resultado;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Tbr_analise_resultado entity.
 */
@SuppressWarnings("unused")
public interface Tbr_analise_resultadoRepository extends JpaRepository<Tbr_analise_resultado,Long> {


    @Query("select u from Tbr_analise_resultado u where u.tbr_amostra_id = ?1")
    List<Tbr_analise_resultado> findAllForAnalise(@Param("tbr_amostra_id") Long id);

}
