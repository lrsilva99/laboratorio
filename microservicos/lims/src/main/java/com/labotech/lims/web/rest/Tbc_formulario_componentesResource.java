package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_formulario_componentes;
import com.labotech.lims.service.Tbc_formulario_componentesService;
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
 * REST controller for managing Tbc_formulario_componentes.
 */
@RestController
@RequestMapping("/api")
public class Tbc_formulario_componentesResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_formulario_componentesResource.class);

    @Inject
    private Tbc_formulario_componentesService tbc_formulario_componentesService;

    /**
     * POST  /tbc-formulario-componentes : Create a new tbc_formulario_componentes.
     *
     * @param tbc_formulario_componentes the tbc_formulario_componentes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_formulario_componentes, or with status 400 (Bad Request) if the tbc_formulario_componentes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-formulario-componentes")
    @Timed
    public ResponseEntity<Tbc_formulario_componentes> createTbc_formulario_componentes(@Valid @RequestBody Tbc_formulario_componentes tbc_formulario_componentes) throws URISyntaxException {
        log.debug("REST request to save Tbc_formulario_componentes : {}", tbc_formulario_componentes);
        if (tbc_formulario_componentes.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_formulario_componentes", "idexists", "A new tbc_formulario_componentes cannot already have an ID")).body(null);
        }
        Tbc_formulario_componentes result = tbc_formulario_componentesService.save(tbc_formulario_componentes);
        return ResponseEntity.created(new URI("/api/tbc-formulario-componentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_formulario_componentes", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-formulario-componentes : Updates an existing tbc_formulario_componentes.
     *
     * @param tbc_formulario_componentes the tbc_formulario_componentes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_formulario_componentes,
     * or with status 400 (Bad Request) if the tbc_formulario_componentes is not valid,
     * or with status 500 (Internal Server Error) if the tbc_formulario_componentes couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-formulario-componentes")
    @Timed
    public ResponseEntity<Tbc_formulario_componentes> updateTbc_formulario_componentes(@Valid @RequestBody Tbc_formulario_componentes tbc_formulario_componentes) throws URISyntaxException {
        log.debug("REST request to update Tbc_formulario_componentes : {}", tbc_formulario_componentes);
        if (tbc_formulario_componentes.getId() == null) {
            return createTbc_formulario_componentes(tbc_formulario_componentes);
        }
        Tbc_formulario_componentes result = tbc_formulario_componentesService.save(tbc_formulario_componentes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_formulario_componentes", tbc_formulario_componentes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-formulario-componentes : get all the tbc_formulario_componentes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_formulario_componentes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-formulario-componentes")
    @Timed
    public ResponseEntity<List<Tbc_formulario_componentes>> getAllTbc_formulario_componentes(@RequestParam(value = "idFormulario") @ApiParam Long idFormulario,@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_formulario_componentes");
        Page<Tbc_formulario_componentes> page = tbc_formulario_componentesService.findAll(idFormulario,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-formulario-componentes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-formulario-componentes/:id : get the "id" tbc_formulario_componentes.
     *
     * @param id the id of the tbc_formulario_componentes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_formulario_componentes, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-formulario-componentes/{id}")
    @Timed
    public ResponseEntity<Tbc_formulario_componentes> getTbc_formulario_componentes(@PathVariable Long id) {
        log.debug("REST request to get Tbc_formulario_componentes : {}", id);
        Tbc_formulario_componentes tbc_formulario_componentes = tbc_formulario_componentesService.findOne(id);
        return Optional.ofNullable(tbc_formulario_componentes)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-formulario-componentes/:id : delete the "id" tbc_formulario_componentes.
     *
     * @param id the id of the tbc_formulario_componentes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-formulario-componentes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_formulario_componentes(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_formulario_componentes : {}", id);
        tbc_formulario_componentesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_formulario_componentes", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-formulario-componentes?query=:query : search for the tbc_formulario_componentes corresponding
     * to the query.
     *
     * @param query the query of the tbc_formulario_componentes search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-formulario-componentes")
    @Timed
    public ResponseEntity<List<Tbc_formulario_componentes>> searchTbc_formulario_componentes(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_formulario_componentes for query {}", query);
        Page<Tbc_formulario_componentes> page = tbc_formulario_componentesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-formulario-componentes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
