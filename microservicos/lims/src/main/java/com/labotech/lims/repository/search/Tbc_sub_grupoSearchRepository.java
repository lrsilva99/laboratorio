package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_sub_grupo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_sub_grupo entity.
 */
public interface Tbc_sub_grupoSearchRepository extends ElasticsearchRepository<Tbc_sub_grupo, Long> {
}
