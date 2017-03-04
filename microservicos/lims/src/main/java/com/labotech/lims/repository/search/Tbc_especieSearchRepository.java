package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_especie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_especie entity.
 */
public interface Tbc_especieSearchRepository extends ElasticsearchRepository<Tbc_especie, Long> {
}
