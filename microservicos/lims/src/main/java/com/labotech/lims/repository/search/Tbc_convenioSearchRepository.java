package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_convenio;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_convenio entity.
 */
public interface Tbc_convenioSearchRepository extends ElasticsearchRepository<Tbc_convenio, Long> {
}
