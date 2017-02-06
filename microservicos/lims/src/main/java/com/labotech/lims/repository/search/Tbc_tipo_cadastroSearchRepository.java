package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_tipo_cadastro;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_tipo_cadastro entity.
 */
public interface Tbc_tipo_cadastroSearchRepository extends ElasticsearchRepository<Tbc_tipo_cadastro, Long> {
}
