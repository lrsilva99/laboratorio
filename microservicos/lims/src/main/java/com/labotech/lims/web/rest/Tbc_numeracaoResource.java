package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_numeracao;
import com.labotech.lims.service.Tbc_numeracaoService;
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
 * REST controller for managing Tbc_numeracao.
 */
@RestController
@RequestMapping("/api")
public class Tbc_numeracaoResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_numeracaoResource.class);
        
    @Inject
    private Tbc_numeracaoService tbc_numeracaoService;

    /**
     * POST  /tbc-numeracaos : Create a new tbc_numeracao.
     *
     * @param tbc_numeracao the tbc_numeracao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_numeracao, or with status 400 (Bad Request) if the tbc_numeracao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-numeracaos")
    @Timed
    public ResponseEntity<Tbc_numeracao> createTbc_numeracao(@Valid @RequestBody Tbc_numeracao tbc_numeracao) throws URISyntaxException {
        log.debug("REST request to save Tbc_numeracao : {}", tbc_numeracao);
        if (tbc_numeracao.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_numeracao", "idexists", "A new tbc_numeracao cannot already have an ID")).body(null);
        }
        Tbc_numeracao result = tbc_numeracaoService.save(tbc_numeracao);
        return ResponseEntity.created(new URI("/api/tbc-numeracaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_numeracao", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-numeracaos : Updates an existing tbc_numeracao.
     *
     * @param tbc_numeracao the tbc_numeracao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_numeracao,
     * or with status 400 (Bad Request) if the tbc_numeracao is not valid,
     * or with status 500 (Internal Server Error) if the tbc_numeracao couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-numeracaos")
    @Timed
    public ResponseEntity<Tbc_numeracao> updateTbc_numeracao(@Valid @RequestBody Tbc_numeracao tbc_numeracao) throws URISyntaxException {
        log.debug("REST request to update Tbc_numeracao : {}", tbc_numeracao);
        if (tbc_numeracao.getId() == null) {
            return createTbc_numeracao(tbc_numeracao);
        }
        Tbc_numeracao result = tbc_numeracaoService.save(tbc_numeracao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_numeracao", tbc_numeracao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-numeracaos : get all the tbc_numeracaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_numeracaos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-numeracaos")
    @Timed
    public ResponseEntity<List<Tbc_numeracao>> getAllTbc_numeracaos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_numeracaos");
        Page<Tbc_numeracao> page = tbc_numeracaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-numeracaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-numeracaos/:id : get the "id" tbc_numeracao.
     *
     * @param id the id of the tbc_numeracao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_numeracao, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-numeracaos/{id}")
    @Timed
    public ResponseEntity<Tbc_numeracao> getTbc_numeracao(@PathVariable Long id) {
        log.debug("REST request to get Tbc_numeracao : {}", id);
        Tbc_numeracao tbc_numeracao = tbc_numeracaoService.findOne(id);
        return Optional.ofNullable(tbc_numeracao)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-numeracaos/:id : delete the "id" tbc_numeracao.
     *
     * @param id the id of the tbc_numeracao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-numeracaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_numeracao(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_numeracao : {}", id);
        tbc_numeracaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_numeracao", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-numeracaos?query=:query : search for the tbc_numeracao corresponding
     * to the query.
     *
     * @param query the query of the tbc_numeracao search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-numeracaos")
    @Timed
    public ResponseEntity<List<Tbc_numeracao>> searchTbc_numeracaos(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_numeracaos for query {}", query);
        Page<Tbc_numeracao> page = tbc_numeracaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-numeracaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
