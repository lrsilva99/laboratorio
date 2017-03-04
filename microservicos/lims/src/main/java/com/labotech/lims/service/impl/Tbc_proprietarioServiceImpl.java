package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_proprietarioService;
import com.labotech.lims.domain.Tbc_proprietario;
import com.labotech.lims.repository.Tbc_proprietarioRepository;
import com.labotech.lims.repository.search.Tbc_proprietarioSearchRepository;
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
 * Service Implementation for managing Tbc_proprietario.
 */
@Service
@Transactional
public class Tbc_proprietarioServiceImpl implements Tbc_proprietarioService{

    private final Logger log = LoggerFactory.getLogger(Tbc_proprietarioServiceImpl.class);
    
    @Inject
    private Tbc_proprietarioRepository tbc_proprietarioRepository;

    @Inject
    private Tbc_proprietarioSearchRepository tbc_proprietarioSearchRepository;

    /**
     * Save a tbc_proprietario.
     *
     * @param tbc_proprietario the entity to save
     * @return the persisted entity
     */
    public Tbc_proprietario save(Tbc_proprietario tbc_proprietario) {
        log.debug("Request to save Tbc_proprietario : {}", tbc_proprietario);
        Tbc_proprietario result = tbc_proprietarioRepository.save(tbc_proprietario);
        tbc_proprietarioSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_proprietarios.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tbc_proprietario> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_proprietarios");
        Page<Tbc_proprietario> result = tbc_proprietarioRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_proprietario by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tbc_proprietario findOne(Long id) {
        log.debug("Request to get Tbc_proprietario : {}", id);
        Tbc_proprietario tbc_proprietario = tbc_proprietarioRepository.findOne(id);
        return tbc_proprietario;
    }

    /**
     *  Delete the  tbc_proprietario by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_proprietario : {}", id);
        tbc_proprietarioRepository.delete(id);
        tbc_proprietarioSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_proprietario corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_proprietario> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_proprietarios for query {}", query);
        Page<Tbc_proprietario> result = tbc_proprietarioSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
