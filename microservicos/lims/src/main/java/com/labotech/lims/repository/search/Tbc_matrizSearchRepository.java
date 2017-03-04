package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_matriz;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_matriz entity.
 */
public interface Tbc_matrizSearchRepository extends ElasticsearchRepository<Tbc_matriz, Long> {
}
