package com.labotech.lims.repository;

import com.labotech.lims.domain.Tbc_numeracao;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Tbc_numeracao entity.
 */
@SuppressWarnings("unused")
public interface Tbc_numeracaoRepository extends JpaRepository<Tbc_numeracao,Long> {
    @Query("select u from Tbc_numeracao u where u.parametro = ?1 and u.ano =?2")
    Tbc_numeracao getTbc_numeracao(@Param("parametro") String id,@Param("ano") Integer ano);

}
