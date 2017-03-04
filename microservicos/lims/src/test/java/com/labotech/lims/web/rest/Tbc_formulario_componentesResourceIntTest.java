package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_formulario_componentes;
import com.labotech.lims.domain.Tbc_formulario;
import com.labotech.lims.domain.Tbc_tipo_campo;
import com.labotech.lims.repository.Tbc_formulario_componentesRepository;
import com.labotech.lims.service.Tbc_formulario_componentesService;
import com.labotech.lims.repository.search.Tbc_formulario_componentesSearchRepository;

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
 * Test class for the Tbc_formulario_componentesResource REST controller.
 *
 * @see Tbc_formulario_componentesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_formulario_componentesResourceIntTest {

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
    private Tbc_formulario_componentesRepository tbc_formulario_componentesRepository;

    @Inject
    private Tbc_formulario_componentesService tbc_formulario_componentesService;

    @Inject
    private Tbc_formulario_componentesSearchRepository tbc_formulario_componentesSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_formulario_componentesMockMvc;

    private Tbc_formulario_componentes tbc_formulario_componentes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_formulario_componentesResource tbc_formulario_componentesResource = new Tbc_formulario_componentesResource();
        ReflectionTestUtils.setField(tbc_formulario_componentesResource, "tbc_formulario_componentesService", tbc_formulario_componentesService);
        this.restTbc_formulario_componentesMockMvc = MockMvcBuilders.standaloneSetup(tbc_formulario_componentesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_formulario_componentes createEntity(EntityManager em) {
        Tbc_formulario_componentes tbc_formulario_componentes = new Tbc_formulario_componentes()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .linha(DEFAULT_LINHA)
                .unidade_medida(DEFAULT_UNIDADE_MEDIDA)
                .valor_padrao(DEFAULT_VALOR_PADRAO)
                .configuracao(DEFAULT_CONFIGURACAO);
        // Add required entity
        Tbc_formulario tbc_formulario = Tbc_formularioResourceIntTest.createEntity(em);
        em.persist(tbc_formulario);
        em.flush();
        tbc_formulario_componentes.setTbc_formulario(tbc_formulario);
        // Add required entity
        Tbc_tipo_campo tbc_tipo_campo = Tbc_tipo_campoResourceIntTest.createEntity(em);
        em.persist(tbc_tipo_campo);
        em.flush();
        tbc_formulario_componentes.setTbc_tipo_campo(tbc_tipo_campo);
        return tbc_formulario_componentes;
    }

    @Before
    public void initTest() {
        tbc_formulario_componentesSearchRepository.deleteAll();
        tbc_formulario_componentes = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_formulario_componentes() throws Exception {
        int databaseSizeBeforeCreate = tbc_formulario_componentesRepository.findAll().size();

        // Create the Tbc_formulario_componentes

        restTbc_formulario_componentesMockMvc.perform(post("/api/tbc-formulario-componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_formulario_componentes)))
            .andExpect(status().isCreated());

        // Validate the Tbc_formulario_componentes in the database
        List<Tbc_formulario_componentes> tbc_formulario_componentesList = tbc_formulario_componentesRepository.findAll();
        assertThat(tbc_formulario_componentesList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_formulario_componentes testTbc_formulario_componentes = tbc_formulario_componentesList.get(tbc_formulario_componentesList.size() - 1);
        assertThat(testTbc_formulario_componentes.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_formulario_componentes.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_formulario_componentes.getLinha()).isEqualTo(DEFAULT_LINHA);
        assertThat(testTbc_formulario_componentes.getUnidade_medida()).isEqualTo(DEFAULT_UNIDADE_MEDIDA);
        assertThat(testTbc_formulario_componentes.getValor_padrao()).isEqualTo(DEFAULT_VALOR_PADRAO);
        assertThat(testTbc_formulario_componentes.getConfiguracao()).isEqualTo(DEFAULT_CONFIGURACAO);

        // Validate the Tbc_formulario_componentes in ElasticSearch
        Tbc_formulario_componentes tbc_formulario_componentesEs = tbc_formulario_componentesSearchRepository.findOne(testTbc_formulario_componentes.getId());
        assertThat(tbc_formulario_componentesEs).isEqualToComparingFieldByField(testTbc_formulario_componentes);
    }

    @Test
    @Transactional
    public void createTbc_formulario_componentesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_formulario_componentesRepository.findAll().size();

        // Create the Tbc_formulario_componentes with an existing ID
        Tbc_formulario_componentes existingTbc_formulario_componentes = new Tbc_formulario_componentes();
        existingTbc_formulario_componentes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_formulario_componentesMockMvc.perform(post("/api/tbc-formulario-componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_formulario_componentes)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_formulario_componentes> tbc_formulario_componentesList = tbc_formulario_componentesRepository.findAll();
        assertThat(tbc_formulario_componentesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_formulario_componentesRepository.findAll().size();
        // set the field null
        tbc_formulario_componentes.setNome(null);

        // Create the Tbc_formulario_componentes, which fails.

        restTbc_formulario_componentesMockMvc.perform(post("/api/tbc-formulario-componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_formulario_componentes)))
            .andExpect(status().isBadRequest());

        List<Tbc_formulario_componentes> tbc_formulario_componentesList = tbc_formulario_componentesRepository.findAll();
        assertThat(tbc_formulario_componentesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLinhaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_formulario_componentesRepository.findAll().size();
        // set the field null
        tbc_formulario_componentes.setLinha(null);

        // Create the Tbc_formulario_componentes, which fails.

        restTbc_formulario_componentesMockMvc.perform(post("/api/tbc-formulario-componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_formulario_componentes)))
            .andExpect(status().isBadRequest());

        List<Tbc_formulario_componentes> tbc_formulario_componentesList = tbc_formulario_componentesRepository.findAll();
        assertThat(tbc_formulario_componentesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnidade_medidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_formulario_componentesRepository.findAll().size();
        // set the field null
        tbc_formulario_componentes.setUnidade_medida(null);

        // Create the Tbc_formulario_componentes, which fails.

        restTbc_formulario_componentesMockMvc.perform(post("/api/tbc-formulario-componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_formulario_componentes)))
            .andExpect(status().isBadRequest());

        List<Tbc_formulario_componentes> tbc_formulario_componentesList = tbc_formulario_componentesRepository.findAll();
        assertThat(tbc_formulario_componentesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValor_padraoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_formulario_componentesRepository.findAll().size();
        // set the field null
        tbc_formulario_componentes.setValor_padrao(null);

        // Create the Tbc_formulario_componentes, which fails.

        restTbc_formulario_componentesMockMvc.perform(post("/api/tbc-formulario-componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_formulario_componentes)))
            .andExpect(status().isBadRequest());

        List<Tbc_formulario_componentes> tbc_formulario_componentesList = tbc_formulario_componentesRepository.findAll();
        assertThat(tbc_formulario_componentesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConfiguracaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_formulario_componentesRepository.findAll().size();
        // set the field null
        tbc_formulario_componentes.setConfiguracao(null);

        // Create the Tbc_formulario_componentes, which fails.

        restTbc_formulario_componentesMockMvc.perform(post("/api/tbc-formulario-componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_formulario_componentes)))
            .andExpect(status().isBadRequest());

        List<Tbc_formulario_componentes> tbc_formulario_componentesList = tbc_formulario_componentesRepository.findAll();
        assertThat(tbc_formulario_componentesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_formulario_componentes() throws Exception {
        // Initialize the database
        tbc_formulario_componentesRepository.saveAndFlush(tbc_formulario_componentes);

        // Get all the tbc_formulario_componentesList
        restTbc_formulario_componentesMockMvc.perform(get("/api/tbc-formulario-componentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_formulario_componentes.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].linha").value(hasItem(DEFAULT_LINHA)))
            .andExpect(jsonPath("$.[*].unidade_medida").value(hasItem(DEFAULT_UNIDADE_MEDIDA.toString())))
            .andExpect(jsonPath("$.[*].valor_padrao").value(hasItem(DEFAULT_VALOR_PADRAO.toString())))
            .andExpect(jsonPath("$.[*].configuracao").value(hasItem(DEFAULT_CONFIGURACAO.toString())));
    }

    @Test
    @Transactional
    public void getTbc_formulario_componentes() throws Exception {
        // Initialize the database
        tbc_formulario_componentesRepository.saveAndFlush(tbc_formulario_componentes);

        // Get the tbc_formulario_componentes
        restTbc_formulario_componentesMockMvc.perform(get("/api/tbc-formulario-componentes/{id}", tbc_formulario_componentes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_formulario_componentes.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.linha").value(DEFAULT_LINHA))
            .andExpect(jsonPath("$.unidade_medida").value(DEFAULT_UNIDADE_MEDIDA.toString()))
            .andExpect(jsonPath("$.valor_padrao").value(DEFAULT_VALOR_PADRAO.toString()))
            .andExpect(jsonPath("$.configuracao").value(DEFAULT_CONFIGURACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_formulario_componentes() throws Exception {
        // Get the tbc_formulario_componentes
        restTbc_formulario_componentesMockMvc.perform(get("/api/tbc-formulario-componentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_formulario_componentes() throws Exception {
        // Initialize the database
        tbc_formulario_componentesService.save(tbc_formulario_componentes);

        int databaseSizeBeforeUpdate = tbc_formulario_componentesRepository.findAll().size();

        // Update the tbc_formulario_componentes
        Tbc_formulario_componentes updatedTbc_formulario_componentes = tbc_formulario_componentesRepository.findOne(tbc_formulario_componentes.getId());
        updatedTbc_formulario_componentes
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .linha(UPDATED_LINHA)
                .unidade_medida(UPDATED_UNIDADE_MEDIDA)
                .valor_padrao(UPDATED_VALOR_PADRAO)
                .configuracao(UPDATED_CONFIGURACAO);

        restTbc_formulario_componentesMockMvc.perform(put("/api/tbc-formulario-componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_formulario_componentes)))
            .andExpect(status().isOk());

        // Validate the Tbc_formulario_componentes in the database
        List<Tbc_formulario_componentes> tbc_formulario_componentesList = tbc_formulario_componentesRepository.findAll();
        assertThat(tbc_formulario_componentesList).hasSize(databaseSizeBeforeUpdate);
        Tbc_formulario_componentes testTbc_formulario_componentes = tbc_formulario_componentesList.get(tbc_formulario_componentesList.size() - 1);
        assertThat(testTbc_formulario_componentes.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_formulario_componentes.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_formulario_componentes.getLinha()).isEqualTo(UPDATED_LINHA);
        assertThat(testTbc_formulario_componentes.getUnidade_medida()).isEqualTo(UPDATED_UNIDADE_MEDIDA);
        assertThat(testTbc_formulario_componentes.getValor_padrao()).isEqualTo(UPDATED_VALOR_PADRAO);
        assertThat(testTbc_formulario_componentes.getConfiguracao()).isEqualTo(UPDATED_CONFIGURACAO);

        // Validate the Tbc_formulario_componentes in ElasticSearch
        Tbc_formulario_componentes tbc_formulario_componentesEs = tbc_formulario_componentesSearchRepository.findOne(testTbc_formulario_componentes.getId());
        assertThat(tbc_formulario_componentesEs).isEqualToComparingFieldByField(testTbc_formulario_componentes);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_formulario_componentes() throws Exception {
        int databaseSizeBeforeUpdate = tbc_formulario_componentesRepository.findAll().size();

        // Create the Tbc_formulario_componentes

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_formulario_componentesMockMvc.perform(put("/api/tbc-formulario-componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_formulario_componentes)))
            .andExpect(status().isCreated());

        // Validate the Tbc_formulario_componentes in the database
        List<Tbc_formulario_componentes> tbc_formulario_componentesList = tbc_formulario_componentesRepository.findAll();
        assertThat(tbc_formulario_componentesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_formulario_componentes() throws Exception {
        // Initialize the database
        tbc_formulario_componentesService.save(tbc_formulario_componentes);

        int databaseSizeBeforeDelete = tbc_formulario_componentesRepository.findAll().size();

        // Get the tbc_formulario_componentes
        restTbc_formulario_componentesMockMvc.perform(delete("/api/tbc-formulario-componentes/{id}", tbc_formulario_componentes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_formulario_componentesExistsInEs = tbc_formulario_componentesSearchRepository.exists(tbc_formulario_componentes.getId());
        assertThat(tbc_formulario_componentesExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_formulario_componentes> tbc_formulario_componentesList = tbc_formulario_componentesRepository.findAll();
        assertThat(tbc_formulario_componentesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_formulario_componentes() throws Exception {
        // Initialize the database
        tbc_formulario_componentesService.save(tbc_formulario_componentes);

        // Search the tbc_formulario_componentes
        restTbc_formulario_componentesMockMvc.perform(get("/api/_search/tbc-formulario-componentes?query=id:" + tbc_formulario_componentes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_formulario_componentes.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].linha").value(hasItem(DEFAULT_LINHA)))
            .andExpect(jsonPath("$.[*].unidade_medida").value(hasItem(DEFAULT_UNIDADE_MEDIDA.toString())))
            .andExpect(jsonPath("$.[*].valor_padrao").value(hasItem(DEFAULT_VALOR_PADRAO.toString())))
            .andExpect(jsonPath("$.[*].configuracao").value(hasItem(DEFAULT_CONFIGURACAO.toString())));
    }
}
