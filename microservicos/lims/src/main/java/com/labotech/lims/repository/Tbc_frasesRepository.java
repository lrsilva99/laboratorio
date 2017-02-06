package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_frases;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tbc_frases entity.
 */
@SuppressWarnings("unused")
public interface Tbc_frasesRepository extends JpaRepository<Tbc_frases,Long> {

}
