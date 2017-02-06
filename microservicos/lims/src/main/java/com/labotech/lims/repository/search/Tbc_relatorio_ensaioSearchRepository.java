package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_relatorio_ensaio;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_relatorio_ensaio entity.
 */
public interface Tbc_relatorio_ensaioSearchRepository extends ElasticsearchRepository<Tbc_relatorio_ensaio, Long> {
}
