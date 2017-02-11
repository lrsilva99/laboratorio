package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_cliente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_cliente entity.
 */
public interface Tbc_clienteSearchRepository extends ElasticsearchRepository<Tbc_cliente, Long> {
}
