package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_frases_opcoes;
import com.labotech.lims.service.Tbc_frases_opcoesService;
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
 * REST controller for managing Tbc_frases_opcoes.
 */
@RestController
@RequestMapping("/api")
public class Tbc_frases_opcoesResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_frases_opcoesResource.class);

    @Inject
    private Tbc_frases_opcoesService tbc_frases_opcoesService;

    /**
     * POST  /tbc-frases-opcoes : Create a new tbc_frases_opcoes.
     *
     * @param tbc_frases_opcoes the tbc_frases_opcoes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_frases_opcoes, or with status 400 (Bad Request) if the tbc_frases_opcoes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-frases-opcoes")
    @Timed
    public ResponseEntity<Tbc_frases_opcoes> createTbc_frases_opcoes(@Valid @RequestBody Tbc_frases_opcoes tbc_frases_opcoes) throws URISyntaxException {
        log.debug("REST request to save Tbc_frases_opcoes : {}", tbc_frases_opcoes);
        tbc_frases_opcoes.setRemovido(false);
        if (tbc_frases_opcoes.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_frases_opcoes", "idexists", "A new tbc_frases_opcoes cannot already have an ID")).body(null);
        }
        Tbc_frases_opcoes result = tbc_frases_opcoesService.save(tbc_frases_opcoes);
        return ResponseEntity.created(new URI("/api/tbc-frases-opcoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_frases_opcoes", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-frases-opcoes : Updates an existing tbc_frases_opcoes.
     *
     * @param tbc_frases_opcoes the tbc_frases_opcoes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_frases_opcoes,
     * or with status 400 (Bad Request) if the tbc_frases_opcoes is not valid,
     * or with status 500 (Internal Server Error) if the tbc_frases_opcoes couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-frases-opcoes")
    @Timed
    public ResponseEntity<Tbc_frases_opcoes> updateTbc_frases_opcoes(@Valid @RequestBody Tbc_frases_opcoes tbc_frases_opcoes) throws URISyntaxException {
        log.debug("REST request to update Tbc_frases_opcoes : {}", tbc_frases_opcoes);
        if (tbc_frases_opcoes.getId() == null) {
            return createTbc_frases_opcoes(tbc_frases_opcoes);
        }
        Tbc_frases_opcoes result = tbc_frases_opcoesService.save(tbc_frases_opcoes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_frases_opcoes", tbc_frases_opcoes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-frases-opcoes : get all the tbc_frases_opcoes.
     * @param idFrase the pagination information
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_frases_opcoes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-frases-opcoes")
    @Timed
    public ResponseEntity<List<Tbc_frases_opcoes>> getAllTbc_frases_opcoes(@RequestParam(value = "idFrase") @ApiParam Long idFrase, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_frases_opcoes");
        Page<Tbc_frases_opcoes> page = tbc_frases_opcoesService.findAll(idFrase, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-frases-opcoes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-frases-opcoes/:id : get the "id" tbc_frases_opcoes.
     *
     * @param id the id of the tbc_frases_opcoes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_frases_opcoes, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-frases-opcoes/{id}")
    @Timed
    public ResponseEntity<Tbc_frases_opcoes> getTbc_frases_opcoes(@PathVariable Long id) {
        log.debug("REST request to get Tbc_frases_opcoes : {}", id);
        Tbc_frases_opcoes tbc_frases_opcoes = tbc_frases_opcoesService.findOne(id);
        return Optional.ofNullable(tbc_frases_opcoes)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-frases-opcoes/:id : delete the "id" tbc_frases_opcoes.
     *
     * @param id the id of the tbc_frases_opcoes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-frases-opcoes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_frases_opcoes(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_frases_opcoes : {}", id);
        tbc_frases_opcoesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_frases_opcoes", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-frases-opcoes?query=:query : search for the tbc_frases_opcoes corresponding
     * to the query.
     *
     * @param query the query of the tbc_frases_opcoes search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-frases-opcoes")
    @Timed
    public ResponseEntity<List<Tbc_frases_opcoes>> searchTbc_frases_opcoes(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_frases_opcoes for query {}", query);
        Page<Tbc_frases_opcoes> page = tbc_frases_opcoesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-frases-opcoes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-frases-opcoes/:id : get the "id" tbc_frases_opcoes.
     *
     * @param id the id of the tbc_frases_opcoes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_frases_opcoes, or with status 404 (Not Found)
     */
    @GetMapping("/frases-opcoes/{id}")
    @Timed
    public ResponseEntity<List<Tbc_frases_opcoes>> getAll(@PathVariable Long id, @ApiParam Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_frases_opcoes");
        Page<Tbc_frases_opcoes> page = tbc_frases_opcoesService.findAll(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/frases-opcoes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
