package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbr_amostraService;
import com.labotech.lims.domain.Tbr_amostra;
import com.labotech.lims.repository.Tbr_amostraRepository;
import com.labotech.lims.repository.search.Tbr_amostraSearchRepository;
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
 * Service Implementation for managing Tbr_amostra.
 */
@Service
@Transactional
public class Tbr_amostraServiceImpl implements Tbr_amostraService{

    private final Logger log = LoggerFactory.getLogger(Tbr_amostraServiceImpl.class);
    
    @Inject
    private Tbr_amostraRepository tbr_amostraRepository;

    @Inject
    private Tbr_amostraSearchRepository tbr_amostraSearchRepository;

    /**
     * Save a tbr_amostra.
     *
     * @param tbr_amostra the entity to save
     * @return the persisted entity
     */
    public Tbr_amostra save(Tbr_amostra tbr_amostra) {
        log.debug("Request to save Tbr_amostra : {}", tbr_amostra);
        Tbr_amostra result = tbr_amostraRepository.save(tbr_amostra);
        tbr_amostraSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbr_amostras.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tbr_amostra> findAll(Pageable pageable) {
        log.debug("Request to get all Tbr_amostras");
        Page<Tbr_amostra> result = tbr_amostraRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbr_amostra by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tbr_amostra findOne(Long id) {
        log.debug("Request to get Tbr_amostra : {}", id);
        Tbr_amostra tbr_amostra = tbr_amostraRepository.findOne(id);
        return tbr_amostra;
    }

    /**
     *  Delete the  tbr_amostra by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbr_amostra : {}", id);
        tbr_amostraRepository.delete(id);
        tbr_amostraSearchRepository.delete(id);
    }

    /**
     * Search for the tbr_amostra corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbr_amostra> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbr_amostras for query {}", query);
        Page<Tbr_amostra> result = tbr_amostraSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
