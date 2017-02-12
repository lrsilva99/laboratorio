package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_tipo_cadastro;
import com.labotech.lims.service.Tbc_tipo_cadastroService;
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
 * REST controller for managing Tbc_tipo_cadastro.
 */
@RestController
@RequestMapping("/api")
public class Tbc_tipo_cadastroResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_tipo_cadastroResource.class);

    @Inject
    private Tbc_tipo_cadastroService tbc_tipo_cadastroService;

    /**
     * POST  /tbc-tipo-cadastros : Create a new tbc_tipo_cadastro.
     *
     * @param tbc_tipo_cadastro the tbc_tipo_cadastro to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_tipo_cadastro, or with status 400 (Bad Request) if the tbc_tipo_cadastro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-tipo-cadastros")
    @Timed
    public ResponseEntity<Tbc_tipo_cadastro> createTbc_tipo_cadastro(@Valid @RequestBody Tbc_tipo_cadastro tbc_tipo_cadastro) throws URISyntaxException {
        log.debug("REST request to save Tbc_tipo_cadastro : {}", tbc_tipo_cadastro);
        if (tbc_tipo_cadastro.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_tipo_cadastro", "idexists", "A new tbc_tipo_cadastro cannot already have an ID")).body(null);
        }
        Tbc_tipo_cadastro result = tbc_tipo_cadastroService.save(tbc_tipo_cadastro);
        return ResponseEntity.created(new URI("/api/tbc-tipo-cadastros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_tipo_cadastro", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-tipo-cadastros : Updates an existing tbc_tipo_cadastro.
     *
     * @param tbc_tipo_cadastro the tbc_tipo_cadastro to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_tipo_cadastro,
     * or with status 400 (Bad Request) if the tbc_tipo_cadastro is not valid,
     * or with status 500 (Internal Server Error) if the tbc_tipo_cadastro couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-tipo-cadastros")
    @Timed
    public ResponseEntity<Tbc_tipo_cadastro> updateTbc_tipo_cadastro(@Valid @RequestBody Tbc_tipo_cadastro tbc_tipo_cadastro) throws URISyntaxException {
        log.debug("REST request to update Tbc_tipo_cadastro : {}", tbc_tipo_cadastro);
        if (tbc_tipo_cadastro.getId() == null) {
            return createTbc_tipo_cadastro(tbc_tipo_cadastro);
        }
        Tbc_tipo_cadastro result = tbc_tipo_cadastroService.save(tbc_tipo_cadastro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_tipo_cadastro", tbc_tipo_cadastro.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-tipo-cadastros : get all the tbc_tipo_cadastros.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_tipo_cadastros in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-tipo-cadastros")
    @Timed
    public ResponseEntity<List<Tbc_tipo_cadastro>> getAllTbc_tipo_cadastros(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_tipo_cadastros");
        Page<Tbc_tipo_cadastro> page = tbc_tipo_cadastroService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-tipo-cadastros");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-tipo-cadastros/:id : get the "id" tbc_tipo_cadastro.
     *
     * @param id the id of the tbc_tipo_cadastro to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_tipo_cadastro, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-tipo-cadastros/{id}")
    @Timed
    public ResponseEntity<Tbc_tipo_cadastro> getTbc_tipo_cadastro(@PathVariable Long id) {
        log.debug("REST request to get Tbc_tipo_cadastro : {}", id);
        Tbc_tipo_cadastro tbc_tipo_cadastro = tbc_tipo_cadastroService.findOne(id);
        return Optional.ofNullable(tbc_tipo_cadastro)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-tipo-cadastros/:id : delete the "id" tbc_tipo_cadastro.
     *
     * @param id the id of the tbc_tipo_cadastro to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-tipo-cadastros/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_tipo_cadastro(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_tipo_cadastro : {}", id);
        tbc_tipo_cadastroService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_tipo_cadastro", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-tipo-cadastros?query=:query : search for the tbc_tipo_cadastro corresponding
     * to the query.
     *
     * @param query the query of the tbc_tipo_cadastro search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-tipo-cadastros")
    @Timed
    public ResponseEntity<List<Tbc_tipo_cadastro>> searchTbc_tipo_cadastros(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_tipo_cadastros for query {}", query);
        Page<Tbc_tipo_cadastro>  page = null;
        if (query.contains("@r@") || query.contains("@R@") ) {
            String param =  query.replaceAll("@r@","").replaceAll("@R@","");
            page = tbc_tipo_cadastroService.search(param, true, pageable);
        }else
            page = tbc_tipo_cadastroService.search(query, false, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-tipo-cadastros");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
