package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_plano_teste_analise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Service Interface for managing Tbc_plano_teste_analise.
 */
public interface Tbc_plano_teste_analiseService {

    /**
     * Save a tbc_plano_teste_analise.
     *
     * @param tbc_plano_teste_analise the entity to save
     * @return the persisted entity
     */
    Tbc_plano_teste_analise save(Tbc_plano_teste_analise tbc_plano_teste_analise);

    /**
     *  Get all the tbc_plano_teste_analises.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_plano_teste_analise> findAll(Long idPlanoTeste, Pageable pageable);

    /**
     *  Get the "id" tbc_plano_teste_analise.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_plano_teste_analise findOne(Long id);

    /**
     *  Delete the "id" tbc_plano_teste_analise.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_plano_teste_analise corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_plano_teste_analise> search(String query, Pageable pageable);
    /**
     *
     *
     *  @param plano_teste_id tbc plano teste
     *
     *  @return the list of entities
     */
    List<Tbc_plano_teste_analise> listAllPlanoTeste (Long plano_teste_id);
}
