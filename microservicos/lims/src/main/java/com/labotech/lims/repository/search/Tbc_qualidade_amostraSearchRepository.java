package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_qualidade_amostra;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_qualidade_amostra entity.
 */
public interface Tbc_qualidade_amostraSearchRepository extends ElasticsearchRepository<Tbc_qualidade_amostra, Long> {
}
