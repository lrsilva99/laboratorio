package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_formulario;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.domain.Tbc_sub_grupo;
import com.labotech.lims.domain.Tbc_grupo_analise;
import com.labotech.lims.repository.Tbc_formularioRepository;
import com.labotech.lims.service.Tbc_formularioService;
import com.labotech.lims.repository.search.Tbc_formularioSearchRepository;

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
 * Test class for the Tbc_formularioResource REST controller.
 *
 * @see Tbc_formularioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_formularioResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_METODO = "AAAAAAAAAA";
    private static final String UPDATED_METODO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_DIASLIBERACAO = 1;
    private static final Integer UPDATED_DIASLIBERACAO = 2;

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    @Inject
    private Tbc_formularioRepository tbc_formularioRepository;

    @Inject
    private Tbc_formularioService tbc_formularioService;

    @Inject
    private Tbc_formularioSearchRepository tbc_formularioSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_formularioMockMvc;

    private Tbc_formulario tbc_formulario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_formularioResource tbc_formularioResource = new Tbc_formularioResource();
        ReflectionTestUtils.setField(tbc_formularioResource, "tbc_formularioService", tbc_formularioService);
        this.restTbc_formularioMockMvc = MockMvcBuilders.standaloneSetup(tbc_formularioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_formulario createEntity(EntityManager em) {
        Tbc_formulario tbc_formulario = new Tbc_formulario()
                .nome(DEFAULT_NOME)
                .metodo(DEFAULT_METODO)
                .descricao(DEFAULT_DESCRICAO)
                .diasliberacao(DEFAULT_DIASLIBERACAO)
                .removido(DEFAULT_REMOVIDO);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_formulario.setTbc_instituicao(tbc_instituicao);
        // Add required entity
        Tbc_sub_grupo tbc_sub_grupo = Tbc_sub_grupoResourceIntTest.createEntity(em);
        em.persist(tbc_sub_grupo);
        em.flush();
        tbc_formulario.setTbc_sub_grupo(tbc_sub_grupo);
        // Add required entity
        Tbc_grupo_analise tbc_grupo_analise = Tbc_grupo_analiseResourceIntTest.createEntity(em);
        em.persist(tbc_grupo_analise);
        em.flush();
        tbc_formulario.setTbc_grupo_analise(tbc_grupo_analise);
        return tbc_formulario;
    }

    @Before
    public void initTest() {
        tbc_formularioSearchRepository.deleteAll();
        tbc_formulario = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_formulario() throws Exception {
        int databaseSizeBeforeCreate = tbc_formularioRepository.findAll().size();

        // Create the Tbc_formulario

        restTbc_formularioMockMvc.perform(post("/api/tbc-formularios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_formulario)))
            .andExpect(status().isCreated());

        // Validate the Tbc_formulario in the database
        List<Tbc_formulario> tbc_formularioList = tbc_formularioRepository.findAll();
        assertThat(tbc_formularioList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_formulario testTbc_formulario = tbc_formularioList.get(tbc_formularioList.size() - 1);
        assertThat(testTbc_formulario.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_formulario.getMetodo()).isEqualTo(DEFAULT_METODO);
        assertThat(testTbc_formulario.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_formulario.getDiasliberacao()).isEqualTo(DEFAULT_DIASLIBERACAO);
        assertThat(testTbc_formulario.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);

        // Validate the Tbc_formulario in ElasticSearch
        Tbc_formulario tbc_formularioEs = tbc_formularioSearchRepository.findOne(testTbc_formulario.getId());
        assertThat(tbc_formularioEs).isEqualToComparingFieldByField(testTbc_formulario);
    }

    @Test
    @Transactional
    public void createTbc_formularioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_formularioRepository.findAll().size();

        // Create the Tbc_formulario with an existing ID
        Tbc_formulario existingTbc_formulario = new Tbc_formulario();
        existingTbc_formulario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_formularioMockMvc.perform(post("/api/tbc-formularios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_formulario)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_formulario> tbc_formularioList = tbc_formularioRepository.findAll();
        assertThat(tbc_formularioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_formularioRepository.findAll().size();
        // set the field null
        tbc_formulario.setNome(null);

        // Create the Tbc_formulario, which fails.

        restTbc_formularioMockMvc.perform(post("/api/tbc-formularios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_formulario)))
            .andExpect(status().isBadRequest());

        List<Tbc_formulario> tbc_formularioList = tbc_formularioRepository.findAll();
        assertThat(tbc_formularioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMetodoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_formularioRepository.findAll().size();
        // set the field null
        tbc_formulario.setMetodo(null);

        // Create the Tbc_formulario, which fails.

        restTbc_formularioMockMvc.perform(post("/api/tbc-formularios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_formulario)))
            .andExpect(status().isBadRequest());

        List<Tbc_formulario> tbc_formularioList = tbc_formularioRepository.findAll();
        assertThat(tbc_formularioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_formularios() throws Exception {
        // Initialize the database
        tbc_formularioRepository.saveAndFlush(tbc_formulario);

        // Get all the tbc_formularioList
        restTbc_formularioMockMvc.perform(get("/api/tbc-formularios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_formulario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].metodo").value(hasItem(DEFAULT_METODO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].diasliberacao").value(hasItem(DEFAULT_DIASLIBERACAO)))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTbc_formulario() throws Exception {
        // Initialize the database
        tbc_formularioRepository.saveAndFlush(tbc_formulario);

        // Get the tbc_formulario
        restTbc_formularioMockMvc.perform(get("/api/tbc-formularios/{id}", tbc_formulario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_formulario.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.metodo").value(DEFAULT_METODO.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.diasliberacao").value(DEFAULT_DIASLIBERACAO))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_formulario() throws Exception {
        // Get the tbc_formulario
        restTbc_formularioMockMvc.perform(get("/api/tbc-formularios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_formulario() throws Exception {
        // Initialize the database
        tbc_formularioService.save(tbc_formulario);

        int databaseSizeBeforeUpdate = tbc_formularioRepository.findAll().size();

        // Update the tbc_formulario
        Tbc_formulario updatedTbc_formulario = tbc_formularioRepository.findOne(tbc_formulario.getId());
        updatedTbc_formulario
                .nome(UPDATED_NOME)
                .metodo(UPDATED_METODO)
                .descricao(UPDATED_DESCRICAO)
                .diasliberacao(UPDATED_DIASLIBERACAO)
                .removido(UPDATED_REMOVIDO);

        restTbc_formularioMockMvc.perform(put("/api/tbc-formularios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_formulario)))
            .andExpect(status().isOk());

        // Validate the Tbc_formulario in the database
        List<Tbc_formulario> tbc_formularioList = tbc_formularioRepository.findAll();
        assertThat(tbc_formularioList).hasSize(databaseSizeBeforeUpdate);
        Tbc_formulario testTbc_formulario = tbc_formularioList.get(tbc_formularioList.size() - 1);
        assertThat(testTbc_formulario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_formulario.getMetodo()).isEqualTo(UPDATED_METODO);
        assertThat(testTbc_formulario.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_formulario.getDiasliberacao()).isEqualTo(UPDATED_DIASLIBERACAO);
        assertThat(testTbc_formulario.isRemovido()).isEqualTo(UPDATED_REMOVIDO);

        // Validate the Tbc_formulario in ElasticSearch
        Tbc_formulario tbc_formularioEs = tbc_formularioSearchRepository.findOne(testTbc_formulario.getId());
        assertThat(tbc_formularioEs).isEqualToComparingFieldByField(testTbc_formulario);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_formulario() throws Exception {
        int databaseSizeBeforeUpdate = tbc_formularioRepository.findAll().size();

        // Create the Tbc_formulario

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_formularioMockMvc.perform(put("/api/tbc-formularios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_formulario)))
            .andExpect(status().isCreated());

        // Validate the Tbc_formulario in the database
        List<Tbc_formulario> tbc_formularioList = tbc_formularioRepository.findAll();
        assertThat(tbc_formularioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_formulario() throws Exception {
        // Initialize the database
        tbc_formularioService.save(tbc_formulario);

        int databaseSizeBeforeDelete = tbc_formularioRepository.findAll().size();

        // Get the tbc_formulario
        restTbc_formularioMockMvc.perform(delete("/api/tbc-formularios/{id}", tbc_formulario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_formularioExistsInEs = tbc_formularioSearchRepository.exists(tbc_formulario.getId());
        assertThat(tbc_formularioExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_formulario> tbc_formularioList = tbc_formularioRepository.findAll();
        assertThat(tbc_formularioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_formulario() throws Exception {
        // Initialize the database
        tbc_formularioService.save(tbc_formulario);

        // Search the tbc_formulario
        restTbc_formularioMockMvc.perform(get("/api/_search/tbc-formularios?query=id:" + tbc_formulario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_formulario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].metodo").value(hasItem(DEFAULT_METODO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].diasliberacao").value(hasItem(DEFAULT_DIASLIBERACAO)))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }
}
