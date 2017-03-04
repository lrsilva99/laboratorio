package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_fazenda;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.repository.Tbc_fazendaRepository;
import com.labotech.lims.service.Tbc_fazendaService;
import com.labotech.lims.repository.search.Tbc_fazendaSearchRepository;

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
 * Test class for the Tbc_fazendaResource REST controller.
 *
 * @see Tbc_fazendaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_fazendaResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Inject
    private Tbc_fazendaRepository tbc_fazendaRepository;

    @Inject
    private Tbc_fazendaService tbc_fazendaService;

    @Inject
    private Tbc_fazendaSearchRepository tbc_fazendaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_fazendaMockMvc;

    private Tbc_fazenda tbc_fazenda;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_fazendaResource tbc_fazendaResource = new Tbc_fazendaResource();
        ReflectionTestUtils.setField(tbc_fazendaResource, "tbc_fazendaService", tbc_fazendaService);
        this.restTbc_fazendaMockMvc = MockMvcBuilders.standaloneSetup(tbc_fazendaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_fazenda createEntity(EntityManager em) {
        Tbc_fazenda tbc_fazenda = new Tbc_fazenda()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .removido(DEFAULT_REMOVIDO)
                .email(DEFAULT_EMAIL);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_fazenda.setTbc_instituicao(tbc_instituicao);
        return tbc_fazenda;
    }

    @Before
    public void initTest() {
        tbc_fazendaSearchRepository.deleteAll();
        tbc_fazenda = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_fazenda() throws Exception {
        int databaseSizeBeforeCreate = tbc_fazendaRepository.findAll().size();

        // Create the Tbc_fazenda

        restTbc_fazendaMockMvc.perform(post("/api/tbc-fazendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_fazenda)))
            .andExpect(status().isCreated());

        // Validate the Tbc_fazenda in the database
        List<Tbc_fazenda> tbc_fazendaList = tbc_fazendaRepository.findAll();
        assertThat(tbc_fazendaList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_fazenda testTbc_fazenda = tbc_fazendaList.get(tbc_fazendaList.size() - 1);
        assertThat(testTbc_fazenda.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_fazenda.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_fazenda.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);
        assertThat(testTbc_fazenda.getEmail()).isEqualTo(DEFAULT_EMAIL);

        // Validate the Tbc_fazenda in ElasticSearch
        Tbc_fazenda tbc_fazendaEs = tbc_fazendaSearchRepository.findOne(testTbc_fazenda.getId());
        assertThat(tbc_fazendaEs).isEqualToComparingFieldByField(testTbc_fazenda);
    }

    @Test
    @Transactional
    public void createTbc_fazendaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_fazendaRepository.findAll().size();

        // Create the Tbc_fazenda with an existing ID
        Tbc_fazenda existingTbc_fazenda = new Tbc_fazenda();
        existingTbc_fazenda.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_fazendaMockMvc.perform(post("/api/tbc-fazendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_fazenda)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_fazenda> tbc_fazendaList = tbc_fazendaRepository.findAll();
        assertThat(tbc_fazendaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_fazendaRepository.findAll().size();
        // set the field null
        tbc_fazenda.setNome(null);

        // Create the Tbc_fazenda, which fails.

        restTbc_fazendaMockMvc.perform(post("/api/tbc-fazendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_fazenda)))
            .andExpect(status().isBadRequest());

        List<Tbc_fazenda> tbc_fazendaList = tbc_fazendaRepository.findAll();
        assertThat(tbc_fazendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_fazendas() throws Exception {
        // Initialize the database
        tbc_fazendaRepository.saveAndFlush(tbc_fazenda);

        // Get all the tbc_fazendaList
        restTbc_fazendaMockMvc.perform(get("/api/tbc-fazendas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_fazenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getTbc_fazenda() throws Exception {
        // Initialize the database
        tbc_fazendaRepository.saveAndFlush(tbc_fazenda);

        // Get the tbc_fazenda
        restTbc_fazendaMockMvc.perform(get("/api/tbc-fazendas/{id}", tbc_fazenda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_fazenda.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_fazenda() throws Exception {
        // Get the tbc_fazenda
        restTbc_fazendaMockMvc.perform(get("/api/tbc-fazendas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_fazenda() throws Exception {
        // Initialize the database
        tbc_fazendaService.save(tbc_fazenda);

        int databaseSizeBeforeUpdate = tbc_fazendaRepository.findAll().size();

        // Update the tbc_fazenda
        Tbc_fazenda updatedTbc_fazenda = tbc_fazendaRepository.findOne(tbc_fazenda.getId());
        updatedTbc_fazenda
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .removido(UPDATED_REMOVIDO)
                .email(UPDATED_EMAIL);

        restTbc_fazendaMockMvc.perform(put("/api/tbc-fazendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_fazenda)))
            .andExpect(status().isOk());

        // Validate the Tbc_fazenda in the database
        List<Tbc_fazenda> tbc_fazendaList = tbc_fazendaRepository.findAll();
        assertThat(tbc_fazendaList).hasSize(databaseSizeBeforeUpdate);
        Tbc_fazenda testTbc_fazenda = tbc_fazendaList.get(tbc_fazendaList.size() - 1);
        assertThat(testTbc_fazenda.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_fazenda.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_fazenda.isRemovido()).isEqualTo(UPDATED_REMOVIDO);
        assertThat(testTbc_fazenda.getEmail()).isEqualTo(UPDATED_EMAIL);

        // Validate the Tbc_fazenda in ElasticSearch
        Tbc_fazenda tbc_fazendaEs = tbc_fazendaSearchRepository.findOne(testTbc_fazenda.getId());
        assertThat(tbc_fazendaEs).isEqualToComparingFieldByField(testTbc_fazenda);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_fazenda() throws Exception {
        int databaseSizeBeforeUpdate = tbc_fazendaRepository.findAll().size();

        // Create the Tbc_fazenda

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_fazendaMockMvc.perform(put("/api/tbc-fazendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_fazenda)))
            .andExpect(status().isCreated());

        // Validate the Tbc_fazenda in the database
        List<Tbc_fazenda> tbc_fazendaList = tbc_fazendaRepository.findAll();
        assertThat(tbc_fazendaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_fazenda() throws Exception {
        // Initialize the database
        tbc_fazendaService.save(tbc_fazenda);

        int databaseSizeBeforeDelete = tbc_fazendaRepository.findAll().size();

        // Get the tbc_fazenda
        restTbc_fazendaMockMvc.perform(delete("/api/tbc-fazendas/{id}", tbc_fazenda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_fazendaExistsInEs = tbc_fazendaSearchRepository.exists(tbc_fazenda.getId());
        assertThat(tbc_fazendaExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_fazenda> tbc_fazendaList = tbc_fazendaRepository.findAll();
        assertThat(tbc_fazendaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_fazenda() throws Exception {
        // Initialize the database
        tbc_fazendaService.save(tbc_fazenda);

        // Search the tbc_fazenda
        restTbc_fazendaMockMvc.perform(get("/api/_search/tbc-fazendas?query=id:" + tbc_fazenda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_fazenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
}
