package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_status;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_status entity.
 */
public interface Tbc_statusSearchRepository extends ElasticsearchRepository<Tbc_status, Long> {
}
