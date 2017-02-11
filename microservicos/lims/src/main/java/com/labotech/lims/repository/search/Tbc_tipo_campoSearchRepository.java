package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_tipo_campo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_tipo_campo entity.
 */
public interface Tbc_tipo_campoSearchRepository extends ElasticsearchRepository<Tbc_tipo_campo, Long> {
}
