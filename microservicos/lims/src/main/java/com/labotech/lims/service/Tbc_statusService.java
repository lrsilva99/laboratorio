package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_status.
 */
public interface Tbc_statusService {

    /**
     * Save a tbc_status.
     *
     * @param tbc_status the entity to save
     * @return the persisted entity
     */
    Tbc_status save(Tbc_status tbc_status);
    /**
     * Save a tbc_status.
     *
     * @param nome the entity to search
     * @return the persisted entity
     */
    Tbc_status findOneNomeStatus(String nome);


    /**
     *  Get all the tbc_statuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_status> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_status.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_status findOne(Long id);

    /**
     *  Delete the "id" tbc_status.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_status corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_status> search(String query, Pageable pageable);


}
