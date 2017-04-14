package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbr_analise_resultado;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbr_analise_resultado entity.
 */
public interface Tbr_analise_resultadoSearchRepository extends ElasticsearchRepository<Tbr_analise_resultado, Long> {
}
