package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_especie;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.domain.Tbc_tipo_cadastro;
import com.labotech.lims.repository.Tbc_especieRepository;
import com.labotech.lims.service.Tbc_especieService;
import com.labotech.lims.repository.search.Tbc_especieSearchRepository;

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
 * Test class for the Tbc_especieResource REST controller.
 *
 * @see Tbc_especieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_especieResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    @Inject
    private Tbc_especieRepository tbc_especieRepository;

    @Inject
    private Tbc_especieService tbc_especieService;

    @Inject
    private Tbc_especieSearchRepository tbc_especieSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_especieMockMvc;

    private Tbc_especie tbc_especie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_especieResource tbc_especieResource = new Tbc_especieResource();
        ReflectionTestUtils.setField(tbc_especieResource, "tbc_especieService", tbc_especieService);
        this.restTbc_especieMockMvc = MockMvcBuilders.standaloneSetup(tbc_especieResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_especie createEntity(EntityManager em) {
        Tbc_especie tbc_especie = new Tbc_especie()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .removido(DEFAULT_REMOVIDO)
                .codigo(DEFAULT_CODIGO);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_especie.setTbc_instituicao(tbc_instituicao);
        // Add required entity
        Tbc_tipo_cadastro tbc_tipo_cadastro = Tbc_tipo_cadastroResourceIntTest.createEntity(em);
        em.persist(tbc_tipo_cadastro);
        em.flush();
        tbc_especie.setTbc_tipo_cadastro(tbc_tipo_cadastro);
        return tbc_especie;
    }

    @Before
    public void initTest() {
        tbc_especieSearchRepository.deleteAll();
        tbc_especie = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_especie() throws Exception {
        int databaseSizeBeforeCreate = tbc_especieRepository.findAll().size();

        // Create the Tbc_especie

        restTbc_especieMockMvc.perform(post("/api/tbc-especies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_especie)))
            .andExpect(status().isCreated());

        // Validate the Tbc_especie in the database
        List<Tbc_especie> tbc_especieList = tbc_especieRepository.findAll();
        assertThat(tbc_especieList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_especie testTbc_especie = tbc_especieList.get(tbc_especieList.size() - 1);
        assertThat(testTbc_especie.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_especie.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_especie.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);
        assertThat(testTbc_especie.getCodigo()).isEqualTo(DEFAULT_CODIGO);

        // Validate the Tbc_especie in ElasticSearch
        Tbc_especie tbc_especieEs = tbc_especieSearchRepository.findOne(testTbc_especie.getId());
        assertThat(tbc_especieEs).isEqualToComparingFieldByField(testTbc_especie);
    }

    @Test
    @Transactional
    public void createTbc_especieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_especieRepository.findAll().size();

        // Create the Tbc_especie with an existing ID
        Tbc_especie existingTbc_especie = new Tbc_especie();
        existingTbc_especie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_especieMockMvc.perform(post("/api/tbc-especies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_especie)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_especie> tbc_especieList = tbc_especieRepository.findAll();
        assertThat(tbc_especieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_especieRepository.findAll().size();
        // set the field null
        tbc_especie.setNome(null);

        // Create the Tbc_especie, which fails.

        restTbc_especieMockMvc.perform(post("/api/tbc-especies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_especie)))
            .andExpect(status().isBadRequest());

        List<Tbc_especie> tbc_especieList = tbc_especieRepository.findAll();
        assertThat(tbc_especieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_especieRepository.findAll().size();
        // set the field null
        tbc_especie.setCodigo(null);

        // Create the Tbc_especie, which fails.

        restTbc_especieMockMvc.perform(post("/api/tbc-especies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_especie)))
            .andExpect(status().isBadRequest());

        List<Tbc_especie> tbc_especieList = tbc_especieRepository.findAll();
        assertThat(tbc_especieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_especies() throws Exception {
        // Initialize the database
        tbc_especieRepository.saveAndFlush(tbc_especie);

        // Get all the tbc_especieList
        restTbc_especieMockMvc.perform(get("/api/tbc-especies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_especie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())));
    }

    @Test
    @Transactional
    public void getTbc_especie() throws Exception {
        // Initialize the database
        tbc_especieRepository.saveAndFlush(tbc_especie);

        // Get the tbc_especie
        restTbc_especieMockMvc.perform(get("/api/tbc-especies/{id}", tbc_especie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_especie.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_especie() throws Exception {
        // Get the tbc_especie
        restTbc_especieMockMvc.perform(get("/api/tbc-especies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_especie() throws Exception {
        // Initialize the database
        tbc_especieService.save(tbc_especie);

        int databaseSizeBeforeUpdate = tbc_especieRepository.findAll().size();

        // Update the tbc_especie
        Tbc_especie updatedTbc_especie = tbc_especieRepository.findOne(tbc_especie.getId());
        updatedTbc_especie
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .removido(UPDATED_REMOVIDO)
                .codigo(UPDATED_CODIGO);

        restTbc_especieMockMvc.perform(put("/api/tbc-especies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_especie)))
            .andExpect(status().isOk());

        // Validate the Tbc_especie in the database
        List<Tbc_especie> tbc_especieList = tbc_especieRepository.findAll();
        assertThat(tbc_especieList).hasSize(databaseSizeBeforeUpdate);
        Tbc_especie testTbc_especie = tbc_especieList.get(tbc_especieList.size() - 1);
        assertThat(testTbc_especie.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_especie.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_especie.isRemovido()).isEqualTo(UPDATED_REMOVIDO);
        assertThat(testTbc_especie.getCodigo()).isEqualTo(UPDATED_CODIGO);

        // Validate the Tbc_especie in ElasticSearch
        Tbc_especie tbc_especieEs = tbc_especieSearchRepository.findOne(testTbc_especie.getId());
        assertThat(tbc_especieEs).isEqualToComparingFieldByField(testTbc_especie);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_especie() throws Exception {
        int databaseSizeBeforeUpdate = tbc_especieRepository.findAll().size();

        // Create the Tbc_especie

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_especieMockMvc.perform(put("/api/tbc-especies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_especie)))
            .andExpect(status().isCreated());

        // Validate the Tbc_especie in the database
        List<Tbc_especie> tbc_especieList = tbc_especieRepository.findAll();
        assertThat(tbc_especieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_especie() throws Exception {
        // Initialize the database
        tbc_especieService.save(tbc_especie);

        int databaseSizeBeforeDelete = tbc_especieRepository.findAll().size();

        // Get the tbc_especie
        restTbc_especieMockMvc.perform(delete("/api/tbc-especies/{id}", tbc_especie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_especieExistsInEs = tbc_especieSearchRepository.exists(tbc_especie.getId());
        assertThat(tbc_especieExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_especie> tbc_especieList = tbc_especieRepository.findAll();
        assertThat(tbc_especieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_especie() throws Exception {
        // Initialize the database
        tbc_especieService.save(tbc_especie);

        // Search the tbc_especie
        restTbc_especieMockMvc.perform(get("/api/_search/tbc-especies?query=id:" + tbc_especie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_especie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())));
    }
}
