package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_sub_grupo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Service Interface for managing Tbc_sub_grupo.
 */
public interface Tbc_sub_grupoService {

    /**
     * Save a tbc_sub_grupo.
     *
     * @param tbc_sub_grupo the entity to save
     * @return the persisted entity
     */
    Tbc_sub_grupo save(Tbc_sub_grupo tbc_sub_grupo);

    /**
     *  Get all the tbc_sub_grupos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_sub_grupo> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_sub_grupo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_sub_grupo findOne(Long id);

    /**
     *  Delete the "id" tbc_sub_grupo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_sub_grupo corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_sub_grupo> search(String query, Boolean removido, Pageable pageable);
}
