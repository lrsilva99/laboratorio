package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_plano_teste_analise;
import com.labotech.lims.service.Tbc_plano_teste_analiseService;
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
 * REST controller for managing Tbc_plano_teste_analise.
 */
@RestController
@RequestMapping("/api")
public class Tbc_plano_teste_analiseResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_plano_teste_analiseResource.class);

    @Inject
    private Tbc_plano_teste_analiseService tbc_plano_teste_analiseService;

    /**
     * POST  /tbc-plano-teste-analises : Create a new tbc_plano_teste_analise.
     *
     * @param tbc_plano_teste_analise the tbc_plano_teste_analise to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_plano_teste_analise, or with status 400 (Bad Request) if the tbc_plano_teste_analise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-plano-teste-analises")
    @Timed
    public ResponseEntity<Tbc_plano_teste_analise> createTbc_plano_teste_analise(@Valid @RequestBody Tbc_plano_teste_analise tbc_plano_teste_analise) throws URISyntaxException {
        log.debug("REST request to save Tbc_plano_teste_analise : {}", tbc_plano_teste_analise);
        if (tbc_plano_teste_analise.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_plano_teste_analise", "idexists", "A new tbc_plano_teste_analise cannot already have an ID")).body(null);
        }
        Tbc_plano_teste_analise result = tbc_plano_teste_analiseService.save(tbc_plano_teste_analise);
        return ResponseEntity.created(new URI("/api/tbc-plano-teste-analises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_plano_teste_analise", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-plano-teste-analises : Updates an existing tbc_plano_teste_analise.
     *
     * @param tbc_plano_teste_analise the tbc_plano_teste_analise to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_plano_teste_analise,
     * or with status 400 (Bad Request) if the tbc_plano_teste_analise is not valid,
     * or with status 500 (Internal Server Error) if the tbc_plano_teste_analise couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-plano-teste-analises")
    @Timed
    public ResponseEntity<Tbc_plano_teste_analise> updateTbc_plano_teste_analise(@Valid @RequestBody Tbc_plano_teste_analise tbc_plano_teste_analise) throws URISyntaxException {
        log.debug("REST request to update Tbc_plano_teste_analise : {}", tbc_plano_teste_analise);
        if (tbc_plano_teste_analise.getId() == null) {
            return createTbc_plano_teste_analise(tbc_plano_teste_analise);
        }
        Tbc_plano_teste_analise result = tbc_plano_teste_analiseService.save(tbc_plano_teste_analise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_plano_teste_analise", tbc_plano_teste_analise.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-plano-teste-analises : get all the tbc_plano_teste_analises.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_plano_teste_analises in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-plano-teste-analises")
    @Timed
    public ResponseEntity<List<Tbc_plano_teste_analise>> getAllTbc_plano_teste_analises(@RequestParam(value = "idPlanoTeste") @ApiParam Long idPlanoTeste,@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_plano_teste_analises");
        Page<Tbc_plano_teste_analise> page = tbc_plano_teste_analiseService.findAll(idPlanoTeste,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-plano-teste-analises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-plano-teste-analises/:id : get the "id" tbc_plano_teste_analise.
     *
     * @param id the id of the tbc_plano_teste_analise to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_plano_teste_analise, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-plano-teste-analises/{id}")
    @Timed
    public ResponseEntity<Tbc_plano_teste_analise> getTbc_plano_teste_analise(@PathVariable Long id) {
        log.debug("REST request to get Tbc_plano_teste_analise : {}", id);
        Tbc_plano_teste_analise tbc_plano_teste_analise = tbc_plano_teste_analiseService.findOne(id);
        return Optional.ofNullable(tbc_plano_teste_analise)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-plano-teste-analises/:id : delete the "id" tbc_plano_teste_analise.
     *
     * @param id the id of the tbc_plano_teste_analise to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-plano-teste-analises/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_plano_teste_analise(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_plano_teste_analise : {}", id);
        tbc_plano_teste_analiseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_plano_teste_analise", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-plano-teste-analises?query=:query : search for the tbc_plano_teste_analise corresponding
     * to the query.
     *
     * @param query the query of the tbc_plano_teste_analise search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-plano-teste-analises")
    @Timed
    public ResponseEntity<List<Tbc_plano_teste_analise>> searchTbc_plano_teste_analises(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_plano_teste_analises for query {}", query);
        Page<Tbc_plano_teste_analise>  page = tbc_plano_teste_analiseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-plano-teste-analises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
