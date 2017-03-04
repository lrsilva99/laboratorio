package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_proprietario;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.repository.Tbc_proprietarioRepository;
import com.labotech.lims.service.Tbc_proprietarioService;
import com.labotech.lims.repository.search.Tbc_proprietarioSearchRepository;

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
 * Test class for the Tbc_proprietarioResource REST controller.
 *
 * @see Tbc_proprietarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_proprietarioResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_CPF_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CPF_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Inject
    private Tbc_proprietarioRepository tbc_proprietarioRepository;

    @Inject
    private Tbc_proprietarioService tbc_proprietarioService;

    @Inject
    private Tbc_proprietarioSearchRepository tbc_proprietarioSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_proprietarioMockMvc;

    private Tbc_proprietario tbc_proprietario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_proprietarioResource tbc_proprietarioResource = new Tbc_proprietarioResource();
        ReflectionTestUtils.setField(tbc_proprietarioResource, "tbc_proprietarioService", tbc_proprietarioService);
        this.restTbc_proprietarioMockMvc = MockMvcBuilders.standaloneSetup(tbc_proprietarioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_proprietario createEntity(EntityManager em) {
        Tbc_proprietario tbc_proprietario = new Tbc_proprietario()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .removido(DEFAULT_REMOVIDO)
                .endereco(DEFAULT_ENDERECO)
                .cpf_cnpj(DEFAULT_CPF_CNPJ)
                .email(DEFAULT_EMAIL);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_proprietario.setTbc_instituicao(tbc_instituicao);
        return tbc_proprietario;
    }

    @Before
    public void initTest() {
        tbc_proprietarioSearchRepository.deleteAll();
        tbc_proprietario = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_proprietario() throws Exception {
        int databaseSizeBeforeCreate = tbc_proprietarioRepository.findAll().size();

        // Create the Tbc_proprietario

        restTbc_proprietarioMockMvc.perform(post("/api/tbc-proprietarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_proprietario)))
            .andExpect(status().isCreated());

        // Validate the Tbc_proprietario in the database
        List<Tbc_proprietario> tbc_proprietarioList = tbc_proprietarioRepository.findAll();
        assertThat(tbc_proprietarioList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_proprietario testTbc_proprietario = tbc_proprietarioList.get(tbc_proprietarioList.size() - 1);
        assertThat(testTbc_proprietario.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_proprietario.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_proprietario.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);
        assertThat(testTbc_proprietario.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testTbc_proprietario.getCpf_cnpj()).isEqualTo(DEFAULT_CPF_CNPJ);
        assertThat(testTbc_proprietario.getEmail()).isEqualTo(DEFAULT_EMAIL);

        // Validate the Tbc_proprietario in ElasticSearch
        Tbc_proprietario tbc_proprietarioEs = tbc_proprietarioSearchRepository.findOne(testTbc_proprietario.getId());
        assertThat(tbc_proprietarioEs).isEqualToComparingFieldByField(testTbc_proprietario);
    }

    @Test
    @Transactional
    public void createTbc_proprietarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_proprietarioRepository.findAll().size();

        // Create the Tbc_proprietario with an existing ID
        Tbc_proprietario existingTbc_proprietario = new Tbc_proprietario();
        existingTbc_proprietario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_proprietarioMockMvc.perform(post("/api/tbc-proprietarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_proprietario)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_proprietario> tbc_proprietarioList = tbc_proprietarioRepository.findAll();
        assertThat(tbc_proprietarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_proprietarioRepository.findAll().size();
        // set the field null
        tbc_proprietario.setNome(null);

        // Create the Tbc_proprietario, which fails.

        restTbc_proprietarioMockMvc.perform(post("/api/tbc-proprietarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_proprietario)))
            .andExpect(status().isBadRequest());

        List<Tbc_proprietario> tbc_proprietarioList = tbc_proprietarioRepository.findAll();
        assertThat(tbc_proprietarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_proprietarioRepository.findAll().size();
        // set the field null
        tbc_proprietario.setDescricao(null);

        // Create the Tbc_proprietario, which fails.

        restTbc_proprietarioMockMvc.perform(post("/api/tbc-proprietarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_proprietario)))
            .andExpect(status().isBadRequest());

        List<Tbc_proprietario> tbc_proprietarioList = tbc_proprietarioRepository.findAll();
        assertThat(tbc_proprietarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_proprietarioRepository.findAll().size();
        // set the field null
        tbc_proprietario.setEndereco(null);

        // Create the Tbc_proprietario, which fails.

        restTbc_proprietarioMockMvc.perform(post("/api/tbc-proprietarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_proprietario)))
            .andExpect(status().isBadRequest());

        List<Tbc_proprietario> tbc_proprietarioList = tbc_proprietarioRepository.findAll();
        assertThat(tbc_proprietarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCpf_cnpjIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_proprietarioRepository.findAll().size();
        // set the field null
        tbc_proprietario.setCpf_cnpj(null);

        // Create the Tbc_proprietario, which fails.

        restTbc_proprietarioMockMvc.perform(post("/api/tbc-proprietarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_proprietario)))
            .andExpect(status().isBadRequest());

        List<Tbc_proprietario> tbc_proprietarioList = tbc_proprietarioRepository.findAll();
        assertThat(tbc_proprietarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_proprietarios() throws Exception {
        // Initialize the database
        tbc_proprietarioRepository.saveAndFlush(tbc_proprietario);

        // Get all the tbc_proprietarioList
        restTbc_proprietarioMockMvc.perform(get("/api/tbc-proprietarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_proprietario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].cpf_cnpj").value(hasItem(DEFAULT_CPF_CNPJ.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getTbc_proprietario() throws Exception {
        // Initialize the database
        tbc_proprietarioRepository.saveAndFlush(tbc_proprietario);

        // Get the tbc_proprietario
        restTbc_proprietarioMockMvc.perform(get("/api/tbc-proprietarios/{id}", tbc_proprietario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_proprietario.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
            .andExpect(jsonPath("$.cpf_cnpj").value(DEFAULT_CPF_CNPJ.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_proprietario() throws Exception {
        // Get the tbc_proprietario
        restTbc_proprietarioMockMvc.perform(get("/api/tbc-proprietarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_proprietario() throws Exception {
        // Initialize the database
        tbc_proprietarioService.save(tbc_proprietario);

        int databaseSizeBeforeUpdate = tbc_proprietarioRepository.findAll().size();

        // Update the tbc_proprietario
        Tbc_proprietario updatedTbc_proprietario = tbc_proprietarioRepository.findOne(tbc_proprietario.getId());
        updatedTbc_proprietario
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .removido(UPDATED_REMOVIDO)
                .endereco(UPDATED_ENDERECO)
                .cpf_cnpj(UPDATED_CPF_CNPJ)
                .email(UPDATED_EMAIL);

        restTbc_proprietarioMockMvc.perform(put("/api/tbc-proprietarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_proprietario)))
            .andExpect(status().isOk());

        // Validate the Tbc_proprietario in the database
        List<Tbc_proprietario> tbc_proprietarioList = tbc_proprietarioRepository.findAll();
        assertThat(tbc_proprietarioList).hasSize(databaseSizeBeforeUpdate);
        Tbc_proprietario testTbc_proprietario = tbc_proprietarioList.get(tbc_proprietarioList.size() - 1);
        assertThat(testTbc_proprietario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_proprietario.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_proprietario.isRemovido()).isEqualTo(UPDATED_REMOVIDO);
        assertThat(testTbc_proprietario.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testTbc_proprietario.getCpf_cnpj()).isEqualTo(UPDATED_CPF_CNPJ);
        assertThat(testTbc_proprietario.getEmail()).isEqualTo(UPDATED_EMAIL);

        // Validate the Tbc_proprietario in ElasticSearch
        Tbc_proprietario tbc_proprietarioEs = tbc_proprietarioSearchRepository.findOne(testTbc_proprietario.getId());
        assertThat(tbc_proprietarioEs).isEqualToComparingFieldByField(testTbc_proprietario);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_proprietario() throws Exception {
        int databaseSizeBeforeUpdate = tbc_proprietarioRepository.findAll().size();

        // Create the Tbc_proprietario

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_proprietarioMockMvc.perform(put("/api/tbc-proprietarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_proprietario)))
            .andExpect(status().isCreated());

        // Validate the Tbc_proprietario in the database
        List<Tbc_proprietario> tbc_proprietarioList = tbc_proprietarioRepository.findAll();
        assertThat(tbc_proprietarioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_proprietario() throws Exception {
        // Initialize the database
        tbc_proprietarioService.save(tbc_proprietario);

        int databaseSizeBeforeDelete = tbc_proprietarioRepository.findAll().size();

        // Get the tbc_proprietario
        restTbc_proprietarioMockMvc.perform(delete("/api/tbc-proprietarios/{id}", tbc_proprietario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_proprietarioExistsInEs = tbc_proprietarioSearchRepository.exists(tbc_proprietario.getId());
        assertThat(tbc_proprietarioExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_proprietario> tbc_proprietarioList = tbc_proprietarioRepository.findAll();
        assertThat(tbc_proprietarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_proprietario() throws Exception {
        // Initialize the database
        tbc_proprietarioService.save(tbc_proprietario);

        // Search the tbc_proprietario
        restTbc_proprietarioMockMvc.perform(get("/api/_search/tbc-proprietarios?query=id:" + tbc_proprietario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_proprietario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].cpf_cnpj").value(hasItem(DEFAULT_CPF_CNPJ.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
}
