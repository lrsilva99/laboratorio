package com.labotech.lims.service.impl;

import com.labotech.lims.service.Tbc_lab_tercerizadoService;
import com.labotech.lims.domain.Tbc_lab_tercerizado;
import com.labotech.lims.repository.Tbc_lab_tercerizadoRepository;
import com.labotech.lims.repository.search.Tbc_lab_tercerizadoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
/**
 * Service Implementation for managing Tbc_lab_tercerizado.
 */
@Service
@Transactional
public class Tbc_lab_tercerizadoServiceImpl implements Tbc_lab_tercerizadoService{

    private final Logger log = LoggerFactory.getLogger(Tbc_lab_tercerizadoServiceImpl.class);

    @Inject
    private Tbc_lab_tercerizadoRepository tbc_lab_tercerizadoRepository;

    @Inject
    private Tbc_lab_tercerizadoSearchRepository tbc_lab_tercerizadoSearchRepository;

    /**
     * Save a tbc_lab_tercerizado.
     *
     * @param tbc_lab_tercerizado the entity to save
     * @return the persisted entity
     */
    public Tbc_lab_tercerizado save(Tbc_lab_tercerizado tbc_lab_tercerizado) {
        log.debug("Request to save Tbc_lab_tercerizado : {}", tbc_lab_tercerizado);
        Tbc_lab_tercerizado result = tbc_lab_tercerizadoRepository.save(tbc_lab_tercerizado);
        tbc_lab_tercerizadoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tbc_lab_tercerizados.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_lab_tercerizado> findAll(Pageable pageable) {
        log.debug("Request to get all Tbc_lab_tercerizados");
        Page<Tbc_lab_tercerizado> result = tbc_lab_tercerizadoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tbc_lab_tercerizado by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tbc_lab_tercerizado findOne(Long id) {
        log.debug("Request to get Tbc_lab_tercerizado : {}", id);
        Tbc_lab_tercerizado tbc_lab_tercerizado = tbc_lab_tercerizadoRepository.findOne(id);
        return tbc_lab_tercerizado;
    }

    /**
     *  Delete the  tbc_lab_tercerizado by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tbc_lab_tercerizado : {}", id);
        tbc_lab_tercerizadoRepository.delete(id);
        tbc_lab_tercerizadoSearchRepository.delete(id);
    }

    /**
     * Search for the tbc_lab_tercerizado corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tbc_lab_tercerizado> search(String query, Boolean removido, Pageable pageable) {
        log.debug("Request to search for a page of Tbc_lab_tercerizados for query {}", query);
        Page<Tbc_lab_tercerizado> result = tbc_lab_tercerizadoRepository.search(query, removido, pageable);
        return result;
    }
}
