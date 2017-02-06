package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_sub_grupoService;
import com.labotech.lims.domain.Tbc_sub_grupo;
import com.labotech.lims.repository.Tbc_sub_grupoRepository;
import com.labotech.lims.repository.search.Tbc_sub_grupoSearchRepository;
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
 * Service Implementation for managing Tbc_sub_grupo.
 */
@Service
@Transactional
public class Tbc_sub_grupoServiceImpl implements Tbc_sub_grupoService{

    private final Logger log = LoggerFactory.getLogger(Tbc_sub_grupoServiceImpl.class);
    
    @Inject
    private Tbc_sub_grupoRepository tbc_sub_grupoRepository;

    @Inject
    private Tbc_sub_grupoSearchRepository tbc_sub_grupoSearchRepository;

    /**
     * Save a tbc_sub_grupo.
     *
     * @param tbc_sub_grupo the entity to save
     * @return the persisted entity
     */
    public Tbc_sub_grupo save(Tbc_sub_grupo tbc_sub_grupo) {
        log.debug("Request to save Tbc_sub_grupo : {}", tbc_sub_grupo);
        Tbc_sub_grupo result = tbc_sub_grupoRepository.save(tbc_sub_grupo);
        tbc_sub_grupoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_sub_grupos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tbc_sub_grupo> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_sub_grupos");
        Page<Tbc_sub_grupo> result = tbc_sub_grupoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_sub_grupo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tbc_sub_grupo findOne(Long id) {
        log.debug("Request to get Tbc_sub_grupo : {}", id);
        Tbc_sub_grupo tbc_sub_grupo = tbc_sub_grupoRepository.findOne(id);
        return tbc_sub_grupo;
    }

    /**
     *  Delete the  tbc_sub_grupo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_sub_grupo : {}", id);
        tbc_sub_grupoRepository.delete(id);
        tbc_sub_grupoSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_sub_grupo corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_sub_grupo> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_sub_grupos for query {}", query);
        Page<Tbc_sub_grupo> result = tbc_sub_grupoSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
