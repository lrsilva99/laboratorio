package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_frases_opcoesService;
import com.labotech.lims.domain.Tbc_frases_opcoes;
import com.labotech.lims.repository.Tbc_frases_opcoesRepository;
import com.labotech.lims.repository.search.Tbc_frases_opcoesSearchRepository;
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
 * Service Implementation for managing Tbc_frases_opcoes.
 */
@Service
@Transactional
public class Tbc_frases_opcoesServiceImpl implements Tbc_frases_opcoesService{

    private final Logger log = LoggerFactory.getLogger(Tbc_frases_opcoesServiceImpl.class);

    @Inject
    private Tbc_frases_opcoesRepository tbc_frases_opcoesRepository;

    @Inject
    private Tbc_frases_opcoesSearchRepository tbc_frases_opcoesSearchRepository;

    /**
     * Save a tbc_frases_opcoes.
     *
     * @param tbc_frases_opcoes the entity to save
     * @return the persisted entity
     */
    public Tbc_frases_opcoes save(Tbc_frases_opcoes tbc_frases_opcoes) {
        log.debug("Request to save Tbc_frases_opcoes : {}", tbc_frases_opcoes);
        Tbc_frases_opcoes result = tbc_frases_opcoesRepository.save(tbc_frases_opcoes);
        tbc_frases_opcoesSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_frases_opcoes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_frases_opcoes> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_frases_opcoes");
        Page<Tbc_frases_opcoes> result = tbc_frases_opcoesRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_frases_opcoes by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tbc_frases_opcoes findOne(Long id) {
        log.debug("Request to get Tbc_frases_opcoes : {}", id);
        Tbc_frases_opcoes tbc_frases_opcoes = tbc_frases_opcoesRepository.findOne(id);
        return tbc_frases_opcoes;
    }

    /**
     *  Delete the  tbc_frases_opcoes by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_frases_opcoes : {}", id);
        tbc_frases_opcoesRepository.delete(id);
        tbc_frases_opcoesSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_frases_opcoes corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_frases_opcoes> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_frases_opcoes for query {}", query);
        Page<Tbc_frases_opcoes> result = tbc_frases_opcoesSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

    @Override
    public Page<Tbc_frases_opcoes> findAll(Long idFrases, Pageable pageable) {
        log.debug("Request to get all Tbc_frases_opcoes");
        Page<Tbc_frases_opcoes> result = tbc_frases_opcoesRepository.findAllForFrases(idFrases, pageable);
        return result;
    }
}
