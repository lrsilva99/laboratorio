package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_formulario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_formulario.
 */
public interface Tbc_formularioService {

    /**
     * Save a tbc_formulario.
     *
     * @param tbc_formulario the entity to save
     * @return the persisted entity
     */
    Tbc_formulario save(Tbc_formulario tbc_formulario);

    /**
     *  Get all the tbc_formularios.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_formulario> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_formulario.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_formulario findOne(Long id);

    /**
     *  Delete the "id" tbc_formulario.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_formulario corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_formulario> search(String query, Pageable pageable);
}
