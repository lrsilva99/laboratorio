package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_genero;
import com.labotech.lims.service.Tbc_generoService;
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
 * REST controller for managing Tbc_genero.
 */
@RestController
@RequestMapping("/api")
public class Tbc_generoResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_generoResource.class);
        
    @Inject
    private Tbc_generoService tbc_generoService;

    /**
     * POST  /tbc-generos : Create a new tbc_genero.
     *
     * @param tbc_genero the tbc_genero to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_genero, or with status 400 (Bad Request) if the tbc_genero has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-generos")
    @Timed
    public ResponseEntity<Tbc_genero> createTbc_genero(@Valid @RequestBody Tbc_genero tbc_genero) throws URISyntaxException {
        log.debug("REST request to save Tbc_genero : {}", tbc_genero);
        if (tbc_genero.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_genero", "idexists", "A new tbc_genero cannot already have an ID")).body(null);
        }
        Tbc_genero result = tbc_generoService.save(tbc_genero);
        return ResponseEntity.created(new URI("/api/tbc-generos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_genero", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-generos : Updates an existing tbc_genero.
     *
     * @param tbc_genero the tbc_genero to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_genero,
     * or with status 400 (Bad Request) if the tbc_genero is not valid,
     * or with status 500 (Internal Server Error) if the tbc_genero couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-generos")
    @Timed
    public ResponseEntity<Tbc_genero> updateTbc_genero(@Valid @RequestBody Tbc_genero tbc_genero) throws URISyntaxException {
        log.debug("REST request to update Tbc_genero : {}", tbc_genero);
        if (tbc_genero.getId() == null) {
            return createTbc_genero(tbc_genero);
        }
        Tbc_genero result = tbc_generoService.save(tbc_genero);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_genero", tbc_genero.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-generos : get all the tbc_generos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_generos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-generos")
    @Timed
    public ResponseEntity<List<Tbc_genero>> getAllTbc_generos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_generos");
        Page<Tbc_genero> page = tbc_generoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-generos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-generos/:id : get the "id" tbc_genero.
     *
     * @param id the id of the tbc_genero to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_genero, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-generos/{id}")
    @Timed
    public ResponseEntity<Tbc_genero> getTbc_genero(@PathVariable Long id) {
        log.debug("REST request to get Tbc_genero : {}", id);
        Tbc_genero tbc_genero = tbc_generoService.findOne(id);
        return Optional.ofNullable(tbc_genero)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-generos/:id : delete the "id" tbc_genero.
     *
     * @param id the id of the tbc_genero to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-generos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_genero(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_genero : {}", id);
        tbc_generoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_genero", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-generos?query=:query : search for the tbc_genero corresponding
     * to the query.
     *
     * @param query the query of the tbc_genero search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-generos")
    @Timed
    public ResponseEntity<List<Tbc_genero>> searchTbc_generos(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_generos for query {}", query);
        Page<Tbc_genero> page = tbc_generoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-generos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
