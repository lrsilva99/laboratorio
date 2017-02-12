package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_formulario;
import com.labotech.lims.service.Tbc_formularioService;
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
 * REST controller for managing Tbc_formulario.
 */
@RestController
@RequestMapping("/api")
public class Tbc_formularioResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_formularioResource.class);

    @Inject
    private Tbc_formularioService tbc_formularioService;

    /**
     * POST  /tbc-formularios : Create a new tbc_formulario.
     *
     * @param tbc_formulario the tbc_formulario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_formulario, or with status 400 (Bad Request) if the tbc_formulario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-formularios")
    @Timed
    public ResponseEntity<Tbc_formulario> createTbc_formulario(@Valid @RequestBody Tbc_formulario tbc_formulario) throws URISyntaxException {
        log.debug("REST request to save Tbc_formulario : {}", tbc_formulario);
        if (tbc_formulario.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_formulario", "idexists", "A new tbc_formulario cannot already have an ID")).body(null);
        }
        Tbc_formulario result = tbc_formularioService.save(tbc_formulario);
        return ResponseEntity.created(new URI("/api/tbc-formularios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_formulario", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-formularios : Updates an existing tbc_formulario.
     *
     * @param tbc_formulario the tbc_formulario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_formulario,
     * or with status 400 (Bad Request) if the tbc_formulario is not valid,
     * or with status 500 (Internal Server Error) if the tbc_formulario couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-formularios")
    @Timed
    public ResponseEntity<Tbc_formulario> updateTbc_formulario(@Valid @RequestBody Tbc_formulario tbc_formulario) throws URISyntaxException {
        log.debug("REST request to update Tbc_formulario : {}", tbc_formulario);
        if (tbc_formulario.getId() == null) {
            return createTbc_formulario(tbc_formulario);
        }
        Tbc_formulario result = tbc_formularioService.save(tbc_formulario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_formulario", tbc_formulario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-formularios : get all the tbc_formularios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_formularios in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-formularios")
    @Timed
    public ResponseEntity<List<Tbc_formulario>> getAllTbc_formularios(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_formularios");
        Page<Tbc_formulario> page = tbc_formularioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-formularios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-formularios/:id : get the "id" tbc_formulario.
     *
     * @param id the id of the tbc_formulario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_formulario, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-formularios/{id}")
    @Timed
    public ResponseEntity<Tbc_formulario> getTbc_formulario(@PathVariable Long id) {
        log.debug("REST request to get Tbc_formulario : {}", id);
        Tbc_formulario tbc_formulario = tbc_formularioService.findOne(id);
        return Optional.ofNullable(tbc_formulario)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-formularios/:id : delete the "id" tbc_formulario.
     *
     * @param id the id of the tbc_formulario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-formularios/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_formulario(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_formulario : {}", id);
        tbc_formularioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_formulario", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-formularios?query=:query : search for the tbc_formulario corresponding
     * to the query.
     *
     * @param query the query of the tbc_formulario search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-formularios")
    @Timed
    public ResponseEntity<List<Tbc_formulario>> searchTbc_formularios(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_formularios for query {}", query);
        Page<Tbc_formulario> page = tbc_formularioService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-formularios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
