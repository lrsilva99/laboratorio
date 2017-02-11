package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_tipo_campo;
import com.labotech.lims.service.Tbc_tipo_campoService;
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
 * REST controller for managing Tbc_tipo_campo.
 */
@RestController
@RequestMapping("/api")
public class Tbc_tipo_campoResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_tipo_campoResource.class);
        
    @Inject
    private Tbc_tipo_campoService tbc_tipo_campoService;

    /**
     * POST  /tbc-tipo-campos : Create a new tbc_tipo_campo.
     *
     * @param tbc_tipo_campo the tbc_tipo_campo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_tipo_campo, or with status 400 (Bad Request) if the tbc_tipo_campo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-tipo-campos")
    @Timed
    public ResponseEntity<Tbc_tipo_campo> createTbc_tipo_campo(@Valid @RequestBody Tbc_tipo_campo tbc_tipo_campo) throws URISyntaxException {
        log.debug("REST request to save Tbc_tipo_campo : {}", tbc_tipo_campo);
        if (tbc_tipo_campo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_tipo_campo", "idexists", "A new tbc_tipo_campo cannot already have an ID")).body(null);
        }
        Tbc_tipo_campo result = tbc_tipo_campoService.save(tbc_tipo_campo);
        return ResponseEntity.created(new URI("/api/tbc-tipo-campos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_tipo_campo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-tipo-campos : Updates an existing tbc_tipo_campo.
     *
     * @param tbc_tipo_campo the tbc_tipo_campo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_tipo_campo,
     * or with status 400 (Bad Request) if the tbc_tipo_campo is not valid,
     * or with status 500 (Internal Server Error) if the tbc_tipo_campo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-tipo-campos")
    @Timed
    public ResponseEntity<Tbc_tipo_campo> updateTbc_tipo_campo(@Valid @RequestBody Tbc_tipo_campo tbc_tipo_campo) throws URISyntaxException {
        log.debug("REST request to update Tbc_tipo_campo : {}", tbc_tipo_campo);
        if (tbc_tipo_campo.getId() == null) {
            return createTbc_tipo_campo(tbc_tipo_campo);
        }
        Tbc_tipo_campo result = tbc_tipo_campoService.save(tbc_tipo_campo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_tipo_campo", tbc_tipo_campo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-tipo-campos : get all the tbc_tipo_campos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_tipo_campos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-tipo-campos")
    @Timed
    public ResponseEntity<List<Tbc_tipo_campo>> getAllTbc_tipo_campos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_tipo_campos");
        Page<Tbc_tipo_campo> page = tbc_tipo_campoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-tipo-campos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-tipo-campos/:id : get the "id" tbc_tipo_campo.
     *
     * @param id the id of the tbc_tipo_campo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_tipo_campo, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-tipo-campos/{id}")
    @Timed
    public ResponseEntity<Tbc_tipo_campo> getTbc_tipo_campo(@PathVariable Long id) {
        log.debug("REST request to get Tbc_tipo_campo : {}", id);
        Tbc_tipo_campo tbc_tipo_campo = tbc_tipo_campoService.findOne(id);
        return Optional.ofNullable(tbc_tipo_campo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-tipo-campos/:id : delete the "id" tbc_tipo_campo.
     *
     * @param id the id of the tbc_tipo_campo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-tipo-campos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_tipo_campo(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_tipo_campo : {}", id);
        tbc_tipo_campoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_tipo_campo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-tipo-campos?query=:query : search for the tbc_tipo_campo corresponding
     * to the query.
     *
     * @param query the query of the tbc_tipo_campo search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-tipo-campos")
    @Timed
    public ResponseEntity<List<Tbc_tipo_campo>> searchTbc_tipo_campos(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_tipo_campos for query {}", query);
        Page<Tbc_tipo_campo> page = tbc_tipo_campoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-tipo-campos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
