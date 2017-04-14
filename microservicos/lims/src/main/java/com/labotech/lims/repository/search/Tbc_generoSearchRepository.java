package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_genero;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_genero entity.
 */
public interface Tbc_generoSearchRepository extends ElasticsearchRepository<Tbc_genero, Long> {
}
