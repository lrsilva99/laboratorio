package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_grupo_analise;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.repository.Tbc_grupo_analiseRepository;
import com.labotech.lims.service.Tbc_grupo_analiseService;
import com.labotech.lims.repository.search.Tbc_grupo_analiseSearchRepository;

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
 * Test class for the Tbc_grupo_analiseResource REST controller.
 *
 * @see Tbc_grupo_analiseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_grupo_analiseResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    @Inject
    private Tbc_grupo_analiseRepository tbc_grupo_analiseRepository;

    @Inject
    private Tbc_grupo_analiseService tbc_grupo_analiseService;

    @Inject
    private Tbc_grupo_analiseSearchRepository tbc_grupo_analiseSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_grupo_analiseMockMvc;

    private Tbc_grupo_analise tbc_grupo_analise;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_grupo_analiseResource tbc_grupo_analiseResource = new Tbc_grupo_analiseResource();
        ReflectionTestUtils.setField(tbc_grupo_analiseResource, "tbc_grupo_analiseService", tbc_grupo_analiseService);
        this.restTbc_grupo_analiseMockMvc = MockMvcBuilders.standaloneSetup(tbc_grupo_analiseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_grupo_analise createEntity(EntityManager em) {
        Tbc_grupo_analise tbc_grupo_analise = new Tbc_grupo_analise()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .removido(DEFAULT_REMOVIDO);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_grupo_analise.setTbc_instituicao(tbc_instituicao);
        return tbc_grupo_analise;
    }

    @Before
    public void initTest() {
        tbc_grupo_analiseSearchRepository.deleteAll();
        tbc_grupo_analise = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_grupo_analise() throws Exception {
        int databaseSizeBeforeCreate = tbc_grupo_analiseRepository.findAll().size();

        // Create the Tbc_grupo_analise

        restTbc_grupo_analiseMockMvc.perform(post("/api/tbc-grupo-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_grupo_analise)))
            .andExpect(status().isCreated());

        // Validate the Tbc_grupo_analise in the database
        List<Tbc_grupo_analise> tbc_grupo_analiseList = tbc_grupo_analiseRepository.findAll();
        assertThat(tbc_grupo_analiseList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_grupo_analise testTbc_grupo_analise = tbc_grupo_analiseList.get(tbc_grupo_analiseList.size() - 1);
        assertThat(testTbc_grupo_analise.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_grupo_analise.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_grupo_analise.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);

        // Validate the Tbc_grupo_analise in ElasticSearch
        Tbc_grupo_analise tbc_grupo_analiseEs = tbc_grupo_analiseSearchRepository.findOne(testTbc_grupo_analise.getId());
        assertThat(tbc_grupo_analiseEs).isEqualToComparingFieldByField(testTbc_grupo_analise);
    }

    @Test
    @Transactional
    public void createTbc_grupo_analiseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_grupo_analiseRepository.findAll().size();

        // Create the Tbc_grupo_analise with an existing ID
        Tbc_grupo_analise existingTbc_grupo_analise = new Tbc_grupo_analise();
        existingTbc_grupo_analise.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_grupo_analiseMockMvc.perform(post("/api/tbc-grupo-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_grupo_analise)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_grupo_analise> tbc_grupo_analiseList = tbc_grupo_analiseRepository.findAll();
        assertThat(tbc_grupo_analiseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_grupo_analiseRepository.findAll().size();
        // set the field null
        tbc_grupo_analise.setNome(null);

        // Create the Tbc_grupo_analise, which fails.

        restTbc_grupo_analiseMockMvc.perform(post("/api/tbc-grupo-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_grupo_analise)))
            .andExpect(status().isBadRequest());

        List<Tbc_grupo_analise> tbc_grupo_analiseList = tbc_grupo_analiseRepository.findAll();
        assertThat(tbc_grupo_analiseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_grupo_analises() throws Exception {
        // Initialize the database
        tbc_grupo_analiseRepository.saveAndFlush(tbc_grupo_analise);

        // Get all the tbc_grupo_analiseList
        restTbc_grupo_analiseMockMvc.perform(get("/api/tbc-grupo-analises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_grupo_analise.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTbc_grupo_analise() throws Exception {
        // Initialize the database
        tbc_grupo_analiseRepository.saveAndFlush(tbc_grupo_analise);

        // Get the tbc_grupo_analise
        restTbc_grupo_analiseMockMvc.perform(get("/api/tbc-grupo-analises/{id}", tbc_grupo_analise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_grupo_analise.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_grupo_analise() throws Exception {
        // Get the tbc_grupo_analise
        restTbc_grupo_analiseMockMvc.perform(get("/api/tbc-grupo-analises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_grupo_analise() throws Exception {
        // Initialize the database
        tbc_grupo_analiseService.save(tbc_grupo_analise);

        int databaseSizeBeforeUpdate = tbc_grupo_analiseRepository.findAll().size();

        // Update the tbc_grupo_analise
        Tbc_grupo_analise updatedTbc_grupo_analise = tbc_grupo_analiseRepository.findOne(tbc_grupo_analise.getId());
        updatedTbc_grupo_analise
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .removido(UPDATED_REMOVIDO);

        restTbc_grupo_analiseMockMvc.perform(put("/api/tbc-grupo-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_grupo_analise)))
            .andExpect(status().isOk());

        // Validate the Tbc_grupo_analise in the database
        List<Tbc_grupo_analise> tbc_grupo_analiseList = tbc_grupo_analiseRepository.findAll();
        assertThat(tbc_grupo_analiseList).hasSize(databaseSizeBeforeUpdate);
        Tbc_grupo_analise testTbc_grupo_analise = tbc_grupo_analiseList.get(tbc_grupo_analiseList.size() - 1);
        assertThat(testTbc_grupo_analise.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_grupo_analise.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_grupo_analise.isRemovido()).isEqualTo(UPDATED_REMOVIDO);

        // Validate the Tbc_grupo_analise in ElasticSearch
        Tbc_grupo_analise tbc_grupo_analiseEs = tbc_grupo_analiseSearchRepository.findOne(testTbc_grupo_analise.getId());
        assertThat(tbc_grupo_analiseEs).isEqualToComparingFieldByField(testTbc_grupo_analise);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_grupo_analise() throws Exception {
        int databaseSizeBeforeUpdate = tbc_grupo_analiseRepository.findAll().size();

        // Create the Tbc_grupo_analise

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_grupo_analiseMockMvc.perform(put("/api/tbc-grupo-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_grupo_analise)))
            .andExpect(status().isCreated());

        // Validate the Tbc_grupo_analise in the database
        List<Tbc_grupo_analise> tbc_grupo_analiseList = tbc_grupo_analiseRepository.findAll();
        assertThat(tbc_grupo_analiseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_grupo_analise() throws Exception {
        // Initialize the database
        tbc_grupo_analiseService.save(tbc_grupo_analise);

        int databaseSizeBeforeDelete = tbc_grupo_analiseRepository.findAll().size();

        // Get the tbc_grupo_analise
        restTbc_grupo_analiseMockMvc.perform(delete("/api/tbc-grupo-analises/{id}", tbc_grupo_analise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_grupo_analiseExistsInEs = tbc_grupo_analiseSearchRepository.exists(tbc_grupo_analise.getId());
        assertThat(tbc_grupo_analiseExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_grupo_analise> tbc_grupo_analiseList = tbc_grupo_analiseRepository.findAll();
        assertThat(tbc_grupo_analiseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_grupo_analise() throws Exception {
        // Initialize the database
        tbc_grupo_analiseService.save(tbc_grupo_analise);

        // Search the tbc_grupo_analise
        restTbc_grupo_analiseMockMvc.perform(get("/api/_search/tbc-grupo-analises?query=id:" + tbc_grupo_analise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_grupo_analise.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }
}
