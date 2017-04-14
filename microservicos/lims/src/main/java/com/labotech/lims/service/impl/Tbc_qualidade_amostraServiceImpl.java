package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_qualidade_amostraService;
import com.labotech.lims.domain.Tbc_qualidade_amostra;
import com.labotech.lims.repository.Tbc_qualidade_amostraRepository;
import com.labotech.lims.repository.search.Tbc_qualidade_amostraSearchRepository;
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
 * Service Implementation for managing Tbc_qualidade_amostra.
 */
@Service
@Transactional
public class Tbc_qualidade_amostraServiceImpl implements Tbc_qualidade_amostraService{

    private final Logger log = LoggerFactory.getLogger(Tbc_qualidade_amostraServiceImpl.class);
    
    @Inject
    private Tbc_qualidade_amostraRepository tbc_qualidade_amostraRepository;

    @Inject
    private Tbc_qualidade_amostraSearchRepository tbc_qualidade_amostraSearchRepository;

    /**
     * Save a tbc_qualidade_amostra.
     *
     * @param tbc_qualidade_amostra the entity to save
     * @return the persisted entity
     */
    public Tbc_qualidade_amostra save(Tbc_qualidade_amostra tbc_qualidade_amostra) {
        log.debug("Request to save Tbc_qualidade_amostra : {}", tbc_qualidade_amostra);
        Tbc_qualidade_amostra result = tbc_qualidade_amostraRepository.save(tbc_qualidade_amostra);
        tbc_qualidade_amostraSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_qualidade_amostras.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tbc_qualidade_amostra> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_qualidade_amostras");
        Page<Tbc_qualidade_amostra> result = tbc_qualidade_amostraRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_qualidade_amostra by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tbc_qualidade_amostra findOne(Long id) {
        log.debug("Request to get Tbc_qualidade_amostra : {}", id);
        Tbc_qualidade_amostra tbc_qualidade_amostra = tbc_qualidade_amostraRepository.findOne(id);
        return tbc_qualidade_amostra;
    }

    /**
     *  Delete the  tbc_qualidade_amostra by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_qualidade_amostra : {}", id);
        tbc_qualidade_amostraRepository.delete(id);
        tbc_qualidade_amostraSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_qualidade_amostra corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_qualidade_amostra> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_qualidade_amostras for query {}", query);
        Page<Tbc_qualidade_amostra> result = tbc_qualidade_amostraSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
