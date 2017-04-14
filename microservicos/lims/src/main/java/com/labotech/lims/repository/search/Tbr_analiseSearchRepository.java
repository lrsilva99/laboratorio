package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbr_analise;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbr_analise entity.
 */
public interface Tbr_analiseSearchRepository extends ElasticsearchRepository<Tbr_analise, Long> {
}
