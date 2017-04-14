package com.labotech.lims.service;

import com.labotech.lims.domain.Tbr_analise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbr_analise.
 */
public interface Tbr_analiseService {

    /**
     * Save a tbr_analise.
     *
     * @param tbr_analise the entity to save
     * @return the persisted entity
     */
    Tbr_analise save(Tbr_analise tbr_analise);

    /**
     *  Get all the tbr_analises.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbr_analise> findAll(Long id_Amostra, Pageable pageable);

    /**
     *  Get all the tbr_analises.
     *
     *  @param id_Amostra the sample
     *  @return the list of entities
     */
    List<Tbr_analise> findAllList(Long id_Amostra);

    /**
     *  Get the "id" tbr_analise.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbr_analise findOne(Long id);

    /**
     *  Delete the "id" tbr_analise.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbr_analise corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbr_analise> search(String query, Pageable pageable);
}
