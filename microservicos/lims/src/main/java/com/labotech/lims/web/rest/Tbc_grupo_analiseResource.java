package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_grupo_analise;
import com.labotech.lims.service.Tbc_grupo_analiseService;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Tbc_grupo_analise.
 */
@RestController
@RequestMapping("/api")
public class Tbc_grupo_analiseResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_grupo_analiseResource.class);
        
    @Inject
    private Tbc_grupo_analiseService tbc_grupo_analiseService;

    /**
     * POST  /tbc-grupo-analises : Create a new tbc_grupo_analise.
     *
     * @param tbc_grupo_analise the tbc_grupo_analise to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_grupo_analise, or with status 400 (Bad Request) if the tbc_grupo_analise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-grupo-analises")
    @Timed
    public ResponseEntity<Tbc_grupo_analise> createTbc_grupo_analise(@Valid @RequestBody Tbc_grupo_analise tbc_grupo_analise) throws URISyntaxException {
        log.debug("REST request to save Tbc_grupo_analise : {}", tbc_grupo_analise);
        if (tbc_grupo_analise.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_grupo_analise", "idexists", "A new tbc_grupo_analise cannot already have an ID")).body(null);
        }
        Tbc_grupo_analise result = tbc_grupo_analiseService.save(tbc_grupo_analise);
        return ResponseEntity.created(new URI("/api/tbc-grupo-analises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_grupo_analise", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-grupo-analises : Updates an existing tbc_grupo_analise.
     *
     * @param tbc_grupo_analise the tbc_grupo_analise to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_grupo_analise,
     * or with status 400 (Bad Request) if the tbc_grupo_analise is not valid,
     * or with status 500 (Internal Server Error) if the tbc_grupo_analise couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-grupo-analises")
    @Timed
    public ResponseEntity<Tbc_grupo_analise> updateTbc_grupo_analise(@Valid @RequestBody Tbc_grupo_analise tbc_grupo_analise) throws URISyntaxException {
        log.debug("REST request to update Tbc_grupo_analise : {}", tbc_grupo_analise);
        if (tbc_grupo_analise.getId() == null) {
            return createTbc_grupo_analise(tbc_grupo_analise);
        }
        Tbc_grupo_analise result = tbc_grupo_analiseService.save(tbc_grupo_analise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_grupo_analise", tbc_grupo_analise.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-grupo-analises : get all the tbc_grupo_analises.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_grupo_analises in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-grupo-analises")
    @Timed
    public ResponseEntity<List<Tbc_grupo_analise>> getAllTbc_grupo_analises(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_grupo_analises");
        Page<Tbc_grupo_analise> page = tbc_grupo_analiseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-grupo-analises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-grupo-analises/:id : get the "id" tbc_grupo_analise.
     *
     * @param id the id of the tbc_grupo_analise to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_grupo_analise, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-grupo-analises/{id}")
    @Timed
    public ResponseEntity<Tbc_grupo_analise> getTbc_grupo_analise(@PathVariable Long id) {
        log.debug("REST request to get Tbc_grupo_analise : {}", id);
        Tbc_grupo_analise tbc_grupo_analise = tbc_grupo_analiseService.findOne(id);
        return Optional.ofNullable(tbc_grupo_analise)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-grupo-analises/:id : delete the "id" tbc_grupo_analise.
     *
     * @param id the id of the tbc_grupo_analise to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-grupo-analises/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_grupo_analise(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_grupo_analise : {}", id);
        tbc_grupo_analiseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_grupo_analise", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-grupo-analises?query=:query : search for the tbc_grupo_analise corresponding
     * to the query.
     *
     * @param query the query of the tbc_grupo_analise search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-grupo-analises")
    @Timed
    public ResponseEntity<List<Tbc_grupo_analise>> searchTbc_grupo_analises(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_grupo_analises for query {}", query);
        Page<Tbc_grupo_analise> page = tbc_grupo_analiseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-grupo-analises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
