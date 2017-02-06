package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_grupo_analiseService;
import com.labotech.lims.domain.Tbc_grupo_analise;
import com.labotech.lims.repository.Tbc_grupo_analiseRepository;
import com.labotech.lims.repository.search.Tbc_grupo_analiseSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Tbc_grupo_analise.
 */
@Service
@Transactional
public class Tbc_grupo_analiseServiceImpl implements Tbc_grupo_analiseService{

    private final Logger log = LoggerFactory.getLogger(Tbc_grupo_analiseServiceImpl.class);
    
    @Inject
    private Tbc_grupo_analiseRepository tbc_grupo_analiseRepository;

    @Inject
    private Tbc_grupo_analiseSearchRepository tbc_grupo_analiseSearchRepository;

    /**
     * Save a tbc_grupo_analise.
     *
     * @param tbc_grupo_analise the entity to save
     * @return the persisted entity
     */
    public Tbc_grupo_analise save(Tbc_grupo_analise tbc_grupo_analise) {
        log.debug("Request to save Tbc_grupo_analise : {}", tbc_grupo_analise);
        Tbc_grupo_analise result = tbc_grupo_analiseRepository.save(tbc_grupo_analise);
        tbc_grupo_analiseSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_grupo_analises.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tbc_grupo_analise> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_grupo_analises");
        Page<Tbc_grupo_analise> result = tbc_grupo_analiseRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_grupo_analise by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tbc_grupo_analise findOne(Long id) {
        log.debug("Request to get Tbc_grupo_analise : {}", id);
        Tbc_grupo_analise tbc_grupo_analise = tbc_grupo_analiseRepository.findOne(id);
        return tbc_grupo_analise;
    }

    /**
     *  Delete the  tbc_grupo_analise by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_grupo_analise : {}", id);
        tbc_grupo_analiseRepository.delete(id);
        tbc_grupo_analiseSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_grupo_analise corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_grupo_analise> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_grupo_analises for query {}", query);
        Page<Tbc_grupo_analise> result = tbc_grupo_analiseSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
