package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_instituicaoService;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.repository.Tbc_instituicaoRepository;
import com.labotech.lims.repository.search.Tbc_instituicaoSearchRepository;
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
 * Service Implementation for managing Tbc_instituicao.
 */
@Service
@Transactional
public class Tbc_instituicaoServiceImpl implements Tbc_instituicaoService{

    private final Logger log = LoggerFactory.getLogger(Tbc_instituicaoServiceImpl.class);

    @Inject
    private Tbc_instituicaoRepository tbc_instituicaoRepository;

    @Inject
    private Tbc_instituicaoSearchRepository tbc_instituicaoSearchRepository;

    /**
     * Save a tbc_instituicao.
     *
     * @param tbc_instituicao the entity to save
     * @return the persisted entity
     */
    public Tbc_instituicao save(Tbc_instituicao tbc_instituicao) {
        log.debug("Request to save Tbc_instituicao : {}", tbc_instituicao);
        Tbc_instituicao result = tbc_instituicaoRepository.save(tbc_instituicao);
        tbc_instituicaoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_instituicaos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_instituicao> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_instituicaos");
        Page<Tbc_instituicao> result = tbc_instituicaoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_instituicao by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tbc_instituicao findOne(Long id) {
        log.debug("Request to get Tbc_instituicao : {}", id);
        Tbc_instituicao tbc_instituicao = tbc_instituicaoRepository.findOne(id);
        return tbc_instituicao;
    }

    /**
     *  Delete the  tbc_instituicao by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_instituicao : {}", id);
        tbc_instituicaoRepository.delete(id);
        tbc_instituicaoSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_instituicao corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_instituicao> search(String query,Boolean removido, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_instituicaos for query {}", query);
        Page<Tbc_instituicao> result = tbc_instituicaoRepository.search(query,removido, pageable);
        return result;
    }
}
