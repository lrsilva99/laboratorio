package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_matriz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_matriz.
 */
public interface Tbc_matrizService {

    /**
     * Save a tbc_matriz.
     *
     * @param tbc_matriz the entity to save
     * @return the persisted entity
     */
    Tbc_matriz save(Tbc_matriz tbc_matriz);

    /**
     *  Get all the tbc_matrizs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_matriz> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_matriz.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_matriz findOne(Long id);

    /**
     *  Delete the "id" tbc_matriz.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_matriz corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_matriz> search(String query, Pageable pageable);
}
