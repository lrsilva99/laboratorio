package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_analises;
import com.labotech.lims.domain.Tbc_sub_grupo;
import com.labotech.lims.domain.Tbc_grupo_analise;
import com.labotech.lims.domain.Tbc_tipo_cadastro;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.repository.Tbc_analisesRepository;
import com.labotech.lims.service.Tbc_analisesService;
import com.labotech.lims.repository.search.Tbc_analisesSearchRepository;

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
 * Test class for the Tbc_analisesResource REST controller.
 *
 * @see Tbc_analisesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_analisesResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERODIAS = 1;
    private static final Integer UPDATED_NUMERODIAS = 2;

    private static final String DEFAULT_METPOP = "AAAAAAAAAA";
    private static final String UPDATED_METPOP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    private static final Boolean DEFAULT_TERCERIZADO = false;
    private static final Boolean UPDATED_TERCERIZADO = true;

    private static final ZonedDateTime DEFAULT_DIRECTIVA_DATA_ATU = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DIRECTIVA_DATA_ATU = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private Tbc_analisesRepository tbc_analisesRepository;

    @Inject
    private Tbc_analisesService tbc_analisesService;

    @Inject
    private Tbc_analisesSearchRepository tbc_analisesSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_analisesMockMvc;

    private Tbc_analises tbc_analises;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_analisesResource tbc_analisesResource = new Tbc_analisesResource();
        ReflectionTestUtils.setField(tbc_analisesResource, "tbc_analisesService", tbc_analisesService);
        this.restTbc_analisesMockMvc = MockMvcBuilders.standaloneSetup(tbc_analisesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_analises createEntity(EntityManager em) {
        Tbc_analises tbc_analises = new Tbc_analises()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .numerodias(DEFAULT_NUMERODIAS)
                .metpop(DEFAULT_METPOP)
                .removido(DEFAULT_REMOVIDO)
                .tercerizado(DEFAULT_TERCERIZADO)
                .directiva_data_atu(DEFAULT_DIRECTIVA_DATA_ATU);
        // Add required entity
        Tbc_sub_grupo tbc_sub_grupo = Tbc_sub_grupoResourceIntTest.createEntity(em);
        em.persist(tbc_sub_grupo);
        em.flush();
        tbc_analises.setTbc_sub_grupo(tbc_sub_grupo);
        // Add required entity
        Tbc_grupo_analise tbc_grupo_analise = Tbc_grupo_analiseResourceIntTest.createEntity(em);
        em.persist(tbc_grupo_analise);
        em.flush();
        tbc_analises.setTbc_grupo_analise(tbc_grupo_analise);
        // Add required entity
        Tbc_tipo_cadastro tbc_tipo_cadastro = Tbc_tipo_cadastroResourceIntTest.createEntity(em);
        em.persist(tbc_tipo_cadastro);
        em.flush();
        tbc_analises.setTbc_tipo_cadastro(tbc_tipo_cadastro);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_analises.setTbc_instituicao(tbc_instituicao);
        return tbc_analises;
    }

    @Before
    public void initTest() {
        tbc_analisesSearchRepository.deleteAll();
        tbc_analises = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_analises() throws Exception {
        int databaseSizeBeforeCreate = tbc_analisesRepository.findAll().size();

        // Create the Tbc_analises

        restTbc_analisesMockMvc.perform(post("/api/tbc-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_analises)))
            .andExpect(status().isCreated());

        // Validate the Tbc_analises in the database
        List<Tbc_analises> tbc_analisesList = tbc_analisesRepository.findAll();
        assertThat(tbc_analisesList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_analises testTbc_analises = tbc_analisesList.get(tbc_analisesList.size() - 1);
        assertThat(testTbc_analises.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_analises.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_analises.getNumerodias()).isEqualTo(DEFAULT_NUMERODIAS);
        assertThat(testTbc_analises.getMetpop()).isEqualTo(DEFAULT_METPOP);
        assertThat(testTbc_analises.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);
        assertThat(testTbc_analises.isTercerizado()).isEqualTo(DEFAULT_TERCERIZADO);
        assertThat(testTbc_analises.getDirectiva_data_atu()).isEqualTo(DEFAULT_DIRECTIVA_DATA_ATU);

        // Validate the Tbc_analises in ElasticSearch
        Tbc_analises tbc_analisesEs = tbc_analisesSearchRepository.findOne(testTbc_analises.getId());
        assertThat(tbc_analisesEs).isEqualToComparingFieldByField(testTbc_analises);
    }

    @Test
    @Transactional
    public void createTbc_analisesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_analisesRepository.findAll().size();

        // Create the Tbc_analises with an existing ID
        Tbc_analises existingTbc_analises = new Tbc_analises();
        existingTbc_analises.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_analisesMockMvc.perform(post("/api/tbc-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_analises)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_analises> tbc_analisesList = tbc_analisesRepository.findAll();
        assertThat(tbc_analisesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_analisesRepository.findAll().size();
        // set the field null
        tbc_analises.setNome(null);

        // Create the Tbc_analises, which fails.

        restTbc_analisesMockMvc.perform(post("/api/tbc-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_analises)))
            .andExpect(status().isBadRequest());

        List<Tbc_analises> tbc_analisesList = tbc_analisesRepository.findAll();
        assertThat(tbc_analisesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumerodiasIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_analisesRepository.findAll().size();
        // set the field null
        tbc_analises.setNumerodias(null);

        // Create the Tbc_analises, which fails.

        restTbc_analisesMockMvc.perform(post("/api/tbc-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_analises)))
            .andExpect(status().isBadRequest());

        List<Tbc_analises> tbc_analisesList = tbc_analisesRepository.findAll();
        assertThat(tbc_analisesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMetpopIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_analisesRepository.findAll().size();
        // set the field null
        tbc_analises.setMetpop(null);

        // Create the Tbc_analises, which fails.

        restTbc_analisesMockMvc.perform(post("/api/tbc-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_analises)))
            .andExpect(status().isBadRequest());

        List<Tbc_analises> tbc_analisesList = tbc_analisesRepository.findAll();
        assertThat(tbc_analisesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_analises() throws Exception {
        // Initialize the database
        tbc_analisesRepository.saveAndFlush(tbc_analises);

        // Get all the tbc_analisesList
        restTbc_analisesMockMvc.perform(get("/api/tbc-analises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_analises.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].numerodias").value(hasItem(DEFAULT_NUMERODIAS)))
            .andExpect(jsonPath("$.[*].metpop").value(hasItem(DEFAULT_METPOP.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].tercerizado").value(hasItem(DEFAULT_TERCERIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].directiva_data_atu").value(hasItem(sameInstant(DEFAULT_DIRECTIVA_DATA_ATU))));
    }

    @Test
    @Transactional
    public void getTbc_analises() throws Exception {
        // Initialize the database
        tbc_analisesRepository.saveAndFlush(tbc_analises);

        // Get the tbc_analises
        restTbc_analisesMockMvc.perform(get("/api/tbc-analises/{id}", tbc_analises.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_analises.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.numerodias").value(DEFAULT_NUMERODIAS))
            .andExpect(jsonPath("$.metpop").value(DEFAULT_METPOP.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()))
            .andExpect(jsonPath("$.tercerizado").value(DEFAULT_TERCERIZADO.booleanValue()))
            .andExpect(jsonPath("$.directiva_data_atu").value(sameInstant(DEFAULT_DIRECTIVA_DATA_ATU)));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_analises() throws Exception {
        // Get the tbc_analises
        restTbc_analisesMockMvc.perform(get("/api/tbc-analises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_analises() throws Exception {
        // Initialize the database
        tbc_analisesService.save(tbc_analises);

        int databaseSizeBeforeUpdate = tbc_analisesRepository.findAll().size();

        // Update the tbc_analises
        Tbc_analises updatedTbc_analises = tbc_analisesRepository.findOne(tbc_analises.getId());
        updatedTbc_analises
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .numerodias(UPDATED_NUMERODIAS)
                .metpop(UPDATED_METPOP)
                .removido(UPDATED_REMOVIDO)
                .tercerizado(UPDATED_TERCERIZADO)
                .directiva_data_atu(UPDATED_DIRECTIVA_DATA_ATU);

        restTbc_analisesMockMvc.perform(put("/api/tbc-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_analises)))
            .andExpect(status().isOk());

        // Validate the Tbc_analises in the database
        List<Tbc_analises> tbc_analisesList = tbc_analisesRepository.findAll();
        assertThat(tbc_analisesList).hasSize(databaseSizeBeforeUpdate);
        Tbc_analises testTbc_analises = tbc_analisesList.get(tbc_analisesList.size() - 1);
        assertThat(testTbc_analises.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_analises.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_analises.getNumerodias()).isEqualTo(UPDATED_NUMERODIAS);
        assertThat(testTbc_analises.getMetpop()).isEqualTo(UPDATED_METPOP);
        assertThat(testTbc_analises.isRemovido()).isEqualTo(UPDATED_REMOVIDO);
        assertThat(testTbc_analises.isTercerizado()).isEqualTo(UPDATED_TERCERIZADO);
        assertThat(testTbc_analises.getDirectiva_data_atu()).isEqualTo(UPDATED_DIRECTIVA_DATA_ATU);

        // Validate the Tbc_analises in ElasticSearch
        Tbc_analises tbc_analisesEs = tbc_analisesSearchRepository.findOne(testTbc_analises.getId());
        assertThat(tbc_analisesEs).isEqualToComparingFieldByField(testTbc_analises);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_analises() throws Exception {
        int databaseSizeBeforeUpdate = tbc_analisesRepository.findAll().size();

        // Create the Tbc_analises

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_analisesMockMvc.perform(put("/api/tbc-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_analises)))
            .andExpect(status().isCreated());

        // Validate the Tbc_analises in the database
        List<Tbc_analises> tbc_analisesList = tbc_analisesRepository.findAll();
        assertThat(tbc_analisesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_analises() throws Exception {
        // Initialize the database
        tbc_analisesService.save(tbc_analises);

        int databaseSizeBeforeDelete = tbc_analisesRepository.findAll().size();

        // Get the tbc_analises
        restTbc_analisesMockMvc.perform(delete("/api/tbc-analises/{id}", tbc_analises.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_analisesExistsInEs = tbc_analisesSearchRepository.exists(tbc_analises.getId());
        assertThat(tbc_analisesExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_analises> tbc_analisesList = tbc_analisesRepository.findAll();
        assertThat(tbc_analisesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_analises() throws Exception {
        // Initialize the database
        tbc_analisesService.save(tbc_analises);

        // Search the tbc_analises
        restTbc_analisesMockMvc.perform(get("/api/_search/tbc-analises?query=id:" + tbc_analises.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_analises.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].numerodias").value(hasItem(DEFAULT_NUMERODIAS)))
            .andExpect(jsonPath("$.[*].metpop").value(hasItem(DEFAULT_METPOP.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].tercerizado").value(hasItem(DEFAULT_TERCERIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].directiva_data_atu").value(hasItem(sameInstant(DEFAULT_DIRECTIVA_DATA_ATU))));
    }
}
