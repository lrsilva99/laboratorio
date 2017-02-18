package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_analises_componenteService;
import com.labotech.lims.domain.Tbc_analises_componente;
import com.labotech.lims.repository.Tbc_analises_componenteRepository;
import com.labotech.lims.repository.search.Tbc_analises_componenteSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Tbc_analises_componente.
 */
@Service
@Transactional
public class Tbc_analises_componenteServiceImpl implements Tbc_analises_componenteService{

    private final Logger log = LoggerFactory.getLogger(Tbc_analises_componenteServiceImpl.class);

    @Inject
    private Tbc_analises_componenteRepository tbc_analises_componenteRepository;

    @Inject
    private Tbc_analises_componenteSearchRepository tbc_analises_componenteSearchRepository;

    /**
     * Save a tbc_analises_componente.
     *
     * @param tbc_analises_componente the entity to save
     * @return the persisted entity
     */
    public Tbc_analises_componente save(Tbc_analises_componente tbc_analises_componente) {
        log.debug("Request to save Tbc_analises_componente : {}", tbc_analises_componente);
        Tbc_analises_componente result = tbc_analises_componenteRepository.save(tbc_analises_componente);
        tbc_analises_componenteSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_analises_componentes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_analises_componente> findAll(Long idAnalise, Pageable pageable) {
        log.debug("Request to get all Tbc_analises_componentes");
        Page<Tbc_analises_componente> result = tbc_analises_componenteRepository.findAllForAnalises(idAnalise,pageable);
        return result;
    }

    /**
     *  Get one tbc_analises_componente by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tbc_analises_componente findOne(Long id) {
        log.debug("Request to get Tbc_analises_componente : {}", id);
        Tbc_analises_componente tbc_analises_componente = tbc_analises_componenteRepository.findOne(id);
        return tbc_analises_componente;
    }

    /**
     *  Delete the  tbc_analises_componente by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_analises_componente : {}", id);
        tbc_analises_componenteRepository.delete(id);
        tbc_analises_componenteSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_analises_componente corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_analises_componente> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_analises_componentes for query {}", query);
        Page<Tbc_analises_componente> result = tbc_analises_componenteSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
