package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_cooperativa;
import com.labotech.lims.service.Tbc_cooperativaService;
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
 * REST controller for managing Tbc_cooperativa.
 */
@RestController
@RequestMapping("/api")
public class Tbc_cooperativaResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_cooperativaResource.class);
        
    @Inject
    private Tbc_cooperativaService tbc_cooperativaService;

    /**
     * POST  /tbc-cooperativas : Create a new tbc_cooperativa.
     *
     * @param tbc_cooperativa the tbc_cooperativa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_cooperativa, or with status 400 (Bad Request) if the tbc_cooperativa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-cooperativas")
    @Timed
    public ResponseEntity<Tbc_cooperativa> createTbc_cooperativa(@Valid @RequestBody Tbc_cooperativa tbc_cooperativa) throws URISyntaxException {
        log.debug("REST request to save Tbc_cooperativa : {}", tbc_cooperativa);
        if (tbc_cooperativa.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_cooperativa", "idexists", "A new tbc_cooperativa cannot already have an ID")).body(null);
        }
        Tbc_cooperativa result = tbc_cooperativaService.save(tbc_cooperativa);
        return ResponseEntity.created(new URI("/api/tbc-cooperativas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_cooperativa", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-cooperativas : Updates an existing tbc_cooperativa.
     *
     * @param tbc_cooperativa the tbc_cooperativa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_cooperativa,
     * or with status 400 (Bad Request) if the tbc_cooperativa is not valid,
     * or with status 500 (Internal Server Error) if the tbc_cooperativa couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-cooperativas")
    @Timed
    public ResponseEntity<Tbc_cooperativa> updateTbc_cooperativa(@Valid @RequestBody Tbc_cooperativa tbc_cooperativa) throws URISyntaxException {
        log.debug("REST request to update Tbc_cooperativa : {}", tbc_cooperativa);
        if (tbc_cooperativa.getId() == null) {
            return createTbc_cooperativa(tbc_cooperativa);
        }
        Tbc_cooperativa result = tbc_cooperativaService.save(tbc_cooperativa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_cooperativa", tbc_cooperativa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-cooperativas : get all the tbc_cooperativas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_cooperativas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-cooperativas")
    @Timed
    public ResponseEntity<List<Tbc_cooperativa>> getAllTbc_cooperativas(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_cooperativas");
        Page<Tbc_cooperativa> page = tbc_cooperativaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-cooperativas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-cooperativas/:id : get the "id" tbc_cooperativa.
     *
     * @param id the id of the tbc_cooperativa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_cooperativa, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-cooperativas/{id}")
    @Timed
    public ResponseEntity<Tbc_cooperativa> getTbc_cooperativa(@PathVariable Long id) {
        log.debug("REST request to get Tbc_cooperativa : {}", id);
        Tbc_cooperativa tbc_cooperativa = tbc_cooperativaService.findOne(id);
        return Optional.ofNullable(tbc_cooperativa)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-cooperativas/:id : delete the "id" tbc_cooperativa.
     *
     * @param id the id of the tbc_cooperativa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-cooperativas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_cooperativa(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_cooperativa : {}", id);
        tbc_cooperativaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_cooperativa", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-cooperativas?query=:query : search for the tbc_cooperativa corresponding
     * to the query.
     *
     * @param query the query of the tbc_cooperativa search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-cooperativas")
    @Timed
    public ResponseEntity<List<Tbc_cooperativa>> searchTbc_cooperativas(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_cooperativas for query {}", query);
        Page<Tbc_cooperativa> page = tbc_cooperativaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-cooperativas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
