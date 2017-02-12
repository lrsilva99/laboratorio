package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_plano_teste;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_plano_teste entity.
 */
public interface Tbc_plano_testeSearchRepository extends ElasticsearchRepository<Tbc_plano_teste, Long> {
}
