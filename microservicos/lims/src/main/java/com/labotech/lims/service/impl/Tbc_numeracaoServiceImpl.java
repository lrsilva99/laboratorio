package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_numeracaoService;
import com.labotech.lims.domain.Tbc_numeracao;
import com.labotech.lims.repository.Tbc_numeracaoRepository;
import com.labotech.lims.repository.search.Tbc_numeracaoSearchRepository;
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
 * Service Implementation for managing Tbc_numeracao.
 */
@Service
@Transactional
public class Tbc_numeracaoServiceImpl implements Tbc_numeracaoService{

    private final Logger log = LoggerFactory.getLogger(Tbc_numeracaoServiceImpl.class);

    @Inject
    private Tbc_numeracaoRepository tbc_numeracaoRepository;

    @Inject
    private Tbc_numeracaoSearchRepository tbc_numeracaoSearchRepository;

    /**
     * Save a tbc_numeracao.
     *
     * @param tbc_numeracao the entity to save
     * @return the persisted entity
     */
    public Tbc_numeracao save(Tbc_numeracao tbc_numeracao) {
        log.debug("Request to save Tbc_numeracao : {}", tbc_numeracao);
        Tbc_numeracao result = tbc_numeracaoRepository.save(tbc_numeracao);
        tbc_numeracaoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_numeracaos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_numeracao> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_numeracaos");
        Page<Tbc_numeracao> result = tbc_numeracaoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_numeracao by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tbc_numeracao findOne(Long id) {
        log.debug("Request to get Tbc_numeracao : {}", id);
        Tbc_numeracao tbc_numeracao = tbc_numeracaoRepository.findOne(id);
        return tbc_numeracao;
    }

    /**
     *  Delete the  tbc_numeracao by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_numeracao : {}", id);
        tbc_numeracaoRepository.delete(id);
        tbc_numeracaoSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_numeracao corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_numeracao> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_numeracaos for query {}", query);
        Page<Tbc_numeracao> result = tbc_numeracaoSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

    /**
     *  Get one tbc_numeracao by parametro and ano.
     *
     *  @param parametro the id of the entity
     *  @param ano the id of the entity
     *  @return of entities
     */
    @Transactional(readOnly = true)
    public Tbc_numeracao findOne(String parametro, Integer ano) {
        log.debug("Request to get Tbc_numeracao : {}", parametro + " - " + ano);
        Tbc_numeracao tbc_numeracao = tbc_numeracaoRepository.getTbc_numeracao(parametro, ano);
        return tbc_numeracao;
    }
}
