package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_instituicao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_instituicao entity.
 */
public interface Tbc_instituicaoSearchRepository extends ElasticsearchRepository<Tbc_instituicao, Long> {
}
