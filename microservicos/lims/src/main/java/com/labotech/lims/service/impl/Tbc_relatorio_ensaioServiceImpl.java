package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_relatorio_ensaioService;
import com.labotech.lims.domain.Tbc_relatorio_ensaio;
import com.labotech.lims.repository.Tbc_relatorio_ensaioRepository;
import com.labotech.lims.repository.search.Tbc_relatorio_ensaioSearchRepository;
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
 * Service Implementation for managing Tbc_relatorio_ensaio.
 */
@Service
@Transactional
public class Tbc_relatorio_ensaioServiceImpl implements Tbc_relatorio_ensaioService{

    private final Logger log = LoggerFactory.getLogger(Tbc_relatorio_ensaioServiceImpl.class);
    
    @Inject
    private Tbc_relatorio_ensaioRepository tbc_relatorio_ensaioRepository;

    @Inject
    private Tbc_relatorio_ensaioSearchRepository tbc_relatorio_ensaioSearchRepository;

    /**
     * Save a tbc_relatorio_ensaio.
     *
     * @param tbc_relatorio_ensaio the entity to save
     * @return the persisted entity
     */
    public Tbc_relatorio_ensaio save(Tbc_relatorio_ensaio tbc_relatorio_ensaio) {
        log.debug("Request to save Tbc_relatorio_ensaio : {}", tbc_relatorio_ensaio);
        Tbc_relatorio_ensaio result = tbc_relatorio_ensaioRepository.save(tbc_relatorio_ensaio);
        tbc_relatorio_ensaioSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_relatorio_ensaios.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tbc_relatorio_ensaio> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_relatorio_ensaios");
        Page<Tbc_relatorio_ensaio> result = tbc_relatorio_ensaioRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_relatorio_ensaio by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tbc_relatorio_ensaio findOne(Long id) {
        log.debug("Request to get Tbc_relatorio_ensaio : {}", id);
        Tbc_relatorio_ensaio tbc_relatorio_ensaio = tbc_relatorio_ensaioRepository.findOne(id);
        return tbc_relatorio_ensaio;
    }

    /**
     *  Delete the  tbc_relatorio_ensaio by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_relatorio_ensaio : {}", id);
        tbc_relatorio_ensaioRepository.delete(id);
        tbc_relatorio_ensaioSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_relatorio_ensaio corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_relatorio_ensaio> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_relatorio_ensaios for query {}", query);
        Page<Tbc_relatorio_ensaio> result = tbc_relatorio_ensaioSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
