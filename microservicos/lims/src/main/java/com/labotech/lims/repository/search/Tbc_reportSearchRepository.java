package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_report;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_report entity.
 */
public interface Tbc_reportSearchRepository extends ElasticsearchRepository<Tbc_report, Long> {
}
