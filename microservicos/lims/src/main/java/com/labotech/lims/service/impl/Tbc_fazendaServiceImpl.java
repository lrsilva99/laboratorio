package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_fazendaService;
import com.labotech.lims.domain.Tbc_fazenda;
import com.labotech.lims.repository.Tbc_fazendaRepository;
import com.labotech.lims.repository.search.Tbc_fazendaSearchRepository;
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
 * Service Implementation for managing Tbc_fazenda.
 */
@Service
@Transactional
public class Tbc_fazendaServiceImpl implements Tbc_fazendaService{

    private final Logger log = LoggerFactory.getLogger(Tbc_fazendaServiceImpl.class);
    
    @Inject
    private Tbc_fazendaRepository tbc_fazendaRepository;

    @Inject
    private Tbc_fazendaSearchRepository tbc_fazendaSearchRepository;

    /**
     * Save a tbc_fazenda.
     *
     * @param tbc_fazenda the entity to save
     * @return the persisted entity
     */
    public Tbc_fazenda save(Tbc_fazenda tbc_fazenda) {
        log.debug("Request to save Tbc_fazenda : {}", tbc_fazenda);
        Tbc_fazenda result = tbc_fazendaRepository.save(tbc_fazenda);
        tbc_fazendaSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_fazendas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tbc_fazenda> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_fazendas");
        Page<Tbc_fazenda> result = tbc_fazendaRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_fazenda by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tbc_fazenda findOne(Long id) {
        log.debug("Request to get Tbc_fazenda : {}", id);
        Tbc_fazenda tbc_fazenda = tbc_fazendaRepository.findOne(id);
        return tbc_fazenda;
    }

    /**
     *  Delete the  tbc_fazenda by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_fazenda : {}", id);
        tbc_fazendaRepository.delete(id);
        tbc_fazendaSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_fazenda corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_fazenda> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_fazendas for query {}", query);
        Page<Tbc_fazenda> result = tbc_fazendaSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
