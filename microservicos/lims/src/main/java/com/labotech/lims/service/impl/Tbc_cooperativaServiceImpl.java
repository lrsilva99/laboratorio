package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_cooperativaService;
import com.labotech.lims.domain.Tbc_cooperativa;
import com.labotech.lims.repository.Tbc_cooperativaRepository;
import com.labotech.lims.repository.search.Tbc_cooperativaSearchRepository;
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
 * Service Implementation for managing Tbc_cooperativa.
 */
@Service
@Transactional
public class Tbc_cooperativaServiceImpl implements Tbc_cooperativaService{

    private final Logger log = LoggerFactory.getLogger(Tbc_cooperativaServiceImpl.class);
    
    @Inject
    private Tbc_cooperativaRepository tbc_cooperativaRepository;

    @Inject
    private Tbc_cooperativaSearchRepository tbc_cooperativaSearchRepository;

    /**
     * Save a tbc_cooperativa.
     *
     * @param tbc_cooperativa the entity to save
     * @return the persisted entity
     */
    public Tbc_cooperativa save(Tbc_cooperativa tbc_cooperativa) {
        log.debug("Request to save Tbc_cooperativa : {}", tbc_cooperativa);
        Tbc_cooperativa result = tbc_cooperativaRepository.save(tbc_cooperativa);
        tbc_cooperativaSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_cooperativas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tbc_cooperativa> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_cooperativas");
        Page<Tbc_cooperativa> result = tbc_cooperativaRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_cooperativa by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tbc_cooperativa findOne(Long id) {
        log.debug("Request to get Tbc_cooperativa : {}", id);
        Tbc_cooperativa tbc_cooperativa = tbc_cooperativaRepository.findOne(id);
        return tbc_cooperativa;
    }

    /**
     *  Delete the  tbc_cooperativa by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_cooperativa : {}", id);
        tbc_cooperativaRepository.delete(id);
        tbc_cooperativaSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_cooperativa corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_cooperativa> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_cooperativas for query {}", query);
        Page<Tbc_cooperativa> result = tbc_cooperativaSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
