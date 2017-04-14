package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_analise_resultado;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_analise_resultado entity.
 */
public interface Tbc_analise_resultadoSearchRepository extends ElasticsearchRepository<Tbc_analise_resultado, Long> {
}
