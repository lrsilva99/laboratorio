package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_analises;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_analises entity.
 */
public interface Tbc_analisesSearchRepository extends ElasticsearchRepository<Tbc_analises, Long> {
}
