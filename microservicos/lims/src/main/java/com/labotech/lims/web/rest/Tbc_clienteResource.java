package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_cliente;
import com.labotech.lims.service.Tbc_clienteService;
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
 * REST controller for managing Tbc_cliente.
 */
@RestController
@RequestMapping("/api")
public class Tbc_clienteResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_clienteResource.class);
        
    @Inject
    private Tbc_clienteService tbc_clienteService;

    /**
     * POST  /tbc-clientes : Create a new tbc_cliente.
     *
     * @param tbc_cliente the tbc_cliente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_cliente, or with status 400 (Bad Request) if the tbc_cliente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-clientes")
    @Timed
    public ResponseEntity<Tbc_cliente> createTbc_cliente(@Valid @RequestBody Tbc_cliente tbc_cliente) throws URISyntaxException {
        log.debug("REST request to save Tbc_cliente : {}", tbc_cliente);
        if (tbc_cliente.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_cliente", "idexists", "A new tbc_cliente cannot already have an ID")).body(null);
        }
        Tbc_cliente result = tbc_clienteService.save(tbc_cliente);
        return ResponseEntity.created(new URI("/api/tbc-clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_cliente", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-clientes : Updates an existing tbc_cliente.
     *
     * @param tbc_cliente the tbc_cliente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_cliente,
     * or with status 400 (Bad Request) if the tbc_cliente is not valid,
     * or with status 500 (Internal Server Error) if the tbc_cliente couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-clientes")
    @Timed
    public ResponseEntity<Tbc_cliente> updateTbc_cliente(@Valid @RequestBody Tbc_cliente tbc_cliente) throws URISyntaxException {
        log.debug("REST request to update Tbc_cliente : {}", tbc_cliente);
        if (tbc_cliente.getId() == null) {
            return createTbc_cliente(tbc_cliente);
        }
        Tbc_cliente result = tbc_clienteService.save(tbc_cliente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_cliente", tbc_cliente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-clientes : get all the tbc_clientes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_clientes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-clientes")
    @Timed
    public ResponseEntity<List<Tbc_cliente>> getAllTbc_clientes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_clientes");
        Page<Tbc_cliente> page = tbc_clienteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-clientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-clientes/:id : get the "id" tbc_cliente.
     *
     * @param id the id of the tbc_cliente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_cliente, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-clientes/{id}")
    @Timed
    public ResponseEntity<Tbc_cliente> getTbc_cliente(@PathVariable Long id) {
        log.debug("REST request to get Tbc_cliente : {}", id);
        Tbc_cliente tbc_cliente = tbc_clienteService.findOne(id);
        return Optional.ofNullable(tbc_cliente)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-clientes/:id : delete the "id" tbc_cliente.
     *
     * @param id the id of the tbc_cliente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-clientes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_cliente(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_cliente : {}", id);
        tbc_clienteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_cliente", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-clientes?query=:query : search for the tbc_cliente corresponding
     * to the query.
     *
     * @param query the query of the tbc_cliente search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-clientes")
    @Timed
    public ResponseEntity<List<Tbc_cliente>> searchTbc_clientes(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_clientes for query {}", query);
        Page<Tbc_cliente> page = tbc_clienteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-clientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
