package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Tbc_status entity.
 */
@SuppressWarnings("unused")
public interface Tbc_statusRepository extends JpaRepository<Tbc_status,Long> {

    @Query("select u from Tbc_status u where u.nome = ?1")
    Tbc_status findOneNomeStatus(@Param("nome") String nome);

}
