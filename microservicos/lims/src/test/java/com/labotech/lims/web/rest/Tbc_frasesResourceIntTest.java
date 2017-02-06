package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_frases;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.domain.Tbc_sub_grupo;
import com.labotech.lims.repository.Tbc_frasesRepository;
import com.labotech.lims.service.Tbc_frasesService;
import com.labotech.lims.repository.search.Tbc_frasesSearchRepository;

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
 * Test class for the Tbc_frasesResource REST controller.
 *
 * @see Tbc_frasesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_frasesResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    @Inject
    private Tbc_frasesRepository tbc_frasesRepository;

    @Inject
    private Tbc_frasesService tbc_frasesService;

    @Inject
    private Tbc_frasesSearchRepository tbc_frasesSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_frasesMockMvc;

    private Tbc_frases tbc_frases;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_frasesResource tbc_frasesResource = new Tbc_frasesResource();
        ReflectionTestUtils.setField(tbc_frasesResource, "tbc_frasesService", tbc_frasesService);
        this.restTbc_frasesMockMvc = MockMvcBuilders.standaloneSetup(tbc_frasesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_frases createEntity(EntityManager em) {
        Tbc_frases tbc_frases = new Tbc_frases()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .removido(DEFAULT_REMOVIDO);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_frases.setTbc_instituicao(tbc_instituicao);
        // Add required entity
        Tbc_sub_grupo tbc_sub_grupo = Tbc_sub_grupoResourceIntTest.createEntity(em);
        em.persist(tbc_sub_grupo);
        em.flush();
        tbc_frases.setTbc_sub_grupo(tbc_sub_grupo);
        return tbc_frases;
    }

    @Before
    public void initTest() {
        tbc_frasesSearchRepository.deleteAll();
        tbc_frases = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_frases() throws Exception {
        int databaseSizeBeforeCreate = tbc_frasesRepository.findAll().size();

        // Create the Tbc_frases

        restTbc_frasesMockMvc.perform(post("/api/tbc-frases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_frases)))
            .andExpect(status().isCreated());

        // Validate the Tbc_frases in the database
        List<Tbc_frases> tbc_frasesList = tbc_frasesRepository.findAll();
        assertThat(tbc_frasesList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_frases testTbc_frases = tbc_frasesList.get(tbc_frasesList.size() - 1);
        assertThat(testTbc_frases.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_frases.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_frases.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);

        // Validate the Tbc_frases in ElasticSearch
        Tbc_frases tbc_frasesEs = tbc_frasesSearchRepository.findOne(testTbc_frases.getId());
        assertThat(tbc_frasesEs).isEqualToComparingFieldByField(testTbc_frases);
    }

    @Test
    @Transactional
    public void createTbc_frasesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_frasesRepository.findAll().size();

        // Create the Tbc_frases with an existing ID
        Tbc_frases existingTbc_frases = new Tbc_frases();
        existingTbc_frases.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_frasesMockMvc.perform(post("/api/tbc-frases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_frases)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_frases> tbc_frasesList = tbc_frasesRepository.findAll();
        assertThat(tbc_frasesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_frasesRepository.findAll().size();
        // set the field null
        tbc_frases.setNome(null);

        // Create the Tbc_frases, which fails.

        restTbc_frasesMockMvc.perform(post("/api/tbc-frases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_frases)))
            .andExpect(status().isBadRequest());

        List<Tbc_frases> tbc_frasesList = tbc_frasesRepository.findAll();
        assertThat(tbc_frasesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_frases() throws Exception {
        // Initialize the database
        tbc_frasesRepository.saveAndFlush(tbc_frases);

        // Get all the tbc_frasesList
        restTbc_frasesMockMvc.perform(get("/api/tbc-frases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_frases.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTbc_frases() throws Exception {
        // Initialize the database
        tbc_frasesRepository.saveAndFlush(tbc_frases);

        // Get the tbc_frases
        restTbc_frasesMockMvc.perform(get("/api/tbc-frases/{id}", tbc_frases.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_frases.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_frases() throws Exception {
        // Get the tbc_frases
        restTbc_frasesMockMvc.perform(get("/api/tbc-frases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_frases() throws Exception {
        // Initialize the database
        tbc_frasesService.save(tbc_frases);

        int databaseSizeBeforeUpdate = tbc_frasesRepository.findAll().size();

        // Update the tbc_frases
        Tbc_frases updatedTbc_frases = tbc_frasesRepository.findOne(tbc_frases.getId());
        updatedTbc_frases
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .removido(UPDATED_REMOVIDO);

        restTbc_frasesMockMvc.perform(put("/api/tbc-frases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_frases)))
            .andExpect(status().isOk());

        // Validate the Tbc_frases in the database
        List<Tbc_frases> tbc_frasesList = tbc_frasesRepository.findAll();
        assertThat(tbc_frasesList).hasSize(databaseSizeBeforeUpdate);
        Tbc_frases testTbc_frases = tbc_frasesList.get(tbc_frasesList.size() - 1);
        assertThat(testTbc_frases.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_frases.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_frases.isRemovido()).isEqualTo(UPDATED_REMOVIDO);

        // Validate the Tbc_frases in ElasticSearch
        Tbc_frases tbc_frasesEs = tbc_frasesSearchRepository.findOne(testTbc_frases.getId());
        assertThat(tbc_frasesEs).isEqualToComparingFieldByField(testTbc_frases);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_frases() throws Exception {
        int databaseSizeBeforeUpdate = tbc_frasesRepository.findAll().size();

        // Create the Tbc_frases

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_frasesMockMvc.perform(put("/api/tbc-frases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_frases)))
            .andExpect(status().isCreated());

        // Validate the Tbc_frases in the database
        List<Tbc_frases> tbc_frasesList = tbc_frasesRepository.findAll();
        assertThat(tbc_frasesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_frases() throws Exception {
        // Initialize the database
        tbc_frasesService.save(tbc_frases);

        int databaseSizeBeforeDelete = tbc_frasesRepository.findAll().size();

        // Get the tbc_frases
        restTbc_frasesMockMvc.perform(delete("/api/tbc-frases/{id}", tbc_frases.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_frasesExistsInEs = tbc_frasesSearchRepository.exists(tbc_frases.getId());
        assertThat(tbc_frasesExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_frases> tbc_frasesList = tbc_frasesRepository.findAll();
        assertThat(tbc_frasesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_frases() throws Exception {
        // Initialize the database
        tbc_frasesService.save(tbc_frases);

        // Search the tbc_frases
        restTbc_frasesMockMvc.perform(get("/api/_search/tbc-frases?query=id:" + tbc_frases.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_frases.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }
}
