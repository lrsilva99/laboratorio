package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_grupo_cliente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_grupo_cliente entity.
 */
public interface Tbc_grupo_clienteSearchRepository extends ElasticsearchRepository<Tbc_grupo_cliente, Long> {
}
