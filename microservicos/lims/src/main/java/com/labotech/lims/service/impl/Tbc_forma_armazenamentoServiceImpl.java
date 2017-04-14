package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_forma_armazenamentoService;
import com.labotech.lims.domain.Tbc_forma_armazenamento;
import com.labotech.lims.repository.Tbc_forma_armazenamentoRepository;
import com.labotech.lims.repository.search.Tbc_forma_armazenamentoSearchRepository;
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
 * Service Implementation for managing Tbc_forma_armazenamento.
 */
@Service
@Transactional
public class Tbc_forma_armazenamentoServiceImpl implements Tbc_forma_armazenamentoService{

    private final Logger log = LoggerFactory.getLogger(Tbc_forma_armazenamentoServiceImpl.class);
    
    @Inject
    private Tbc_forma_armazenamentoRepository tbc_forma_armazenamentoRepository;

    @Inject
    private Tbc_forma_armazenamentoSearchRepository tbc_forma_armazenamentoSearchRepository;

    /**
     * Save a tbc_forma_armazenamento.
     *
     * @param tbc_forma_armazenamento the entity to save
     * @return the persisted entity
     */
    public Tbc_forma_armazenamento save(Tbc_forma_armazenamento tbc_forma_armazenamento) {
        log.debug("Request to save Tbc_forma_armazenamento : {}", tbc_forma_armazenamento);
        Tbc_forma_armazenamento result = tbc_forma_armazenamentoRepository.save(tbc_forma_armazenamento);
        tbc_forma_armazenamentoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_forma_armazenamentos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tbc_forma_armazenamento> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_forma_armazenamentos");
        Page<Tbc_forma_armazenamento> result = tbc_forma_armazenamentoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_forma_armazenamento by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tbc_forma_armazenamento findOne(Long id) {
        log.debug("Request to get Tbc_forma_armazenamento : {}", id);
        Tbc_forma_armazenamento tbc_forma_armazenamento = tbc_forma_armazenamentoRepository.findOne(id);
        return tbc_forma_armazenamento;
    }

    /**
     *  Delete the  tbc_forma_armazenamento by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_forma_armazenamento : {}", id);
        tbc_forma_armazenamentoRepository.delete(id);
        tbc_forma_armazenamentoSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_forma_armazenamento corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_forma_armazenamento> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_forma_armazenamentos for query {}", query);
        Page<Tbc_forma_armazenamento> result = tbc_forma_armazenamentoSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
