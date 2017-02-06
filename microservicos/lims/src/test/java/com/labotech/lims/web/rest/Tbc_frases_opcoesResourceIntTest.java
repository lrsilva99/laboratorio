package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_frases_opcoes;
import com.labotech.lims.domain.Tbc_frases;
import com.labotech.lims.repository.Tbc_frases_opcoesRepository;
import com.labotech.lims.service.Tbc_frases_opcoesService;
import com.labotech.lims.repository.search.Tbc_frases_opcoesSearchRepository;

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
 * Test class for the Tbc_frases_opcoesResource REST controller.
 *
 * @see Tbc_frases_opcoesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_frases_opcoesResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    @Inject
    private Tbc_frases_opcoesRepository tbc_frases_opcoesRepository;

    @Inject
    private Tbc_frases_opcoesService tbc_frases_opcoesService;

    @Inject
    private Tbc_frases_opcoesSearchRepository tbc_frases_opcoesSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_frases_opcoesMockMvc;

    private Tbc_frases_opcoes tbc_frases_opcoes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_frases_opcoesResource tbc_frases_opcoesResource = new Tbc_frases_opcoesResource();
        ReflectionTestUtils.setField(tbc_frases_opcoesResource, "tbc_frases_opcoesService", tbc_frases_opcoesService);
        this.restTbc_frases_opcoesMockMvc = MockMvcBuilders.standaloneSetup(tbc_frases_opcoesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_frases_opcoes createEntity(EntityManager em) {
        Tbc_frases_opcoes tbc_frases_opcoes = new Tbc_frases_opcoes()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .removido(DEFAULT_REMOVIDO);
        // Add required entity
        Tbc_frases tbc_frases = Tbc_frasesResourceIntTest.createEntity(em);
        em.persist(tbc_frases);
        em.flush();
        tbc_frases_opcoes.setTbc_frases(tbc_frases);
        return tbc_frases_opcoes;
    }

    @Before
    public void initTest() {
        tbc_frases_opcoesSearchRepository.deleteAll();
        tbc_frases_opcoes = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_frases_opcoes() throws Exception {
        int databaseSizeBeforeCreate = tbc_frases_opcoesRepository.findAll().size();

        // Create the Tbc_frases_opcoes

        restTbc_frases_opcoesMockMvc.perform(post("/api/tbc-frases-opcoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_frases_opcoes)))
            .andExpect(status().isCreated());

        // Validate the Tbc_frases_opcoes in the database
        List<Tbc_frases_opcoes> tbc_frases_opcoesList = tbc_frases_opcoesRepository.findAll();
        assertThat(tbc_frases_opcoesList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_frases_opcoes testTbc_frases_opcoes = tbc_frases_opcoesList.get(tbc_frases_opcoesList.size() - 1);
        assertThat(testTbc_frases_opcoes.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_frases_opcoes.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_frases_opcoes.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);

        // Validate the Tbc_frases_opcoes in ElasticSearch
        Tbc_frases_opcoes tbc_frases_opcoesEs = tbc_frases_opcoesSearchRepository.findOne(testTbc_frases_opcoes.getId());
        assertThat(tbc_frases_opcoesEs).isEqualToComparingFieldByField(testTbc_frases_opcoes);
    }

    @Test
    @Transactional
    public void createTbc_frases_opcoesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_frases_opcoesRepository.findAll().size();

        // Create the Tbc_frases_opcoes with an existing ID
        Tbc_frases_opcoes existingTbc_frases_opcoes = new Tbc_frases_opcoes();
        existingTbc_frases_opcoes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_frases_opcoesMockMvc.perform(post("/api/tbc-frases-opcoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_frases_opcoes)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_frases_opcoes> tbc_frases_opcoesList = tbc_frases_opcoesRepository.findAll();
        assertThat(tbc_frases_opcoesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_frases_opcoesRepository.findAll().size();
        // set the field null
        tbc_frases_opcoes.setNome(null);

        // Create the Tbc_frases_opcoes, which fails.

        restTbc_frases_opcoesMockMvc.perform(post("/api/tbc-frases-opcoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_frases_opcoes)))
            .andExpect(status().isBadRequest());

        List<Tbc_frases_opcoes> tbc_frases_opcoesList = tbc_frases_opcoesRepository.findAll();
        assertThat(tbc_frases_opcoesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_frases_opcoesRepository.findAll().size();
        // set the field null
        tbc_frases_opcoes.setDescricao(null);

        // Create the Tbc_frases_opcoes, which fails.

        restTbc_frases_opcoesMockMvc.perform(post("/api/tbc-frases-opcoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_frases_opcoes)))
            .andExpect(status().isBadRequest());

        List<Tbc_frases_opcoes> tbc_frases_opcoesList = tbc_frases_opcoesRepository.findAll();
        assertThat(tbc_frases_opcoesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_frases_opcoes() throws Exception {
        // Initialize the database
        tbc_frases_opcoesRepository.saveAndFlush(tbc_frases_opcoes);

        // Get all the tbc_frases_opcoesList
        restTbc_frases_opcoesMockMvc.perform(get("/api/tbc-frases-opcoes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_frases_opcoes.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTbc_frases_opcoes() throws Exception {
        // Initialize the database
        tbc_frases_opcoesRepository.saveAndFlush(tbc_frases_opcoes);

        // Get the tbc_frases_opcoes
        restTbc_frases_opcoesMockMvc.perform(get("/api/tbc-frases-opcoes/{id}", tbc_frases_opcoes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_frases_opcoes.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_frases_opcoes() throws Exception {
        // Get the tbc_frases_opcoes
        restTbc_frases_opcoesMockMvc.perform(get("/api/tbc-frases-opcoes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_frases_opcoes() throws Exception {
        // Initialize the database
        tbc_frases_opcoesService.save(tbc_frases_opcoes);

        int databaseSizeBeforeUpdate = tbc_frases_opcoesRepository.findAll().size();

        // Update the tbc_frases_opcoes
        Tbc_frases_opcoes updatedTbc_frases_opcoes = tbc_frases_opcoesRepository.findOne(tbc_frases_opcoes.getId());
        updatedTbc_frases_opcoes
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .removido(UPDATED_REMOVIDO);

        restTbc_frases_opcoesMockMvc.perform(put("/api/tbc-frases-opcoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_frases_opcoes)))
            .andExpect(status().isOk());

        // Validate the Tbc_frases_opcoes in the database
        List<Tbc_frases_opcoes> tbc_frases_opcoesList = tbc_frases_opcoesRepository.findAll();
        assertThat(tbc_frases_opcoesList).hasSize(databaseSizeBeforeUpdate);
        Tbc_frases_opcoes testTbc_frases_opcoes = tbc_frases_opcoesList.get(tbc_frases_opcoesList.size() - 1);
        assertThat(testTbc_frases_opcoes.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_frases_opcoes.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_frases_opcoes.isRemovido()).isEqualTo(UPDATED_REMOVIDO);

        // Validate the Tbc_frases_opcoes in ElasticSearch
        Tbc_frases_opcoes tbc_frases_opcoesEs = tbc_frases_opcoesSearchRepository.findOne(testTbc_frases_opcoes.getId());
        assertThat(tbc_frases_opcoesEs).isEqualToComparingFieldByField(testTbc_frases_opcoes);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_frases_opcoes() throws Exception {
        int databaseSizeBeforeUpdate = tbc_frases_opcoesRepository.findAll().size();

        // Create the Tbc_frases_opcoes

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_frases_opcoesMockMvc.perform(put("/api/tbc-frases-opcoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_frases_opcoes)))
            .andExpect(status().isCreated());

        // Validate the Tbc_frases_opcoes in the database
        List<Tbc_frases_opcoes> tbc_frases_opcoesList = tbc_frases_opcoesRepository.findAll();
        assertThat(tbc_frases_opcoesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_frases_opcoes() throws Exception {
        // Initialize the database
        tbc_frases_opcoesService.save(tbc_frases_opcoes);

        int databaseSizeBeforeDelete = tbc_frases_opcoesRepository.findAll().size();

        // Get the tbc_frases_opcoes
        restTbc_frases_opcoesMockMvc.perform(delete("/api/tbc-frases-opcoes/{id}", tbc_frases_opcoes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_frases_opcoesExistsInEs = tbc_frases_opcoesSearchRepository.exists(tbc_frases_opcoes.getId());
        assertThat(tbc_frases_opcoesExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_frases_opcoes> tbc_frases_opcoesList = tbc_frases_opcoesRepository.findAll();
        assertThat(tbc_frases_opcoesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_frases_opcoes() throws Exception {
        // Initialize the database
        tbc_frases_opcoesService.save(tbc_frases_opcoes);

        // Search the tbc_frases_opcoes
        restTbc_frases_opcoesMockMvc.perform(get("/api/_search/tbc-frases-opcoes?query=id:" + tbc_frases_opcoes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_frases_opcoes.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }
}
