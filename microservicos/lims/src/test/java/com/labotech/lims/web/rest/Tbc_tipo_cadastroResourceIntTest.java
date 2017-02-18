package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_tipo_cadastro;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.repository.Tbc_tipo_cadastroRepository;
import com.labotech.lims.service.Tbc_tipo_cadastroService;
import com.labotech.lims.repository.search.Tbc_tipo_cadastroSearchRepository;

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
 * Test class for the Tbc_tipo_cadastroResource REST controller.
 *
 * @see Tbc_tipo_cadastroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_tipo_cadastroResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    @Inject
    private Tbc_tipo_cadastroRepository tbc_tipo_cadastroRepository;

    @Inject
    private Tbc_tipo_cadastroService tbc_tipo_cadastroService;

    @Inject
    private Tbc_tipo_cadastroSearchRepository tbc_tipo_cadastroSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_tipo_cadastroMockMvc;

    private Tbc_tipo_cadastro tbc_tipo_cadastro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_tipo_cadastroResource tbc_tipo_cadastroResource = new Tbc_tipo_cadastroResource();
        ReflectionTestUtils.setField(tbc_tipo_cadastroResource, "tbc_tipo_cadastroService", tbc_tipo_cadastroService);
        this.restTbc_tipo_cadastroMockMvc = MockMvcBuilders.standaloneSetup(tbc_tipo_cadastroResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_tipo_cadastro createEntity(EntityManager em) {
        Tbc_tipo_cadastro tbc_tipo_cadastro = new Tbc_tipo_cadastro()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .removido(DEFAULT_REMOVIDO);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_tipo_cadastro.setTbc_instituicao(tbc_instituicao);
        return tbc_tipo_cadastro;
    }

    @Before
    public void initTest() {
        tbc_tipo_cadastroSearchRepository.deleteAll();
        tbc_tipo_cadastro = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_tipo_cadastro() throws Exception {
        int databaseSizeBeforeCreate = tbc_tipo_cadastroRepository.findAll().size();

        // Create the Tbc_tipo_cadastro

        restTbc_tipo_cadastroMockMvc.perform(post("/api/tbc-tipo-cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_tipo_cadastro)))
            .andExpect(status().isCreated());

        // Validate the Tbc_tipo_cadastro in the database
        List<Tbc_tipo_cadastro> tbc_tipo_cadastroList = tbc_tipo_cadastroRepository.findAll();
        assertThat(tbc_tipo_cadastroList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_tipo_cadastro testTbc_tipo_cadastro = tbc_tipo_cadastroList.get(tbc_tipo_cadastroList.size() - 1);
        assertThat(testTbc_tipo_cadastro.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_tipo_cadastro.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_tipo_cadastro.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);

        // Validate the Tbc_tipo_cadastro in ElasticSearch
        Tbc_tipo_cadastro tbc_tipo_cadastroEs = tbc_tipo_cadastroSearchRepository.findOne(testTbc_tipo_cadastro.getId());
        assertThat(tbc_tipo_cadastroEs).isEqualToComparingFieldByField(testTbc_tipo_cadastro);
    }

    @Test
    @Transactional
    public void createTbc_tipo_cadastroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_tipo_cadastroRepository.findAll().size();

        // Create the Tbc_tipo_cadastro with an existing ID
        Tbc_tipo_cadastro existingTbc_tipo_cadastro = new Tbc_tipo_cadastro();
        existingTbc_tipo_cadastro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_tipo_cadastroMockMvc.perform(post("/api/tbc-tipo-cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_tipo_cadastro)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_tipo_cadastro> tbc_tipo_cadastroList = tbc_tipo_cadastroRepository.findAll();
        assertThat(tbc_tipo_cadastroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_tipo_cadastroRepository.findAll().size();
        // set the field null
        tbc_tipo_cadastro.setNome(null);

        // Create the Tbc_tipo_cadastro, which fails.

        restTbc_tipo_cadastroMockMvc.perform(post("/api/tbc-tipo-cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_tipo_cadastro)))
            .andExpect(status().isBadRequest());

        List<Tbc_tipo_cadastro> tbc_tipo_cadastroList = tbc_tipo_cadastroRepository.findAll();
        assertThat(tbc_tipo_cadastroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_tipo_cadastros() throws Exception {
        // Initialize the database
        tbc_tipo_cadastroRepository.saveAndFlush(tbc_tipo_cadastro);

        // Get all the tbc_tipo_cadastroList
        restTbc_tipo_cadastroMockMvc.perform(get("/api/tbc-tipo-cadastros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_tipo_cadastro.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTbc_tipo_cadastro() throws Exception {
        // Initialize the database
        tbc_tipo_cadastroRepository.saveAndFlush(tbc_tipo_cadastro);

        // Get the tbc_tipo_cadastro
        restTbc_tipo_cadastroMockMvc.perform(get("/api/tbc-tipo-cadastros/{id}", tbc_tipo_cadastro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_tipo_cadastro.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_tipo_cadastro() throws Exception {
        // Get the tbc_tipo_cadastro
        restTbc_tipo_cadastroMockMvc.perform(get("/api/tbc-tipo-cadastros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_tipo_cadastro() throws Exception {
        // Initialize the database
        tbc_tipo_cadastroService.save(tbc_tipo_cadastro);

        int databaseSizeBeforeUpdate = tbc_tipo_cadastroRepository.findAll().size();

        // Update the tbc_tipo_cadastro
        Tbc_tipo_cadastro updatedTbc_tipo_cadastro = tbc_tipo_cadastroRepository.findOne(tbc_tipo_cadastro.getId());
        updatedTbc_tipo_cadastro
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .removido(UPDATED_REMOVIDO);

        restTbc_tipo_cadastroMockMvc.perform(put("/api/tbc-tipo-cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_tipo_cadastro)))
            .andExpect(status().isOk());

        // Validate the Tbc_tipo_cadastro in the database
        List<Tbc_tipo_cadastro> tbc_tipo_cadastroList = tbc_tipo_cadastroRepository.findAll();
        assertThat(tbc_tipo_cadastroList).hasSize(databaseSizeBeforeUpdate);
        Tbc_tipo_cadastro testTbc_tipo_cadastro = tbc_tipo_cadastroList.get(tbc_tipo_cadastroList.size() - 1);
        assertThat(testTbc_tipo_cadastro.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_tipo_cadastro.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_tipo_cadastro.isRemovido()).isEqualTo(UPDATED_REMOVIDO);

        // Validate the Tbc_tipo_cadastro in ElasticSearch
        Tbc_tipo_cadastro tbc_tipo_cadastroEs = tbc_tipo_cadastroSearchRepository.findOne(testTbc_tipo_cadastro.getId());
        assertThat(tbc_tipo_cadastroEs).isEqualToComparingFieldByField(testTbc_tipo_cadastro);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_tipo_cadastro() throws Exception {
        int databaseSizeBeforeUpdate = tbc_tipo_cadastroRepository.findAll().size();

        // Create the Tbc_tipo_cadastro

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_tipo_cadastroMockMvc.perform(put("/api/tbc-tipo-cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_tipo_cadastro)))
            .andExpect(status().isCreated());

        // Validate the Tbc_tipo_cadastro in the database
        List<Tbc_tipo_cadastro> tbc_tipo_cadastroList = tbc_tipo_cadastroRepository.findAll();
        assertThat(tbc_tipo_cadastroList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_tipo_cadastro() throws Exception {
        // Initialize the database
        tbc_tipo_cadastroService.save(tbc_tipo_cadastro);

        int databaseSizeBeforeDelete = tbc_tipo_cadastroRepository.findAll().size();

        // Get the tbc_tipo_cadastro
        restTbc_tipo_cadastroMockMvc.perform(delete("/api/tbc-tipo-cadastros/{id}", tbc_tipo_cadastro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_tipo_cadastroExistsInEs = tbc_tipo_cadastroSearchRepository.exists(tbc_tipo_cadastro.getId());
        assertThat(tbc_tipo_cadastroExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_tipo_cadastro> tbc_tipo_cadastroList = tbc_tipo_cadastroRepository.findAll();
        assertThat(tbc_tipo_cadastroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_tipo_cadastro() throws Exception {
        // Initialize the database
        tbc_tipo_cadastroService.save(tbc_tipo_cadastro);

        // Search the tbc_tipo_cadastro
        restTbc_tipo_cadastroMockMvc.perform(get("/api/_search/tbc-tipo-cadastros?query=id:" + tbc_tipo_cadastro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_tipo_cadastro.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }
}
