package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_status;
import com.labotech.lims.repository.Tbc_statusRepository;
import com.labotech.lims.service.Tbc_statusService;
import com.labotech.lims.repository.search.Tbc_statusSearchRepository;

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
 * Test class for the Tbc_statusResource REST controller.
 *
 * @see Tbc_statusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_statusResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    private static final String DEFAULT_ICONE = "AAAAAAAAAA";
    private static final String UPDATED_ICONE = "BBBBBBBBBB";

    private static final String DEFAULT_COR = "AAAAAAAAAA";
    private static final String UPDATED_COR = "BBBBBBBBBB";

    @Inject
    private Tbc_statusRepository tbc_statusRepository;

    @Inject
    private Tbc_statusService tbc_statusService;

    @Inject
    private Tbc_statusSearchRepository tbc_statusSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_statusMockMvc;

    private Tbc_status tbc_status;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_statusResource tbc_statusResource = new Tbc_statusResource();
        ReflectionTestUtils.setField(tbc_statusResource, "tbc_statusService", tbc_statusService);
        this.restTbc_statusMockMvc = MockMvcBuilders.standaloneSetup(tbc_statusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_status createEntity(EntityManager em) {
        Tbc_status tbc_status = new Tbc_status()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .removido(DEFAULT_REMOVIDO)
                .icone(DEFAULT_ICONE)
                .cor(DEFAULT_COR);
        return tbc_status;
    }

    @Before
    public void initTest() {
        tbc_statusSearchRepository.deleteAll();
        tbc_status = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_status() throws Exception {
        int databaseSizeBeforeCreate = tbc_statusRepository.findAll().size();

        // Create the Tbc_status

        restTbc_statusMockMvc.perform(post("/api/tbc-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_status)))
            .andExpect(status().isCreated());

        // Validate the Tbc_status in the database
        List<Tbc_status> tbc_statusList = tbc_statusRepository.findAll();
        assertThat(tbc_statusList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_status testTbc_status = tbc_statusList.get(tbc_statusList.size() - 1);
        assertThat(testTbc_status.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_status.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_status.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);
        assertThat(testTbc_status.getIcone()).isEqualTo(DEFAULT_ICONE);
        assertThat(testTbc_status.getCor()).isEqualTo(DEFAULT_COR);

        // Validate the Tbc_status in ElasticSearch
        Tbc_status tbc_statusEs = tbc_statusSearchRepository.findOne(testTbc_status.getId());
        assertThat(tbc_statusEs).isEqualToComparingFieldByField(testTbc_status);
    }

    @Test
    @Transactional
    public void createTbc_statusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_statusRepository.findAll().size();

        // Create the Tbc_status with an existing ID
        Tbc_status existingTbc_status = new Tbc_status();
        existingTbc_status.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_statusMockMvc.perform(post("/api/tbc-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_status)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_status> tbc_statusList = tbc_statusRepository.findAll();
        assertThat(tbc_statusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_statusRepository.findAll().size();
        // set the field null
        tbc_status.setNome(null);

        // Create the Tbc_status, which fails.

        restTbc_statusMockMvc.perform(post("/api/tbc-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_status)))
            .andExpect(status().isBadRequest());

        List<Tbc_status> tbc_statusList = tbc_statusRepository.findAll();
        assertThat(tbc_statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_statuses() throws Exception {
        // Initialize the database
        tbc_statusRepository.saveAndFlush(tbc_status);

        // Get all the tbc_statusList
        restTbc_statusMockMvc.perform(get("/api/tbc-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_status.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].icone").value(hasItem(DEFAULT_ICONE.toString())))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR.toString())));
    }

    @Test
    @Transactional
    public void getTbc_status() throws Exception {
        // Initialize the database
        tbc_statusRepository.saveAndFlush(tbc_status);

        // Get the tbc_status
        restTbc_statusMockMvc.perform(get("/api/tbc-statuses/{id}", tbc_status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_status.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()))
            .andExpect(jsonPath("$.icone").value(DEFAULT_ICONE.toString()))
            .andExpect(jsonPath("$.cor").value(DEFAULT_COR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_status() throws Exception {
        // Get the tbc_status
        restTbc_statusMockMvc.perform(get("/api/tbc-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_status() throws Exception {
        // Initialize the database
        tbc_statusService.save(tbc_status);

        int databaseSizeBeforeUpdate = tbc_statusRepository.findAll().size();

        // Update the tbc_status
        Tbc_status updatedTbc_status = tbc_statusRepository.findOne(tbc_status.getId());
        updatedTbc_status
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .removido(UPDATED_REMOVIDO)
                .icone(UPDATED_ICONE)
                .cor(UPDATED_COR);

        restTbc_statusMockMvc.perform(put("/api/tbc-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_status)))
            .andExpect(status().isOk());

        // Validate the Tbc_status in the database
        List<Tbc_status> tbc_statusList = tbc_statusRepository.findAll();
        assertThat(tbc_statusList).hasSize(databaseSizeBeforeUpdate);
        Tbc_status testTbc_status = tbc_statusList.get(tbc_statusList.size() - 1);
        assertThat(testTbc_status.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_status.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_status.isRemovido()).isEqualTo(UPDATED_REMOVIDO);
        assertThat(testTbc_status.getIcone()).isEqualTo(UPDATED_ICONE);
        assertThat(testTbc_status.getCor()).isEqualTo(UPDATED_COR);

        // Validate the Tbc_status in ElasticSearch
        Tbc_status tbc_statusEs = tbc_statusSearchRepository.findOne(testTbc_status.getId());
        assertThat(tbc_statusEs).isEqualToComparingFieldByField(testTbc_status);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_status() throws Exception {
        int databaseSizeBeforeUpdate = tbc_statusRepository.findAll().size();

        // Create the Tbc_status

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_statusMockMvc.perform(put("/api/tbc-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_status)))
            .andExpect(status().isCreated());

        // Validate the Tbc_status in the database
        List<Tbc_status> tbc_statusList = tbc_statusRepository.findAll();
        assertThat(tbc_statusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_status() throws Exception {
        // Initialize the database
        tbc_statusService.save(tbc_status);

        int databaseSizeBeforeDelete = tbc_statusRepository.findAll().size();

        // Get the tbc_status
        restTbc_statusMockMvc.perform(delete("/api/tbc-statuses/{id}", tbc_status.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_statusExistsInEs = tbc_statusSearchRepository.exists(tbc_status.getId());
        assertThat(tbc_statusExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_status> tbc_statusList = tbc_statusRepository.findAll();
        assertThat(tbc_statusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_status() throws Exception {
        // Initialize the database
        tbc_statusService.save(tbc_status);

        // Search the tbc_status
        restTbc_statusMockMvc.perform(get("/api/_search/tbc-statuses?query=id:" + tbc_status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_status.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].icone").value(hasItem(DEFAULT_ICONE.toString())))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR.toString())));
    }
}
