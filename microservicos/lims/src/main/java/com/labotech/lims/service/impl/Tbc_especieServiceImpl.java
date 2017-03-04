package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_especieService;
import com.labotech.lims.domain.Tbc_especie;
import com.labotech.lims.repository.Tbc_especieRepository;
import com.labotech.lims.repository.search.Tbc_especieSearchRepository;
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
 * Service Implementation for managing Tbc_especie.
 */
@Service
@Transactional
public class Tbc_especieServiceImpl implements Tbc_especieService{

    private final Logger log = LoggerFactory.getLogger(Tbc_especieServiceImpl.class);
    
    @Inject
    private Tbc_especieRepository tbc_especieRepository;

    @Inject
    private Tbc_especieSearchRepository tbc_especieSearchRepository;

    /**
     * Save a tbc_especie.
     *
     * @param tbc_especie the entity to save
     * @return the persisted entity
     */
    public Tbc_especie save(Tbc_especie tbc_especie) {
        log.debug("Request to save Tbc_especie : {}", tbc_especie);
        Tbc_especie result = tbc_especieRepository.save(tbc_especie);
        tbc_especieSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_especies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tbc_especie> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_especies");
        Page<Tbc_especie> result = tbc_especieRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_especie by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tbc_especie findOne(Long id) {
        log.debug("Request to get Tbc_especie : {}", id);
        Tbc_especie tbc_especie = tbc_especieRepository.findOne(id);
        return tbc_especie;
    }

    /**
     *  Delete the  tbc_especie by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_especie : {}", id);
        tbc_especieRepository.delete(id);
        tbc_especieSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_especie corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_especie> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_especies for query {}", query);
        Page<Tbc_especie> result = tbc_especieSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
