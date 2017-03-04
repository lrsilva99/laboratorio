package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_cooperativa;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.repository.Tbc_cooperativaRepository;
import com.labotech.lims.service.Tbc_cooperativaService;
import com.labotech.lims.repository.search.Tbc_cooperativaSearchRepository;

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
 * Test class for the Tbc_cooperativaResource REST controller.
 *
 * @see Tbc_cooperativaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_cooperativaResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    @Inject
    private Tbc_cooperativaRepository tbc_cooperativaRepository;

    @Inject
    private Tbc_cooperativaService tbc_cooperativaService;

    @Inject
    private Tbc_cooperativaSearchRepository tbc_cooperativaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_cooperativaMockMvc;

    private Tbc_cooperativa tbc_cooperativa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_cooperativaResource tbc_cooperativaResource = new Tbc_cooperativaResource();
        ReflectionTestUtils.setField(tbc_cooperativaResource, "tbc_cooperativaService", tbc_cooperativaService);
        this.restTbc_cooperativaMockMvc = MockMvcBuilders.standaloneSetup(tbc_cooperativaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_cooperativa createEntity(EntityManager em) {
        Tbc_cooperativa tbc_cooperativa = new Tbc_cooperativa()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .removido(DEFAULT_REMOVIDO)
                .email(DEFAULT_EMAIL)
                .telefone(DEFAULT_TELEFONE);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_cooperativa.setTbc_instituicao(tbc_instituicao);
        return tbc_cooperativa;
    }

    @Before
    public void initTest() {
        tbc_cooperativaSearchRepository.deleteAll();
        tbc_cooperativa = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_cooperativa() throws Exception {
        int databaseSizeBeforeCreate = tbc_cooperativaRepository.findAll().size();

        // Create the Tbc_cooperativa

        restTbc_cooperativaMockMvc.perform(post("/api/tbc-cooperativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_cooperativa)))
            .andExpect(status().isCreated());

        // Validate the Tbc_cooperativa in the database
        List<Tbc_cooperativa> tbc_cooperativaList = tbc_cooperativaRepository.findAll();
        assertThat(tbc_cooperativaList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_cooperativa testTbc_cooperativa = tbc_cooperativaList.get(tbc_cooperativaList.size() - 1);
        assertThat(testTbc_cooperativa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_cooperativa.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_cooperativa.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);
        assertThat(testTbc_cooperativa.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTbc_cooperativa.getTelefone()).isEqualTo(DEFAULT_TELEFONE);

        // Validate the Tbc_cooperativa in ElasticSearch
        Tbc_cooperativa tbc_cooperativaEs = tbc_cooperativaSearchRepository.findOne(testTbc_cooperativa.getId());
        assertThat(tbc_cooperativaEs).isEqualToComparingFieldByField(testTbc_cooperativa);
    }

    @Test
    @Transactional
    public void createTbc_cooperativaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_cooperativaRepository.findAll().size();

        // Create the Tbc_cooperativa with an existing ID
        Tbc_cooperativa existingTbc_cooperativa = new Tbc_cooperativa();
        existingTbc_cooperativa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_cooperativaMockMvc.perform(post("/api/tbc-cooperativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_cooperativa)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_cooperativa> tbc_cooperativaList = tbc_cooperativaRepository.findAll();
        assertThat(tbc_cooperativaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_cooperativaRepository.findAll().size();
        // set the field null
        tbc_cooperativa.setNome(null);

        // Create the Tbc_cooperativa, which fails.

        restTbc_cooperativaMockMvc.perform(post("/api/tbc-cooperativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_cooperativa)))
            .andExpect(status().isBadRequest());

        List<Tbc_cooperativa> tbc_cooperativaList = tbc_cooperativaRepository.findAll();
        assertThat(tbc_cooperativaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_cooperativaRepository.findAll().size();
        // set the field null
        tbc_cooperativa.setEmail(null);

        // Create the Tbc_cooperativa, which fails.

        restTbc_cooperativaMockMvc.perform(post("/api/tbc-cooperativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_cooperativa)))
            .andExpect(status().isBadRequest());

        List<Tbc_cooperativa> tbc_cooperativaList = tbc_cooperativaRepository.findAll();
        assertThat(tbc_cooperativaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_cooperativaRepository.findAll().size();
        // set the field null
        tbc_cooperativa.setTelefone(null);

        // Create the Tbc_cooperativa, which fails.

        restTbc_cooperativaMockMvc.perform(post("/api/tbc-cooperativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_cooperativa)))
            .andExpect(status().isBadRequest());

        List<Tbc_cooperativa> tbc_cooperativaList = tbc_cooperativaRepository.findAll();
        assertThat(tbc_cooperativaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_cooperativas() throws Exception {
        // Initialize the database
        tbc_cooperativaRepository.saveAndFlush(tbc_cooperativa);

        // Get all the tbc_cooperativaList
        restTbc_cooperativaMockMvc.perform(get("/api/tbc-cooperativas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_cooperativa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())));
    }

    @Test
    @Transactional
    public void getTbc_cooperativa() throws Exception {
        // Initialize the database
        tbc_cooperativaRepository.saveAndFlush(tbc_cooperativa);

        // Get the tbc_cooperativa
        restTbc_cooperativaMockMvc.perform(get("/api/tbc-cooperativas/{id}", tbc_cooperativa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_cooperativa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_cooperativa() throws Exception {
        // Get the tbc_cooperativa
        restTbc_cooperativaMockMvc.perform(get("/api/tbc-cooperativas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_cooperativa() throws Exception {
        // Initialize the database
        tbc_cooperativaService.save(tbc_cooperativa);

        int databaseSizeBeforeUpdate = tbc_cooperativaRepository.findAll().size();

        // Update the tbc_cooperativa
        Tbc_cooperativa updatedTbc_cooperativa = tbc_cooperativaRepository.findOne(tbc_cooperativa.getId());
        updatedTbc_cooperativa
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .removido(UPDATED_REMOVIDO)
                .email(UPDATED_EMAIL)
                .telefone(UPDATED_TELEFONE);

        restTbc_cooperativaMockMvc.perform(put("/api/tbc-cooperativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_cooperativa)))
            .andExpect(status().isOk());

        // Validate the Tbc_cooperativa in the database
        List<Tbc_cooperativa> tbc_cooperativaList = tbc_cooperativaRepository.findAll();
        assertThat(tbc_cooperativaList).hasSize(databaseSizeBeforeUpdate);
        Tbc_cooperativa testTbc_cooperativa = tbc_cooperativaList.get(tbc_cooperativaList.size() - 1);
        assertThat(testTbc_cooperativa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_cooperativa.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_cooperativa.isRemovido()).isEqualTo(UPDATED_REMOVIDO);
        assertThat(testTbc_cooperativa.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTbc_cooperativa.getTelefone()).isEqualTo(UPDATED_TELEFONE);

        // Validate the Tbc_cooperativa in ElasticSearch
        Tbc_cooperativa tbc_cooperativaEs = tbc_cooperativaSearchRepository.findOne(testTbc_cooperativa.getId());
        assertThat(tbc_cooperativaEs).isEqualToComparingFieldByField(testTbc_cooperativa);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_cooperativa() throws Exception {
        int databaseSizeBeforeUpdate = tbc_cooperativaRepository.findAll().size();

        // Create the Tbc_cooperativa

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_cooperativaMockMvc.perform(put("/api/tbc-cooperativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_cooperativa)))
            .andExpect(status().isCreated());

        // Validate the Tbc_cooperativa in the database
        List<Tbc_cooperativa> tbc_cooperativaList = tbc_cooperativaRepository.findAll();
        assertThat(tbc_cooperativaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_cooperativa() throws Exception {
        // Initialize the database
        tbc_cooperativaService.save(tbc_cooperativa);

        int databaseSizeBeforeDelete = tbc_cooperativaRepository.findAll().size();

        // Get the tbc_cooperativa
        restTbc_cooperativaMockMvc.perform(delete("/api/tbc-cooperativas/{id}", tbc_cooperativa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_cooperativaExistsInEs = tbc_cooperativaSearchRepository.exists(tbc_cooperativa.getId());
        assertThat(tbc_cooperativaExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_cooperativa> tbc_cooperativaList = tbc_cooperativaRepository.findAll();
        assertThat(tbc_cooperativaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_cooperativa() throws Exception {
        // Initialize the database
        tbc_cooperativaService.save(tbc_cooperativa);

        // Search the tbc_cooperativa
        restTbc_cooperativaMockMvc.perform(get("/api/_search/tbc-cooperativas?query=id:" + tbc_cooperativa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_cooperativa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())));
    }
}
