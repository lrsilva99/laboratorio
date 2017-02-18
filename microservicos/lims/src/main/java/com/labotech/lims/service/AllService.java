package com.labotech.lims.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Leonardo on 11/02/2017.
 */
public interface AllService {

    /**
     * Save a tbc_instituicao.
     *
     * @param tbc_instituicao the entity to save
     * @return the persisted entity
     */
    Object save(Object tbc_instituicao);

    /**
     *  Get all the tbc_instituicaos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Class> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_instituicao.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Object findOne(Long id);

    /**
     *  Delete the "id" tbc_instituicao.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_instituicao corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Class> search(String query, Boolean removido, Pageable pageable);
}
