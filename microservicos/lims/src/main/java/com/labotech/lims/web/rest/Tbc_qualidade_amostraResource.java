package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_qualidade_amostra;
import com.labotech.lims.service.Tbc_qualidade_amostraService;
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
 * REST controller for managing Tbc_qualidade_amostra.
 */
@RestController
@RequestMapping("/api")
public class Tbc_qualidade_amostraResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_qualidade_amostraResource.class);
        
    @Inject
    private Tbc_qualidade_amostraService tbc_qualidade_amostraService;

    /**
     * POST  /tbc-qualidade-amostras : Create a new tbc_qualidade_amostra.
     *
     * @param tbc_qualidade_amostra the tbc_qualidade_amostra to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_qualidade_amostra, or with status 400 (Bad Request) if the tbc_qualidade_amostra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-qualidade-amostras")
    @Timed
    public ResponseEntity<Tbc_qualidade_amostra> createTbc_qualidade_amostra(@Valid @RequestBody Tbc_qualidade_amostra tbc_qualidade_amostra) throws URISyntaxException {
        log.debug("REST request to save Tbc_qualidade_amostra : {}", tbc_qualidade_amostra);
        if (tbc_qualidade_amostra.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_qualidade_amostra", "idexists", "A new tbc_qualidade_amostra cannot already have an ID")).body(null);
        }
        Tbc_qualidade_amostra result = tbc_qualidade_amostraService.save(tbc_qualidade_amostra);
        return ResponseEntity.created(new URI("/api/tbc-qualidade-amostras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_qualidade_amostra", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-qualidade-amostras : Updates an existing tbc_qualidade_amostra.
     *
     * @param tbc_qualidade_amostra the tbc_qualidade_amostra to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_qualidade_amostra,
     * or with status 400 (Bad Request) if the tbc_qualidade_amostra is not valid,
     * or with status 500 (Internal Server Error) if the tbc_qualidade_amostra couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-qualidade-amostras")
    @Timed
    public ResponseEntity<Tbc_qualidade_amostra> updateTbc_qualidade_amostra(@Valid @RequestBody Tbc_qualidade_amostra tbc_qualidade_amostra) throws URISyntaxException {
        log.debug("REST request to update Tbc_qualidade_amostra : {}", tbc_qualidade_amostra);
        if (tbc_qualidade_amostra.getId() == null) {
            return createTbc_qualidade_amostra(tbc_qualidade_amostra);
        }
        Tbc_qualidade_amostra result = tbc_qualidade_amostraService.save(tbc_qualidade_amostra);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_qualidade_amostra", tbc_qualidade_amostra.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-qualidade-amostras : get all the tbc_qualidade_amostras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_qualidade_amostras in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-qualidade-amostras")
    @Timed
    public ResponseEntity<List<Tbc_qualidade_amostra>> getAllTbc_qualidade_amostras(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_qualidade_amostras");
        Page<Tbc_qualidade_amostra> page = tbc_qualidade_amostraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-qualidade-amostras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-qualidade-amostras/:id : get the "id" tbc_qualidade_amostra.
     *
     * @param id the id of the tbc_qualidade_amostra to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_qualidade_amostra, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-qualidade-amostras/{id}")
    @Timed
    public ResponseEntity<Tbc_qualidade_amostra> getTbc_qualidade_amostra(@PathVariable Long id) {
        log.debug("REST request to get Tbc_qualidade_amostra : {}", id);
        Tbc_qualidade_amostra tbc_qualidade_amostra = tbc_qualidade_amostraService.findOne(id);
        return Optional.ofNullable(tbc_qualidade_amostra)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-qualidade-amostras/:id : delete the "id" tbc_qualidade_amostra.
     *
     * @param id the id of the tbc_qualidade_amostra to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-qualidade-amostras/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_qualidade_amostra(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_qualidade_amostra : {}", id);
        tbc_qualidade_amostraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_qualidade_amostra", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-qualidade-amostras?query=:query : search for the tbc_qualidade_amostra corresponding
     * to the query.
     *
     * @param query the query of the tbc_qualidade_amostra search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-qualidade-amostras")
    @Timed
    public ResponseEntity<List<Tbc_qualidade_amostra>> searchTbc_qualidade_amostras(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_qualidade_amostras for query {}", query);
        Page<Tbc_qualidade_amostra> page = tbc_qualidade_amostraService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-qualidade-amostras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
