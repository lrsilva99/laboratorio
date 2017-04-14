package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_forma_armazenamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_forma_armazenamento.
 */
public interface Tbc_forma_armazenamentoService {

    /**
     * Save a tbc_forma_armazenamento.
     *
     * @param tbc_forma_armazenamento the entity to save
     * @return the persisted entity
     */
    Tbc_forma_armazenamento save(Tbc_forma_armazenamento tbc_forma_armazenamento);

    /**
     *  Get all the tbc_forma_armazenamentos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_forma_armazenamento> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_forma_armazenamento.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_forma_armazenamento findOne(Long id);

    /**
     *  Delete the "id" tbc_forma_armazenamento.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_forma_armazenamento corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_forma_armazenamento> search(String query, Pageable pageable);
}
