package com.labotech.lims.domain.util.resultado;

import com.labotech.lims.domain.Tbr_analise;
import com.labotech.lims.domain.Tbr_analise_resultado;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Leonardo on 04/04/2017.
 */
public class Aux_agrupa_resultado_tela {

    private static final long serialVersionUID = 1L;
    private String grupo = "";
    private List<Tbr_analise_resultado> tbr_analise_resultados = new ArrayList<>();
    private String analise;
    private String observacao;
    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public List<Tbr_analise_resultado> getTbr_analise_resultados() {
        return tbr_analise_resultados;
    }

    public void setTbr_analise_resultados(List<Tbr_analise_resultado> tbr_analise_resultados) {
        this.tbr_analise_resultados = tbr_analise_resultados;
    }

    public String getAnalise() {
        return analise;
    }

    public void setAnalise(String analise) {
        this.analise = analise;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;

    }
}
