package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_report;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.repository.Tbc_reportRepository;
import com.labotech.lims.service.Tbc_reportService;
import com.labotech.lims.repository.search.Tbc_reportSearchRepository;

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
 * Test class for the Tbc_reportResource REST controller.
 *
 * @see Tbc_reportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_reportResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_ARQUIVO = "AAAAAAAAAA";
    private static final String UPDATED_ARQUIVO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    @Inject
    private Tbc_reportRepository tbc_reportRepository;

    @Inject
    private Tbc_reportService tbc_reportService;

    @Inject
    private Tbc_reportSearchRepository tbc_reportSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_reportMockMvc;

    private Tbc_report tbc_report;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_reportResource tbc_reportResource = new Tbc_reportResource();
        ReflectionTestUtils.setField(tbc_reportResource, "tbc_reportService", tbc_reportService);
        this.restTbc_reportMockMvc = MockMvcBuilders.standaloneSetup(tbc_reportResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_report createEntity(EntityManager em) {
        Tbc_report tbc_report = new Tbc_report()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .arquivo(DEFAULT_ARQUIVO)
                .removido(DEFAULT_REMOVIDO);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_report.setTbc_instituicao(tbc_instituicao);
        return tbc_report;
    }

    @Before
    public void initTest() {
        tbc_reportSearchRepository.deleteAll();
        tbc_report = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_report() throws Exception {
        int databaseSizeBeforeCreate = tbc_reportRepository.findAll().size();

        // Create the Tbc_report

        restTbc_reportMockMvc.perform(post("/api/tbc-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_report)))
            .andExpect(status().isCreated());

        // Validate the Tbc_report in the database
        List<Tbc_report> tbc_reportList = tbc_reportRepository.findAll();
        assertThat(tbc_reportList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_report testTbc_report = tbc_reportList.get(tbc_reportList.size() - 1);
        assertThat(testTbc_report.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_report.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_report.getArquivo()).isEqualTo(DEFAULT_ARQUIVO);
        assertThat(testTbc_report.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);

        // Validate the Tbc_report in ElasticSearch
        Tbc_report tbc_reportEs = tbc_reportSearchRepository.findOne(testTbc_report.getId());
        assertThat(tbc_reportEs).isEqualToComparingFieldByField(testTbc_report);
    }

    @Test
    @Transactional
    public void createTbc_reportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_reportRepository.findAll().size();

        // Create the Tbc_report with an existing ID
        Tbc_report existingTbc_report = new Tbc_report();
        existingTbc_report.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_reportMockMvc.perform(post("/api/tbc-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_report)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_report> tbc_reportList = tbc_reportRepository.findAll();
        assertThat(tbc_reportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_reportRepository.findAll().size();
        // set the field null
        tbc_report.setNome(null);

        // Create the Tbc_report, which fails.

        restTbc_reportMockMvc.perform(post("/api/tbc-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_report)))
            .andExpect(status().isBadRequest());

        List<Tbc_report> tbc_reportList = tbc_reportRepository.findAll();
        assertThat(tbc_reportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkArquivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_reportRepository.findAll().size();
        // set the field null
        tbc_report.setArquivo(null);

        // Create the Tbc_report, which fails.

        restTbc_reportMockMvc.perform(post("/api/tbc-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_report)))
            .andExpect(status().isBadRequest());

        List<Tbc_report> tbc_reportList = tbc_reportRepository.findAll();
        assertThat(tbc_reportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_reports() throws Exception {
        // Initialize the database
        tbc_reportRepository.saveAndFlush(tbc_report);

        // Get all the tbc_reportList
        restTbc_reportMockMvc.perform(get("/api/tbc-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_report.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].arquivo").value(hasItem(DEFAULT_ARQUIVO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTbc_report() throws Exception {
        // Initialize the database
        tbc_reportRepository.saveAndFlush(tbc_report);

        // Get the tbc_report
        restTbc_reportMockMvc.perform(get("/api/tbc-reports/{id}", tbc_report.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_report.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.arquivo").value(DEFAULT_ARQUIVO.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_report() throws Exception {
        // Get the tbc_report
        restTbc_reportMockMvc.perform(get("/api/tbc-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_report() throws Exception {
        // Initialize the database
        tbc_reportService.save(tbc_report);

        int databaseSizeBeforeUpdate = tbc_reportRepository.findAll().size();

        // Update the tbc_report
        Tbc_report updatedTbc_report = tbc_reportRepository.findOne(tbc_report.getId());
        updatedTbc_report
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .arquivo(UPDATED_ARQUIVO)
                .removido(UPDATED_REMOVIDO);

        restTbc_reportMockMvc.perform(put("/api/tbc-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_report)))
            .andExpect(status().isOk());

        // Validate the Tbc_report in the database
        List<Tbc_report> tbc_reportList = tbc_reportRepository.findAll();
        assertThat(tbc_reportList).hasSize(databaseSizeBeforeUpdate);
        Tbc_report testTbc_report = tbc_reportList.get(tbc_reportList.size() - 1);
        assertThat(testTbc_report.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_report.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_report.getArquivo()).isEqualTo(UPDATED_ARQUIVO);
        assertThat(testTbc_report.isRemovido()).isEqualTo(UPDATED_REMOVIDO);

        // Validate the Tbc_report in ElasticSearch
        Tbc_report tbc_reportEs = tbc_reportSearchRepository.findOne(testTbc_report.getId());
        assertThat(tbc_reportEs).isEqualToComparingFieldByField(testTbc_report);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_report() throws Exception {
        int databaseSizeBeforeUpdate = tbc_reportRepository.findAll().size();

        // Create the Tbc_report

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_reportMockMvc.perform(put("/api/tbc-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_report)))
            .andExpect(status().isCreated());

        // Validate the Tbc_report in the database
        List<Tbc_report> tbc_reportList = tbc_reportRepository.findAll();
        assertThat(tbc_reportList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_report() throws Exception {
        // Initialize the database
        tbc_reportService.save(tbc_report);

        int databaseSizeBeforeDelete = tbc_reportRepository.findAll().size();

        // Get the tbc_report
        restTbc_reportMockMvc.perform(delete("/api/tbc-reports/{id}", tbc_report.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_reportExistsInEs = tbc_reportSearchRepository.exists(tbc_report.getId());
        assertThat(tbc_reportExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_report> tbc_reportList = tbc_reportRepository.findAll();
        assertThat(tbc_reportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_report() throws Exception {
        // Initialize the database
        tbc_reportService.save(tbc_report);

        // Search the tbc_report
        restTbc_reportMockMvc.perform(get("/api/_search/tbc-reports?query=id:" + tbc_report.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_report.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].arquivo").value(hasItem(DEFAULT_ARQUIVO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }
}
