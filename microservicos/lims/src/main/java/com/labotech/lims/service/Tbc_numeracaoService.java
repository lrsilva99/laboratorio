package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_numeracao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_numeracao.
 */
public interface Tbc_numeracaoService {

    /**
     * Save a tbc_numeracao.
     *
     * @param tbc_numeracao the entity to save
     * @return the persisted entity
     */
    Tbc_numeracao save(Tbc_numeracao tbc_numeracao);

    /**
     *  Get all the tbc_numeracaos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_numeracao> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_numeracao.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_numeracao findOne(Long id);


    /**
     *  Delete the "id" tbc_numeracao.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_numeracao corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_numeracao> search(String query, Pageable pageable);
    /**
     *  Get the "id" tbc_numeracao.
     *
     *  @param ano the id of the entity
     *  @param parametro the parametro of the entity
     *  @return the entity
     */
    Tbc_numeracao findOne(String parametro , Integer ano);

}
