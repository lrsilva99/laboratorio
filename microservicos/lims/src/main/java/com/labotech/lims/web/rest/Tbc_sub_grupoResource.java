package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_sub_grupo;
import com.labotech.lims.service.Tbc_sub_grupoService;
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
 * REST controller for managing Tbc_sub_grupo.
 */
@RestController
@RequestMapping("/api")
public class Tbc_sub_grupoResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_sub_grupoResource.class);
        
    @Inject
    private Tbc_sub_grupoService tbc_sub_grupoService;

    /**
     * POST  /tbc-sub-grupos : Create a new tbc_sub_grupo.
     *
     * @param tbc_sub_grupo the tbc_sub_grupo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_sub_grupo, or with status 400 (Bad Request) if the tbc_sub_grupo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-sub-grupos")
    @Timed
    public ResponseEntity<Tbc_sub_grupo> createTbc_sub_grupo(@Valid @RequestBody Tbc_sub_grupo tbc_sub_grupo) throws URISyntaxException {
        log.debug("REST request to save Tbc_sub_grupo : {}", tbc_sub_grupo);
        if (tbc_sub_grupo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_sub_grupo", "idexists", "A new tbc_sub_grupo cannot already have an ID")).body(null);
        }
        Tbc_sub_grupo result = tbc_sub_grupoService.save(tbc_sub_grupo);
        return ResponseEntity.created(new URI("/api/tbc-sub-grupos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_sub_grupo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-sub-grupos : Updates an existing tbc_sub_grupo.
     *
     * @param tbc_sub_grupo the tbc_sub_grupo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_sub_grupo,
     * or with status 400 (Bad Request) if the tbc_sub_grupo is not valid,
     * or with status 500 (Internal Server Error) if the tbc_sub_grupo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-sub-grupos")
    @Timed
    public ResponseEntity<Tbc_sub_grupo> updateTbc_sub_grupo(@Valid @RequestBody Tbc_sub_grupo tbc_sub_grupo) throws URISyntaxException {
        log.debug("REST request to update Tbc_sub_grupo : {}", tbc_sub_grupo);
        if (tbc_sub_grupo.getId() == null) {
            return createTbc_sub_grupo(tbc_sub_grupo);
        }
        Tbc_sub_grupo result = tbc_sub_grupoService.save(tbc_sub_grupo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_sub_grupo", tbc_sub_grupo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-sub-grupos : get all the tbc_sub_grupos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_sub_grupos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-sub-grupos")
    @Timed
    public ResponseEntity<List<Tbc_sub_grupo>> getAllTbc_sub_grupos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_sub_grupos");
        Page<Tbc_sub_grupo> page = tbc_sub_grupoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-sub-grupos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-sub-grupos/:id : get the "id" tbc_sub_grupo.
     *
     * @param id the id of the tbc_sub_grupo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_sub_grupo, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-sub-grupos/{id}")
    @Timed
    public ResponseEntity<Tbc_sub_grupo> getTbc_sub_grupo(@PathVariable Long id) {
        log.debug("REST request to get Tbc_sub_grupo : {}", id);
        Tbc_sub_grupo tbc_sub_grupo = tbc_sub_grupoService.findOne(id);
        return Optional.ofNullable(tbc_sub_grupo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-sub-grupos/:id : delete the "id" tbc_sub_grupo.
     *
     * @param id the id of the tbc_sub_grupo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-sub-grupos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_sub_grupo(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_sub_grupo : {}", id);
        tbc_sub_grupoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_sub_grupo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-sub-grupos?query=:query : search for the tbc_sub_grupo corresponding
     * to the query.
     *
     * @param query the query of the tbc_sub_grupo search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-sub-grupos")
    @Timed
    public ResponseEntity<List<Tbc_sub_grupo>> searchTbc_sub_grupos(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_sub_grupos for query {}", query);
        Page<Tbc_sub_grupo> page = tbc_sub_grupoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-sub-grupos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
