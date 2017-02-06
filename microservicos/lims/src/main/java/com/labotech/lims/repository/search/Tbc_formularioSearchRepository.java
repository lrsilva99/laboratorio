package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_formulario;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_formulario entity.
 */
public interface Tbc_formularioSearchRepository extends ElasticsearchRepository<Tbc_formulario, Long> {
}
