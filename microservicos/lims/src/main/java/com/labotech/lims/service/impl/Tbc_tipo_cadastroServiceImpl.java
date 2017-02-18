package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_tipo_cadastroService;
import com.labotech.lims.domain.Tbc_tipo_cadastro;
import com.labotech.lims.repository.Tbc_tipo_cadastroRepository;
import com.labotech.lims.repository.search.Tbc_tipo_cadastroSearchRepository;
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
 * Service Implementation for managing Tbc_tipo_cadastro.
 */
@Service
@Transactional
public class Tbc_tipo_cadastroServiceImpl implements Tbc_tipo_cadastroService{

    private final Logger log = LoggerFactory.getLogger(Tbc_tipo_cadastroServiceImpl.class);

    @Inject
    private Tbc_tipo_cadastroRepository tbc_tipo_cadastroRepository;

    @Inject
    private Tbc_tipo_cadastroSearchRepository tbc_tipo_cadastroSearchRepository;

    /**
     * Save a tbc_tipo_cadastro.
     *
     * @param tbc_tipo_cadastro the entity to save
     * @return the persisted entity
     */
    public Tbc_tipo_cadastro save(Tbc_tipo_cadastro tbc_tipo_cadastro) {
        log.debug("Request to save Tbc_tipo_cadastro : {}", tbc_tipo_cadastro);
        Tbc_tipo_cadastro result = tbc_tipo_cadastroRepository.save(tbc_tipo_cadastro);
        tbc_tipo_cadastroSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_tipo_cadastros.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_tipo_cadastro> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_tipo_cadastros");
        Page<Tbc_tipo_cadastro> result = tbc_tipo_cadastroRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_tipo_cadastro by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tbc_tipo_cadastro findOne(Long id) {
        log.debug("Request to get Tbc_tipo_cadastro : {}", id);
        Tbc_tipo_cadastro tbc_tipo_cadastro = tbc_tipo_cadastroRepository.findOne(id);
        return tbc_tipo_cadastro;
    }

    /**
     *  Delete the  tbc_tipo_cadastro by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_tipo_cadastro : {}", id);
        tbc_tipo_cadastroRepository.delete(id);
        tbc_tipo_cadastroSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_tipo_cadastro corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_tipo_cadastro> search(String query, Boolean removido, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_tipo_cadastros for query {}", query);
        Page<Tbc_tipo_cadastro> result = tbc_tipo_cadastroRepository.search(query, removido, pageable);
        return result;
    }
}
