package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_relatorio_ensaio;
import com.labotech.lims.repository.Tbc_relatorio_ensaioRepository;
import com.labotech.lims.service.Tbc_relatorio_ensaioService;
import com.labotech.lims.repository.search.Tbc_relatorio_ensaioSearchRepository;

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
 * Test class for the Tbc_relatorio_ensaioResource REST controller.
 *
 * @see Tbc_relatorio_ensaioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_relatorio_ensaioResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_CAMINHO = "AAAAAAAAAA";
    private static final String UPDATED_CAMINHO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    @Inject
    private Tbc_relatorio_ensaioRepository tbc_relatorio_ensaioRepository;

    @Inject
    private Tbc_relatorio_ensaioService tbc_relatorio_ensaioService;

    @Inject
    private Tbc_relatorio_ensaioSearchRepository tbc_relatorio_ensaioSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_relatorio_ensaioMockMvc;

    private Tbc_relatorio_ensaio tbc_relatorio_ensaio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_relatorio_ensaioResource tbc_relatorio_ensaioResource = new Tbc_relatorio_ensaioResource();
        ReflectionTestUtils.setField(tbc_relatorio_ensaioResource, "tbc_relatorio_ensaioService", tbc_relatorio_ensaioService);
        this.restTbc_relatorio_ensaioMockMvc = MockMvcBuilders.standaloneSetup(tbc_relatorio_ensaioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_relatorio_ensaio createEntity(EntityManager em) {
        Tbc_relatorio_ensaio tbc_relatorio_ensaio = new Tbc_relatorio_ensaio()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .caminho(DEFAULT_CAMINHO)
                .removido(DEFAULT_REMOVIDO);
        return tbc_relatorio_ensaio;
    }

    @Before
    public void initTest() {
        tbc_relatorio_ensaioSearchRepository.deleteAll();
        tbc_relatorio_ensaio = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_relatorio_ensaio() throws Exception {
        int databaseSizeBeforeCreate = tbc_relatorio_ensaioRepository.findAll().size();

        // Create the Tbc_relatorio_ensaio

        restTbc_relatorio_ensaioMockMvc.perform(post("/api/tbc-relatorio-ensaios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_relatorio_ensaio)))
            .andExpect(status().isCreated());

        // Validate the Tbc_relatorio_ensaio in the database
        List<Tbc_relatorio_ensaio> tbc_relatorio_ensaioList = tbc_relatorio_ensaioRepository.findAll();
        assertThat(tbc_relatorio_ensaioList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_relatorio_ensaio testTbc_relatorio_ensaio = tbc_relatorio_ensaioList.get(tbc_relatorio_ensaioList.size() - 1);
        assertThat(testTbc_relatorio_ensaio.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_relatorio_ensaio.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_relatorio_ensaio.getCaminho()).isEqualTo(DEFAULT_CAMINHO);
        assertThat(testTbc_relatorio_ensaio.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);

        // Validate the Tbc_relatorio_ensaio in ElasticSearch
        Tbc_relatorio_ensaio tbc_relatorio_ensaioEs = tbc_relatorio_ensaioSearchRepository.findOne(testTbc_relatorio_ensaio.getId());
        assertThat(tbc_relatorio_ensaioEs).isEqualToComparingFieldByField(testTbc_relatorio_ensaio);
    }

    @Test
    @Transactional
    public void createTbc_relatorio_ensaioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_relatorio_ensaioRepository.findAll().size();

        // Create the Tbc_relatorio_ensaio with an existing ID
        Tbc_relatorio_ensaio existingTbc_relatorio_ensaio = new Tbc_relatorio_ensaio();
        existingTbc_relatorio_ensaio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_relatorio_ensaioMockMvc.perform(post("/api/tbc-relatorio-ensaios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_relatorio_ensaio)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_relatorio_ensaio> tbc_relatorio_ensaioList = tbc_relatorio_ensaioRepository.findAll();
        assertThat(tbc_relatorio_ensaioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_relatorio_ensaioRepository.findAll().size();
        // set the field null
        tbc_relatorio_ensaio.setNome(null);

        // Create the Tbc_relatorio_ensaio, which fails.

        restTbc_relatorio_ensaioMockMvc.perform(post("/api/tbc-relatorio-ensaios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_relatorio_ensaio)))
            .andExpect(status().isBadRequest());

        List<Tbc_relatorio_ensaio> tbc_relatorio_ensaioList = tbc_relatorio_ensaioRepository.findAll();
        assertThat(tbc_relatorio_ensaioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCaminhoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_relatorio_ensaioRepository.findAll().size();
        // set the field null
        tbc_relatorio_ensaio.setCaminho(null);

        // Create the Tbc_relatorio_ensaio, which fails.

        restTbc_relatorio_ensaioMockMvc.perform(post("/api/tbc-relatorio-ensaios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_relatorio_ensaio)))
            .andExpect(status().isBadRequest());

        List<Tbc_relatorio_ensaio> tbc_relatorio_ensaioList = tbc_relatorio_ensaioRepository.findAll();
        assertThat(tbc_relatorio_ensaioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_relatorio_ensaios() throws Exception {
        // Initialize the database
        tbc_relatorio_ensaioRepository.saveAndFlush(tbc_relatorio_ensaio);

        // Get all the tbc_relatorio_ensaioList
        restTbc_relatorio_ensaioMockMvc.perform(get("/api/tbc-relatorio-ensaios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_relatorio_ensaio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].caminho").value(hasItem(DEFAULT_CAMINHO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTbc_relatorio_ensaio() throws Exception {
        // Initialize the database
        tbc_relatorio_ensaioRepository.saveAndFlush(tbc_relatorio_ensaio);

        // Get the tbc_relatorio_ensaio
        restTbc_relatorio_ensaioMockMvc.perform(get("/api/tbc-relatorio-ensaios/{id}", tbc_relatorio_ensaio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_relatorio_ensaio.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.caminho").value(DEFAULT_CAMINHO.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_relatorio_ensaio() throws Exception {
        // Get the tbc_relatorio_ensaio
        restTbc_relatorio_ensaioMockMvc.perform(get("/api/tbc-relatorio-ensaios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_relatorio_ensaio() throws Exception {
        // Initialize the database
        tbc_relatorio_ensaioService.save(tbc_relatorio_ensaio);

        int databaseSizeBeforeUpdate = tbc_relatorio_ensaioRepository.findAll().size();

        // Update the tbc_relatorio_ensaio
        Tbc_relatorio_ensaio updatedTbc_relatorio_ensaio = tbc_relatorio_ensaioRepository.findOne(tbc_relatorio_ensaio.getId());
        updatedTbc_relatorio_ensaio
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .caminho(UPDATED_CAMINHO)
                .removido(UPDATED_REMOVIDO);

        restTbc_relatorio_ensaioMockMvc.perform(put("/api/tbc-relatorio-ensaios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_relatorio_ensaio)))
            .andExpect(status().isOk());

        // Validate the Tbc_relatorio_ensaio in the database
        List<Tbc_relatorio_ensaio> tbc_relatorio_ensaioList = tbc_relatorio_ensaioRepository.findAll();
        assertThat(tbc_relatorio_ensaioList).hasSize(databaseSizeBeforeUpdate);
        Tbc_relatorio_ensaio testTbc_relatorio_ensaio = tbc_relatorio_ensaioList.get(tbc_relatorio_ensaioList.size() - 1);
        assertThat(testTbc_relatorio_ensaio.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_relatorio_ensaio.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_relatorio_ensaio.getCaminho()).isEqualTo(UPDATED_CAMINHO);
        assertThat(testTbc_relatorio_ensaio.isRemovido()).isEqualTo(UPDATED_REMOVIDO);

        // Validate the Tbc_relatorio_ensaio in ElasticSearch
        Tbc_relatorio_ensaio tbc_relatorio_ensaioEs = tbc_relatorio_ensaioSearchRepository.findOne(testTbc_relatorio_ensaio.getId());
        assertThat(tbc_relatorio_ensaioEs).isEqualToComparingFieldByField(testTbc_relatorio_ensaio);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_relatorio_ensaio() throws Exception {
        int databaseSizeBeforeUpdate = tbc_relatorio_ensaioRepository.findAll().size();

        // Create the Tbc_relatorio_ensaio

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_relatorio_ensaioMockMvc.perform(put("/api/tbc-relatorio-ensaios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_relatorio_ensaio)))
            .andExpect(status().isCreated());

        // Validate the Tbc_relatorio_ensaio in the database
        List<Tbc_relatorio_ensaio> tbc_relatorio_ensaioList = tbc_relatorio_ensaioRepository.findAll();
        assertThat(tbc_relatorio_ensaioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_relatorio_ensaio() throws Exception {
        // Initialize the database
        tbc_relatorio_ensaioService.save(tbc_relatorio_ensaio);

        int databaseSizeBeforeDelete = tbc_relatorio_ensaioRepository.findAll().size();

        // Get the tbc_relatorio_ensaio
        restTbc_relatorio_ensaioMockMvc.perform(delete("/api/tbc-relatorio-ensaios/{id}", tbc_relatorio_ensaio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_relatorio_ensaioExistsInEs = tbc_relatorio_ensaioSearchRepository.exists(tbc_relatorio_ensaio.getId());
        assertThat(tbc_relatorio_ensaioExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_relatorio_ensaio> tbc_relatorio_ensaioList = tbc_relatorio_ensaioRepository.findAll();
        assertThat(tbc_relatorio_ensaioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_relatorio_ensaio() throws Exception {
        // Initialize the database
        tbc_relatorio_ensaioService.save(tbc_relatorio_ensaio);

        // Search the tbc_relatorio_ensaio
        restTbc_relatorio_ensaioMockMvc.perform(get("/api/_search/tbc-relatorio-ensaios?query=id:" + tbc_relatorio_ensaio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_relatorio_ensaio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].caminho").value(hasItem(DEFAULT_CAMINHO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }
}
