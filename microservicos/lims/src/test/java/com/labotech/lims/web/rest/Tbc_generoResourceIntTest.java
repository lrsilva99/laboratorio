package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_genero;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.repository.Tbc_generoRepository;
import com.labotech.lims.service.Tbc_generoService;
import com.labotech.lims.repository.search.Tbc_generoSearchRepository;

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
 * Test class for the Tbc_generoResource REST controller.
 *
 * @see Tbc_generoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_generoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVER = false;
    private static final Boolean UPDATED_REMOVER = true;

    @Inject
    private Tbc_generoRepository tbc_generoRepository;

    @Inject
    private Tbc_generoService tbc_generoService;

    @Inject
    private Tbc_generoSearchRepository tbc_generoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_generoMockMvc;

    private Tbc_genero tbc_genero;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_generoResource tbc_generoResource = new Tbc_generoResource();
        ReflectionTestUtils.setField(tbc_generoResource, "tbc_generoService", tbc_generoService);
        this.restTbc_generoMockMvc = MockMvcBuilders.standaloneSetup(tbc_generoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_genero createEntity(EntityManager em) {
        Tbc_genero tbc_genero = new Tbc_genero()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .remover(DEFAULT_REMOVER);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_genero.setTbc_instituicao(tbc_instituicao);
        return tbc_genero;
    }

    @Before
    public void initTest() {
        tbc_generoSearchRepository.deleteAll();
        tbc_genero = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_genero() throws Exception {
        int databaseSizeBeforeCreate = tbc_generoRepository.findAll().size();

        // Create the Tbc_genero

        restTbc_generoMockMvc.perform(post("/api/tbc-generos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_genero)))
            .andExpect(status().isCreated());

        // Validate the Tbc_genero in the database
        List<Tbc_genero> tbc_generoList = tbc_generoRepository.findAll();
        assertThat(tbc_generoList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_genero testTbc_genero = tbc_generoList.get(tbc_generoList.size() - 1);
        assertThat(testTbc_genero.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_genero.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_genero.isRemover()).isEqualTo(DEFAULT_REMOVER);

        // Validate the Tbc_genero in ElasticSearch
        Tbc_genero tbc_generoEs = tbc_generoSearchRepository.findOne(testTbc_genero.getId());
        assertThat(tbc_generoEs).isEqualToComparingFieldByField(testTbc_genero);
    }

    @Test
    @Transactional
    public void createTbc_generoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_generoRepository.findAll().size();

        // Create the Tbc_genero with an existing ID
        Tbc_genero existingTbc_genero = new Tbc_genero();
        existingTbc_genero.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_generoMockMvc.perform(post("/api/tbc-generos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_genero)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_genero> tbc_generoList = tbc_generoRepository.findAll();
        assertThat(tbc_generoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_generoRepository.findAll().size();
        // set the field null
        tbc_genero.setNome(null);

        // Create the Tbc_genero, which fails.

        restTbc_generoMockMvc.perform(post("/api/tbc-generos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_genero)))
            .andExpect(status().isBadRequest());

        List<Tbc_genero> tbc_generoList = tbc_generoRepository.findAll();
        assertThat(tbc_generoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_generos() throws Exception {
        // Initialize the database
        tbc_generoRepository.saveAndFlush(tbc_genero);

        // Get all the tbc_generoList
        restTbc_generoMockMvc.perform(get("/api/tbc-generos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_genero.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].remover").value(hasItem(DEFAULT_REMOVER.booleanValue())));
    }

    @Test
    @Transactional
    public void getTbc_genero() throws Exception {
        // Initialize the database
        tbc_generoRepository.saveAndFlush(tbc_genero);

        // Get the tbc_genero
        restTbc_generoMockMvc.perform(get("/api/tbc-generos/{id}", tbc_genero.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_genero.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.remover").value(DEFAULT_REMOVER.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_genero() throws Exception {
        // Get the tbc_genero
        restTbc_generoMockMvc.perform(get("/api/tbc-generos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_genero() throws Exception {
        // Initialize the database
        tbc_generoService.save(tbc_genero);

        int databaseSizeBeforeUpdate = tbc_generoRepository.findAll().size();

        // Update the tbc_genero
        Tbc_genero updatedTbc_genero = tbc_generoRepository.findOne(tbc_genero.getId());
        updatedTbc_genero
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .remover(UPDATED_REMOVER);

        restTbc_generoMockMvc.perform(put("/api/tbc-generos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_genero)))
            .andExpect(status().isOk());

        // Validate the Tbc_genero in the database
        List<Tbc_genero> tbc_generoList = tbc_generoRepository.findAll();
        assertThat(tbc_generoList).hasSize(databaseSizeBeforeUpdate);
        Tbc_genero testTbc_genero = tbc_generoList.get(tbc_generoList.size() - 1);
        assertThat(testTbc_genero.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_genero.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_genero.isRemover()).isEqualTo(UPDATED_REMOVER);

        // Validate the Tbc_genero in ElasticSearch
        Tbc_genero tbc_generoEs = tbc_generoSearchRepository.findOne(testTbc_genero.getId());
        assertThat(tbc_generoEs).isEqualToComparingFieldByField(testTbc_genero);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_genero() throws Exception {
        int databaseSizeBeforeUpdate = tbc_generoRepository.findAll().size();

        // Create the Tbc_genero

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_generoMockMvc.perform(put("/api/tbc-generos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_genero)))
            .andExpect(status().isCreated());

        // Validate the Tbc_genero in the database
        List<Tbc_genero> tbc_generoList = tbc_generoRepository.findAll();
        assertThat(tbc_generoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_genero() throws Exception {
        // Initialize the database
        tbc_generoService.save(tbc_genero);

        int databaseSizeBeforeDelete = tbc_generoRepository.findAll().size();

        // Get the tbc_genero
        restTbc_generoMockMvc.perform(delete("/api/tbc-generos/{id}", tbc_genero.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_generoExistsInEs = tbc_generoSearchRepository.exists(tbc_genero.getId());
        assertThat(tbc_generoExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_genero> tbc_generoList = tbc_generoRepository.findAll();
        assertThat(tbc_generoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_genero() throws Exception {
        // Initialize the database
        tbc_generoService.save(tbc_genero);

        // Search the tbc_genero
        restTbc_generoMockMvc.perform(get("/api/_search/tbc-generos?query=id:" + tbc_genero.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_genero.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].remover").value(hasItem(DEFAULT_REMOVER.booleanValue())));
    }
}
