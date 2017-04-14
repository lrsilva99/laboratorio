package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_qualidade_amostra;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.repository.Tbc_qualidade_amostraRepository;
import com.labotech.lims.service.Tbc_qualidade_amostraService;
import com.labotech.lims.repository.search.Tbc_qualidade_amostraSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Tbc_qualidade_amostraResource REST controller.
 *
 * @see Tbc_qualidade_amostraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_qualidade_amostraResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVER = false;
    private static final Boolean UPDATED_REMOVER = true;

    @Inject
    private Tbc_qualidade_amostraRepository tbc_qualidade_amostraRepository;

    @Inject
    private Tbc_qualidade_amostraService tbc_qualidade_amostraService;

    @Inject
    private Tbc_qualidade_amostraSearchRepository tbc_qualidade_amostraSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_qualidade_amostraMockMvc;

    private Tbc_qualidade_amostra tbc_qualidade_amostra;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_qualidade_amostraResource tbc_qualidade_amostraResource = new Tbc_qualidade_amostraResource();
        ReflectionTestUtils.setField(tbc_qualidade_amostraResource, "tbc_qualidade_amostraService", tbc_qualidade_amostraService);
        this.restTbc_qualidade_amostraMockMvc = MockMvcBuilders.standaloneSetup(tbc_qualidade_amostraResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_qualidade_amostra createEntity(EntityManager em) {
        Tbc_qualidade_amostra tbc_qualidade_amostra = new Tbc_qualidade_amostra()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .remover(DEFAULT_REMOVER);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_qualidade_amostra.setTbc_instituicao(tbc_instituicao);
        return tbc_qualidade_amostra;
    }

    @Before
    public void initTest() {
        tbc_qualidade_amostraSearchRepository.deleteAll();
        tbc_qualidade_amostra = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_qualidade_amostra() throws Exception {
        int databaseSizeBeforeCreate = tbc_qualidade_amostraRepository.findAll().size();

        // Create the Tbc_qualidade_amostra

        restTbc_qualidade_amostraMockMvc.perform(post("/api/tbc-qualidade-amostras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_qualidade_amostra)))
            .andExpect(status().isCreated());

        // Validate the Tbc_qualidade_amostra in the database
        List<Tbc_qualidade_amostra> tbc_qualidade_amostraList = tbc_qualidade_amostraRepository.findAll();
        assertThat(tbc_qualidade_amostraList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_qualidade_amostra testTbc_qualidade_amostra = tbc_qualidade_amostraList.get(tbc_qualidade_amostraList.size() - 1);
        assertThat(testTbc_qualidade_amostra.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_qualidade_amostra.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_qualidade_amostra.isRemover()).isEqualTo(DEFAULT_REMOVER);

        // Validate the Tbc_qualidade_amostra in ElasticSearch
        Tbc_qualidade_amostra tbc_qualidade_amostraEs = tbc_qualidade_amostraSearchRepository.findOne(testTbc_qualidade_amostra.getId());
        assertThat(tbc_qualidade_amostraEs).isEqualToComparingFieldByField(testTbc_qualidade_amostra);
    }

    @Test
    @Transactional
    public void createTbc_qualidade_amostraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_qualidade_amostraRepository.findAll().size();

        // Create the Tbc_qualidade_amostra with an existing ID
        Tbc_qualidade_amostra existingTbc_qualidade_amostra = new Tbc_qualidade_amostra();
        existingTbc_qualidade_amostra.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_qualidade_amostraMockMvc.perform(post("/api/tbc-qualidade-amostras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_qualidade_amostra)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_qualidade_amostra> tbc_qualidade_amostraList = tbc_qualidade_amostraRepository.findAll();
        assertThat(tbc_qualidade_amostraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_qualidade_amostraRepository.findAll().size();
        // set the field null
        tbc_qualidade_amostra.setNome(null);

        // Create the Tbc_qualidade_amostra, which fails.

        restTbc_qualidade_amostraMockMvc.perform(post("/api/tbc-qualidade-amostras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_qualidade_amostra)))
            .andExpect(status().isBadRequest());

        List<Tbc_qualidade_amostra> tbc_qualidade_amostraList = tbc_qualidade_amostraRepository.findAll();
        assertThat(tbc_qualidade_amostraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_qualidade_amostras() throws Exception {
        // Initialize the database
        tbc_qualidade_amostraRepository.saveAndFlush(tbc_qualidade_amostra);

        // Get all the tbc_qualidade_amostraList
        restTbc_qualidade_amostraMockMvc.perform(get("/api/tbc-qualidade-amostras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_qualidade_amostra.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].remover").value(hasItem(DEFAULT_REMOVER.booleanValue())));
    }

    @Test
    @Transactional
    public void getTbc_qualidade_amostra() throws Exception {
        // Initialize the database
        tbc_qualidade_amostraRepository.saveAndFlush(tbc_qualidade_amostra);

        // Get the tbc_qualidade_amostra
        restTbc_qualidade_amostraMockMvc.perform(get("/api/tbc-qualidade-amostras/{id}", tbc_qualidade_amostra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_qualidade_amostra.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.remover").value(DEFAULT_REMOVER.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_qualidade_amostra() throws Exception {
        // Get the tbc_qualidade_amostra
        restTbc_qualidade_amostraMockMvc.perform(get("/api/tbc-qualidade-amostras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_qualidade_amostra() throws Exception {
        // Initialize the database
        tbc_qualidade_amostraService.save(tbc_qualidade_amostra);

        int databaseSizeBeforeUpdate = tbc_qualidade_amostraRepository.findAll().size();

        // Update the tbc_qualidade_amostra
        Tbc_qualidade_amostra updatedTbc_qualidade_amostra = tbc_qualidade_amostraRepository.findOne(tbc_qualidade_amostra.getId());
        updatedTbc_qualidade_amostra
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .remover(UPDATED_REMOVER);

        restTbc_qualidade_amostraMockMvc.perform(put("/api/tbc-qualidade-amostras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_qualidade_amostra)))
            .andExpect(status().isOk());

        // Validate the Tbc_qualidade_amostra in the database
        List<Tbc_qualidade_amostra> tbc_qualidade_amostraList = tbc_qualidade_amostraRepository.findAll();
        assertThat(tbc_qualidade_amostraList).hasSize(databaseSizeBeforeUpdate);
        Tbc_qualidade_amostra testTbc_qualidade_amostra = tbc_qualidade_amostraList.get(tbc_qualidade_amostraList.size() - 1);
        assertThat(testTbc_qualidade_amostra.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_qualidade_amostra.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_qualidade_amostra.isRemover()).isEqualTo(UPDATED_REMOVER);

        // Validate the Tbc_qualidade_amostra in ElasticSearch
        Tbc_qualidade_amostra tbc_qualidade_amostraEs = tbc_qualidade_amostraSearchRepository.findOne(testTbc_qualidade_amostra.getId());
        assertThat(tbc_qualidade_amostraEs).isEqualToComparingFieldByField(testTbc_qualidade_amostra);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_qualidade_amostra() throws Exception {
        int databaseSizeBeforeUpdate = tbc_qualidade_amostraRepository.findAll().size();

        // Create the Tbc_qualidade_amostra

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_qualidade_amostraMockMvc.perform(put("/api/tbc-qualidade-amostras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_qualidade_amostra)))
            .andExpect(status().isCreated());

        // Validate the Tbc_qualidade_amostra in the database
        List<Tbc_qualidade_amostra> tbc_qualidade_amostraList = tbc_qualidade_amostraRepository.findAll();
        assertThat(tbc_qualidade_amostraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_qualidade_amostra() throws Exception {
        // Initialize the database
        tbc_qualidade_amostraService.save(tbc_qualidade_amostra);

        int databaseSizeBeforeDelete = tbc_qualidade_amostraRepository.findAll().size();

        // Get the tbc_qualidade_amostra
        restTbc_qualidade_amostraMockMvc.perform(delete("/api/tbc-qualidade-amostras/{id}", tbc_qualidade_amostra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_qualidade_amostraExistsInEs = tbc_qualidade_amostraSearchRepository.exists(tbc_qualidade_amostra.getId());
        assertThat(tbc_qualidade_amostraExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_qualidade_amostra> tbc_qualidade_amostraList = tbc_qualidade_amostraRepository.findAll();
        assertThat(tbc_qualidade_amostraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_qualidade_amostra() throws Exception {
        // Initialize the database
        tbc_qualidade_amostraService.save(tbc_qualidade_amostra);

        // Search the tbc_qualidade_amostra
        restTbc_qualidade_amostraMockMvc.perform(get("/api/_search/tbc-qualidade-amostras?query=id:" + tbc_qualidade_amostra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_qualidade_amostra.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].remover").value(hasItem(DEFAULT_REMOVER.booleanValue())));
    }
}
