package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_frases;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_frases entity.
 */
public interface Tbc_frasesSearchRepository extends ElasticsearchRepository<Tbc_frases, Long> {
}
