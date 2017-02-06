package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_frases;
import com.labotech.lims.service.Tbc_frasesService;
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
 * REST controller for managing Tbc_frases.
 */
@RestController
@RequestMapping("/api")
public class Tbc_frasesResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_frasesResource.class);
        
    @Inject
    private Tbc_frasesService tbc_frasesService;

    /**
     * POST  /tbc-frases : Create a new tbc_frases.
     *
     * @param tbc_frases the tbc_frases to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_frases, or with status 400 (Bad Request) if the tbc_frases has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-frases")
    @Timed
    public ResponseEntity<Tbc_frases> createTbc_frases(@Valid @RequestBody Tbc_frases tbc_frases) throws URISyntaxException {
        log.debug("REST request to save Tbc_frases : {}", tbc_frases);
        if (tbc_frases.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_frases", "idexists", "A new tbc_frases cannot already have an ID")).body(null);
        }
        Tbc_frases result = tbc_frasesService.save(tbc_frases);
        return ResponseEntity.created(new URI("/api/tbc-frases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_frases", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-frases : Updates an existing tbc_frases.
     *
     * @param tbc_frases the tbc_frases to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_frases,
     * or with status 400 (Bad Request) if the tbc_frases is not valid,
     * or with status 500 (Internal Server Error) if the tbc_frases couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-frases")
    @Timed
    public ResponseEntity<Tbc_frases> updateTbc_frases(@Valid @RequestBody Tbc_frases tbc_frases) throws URISyntaxException {
        log.debug("REST request to update Tbc_frases : {}", tbc_frases);
        if (tbc_frases.getId() == null) {
            return createTbc_frases(tbc_frases);
        }
        Tbc_frases result = tbc_frasesService.save(tbc_frases);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_frases", tbc_frases.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-frases : get all the tbc_frases.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_frases in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-frases")
    @Timed
    public ResponseEntity<List<Tbc_frases>> getAllTbc_frases(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_frases");
        Page<Tbc_frases> page = tbc_frasesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-frases");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-frases/:id : get the "id" tbc_frases.
     *
     * @param id the id of the tbc_frases to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_frases, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-frases/{id}")
    @Timed
    public ResponseEntity<Tbc_frases> getTbc_frases(@PathVariable Long id) {
        log.debug("REST request to get Tbc_frases : {}", id);
        Tbc_frases tbc_frases = tbc_frasesService.findOne(id);
        return Optional.ofNullable(tbc_frases)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-frases/:id : delete the "id" tbc_frases.
     *
     * @param id the id of the tbc_frases to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-frases/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_frases(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_frases : {}", id);
        tbc_frasesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_frases", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-frases?query=:query : search for the tbc_frases corresponding
     * to the query.
     *
     * @param query the query of the tbc_frases search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-frases")
    @Timed
    public ResponseEntity<List<Tbc_frases>> searchTbc_frases(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_frases for query {}", query);
        Page<Tbc_frases> page = tbc_frasesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-frases");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
