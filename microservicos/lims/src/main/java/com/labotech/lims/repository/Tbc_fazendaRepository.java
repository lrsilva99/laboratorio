package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_fazenda;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tbc_fazenda entity.
 */
@SuppressWarnings("unused")
public interface Tbc_fazendaRepository extends JpaRepository<Tbc_fazenda,Long> {

}
