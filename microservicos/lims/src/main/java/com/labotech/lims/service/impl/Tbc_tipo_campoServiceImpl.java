package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_tipo_campoService;
import com.labotech.lims.domain.Tbc_tipo_campo;
import com.labotech.lims.repository.Tbc_tipo_campoRepository;
import com.labotech.lims.repository.search.Tbc_tipo_campoSearchRepository;
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
 * Service Implementation for managing Tbc_tipo_campo.
 */
@Service
@Transactional
public class Tbc_tipo_campoServiceImpl implements Tbc_tipo_campoService{

    private final Logger log = LoggerFactory.getLogger(Tbc_tipo_campoServiceImpl.class);
    
    @Inject
    private Tbc_tipo_campoRepository tbc_tipo_campoRepository;

    @Inject
    private Tbc_tipo_campoSearchRepository tbc_tipo_campoSearchRepository;

    /**
     * Save a tbc_tipo_campo.
     *
     * @param tbc_tipo_campo the entity to save
     * @return the persisted entity
     */
    public Tbc_tipo_campo save(Tbc_tipo_campo tbc_tipo_campo) {
        log.debug("Request to save Tbc_tipo_campo : {}", tbc_tipo_campo);
        Tbc_tipo_campo result = tbc_tipo_campoRepository.save(tbc_tipo_campo);
        tbc_tipo_campoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_tipo_campos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tbc_tipo_campo> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_tipo_campos");
        Page<Tbc_tipo_campo> result = tbc_tipo_campoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_tipo_campo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tbc_tipo_campo findOne(Long id) {
        log.debug("Request to get Tbc_tipo_campo : {}", id);
        Tbc_tipo_campo tbc_tipo_campo = tbc_tipo_campoRepository.findOne(id);
        return tbc_tipo_campo;
    }

    /**
     *  Delete the  tbc_tipo_campo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_tipo_campo : {}", id);
        tbc_tipo_campoRepository.delete(id);
        tbc_tipo_campoSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_tipo_campo corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_tipo_campo> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_tipo_campos for query {}", query);
        Page<Tbc_tipo_campo> result = tbc_tipo_campoSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
