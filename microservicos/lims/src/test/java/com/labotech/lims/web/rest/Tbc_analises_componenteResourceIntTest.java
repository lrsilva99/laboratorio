package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_analises_componente;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.domain.Tbc_tipo_campo;
import com.labotech.lims.domain.Tbc_analises;
import com.labotech.lims.repository.Tbc_analises_componenteRepository;
import com.labotech.lims.service.Tbc_analises_componenteService;
import com.labotech.lims.repository.search.Tbc_analises_componenteSearchRepository;

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
 * Test class for the Tbc_analises_componenteResource REST controller.
 *
 * @see Tbc_analises_componenteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_analises_componenteResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_LINHA = 1;
    private static final Integer UPDATED_LINHA = 2;

    private static final String DEFAULT_UNIDADE_MEDIDA = "AAAAAAAAAA";
    private static final String UPDATED_UNIDADE_MEDIDA = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_PADRAO = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_PADRAO = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIGURACAO = "AAAAAAAAAA";
    private static final String UPDATED_CONFIGURACAO = "BBBBBBBBBB";

    @Inject
    private Tbc_analises_componenteRepository tbc_analises_componenteRepository;

    @Inject
    private Tbc_analises_componenteService tbc_analises_componenteService;

    @Inject
    private Tbc_analises_componenteSearchRepository tbc_analises_componenteSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_analises_componenteMockMvc;

    private Tbc_analises_componente tbc_analises_componente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_analises_componenteResource tbc_analises_componenteResource = new Tbc_analises_componenteResource();
        ReflectionTestUtils.setField(tbc_analises_componenteResource, "tbc_analises_componenteService", tbc_analises_componenteService);
        this.restTbc_analises_componenteMockMvc = MockMvcBuilders.standaloneSetup(tbc_analises_componenteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_analises_componente createEntity(EntityManager em) {
        Tbc_analises_componente tbc_analises_componente = new Tbc_analises_componente()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .linha(DEFAULT_LINHA)
                .unidade_medida(DEFAULT_UNIDADE_MEDIDA)
                .valor_padrao(DEFAULT_VALOR_PADRAO)
                .configuracao(DEFAULT_CONFIGURACAO);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_analises_componente.setTbc_instituicao(tbc_instituicao);
        // Add required entity
        Tbc_tipo_campo tbc_tipo_campo = Tbc_tipo_campoResourceIntTest.createEntity(em);
        em.persist(tbc_tipo_campo);
        em.flush();
        tbc_analises_componente.setTbc_tipo_campo(tbc_tipo_campo);
        // Add required entity
        Tbc_analises tbc_analises = Tbc_analisesResourceIntTest.createEntity(em);
        em.persist(tbc_analises);
        em.flush();
        tbc_analises_componente.setTbc_analises(tbc_analises);
        return tbc_analises_componente;
    }

    @Before
    public void initTest() {
        tbc_analises_componenteSearchRepository.deleteAll();
        tbc_analises_componente = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_analises_componente() throws Exception {
        int databaseSizeBeforeCreate = tbc_analises_componenteRepository.findAll().size();

        // Create the Tbc_analises_componente

        restTbc_analises_componenteMockMvc.perform(post("/api/tbc-analises-componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_analises_componente)))
            .andExpect(status().isCreated());

        // Validate the Tbc_analises_componente in the database
        List<Tbc_analises_componente> tbc_analises_componenteList = tbc_analises_componenteRepository.findAll();
        assertThat(tbc_analises_componenteList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_analises_componente testTbc_analises_componente = tbc_analises_componenteList.get(tbc_analises_componenteList.size() - 1);
        assertThat(testTbc_analises_componente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_analises_componente.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_analises_componente.getLinha()).isEqualTo(DEFAULT_LINHA);
        assertThat(testTbc_analises_componente.getUnidade_medida()).isEqualTo(DEFAULT_UNIDADE_MEDIDA);
        assertThat(testTbc_analises_componente.getValor_padrao()).isEqualTo(DEFAULT_VALOR_PADRAO);
        assertThat(testTbc_analises_componente.getConfiguracao()).isEqualTo(DEFAULT_CONFIGURACAO);

        // Validate the Tbc_analises_componente in ElasticSearch
        Tbc_analises_componente tbc_analises_componenteEs = tbc_analises_componenteSearchRepository.findOne(testTbc_analises_componente.getId());
        assertThat(tbc_analises_componenteEs).isEqualToComparingFieldByField(testTbc_analises_componente);
    }

    @Test
    @Transactional
    public void createTbc_analises_componenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_analises_componenteRepository.findAll().size();

        // Create the Tbc_analises_componente with an existing ID
        Tbc_analises_componente existingTbc_analises_componente = new Tbc_analises_componente();
        existingTbc_analises_componente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_analises_componenteMockMvc.perform(post("/api/tbc-analises-componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_analises_componente)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_analises_componente> tbc_analises_componenteList = tbc_analises_componenteRepository.findAll();
        assertThat(tbc_analises_componenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_analises_componenteRepository.findAll().size();
        // set the field null
        tbc_analises_componente.setNome(null);

        // Create the Tbc_analises_componente, which fails.

        restTbc_analises_componenteMockMvc.perform(post("/api/tbc-analises-componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_analises_componente)))
            .andExpect(status().isBadRequest());

        List<Tbc_analises_componente> tbc_analises_componenteList = tbc_analises_componenteRepository.findAll();
        assertThat(tbc_analises_componenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_analises_componentes() throws Exception {
        // Initialize the database
        tbc_analises_componenteRepository.saveAndFlush(tbc_analises_componente);

        // Get all the tbc_analises_componenteList
        restTbc_analises_componenteMockMvc.perform(get("/api/tbc-analises-componentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_analises_componente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].linha").value(hasItem(DEFAULT_LINHA)))
            .andExpect(jsonPath("$.[*].unidade_medida").value(hasItem(DEFAULT_UNIDADE_MEDIDA.toString())))
            .andExpect(jsonPath("$.[*].valor_padrao").value(hasItem(DEFAULT_VALOR_PADRAO.toString())))
            .andExpect(jsonPath("$.[*].configuracao").value(hasItem(DEFAULT_CONFIGURACAO.toString())));
    }

    @Test
    @Transactional
    public void getTbc_analises_componente() throws Exception {
        // Initialize the database
        tbc_analises_componenteRepository.saveAndFlush(tbc_analises_componente);

        // Get the tbc_analises_componente
        restTbc_analises_componenteMockMvc.perform(get("/api/tbc-analises-componentes/{id}", tbc_analises_componente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_analises_componente.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.linha").value(DEFAULT_LINHA))
            .andExpect(jsonPath("$.unidade_medida").value(DEFAULT_UNIDADE_MEDIDA.toString()))
            .andExpect(jsonPath("$.valor_padrao").value(DEFAULT_VALOR_PADRAO.toString()))
            .andExpect(jsonPath("$.configuracao").value(DEFAULT_CONFIGURACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_analises_componente() throws Exception {
        // Get the tbc_analises_componente
        restTbc_analises_componenteMockMvc.perform(get("/api/tbc-analises-componentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_analises_componente() throws Exception {
        // Initialize the database
        tbc_analises_componenteService.save(tbc_analises_componente);

        int databaseSizeBeforeUpdate = tbc_analises_componenteRepository.findAll().size();

        // Update the tbc_analises_componente
        Tbc_analises_componente updatedTbc_analises_componente = tbc_analises_componenteRepository.findOne(tbc_analises_componente.getId());
        updatedTbc_analises_componente
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .linha(UPDATED_LINHA)
                .unidade_medida(UPDATED_UNIDADE_MEDIDA)
                .valor_padrao(UPDATED_VALOR_PADRAO)
                .configuracao(UPDATED_CONFIGURACAO);

        restTbc_analises_componenteMockMvc.perform(put("/api/tbc-analises-componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_analises_componente)))
            .andExpect(status().isOk());

        // Validate the Tbc_analises_componente in the database
        List<Tbc_analises_componente> tbc_analises_componenteList = tbc_analises_componenteRepository.findAll();
        assertThat(tbc_analises_componenteList).hasSize(databaseSizeBeforeUpdate);
        Tbc_analises_componente testTbc_analises_componente = tbc_analises_componenteList.get(tbc_analises_componenteList.size() - 1);
        assertThat(testTbc_analises_componente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_analises_componente.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_analises_componente.getLinha()).isEqualTo(UPDATED_LINHA);
        assertThat(testTbc_analises_componente.getUnidade_medida()).isEqualTo(UPDATED_UNIDADE_MEDIDA);
        assertThat(testTbc_analises_componente.getValor_padrao()).isEqualTo(UPDATED_VALOR_PADRAO);
        assertThat(testTbc_analises_componente.getConfiguracao()).isEqualTo(UPDATED_CONFIGURACAO);

        // Validate the Tbc_analises_componente in ElasticSearch
        Tbc_analises_componente tbc_analises_componenteEs = tbc_analises_componenteSearchRepository.findOne(testTbc_analises_componente.getId());
        assertThat(tbc_analises_componenteEs).isEqualToComparingFieldByField(testTbc_analises_componente);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_analises_componente() throws Exception {
        int databaseSizeBeforeUpdate = tbc_analises_componenteRepository.findAll().size();

        // Create the Tbc_analises_componente

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_analises_componenteMockMvc.perform(put("/api/tbc-analises-componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_analises_componente)))
            .andExpect(status().isCreated());

        // Validate the Tbc_analises_componente in the database
        List<Tbc_analises_componente> tbc_analises_componenteList = tbc_analises_componenteRepository.findAll();
        assertThat(tbc_analises_componenteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_analises_componente() throws Exception {
        // Initialize the database
        tbc_analises_componenteService.save(tbc_analises_componente);

        int databaseSizeBeforeDelete = tbc_analises_componenteRepository.findAll().size();

        // Get the tbc_analises_componente
        restTbc_analises_componenteMockMvc.perform(delete("/api/tbc-analises-componentes/{id}", tbc_analises_componente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_analises_componenteExistsInEs = tbc_analises_componenteSearchRepository.exists(tbc_analises_componente.getId());
        assertThat(tbc_analises_componenteExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_analises_componente> tbc_analises_componenteList = tbc_analises_componenteRepository.findAll();
        assertThat(tbc_analises_componenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_analises_componente() throws Exception {
        // Initialize the database
        tbc_analises_componenteService.save(tbc_analises_componente);

        // Search the tbc_analises_componente
        restTbc_analises_componenteMockMvc.perform(get("/api/_search/tbc-analises-componentes?query=id:" + tbc_analises_componente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_analises_componente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].linha").value(hasItem(DEFAULT_LINHA)))
            .andExpect(jsonPath("$.[*].unidade_medida").value(hasItem(DEFAULT_UNIDADE_MEDIDA.toString())))
            .andExpect(jsonPath("$.[*].valor_padrao").value(hasItem(DEFAULT_VALOR_PADRAO.toString())))
            .andExpect(jsonPath("$.[*].configuracao").value(hasItem(DEFAULT_CONFIGURACAO.toString())));
    }
}
