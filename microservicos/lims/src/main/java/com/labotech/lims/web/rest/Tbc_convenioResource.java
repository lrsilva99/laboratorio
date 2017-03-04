package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_convenio;
import com.labotech.lims.service.Tbc_convenioService;
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
 * REST controller for managing Tbc_convenio.
 */
@RestController
@RequestMapping("/api")
public class Tbc_convenioResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_convenioResource.class);
        
    @Inject
    private Tbc_convenioService tbc_convenioService;

    /**
     * POST  /tbc-convenios : Create a new tbc_convenio.
     *
     * @param tbc_convenio the tbc_convenio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_convenio, or with status 400 (Bad Request) if the tbc_convenio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-convenios")
    @Timed
    public ResponseEntity<Tbc_convenio> createTbc_convenio(@Valid @RequestBody Tbc_convenio tbc_convenio) throws URISyntaxException {
        log.debug("REST request to save Tbc_convenio : {}", tbc_convenio);
        if (tbc_convenio.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_convenio", "idexists", "A new tbc_convenio cannot already have an ID")).body(null);
        }
        Tbc_convenio result = tbc_convenioService.save(tbc_convenio);
        return ResponseEntity.created(new URI("/api/tbc-convenios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_convenio", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-convenios : Updates an existing tbc_convenio.
     *
     * @param tbc_convenio the tbc_convenio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_convenio,
     * or with status 400 (Bad Request) if the tbc_convenio is not valid,
     * or with status 500 (Internal Server Error) if the tbc_convenio couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-convenios")
    @Timed
    public ResponseEntity<Tbc_convenio> updateTbc_convenio(@Valid @RequestBody Tbc_convenio tbc_convenio) throws URISyntaxException {
        log.debug("REST request to update Tbc_convenio : {}", tbc_convenio);
        if (tbc_convenio.getId() == null) {
            return createTbc_convenio(tbc_convenio);
        }
        Tbc_convenio result = tbc_convenioService.save(tbc_convenio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_convenio", tbc_convenio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-convenios : get all the tbc_convenios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_convenios in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-convenios")
    @Timed
    public ResponseEntity<List<Tbc_convenio>> getAllTbc_convenios(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_convenios");
        Page<Tbc_convenio> page = tbc_convenioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-convenios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-convenios/:id : get the "id" tbc_convenio.
     *
     * @param id the id of the tbc_convenio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_convenio, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-convenios/{id}")
    @Timed
    public ResponseEntity<Tbc_convenio> getTbc_convenio(@PathVariable Long id) {
        log.debug("REST request to get Tbc_convenio : {}", id);
        Tbc_convenio tbc_convenio = tbc_convenioService.findOne(id);
        return Optional.ofNullable(tbc_convenio)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-convenios/:id : delete the "id" tbc_convenio.
     *
     * @param id the id of the tbc_convenio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-convenios/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_convenio(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_convenio : {}", id);
        tbc_convenioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_convenio", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-convenios?query=:query : search for the tbc_convenio corresponding
     * to the query.
     *
     * @param query the query of the tbc_convenio search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-convenios")
    @Timed
    public ResponseEntity<List<Tbc_convenio>> searchTbc_convenios(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_convenios for query {}", query);
        Page<Tbc_convenio> page = tbc_convenioService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-convenios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
