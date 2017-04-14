package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbr_analise;
import com.labotech.lims.service.Tbr_analiseService;
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
 * REST controller for managing Tbr_analise.
 */
@RestController
@RequestMapping("/api")
public class Tbr_analiseResource {

    private final Logger log = LoggerFactory.getLogger(Tbr_analiseResource.class);

    @Inject
    private Tbr_analiseService tbr_analiseService;

    /**
     * POST  /tbr-analises : Create a new tbr_analise.
     *
     * @param tbr_analise the tbr_analise to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbr_analise, or with status 400 (Bad Request) if the tbr_analise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbr-analises")
    @Timed
    public ResponseEntity<Tbr_analise> createTbr_analise(@Valid @RequestBody Tbr_analise tbr_analise) throws URISyntaxException {
        log.debug("REST request to save Tbr_analise : {}", tbr_analise);
        if (tbr_analise.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbr_analise", "idexists", "A new tbr_analise cannot already have an ID")).body(null);
        }
        Tbr_analise result = tbr_analiseService.save(tbr_analise);
        return ResponseEntity.created(new URI("/api/tbr-analises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbr_analise", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbr-analises : Updates an existing tbr_analise.
     *
     * @param tbr_analise the tbr_analise to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbr_analise,
     * or with status 400 (Bad Request) if the tbr_analise is not valid,
     * or with status 500 (Internal Server Error) if the tbr_analise couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbr-analises")
    @Timed
    public ResponseEntity<Tbr_analise> updateTbr_analise(@Valid @RequestBody Tbr_analise tbr_analise) throws URISyntaxException {
        log.debug("REST request to update Tbr_analise : {}", tbr_analise);
        if (tbr_analise.getId() == null) {
            return createTbr_analise(tbr_analise);
        }
        Tbr_analise result = tbr_analiseService.save(tbr_analise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbr_analise", tbr_analise.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbr-analises : get all the tbr_analises.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbr_analises in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbr-analises")
    @Timed
    public ResponseEntity<List<Tbr_analise>> getAllTbr_analises(@ApiParam Long id_Amostra, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbr_analises");
        log.debug("id_amostra" + id_Amostra);
        Page<Tbr_analise> page = tbr_analiseService.findAll(id_Amostra,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbr-analises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbr-analises/:id : get the "id" tbr_analise.
     *
     * @param id the id of the tbr_analise to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbr_analise, or with status 404 (Not Found)
     */
    @GetMapping("/tbr-analises/{id}")
    @Timed
    public ResponseEntity<Tbr_analise> getTbr_analise(@PathVariable Long id) {
        log.debug("REST request to get Tbr_analise : {}", id);
        Tbr_analise tbr_analise = tbr_analiseService.findOne(id);
        return Optional.ofNullable(tbr_analise)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbr-analises/:id : delete the "id" tbr_analise.
     *
     * @param id the id of the tbr_analise to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbr-analises/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbr_analise(@PathVariable Long id) {
        log.debug("REST request to delete Tbr_analise : {}", id);
        tbr_analiseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbr_analise", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbr-analises?query=:query : search for the tbr_analise corresponding
     * to the query.
     *
     * @param query the query of the tbr_analise search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbr-analises")
    @Timed
    public ResponseEntity<List<Tbr_analise>> searchTbr_analises(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbr_analises for query {}", query);
        Page<Tbr_analise> page = tbr_analiseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbr-analises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
