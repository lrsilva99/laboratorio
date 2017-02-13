package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_lab_tercerizado;
import com.labotech.lims.service.Tbc_lab_tercerizadoService;
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
 * REST controller for managing Tbc_lab_tercerizado.
 */
@RestController
@RequestMapping("/api")
public class Tbc_lab_tercerizadoResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_lab_tercerizadoResource.class);

    @Inject
    private Tbc_lab_tercerizadoService tbc_lab_tercerizadoService;

    /**
     * POST  /tbc-lab-tercerizados : Create a new tbc_lab_tercerizado.
     *
     * @param tbc_lab_tercerizado the tbc_lab_tercerizado to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_lab_tercerizado, or with status 400 (Bad Request) if the tbc_lab_tercerizado has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-lab-tercerizados")
    @Timed
    public ResponseEntity<Tbc_lab_tercerizado> createTbc_lab_tercerizado(@Valid @RequestBody Tbc_lab_tercerizado tbc_lab_tercerizado) throws URISyntaxException {
        log.debug("REST request to save Tbc_lab_tercerizado : {}", tbc_lab_tercerizado);
        tbc_lab_tercerizado.setRemovido(false);
        if (tbc_lab_tercerizado.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_lab_tercerizado", "idexists", "A new tbc_lab_tercerizado cannot already have an ID")).body(null);
        }
        Tbc_lab_tercerizado result = tbc_lab_tercerizadoService.save(tbc_lab_tercerizado);
        return ResponseEntity.created(new URI("/api/tbc-lab-tercerizados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_lab_tercerizado", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-lab-tercerizados : Updates an existing tbc_lab_tercerizado.
     *
     * @param tbc_lab_tercerizado the tbc_lab_tercerizado to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_lab_tercerizado,
     * or with status 400 (Bad Request) if the tbc_lab_tercerizado is not valid,
     * or with status 500 (Internal Server Error) if the tbc_lab_tercerizado couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-lab-tercerizados")
    @Timed
    public ResponseEntity<Tbc_lab_tercerizado> updateTbc_lab_tercerizado(@Valid @RequestBody Tbc_lab_tercerizado tbc_lab_tercerizado) throws URISyntaxException {
        log.debug("REST request to update Tbc_lab_tercerizado : {}", tbc_lab_tercerizado);
        if (tbc_lab_tercerizado.getId() == null) {
            return createTbc_lab_tercerizado(tbc_lab_tercerizado);
        }
        Tbc_lab_tercerizado result = tbc_lab_tercerizadoService.save(tbc_lab_tercerizado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_lab_tercerizado", tbc_lab_tercerizado.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-lab-tercerizados : get all the tbc_lab_tercerizados.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_lab_tercerizados in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-lab-tercerizados")
    @Timed
    public ResponseEntity<List<Tbc_lab_tercerizado>> getAllTbc_lab_tercerizados(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_lab_tercerizados");
        Page<Tbc_lab_tercerizado> page = tbc_lab_tercerizadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-lab-tercerizados");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-lab-tercerizados/:id : get the "id" tbc_lab_tercerizado.
     *
     * @param id the id of the tbc_lab_tercerizado to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_lab_tercerizado, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-lab-tercerizados/{id}")
    @Timed
    public ResponseEntity<Tbc_lab_tercerizado> getTbc_lab_tercerizado(@PathVariable Long id) {
        log.debug("REST request to get Tbc_lab_tercerizado : {}", id);
        Tbc_lab_tercerizado tbc_lab_tercerizado = tbc_lab_tercerizadoService.findOne(id);
        return Optional.ofNullable(tbc_lab_tercerizado)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-lab-tercerizados/:id : delete the "id" tbc_lab_tercerizado.
     *
     * @param id the id of the tbc_lab_tercerizado to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-lab-tercerizados/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_lab_tercerizado(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_lab_tercerizado : {}", id);
        tbc_lab_tercerizadoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_lab_tercerizado", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-lab-tercerizados?query=:query : search for the tbc_lab_tercerizado corresponding
     * to the query.
     *
     * @param query the query of the tbc_lab_tercerizado search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-lab-tercerizados")
    @Timed
    public ResponseEntity<List<Tbc_lab_tercerizado>> searchTbc_lab_tercerizados(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_lab_tercerizados for query {}", query);
        Page<Tbc_lab_tercerizado> page = null;
        if (query.contains("@r@") || query.contains("@R@") ) {
            String param =  query.replaceAll("@r@","").replaceAll("@R@","");
            page = tbc_lab_tercerizadoService.search(param, true, pageable);
        }else
            page = tbc_lab_tercerizadoService.search(query, false,pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-lab-tercerizados");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
