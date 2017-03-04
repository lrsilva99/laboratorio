package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_matrizService;
import com.labotech.lims.domain.Tbc_matriz;
import com.labotech.lims.repository.Tbc_matrizRepository;
import com.labotech.lims.repository.search.Tbc_matrizSearchRepository;
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
 * Service Implementation for managing Tbc_matriz.
 */
@Service
@Transactional
public class Tbc_matrizServiceImpl implements Tbc_matrizService{

    private final Logger log = LoggerFactory.getLogger(Tbc_matrizServiceImpl.class);
    
    @Inject
    private Tbc_matrizRepository tbc_matrizRepository;

    @Inject
    private Tbc_matrizSearchRepository tbc_matrizSearchRepository;

    /**
     * Save a tbc_matriz.
     *
     * @param tbc_matriz the entity to save
     * @return the persisted entity
     */
    public Tbc_matriz save(Tbc_matriz tbc_matriz) {
        log.debug("Request to save Tbc_matriz : {}", tbc_matriz);
        Tbc_matriz result = tbc_matrizRepository.save(tbc_matriz);
        tbc_matrizSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_matrizs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tbc_matriz> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_matrizs");
        Page<Tbc_matriz> result = tbc_matrizRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_matriz by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tbc_matriz findOne(Long id) {
        log.debug("Request to get Tbc_matriz : {}", id);
        Tbc_matriz tbc_matriz = tbc_matrizRepository.findOne(id);
        return tbc_matriz;
    }

    /**
     *  Delete the  tbc_matriz by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_matriz : {}", id);
        tbc_matrizRepository.delete(id);
        tbc_matrizSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_matriz corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_matriz> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_matrizs for query {}", query);
        Page<Tbc_matriz> result = tbc_matrizSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
