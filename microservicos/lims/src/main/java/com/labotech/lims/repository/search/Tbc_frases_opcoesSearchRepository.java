package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_frases_opcoes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_frases_opcoes entity.
 */
public interface Tbc_frases_opcoesSearchRepository extends ElasticsearchRepository<Tbc_frases_opcoes, Long> {
}
