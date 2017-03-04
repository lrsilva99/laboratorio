package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_fazenda;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_fazenda entity.
 */
public interface Tbc_fazendaSearchRepository extends ElasticsearchRepository<Tbc_fazenda, Long> {
}
