package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_generoService;
import com.labotech.lims.domain.Tbc_genero;
import com.labotech.lims.repository.Tbc_generoRepository;
import com.labotech.lims.repository.search.Tbc_generoSearchRepository;
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
 * Service Implementation for managing Tbc_genero.
 */
@Service
@Transactional
public class Tbc_generoServiceImpl implements Tbc_generoService{

    private final Logger log = LoggerFactory.getLogger(Tbc_generoServiceImpl.class);
    
    @Inject
    private Tbc_generoRepository tbc_generoRepository;

    @Inject
    private Tbc_generoSearchRepository tbc_generoSearchRepository;

    /**
     * Save a tbc_genero.
     *
     * @param tbc_genero the entity to save
     * @return the persisted entity
     */
    public Tbc_genero save(Tbc_genero tbc_genero) {
        log.debug("Request to save Tbc_genero : {}", tbc_genero);
        Tbc_genero result = tbc_generoRepository.save(tbc_genero);
        tbc_generoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_generos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tbc_genero> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_generos");
        Page<Tbc_genero> result = tbc_generoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_genero by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tbc_genero findOne(Long id) {
        log.debug("Request to get Tbc_genero : {}", id);
        Tbc_genero tbc_genero = tbc_generoRepository.findOne(id);
        return tbc_genero;
    }

    /**
     *  Delete the  tbc_genero by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_genero : {}", id);
        tbc_generoRepository.delete(id);
        tbc_generoSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_genero corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_genero> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_generos for query {}", query);
        Page<Tbc_genero> result = tbc_generoSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
