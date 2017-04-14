package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_statusService;
import com.labotech.lims.domain.Tbc_status;
import com.labotech.lims.repository.Tbc_statusRepository;
import com.labotech.lims.repository.search.Tbc_statusSearchRepository;
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
 * Service Implementation for managing Tbc_status.
 */
@Service
@Transactional
public class Tbc_statusServiceImpl implements Tbc_statusService{

    private final Logger log = LoggerFactory.getLogger(Tbc_statusServiceImpl.class);

    @Inject
    private Tbc_statusRepository tbc_statusRepository;

    @Inject
    private Tbc_statusSearchRepository tbc_statusSearchRepository;

    /**
     * Save a tbc_status.
     *
     * @param tbc_status the entity to save
     * @return the persisted entity
     */
    public Tbc_status save(Tbc_status tbc_status) {
        log.debug("Request to save Tbc_status : {}", tbc_status);
        Tbc_status result = tbc_statusRepository.save(tbc_status);
        tbc_statusSearchRepository.save(result);
        return result;
    }

    @Override
    public Tbc_status findOneNomeStatus(String nome) {
        Tbc_status tbc_status = tbc_statusRepository.findOneNomeStatus(nome);
        return tbc_status;
    }

    /**
     *  Get all the tbc_statuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_status> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_statuses");
        Page<Tbc_status> result = tbc_statusRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_status by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tbc_status findOne(Long id) {
        log.debug("Request to get Tbc_status : {}", id);
        Tbc_status tbc_status = tbc_statusRepository.findOne(id);
        return tbc_status;
    }

    /**
     *  Delete the  tbc_status by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_status : {}", id);
        tbc_statusRepository.delete(id);
        tbc_statusSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_status corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_status> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_statuses for query {}", query);
        Page<Tbc_status> result = tbc_statusSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
