package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_analises;
import com.labotech.lims.service.Tbc_analisesService;
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
 * REST controller for managing Tbc_analises.
 */
@RestController
@RequestMapping("/api")
public class Tbc_analisesResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_analisesResource.class);

    @Inject
    private Tbc_analisesService tbc_analisesService;

    /**
     * POST  /tbc-analises : Create a new tbc_analises.
     *
     * @param tbc_analises the tbc_analises to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_analises, or with status 400 (Bad Request) if the tbc_analises has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-analises")
    @Timed
    public ResponseEntity<Tbc_analises> createTbc_analises(@Valid @RequestBody Tbc_analises tbc_analises) throws URISyntaxException {
        log.debug("REST request to save Tbc_analises : {}", tbc_analises);
        if (tbc_analises.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_analises", "idexists", "A new tbc_analises cannot already have an ID")).body(null);
        }
        Tbc_analises result = tbc_analisesService.save(tbc_analises);
        return ResponseEntity.created(new URI("/api/tbc-analises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_analises", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-analises : Updates an existing tbc_analises.
     *
     * @param tbc_analises the tbc_analises to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_analises,
     * or with status 400 (Bad Request) if the tbc_analises is not valid,
     * or with status 500 (Internal Server Error) if the tbc_analises couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-analises")
    @Timed
    public ResponseEntity<Tbc_analises> updateTbc_analises(@Valid @RequestBody Tbc_analises tbc_analises) throws URISyntaxException {
        log.debug("REST request to update Tbc_analises : {}", tbc_analises);
        if (tbc_analises.getId() == null) {
            return createTbc_analises(tbc_analises);
        }
        Tbc_analises result = tbc_analisesService.save(tbc_analises);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_analises", tbc_analises.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-analises : get all the tbc_analises.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_analises in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-analises")
    @Timed
    public ResponseEntity<List<Tbc_analises>> getAllTbc_analises(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_analises");
        Page<Tbc_analises> page = tbc_analisesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-analises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-analises/:id : get the "id" tbc_analises.
     *
     * @param id the id of the tbc_analises to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_analises, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-analises/{id}")
    @Timed
    public ResponseEntity<Tbc_analises> getTbc_analises(@PathVariable Long id) {
        log.debug("REST request to get Tbc_analises : {}", id);
        Tbc_analises tbc_analises = tbc_analisesService.findOne(id);
        return Optional.ofNullable(tbc_analises)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-analises/:id : delete the "id" tbc_analises.
     *
     * @param id the id of the tbc_analises to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-analises/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_analises(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_analises : {}", id);
        tbc_analisesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_analises", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-analises?query=:query : search for the tbc_analises corresponding
     * to the query.
     *
     * @param query the query of the tbc_analises search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-analises")
    @Timed
    public ResponseEntity<List<Tbc_analises>> searchTbc_analises(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_analises for query {}", query);
        Page<Tbc_analises> page = null;
        if (query.contains("@r@") || query.contains("@R@") ) {
            String param =  query.replaceAll("@r@","").replaceAll("@R@","");
            page = tbc_analisesService.search(param, true, pageable);
        }else
            page = tbc_analisesService.search(query, false, pageable);

        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-analises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
