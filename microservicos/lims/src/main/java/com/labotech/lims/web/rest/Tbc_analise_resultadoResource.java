package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_analise_resultado;

import com.labotech.lims.repository.Tbc_analise_resultadoRepository;
import com.labotech.lims.repository.search.Tbc_analise_resultadoSearchRepository;
import com.labotech.lims.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Tbc_analise_resultado.
 */
@RestController
@RequestMapping("/api")
public class Tbc_analise_resultadoResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_analise_resultadoResource.class);
        
    @Inject
    private Tbc_analise_resultadoRepository tbc_analise_resultadoRepository;

    @Inject
    private Tbc_analise_resultadoSearchRepository tbc_analise_resultadoSearchRepository;

    /**
     * POST  /tbc-analise-resultados : Create a new tbc_analise_resultado.
     *
     * @param tbc_analise_resultado the tbc_analise_resultado to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_analise_resultado, or with status 400 (Bad Request) if the tbc_analise_resultado has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-analise-resultados")
    @Timed
    public ResponseEntity<Tbc_analise_resultado> createTbc_analise_resultado(@Valid @RequestBody Tbc_analise_resultado tbc_analise_resultado) throws URISyntaxException {
        log.debug("REST request to save Tbc_analise_resultado : {}", tbc_analise_resultado);
        if (tbc_analise_resultado.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_analise_resultado", "idexists", "A new tbc_analise_resultado cannot already have an ID")).body(null);
        }
        Tbc_analise_resultado result = tbc_analise_resultadoRepository.save(tbc_analise_resultado);
        tbc_analise_resultadoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tbc-analise-resultados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_analise_resultado", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-analise-resultados : Updates an existing tbc_analise_resultado.
     *
     * @param tbc_analise_resultado the tbc_analise_resultado to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_analise_resultado,
     * or with status 400 (Bad Request) if the tbc_analise_resultado is not valid,
     * or with status 500 (Internal Server Error) if the tbc_analise_resultado couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-analise-resultados")
    @Timed
    public ResponseEntity<Tbc_analise_resultado> updateTbc_analise_resultado(@Valid @RequestBody Tbc_analise_resultado tbc_analise_resultado) throws URISyntaxException {
        log.debug("REST request to update Tbc_analise_resultado : {}", tbc_analise_resultado);
        if (tbc_analise_resultado.getId() == null) {
            return createTbc_analise_resultado(tbc_analise_resultado);
        }
        Tbc_analise_resultado result = tbc_analise_resultadoRepository.save(tbc_analise_resultado);
        tbc_analise_resultadoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_analise_resultado", tbc_analise_resultado.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-analise-resultados : get all the tbc_analise_resultados.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_analise_resultados in body
     */
    @GetMapping("/tbc-analise-resultados")
    @Timed
    public List<Tbc_analise_resultado> getAllTbc_analise_resultados() {
        log.debug("REST request to get all Tbc_analise_resultados");
        List<Tbc_analise_resultado> tbc_analise_resultados = tbc_analise_resultadoRepository.findAll();
        return tbc_analise_resultados;
    }

    /**
     * GET  /tbc-analise-resultados/:id : get the "id" tbc_analise_resultado.
     *
     * @param id the id of the tbc_analise_resultado to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_analise_resultado, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-analise-resultados/{id}")
    @Timed
    public ResponseEntity<Tbc_analise_resultado> getTbc_analise_resultado(@PathVariable Long id) {
        log.debug("REST request to get Tbc_analise_resultado : {}", id);
        Tbc_analise_resultado tbc_analise_resultado = tbc_analise_resultadoRepository.findOne(id);
        return Optional.ofNullable(tbc_analise_resultado)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-analise-resultados/:id : delete the "id" tbc_analise_resultado.
     *
     * @param id the id of the tbc_analise_resultado to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-analise-resultados/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_analise_resultado(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_analise_resultado : {}", id);
        tbc_analise_resultadoRepository.delete(id);
        tbc_analise_resultadoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_analise_resultado", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-analise-resultados?query=:query : search for the tbc_analise_resultado corresponding
     * to the query.
     *
     * @param query the query of the tbc_analise_resultado search 
     * @return the result of the search
     */
    @GetMapping("/_search/tbc-analise-resultados")
    @Timed
    public List<Tbc_analise_resultado> searchTbc_analise_resultados(@RequestParam String query) {
        log.debug("REST request to search Tbc_analise_resultados for query {}", query);
        return StreamSupport
            .stream(tbc_analise_resultadoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
