package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_formulario_componentesService;
import com.labotech.lims.domain.Tbc_formulario_componentes;
import com.labotech.lims.repository.Tbc_formulario_componentesRepository;
import com.labotech.lims.repository.search.Tbc_formulario_componentesSearchRepository;
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
 * Service Implementation for managing Tbc_formulario_componentes.
 */
@Service
@Transactional
public class Tbc_formulario_componentesServiceImpl implements Tbc_formulario_componentesService{

    private final Logger log = LoggerFactory.getLogger(Tbc_formulario_componentesServiceImpl.class);

    @Inject
    private Tbc_formulario_componentesRepository tbc_formulario_componentesRepository;

    @Inject
    private Tbc_formulario_componentesSearchRepository tbc_formulario_componentesSearchRepository;

    /**
     * Save a tbc_formulario_componentes.
     *
     * @param tbc_formulario_componentes the entity to save
     * @return the persisted entity
     */
    public Tbc_formulario_componentes save(Tbc_formulario_componentes tbc_formulario_componentes) {
        log.debug("Request to save Tbc_formulario_componentes : {}", tbc_formulario_componentes);
        Tbc_formulario_componentes result = tbc_formulario_componentesRepository.save(tbc_formulario_componentes);
        tbc_formulario_componentesSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_formulario_componentes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_formulario_componentes> findAll(Long idFormulario,Pageable pageable) {
        log.debug("Request to get all Tbc_formulario_componentes");
        Page<Tbc_formulario_componentes> result = tbc_formulario_componentesRepository.findAllForFormulario(idFormulario,pageable);
        return result;
    }

    /**
     *  Get one tbc_formulario_componentes by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tbc_formulario_componentes findOne(Long id) {
        log.debug("Request to get Tbc_formulario_componentes : {}", id);
        Tbc_formulario_componentes tbc_formulario_componentes = tbc_formulario_componentesRepository.findOne(id);
        return tbc_formulario_componentes;
    }

    /**
     *  Delete the  tbc_formulario_componentes by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_formulario_componentes : {}", id);
        tbc_formulario_componentesRepository.delete(id);
        tbc_formulario_componentesSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_formulario_componentes corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_formulario_componentes> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_formulario_componentes for query {}", query);
        Page<Tbc_formulario_componentes> result = tbc_formulario_componentesSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
