package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_status;
import com.labotech.lims.service.Tbc_statusService;
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
 * REST controller for managing Tbc_status.
 */
@RestController
@RequestMapping("/api")
public class Tbc_statusResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_statusResource.class);
        
    @Inject
    private Tbc_statusService tbc_statusService;

    /**
     * POST  /tbc-statuses : Create a new tbc_status.
     *
     * @param tbc_status the tbc_status to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_status, or with status 400 (Bad Request) if the tbc_status has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-statuses")
    @Timed
    public ResponseEntity<Tbc_status> createTbc_status(@Valid @RequestBody Tbc_status tbc_status) throws URISyntaxException {
        log.debug("REST request to save Tbc_status : {}", tbc_status);
        if (tbc_status.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_status", "idexists", "A new tbc_status cannot already have an ID")).body(null);
        }
        Tbc_status result = tbc_statusService.save(tbc_status);
        return ResponseEntity.created(new URI("/api/tbc-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_status", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-statuses : Updates an existing tbc_status.
     *
     * @param tbc_status the tbc_status to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_status,
     * or with status 400 (Bad Request) if the tbc_status is not valid,
     * or with status 500 (Internal Server Error) if the tbc_status couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-statuses")
    @Timed
    public ResponseEntity<Tbc_status> updateTbc_status(@Valid @RequestBody Tbc_status tbc_status) throws URISyntaxException {
        log.debug("REST request to update Tbc_status : {}", tbc_status);
        if (tbc_status.getId() == null) {
            return createTbc_status(tbc_status);
        }
        Tbc_status result = tbc_statusService.save(tbc_status);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_status", tbc_status.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-statuses : get all the tbc_statuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_statuses in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-statuses")
    @Timed
    public ResponseEntity<List<Tbc_status>> getAllTbc_statuses(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_statuses");
        Page<Tbc_status> page = tbc_statusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-statuses/:id : get the "id" tbc_status.
     *
     * @param id the id of the tbc_status to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_status, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-statuses/{id}")
    @Timed
    public ResponseEntity<Tbc_status> getTbc_status(@PathVariable Long id) {
        log.debug("REST request to get Tbc_status : {}", id);
        Tbc_status tbc_status = tbc_statusService.findOne(id);
        return Optional.ofNullable(tbc_status)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-statuses/:id : delete the "id" tbc_status.
     *
     * @param id the id of the tbc_status to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_status(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_status : {}", id);
        tbc_statusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_status", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-statuses?query=:query : search for the tbc_status corresponding
     * to the query.
     *
     * @param query the query of the tbc_status search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-statuses")
    @Timed
    public ResponseEntity<List<Tbc_status>> searchTbc_statuses(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_statuses for query {}", query);
        Page<Tbc_status> page = tbc_statusService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
