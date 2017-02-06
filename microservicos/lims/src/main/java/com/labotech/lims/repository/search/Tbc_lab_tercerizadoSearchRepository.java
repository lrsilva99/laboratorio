package com.labotech.lims.repository.search;

import com.labotech.lims.domain.Tbc_lab_tercerizado;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tbc_lab_tercerizado entity.
 */
public interface Tbc_lab_tercerizadoSearchRepository extends ElasticsearchRepository<Tbc_lab_tercerizado, Long> {
}
