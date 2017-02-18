package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_analises_componente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_analises_componente entity.
 */
public interface Tbc_analises_componenteSearchRepository extends ElasticsearchRepository<Tbc_analises_componente, Long> {
}
