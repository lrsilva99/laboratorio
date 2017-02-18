package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_report;
import com.labotech.lims.service.Tbc_reportService;
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
 * REST controller for managing Tbc_report.
 */
@RestController
@RequestMapping("/api")
public class Tbc_reportResource {

    private final Logger log = LoggerFactory.getLogger(Tbc_reportResource.class);

    @Inject
    private Tbc_reportService tbc_reportService;

    /**
     * POST  /tbc-reports : Create a new tbc_report.
     *
     * @param tbc_report the tbc_report to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbc_report, or with status 400 (Bad Request) if the tbc_report has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbc-reports")
    @Timed
    public ResponseEntity<Tbc_report> createTbc_report(@Valid @RequestBody Tbc_report tbc_report) throws URISyntaxException {
        log.debug("REST request to save Tbc_report : {}", tbc_report);
        tbc_report.setRemovido(false);
        if (tbc_report.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbc_report", "idexists", "A new tbc_report cannot already have an ID")).body(null);
        }
        Tbc_report result = tbc_reportService.save(tbc_report);
        return ResponseEntity.created(new URI("/api/tbc-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbc_report", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbc-reports : Updates an existing tbc_report.
     *
     * @param tbc_report the tbc_report to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbc_report,
     * or with status 400 (Bad Request) if the tbc_report is not valid,
     * or with status 500 (Internal Server Error) if the tbc_report couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbc-reports")
    @Timed
    public ResponseEntity<Tbc_report> updateTbc_report(@Valid @RequestBody Tbc_report tbc_report) throws URISyntaxException {
        log.debug("REST request to update Tbc_report : {}", tbc_report);
        if (tbc_report.getId() == null) {
            return createTbc_report(tbc_report);
        }
        Tbc_report result = tbc_reportService.save(tbc_report);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbc_report", tbc_report.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbc-reports : get all the tbc_reports.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbc_reports in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbc-reports")
    @Timed
    public ResponseEntity<List<Tbc_report>> getAllTbc_reports(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbc_reports");
        Page<Tbc_report> page = tbc_reportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbc-reports");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbc-reports/:id : get the "id" tbc_report.
     *
     * @param id the id of the tbc_report to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbc_report, or with status 404 (Not Found)
     */
    @GetMapping("/tbc-reports/{id}")
    @Timed
    public ResponseEntity<Tbc_report> getTbc_report(@PathVariable Long id) {
        log.debug("REST request to get Tbc_report : {}", id);
        Tbc_report tbc_report = tbc_reportService.findOne(id);
        return Optional.ofNullable(tbc_report)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbc-reports/:id : delete the "id" tbc_report.
     *
     * @param id the id of the tbc_report to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbc-reports/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbc_report(@PathVariable Long id) {
        log.debug("REST request to delete Tbc_report : {}", id);
        tbc_reportService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbc_report", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbc-reports?query=:query : search for the tbc_report corresponding
     * to the query.
     *
     * @param query the query of the tbc_report search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbc-reports")
    @Timed
    public ResponseEntity<List<Tbc_report>> searchTbc_reports(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbc_reports for query {}", query);
        Page<Tbc_report>  page = null;
        if (query.contains("@r@") || query.contains("@R@") ) {
            String param =  query.replaceAll("@r@","").replaceAll("@R@","");
            page = tbc_reportService.search(param, true, pageable);
        }else
            page = tbc_reportService.search(query, false, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbc-reports");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
