package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_numeracao;
import com.labotech.lims.repository.Tbc_numeracaoRepository;
import com.labotech.lims.service.Tbc_numeracaoService;
import com.labotech.lims.repository.search.Tbc_numeracaoSearchRepository;

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
 * Test class for the Tbc_numeracaoResource REST controller.
 *
 * @see Tbc_numeracaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_numeracaoResourceIntTest {

    private static final Integer DEFAULT_ANO = 1;
    private static final Integer UPDATED_ANO = 2;

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_PARAMETRO = "AAAAAAAAAA";
    private static final String UPDATED_PARAMETRO = "BBBBBBBBBB";

    @Inject
    private Tbc_numeracaoRepository tbc_numeracaoRepository;

    @Inject
    private Tbc_numeracaoService tbc_numeracaoService;

    @Inject
    private Tbc_numeracaoSearchRepository tbc_numeracaoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_numeracaoMockMvc;

    private Tbc_numeracao tbc_numeracao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_numeracaoResource tbc_numeracaoResource = new Tbc_numeracaoResource();
        ReflectionTestUtils.setField(tbc_numeracaoResource, "tbc_numeracaoService", tbc_numeracaoService);
        this.restTbc_numeracaoMockMvc = MockMvcBuilders.standaloneSetup(tbc_numeracaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_numeracao createEntity(EntityManager em) {
        Tbc_numeracao tbc_numeracao = new Tbc_numeracao()
                .ano(DEFAULT_ANO)
                .numero(DEFAULT_NUMERO)
                .parametro(DEFAULT_PARAMETRO);
        return tbc_numeracao;
    }

    @Before
    public void initTest() {
        tbc_numeracaoSearchRepository.deleteAll();
        tbc_numeracao = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_numeracao() throws Exception {
        int databaseSizeBeforeCreate = tbc_numeracaoRepository.findAll().size();

        // Create the Tbc_numeracao

        restTbc_numeracaoMockMvc.perform(post("/api/tbc-numeracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_numeracao)))
            .andExpect(status().isCreated());

        // Validate the Tbc_numeracao in the database
        List<Tbc_numeracao> tbc_numeracaoList = tbc_numeracaoRepository.findAll();
        assertThat(tbc_numeracaoList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_numeracao testTbc_numeracao = tbc_numeracaoList.get(tbc_numeracaoList.size() - 1);
        assertThat(testTbc_numeracao.getAno()).isEqualTo(DEFAULT_ANO);
        assertThat(testTbc_numeracao.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testTbc_numeracao.getParametro()).isEqualTo(DEFAULT_PARAMETRO);

        // Validate the Tbc_numeracao in ElasticSearch
        Tbc_numeracao tbc_numeracaoEs = tbc_numeracaoSearchRepository.findOne(testTbc_numeracao.getId());
        assertThat(tbc_numeracaoEs).isEqualToComparingFieldByField(testTbc_numeracao);
    }

    @Test
    @Transactional
    public void createTbc_numeracaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_numeracaoRepository.findAll().size();

        // Create the Tbc_numeracao with an existing ID
        Tbc_numeracao existingTbc_numeracao = new Tbc_numeracao();
        existingTbc_numeracao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_numeracaoMockMvc.perform(post("/api/tbc-numeracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_numeracao)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_numeracao> tbc_numeracaoList = tbc_numeracaoRepository.findAll();
        assertThat(tbc_numeracaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_numeracaoRepository.findAll().size();
        // set the field null
        tbc_numeracao.setAno(null);

        // Create the Tbc_numeracao, which fails.

        restTbc_numeracaoMockMvc.perform(post("/api/tbc-numeracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_numeracao)))
            .andExpect(status().isBadRequest());

        List<Tbc_numeracao> tbc_numeracaoList = tbc_numeracaoRepository.findAll();
        assertThat(tbc_numeracaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_numeracaoRepository.findAll().size();
        // set the field null
        tbc_numeracao.setNumero(null);

        // Create the Tbc_numeracao, which fails.

        restTbc_numeracaoMockMvc.perform(post("/api/tbc-numeracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_numeracao)))
            .andExpect(status().isBadRequest());

        List<Tbc_numeracao> tbc_numeracaoList = tbc_numeracaoRepository.findAll();
        assertThat(tbc_numeracaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkParametroIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_numeracaoRepository.findAll().size();
        // set the field null
        tbc_numeracao.setParametro(null);

        // Create the Tbc_numeracao, which fails.

        restTbc_numeracaoMockMvc.perform(post("/api/tbc-numeracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_numeracao)))
            .andExpect(status().isBadRequest());

        List<Tbc_numeracao> tbc_numeracaoList = tbc_numeracaoRepository.findAll();
        assertThat(tbc_numeracaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_numeracaos() throws Exception {
        // Initialize the database
        tbc_numeracaoRepository.saveAndFlush(tbc_numeracao);

        // Get all the tbc_numeracaoList
        restTbc_numeracaoMockMvc.perform(get("/api/tbc-numeracaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_numeracao.getId().intValue())))
            .andExpect(jsonPath("$.[*].ano").value(hasItem(DEFAULT_ANO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].parametro").value(hasItem(DEFAULT_PARAMETRO.toString())));
    }

    @Test
    @Transactional
    public void getTbc_numeracao() throws Exception {
        // Initialize the database
        tbc_numeracaoRepository.saveAndFlush(tbc_numeracao);

        // Get the tbc_numeracao
        restTbc_numeracaoMockMvc.perform(get("/api/tbc-numeracaos/{id}", tbc_numeracao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_numeracao.getId().intValue()))
            .andExpect(jsonPath("$.ano").value(DEFAULT_ANO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.parametro").value(DEFAULT_PARAMETRO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_numeracao() throws Exception {
        // Get the tbc_numeracao
        restTbc_numeracaoMockMvc.perform(get("/api/tbc-numeracaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_numeracao() throws Exception {
        // Initialize the database
        tbc_numeracaoService.save(tbc_numeracao);

        int databaseSizeBeforeUpdate = tbc_numeracaoRepository.findAll().size();

        // Update the tbc_numeracao
        Tbc_numeracao updatedTbc_numeracao = tbc_numeracaoRepository.findOne(tbc_numeracao.getId());
        updatedTbc_numeracao
                .ano(UPDATED_ANO)
                .numero(UPDATED_NUMERO)
                .parametro(UPDATED_PARAMETRO);

        restTbc_numeracaoMockMvc.perform(put("/api/tbc-numeracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_numeracao)))
            .andExpect(status().isOk());

        // Validate the Tbc_numeracao in the database
        List<Tbc_numeracao> tbc_numeracaoList = tbc_numeracaoRepository.findAll();
        assertThat(tbc_numeracaoList).hasSize(databaseSizeBeforeUpdate);
        Tbc_numeracao testTbc_numeracao = tbc_numeracaoList.get(tbc_numeracaoList.size() - 1);
        assertThat(testTbc_numeracao.getAno()).isEqualTo(UPDATED_ANO);
        assertThat(testTbc_numeracao.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTbc_numeracao.getParametro()).isEqualTo(UPDATED_PARAMETRO);

        // Validate the Tbc_numeracao in ElasticSearch
        Tbc_numeracao tbc_numeracaoEs = tbc_numeracaoSearchRepository.findOne(testTbc_numeracao.getId());
        assertThat(tbc_numeracaoEs).isEqualToComparingFieldByField(testTbc_numeracao);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_numeracao() throws Exception {
        int databaseSizeBeforeUpdate = tbc_numeracaoRepository.findAll().size();

        // Create the Tbc_numeracao

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_numeracaoMockMvc.perform(put("/api/tbc-numeracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_numeracao)))
            .andExpect(status().isCreated());

        // Validate the Tbc_numeracao in the database
        List<Tbc_numeracao> tbc_numeracaoList = tbc_numeracaoRepository.findAll();
        assertThat(tbc_numeracaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_numeracao() throws Exception {
        // Initialize the database
        tbc_numeracaoService.save(tbc_numeracao);

        int databaseSizeBeforeDelete = tbc_numeracaoRepository.findAll().size();

        // Get the tbc_numeracao
        restTbc_numeracaoMockMvc.perform(delete("/api/tbc-numeracaos/{id}", tbc_numeracao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_numeracaoExistsInEs = tbc_numeracaoSearchRepository.exists(tbc_numeracao.getId());
        assertThat(tbc_numeracaoExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_numeracao> tbc_numeracaoList = tbc_numeracaoRepository.findAll();
        assertThat(tbc_numeracaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_numeracao() throws Exception {
        // Initialize the database
        tbc_numeracaoService.save(tbc_numeracao);

        // Search the tbc_numeracao
        restTbc_numeracaoMockMvc.perform(get("/api/_search/tbc-numeracaos?query=id:" + tbc_numeracao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_numeracao.getId().intValue())))
            .andExpect(jsonPath("$.[*].ano").value(hasItem(DEFAULT_ANO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].parametro").value(hasItem(DEFAULT_PARAMETRO.toString())));
    }
}
