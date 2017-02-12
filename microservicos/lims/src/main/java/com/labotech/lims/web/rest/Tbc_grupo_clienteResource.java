package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_grupo_cliente;
import com.labotech.lims.service.Tbc_grupo_clienteService;
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
 * REST controller for managing Tbc_grupo_cliente.
 */
@RestController
@RequestMapping("/api")
public class Tbc_grupo_clienteResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_grupo_clienteResource.class);

    @Inject
    private Tbc_grupo_clienteService tbc_grupo_clienteService;

    /**
     * POST  /tbc-grupo-clientes : Create a new tbc_grupo_cliente.
     *
     * @param tbc_grupo_cliente the tbc_grupo_cliente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_grupo_cliente, or with status 400 (Bad Request) if the tbc_grupo_cliente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-grupo-clientes")
    @Timed
    public ResponseEntity<Tbc_grupo_cliente> createTbc_grupo_cliente(@Valid @RequestBody Tbc_grupo_cliente tbc_grupo_cliente) throws URISyntaxException {
        log.debug("REST request to save Tbc_grupo_cliente : {}", tbc_grupo_cliente);
        if (tbc_grupo_cliente.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_grupo_cliente", "idexists", "A new tbc_grupo_cliente cannot already have an ID")).body(null);
        }
        Tbc_grupo_cliente result = tbc_grupo_clienteService.save(tbc_grupo_cliente);
        return ResponseEntity.created(new URI("/api/tbc-grupo-clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_grupo_cliente", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-grupo-clientes : Updates an existing tbc_grupo_cliente.
     *
     * @param tbc_grupo_cliente the tbc_grupo_cliente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_grupo_cliente,
     * or with status 400 (Bad Request) if the tbc_grupo_cliente is not valid,
     * or with status 500 (Internal Server Error) if the tbc_grupo_cliente couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-grupo-clientes")
    @Timed
    public ResponseEntity<Tbc_grupo_cliente> updateTbc_grupo_cliente(@Valid @RequestBody Tbc_grupo_cliente tbc_grupo_cliente) throws URISyntaxException {
        log.debug("REST request to update Tbc_grupo_cliente : {}", tbc_grupo_cliente);
        if (tbc_grupo_cliente.getId() == null) {
            return createTbc_grupo_cliente(tbc_grupo_cliente);
        }
        Tbc_grupo_cliente result = tbc_grupo_clienteService.save(tbc_grupo_cliente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_grupo_cliente", tbc_grupo_cliente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-grupo-clientes : get all the tbc_grupo_clientes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_grupo_clientes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-grupo-clientes")
    @Timed
    public ResponseEntity<List<Tbc_grupo_cliente>> getAllTbc_grupo_clientes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_grupo_clientes");
        Page<Tbc_grupo_cliente> page = tbc_grupo_clienteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-grupo-clientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-grupo-clientes/:id : get the "id" tbc_grupo_cliente.
     *
     * @param id the id of the tbc_grupo_cliente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_grupo_cliente, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-grupo-clientes/{id}")
    @Timed
    public ResponseEntity<Tbc_grupo_cliente> getTbc_grupo_cliente(@PathVariable Long id) {
        log.debug("REST request to get Tbc_grupo_cliente : {}", id);
        Tbc_grupo_cliente tbc_grupo_cliente = tbc_grupo_clienteService.findOne(id);
        return Optional.ofNullable(tbc_grupo_cliente)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-grupo-clientes/:id : delete the "id" tbc_grupo_cliente.
     *
     * @param id the id of the tbc_grupo_cliente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-grupo-clientes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_grupo_cliente(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_grupo_cliente : {}", id);
        tbc_grupo_clienteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_grupo_cliente", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-grupo-clientes?query=:query : search for the tbc_grupo_cliente corresponding
     * to the query.
     *
     * @param query the query of the tbc_grupo_cliente search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-grupo-clientes")
    @Timed
    public ResponseEntity<List<Tbc_grupo_cliente>> searchTbc_grupo_clientes(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_grupo_clientes for query {}", query);
        Page<Tbc_grupo_cliente> page = null;
        if (query.contains("@r@") || query.contains("@R@") ) {
            String param =  query.replaceAll("@r@","").replaceAll("@R@","");
            page = tbc_grupo_clienteService.search(param,true, pageable);
        }
        else
            page = tbc_grupo_clienteService.search(query, false,pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-grupo-clientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
