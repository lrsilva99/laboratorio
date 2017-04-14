package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_formulario_componentes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_formulario_componentes.
 */
public interface Tbc_formulario_componentesService {

    /**
     * Save a tbc_formulario_componentes.
     *
     * @param tbc_formulario_componentes the entity to save
     * @return the persisted entity
     */
    Tbc_formulario_componentes save(Tbc_formulario_componentes tbc_formulario_componentes);

    /**
     *  Get all the tbc_formulario_componentes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_formulario_componentes> findAll(Long idFormulario, Pageable pageable);

    /**
     *  Get the "id" tbc_formulario_componentes.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_formulario_componentes findOne(Long id);

    /**
     *  Delete the "id" tbc_formulario_componentes.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_formulario_componentes corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_formulario_componentes> search(String query, Pageable pageable);
}
