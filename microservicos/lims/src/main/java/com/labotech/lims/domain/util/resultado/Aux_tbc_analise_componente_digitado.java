package com.labotech.lims.domain.util.resultado;

import com.labotech.lims.domain.Tbc_analises_componente;
import com.labotech.lims.domain.Tbr_analise_resultado;

/**
 * Created by Leonardo on 10/04/2017.
 */
public class Aux_tbc_analise_componente_digitado {
    private Tbc_analises_componente tbc_analises_componente;
    private Tbr_analise_resultado tbr_analise_resultado;

    public Aux_tbc_analise_componente_digitado(Tbc_analises_componente tbc_analises_componente) {
        this.tbc_analises_componente = tbc_analises_componente;
    }

    public Tbc_analises_componente getTbc_analises_componente() {
        return tbc_analises_componente;
    }

    public void setTbc_analises_componente(Tbc_analises_componente tbc_analises_componente) {
        this.tbc_analises_componente = tbc_analises_componente;
    }

    public Tbr_analise_resultado getTbr_analise_resultado() {
        return tbr_analise_resultado;
    }

    public void setTbr_analise_resultado(Tbr_analise_resultado tbr_analise_resultado) {
        this.tbr_analise_resultado = tbr_analise_resultado;
    }
}
