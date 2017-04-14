package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_forma_armazenamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_forma_armazenamento entity.
 */
public interface Tbc_forma_armazenamentoSearchRepository extends ElasticsearchRepository<Tbc_forma_armazenamento, Long> {
}
