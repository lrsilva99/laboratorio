package com.labotech.lims.domain.util.resultado;

import com.labotech.lims.domain.Tbc_analises;
import com.labotech.lims.domain.Tbc_analises_componente;
import com.labotech.lims.domain.Tbr_analise;
import com.labotech.lims.service.Tbc_analises_componenteService;
import com.labotech.lims.web.rest.Tbr_analise_resultadoResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 10/04/2017.
 * Agrupar as análises e seus componets
 */
public class Aux_tbc_analise_tbc_analise_componete {
    private final Logger log = LoggerFactory.getLogger(Tbr_analise_resultadoResource.class);
    private Tbc_analises tbc_analises;
    private List<Aux_tbc_analise_componente_digitado> auctbc_analise_componente_digitados =  new ArrayList<>();
    private Tbr_analise tbr_analise;
    private String observacao= new String();
    public Aux_tbc_analise_tbc_analise_componete(Tbr_analise tbr_analise, Tbc_analises tbc_analises, Tbc_analises_componenteService tbc_analises_componenteService, String observacao) {
        this.tbc_analises = tbc_analises;
        this.observacao = observacao;
        this.tbr_analise = tbr_analise;
        log.debug("Observacao: {}", observacao);

        List<Tbc_analises_componente> tbc_analises_componentes = tbc_analises_componenteService.findAllListForAnalise(tbc_analises.getId());
        for (int i = 0; i < tbc_analises_componentes.size(); i++) {
            Aux_tbc_analise_componente_digitado auctbc_analise_componente_digitado =  new Aux_tbc_analise_componente_digitado(tbc_analises_componentes.get(i));
            auctbc_analise_componente_digitados.add(auctbc_analise_componente_digitado);
        }
    }

    public Tbc_analises getTbc_analises() {
        return tbc_analises;
    }
    public void setTbc_analises(Tbc_analises tbc_analises) {
        this.tbc_analises = tbc_analises;
    }

    public List<Aux_tbc_analise_componente_digitado> getAuctbc_analise_componente_digitados() {
        return auctbc_analise_componente_digitados;
    }

    public void setAuctbc_analise_componente_digitados(List<Aux_tbc_analise_componente_digitado> auctbc_analise_componente_digitados) {
        this.auctbc_analise_componente_digitados = auctbc_analise_componente_digitados;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Tbr_analise getTbr_analise() {
        return tbr_analise;
    }

    public void setTbr_analise(Tbr_analise tbr_analise) {
        this.tbr_analise = tbr_analise;
    }


    public int getTotalComponente(){
        return auctbc_analise_componente_digitados.size();
    }
}
