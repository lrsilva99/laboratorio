package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_grupo_cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_grupo_cliente.
 */
public interface Tbc_grupo_clienteService {

    /**
     * Save a tbc_grupo_cliente.
     *
     * @param tbc_grupo_cliente the entity to save
     * @return the persisted entity
     */
    Tbc_grupo_cliente save(Tbc_grupo_cliente tbc_grupo_cliente);

    /**
     *  Get all the tbc_grupo_clientes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_grupo_cliente> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_grupo_cliente.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_grupo_cliente findOne(Long id);

    /**
     *  Delete the "id" tbc_grupo_cliente.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_grupo_cliente corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_grupo_cliente> search(String query, Pageable pageable);
}
