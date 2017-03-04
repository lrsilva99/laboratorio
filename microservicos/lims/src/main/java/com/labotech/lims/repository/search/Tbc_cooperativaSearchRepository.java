package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_cooperativa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_cooperativa entity.
 */
public interface Tbc_cooperativaSearchRepository extends ElasticsearchRepository<Tbc_cooperativa, Long> {
}
