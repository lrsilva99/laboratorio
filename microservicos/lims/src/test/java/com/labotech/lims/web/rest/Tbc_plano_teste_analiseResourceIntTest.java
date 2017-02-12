package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_plano_teste_analise;
import com.labotech.lims.domain.Tbc_plano_teste;
import com.labotech.lims.domain.Tbc_analises;
import com.labotech.lims.repository.Tbc_plano_teste_analiseRepository;
import com.labotech.lims.service.Tbc_plano_teste_analiseService;
import com.labotech.lims.repository.search.Tbc_plano_teste_analiseSearchRepository;

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

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Tbc_plano_teste_analiseResource REST controller.
 *
 * @see Tbc_plano_teste_analiseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_plano_teste_analiseResourceIntTest {

    private static final Integer DEFAULT_ORDEM = 1;
    private static final Integer UPDATED_ORDEM = 2;

    @Inject
    private Tbc_plano_teste_analiseRepository tbc_plano_teste_analiseRepository;

    @Inject
    private Tbc_plano_teste_analiseService tbc_plano_teste_analiseService;

    @Inject
    private Tbc_plano_teste_analiseSearchRepository tbc_plano_teste_analiseSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_plano_teste_analiseMockMvc;

    private Tbc_plano_teste_analise tbc_plano_teste_analise;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_plano_teste_analiseResource tbc_plano_teste_analiseResource = new Tbc_plano_teste_analiseResource();
        ReflectionTestUtils.setField(tbc_plano_teste_analiseResource, "tbc_plano_teste_analiseService", tbc_plano_teste_analiseService);
        this.restTbc_plano_teste_analiseMockMvc = MockMvcBuilders.standaloneSetup(tbc_plano_teste_analiseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_plano_teste_analise createEntity(EntityManager em) {
        Tbc_plano_teste_analise tbc_plano_teste_analise = new Tbc_plano_teste_analise()
                .ordem(DEFAULT_ORDEM);
        // Add required entity
        Tbc_plano_teste tbc_plano_teste = Tbc_plano_testeResourceIntTest.createEntity(em);
        em.persist(tbc_plano_teste);
        em.flush();
        tbc_plano_teste_analise.setTbc_plano_teste(tbc_plano_teste);
        // Add required entity
        Tbc_analises tbc_analises = Tbc_analisesResourceIntTest.createEntity(em);
        em.persist(tbc_analises);
        em.flush();
        tbc_plano_teste_analise.setTbc_analises(tbc_analises);
        return tbc_plano_teste_analise;
    }

    @Before
    public void initTest() {
        tbc_plano_teste_analiseSearchRepository.deleteAll();
        tbc_plano_teste_analise = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_plano_teste_analise() throws Exception {
        int databaseSizeBeforeCreate = tbc_plano_teste_analiseRepository.findAll().size();

        // Create the Tbc_plano_teste_analise

        restTbc_plano_teste_analiseMockMvc.perform(post("/api/tbc-plano-teste-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_plano_teste_analise)))
            .andExpect(status().isCreated());

        // Validate the Tbc_plano_teste_analise in the database
        List<Tbc_plano_teste_analise> tbc_plano_teste_analiseList = tbc_plano_teste_analiseRepository.findAll();
        assertThat(tbc_plano_teste_analiseList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_plano_teste_analise testTbc_plano_teste_analise = tbc_plano_teste_analiseList.get(tbc_plano_teste_analiseList.size() - 1);
        assertThat(testTbc_plano_teste_analise.getOrdem()).isEqualTo(DEFAULT_ORDEM);

        // Validate the Tbc_plano_teste_analise in ElasticSearch
        Tbc_plano_teste_analise tbc_plano_teste_analiseEs = tbc_plano_teste_analiseSearchRepository.findOne(testTbc_plano_teste_analise.getId());
        assertThat(tbc_plano_teste_analiseEs).isEqualToComparingFieldByField(testTbc_plano_teste_analise);
    }

    @Test
    @Transactional
    public void createTbc_plano_teste_analiseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_plano_teste_analiseRepository.findAll().size();

        // Create the Tbc_plano_teste_analise with an existing ID
        Tbc_plano_teste_analise existingTbc_plano_teste_analise = new Tbc_plano_teste_analise();
        existingTbc_plano_teste_analise.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_plano_teste_analiseMockMvc.perform(post("/api/tbc-plano-teste-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_plano_teste_analise)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_plano_teste_analise> tbc_plano_teste_analiseList = tbc_plano_teste_analiseRepository.findAll();
        assertThat(tbc_plano_teste_analiseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrdemIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_plano_teste_analiseRepository.findAll().size();
        // set the field null
        tbc_plano_teste_analise.setOrdem(null);

        // Create the Tbc_plano_teste_analise, which fails.

        restTbc_plano_teste_analiseMockMvc.perform(post("/api/tbc-plano-teste-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_plano_teste_analise)))
            .andExpect(status().isBadRequest());

        List<Tbc_plano_teste_analise> tbc_plano_teste_analiseList = tbc_plano_teste_analiseRepository.findAll();
        assertThat(tbc_plano_teste_analiseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_plano_teste_analises() throws Exception {
        // Initialize the database
        tbc_plano_teste_analiseRepository.saveAndFlush(tbc_plano_teste_analise);

        // Get all the tbc_plano_teste_analiseList
        restTbc_plano_teste_analiseMockMvc.perform(get("/api/tbc-plano-teste-analises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_plano_teste_analise.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)));
    }

    @Test
    @Transactional
    public void getTbc_plano_teste_analise() throws Exception {
        // Initialize the database
        tbc_plano_teste_analiseRepository.saveAndFlush(tbc_plano_teste_analise);

        // Get the tbc_plano_teste_analise
        restTbc_plano_teste_analiseMockMvc.perform(get("/api/tbc-plano-teste-analises/{id}", tbc_plano_teste_analise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_plano_teste_analise.getId().intValue()))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_plano_teste_analise() throws Exception {
        // Get the tbc_plano_teste_analise
        restTbc_plano_teste_analiseMockMvc.perform(get("/api/tbc-plano-teste-analises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_plano_teste_analise() throws Exception {
        // Initialize the database
        tbc_plano_teste_analiseService.save(tbc_plano_teste_analise);

        int databaseSizeBeforeUpdate = tbc_plano_teste_analiseRepository.findAll().size();

        // Update the tbc_plano_teste_analise
        Tbc_plano_teste_analise updatedTbc_plano_teste_analise = tbc_plano_teste_analiseRepository.findOne(tbc_plano_teste_analise.getId());
        updatedTbc_plano_teste_analise
                .ordem(UPDATED_ORDEM);

        restTbc_plano_teste_analiseMockMvc.perform(put("/api/tbc-plano-teste-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_plano_teste_analise)))
            .andExpect(status().isOk());

        // Validate the Tbc_plano_teste_analise in the database
        List<Tbc_plano_teste_analise> tbc_plano_teste_analiseList = tbc_plano_teste_analiseRepository.findAll();
        assertThat(tbc_plano_teste_analiseList).hasSize(databaseSizeBeforeUpdate);
        Tbc_plano_teste_analise testTbc_plano_teste_analise = tbc_plano_teste_analiseList.get(tbc_plano_teste_analiseList.size() - 1);
        assertThat(testTbc_plano_teste_analise.getOrdem()).isEqualTo(UPDATED_ORDEM);

        // Validate the Tbc_plano_teste_analise in ElasticSearch
        Tbc_plano_teste_analise tbc_plano_teste_analiseEs = tbc_plano_teste_analiseSearchRepository.findOne(testTbc_plano_teste_analise.getId());
        assertThat(tbc_plano_teste_analiseEs).isEqualToComparingFieldByField(testTbc_plano_teste_analise);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_plano_teste_analise() throws Exception {
        int databaseSizeBeforeUpdate = tbc_plano_teste_analiseRepository.findAll().size();

        // Create the Tbc_plano_teste_analise

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_plano_teste_analiseMockMvc.perform(put("/api/tbc-plano-teste-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_plano_teste_analise)))
            .andExpect(status().isCreated());

        // Validate the Tbc_plano_teste_analise in the database
        List<Tbc_plano_teste_analise> tbc_plano_teste_analiseList = tbc_plano_teste_analiseRepository.findAll();
        assertThat(tbc_plano_teste_analiseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_plano_teste_analise() throws Exception {
        // Initialize the database
        tbc_plano_teste_analiseService.save(tbc_plano_teste_analise);

        int databaseSizeBeforeDelete = tbc_plano_teste_analiseRepository.findAll().size();

        // Get the tbc_plano_teste_analise
        restTbc_plano_teste_analiseMockMvc.perform(delete("/api/tbc-plano-teste-analises/{id}", tbc_plano_teste_analise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_plano_teste_analiseExistsInEs = tbc_plano_teste_analiseSearchRepository.exists(tbc_plano_teste_analise.getId());
        assertThat(tbc_plano_teste_analiseExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_plano_teste_analise> tbc_plano_teste_analiseList = tbc_plano_teste_analiseRepository.findAll();
        assertThat(tbc_plano_teste_analiseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_plano_teste_analise() throws Exception {
        // Initialize the database
        tbc_plano_teste_analiseService.save(tbc_plano_teste_analise);

        // Search the tbc_plano_teste_analise
        restTbc_plano_teste_analiseMockMvc.perform(get("/api/_search/tbc-plano-teste-analises?query=id:" + tbc_plano_teste_analise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_plano_teste_analise.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)));
    }
}
