package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbr_amostra;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbr_amostra entity.
 */
public interface Tbr_amostraSearchRepository extends ElasticsearchRepository<Tbr_amostra, Long> {
}
