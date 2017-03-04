package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_matriz;
import com.labotech.lims.service.Tbc_matrizService;
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
 * REST controller for managing Tbc_matriz.
 */
@RestController
@RequestMapping("/api")
public class Tbc_matrizResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_matrizResource.class);
        
    @Inject
    private Tbc_matrizService tbc_matrizService;

    /**
     * POST  /tbc-matrizs : Create a new tbc_matriz.
     *
     * @param tbc_matriz the tbc_matriz to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_matriz, or with status 400 (Bad Request) if the tbc_matriz has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-matrizs")
    @Timed
    public ResponseEntity<Tbc_matriz> createTbc_matriz(@Valid @RequestBody Tbc_matriz tbc_matriz) throws URISyntaxException {
        log.debug("REST request to save Tbc_matriz : {}", tbc_matriz);
        if (tbc_matriz.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_matriz", "idexists", "A new tbc_matriz cannot already have an ID")).body(null);
        }
        Tbc_matriz result = tbc_matrizService.save(tbc_matriz);
        return ResponseEntity.created(new URI("/api/tbc-matrizs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_matriz", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-matrizs : Updates an existing tbc_matriz.
     *
     * @param tbc_matriz the tbc_matriz to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_matriz,
     * or with status 400 (Bad Request) if the tbc_matriz is not valid,
     * or with status 500 (Internal Server Error) if the tbc_matriz couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-matrizs")
    @Timed
    public ResponseEntity<Tbc_matriz> updateTbc_matriz(@Valid @RequestBody Tbc_matriz tbc_matriz) throws URISyntaxException {
        log.debug("REST request to update Tbc_matriz : {}", tbc_matriz);
        if (tbc_matriz.getId() == null) {
            return createTbc_matriz(tbc_matriz);
        }
        Tbc_matriz result = tbc_matrizService.save(tbc_matriz);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_matriz", tbc_matriz.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-matrizs : get all the tbc_matrizs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_matrizs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-matrizs")
    @Timed
    public ResponseEntity<List<Tbc_matriz>> getAllTbc_matrizs(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_matrizs");
        Page<Tbc_matriz> page = tbc_matrizService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-matrizs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-matrizs/:id : get the "id" tbc_matriz.
     *
     * @param id the id of the tbc_matriz to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_matriz, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-matrizs/{id}")
    @Timed
    public ResponseEntity<Tbc_matriz> getTbc_matriz(@PathVariable Long id) {
        log.debug("REST request to get Tbc_matriz : {}", id);
        Tbc_matriz tbc_matriz = tbc_matrizService.findOne(id);
        return Optional.ofNullable(tbc_matriz)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-matrizs/:id : delete the "id" tbc_matriz.
     *
     * @param id the id of the tbc_matriz to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-matrizs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_matriz(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_matriz : {}", id);
        tbc_matrizService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_matriz", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-matrizs?query=:query : search for the tbc_matriz corresponding
     * to the query.
     *
     * @param query the query of the tbc_matriz search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-matrizs")
    @Timed
    public ResponseEntity<List<Tbc_matriz>> searchTbc_matrizs(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_matrizs for query {}", query);
        Page<Tbc_matriz> page = tbc_matrizService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-matrizs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
