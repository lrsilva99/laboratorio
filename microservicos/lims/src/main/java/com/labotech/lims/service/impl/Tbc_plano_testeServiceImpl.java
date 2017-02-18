package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_plano_testeService;
import com.labotech.lims.domain.Tbc_plano_teste;
import com.labotech.lims.repository.Tbc_plano_testeRepository;
import com.labotech.lims.repository.search.Tbc_plano_testeSearchRepository;
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
 * Service Implementation for managing Tbc_plano_teste.
 */
@Service
@Transactional
public class Tbc_plano_testeServiceImpl implements Tbc_plano_testeService{

    private final Logger log = LoggerFactory.getLogger(Tbc_plano_testeServiceImpl.class);

    @Inject
    private Tbc_plano_testeRepository tbc_plano_testeRepository;

    @Inject
    private Tbc_plano_testeSearchRepository tbc_plano_testeSearchRepository;

    /**
     * Save a tbc_plano_teste.
     *
     * @param tbc_plano_teste the entity to save
     * @return the persisted entity
     */
    public Tbc_plano_teste save(Tbc_plano_teste tbc_plano_teste) {
        log.debug("Request to save Tbc_plano_teste : {}", tbc_plano_teste);
        Tbc_plano_teste result = tbc_plano_testeRepository.save(tbc_plano_teste);
        tbc_plano_testeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_plano_testes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_plano_teste> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_plano_testes");
        Page<Tbc_plano_teste> result = tbc_plano_testeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_plano_teste by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tbc_plano_teste findOne(Long id) {
        log.debug("Request to get Tbc_plano_teste : {}", id);
        Tbc_plano_teste tbc_plano_teste = tbc_plano_testeRepository.findOne(id);
        return tbc_plano_teste;
    }

    /**
     *  Delete the  tbc_plano_teste by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_plano_teste : {}", id);
        tbc_plano_testeRepository.delete(id);
        tbc_plano_testeSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_plano_teste_analise corresponding to the query.
     *
     *  @param query the query of the search
     *  @param removido the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_plano_teste> search(String query, Boolean removido, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_plano_testes for query {}", query);
        Page<Tbc_plano_teste> result = tbc_plano_testeRepository.search(query, removido, pageable);
        return result;
    }
}
