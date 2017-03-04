package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_convenioService;
import com.labotech.lims.domain.Tbc_convenio;
import com.labotech.lims.repository.Tbc_convenioRepository;
import com.labotech.lims.repository.search.Tbc_convenioSearchRepository;
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
 * Service Implementation for managing Tbc_convenio.
 */
@Service
@Transactional
public class Tbc_convenioServiceImpl implements Tbc_convenioService{

    private final Logger log = LoggerFactory.getLogger(Tbc_convenioServiceImpl.class);
    
    @Inject
    private Tbc_convenioRepository tbc_convenioRepository;

    @Inject
    private Tbc_convenioSearchRepository tbc_convenioSearchRepository;

    /**
     * Save a tbc_convenio.
     *
     * @param tbc_convenio the entity to save
     * @return the persisted entity
     */
    public Tbc_convenio save(Tbc_convenio tbc_convenio) {
        log.debug("Request to save Tbc_convenio : {}", tbc_convenio);
        Tbc_convenio result = tbc_convenioRepository.save(tbc_convenio);
        tbc_convenioSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_convenios.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tbc_convenio> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_convenios");
        Page<Tbc_convenio> result = tbc_convenioRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_convenio by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tbc_convenio findOne(Long id) {
        log.debug("Request to get Tbc_convenio : {}", id);
        Tbc_convenio tbc_convenio = tbc_convenioRepository.findOne(id);
        return tbc_convenio;
    }

    /**
     *  Delete the  tbc_convenio by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_convenio : {}", id);
        tbc_convenioRepository.delete(id);
        tbc_convenioSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_convenio corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_convenio> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_convenios for query {}", query);
        Page<Tbc_convenio> result = tbc_convenioSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
