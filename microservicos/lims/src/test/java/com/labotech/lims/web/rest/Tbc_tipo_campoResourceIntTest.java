package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_tipo_campo;
import com.labotech.lims.repository.Tbc_tipo_campoRepository;
import com.labotech.lims.service.Tbc_tipo_campoService;
import com.labotech.lims.repository.search.Tbc_tipo_campoSearchRepository;

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
 * Test class for the Tbc_tipo_campoResource REST controller.
 *
 * @see Tbc_tipo_campoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_tipo_campoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Inject
    private Tbc_tipo_campoRepository tbc_tipo_campoRepository;

    @Inject
    private Tbc_tipo_campoService tbc_tipo_campoService;

    @Inject
    private Tbc_tipo_campoSearchRepository tbc_tipo_campoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_tipo_campoMockMvc;

    private Tbc_tipo_campo tbc_tipo_campo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_tipo_campoResource tbc_tipo_campoResource = new Tbc_tipo_campoResource();
        ReflectionTestUtils.setField(tbc_tipo_campoResource, "tbc_tipo_campoService", tbc_tipo_campoService);
        this.restTbc_tipo_campoMockMvc = MockMvcBuilders.standaloneSetup(tbc_tipo_campoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_tipo_campo createEntity(EntityManager em) {
        Tbc_tipo_campo tbc_tipo_campo = new Tbc_tipo_campo()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO);
        return tbc_tipo_campo;
    }

    @Before
    public void initTest() {
        tbc_tipo_campoSearchRepository.deleteAll();
        tbc_tipo_campo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_tipo_campo() throws Exception {
        int databaseSizeBeforeCreate = tbc_tipo_campoRepository.findAll().size();

        // Create the Tbc_tipo_campo

        restTbc_tipo_campoMockMvc.perform(post("/api/tbc-tipo-campos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_tipo_campo)))
            .andExpect(status().isCreated());

        // Validate the Tbc_tipo_campo in the database
        List<Tbc_tipo_campo> tbc_tipo_campoList = tbc_tipo_campoRepository.findAll();
        assertThat(tbc_tipo_campoList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_tipo_campo testTbc_tipo_campo = tbc_tipo_campoList.get(tbc_tipo_campoList.size() - 1);
        assertThat(testTbc_tipo_campo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_tipo_campo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the Tbc_tipo_campo in ElasticSearch
        Tbc_tipo_campo tbc_tipo_campoEs = tbc_tipo_campoSearchRepository.findOne(testTbc_tipo_campo.getId());
        assertThat(tbc_tipo_campoEs).isEqualToComparingFieldByField(testTbc_tipo_campo);
    }

    @Test
    @Transactional
    public void createTbc_tipo_campoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_tipo_campoRepository.findAll().size();

        // Create the Tbc_tipo_campo with an existing ID
        Tbc_tipo_campo existingTbc_tipo_campo = new Tbc_tipo_campo();
        existingTbc_tipo_campo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_tipo_campoMockMvc.perform(post("/api/tbc-tipo-campos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_tipo_campo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_tipo_campo> tbc_tipo_campoList = tbc_tipo_campoRepository.findAll();
        assertThat(tbc_tipo_campoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_tipo_campoRepository.findAll().size();
        // set the field null
        tbc_tipo_campo.setNome(null);

        // Create the Tbc_tipo_campo, which fails.

        restTbc_tipo_campoMockMvc.perform(post("/api/tbc-tipo-campos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_tipo_campo)))
            .andExpect(status().isBadRequest());

        List<Tbc_tipo_campo> tbc_tipo_campoList = tbc_tipo_campoRepository.findAll();
        assertThat(tbc_tipo_campoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_tipo_campos() throws Exception {
        // Initialize the database
        tbc_tipo_campoRepository.saveAndFlush(tbc_tipo_campo);

        // Get all the tbc_tipo_campoList
        restTbc_tipo_campoMockMvc.perform(get("/api/tbc-tipo-campos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_tipo_campo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getTbc_tipo_campo() throws Exception {
        // Initialize the database
        tbc_tipo_campoRepository.saveAndFlush(tbc_tipo_campo);

        // Get the tbc_tipo_campo
        restTbc_tipo_campoMockMvc.perform(get("/api/tbc-tipo-campos/{id}", tbc_tipo_campo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_tipo_campo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_tipo_campo() throws Exception {
        // Get the tbc_tipo_campo
        restTbc_tipo_campoMockMvc.perform(get("/api/tbc-tipo-campos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_tipo_campo() throws Exception {
        // Initialize the database
        tbc_tipo_campoService.save(tbc_tipo_campo);

        int databaseSizeBeforeUpdate = tbc_tipo_campoRepository.findAll().size();

        // Update the tbc_tipo_campo
        Tbc_tipo_campo updatedTbc_tipo_campo = tbc_tipo_campoRepository.findOne(tbc_tipo_campo.getId());
        updatedTbc_tipo_campo
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO);

        restTbc_tipo_campoMockMvc.perform(put("/api/tbc-tipo-campos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_tipo_campo)))
            .andExpect(status().isOk());

        // Validate the Tbc_tipo_campo in the database
        List<Tbc_tipo_campo> tbc_tipo_campoList = tbc_tipo_campoRepository.findAll();
        assertThat(tbc_tipo_campoList).hasSize(databaseSizeBeforeUpdate);
        Tbc_tipo_campo testTbc_tipo_campo = tbc_tipo_campoList.get(tbc_tipo_campoList.size() - 1);
        assertThat(testTbc_tipo_campo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_tipo_campo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the Tbc_tipo_campo in ElasticSearch
        Tbc_tipo_campo tbc_tipo_campoEs = tbc_tipo_campoSearchRepository.findOne(testTbc_tipo_campo.getId());
        assertThat(tbc_tipo_campoEs).isEqualToComparingFieldByField(testTbc_tipo_campo);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_tipo_campo() throws Exception {
        int databaseSizeBeforeUpdate = tbc_tipo_campoRepository.findAll().size();

        // Create the Tbc_tipo_campo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_tipo_campoMockMvc.perform(put("/api/tbc-tipo-campos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_tipo_campo)))
            .andExpect(status().isCreated());

        // Validate the Tbc_tipo_campo in the database
        List<Tbc_tipo_campo> tbc_tipo_campoList = tbc_tipo_campoRepository.findAll();
        assertThat(tbc_tipo_campoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_tipo_campo() throws Exception {
        // Initialize the database
        tbc_tipo_campoService.save(tbc_tipo_campo);

        int databaseSizeBeforeDelete = tbc_tipo_campoRepository.findAll().size();

        // Get the tbc_tipo_campo
        restTbc_tipo_campoMockMvc.perform(delete("/api/tbc-tipo-campos/{id}", tbc_tipo_campo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_tipo_campoExistsInEs = tbc_tipo_campoSearchRepository.exists(tbc_tipo_campo.getId());
        assertThat(tbc_tipo_campoExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_tipo_campo> tbc_tipo_campoList = tbc_tipo_campoRepository.findAll();
        assertThat(tbc_tipo_campoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_tipo_campo() throws Exception {
        // Initialize the database
        tbc_tipo_campoService.save(tbc_tipo_campo);

        // Search the tbc_tipo_campo
        restTbc_tipo_campoMockMvc.perform(get("/api/_search/tbc-tipo-campos?query=id:" + tbc_tipo_campo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_tipo_campo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
}
