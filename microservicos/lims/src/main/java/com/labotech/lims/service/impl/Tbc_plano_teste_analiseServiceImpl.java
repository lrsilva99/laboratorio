package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_plano_teste_analiseService;
import com.labotech.lims.domain.Tbc_plano_teste_analise;
import com.labotech.lims.repository.Tbc_plano_teste_analiseRepository;
import com.labotech.lims.repository.search.Tbc_plano_teste_analiseSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Tbc_plano_teste_analise.
 */
@Service
@Transactional
public class Tbc_plano_teste_analiseServiceImpl implements Tbc_plano_teste_analiseService{

    private final Logger log = LoggerFactory.getLogger(Tbc_plano_teste_analiseServiceImpl.class);

    @Inject
    private Tbc_plano_teste_analiseRepository tbc_plano_teste_analiseRepository;

    @Inject
    private Tbc_plano_teste_analiseSearchRepository tbc_plano_teste_analiseSearchRepository;

    /**
     * Save a tbc_plano_teste_analise.
     *
     * @param tbc_plano_teste_analise the entity to save
     * @return the persisted entity
     */
    public Tbc_plano_teste_analise save(Tbc_plano_teste_analise tbc_plano_teste_analise) {
        log.debug("Request to save Tbc_plano_teste_analise : {}", tbc_plano_teste_analise);
        Tbc_plano_teste_analise result = tbc_plano_teste_analiseRepository.save(tbc_plano_teste_analise);
        tbc_plano_teste_analiseSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_plano_teste_analises.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_plano_teste_analise> findAll(Long idPlanoTeste, Pageable pageable) {
        log.debug("Request to get all Tbc_plano_teste_analises");
        Page<Tbc_plano_teste_analise> result = tbc_plano_teste_analiseRepository.findAll(idPlanoTeste,pageable);
        return result;
    }

    /**
     *  Get one tbc_plano_teste_analise by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tbc_plano_teste_analise findOne(Long id) {
        log.debug("Request to get Tbc_plano_teste_analise : {}", id);
        Tbc_plano_teste_analise tbc_plano_teste_analise = tbc_plano_teste_analiseRepository.findOne(id);
        return tbc_plano_teste_analise;
    }

    /**
     *  Delete the  tbc_plano_teste_analise by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_plano_teste_analise : {}", id);
        tbc_plano_teste_analiseRepository.delete(id);
        tbc_plano_teste_analiseSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_plano_teste_analise corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_plano_teste_analise> search(String query,Pageable pageable) {
        log.debug("Request to search for a page of Tbc_plano_teste_analises for query {}", query);
        Page<Tbc_plano_teste_analise> result = tbc_plano_teste_analiseSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

    @Override
    public List<Tbc_plano_teste_analise> listAllPlanoTeste(Long plano_teste_id) {
        return tbc_plano_teste_analiseRepository.listAllPlanoTeste(plano_teste_id);
    }
}
