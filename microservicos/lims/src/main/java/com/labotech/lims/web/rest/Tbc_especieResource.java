package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_especie;
import com.labotech.lims.service.Tbc_especieService;
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
 * REST controller for managing Tbc_especie.
 */
@RestController
@RequestMapping("/api")
public class Tbc_especieResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_especieResource.class);
        
    @Inject
    private Tbc_especieService tbc_especieService;

    /**
     * POST  /tbc-especies : Create a new tbc_especie.
     *
     * @param tbc_especie the tbc_especie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_especie, or with status 400 (Bad Request) if the tbc_especie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-especies")
    @Timed
    public ResponseEntity<Tbc_especie> createTbc_especie(@Valid @RequestBody Tbc_especie tbc_especie) throws URISyntaxException {
        log.debug("REST request to save Tbc_especie : {}", tbc_especie);
        if (tbc_especie.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_especie", "idexists", "A new tbc_especie cannot already have an ID")).body(null);
        }
        Tbc_especie result = tbc_especieService.save(tbc_especie);
        return ResponseEntity.created(new URI("/api/tbc-especies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_especie", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-especies : Updates an existing tbc_especie.
     *
     * @param tbc_especie the tbc_especie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_especie,
     * or with status 400 (Bad Request) if the tbc_especie is not valid,
     * or with status 500 (Internal Server Error) if the tbc_especie couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-especies")
    @Timed
    public ResponseEntity<Tbc_especie> updateTbc_especie(@Valid @RequestBody Tbc_especie tbc_especie) throws URISyntaxException {
        log.debug("REST request to update Tbc_especie : {}", tbc_especie);
        if (tbc_especie.getId() == null) {
            return createTbc_especie(tbc_especie);
        }
        Tbc_especie result = tbc_especieService.save(tbc_especie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_especie", tbc_especie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-especies : get all the tbc_especies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_especies in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-especies")
    @Timed
    public ResponseEntity<List<Tbc_especie>> getAllTbc_especies(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_especies");
        Page<Tbc_especie> page = tbc_especieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-especies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-especies/:id : get the "id" tbc_especie.
     *
     * @param id the id of the tbc_especie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_especie, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-especies/{id}")
    @Timed
    public ResponseEntity<Tbc_especie> getTbc_especie(@PathVariable Long id) {
        log.debug("REST request to get Tbc_especie : {}", id);
        Tbc_especie tbc_especie = tbc_especieService.findOne(id);
        return Optional.ofNullable(tbc_especie)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-especies/:id : delete the "id" tbc_especie.
     *
     * @param id the id of the tbc_especie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-especies/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_especie(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_especie : {}", id);
        tbc_especieService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_especie", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-especies?query=:query : search for the tbc_especie corresponding
     * to the query.
     *
     * @param query the query of the tbc_especie search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-especies")
    @Timed
    public ResponseEntity<List<Tbc_especie>> searchTbc_especies(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_especies for query {}", query);
        Page<Tbc_especie> page = tbc_especieService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-especies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
