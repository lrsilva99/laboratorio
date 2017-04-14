package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbr_analiseService;
import com.labotech.lims.domain.Tbr_analise;
import com.labotech.lims.repository.Tbr_analiseRepository;
import com.labotech.lims.repository.search.Tbr_analiseSearchRepository;
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
 * Service Implementation for managing Tbr_analise.
 */
@Service
@Transactional
public class Tbr_analiseServiceImpl implements Tbr_analiseService{

    private final Logger log = LoggerFactory.getLogger(Tbr_analiseServiceImpl.class);

    @Inject
    private Tbr_analiseRepository tbr_analiseRepository;

    @Inject
    private Tbr_analiseSearchRepository tbr_analiseSearchRepository;

    /**
     * Save a tbr_analise.
     *
     * @param tbr_analise the entity to save
     * @return the persisted entity
     */
    public Tbr_analise save(Tbr_analise tbr_analise) {
        log.debug("Request to save Tbr_analise : {}", tbr_analise);
        Tbr_analise result = tbr_analiseRepository.save(tbr_analise);
        tbr_analiseSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbr_analises.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbr_analise> findAll(Long id_Amostra, Pageable pageable) {
        log.debug("Request to get all Tbr_analises");
        Page<Tbr_analise> result = tbr_analiseRepository.findAllForAmostra(id_Amostra,pageable);
        return result;
    }

    @Override
    public List<Tbr_analise> findAllList(Long id_Amostra) {
        return tbr_analiseRepository.findAllList(id_Amostra);
    }

    /**
     *  Get one tbr_analise by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tbr_analise findOne(Long id) {
        log.debug("Request to get Tbr_analise : {}", id);
        Tbr_analise tbr_analise = tbr_analiseRepository.findOne(id);
        return tbr_analise;
    }

    /**
     *  Delete the  tbr_analise by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbr_analise : {}", id);
        tbr_analiseRepository.delete(id);
        tbr_analiseSearchRepository.delete(id);
    }

    /**
     * Search for the tbr_analise corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbr_analise> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tbr_analises for query {}", query);
        Page<Tbr_analise> result = tbr_analiseSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
