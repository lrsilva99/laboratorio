package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.service.Tbc_instituicaoService;
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
 * REST controller for managing Tbc_instituicao.
 */
@RestController
@RequestMapping("/api")
public class Tbc_instituicaoResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_instituicaoResource.class);
        
    @Inject
    private Tbc_instituicaoService tbc_instituicaoService;

    /**
     * POST  /tbc-instituicaos : Create a new tbc_instituicao.
     *
     * @param tbc_instituicao the tbc_instituicao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_instituicao, or with status 400 (Bad Request) if the tbc_instituicao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-instituicaos")
    @Timed
    public ResponseEntity<Tbc_instituicao> createTbc_instituicao(@Valid @RequestBody Tbc_instituicao tbc_instituicao) throws URISyntaxException {
        log.debug("REST request to save Tbc_instituicao : {}", tbc_instituicao);
        if (tbc_instituicao.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_instituicao", "idexists", "A new tbc_instituicao cannot already have an ID")).body(null);
        }
        Tbc_instituicao result = tbc_instituicaoService.save(tbc_instituicao);
        return ResponseEntity.created(new URI("/api/tbc-instituicaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_instituicao", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-instituicaos : Updates an existing tbc_instituicao.
     *
     * @param tbc_instituicao the tbc_instituicao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_instituicao,
     * or with status 400 (Bad Request) if the tbc_instituicao is not valid,
     * or with status 500 (Internal Server Error) if the tbc_instituicao couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-instituicaos")
    @Timed
    public ResponseEntity<Tbc_instituicao> updateTbc_instituicao(@Valid @RequestBody Tbc_instituicao tbc_instituicao) throws URISyntaxException {
        log.debug("REST request to update Tbc_instituicao : {}", tbc_instituicao);
        if (tbc_instituicao.getId() == null) {
            return createTbc_instituicao(tbc_instituicao);
        }
        Tbc_instituicao result = tbc_instituicaoService.save(tbc_instituicao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_instituicao", tbc_instituicao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-instituicaos : get all the tbc_instituicaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_instituicaos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-instituicaos")
    @Timed
    public ResponseEntity<List<Tbc_instituicao>> getAllTbc_instituicaos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_instituicaos");
        Page<Tbc_instituicao> page = tbc_instituicaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-instituicaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-instituicaos/:id : get the "id" tbc_instituicao.
     *
     * @param id the id of the tbc_instituicao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_instituicao, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-instituicaos/{id}")
    @Timed
    public ResponseEntity<Tbc_instituicao> getTbc_instituicao(@PathVariable Long id) {
        log.debug("REST request to get Tbc_instituicao : {}", id);
        Tbc_instituicao tbc_instituicao = tbc_instituicaoService.findOne(id);
        return Optional.ofNullable(tbc_instituicao)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-instituicaos/:id : delete the "id" tbc_instituicao.
     *
     * @param id the id of the tbc_instituicao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-instituicaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_instituicao(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_instituicao : {}", id);
        tbc_instituicaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_instituicao", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-instituicaos?query=:query : search for the tbc_instituicao corresponding
     * to the query.
     *
     * @param query the query of the tbc_instituicao search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-instituicaos")
    @Timed
    public ResponseEntity<List<Tbc_instituicao>> searchTbc_instituicaos(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_instituicaos for query {}", query);
        Page<Tbc_instituicao> page = tbc_instituicaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-instituicaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
