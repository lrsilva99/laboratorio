package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_matriz;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.domain.Tbc_tipo_cadastro;
import com.labotech.lims.repository.Tbc_matrizRepository;
import com.labotech.lims.service.Tbc_matrizService;
import com.labotech.lims.repository.search.Tbc_matrizSearchRepository;

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
 * Test class for the Tbc_matrizResource REST controller.
 *
 * @see Tbc_matrizResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_matrizResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    @Inject
    private Tbc_matrizRepository tbc_matrizRepository;

    @Inject
    private Tbc_matrizService tbc_matrizService;

    @Inject
    private Tbc_matrizSearchRepository tbc_matrizSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_matrizMockMvc;

    private Tbc_matriz tbc_matriz;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_matrizResource tbc_matrizResource = new Tbc_matrizResource();
        ReflectionTestUtils.setField(tbc_matrizResource, "tbc_matrizService", tbc_matrizService);
        this.restTbc_matrizMockMvc = MockMvcBuilders.standaloneSetup(tbc_matrizResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_matriz createEntity(EntityManager em) {
        Tbc_matriz tbc_matriz = new Tbc_matriz()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .removido(DEFAULT_REMOVIDO);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_matriz.setTbc_instituicao(tbc_instituicao);
        // Add required entity
        Tbc_tipo_cadastro tbc_tipo_cadastro = Tbc_tipo_cadastroResourceIntTest.createEntity(em);
        em.persist(tbc_tipo_cadastro);
        em.flush();
        tbc_matriz.setTbc_tipo_cadastro(tbc_tipo_cadastro);
        return tbc_matriz;
    }

    @Before
    public void initTest() {
        tbc_matrizSearchRepository.deleteAll();
        tbc_matriz = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_matriz() throws Exception {
        int databaseSizeBeforeCreate = tbc_matrizRepository.findAll().size();

        // Create the Tbc_matriz

        restTbc_matrizMockMvc.perform(post("/api/tbc-matrizs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_matriz)))
            .andExpect(status().isCreated());

        // Validate the Tbc_matriz in the database
        List<Tbc_matriz> tbc_matrizList = tbc_matrizRepository.findAll();
        assertThat(tbc_matrizList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_matriz testTbc_matriz = tbc_matrizList.get(tbc_matrizList.size() - 1);
        assertThat(testTbc_matriz.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_matriz.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_matriz.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);

        // Validate the Tbc_matriz in ElasticSearch
        Tbc_matriz tbc_matrizEs = tbc_matrizSearchRepository.findOne(testTbc_matriz.getId());
        assertThat(tbc_matrizEs).isEqualToComparingFieldByField(testTbc_matriz);
    }

    @Test
    @Transactional
    public void createTbc_matrizWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_matrizRepository.findAll().size();

        // Create the Tbc_matriz with an existing ID
        Tbc_matriz existingTbc_matriz = new Tbc_matriz();
        existingTbc_matriz.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_matrizMockMvc.perform(post("/api/tbc-matrizs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_matriz)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_matriz> tbc_matrizList = tbc_matrizRepository.findAll();
        assertThat(tbc_matrizList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_matrizRepository.findAll().size();
        // set the field null
        tbc_matriz.setNome(null);

        // Create the Tbc_matriz, which fails.

        restTbc_matrizMockMvc.perform(post("/api/tbc-matrizs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_matriz)))
            .andExpect(status().isBadRequest());

        List<Tbc_matriz> tbc_matrizList = tbc_matrizRepository.findAll();
        assertThat(tbc_matrizList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_matrizs() throws Exception {
        // Initialize the database
        tbc_matrizRepository.saveAndFlush(tbc_matriz);

        // Get all the tbc_matrizList
        restTbc_matrizMockMvc.perform(get("/api/tbc-matrizs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_matriz.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTbc_matriz() throws Exception {
        // Initialize the database
        tbc_matrizRepository.saveAndFlush(tbc_matriz);

        // Get the tbc_matriz
        restTbc_matrizMockMvc.perform(get("/api/tbc-matrizs/{id}", tbc_matriz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_matriz.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_matriz() throws Exception {
        // Get the tbc_matriz
        restTbc_matrizMockMvc.perform(get("/api/tbc-matrizs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_matriz() throws Exception {
        // Initialize the database
        tbc_matrizService.save(tbc_matriz);

        int databaseSizeBeforeUpdate = tbc_matrizRepository.findAll().size();

        // Update the tbc_matriz
        Tbc_matriz updatedTbc_matriz = tbc_matrizRepository.findOne(tbc_matriz.getId());
        updatedTbc_matriz
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .removido(UPDATED_REMOVIDO);

        restTbc_matrizMockMvc.perform(put("/api/tbc-matrizs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_matriz)))
            .andExpect(status().isOk());

        // Validate the Tbc_matriz in the database
        List<Tbc_matriz> tbc_matrizList = tbc_matrizRepository.findAll();
        assertThat(tbc_matrizList).hasSize(databaseSizeBeforeUpdate);
        Tbc_matriz testTbc_matriz = tbc_matrizList.get(tbc_matrizList.size() - 1);
        assertThat(testTbc_matriz.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_matriz.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_matriz.isRemovido()).isEqualTo(UPDATED_REMOVIDO);

        // Validate the Tbc_matriz in ElasticSearch
        Tbc_matriz tbc_matrizEs = tbc_matrizSearchRepository.findOne(testTbc_matriz.getId());
        assertThat(tbc_matrizEs).isEqualToComparingFieldByField(testTbc_matriz);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_matriz() throws Exception {
        int databaseSizeBeforeUpdate = tbc_matrizRepository.findAll().size();

        // Create the Tbc_matriz

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_matrizMockMvc.perform(put("/api/tbc-matrizs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_matriz)))
            .andExpect(status().isCreated());

        // Validate the Tbc_matriz in the database
        List<Tbc_matriz> tbc_matrizList = tbc_matrizRepository.findAll();
        assertThat(tbc_matrizList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_matriz() throws Exception {
        // Initialize the database
        tbc_matrizService.save(tbc_matriz);

        int databaseSizeBeforeDelete = tbc_matrizRepository.findAll().size();

        // Get the tbc_matriz
        restTbc_matrizMockMvc.perform(delete("/api/tbc-matrizs/{id}", tbc_matriz.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_matrizExistsInEs = tbc_matrizSearchRepository.exists(tbc_matriz.getId());
        assertThat(tbc_matrizExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_matriz> tbc_matrizList = tbc_matrizRepository.findAll();
        assertThat(tbc_matrizList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_matriz() throws Exception {
        // Initialize the database
        tbc_matrizService.save(tbc_matriz);

        // Search the tbc_matriz
        restTbc_matrizMockMvc.perform(get("/api/_search/tbc-matrizs?query=id:" + tbc_matriz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_matriz.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }
}
