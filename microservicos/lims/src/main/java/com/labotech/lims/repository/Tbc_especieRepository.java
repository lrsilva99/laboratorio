package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_especie;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tbc_especie entity.
 */
@SuppressWarnings("unused")
public interface Tbc_especieRepository extends JpaRepository<Tbc_especie,Long> {

}
