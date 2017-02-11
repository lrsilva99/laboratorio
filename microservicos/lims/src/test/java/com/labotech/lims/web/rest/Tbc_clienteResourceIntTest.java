package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_cliente;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.domain.Tbc_grupo_cliente;
import com.labotech.lims.repository.Tbc_clienteRepository;
import com.labotech.lims.service.Tbc_clienteService;
import com.labotech.lims.repository.search.Tbc_clienteSearchRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.labotech.lims.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Tbc_clienteResource REST controller.
 *
 * @see Tbc_clienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_clienteResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IMPRIMIR = false;
    private static final Boolean UPDATED_IMPRIMIR = true;

    private static final Boolean DEFAULT_ENVIAR_EMAIL = false;
    private static final Boolean UPDATED_ENVIAR_EMAIL = true;

    private static final ZonedDateTime DEFAULT_DIRECTIVA_DATA_ATU = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DIRECTIVA_DATA_ATU = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CPF_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CPF_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_CLIENTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BLOQUEAR = false;
    private static final Boolean UPDATED_BLOQUEAR = true;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Inject
    private Tbc_clienteRepository tbc_clienteRepository;

    @Inject
    private Tbc_clienteService tbc_clienteService;

    @Inject
    private Tbc_clienteSearchRepository tbc_clienteSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_clienteMockMvc;

    private Tbc_cliente tbc_cliente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_clienteResource tbc_clienteResource = new Tbc_clienteResource();
        ReflectionTestUtils.setField(tbc_clienteResource, "tbc_clienteService", tbc_clienteService);
        this.restTbc_clienteMockMvc = MockMvcBuilders.standaloneSetup(tbc_clienteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_cliente createEntity(EntityManager em) {
        Tbc_cliente tbc_cliente = new Tbc_cliente()
                .nome(DEFAULT_NOME)
                .cep(DEFAULT_CEP)
                .endereco(DEFAULT_ENDERECO)
                .email(DEFAULT_EMAIL)
                .imprimir(DEFAULT_IMPRIMIR)
                .enviar_email(DEFAULT_ENVIAR_EMAIL)
                .directiva_data_atu(DEFAULT_DIRECTIVA_DATA_ATU)
                .cpf_cnpj(DEFAULT_CPF_CNPJ)
                .codigo_cliente(DEFAULT_CODIGO_CLIENTE)
                .removido(DEFAULT_REMOVIDO)
                .bairro(DEFAULT_BAIRRO)
                .bloquear(DEFAULT_BLOQUEAR)
                .descricao(DEFAULT_DESCRICAO);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_cliente.setTbc_instituicao(tbc_instituicao);
        // Add required entity
        Tbc_grupo_cliente tbc_grupo_cliente = Tbc_grupo_clienteResourceIntTest.createEntity(em);
        em.persist(tbc_grupo_cliente);
        em.flush();
        tbc_cliente.setTbc_grupo_cliente(tbc_grupo_cliente);
        return tbc_cliente;
    }

    @Before
    public void initTest() {
        tbc_clienteSearchRepository.deleteAll();
        tbc_cliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_cliente() throws Exception {
        int databaseSizeBeforeCreate = tbc_clienteRepository.findAll().size();

        // Create the Tbc_cliente

        restTbc_clienteMockMvc.perform(post("/api/tbc-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_cliente)))
            .andExpect(status().isCreated());

        // Validate the Tbc_cliente in the database
        List<Tbc_cliente> tbc_clienteList = tbc_clienteRepository.findAll();
        assertThat(tbc_clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_cliente testTbc_cliente = tbc_clienteList.get(tbc_clienteList.size() - 1);
        assertThat(testTbc_cliente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_cliente.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testTbc_cliente.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testTbc_cliente.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTbc_cliente.isImprimir()).isEqualTo(DEFAULT_IMPRIMIR);
        assertThat(testTbc_cliente.isEnviar_email()).isEqualTo(DEFAULT_ENVIAR_EMAIL);
        assertThat(testTbc_cliente.getDirectiva_data_atu()).isEqualTo(DEFAULT_DIRECTIVA_DATA_ATU);
        assertThat(testTbc_cliente.getCpf_cnpj()).isEqualTo(DEFAULT_CPF_CNPJ);
        assertThat(testTbc_cliente.getCodigo_cliente()).isEqualTo(DEFAULT_CODIGO_CLIENTE);
        assertThat(testTbc_cliente.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);
        assertThat(testTbc_cliente.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testTbc_cliente.isBloquear()).isEqualTo(DEFAULT_BLOQUEAR);
        assertThat(testTbc_cliente.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the Tbc_cliente in ElasticSearch
        Tbc_cliente tbc_clienteEs = tbc_clienteSearchRepository.findOne(testTbc_cliente.getId());
        assertThat(tbc_clienteEs).isEqualToComparingFieldByField(testTbc_cliente);
    }

    @Test
    @Transactional
    public void createTbc_clienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_clienteRepository.findAll().size();

        // Create the Tbc_cliente with an existing ID
        Tbc_cliente existingTbc_cliente = new Tbc_cliente();
        existingTbc_cliente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_clienteMockMvc.perform(post("/api/tbc-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_cliente)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_cliente> tbc_clienteList = tbc_clienteRepository.findAll();
        assertThat(tbc_clienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_clienteRepository.findAll().size();
        // set the field null
        tbc_cliente.setNome(null);

        // Create the Tbc_cliente, which fails.

        restTbc_clienteMockMvc.perform(post("/api/tbc-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_cliente)))
            .andExpect(status().isBadRequest());

        List<Tbc_cliente> tbc_clienteList = tbc_clienteRepository.findAll();
        assertThat(tbc_clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCepIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_clienteRepository.findAll().size();
        // set the field null
        tbc_cliente.setCep(null);

        // Create the Tbc_cliente, which fails.

        restTbc_clienteMockMvc.perform(post("/api/tbc-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_cliente)))
            .andExpect(status().isBadRequest());

        List<Tbc_cliente> tbc_clienteList = tbc_clienteRepository.findAll();
        assertThat(tbc_clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_clienteRepository.findAll().size();
        // set the field null
        tbc_cliente.setEndereco(null);

        // Create the Tbc_cliente, which fails.

        restTbc_clienteMockMvc.perform(post("/api/tbc-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_cliente)))
            .andExpect(status().isBadRequest());

        List<Tbc_cliente> tbc_clienteList = tbc_clienteRepository.findAll();
        assertThat(tbc_clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCpf_cnpjIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_clienteRepository.findAll().size();
        // set the field null
        tbc_cliente.setCpf_cnpj(null);

        // Create the Tbc_cliente, which fails.

        restTbc_clienteMockMvc.perform(post("/api/tbc-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_cliente)))
            .andExpect(status().isBadRequest());

        List<Tbc_cliente> tbc_clienteList = tbc_clienteRepository.findAll();
        assertThat(tbc_clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigo_clienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_clienteRepository.findAll().size();
        // set the field null
        tbc_cliente.setCodigo_cliente(null);

        // Create the Tbc_cliente, which fails.

        restTbc_clienteMockMvc.perform(post("/api/tbc-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_cliente)))
            .andExpect(status().isBadRequest());

        List<Tbc_cliente> tbc_clienteList = tbc_clienteRepository.findAll();
        assertThat(tbc_clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_clientes() throws Exception {
        // Initialize the database
        tbc_clienteRepository.saveAndFlush(tbc_cliente);

        // Get all the tbc_clienteList
        restTbc_clienteMockMvc.perform(get("/api/tbc-clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP.toString())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].imprimir").value(hasItem(DEFAULT_IMPRIMIR.booleanValue())))
            .andExpect(jsonPath("$.[*].enviar_email").value(hasItem(DEFAULT_ENVIAR_EMAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].directiva_data_atu").value(hasItem(sameInstant(DEFAULT_DIRECTIVA_DATA_ATU))))
            .andExpect(jsonPath("$.[*].cpf_cnpj").value(hasItem(DEFAULT_CPF_CNPJ.toString())))
            .andExpect(jsonPath("$.[*].codigo_cliente").value(hasItem(DEFAULT_CODIGO_CLIENTE.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO.toString())))
            .andExpect(jsonPath("$.[*].bloquear").value(hasItem(DEFAULT_BLOQUEAR.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getTbc_cliente() throws Exception {
        // Initialize the database
        tbc_clienteRepository.saveAndFlush(tbc_cliente);

        // Get the tbc_cliente
        restTbc_clienteMockMvc.perform(get("/api/tbc-clientes/{id}", tbc_cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_cliente.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP.toString()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.imprimir").value(DEFAULT_IMPRIMIR.booleanValue()))
            .andExpect(jsonPath("$.enviar_email").value(DEFAULT_ENVIAR_EMAIL.booleanValue()))
            .andExpect(jsonPath("$.directiva_data_atu").value(sameInstant(DEFAULT_DIRECTIVA_DATA_ATU)))
            .andExpect(jsonPath("$.cpf_cnpj").value(DEFAULT_CPF_CNPJ.toString()))
            .andExpect(jsonPath("$.codigo_cliente").value(DEFAULT_CODIGO_CLIENTE.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO.toString()))
            .andExpect(jsonPath("$.bloquear").value(DEFAULT_BLOQUEAR.booleanValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_cliente() throws Exception {
        // Get the tbc_cliente
        restTbc_clienteMockMvc.perform(get("/api/tbc-clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_cliente() throws Exception {
        // Initialize the database
        tbc_clienteService.save(tbc_cliente);

        int databaseSizeBeforeUpdate = tbc_clienteRepository.findAll().size();

        // Update the tbc_cliente
        Tbc_cliente updatedTbc_cliente = tbc_clienteRepository.findOne(tbc_cliente.getId());
        updatedTbc_cliente
                .nome(UPDATED_NOME)
                .cep(UPDATED_CEP)
                .endereco(UPDATED_ENDERECO)
                .email(UPDATED_EMAIL)
                .imprimir(UPDATED_IMPRIMIR)
                .enviar_email(UPDATED_ENVIAR_EMAIL)
                .directiva_data_atu(UPDATED_DIRECTIVA_DATA_ATU)
                .cpf_cnpj(UPDATED_CPF_CNPJ)
                .codigo_cliente(UPDATED_CODIGO_CLIENTE)
                .removido(UPDATED_REMOVIDO)
                .bairro(UPDATED_BAIRRO)
                .bloquear(UPDATED_BLOQUEAR)
                .descricao(UPDATED_DESCRICAO);

        restTbc_clienteMockMvc.perform(put("/api/tbc-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_cliente)))
            .andExpect(status().isOk());

        // Validate the Tbc_cliente in the database
        List<Tbc_cliente> tbc_clienteList = tbc_clienteRepository.findAll();
        assertThat(tbc_clienteList).hasSize(databaseSizeBeforeUpdate);
        Tbc_cliente testTbc_cliente = tbc_clienteList.get(tbc_clienteList.size() - 1);
        assertThat(testTbc_cliente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_cliente.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testTbc_cliente.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testTbc_cliente.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTbc_cliente.isImprimir()).isEqualTo(UPDATED_IMPRIMIR);
        assertThat(testTbc_cliente.isEnviar_email()).isEqualTo(UPDATED_ENVIAR_EMAIL);
        assertThat(testTbc_cliente.getDirectiva_data_atu()).isEqualTo(UPDATED_DIRECTIVA_DATA_ATU);
        assertThat(testTbc_cliente.getCpf_cnpj()).isEqualTo(UPDATED_CPF_CNPJ);
        assertThat(testTbc_cliente.getCodigo_cliente()).isEqualTo(UPDATED_CODIGO_CLIENTE);
        assertThat(testTbc_cliente.isRemovido()).isEqualTo(UPDATED_REMOVIDO);
        assertThat(testTbc_cliente.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testTbc_cliente.isBloquear()).isEqualTo(UPDATED_BLOQUEAR);
        assertThat(testTbc_cliente.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the Tbc_cliente in ElasticSearch
        Tbc_cliente tbc_clienteEs = tbc_clienteSearchRepository.findOne(testTbc_cliente.getId());
        assertThat(tbc_clienteEs).isEqualToComparingFieldByField(testTbc_cliente);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_cliente() throws Exception {
        int databaseSizeBeforeUpdate = tbc_clienteRepository.findAll().size();

        // Create the Tbc_cliente

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_clienteMockMvc.perform(put("/api/tbc-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_cliente)))
            .andExpect(status().isCreated());

        // Validate the Tbc_cliente in the database
        List<Tbc_cliente> tbc_clienteList = tbc_clienteRepository.findAll();
        assertThat(tbc_clienteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_cliente() throws Exception {
        // Initialize the database
        tbc_clienteService.save(tbc_cliente);

        int databaseSizeBeforeDelete = tbc_clienteRepository.findAll().size();

        // Get the tbc_cliente
        restTbc_clienteMockMvc.perform(delete("/api/tbc-clientes/{id}", tbc_cliente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_clienteExistsInEs = tbc_clienteSearchRepository.exists(tbc_cliente.getId());
        assertThat(tbc_clienteExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_cliente> tbc_clienteList = tbc_clienteRepository.findAll();
        assertThat(tbc_clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_cliente() throws Exception {
        // Initialize the database
        tbc_clienteService.save(tbc_cliente);

        // Search the tbc_cliente
        restTbc_clienteMockMvc.perform(get("/api/_search/tbc-clientes?query=id:" + tbc_cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP.toString())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].imprimir").value(hasItem(DEFAULT_IMPRIMIR.booleanValue())))
            .andExpect(jsonPath("$.[*].enviar_email").value(hasItem(DEFAULT_ENVIAR_EMAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].directiva_data_atu").value(hasItem(sameInstant(DEFAULT_DIRECTIVA_DATA_ATU))))
            .andExpect(jsonPath("$.[*].cpf_cnpj").value(hasItem(DEFAULT_CPF_CNPJ.toString())))
            .andExpect(jsonPath("$.[*].codigo_cliente").value(hasItem(DEFAULT_CODIGO_CLIENTE.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO.toString())))
            .andExpect(jsonPath("$.[*].bloquear").value(hasItem(DEFAULT_BLOQUEAR.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
}
