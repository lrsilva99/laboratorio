package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_analises_componente;
import com.labotech.lims.service.Tbc_analises_componenteService;
import com.labotech.lims.web.rest.util.HeaderUtil;
import com.labotech.lims.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tbc_analises_componente.
 */
@RestController
@RequestMapping("/api")
public class Tbc_analises_componenteResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_analises_componenteResource.class);

    @Inject
    private Tbc_analises_componenteService tbc_analises_componenteService;

    /**
     * POST  /tbc-analises-componentes : Create a new tbc_analises_componente.
     *
     * @param tbc_analises_componente the tbc_analises_componente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_analises_componente, or with status 400 (Bad Request) if the tbc_analises_componente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-analises-componentes")
    @Timed
    public ResponseEntity<Tbc_analises_componente> createTbc_analises_componente(@Valid @RequestBody Tbc_analises_componente tbc_analises_componente) throws URISyntaxException {
        log.debug("REST request to save Tbc_analises_componente : {}", tbc_analises_componente);
        if (tbc_analises_componente.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_analises_componente", "idexists", "A new tbc_analises_componente cannot already have an ID")).body(null);
        }
        Tbc_analises_componente result = tbc_analises_componenteService.save(tbc_analises_componente);
        return ResponseEntity.created(new URI("/api/tbc-analises-componentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_analises_componente", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-analises-componentes : Updates an existing tbc_analises_componente.
     *
     * @param tbc_analises_componente the tbc_analises_componente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_analises_componente,
     * or with status 400 (Bad Request) if the tbc_analises_componente is not valid,
     * or with status 500 (Internal Server Error) if the tbc_analises_componente couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-analises-componentes")
    @Timed
    public ResponseEntity<Tbc_analises_componente> updateTbc_analises_componente(@Valid @RequestBody Tbc_analises_componente tbc_analises_componente) throws URISyntaxException {
        log.debug("REST request to update Tbc_analises_componente : {}", tbc_analises_componente);
        if (tbc_analises_componente.getId() == null) {
            return createTbc_analises_componente(tbc_analises_componente);
        }
        Tbc_analises_componente result = tbc_analises_componenteService.save(tbc_analises_componente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_analises_componente", tbc_analises_componente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-analises-componentes : get all the tbc_analises_componentes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_analises_componentes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-analises-componentes")
    @Timed
    public ResponseEntity<List<Tbc_analises_componente>> getAllTbc_analises_componentes(@RequestParam(value = "idAnalise") @ApiParam Long idAnalise, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_analises_componentes");
        Page<Tbc_analises_componente> page = tbc_analises_componenteService.findAll(idAnalise, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-analises-componentes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-analises-componentes/:id : get the "id" tbc_analises_componente.
     *
     * @param id the id of the tbc_analises_componente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_analises_componente, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-analises-componentes/{id}")
    @Timed
    public ResponseEntity<Tbc_analises_componente> getTbc_analises_componente(@PathVariable Long id) {
        log.debug("REST request to get Tbc_analises_componente : {}", id);
        Tbc_analises_componente tbc_analises_componente = tbc_analises_componenteService.findOne(id);
        return Optional.ofNullable(tbc_analises_componente)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-analises-componentes/:id : delete the "id" tbc_analises_componente.
     *
     * @param id the id of the tbc_analises_componente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-analises-componentes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_analises_componente(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_analises_componente : {}", id);
        tbc_analises_componenteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_analises_componente", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-analises-componentes?query=:query : search for the tbc_analises_componente corresponding
     * to the query.
     *
     * @param query the query of the tbc_analises_componente search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-analises-componentes")
    @Timed
    public ResponseEntity<List<Tbc_analises_componente>> searchTbc_analises_componentes(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_analises_componentes for query {}", query);
        Page<Tbc_analises_componente> page = tbc_analises_componenteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-analises-componentes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
