package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_plano_teste;
import com.labotech.lims.domain.Tbc_tipo_cadastro;
import com.labotech.lims.domain.Tbc_sub_grupo;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.repository.Tbc_plano_testeRepository;
import com.labotech.lims.service.Tbc_plano_testeService;
import com.labotech.lims.repository.search.Tbc_plano_testeSearchRepository;

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
 * Test class for the Tbc_plano_testeResource REST controller.
 *
 * @see Tbc_plano_testeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_plano_testeResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    @Inject
    private Tbc_plano_testeRepository tbc_plano_testeRepository;

    @Inject
    private Tbc_plano_testeService tbc_plano_testeService;

    @Inject
    private Tbc_plano_testeSearchRepository tbc_plano_testeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_plano_testeMockMvc;

    private Tbc_plano_teste tbc_plano_teste;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_plano_testeResource tbc_plano_testeResource = new Tbc_plano_testeResource();
        ReflectionTestUtils.setField(tbc_plano_testeResource, "tbc_plano_testeService", tbc_plano_testeService);
        this.restTbc_plano_testeMockMvc = MockMvcBuilders.standaloneSetup(tbc_plano_testeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_plano_teste createEntity(EntityManager em) {
        Tbc_plano_teste tbc_plano_teste = new Tbc_plano_teste()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .removido(DEFAULT_REMOVIDO);
        // Add required entity
        Tbc_tipo_cadastro tbc_tipo_cadastro = Tbc_tipo_cadastroResourceIntTest.createEntity(em);
        em.persist(tbc_tipo_cadastro);
        em.flush();
        tbc_plano_teste.setTbc_tipo_cadastro(tbc_tipo_cadastro);
        // Add required entity
        Tbc_sub_grupo tbc_sub_grupo = Tbc_sub_grupoResourceIntTest.createEntity(em);
        em.persist(tbc_sub_grupo);
        em.flush();
        tbc_plano_teste.setTbc_sub_grupo(tbc_sub_grupo);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_plano_teste.setTbc_instituicao(tbc_instituicao);
        return tbc_plano_teste;
    }

    @Before
    public void initTest() {
        tbc_plano_testeSearchRepository.deleteAll();
        tbc_plano_teste = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_plano_teste() throws Exception {
        int databaseSizeBeforeCreate = tbc_plano_testeRepository.findAll().size();

        // Create the Tbc_plano_teste

        restTbc_plano_testeMockMvc.perform(post("/api/tbc-plano-testes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_plano_teste)))
            .andExpect(status().isCreated());

        // Validate the Tbc_plano_teste in the database
        List<Tbc_plano_teste> tbc_plano_testeList = tbc_plano_testeRepository.findAll();
        assertThat(tbc_plano_testeList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_plano_teste testTbc_plano_teste = tbc_plano_testeList.get(tbc_plano_testeList.size() - 1);
        assertThat(testTbc_plano_teste.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_plano_teste.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_plano_teste.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);

        // Validate the Tbc_plano_teste in ElasticSearch
        Tbc_plano_teste tbc_plano_testeEs = tbc_plano_testeSearchRepository.findOne(testTbc_plano_teste.getId());
        assertThat(tbc_plano_testeEs).isEqualToComparingFieldByField(testTbc_plano_teste);
    }

    @Test
    @Transactional
    public void createTbc_plano_testeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_plano_testeRepository.findAll().size();

        // Create the Tbc_plano_teste with an existing ID
        Tbc_plano_teste existingTbc_plano_teste = new Tbc_plano_teste();
        existingTbc_plano_teste.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_plano_testeMockMvc.perform(post("/api/tbc-plano-testes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_plano_teste)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_plano_teste> tbc_plano_testeList = tbc_plano_testeRepository.findAll();
        assertThat(tbc_plano_testeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_plano_testeRepository.findAll().size();
        // set the field null
        tbc_plano_teste.setNome(null);

        // Create the Tbc_plano_teste, which fails.

        restTbc_plano_testeMockMvc.perform(post("/api/tbc-plano-testes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_plano_teste)))
            .andExpect(status().isBadRequest());

        List<Tbc_plano_teste> tbc_plano_testeList = tbc_plano_testeRepository.findAll();
        assertThat(tbc_plano_testeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_plano_testes() throws Exception {
        // Initialize the database
        tbc_plano_testeRepository.saveAndFlush(tbc_plano_teste);

        // Get all the tbc_plano_testeList
        restTbc_plano_testeMockMvc.perform(get("/api/tbc-plano-testes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_plano_teste.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTbc_plano_teste() throws Exception {
        // Initialize the database
        tbc_plano_testeRepository.saveAndFlush(tbc_plano_teste);

        // Get the tbc_plano_teste
        restTbc_plano_testeMockMvc.perform(get("/api/tbc-plano-testes/{id}", tbc_plano_teste.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_plano_teste.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_plano_teste() throws Exception {
        // Get the tbc_plano_teste
        restTbc_plano_testeMockMvc.perform(get("/api/tbc-plano-testes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_plano_teste() throws Exception {
        // Initialize the database
        tbc_plano_testeService.save(tbc_plano_teste);

        int databaseSizeBeforeUpdate = tbc_plano_testeRepository.findAll().size();

        // Update the tbc_plano_teste
        Tbc_plano_teste updatedTbc_plano_teste = tbc_plano_testeRepository.findOne(tbc_plano_teste.getId());
        updatedTbc_plano_teste
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .removido(UPDATED_REMOVIDO);

        restTbc_plano_testeMockMvc.perform(put("/api/tbc-plano-testes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_plano_teste)))
            .andExpect(status().isOk());

        // Validate the Tbc_plano_teste in the database
        List<Tbc_plano_teste> tbc_plano_testeList = tbc_plano_testeRepository.findAll();
        assertThat(tbc_plano_testeList).hasSize(databaseSizeBeforeUpdate);
        Tbc_plano_teste testTbc_plano_teste = tbc_plano_testeList.get(tbc_plano_testeList.size() - 1);
        assertThat(testTbc_plano_teste.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_plano_teste.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_plano_teste.isRemovido()).isEqualTo(UPDATED_REMOVIDO);

        // Validate the Tbc_plano_teste in ElasticSearch
        Tbc_plano_teste tbc_plano_testeEs = tbc_plano_testeSearchRepository.findOne(testTbc_plano_teste.getId());
        assertThat(tbc_plano_testeEs).isEqualToComparingFieldByField(testTbc_plano_teste);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_plano_teste() throws Exception {
        int databaseSizeBeforeUpdate = tbc_plano_testeRepository.findAll().size();

        // Create the Tbc_plano_teste

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_plano_testeMockMvc.perform(put("/api/tbc-plano-testes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_plano_teste)))
            .andExpect(status().isCreated());

        // Validate the Tbc_plano_teste in the database
        List<Tbc_plano_teste> tbc_plano_testeList = tbc_plano_testeRepository.findAll();
        assertThat(tbc_plano_testeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_plano_teste() throws Exception {
        // Initialize the database
        tbc_plano_testeService.save(tbc_plano_teste);

        int databaseSizeBeforeDelete = tbc_plano_testeRepository.findAll().size();

        // Get the tbc_plano_teste
        restTbc_plano_testeMockMvc.perform(delete("/api/tbc-plano-testes/{id}", tbc_plano_teste.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_plano_testeExistsInEs = tbc_plano_testeSearchRepository.exists(tbc_plano_teste.getId());
        assertThat(tbc_plano_testeExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_plano_teste> tbc_plano_testeList = tbc_plano_testeRepository.findAll();
        assertThat(tbc_plano_testeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_plano_teste() throws Exception {
        // Initialize the database
        tbc_plano_testeService.save(tbc_plano_teste);

        // Search the tbc_plano_teste
        restTbc_plano_testeMockMvc.perform(get("/api/_search/tbc-plano-testes?query=id:" + tbc_plano_teste.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_plano_teste.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }
}
