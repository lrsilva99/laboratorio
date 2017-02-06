package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.repository.Tbc_instituicaoRepository;
import com.labotech.lims.service.Tbc_instituicaoService;
import com.labotech.lims.repository.search.Tbc_instituicaoSearchRepository;

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
 * Test class for the Tbc_instituicaoResource REST controller.
 *
 * @see Tbc_instituicaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_instituicaoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String DEFAULT_UF = "AA";
    private static final String UPDATED_UF = "BB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Inject
    private Tbc_instituicaoRepository tbc_instituicaoRepository;

    @Inject
    private Tbc_instituicaoService tbc_instituicaoService;

    @Inject
    private Tbc_instituicaoSearchRepository tbc_instituicaoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_instituicaoMockMvc;

    private Tbc_instituicao tbc_instituicao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_instituicaoResource tbc_instituicaoResource = new Tbc_instituicaoResource();
        ReflectionTestUtils.setField(tbc_instituicaoResource, "tbc_instituicaoService", tbc_instituicaoService);
        this.restTbc_instituicaoMockMvc = MockMvcBuilders.standaloneSetup(tbc_instituicaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_instituicao createEntity(EntityManager em) {
        Tbc_instituicao tbc_instituicao = new Tbc_instituicao()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .sigla(DEFAULT_SIGLA)
                .uf(DEFAULT_UF)
                .endereco(DEFAULT_ENDERECO)
                .telefone(DEFAULT_TELEFONE)
                .removido(DEFAULT_REMOVIDO)
                .email(DEFAULT_EMAIL);
        return tbc_instituicao;
    }

    @Before
    public void initTest() {
        tbc_instituicaoSearchRepository.deleteAll();
        tbc_instituicao = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_instituicao() throws Exception {
        int databaseSizeBeforeCreate = tbc_instituicaoRepository.findAll().size();

        // Create the Tbc_instituicao

        restTbc_instituicaoMockMvc.perform(post("/api/tbc-instituicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_instituicao)))
            .andExpect(status().isCreated());

        // Validate the Tbc_instituicao in the database
        List<Tbc_instituicao> tbc_instituicaoList = tbc_instituicaoRepository.findAll();
        assertThat(tbc_instituicaoList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_instituicao testTbc_instituicao = tbc_instituicaoList.get(tbc_instituicaoList.size() - 1);
        assertThat(testTbc_instituicao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_instituicao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_instituicao.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testTbc_instituicao.getUf()).isEqualTo(DEFAULT_UF);
        assertThat(testTbc_instituicao.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testTbc_instituicao.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testTbc_instituicao.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);
        assertThat(testTbc_instituicao.getEmail()).isEqualTo(DEFAULT_EMAIL);

        // Validate the Tbc_instituicao in ElasticSearch
        Tbc_instituicao tbc_instituicaoEs = tbc_instituicaoSearchRepository.findOne(testTbc_instituicao.getId());
        assertThat(tbc_instituicaoEs).isEqualToComparingFieldByField(testTbc_instituicao);
    }

    @Test
    @Transactional
    public void createTbc_instituicaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_instituicaoRepository.findAll().size();

        // Create the Tbc_instituicao with an existing ID
        Tbc_instituicao existingTbc_instituicao = new Tbc_instituicao();
        existingTbc_instituicao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_instituicaoMockMvc.perform(post("/api/tbc-instituicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_instituicao)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_instituicao> tbc_instituicaoList = tbc_instituicaoRepository.findAll();
        assertThat(tbc_instituicaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_instituicaoRepository.findAll().size();
        // set the field null
        tbc_instituicao.setNome(null);

        // Create the Tbc_instituicao, which fails.

        restTbc_instituicaoMockMvc.perform(post("/api/tbc-instituicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_instituicao)))
            .andExpect(status().isBadRequest());

        List<Tbc_instituicao> tbc_instituicaoList = tbc_instituicaoRepository.findAll();
        assertThat(tbc_instituicaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_instituicaoRepository.findAll().size();
        // set the field null
        tbc_instituicao.setSigla(null);

        // Create the Tbc_instituicao, which fails.

        restTbc_instituicaoMockMvc.perform(post("/api/tbc-instituicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_instituicao)))
            .andExpect(status().isBadRequest());

        List<Tbc_instituicao> tbc_instituicaoList = tbc_instituicaoRepository.findAll();
        assertThat(tbc_instituicaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUfIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_instituicaoRepository.findAll().size();
        // set the field null
        tbc_instituicao.setUf(null);

        // Create the Tbc_instituicao, which fails.

        restTbc_instituicaoMockMvc.perform(post("/api/tbc-instituicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_instituicao)))
            .andExpect(status().isBadRequest());

        List<Tbc_instituicao> tbc_instituicaoList = tbc_instituicaoRepository.findAll();
        assertThat(tbc_instituicaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_instituicaoRepository.findAll().size();
        // set the field null
        tbc_instituicao.setEmail(null);

        // Create the Tbc_instituicao, which fails.

        restTbc_instituicaoMockMvc.perform(post("/api/tbc-instituicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_instituicao)))
            .andExpect(status().isBadRequest());

        List<Tbc_instituicao> tbc_instituicaoList = tbc_instituicaoRepository.findAll();
        assertThat(tbc_instituicaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_instituicaos() throws Exception {
        // Initialize the database
        tbc_instituicaoRepository.saveAndFlush(tbc_instituicao);

        // Get all the tbc_instituicaoList
        restTbc_instituicaoMockMvc.perform(get("/api/tbc-instituicaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_instituicao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA.toString())))
            .andExpect(jsonPath("$.[*].uf").value(hasItem(DEFAULT_UF.toString())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getTbc_instituicao() throws Exception {
        // Initialize the database
        tbc_instituicaoRepository.saveAndFlush(tbc_instituicao);

        // Get the tbc_instituicao
        restTbc_instituicaoMockMvc.perform(get("/api/tbc-instituicaos/{id}", tbc_instituicao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_instituicao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA.toString()))
            .andExpect(jsonPath("$.uf").value(DEFAULT_UF.toString()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_instituicao() throws Exception {
        // Get the tbc_instituicao
        restTbc_instituicaoMockMvc.perform(get("/api/tbc-instituicaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_instituicao() throws Exception {
        // Initialize the database
        tbc_instituicaoService.save(tbc_instituicao);

        int databaseSizeBeforeUpdate = tbc_instituicaoRepository.findAll().size();

        // Update the tbc_instituicao
        Tbc_instituicao updatedTbc_instituicao = tbc_instituicaoRepository.findOne(tbc_instituicao.getId());
        updatedTbc_instituicao
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .sigla(UPDATED_SIGLA)
                .uf(UPDATED_UF)
                .endereco(UPDATED_ENDERECO)
                .telefone(UPDATED_TELEFONE)
                .removido(UPDATED_REMOVIDO)
                .email(UPDATED_EMAIL);

        restTbc_instituicaoMockMvc.perform(put("/api/tbc-instituicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_instituicao)))
            .andExpect(status().isOk());

        // Validate the Tbc_instituicao in the database
        List<Tbc_instituicao> tbc_instituicaoList = tbc_instituicaoRepository.findAll();
        assertThat(tbc_instituicaoList).hasSize(databaseSizeBeforeUpdate);
        Tbc_instituicao testTbc_instituicao = tbc_instituicaoList.get(tbc_instituicaoList.size() - 1);
        assertThat(testTbc_instituicao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_instituicao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_instituicao.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testTbc_instituicao.getUf()).isEqualTo(UPDATED_UF);
        assertThat(testTbc_instituicao.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testTbc_instituicao.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testTbc_instituicao.isRemovido()).isEqualTo(UPDATED_REMOVIDO);
        assertThat(testTbc_instituicao.getEmail()).isEqualTo(UPDATED_EMAIL);

        // Validate the Tbc_instituicao in ElasticSearch
        Tbc_instituicao tbc_instituicaoEs = tbc_instituicaoSearchRepository.findOne(testTbc_instituicao.getId());
        assertThat(tbc_instituicaoEs).isEqualToComparingFieldByField(testTbc_instituicao);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_instituicao() throws Exception {
        int databaseSizeBeforeUpdate = tbc_instituicaoRepository.findAll().size();

        // Create the Tbc_instituicao

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_instituicaoMockMvc.perform(put("/api/tbc-instituicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_instituicao)))
            .andExpect(status().isCreated());

        // Validate the Tbc_instituicao in the database
        List<Tbc_instituicao> tbc_instituicaoList = tbc_instituicaoRepository.findAll();
        assertThat(tbc_instituicaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_instituicao() throws Exception {
        // Initialize the database
        tbc_instituicaoService.save(tbc_instituicao);

        int databaseSizeBeforeDelete = tbc_instituicaoRepository.findAll().size();

        // Get the tbc_instituicao
        restTbc_instituicaoMockMvc.perform(delete("/api/tbc-instituicaos/{id}", tbc_instituicao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_instituicaoExistsInEs = tbc_instituicaoSearchRepository.exists(tbc_instituicao.getId());
        assertThat(tbc_instituicaoExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_instituicao> tbc_instituicaoList = tbc_instituicaoRepository.findAll();
        assertThat(tbc_instituicaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_instituicao() throws Exception {
        // Initialize the database
        tbc_instituicaoService.save(tbc_instituicao);

        // Search the tbc_instituicao
        restTbc_instituicaoMockMvc.perform(get("/api/_search/tbc-instituicaos?query=id:" + tbc_instituicao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_instituicao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA.toString())))
            .andExpect(jsonPath("$.[*].uf").value(hasItem(DEFAULT_UF.toString())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
}
