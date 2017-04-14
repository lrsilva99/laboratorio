package com.labotech.lims.domain.util.resultado;

import com.labotech.lims.domain.Tbc_status;
import com.labotech.lims.domain.Tbr_analise;
import com.labotech.lims.domain.Tbr_analise_resultado;
import com.labotech.lims.service.Tbc_analises_componenteService;
import com.labotech.lims.service.Tbc_statusService;
import com.labotech.lims.utility.Utility_Date;
import com.labotech.lims.web.rest.Tbr_analise_resultadoResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 10/04/2017.
 */
public class Crud_agrupa_resultado_preenchido {

    private List<Tbr_analise_resultado> tbr_analise_resultados;
    private List<Aux_tbc_analise_tbc_analise_componete> tbc_analise_tbc_analise_componetes = new ArrayList<>();
    private List<Tbr_analise> tbr_analises;
    private final Logger log = LoggerFactory.getLogger(Tbr_analise_resultadoResource.class);


    public Crud_agrupa_resultado_preenchido(List<Tbr_analise> tbr_analises, Tbc_analises_componenteService tbc_analises_componenteService, Tbc_statusService tbc_statusService, List<Aux_agrupa_resultado_tela> agrupa_resultado_telas, List<Tbr_analise_resultado> tbr_analise_resultados) {
        this.tbr_analises = tbr_analises;
        this.tbr_analise_resultados = tbr_analise_resultados;
        getTbcAnaliseTbcAnaliseComponente(tbc_analises_componenteService,tbc_statusService,agrupa_resultado_telas);
    }
    public List<Tbr_analise_resultado> getTbr_analise_resultados() {
        return tbr_analise_resultados;
    }

    public void setTbr_analise_resultados(List<Tbr_analise_resultado> tbr_analise_resultados) {
        this.tbr_analise_resultados = tbr_analise_resultados;
    }

    public List<Aux_tbc_analise_tbc_analise_componete> getTbc_analise_tbc_analise_componetes() {
        return tbc_analise_tbc_analise_componetes;
    }

    public void setTbc_analise_tbc_analise_componetes(List<Aux_tbc_analise_tbc_analise_componete> tbc_analise_tbc_analise_componetes) {
        this.tbc_analise_tbc_analise_componetes = tbc_analise_tbc_analise_componetes;
    }

    public List<Tbr_analise> getTbr_analises() {
        return tbr_analises;
    }

    public void setTbr_analises(List<Tbr_analise> tbr_analises) {
        this.tbr_analises = tbr_analises;
    }

    //Carregar e virificar se os componentes digitados estão iguais a relação dos componetes padrão por análise
    private void getTbcAnaliseTbcAnaliseComponente(Tbc_analises_componenteService tbc_analises_componenteService, Tbc_statusService tbc_statusService, List<Aux_agrupa_resultado_tela> agrupa_resultado_telas){
        for (int i = 0; i < getTbr_analises().size() ; i++) {
            Aux_tbc_analise_tbc_analise_componete tbc_analise_tbc_analise_componete =  new Aux_tbc_analise_tbc_analise_componete(getTbr_analises().get(i), getTbr_analises().get(i).getTbc_analises(),tbc_analises_componenteService, getTbr_analises().get(i).getObservacao());
            tbc_analise_tbc_analise_componetes.add(tbc_analise_tbc_analise_componete);
        }
        Tbc_status tbc_status =  tbc_statusService.findOne(Long.valueOf(5));
        int totalResultadoDigitado = tbr_analise_resultados.size();
        log.debug("totalResultadoDigitado : {}", totalResultadoDigitado);
        for (int i = 0; i < tbc_analise_tbc_analise_componetes.size() ; i++) {
            log.debug("tbc_analise_tbc_analise_componetes.size() : {}", tbc_analise_tbc_analise_componetes.size());
            for (int j = 0; j < tbc_analise_tbc_analise_componetes.get(i).getAuctbc_analise_componente_digitados().size(); j++) {
                for (int k = 0; k < tbr_analise_resultados.size(); k++) {
                    log.debug("tbr_analise_resultados.size() : {}", tbr_analise_resultados.size());
                    if (tbr_analise_resultados.get(k).getAnalises_componente().equals(tbc_analise_tbc_analise_componetes.get(i).getAuctbc_analise_componente_digitados().get(j).getTbc_analises_componente().getNome())){
                        tbc_analise_tbc_analise_componetes.get(i).getAuctbc_analise_componente_digitados().get(j).setTbr_analise_resultado(tbr_analise_resultados.get(k));
                        log.debug("Adicionado: {}", tbr_analise_resultados.get(k).getTbc_analises_componente().getNome());
                    }
                }
            }
        }

        Aux_agrupa_resultado_tela agrupa_resultado_tela =  new Aux_agrupa_resultado_tela();

        for (int i = 0; i < tbc_analise_tbc_analise_componetes.size() ; i++) {
            if (tbc_analise_tbc_analise_componetes.get(i).getTbc_analises().getTbc_grupo_analise().getNome().equals(agrupa_resultado_tela.getGrupo() + tbc_analise_tbc_analise_componetes.get(i).getTbc_analises().getId())) {
                agrupa_resultado_tela.setGrupo(tbc_analise_tbc_analise_componetes.get(i).getTbc_analises().getTbc_grupo_analise().getNome());
                agrupa_resultado_tela.setAnalise(String.valueOf(tbc_analise_tbc_analise_componetes.get(i).getTbc_analises().getId()));
                agrupa_resultado_tela.setObservacao(tbc_analise_tbc_analise_componetes.get(i).getObservacao());
                log.debug("Observacao2: {}", tbc_analise_tbc_analise_componetes.get(i).getObservacao());
            }
            else {
                agrupa_resultado_tela = new Aux_agrupa_resultado_tela();
                agrupa_resultado_tela.setGrupo(tbc_analise_tbc_analise_componetes.get(i).getTbc_analises().getTbc_grupo_analise().getNome());
                agrupa_resultado_telas.add(agrupa_resultado_tela);
                agrupa_resultado_tela.setAnalise(String.valueOf(tbc_analise_tbc_analise_componetes.get(i).getTbc_analises().getId()));
                agrupa_resultado_tela.setObservacao(String.valueOf(tbc_analise_tbc_analise_componetes.get(i).getObservacao()));

            }

            for (int j = 0; j < tbc_analise_tbc_analise_componetes.get(i).getAuctbc_analise_componente_digitados().size(); j++) {
                    if (tbc_analise_tbc_analise_componetes.get(i).getAuctbc_analise_componente_digitados().get(j).getTbr_analise_resultado() == null){
                        Tbr_analise_resultado tbr_analise_resultado = new Tbr_analise_resultado();
                        tbr_analise_resultado.setTbc_status(tbc_status);
                        tbr_analise_resultado.setTbc_status_ultimo(tbc_status);
                        //tbr_analise_resultado.setCreatedBy("Systema");
                        tbr_analise_resultado.setCreatedDate(Utility_Date.getDataHoraAtual());
                        tbr_analise_resultado.setAnalises_componente(tbc_analise_tbc_analise_componetes.get(i).getAuctbc_analise_componente_digitados().get(j).getTbc_analises_componente().getNome());
                        tbr_analise_resultado.setTbr_amostra_id(tbc_analise_tbc_analise_componetes.get(i).getTbr_analise().getTbr_amostra().getId());
                        tbr_analise_resultado.setTbr_analise_id(tbc_analise_tbc_analise_componetes.get(i).getTbr_analise().getId());
                        tbr_analise_resultado.setTbc_analises_componente(tbc_analise_tbc_analise_componetes.get(i).getAuctbc_analise_componente_digitados().get(j).getTbc_analises_componente());
                        tbr_analise_resultado.setUnidade_medida(tbc_analise_tbc_analise_componetes.get(i).getAuctbc_analise_componente_digitados().get(j).getTbc_analises_componente().getUnidade_medida());
                        agrupa_resultado_tela.getTbr_analise_resultados().add(tbr_analise_resultado);
                }else
                        agrupa_resultado_tela.getTbr_analise_resultados().add(tbc_analise_tbc_analise_componetes.get(i).getAuctbc_analise_componente_digitados().get(j).getTbr_analise_resultado());

            }

        }
    }

}
