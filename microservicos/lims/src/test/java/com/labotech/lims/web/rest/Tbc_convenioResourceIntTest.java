package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_convenio;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.repository.Tbc_convenioRepository;
import com.labotech.lims.service.Tbc_convenioService;
import com.labotech.lims.repository.search.Tbc_convenioSearchRepository;

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
 * Test class for the Tbc_convenioResource REST controller.
 *
 * @see Tbc_convenioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_convenioResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    @Inject
    private Tbc_convenioRepository tbc_convenioRepository;

    @Inject
    private Tbc_convenioService tbc_convenioService;

    @Inject
    private Tbc_convenioSearchRepository tbc_convenioSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_convenioMockMvc;

    private Tbc_convenio tbc_convenio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_convenioResource tbc_convenioResource = new Tbc_convenioResource();
        ReflectionTestUtils.setField(tbc_convenioResource, "tbc_convenioService", tbc_convenioService);
        this.restTbc_convenioMockMvc = MockMvcBuilders.standaloneSetup(tbc_convenioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_convenio createEntity(EntityManager em) {
        Tbc_convenio tbc_convenio = new Tbc_convenio()
                .nome(DEFAULT_NOME)
                .cnpj(DEFAULT_CNPJ)
                .descricao(DEFAULT_DESCRICAO)
                .removido(DEFAULT_REMOVIDO);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_convenio.setTbc_instituicao(tbc_instituicao);
        return tbc_convenio;
    }

    @Before
    public void initTest() {
        tbc_convenioSearchRepository.deleteAll();
        tbc_convenio = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_convenio() throws Exception {
        int databaseSizeBeforeCreate = tbc_convenioRepository.findAll().size();

        // Create the Tbc_convenio

        restTbc_convenioMockMvc.perform(post("/api/tbc-convenios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_convenio)))
            .andExpect(status().isCreated());

        // Validate the Tbc_convenio in the database
        List<Tbc_convenio> tbc_convenioList = tbc_convenioRepository.findAll();
        assertThat(tbc_convenioList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_convenio testTbc_convenio = tbc_convenioList.get(tbc_convenioList.size() - 1);
        assertThat(testTbc_convenio.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_convenio.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testTbc_convenio.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_convenio.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);

        // Validate the Tbc_convenio in ElasticSearch
        Tbc_convenio tbc_convenioEs = tbc_convenioSearchRepository.findOne(testTbc_convenio.getId());
        assertThat(tbc_convenioEs).isEqualToComparingFieldByField(testTbc_convenio);
    }

    @Test
    @Transactional
    public void createTbc_convenioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_convenioRepository.findAll().size();

        // Create the Tbc_convenio with an existing ID
        Tbc_convenio existingTbc_convenio = new Tbc_convenio();
        existingTbc_convenio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_convenioMockMvc.perform(post("/api/tbc-convenios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_convenio)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_convenio> tbc_convenioList = tbc_convenioRepository.findAll();
        assertThat(tbc_convenioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_convenioRepository.findAll().size();
        // set the field null
        tbc_convenio.setNome(null);

        // Create the Tbc_convenio, which fails.

        restTbc_convenioMockMvc.perform(post("/api/tbc-convenios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_convenio)))
            .andExpect(status().isBadRequest());

        List<Tbc_convenio> tbc_convenioList = tbc_convenioRepository.findAll();
        assertThat(tbc_convenioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCnpjIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_convenioRepository.findAll().size();
        // set the field null
        tbc_convenio.setCnpj(null);

        // Create the Tbc_convenio, which fails.

        restTbc_convenioMockMvc.perform(post("/api/tbc-convenios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_convenio)))
            .andExpect(status().isBadRequest());

        List<Tbc_convenio> tbc_convenioList = tbc_convenioRepository.findAll();
        assertThat(tbc_convenioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_convenios() throws Exception {
        // Initialize the database
        tbc_convenioRepository.saveAndFlush(tbc_convenio);

        // Get all the tbc_convenioList
        restTbc_convenioMockMvc.perform(get("/api/tbc-convenios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_convenio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTbc_convenio() throws Exception {
        // Initialize the database
        tbc_convenioRepository.saveAndFlush(tbc_convenio);

        // Get the tbc_convenio
        restTbc_convenioMockMvc.perform(get("/api/tbc-convenios/{id}", tbc_convenio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_convenio.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_convenio() throws Exception {
        // Get the tbc_convenio
        restTbc_convenioMockMvc.perform(get("/api/tbc-convenios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_convenio() throws Exception {
        // Initialize the database
        tbc_convenioService.save(tbc_convenio);

        int databaseSizeBeforeUpdate = tbc_convenioRepository.findAll().size();

        // Update the tbc_convenio
        Tbc_convenio updatedTbc_convenio = tbc_convenioRepository.findOne(tbc_convenio.getId());
        updatedTbc_convenio
                .nome(UPDATED_NOME)
                .cnpj(UPDATED_CNPJ)
                .descricao(UPDATED_DESCRICAO)
                .removido(UPDATED_REMOVIDO);

        restTbc_convenioMockMvc.perform(put("/api/tbc-convenios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_convenio)))
            .andExpect(status().isOk());

        // Validate the Tbc_convenio in the database
        List<Tbc_convenio> tbc_convenioList = tbc_convenioRepository.findAll();
        assertThat(tbc_convenioList).hasSize(databaseSizeBeforeUpdate);
        Tbc_convenio testTbc_convenio = tbc_convenioList.get(tbc_convenioList.size() - 1);
        assertThat(testTbc_convenio.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_convenio.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testTbc_convenio.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_convenio.isRemovido()).isEqualTo(UPDATED_REMOVIDO);

        // Validate the Tbc_convenio in ElasticSearch
        Tbc_convenio tbc_convenioEs = tbc_convenioSearchRepository.findOne(testTbc_convenio.getId());
        assertThat(tbc_convenioEs).isEqualToComparingFieldByField(testTbc_convenio);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_convenio() throws Exception {
        int databaseSizeBeforeUpdate = tbc_convenioRepository.findAll().size();

        // Create the Tbc_convenio

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_convenioMockMvc.perform(put("/api/tbc-convenios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_convenio)))
            .andExpect(status().isCreated());

        // Validate the Tbc_convenio in the database
        List<Tbc_convenio> tbc_convenioList = tbc_convenioRepository.findAll();
        assertThat(tbc_convenioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_convenio() throws Exception {
        // Initialize the database
        tbc_convenioService.save(tbc_convenio);

        int databaseSizeBeforeDelete = tbc_convenioRepository.findAll().size();

        // Get the tbc_convenio
        restTbc_convenioMockMvc.perform(delete("/api/tbc-convenios/{id}", tbc_convenio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_convenioExistsInEs = tbc_convenioSearchRepository.exists(tbc_convenio.getId());
        assertThat(tbc_convenioExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_convenio> tbc_convenioList = tbc_convenioRepository.findAll();
        assertThat(tbc_convenioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_convenio() throws Exception {
        // Initialize the database
        tbc_convenioService.save(tbc_convenio);

        // Search the tbc_convenio
        restTbc_convenioMockMvc.perform(get("/api/_search/tbc-convenios?query=id:" + tbc_convenio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_convenio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }
}
