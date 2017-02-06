package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_relatorio_ensaio;
import com.labotech.lims.service.Tbc_relatorio_ensaioService;
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
 * REST controller for managing Tbc_relatorio_ensaio.
 */
@RestController
@RequestMapping("/api")
public class Tbc_relatorio_ensaioResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_relatorio_ensaioResource.class);
        
    @Inject
    private Tbc_relatorio_ensaioService tbc_relatorio_ensaioService;

    /**
     * POST  /tbc-relatorio-ensaios : Create a new tbc_relatorio_ensaio.
     *
     * @param tbc_relatorio_ensaio the tbc_relatorio_ensaio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_relatorio_ensaio, or with status 400 (Bad Request) if the tbc_relatorio_ensaio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-relatorio-ensaios")
    @Timed
    public ResponseEntity<Tbc_relatorio_ensaio> createTbc_relatorio_ensaio(@Valid @RequestBody Tbc_relatorio_ensaio tbc_relatorio_ensaio) throws URISyntaxException {
        log.debug("REST request to save Tbc_relatorio_ensaio : {}", tbc_relatorio_ensaio);
        if (tbc_relatorio_ensaio.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_relatorio_ensaio", "idexists", "A new tbc_relatorio_ensaio cannot already have an ID")).body(null);
        }
        Tbc_relatorio_ensaio result = tbc_relatorio_ensaioService.save(tbc_relatorio_ensaio);
        return ResponseEntity.created(new URI("/api/tbc-relatorio-ensaios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_relatorio_ensaio", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-relatorio-ensaios : Updates an existing tbc_relatorio_ensaio.
     *
     * @param tbc_relatorio_ensaio the tbc_relatorio_ensaio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_relatorio_ensaio,
     * or with status 400 (Bad Request) if the tbc_relatorio_ensaio is not valid,
     * or with status 500 (Internal Server Error) if the tbc_relatorio_ensaio couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-relatorio-ensaios")
    @Timed
    public ResponseEntity<Tbc_relatorio_ensaio> updateTbc_relatorio_ensaio(@Valid @RequestBody Tbc_relatorio_ensaio tbc_relatorio_ensaio) throws URISyntaxException {
        log.debug("REST request to update Tbc_relatorio_ensaio : {}", tbc_relatorio_ensaio);
        if (tbc_relatorio_ensaio.getId() == null) {
            return createTbc_relatorio_ensaio(tbc_relatorio_ensaio);
        }
        Tbc_relatorio_ensaio result = tbc_relatorio_ensaioService.save(tbc_relatorio_ensaio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_relatorio_ensaio", tbc_relatorio_ensaio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-relatorio-ensaios : get all the tbc_relatorio_ensaios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_relatorio_ensaios in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-relatorio-ensaios")
    @Timed
    public ResponseEntity<List<Tbc_relatorio_ensaio>> getAllTbc_relatorio_ensaios(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_relatorio_ensaios");
        Page<Tbc_relatorio_ensaio> page = tbc_relatorio_ensaioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-relatorio-ensaios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-relatorio-ensaios/:id : get the "id" tbc_relatorio_ensaio.
     *
     * @param id the id of the tbc_relatorio_ensaio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_relatorio_ensaio, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-relatorio-ensaios/{id}")
    @Timed
    public ResponseEntity<Tbc_relatorio_ensaio> getTbc_relatorio_ensaio(@PathVariable Long id) {
        log.debug("REST request to get Tbc_relatorio_ensaio : {}", id);
        Tbc_relatorio_ensaio tbc_relatorio_ensaio = tbc_relatorio_ensaioService.findOne(id);
        return Optional.ofNullable(tbc_relatorio_ensaio)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-relatorio-ensaios/:id : delete the "id" tbc_relatorio_ensaio.
     *
     * @param id the id of the tbc_relatorio_ensaio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-relatorio-ensaios/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_relatorio_ensaio(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_relatorio_ensaio : {}", id);
        tbc_relatorio_ensaioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_relatorio_ensaio", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-relatorio-ensaios?query=:query : search for the tbc_relatorio_ensaio corresponding
     * to the query.
     *
     * @param query the query of the tbc_relatorio_ensaio search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-relatorio-ensaios")
    @Timed
    public ResponseEntity<List<Tbc_relatorio_ensaio>> searchTbc_relatorio_ensaios(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_relatorio_ensaios for query {}", query);
        Page<Tbc_relatorio_ensaio> page = tbc_relatorio_ensaioService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-relatorio-ensaios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
