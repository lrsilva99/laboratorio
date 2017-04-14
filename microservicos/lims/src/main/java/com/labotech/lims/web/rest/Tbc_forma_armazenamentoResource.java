package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_forma_armazenamento;
import com.labotech.lims.service.Tbc_forma_armazenamentoService;
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
 * REST controller for managing Tbc_forma_armazenamento.
 */
@RestController
@RequestMapping("/api")
public class Tbc_forma_armazenamentoResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_forma_armazenamentoResource.class);
        
    @Inject
    private Tbc_forma_armazenamentoService tbc_forma_armazenamentoService;

    /**
     * POST  /tbc-forma-armazenamentos : Create a new tbc_forma_armazenamento.
     *
     * @param tbc_forma_armazenamento the tbc_forma_armazenamento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_forma_armazenamento, or with status 400 (Bad Request) if the tbc_forma_armazenamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-forma-armazenamentos")
    @Timed
    public ResponseEntity<Tbc_forma_armazenamento> createTbc_forma_armazenamento(@Valid @RequestBody Tbc_forma_armazenamento tbc_forma_armazenamento) throws URISyntaxException {
        log.debug("REST request to save Tbc_forma_armazenamento : {}", tbc_forma_armazenamento);
        if (tbc_forma_armazenamento.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_forma_armazenamento", "idexists", "A new tbc_forma_armazenamento cannot already have an ID")).body(null);
        }
        Tbc_forma_armazenamento result = tbc_forma_armazenamentoService.save(tbc_forma_armazenamento);
        return ResponseEntity.created(new URI("/api/tbc-forma-armazenamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_forma_armazenamento", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-forma-armazenamentos : Updates an existing tbc_forma_armazenamento.
     *
     * @param tbc_forma_armazenamento the tbc_forma_armazenamento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_forma_armazenamento,
     * or with status 400 (Bad Request) if the tbc_forma_armazenamento is not valid,
     * or with status 500 (Internal Server Error) if the tbc_forma_armazenamento couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-forma-armazenamentos")
    @Timed
    public ResponseEntity<Tbc_forma_armazenamento> updateTbc_forma_armazenamento(@Valid @RequestBody Tbc_forma_armazenamento tbc_forma_armazenamento) throws URISyntaxException {
        log.debug("REST request to update Tbc_forma_armazenamento : {}", tbc_forma_armazenamento);
        if (tbc_forma_armazenamento.getId() == null) {
            return createTbc_forma_armazenamento(tbc_forma_armazenamento);
        }
        Tbc_forma_armazenamento result = tbc_forma_armazenamentoService.save(tbc_forma_armazenamento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_forma_armazenamento", tbc_forma_armazenamento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-forma-armazenamentos : get all the tbc_forma_armazenamentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_forma_armazenamentos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-forma-armazenamentos")
    @Timed
    public ResponseEntity<List<Tbc_forma_armazenamento>> getAllTbc_forma_armazenamentos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_forma_armazenamentos");
        Page<Tbc_forma_armazenamento> page = tbc_forma_armazenamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-forma-armazenamentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-forma-armazenamentos/:id : get the "id" tbc_forma_armazenamento.
     *
     * @param id the id of the tbc_forma_armazenamento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_forma_armazenamento, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-forma-armazenamentos/{id}")
    @Timed
    public ResponseEntity<Tbc_forma_armazenamento> getTbc_forma_armazenamento(@PathVariable Long id) {
        log.debug("REST request to get Tbc_forma_armazenamento : {}", id);
        Tbc_forma_armazenamento tbc_forma_armazenamento = tbc_forma_armazenamentoService.findOne(id);
        return Optional.ofNullable(tbc_forma_armazenamento)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-forma-armazenamentos/:id : delete the "id" tbc_forma_armazenamento.
     *
     * @param id the id of the tbc_forma_armazenamento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-forma-armazenamentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_forma_armazenamento(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_forma_armazenamento : {}", id);
        tbc_forma_armazenamentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_forma_armazenamento", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-forma-armazenamentos?query=:query : search for the tbc_forma_armazenamento corresponding
     * to the query.
     *
     * @param query the query of the tbc_forma_armazenamento search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-forma-armazenamentos")
    @Timed
    public ResponseEntity<List<Tbc_forma_armazenamento>> searchTbc_forma_armazenamentos(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_forma_armazenamentos for query {}", query);
        Page<Tbc_forma_armazenamento> page = tbc_forma_armazenamentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-forma-armazenamentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
