package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_plano_teste;
import com.labotech.lims.service.Tbc_plano_testeService;
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
 * REST controller for managing Tbc_plano_teste.
 */
@RestController
@RequestMapping("/api")
public class Tbc_plano_testeResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_plano_testeResource.class);

    @Inject
    private Tbc_plano_testeService tbc_plano_testeService;

    /**
     * POST  /tbc-plano-testes : Create a new tbc_plano_teste.
     *
     * @param tbc_plano_teste the tbc_plano_teste to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_plano_teste, or with status 400 (Bad Request) if the tbc_plano_teste has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-plano-testes")
    @Timed
    public ResponseEntity<Tbc_plano_teste> createTbc_plano_teste(@Valid @RequestBody Tbc_plano_teste tbc_plano_teste) throws URISyntaxException {
        log.debug("REST request to save Tbc_plano_teste : {}", tbc_plano_teste);
        if (tbc_plano_teste.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_plano_teste", "idexists", "A new tbc_plano_teste cannot already have an ID")).body(null);
        }
        Tbc_plano_teste result = tbc_plano_testeService.save(tbc_plano_teste);
        return ResponseEntity.created(new URI("/api/tbc-plano-testes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_plano_teste", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-plano-testes : Updates an existing tbc_plano_teste.
     *
     * @param tbc_plano_teste the tbc_plano_teste to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_plano_teste,
     * or with status 400 (Bad Request) if the tbc_plano_teste is not valid,
     * or with status 500 (Internal Server Error) if the tbc_plano_teste couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-plano-testes")
    @Timed
    public ResponseEntity<Tbc_plano_teste> updateTbc_plano_teste(@Valid @RequestBody Tbc_plano_teste tbc_plano_teste) throws URISyntaxException {
        log.debug("REST request to update Tbc_plano_teste : {}", tbc_plano_teste);
        if (tbc_plano_teste.getId() == null) {
            return createTbc_plano_teste(tbc_plano_teste);
        }
        Tbc_plano_teste result = tbc_plano_testeService.save(tbc_plano_teste);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_plano_teste", tbc_plano_teste.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-plano-testes : get all the tbc_plano_testes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_plano_testes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-plano-testes")
    @Timed
    public ResponseEntity<List<Tbc_plano_teste>> getAllTbc_plano_testes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_plano_testes");
        Page<Tbc_plano_teste> page = tbc_plano_testeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-plano-testes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-plano-testes/:id : get the "id" tbc_plano_teste.
     *
     * @param id the id of the tbc_plano_teste to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_plano_teste, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-plano-testes/{id}")
    @Timed
    public ResponseEntity<Tbc_plano_teste> getTbc_plano_teste(@PathVariable Long id) {
        log.debug("REST request to get Tbc_plano_teste : {}", id);
        Tbc_plano_teste tbc_plano_teste = tbc_plano_testeService.findOne(id);
        return Optional.ofNullable(tbc_plano_teste)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-plano-testes/:id : delete the "id" tbc_plano_teste.
     *
     * @param id the id of the tbc_plano_teste to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-plano-testes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_plano_teste(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_plano_teste : {}", id);
        tbc_plano_testeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_plano_teste", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-plano-testes?query=:query : search for the tbc_plano_teste corresponding
     * to the query.
     *
     * @param query the query of the tbc_plano_teste search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-plano-testes")
    @Timed
    public ResponseEntity<List<Tbc_plano_teste>> searchTbc_plano_testes(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_plano_testes for query {}", query);
        Page<Tbc_plano_teste> page = null;
        if (query.contains("@r@") || query.contains("@R@") ) {
            String param =  query.replaceAll("@removido@","").replaceAll("R","");
            page = tbc_plano_testeService.search(param, true, pageable);
        }else
            page = tbc_plano_testeService.search(query, false, pageable);

        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-plano-testes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
