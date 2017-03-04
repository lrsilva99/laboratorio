package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_proprietario;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_proprietario entity.
 */
public interface Tbc_proprietarioSearchRepository extends ElasticsearchRepository<Tbc_proprietario, Long> {
}
