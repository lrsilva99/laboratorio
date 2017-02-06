package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_grupo_analise;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_grupo_analise entity.
 */
public interface Tbc_grupo_analiseSearchRepository extends ElasticsearchRepository<Tbc_grupo_analise, Long> {
}
