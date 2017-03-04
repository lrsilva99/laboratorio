package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_fazenda;
import com.labotech.lims.service.Tbc_fazendaService;
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
 * REST controller for managing Tbc_fazenda.
 */
@RestController
@RequestMapping("/api")
public class Tbc_fazendaResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_fazendaResource.class);
        
    @Inject
    private Tbc_fazendaService tbc_fazendaService;

    /**
     * POST  /tbc-fazendas : Create a new tbc_fazenda.
     *
     * @param tbc_fazenda the tbc_fazenda to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_fazenda, or with status 400 (Bad Request) if the tbc_fazenda has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-fazendas")
    @Timed
    public ResponseEntity<Tbc_fazenda> createTbc_fazenda(@Valid @RequestBody Tbc_fazenda tbc_fazenda) throws URISyntaxException {
        log.debug("REST request to save Tbc_fazenda : {}", tbc_fazenda);
        if (tbc_fazenda.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_fazenda", "idexists", "A new tbc_fazenda cannot already have an ID")).body(null);
        }
        Tbc_fazenda result = tbc_fazendaService.save(tbc_fazenda);
        return ResponseEntity.created(new URI("/api/tbc-fazendas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_fazenda", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-fazendas : Updates an existing tbc_fazenda.
     *
     * @param tbc_fazenda the tbc_fazenda to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_fazenda,
     * or with status 400 (Bad Request) if the tbc_fazenda is not valid,
     * or with status 500 (Internal Server Error) if the tbc_fazenda couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-fazendas")
    @Timed
    public ResponseEntity<Tbc_fazenda> updateTbc_fazenda(@Valid @RequestBody Tbc_fazenda tbc_fazenda) throws URISyntaxException {
        log.debug("REST request to update Tbc_fazenda : {}", tbc_fazenda);
        if (tbc_fazenda.getId() == null) {
            return createTbc_fazenda(tbc_fazenda);
        }
        Tbc_fazenda result = tbc_fazendaService.save(tbc_fazenda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_fazenda", tbc_fazenda.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-fazendas : get all the tbc_fazendas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_fazendas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-fazendas")
    @Timed
    public ResponseEntity<List<Tbc_fazenda>> getAllTbc_fazendas(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_fazendas");
        Page<Tbc_fazenda> page = tbc_fazendaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-fazendas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-fazendas/:id : get the "id" tbc_fazenda.
     *
     * @param id the id of the tbc_fazenda to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_fazenda, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-fazendas/{id}")
    @Timed
    public ResponseEntity<Tbc_fazenda> getTbc_fazenda(@PathVariable Long id) {
        log.debug("REST request to get Tbc_fazenda : {}", id);
        Tbc_fazenda tbc_fazenda = tbc_fazendaService.findOne(id);
        return Optional.ofNullable(tbc_fazenda)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-fazendas/:id : delete the "id" tbc_fazenda.
     *
     * @param id the id of the tbc_fazenda to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-fazendas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_fazenda(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_fazenda : {}", id);
        tbc_fazendaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_fazenda", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-fazendas?query=:query : search for the tbc_fazenda corresponding
     * to the query.
     *
     * @param query the query of the tbc_fazenda search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-fazendas")
    @Timed
    public ResponseEntity<List<Tbc_fazenda>> searchTbc_fazendas(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_fazendas for query {}", query);
        Page<Tbc_fazenda> page = tbc_fazendaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-fazendas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
