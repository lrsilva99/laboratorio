package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_grupo_cliente;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.repository.Tbc_grupo_clienteRepository;
import com.labotech.lims.service.Tbc_grupo_clienteService;
import com.labotech.lims.repository.search.Tbc_grupo_clienteSearchRepository;

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
 * Test class for the Tbc_grupo_clienteResource REST controller.
 *
 * @see Tbc_grupo_clienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_grupo_clienteResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    private static final String DEFAULT_RESPONSAVEL = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSAVEL = "BBBBBBBBBB";

    @Inject
    private Tbc_grupo_clienteRepository tbc_grupo_clienteRepository;

    @Inject
    private Tbc_grupo_clienteService tbc_grupo_clienteService;

    @Inject
    private Tbc_grupo_clienteSearchRepository tbc_grupo_clienteSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_grupo_clienteMockMvc;

    private Tbc_grupo_cliente tbc_grupo_cliente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_grupo_clienteResource tbc_grupo_clienteResource = new Tbc_grupo_clienteResource();
        ReflectionTestUtils.setField(tbc_grupo_clienteResource, "tbc_grupo_clienteService", tbc_grupo_clienteService);
        this.restTbc_grupo_clienteMockMvc = MockMvcBuilders.standaloneSetup(tbc_grupo_clienteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_grupo_cliente createEntity(EntityManager em) {
        Tbc_grupo_cliente tbc_grupo_cliente = new Tbc_grupo_cliente()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .removido(DEFAULT_REMOVIDO)
                .responsavel(DEFAULT_RESPONSAVEL);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_grupo_cliente.setTbc_instituicao(tbc_instituicao);
        return tbc_grupo_cliente;
    }

    @Before
    public void initTest() {
        tbc_grupo_clienteSearchRepository.deleteAll();
        tbc_grupo_cliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_grupo_cliente() throws Exception {
        int databaseSizeBeforeCreate = tbc_grupo_clienteRepository.findAll().size();

        // Create the Tbc_grupo_cliente

        restTbc_grupo_clienteMockMvc.perform(post("/api/tbc-grupo-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_grupo_cliente)))
            .andExpect(status().isCreated());

        // Validate the Tbc_grupo_cliente in the database
        List<Tbc_grupo_cliente> tbc_grupo_clienteList = tbc_grupo_clienteRepository.findAll();
        assertThat(tbc_grupo_clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_grupo_cliente testTbc_grupo_cliente = tbc_grupo_clienteList.get(tbc_grupo_clienteList.size() - 1);
        assertThat(testTbc_grupo_cliente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_grupo_cliente.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_grupo_cliente.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);
        assertThat(testTbc_grupo_cliente.getResponsavel()).isEqualTo(DEFAULT_RESPONSAVEL);

        // Validate the Tbc_grupo_cliente in ElasticSearch
        Tbc_grupo_cliente tbc_grupo_clienteEs = tbc_grupo_clienteSearchRepository.findOne(testTbc_grupo_cliente.getId());
        assertThat(tbc_grupo_clienteEs).isEqualToComparingFieldByField(testTbc_grupo_cliente);
    }

    @Test
    @Transactional
    public void createTbc_grupo_clienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_grupo_clienteRepository.findAll().size();

        // Create the Tbc_grupo_cliente with an existing ID
        Tbc_grupo_cliente existingTbc_grupo_cliente = new Tbc_grupo_cliente();
        existingTbc_grupo_cliente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_grupo_clienteMockMvc.perform(post("/api/tbc-grupo-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_grupo_cliente)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_grupo_cliente> tbc_grupo_clienteList = tbc_grupo_clienteRepository.findAll();
        assertThat(tbc_grupo_clienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_grupo_clienteRepository.findAll().size();
        // set the field null
        tbc_grupo_cliente.setNome(null);

        // Create the Tbc_grupo_cliente, which fails.

        restTbc_grupo_clienteMockMvc.perform(post("/api/tbc-grupo-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_grupo_cliente)))
            .andExpect(status().isBadRequest());

        List<Tbc_grupo_cliente> tbc_grupo_clienteList = tbc_grupo_clienteRepository.findAll();
        assertThat(tbc_grupo_clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResponsavelIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_grupo_clienteRepository.findAll().size();
        // set the field null
        tbc_grupo_cliente.setResponsavel(null);

        // Create the Tbc_grupo_cliente, which fails.

        restTbc_grupo_clienteMockMvc.perform(post("/api/tbc-grupo-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_grupo_cliente)))
            .andExpect(status().isBadRequest());

        List<Tbc_grupo_cliente> tbc_grupo_clienteList = tbc_grupo_clienteRepository.findAll();
        assertThat(tbc_grupo_clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_grupo_clientes() throws Exception {
        // Initialize the database
        tbc_grupo_clienteRepository.saveAndFlush(tbc_grupo_cliente);

        // Get all the tbc_grupo_clienteList
        restTbc_grupo_clienteMockMvc.perform(get("/api/tbc-grupo-clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_grupo_cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].responsavel").value(hasItem(DEFAULT_RESPONSAVEL.toString())));
    }

    @Test
    @Transactional
    public void getTbc_grupo_cliente() throws Exception {
        // Initialize the database
        tbc_grupo_clienteRepository.saveAndFlush(tbc_grupo_cliente);

        // Get the tbc_grupo_cliente
        restTbc_grupo_clienteMockMvc.perform(get("/api/tbc-grupo-clientes/{id}", tbc_grupo_cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_grupo_cliente.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()))
            .andExpect(jsonPath("$.responsavel").value(DEFAULT_RESPONSAVEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_grupo_cliente() throws Exception {
        // Get the tbc_grupo_cliente
        restTbc_grupo_clienteMockMvc.perform(get("/api/tbc-grupo-clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_grupo_cliente() throws Exception {
        // Initialize the database
        tbc_grupo_clienteService.save(tbc_grupo_cliente);

        int databaseSizeBeforeUpdate = tbc_grupo_clienteRepository.findAll().size();

        // Update the tbc_grupo_cliente
        Tbc_grupo_cliente updatedTbc_grupo_cliente = tbc_grupo_clienteRepository.findOne(tbc_grupo_cliente.getId());
        updatedTbc_grupo_cliente
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .removido(UPDATED_REMOVIDO)
                .responsavel(UPDATED_RESPONSAVEL);

        restTbc_grupo_clienteMockMvc.perform(put("/api/tbc-grupo-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_grupo_cliente)))
            .andExpect(status().isOk());

        // Validate the Tbc_grupo_cliente in the database
        List<Tbc_grupo_cliente> tbc_grupo_clienteList = tbc_grupo_clienteRepository.findAll();
        assertThat(tbc_grupo_clienteList).hasSize(databaseSizeBeforeUpdate);
        Tbc_grupo_cliente testTbc_grupo_cliente = tbc_grupo_clienteList.get(tbc_grupo_clienteList.size() - 1);
        assertThat(testTbc_grupo_cliente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_grupo_cliente.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_grupo_cliente.isRemovido()).isEqualTo(UPDATED_REMOVIDO);
        assertThat(testTbc_grupo_cliente.getResponsavel()).isEqualTo(UPDATED_RESPONSAVEL);

        // Validate the Tbc_grupo_cliente in ElasticSearch
        Tbc_grupo_cliente tbc_grupo_clienteEs = tbc_grupo_clienteSearchRepository.findOne(testTbc_grupo_cliente.getId());
        assertThat(tbc_grupo_clienteEs).isEqualToComparingFieldByField(testTbc_grupo_cliente);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_grupo_cliente() throws Exception {
        int databaseSizeBeforeUpdate = tbc_grupo_clienteRepository.findAll().size();

        // Create the Tbc_grupo_cliente

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_grupo_clienteMockMvc.perform(put("/api/tbc-grupo-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_grupo_cliente)))
            .andExpect(status().isCreated());

        // Validate the Tbc_grupo_cliente in the database
        List<Tbc_grupo_cliente> tbc_grupo_clienteList = tbc_grupo_clienteRepository.findAll();
        assertThat(tbc_grupo_clienteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_grupo_cliente() throws Exception {
        // Initialize the database
        tbc_grupo_clienteService.save(tbc_grupo_cliente);

        int databaseSizeBeforeDelete = tbc_grupo_clienteRepository.findAll().size();

        // Get the tbc_grupo_cliente
        restTbc_grupo_clienteMockMvc.perform(delete("/api/tbc-grupo-clientes/{id}", tbc_grupo_cliente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_grupo_clienteExistsInEs = tbc_grupo_clienteSearchRepository.exists(tbc_grupo_cliente.getId());
        assertThat(tbc_grupo_clienteExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_grupo_cliente> tbc_grupo_clienteList = tbc_grupo_clienteRepository.findAll();
        assertThat(tbc_grupo_clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_grupo_cliente() throws Exception {
        // Initialize the database
        tbc_grupo_clienteService.save(tbc_grupo_cliente);

        // Search the tbc_grupo_cliente
        restTbc_grupo_clienteMockMvc.perform(get("/api/_search/tbc-grupo-clientes?query=id:" + tbc_grupo_cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_grupo_cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].responsavel").value(hasItem(DEFAULT_RESPONSAVEL.toString())));
    }
}
