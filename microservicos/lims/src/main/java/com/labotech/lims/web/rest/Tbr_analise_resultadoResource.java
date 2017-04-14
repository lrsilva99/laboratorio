package com.labotech.lims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labotech.lims.domain.Tbc_analises_componente;
import com.labotech.lims.domain.Tbc_status;
import com.labotech.lims.domain.Tbr_analise;
import com.labotech.lims.domain.Tbr_analise_resultado;

import com.labotech.lims.domain.util.resultado.Crud_agrupa_resultado_preenchido;
import com.labotech.lims.domain.util.resultado.Aux_agrupa_resultado_tela;
import com.labotech.lims.domain.util.resultado.Crud_tbr_analise_resultado;
import com.labotech.lims.repository.Tbr_analise_resultadoRepository;
import com.labotech.lims.repository.search.Tbr_analise_resultadoSearchRepository;
import com.labotech.lims.service.Tbc_analises_componenteService;
import com.labotech.lims.service.Tbc_statusService;
import com.labotech.lims.service.Tbr_analiseService;
import com.labotech.lims.utility.Utility_Date;
import com.labotech.lims.web.rest.util.HeaderUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tbr_analise_resultado.
 */
@RestController
@RequestMapping("/api")
public class Tbr_analise_resultadoResource {

    private final Logger log = LoggerFactory.getLogger(Tbr_analise_resultadoResource.class);

    @Inject
    private Tbr_analise_resultadoRepository tbr_analise_resultadoRepository;
    @Inject
    private Tbr_analise_resultadoRepository tbr_analise_resultadoRepository2;

    @Inject
    private Tbr_analise_resultadoSearchRepository tbr_analise_resultadoSearchRepository;


    @Inject
    private Tbc_statusService tbc_statusService;

    @Inject
    private Tbc_analises_componenteService tbc_analises_componenteService;

    @Inject
    private Tbr_analiseService tbr_analiseService;


    /**
     * POST  /tbr-analise-resultados : Create a new tbr_analise_resultado.
     *
     * @param tbr_analise_resultado the tbr_analise_resultado to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tbr_analise_resultado, or with status 400 (Bad Request) if the tbr_analise_resultado has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tbr-analise-resultados")
    @Timed
    public ResponseEntity<Tbr_analise_resultado> createTbr_analise_resultado(@Valid @RequestBody Tbr_analise_resultado tbr_analise_resultado) throws URISyntaxException {
        log.debug("REST request to save Tbr_analise_resultado : {}", tbr_analise_resultado);
        if (tbr_analise_resultado.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tbr_analise_resultado", "idexists", "A new tbr_analise_resultado cannot already have an ID")).body(null);
        }
        Tbr_analise_resultado result = tbr_analise_resultadoRepository.save(tbr_analise_resultado);
        tbr_analise_resultadoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tbr-analise-resultados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tbr_analise_resultado", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tbr-analise-resultados : Updates an existing tbr_analise_resultado.
     *
     * @param tbr_analise_resultado the tbr_analise_resultado to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tbr_analise_resultado,
     * or with status 400 (Bad Request) if the tbr_analise_resultado is not valid,
     * or with status 500 (Internal Server Error) if the tbr_analise_resultado couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tbr-analise-resultados")
    @Timed
    public ResponseEntity<List<Aux_agrupa_resultado_tela>> updateTbr_analise_resultado(@Valid @RequestBody List<Aux_agrupa_resultado_tela> tbr_analise_resultado) throws URISyntaxException {
        log.debug("REST request to update Tbr_analise_resultado : {}", tbr_analise_resultado.size());
        //if (tbr_analise_resultado.getId() == null) {
        //   return createTbr_analise_resultado(tbr_analise_resultado);
        //}
        //Tbr_analise_resultado result = tbr_analise_resultadoRepository.save(tbr_analise_resultado);
        Crud_tbr_analise_resultado.put(
            tbr_analise_resultadoSearchRepository,
            tbc_statusService,
            tbr_analise_resultado,
            tbr_analise_resultadoRepository,
            tbr_analiseService
        );



        return Optional.ofNullable(tbr_analise_resultado)
            .map(result -> new ResponseEntity<>(
                tbr_analise_resultado,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * GET  /tbr-analise-resultados : get all the tbr_analise_resultados.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tbr_analise_resultados in body
     */
    @GetMapping("/tbr-analise-resultados")
    @Timed
    public List<Aux_agrupa_resultado_tela> getAllTbr_analise_resultados(@RequestParam(value = "tbr_amostra_id") @ApiParam Long tbr_amostra_id) {
        log.debug("REST request to get all Tbr_analise_resultados");
        log.debug("Parametro: {}", tbr_amostra_id);
        List<Tbr_analise_resultado> tbr_analise_resultados = tbr_analise_resultadoRepository.findAllForAnalise(tbr_amostra_id);
        List<Aux_agrupa_resultado_tela> agrupa_resultado_telas = new ArrayList<>();
        if (tbr_analise_resultados.size() == 0){
            this.adiciona_Nova_Entrada_Resultado(tbr_analise_resultados,agrupa_resultado_telas,tbr_amostra_id);
        }else{
            List<Tbr_analise> tbr_analises = tbr_analiseService.findAllList(tbr_amostra_id);

            Crud_agrupa_resultado_preenchido agrupa_resultado_tela =  new Crud_agrupa_resultado_preenchido(tbr_analises, tbc_analises_componenteService, tbc_statusService,agrupa_resultado_telas,tbr_analise_resultados);

        }







        return agrupa_resultado_telas;
    }

    /**
     * GET  /tbr-analise-resultados/:id : get the "id" tbr_analise_resultado.
     *
     * @param id the id of the tbr_analise_resultado to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tbr_analise_resultado, or with status 404 (Not Found)
     */
    @GetMapping("/tbr-analise-resultados/{id}")
    @Timed
    public ResponseEntity<Tbr_analise_resultado> getTbr_analise_resultado(@PathVariable Long id) {
        log.debug("REST request to get Tbr_analise_resultado : {}", id);
        Tbr_analise_resultado tbr_analise_resultado = tbr_analise_resultadoRepository.findOne(id);
        return Optional.ofNullable(tbr_analise_resultado)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tbr-analise-resultados/:id : delete the "id" tbr_analise_resultado.
     *
     * @param id the id of the tbr_analise_resultado to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tbr-analise-resultados/{id}")
    @Timed
    public ResponseEntity<Void> deleteTbr_analise_resultado(@PathVariable Long id) {
        log.debug("REST request to delete Tbr_analise_resultado : {}", id);
        tbr_analise_resultadoRepository.delete(id);
        tbr_analise_resultadoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tbr_analise_resultado", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tbr-analise-resultados?query=:query : search for the tbr_analise_resultado corresponding
     * to the query.
     *
     * @param query the query of the tbr_analise_resultado search
     * @return the result of the search
     */
    @GetMapping("/_search/tbr-analise-resultados")
    @Timed
    public List<Aux_agrupa_resultado_tela> searchTbr_analise_resultados(@RequestParam String query) {
        log.debug("REST request to search Tbr_analise_resultados for query {}", query);
        // return StreamSupport
        //    .stream(tbr_analise_resultadoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        //   .collect(Collectors.toList());
        return this.getAllTbr_analise_resultados(Long.valueOf(query));
    }

    private void adiciona_Nova_Entrada_Resultado (List<Tbr_analise_resultado> tbr_analise_resultados, List<Aux_agrupa_resultado_tela> agrupa_resultado_telas, Long tbr_amostra_id){
        Aux_agrupa_resultado_tela agrupa_resultado_tela =  new Aux_agrupa_resultado_tela();
        List<Tbr_analise> tbr_analise =  tbr_analiseService.findAllList(tbr_amostra_id);
        log.debug("Dados da amostra: {}", tbr_amostra_id);
        Tbc_status tbc_status =  tbc_statusService.findOne(Long.valueOf(5));
        for (int i = 0; i <tbr_analise.size() ; i++) {
            List<Tbc_analises_componente> tbc_analises_componentes = tbc_analises_componenteService.findAllListForAnalise(tbr_analise.get(i).getTbc_analises().getId());
            String compara = tbr_analise.get(i).getTbc_analises().getTbc_grupo_analise().getNome() + tbr_analise.get(i).getTbc_analises().getId();
            if (tbc_analises_componentes.size() > 0) {
                if (tbr_analise.get(i).getTbc_analises().getTbc_grupo_analise().getNome().equals(agrupa_resultado_tela.getGrupo() + tbr_analise.get(i).getTbc_analises().getId()))
                    agrupa_resultado_tela.setGrupo(tbr_analise.get(i).getTbc_analises().getTbc_grupo_analise().getNome());
                else {
                    agrupa_resultado_tela = new Aux_agrupa_resultado_tela();
                    agrupa_resultado_tela.setGrupo(tbr_analise.get(i).getTbc_analises().getTbc_grupo_analise().getNome());
                    agrupa_resultado_telas.add(agrupa_resultado_tela);
                }
                for (int j = 0; j < tbc_analises_componentes.size(); j++) {
                    Tbr_analise_resultado tbr_analise_resultado = new Tbr_analise_resultado();
                    tbr_analise_resultado.setTbc_status(tbc_status);
                    tbr_analise_resultado.setTbc_status_ultimo(tbc_status);
                    tbr_analise_resultado.setCreatedBy(tbr_analise.get(i).getCreatedBy());
                    tbr_analise_resultado.setCreatedDate(Utility_Date.getDataHoraAtual());
                    tbr_analise_resultado.setAnalises_componente(tbc_analises_componentes.get(j).getNome());
                    tbr_analise_resultado.setTbr_amostra_id(tbr_amostra_id);
                    tbr_analise_resultado.setTbr_analise_id(tbr_analise.get(i).getId());
                    tbr_analise_resultado.setTbc_analises_componente(tbc_analises_componentes.get(j));
                    tbr_analise_resultado.setUnidade_medida(tbc_analises_componentes.get(j).getUnidade_medida());
                    tbr_analise_resultados.add(tbr_analise_resultado);
                    agrupa_resultado_tela.getTbr_analise_resultados().add(tbr_analise_resultado);
                }
            }
        }
    }
}
