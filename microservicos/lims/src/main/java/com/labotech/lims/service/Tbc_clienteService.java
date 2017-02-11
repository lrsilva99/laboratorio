package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_cliente.
 */
public interface Tbc_clienteService {

    /**
     * Save a tbc_cliente.
     *
     * @param tbc_cliente the entity to save
     * @return the persisted entity
     */
    Tbc_cliente save(Tbc_cliente tbc_cliente);

    /**
     *  Get all the tbc_clientes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_cliente> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_cliente.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_cliente findOne(Long id);

    /**
     *  Delete the "id" tbc_cliente.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_cliente corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_cliente> search(String query, Pageable pageable);
}
