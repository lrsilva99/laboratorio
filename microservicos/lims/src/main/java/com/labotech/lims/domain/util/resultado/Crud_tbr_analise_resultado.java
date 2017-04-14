package com.labotech.lims.domain.util.resultado;

import com.labotech.lims.domain.Tbc_status;
import com.labotech.lims.domain.Tbr_analise;
import com.labotech.lims.domain.Tbr_analise_resultado;
import com.labotech.lims.repository.Tbr_analise_resultadoRepository;
import com.labotech.lims.repository.search.Tbr_analise_resultadoSearchRepository;
import com.labotech.lims.service.Tbc_statusService;
import com.labotech.lims.service.Tbr_analiseService;
import com.labotech.lims.utility.Utility_Date;
import com.labotech.lims.web.rest.Tbr_analise_resultadoResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 13/04/2017.
 */
public class Crud_tbr_analise_resultado {
    private static final Logger log = LoggerFactory.getLogger(Tbr_analise_resultadoResource.class);
    private static List<Tbr_analise_resultado> updateStatusAnalise = new ArrayList<>();


    public static void put (Tbr_analise_resultadoSearchRepository tbr_analise_resultadoSearchRepository,
                               Tbc_statusService tbc_statusService,
                               List<Aux_agrupa_resultado_tela> tbr_analise_resultado,
                               Tbr_analise_resultadoRepository tbr_analise_resultadoRepository,
                               Tbr_analiseService tbr_analiseService
                               )
    {
        log.debug("Atualizando dados tbr_analise_resultado : {}", tbr_analiseService != null);
        Tbc_status tbc_statusCompletado =  null;
        Tbc_status tbc_statusAutorizado =  null;
        for (int i = 0; i <tbr_analise_resultado.size() ; i++) {
            //Armaenar o id da tbr_analise para ser atualizado ao final;
            Tbr_analise_resultado tbrAnaliseResultado = null;
            for (int j = 0; j < tbr_analise_resultado.get(i).getTbr_analise_resultados().size(); j++) {
                if (tbr_analise_resultado.get(i).getTbr_analise_resultados().get(j).getId() == null) {
                    if (salvarNovoResultado(tbc_statusCompletado,tbc_statusService,tbr_analise_resultado.get(i).getTbr_analise_resultados().get(j))) {
                        tbrAnaliseResultado = tbr_analise_resultadoRepository.save(tbr_analise_resultado.get(i).getTbr_analise_resultados().get(j));
                        tbr_analise_resultadoSearchRepository.save(tbrAnaliseResultado);
                    }
                }
                else {
                    if (tbr_analise_resultado.get(i).getTbr_analise_resultados().get(j).getTbc_status().getId() == 9) {
                        autorizaAtualizarResultado(tbc_statusAutorizado,tbc_statusService,tbr_analise_resultado.get(i).getTbr_analise_resultados().get(j),tbr_analise_resultadoRepository);
                        tbr_analise_resultadoRepository.save(tbr_analise_resultado.get(i).getTbr_analise_resultados().get(j));
                    } else
                    if (tbr_analise_resultado.get(i).getTbr_analise_resultados().get(j).getTbc_status().getId() == 10) {
                        tbr_analise_resultado.get(i).getTbr_analise_resultados().get(j).setTbc_status(tbr_analise_resultado.get(i).getTbr_analise_resultados().get(j).getTbc_status_ultimo());
                        tbrAnaliseResultado = tbr_analise_resultadoRepository.save(tbr_analise_resultado.get(i).getTbr_analise_resultados().get(j));
                    } else
                    {
                        Tbr_analise_resultado tbr_analise_resultadoOrigial = tbr_analise_resultadoRepository.getOne(tbr_analise_resultado.get(i).getTbr_analise_resultados().get(j).getId());
                        if (!tbr_analise_resultadoOrigial.getResultado().equals(tbr_analise_resultado.get(i).getTbr_analise_resultados().get(j).getResultado()))
                            //tenho que fazer um procedimento de armazenar histórico de alteração;
                            tbrAnaliseResultado = tbr_analise_resultadoRepository.save(tbr_analise_resultado.get(i).getTbr_analise_resultados().get(j));
                    }
                }

                if (tbr_analise_resultado.get(i).getAnalise() != null || tbr_analise_resultado.get(i).getAnalise() != null){
                    Long tbc_resultado_id = Long.valueOf(tbr_analise_resultado.get(i).getAnalise());
                    Tbr_analise tbr_analise = tbr_analiseService.findOne(tbc_resultado_id);
                    if (tbr_analise != null) {
                        tbr_analise.setObservacao(tbr_analise_resultado.get(i).getObservacao());
                        tbr_analiseService.save(tbr_analise);
                    }
                }
            }
            if (tbrAnaliseResultado != null) {
                boolean adiciona = true;
                for (int j = 0; j < updateStatusAnalise.size(); j++) {
                    if (updateStatusAnalise.get(j).getTbr_analise_id().equals(tbrAnaliseResultado.getTbr_amostra_id()))
                        adiciona = false;
                    break;
                }
                updateStatusAnalise.add(tbrAnaliseResultado);
            }
        }
        putStatusTbrAnalise(tbr_analiseService);
    }


    private static boolean salvarNovoResultado(Tbc_status tbc_statusCompletado,Tbc_statusService tbc_statusService,Tbr_analise_resultado tbr_analise_resultado){
        boolean salvar = true;
        if(tbc_statusCompletado == null)
            tbc_statusCompletado=  tbc_statusService.findOne(Long.valueOf(3));
        tbr_analise_resultado.setCompletado_por(tbr_analise_resultado.getCreatedBy());
        tbr_analise_resultado.setCompletado_data(Utility_Date.getDataHoraAtual());
        tbr_analise_resultado.setTbc_status(tbc_statusCompletado);
        if (tbr_analise_resultado.getResultado() != null)
            tbr_analise_resultado.setResultado_texto(tbr_analise_resultado.getResultado());
        else
            salvar = false;

        return salvar;
    }

    private static void autorizaAtualizarResultado(Tbc_status tbc_statusAutorizado,Tbc_statusService tbc_statusService,Tbr_analise_resultado tbr_analise_resultado, Tbr_analise_resultadoRepository tbr_analise_resultadoRepository){
        tbr_analise_resultado.setAutorizado_por(tbr_analise_resultado.getLastModifiedBy());
        tbr_analise_resultado.setAutorizado_data(Utility_Date.getDataHoraAtual());
        Tbr_analise_resultado tbr_analise_resultadoOriginal = tbr_analise_resultadoRepository.getOne(tbr_analise_resultado.getId());
        tbr_analise_resultado.setTbc_status_ultimo(tbr_analise_resultadoOriginal.getTbc_status());
        if (tbc_statusAutorizado == null)
            tbc_statusAutorizado = tbc_statusService.findOne(Long.valueOf(1));
        tbr_analise_resultado.setTbc_status(tbc_statusAutorizado);
    }

    private static void putStatusTbrAnalise (Tbr_analiseService tbr_analiseService){

        for (int i = 0; i <updateStatusAnalise.size() ; i++) {
            Tbr_analise_resultado tbrAnaliseResultado = updateStatusAnalise.get(i);
            log.debug("Tbr_resultado_analise: {}", tbrAnaliseResultado);
            Tbr_analise tbr_analise = tbr_analiseService.findOne(tbrAnaliseResultado.getTbr_analise_id());
            log.debug("Atualizando analise: {}", tbr_analise);
            log.debug("Status vazio: {}", tbr_analise != null);
            tbr_analise.setTbc_status(tbrAnaliseResultado.getTbc_status());
            if (!tbr_analise.getTbc_status().getId().equals(1))
                if (tbrAnaliseResultado.getTbc_status().getId().equals(10)){
                    tbr_analise.setAutorizado_data(tbrAnaliseResultado.getAutorizado_data());
                    tbr_analise.setAutorizado_por(tbrAnaliseResultado.getAutorizado_por());
                }
            tbr_analiseService.save(tbr_analise);
        }
    }
}
