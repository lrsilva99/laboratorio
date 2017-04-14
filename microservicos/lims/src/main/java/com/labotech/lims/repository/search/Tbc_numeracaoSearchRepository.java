package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_numeracao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_numeracao entity.
 */
public interface Tbc_numeracaoSearchRepository extends ElasticsearchRepository<Tbc_numeracao, Long> {
}
