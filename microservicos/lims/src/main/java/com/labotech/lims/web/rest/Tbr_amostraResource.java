package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.*;
import com.labotech.lims.service.*;
import com.labotech.lims.utility.Utility_Date;
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
 * REST controller for managing Tbr_amostra.
 */
@RestController
@RequestMapping("/api")
public class Tbr_amostraResource {

    private final Logger log = LoggerFactory.getLogger(Tbr_amostraResource.class);

    @Inject
    private Tbr_amostraService tbr_amostraService;
    @Inject
    private Tbc_analisesService tbc_analisesService;
    @Inject
    private Tbr_analiseService tbr_analiseService;
    @Inject
    private Tbc_plano_teste_analiseService tbc_plano_teste_analiseService;
    @Inject
    private Tbc_numeracaoService tbc_numeracaoService;



    /**
     * POST  /tbr-amostras : Create a new tbr_amostra.
     *
     * @param tbr_amostra the tbr_amostra to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbr_amostra, or with status 400 (Bad Request) if the tbr_amostra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbr-amostras")
    @Timed
    public ResponseEntity<Tbr_amostra> createTbr_amostra(@Valid @RequestBody Tbr_amostra tbr_amostra) throws URISyntaxException {
        log.debug("REST request to save Tbr_amostra : {}", tbr_amostra);
        log.debug("REST request to save Tbr_amostra : {}", tbr_amostra.getTbr_analises());
        if (tbr_amostra.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbr_amostra", "idexists", "A new tbr_amostra cannot already have an ID")).body(null);
        }
        //Verifiar o contador de numeraÃ§Ã£o automÃ¡tica da amostra
        Tbc_numeracao tbc_numeracao = tbc_numeracaoService.findOne(tbr_amostra.getTbc_formulario().getTipo_identificacao(), Utility_Date.getAnoCorrente());
        if (tbc_numeracao == null ) {
            tbc_numeracao = new Tbc_numeracao();
            tbc_numeracao.setAno(Utility_Date.getAnoCorrente());
            tbc_numeracao.setNumero(1);
            tbc_numeracao.setParametro(tbr_amostra.getTbc_formulario().getTipo_identificacao());
            tbc_numeracao.setCreatedBy(tbr_amostra.getCreatedBy());
            tbc_numeracao.setCreatedDate(Utility_Date.getDataHoraAtual());
        }
        else{
            tbc_numeracao.setNumero(tbc_numeracao.getNumero() + 1);
            tbc_numeracao.setLastModifiedBy(tbr_amostra.getCreatedBy());
            tbc_numeracao.setLastModifiedDate(Utility_Date.getDataHoraAtual());
        }
        log.debug("REST request to save tbc_numeracao : {}", tbc_numeracao);
        tbc_numeracaoService.save(tbc_numeracao);
        tbr_amostra.setReq_texto(tbc_numeracao.getReq_Texto());
        tbr_amostra.setTbc_status(tbr_amostra.getTbc_formulario().getTbc_status());

        Tbr_amostra result = tbr_amostraService.save(tbr_amostra);
        adicionarAnalise(tbr_amostra.getPlano_teste_a(),result);
        adicionarPlanoTeste(tbr_amostra.getPlano_teste_b(),result);
        return ResponseEntity.created(new URI("/api/tbr-amostras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbr_amostra", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbr-amostras : Updates an existing tbr_amostra.
     *
     * @param tbr_amostra the tbr_amostra to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbr_amostra,
     * or with status 400 (Bad Request) if the tbr_amostra is not valid,
     * or with status 500 (Internal Server Error) if the tbr_amostra couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbr-amostras")
    @Timed
    public ResponseEntity<Tbr_amostra> updateTbr_amostra(@Valid @RequestBody Tbr_amostra tbr_amostra) throws URISyntaxException {
        log.debug("REST request to update Tbr_amostra : {}", tbr_amostra);

        if (tbr_amostra.getId() == null) {
            return createTbr_amostra(tbr_amostra);
        }
        Tbr_amostra result = tbr_amostraService.save(tbr_amostra);
        adicionarAnalise(tbr_amostra.getPlano_teste_a(),result);
        adicionarPlanoTeste(tbr_amostra.getPlano_teste_b(),result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tbr_amostra", tbr_amostra.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tbr-amostras : get all the tbr_amostras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tbr_amostras in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tbr-amostras")
    @Timed
    public ResponseEntity<List<Tbr_amostra>> getAllTbr_amostras(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tbr_amostras");
        Page<Tbr_amostra> page = tbr_amostraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbr-amostras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tbr-amostras/:id : get the "id" tbr_amostra.
     *
     * @param id the id of the tbr_amostra to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbr_amostra, or with status 404 (Not Found)
     */
    @GetMapping("/tbr-amostras/{id}")
    @Timed
    public ResponseEntity<Tbr_amostra> getTbr_amostra(@PathVariable Long id) {
        log.debug("REST request to get Tbr_amostra : {}", id);
        Tbr_amostra tbr_amostra = tbr_amostraService.findOne(id);
        return Optional.ofNullable(tbr_amostra)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbr-amostras/:id : delete the "id" tbr_amostra.
     *
     * @param id the id of the tbr_amostra to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbr-amostras/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbr_amostra(@PathVariable Long id) {
        log.debug("REST request to delete Tbr_amostra : {}", id);
        tbr_amostraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbr_amostra", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbr-amostras?query=:query : search for the tbr_amostra corresponding
     * to the query.
     *
     * @param query the query of the tbr_amostra search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tbr-amostras")
    @Timed
    public ResponseEntity<List<Tbr_amostra>> searchTbr_amostras(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tbr_amostras for query {}", query);
        Page<Tbr_amostra> page = tbr_amostraService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tbr-amostras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    //adicionar Análise nas amostras
    private  void adicionarAnalise(String texto, Tbr_amostra tbr_amostra) {
        if (texto!= "" || ! texto.equals("")) {
            String s3 = texto;
            String[] temp = null;
            temp = s3.split("@");
            for (int i = 0; i < temp.length; i++) {
                Tbr_analise tbr_analise = new Tbr_analise();
                tbr_analise.setTbc_analises(tbc_analisesService.findOne(Long.valueOf(temp[i])));
                tbr_analise.forma_cadastro("Individual");
                tbr_analise.tbr_amostra(tbr_amostra);
                tbr_analise.setTbc_status(tbr_amostra.getTbc_status());
                tbr_analise.setCreatedBy(tbr_amostra.getCreatedBy());
                tbr_analise.setCreatedDate(tbr_amostra.getCreatedDate());
                tbr_analiseService.save(tbr_analise);
                tbr_analise = null;
            }
        }
    }
    //adicionar Análise nas amostras
    private  void adicionarPlanoTeste(String texto, Tbr_amostra tbr_amostra) {
        if (texto!= "" || ! texto.equals("")) {
            String s3 = texto;
            String[] temp = null;
            temp = s3.split("@");
            for (int i = 0; i < temp.length; i++) {
                List<Tbc_plano_teste_analise> tbc_plano_teste_analises = tbc_plano_teste_analiseService.listAllPlanoTeste(Long.valueOf(temp[i]));
                for (int j = 0; j < tbc_plano_teste_analises.size(); j++) {
                    Tbr_analise tbr_analise = new Tbr_analise();
                    tbr_analise.setTbc_analises(tbc_plano_teste_analises.get(j).getTbc_analises());
                    tbr_analise.forma_cadastro("Individual");
                    tbr_analise.tbr_amostra(tbr_amostra);
                    tbr_analise.setTbc_status(tbr_amostra.getTbc_status());
                    tbr_analise.setCreatedBy(tbr_amostra.getCreatedBy());
                    tbr_analise.setCreatedDate(tbr_amostra.getCreatedDate());
                    tbr_analiseService.save(tbr_analise);
                    tbr_analise = null;
                }
                tbc_plano_teste_analises = null;
            }
        }
    }


}
