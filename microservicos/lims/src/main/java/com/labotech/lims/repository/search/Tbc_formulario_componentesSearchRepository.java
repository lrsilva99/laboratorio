package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_formulario_componentes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_formulario_componentes entity.
 */
public interface Tbc_formulario_componentesSearchRepository extends ElasticsearchRepository<Tbc_formulario_componentes, Long> {
}
