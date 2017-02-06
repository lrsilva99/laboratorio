package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_sub_grupo;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.repository.Tbc_sub_grupoRepository;
import com.labotech.lims.service.Tbc_sub_grupoService;
import com.labotech.lims.repository.search.Tbc_sub_grupoSearchRepository;

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
 * Test class for the Tbc_sub_grupoResource REST controller.
 *
 * @see Tbc_sub_grupoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_sub_grupoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    @Inject
    private Tbc_sub_grupoRepository tbc_sub_grupoRepository;

    @Inject
    private Tbc_sub_grupoService tbc_sub_grupoService;

    @Inject
    private Tbc_sub_grupoSearchRepository tbc_sub_grupoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_sub_grupoMockMvc;

    private Tbc_sub_grupo tbc_sub_grupo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_sub_grupoResource tbc_sub_grupoResource = new Tbc_sub_grupoResource();
        ReflectionTestUtils.setField(tbc_sub_grupoResource, "tbc_sub_grupoService", tbc_sub_grupoService);
        this.restTbc_sub_grupoMockMvc = MockMvcBuilders.standaloneSetup(tbc_sub_grupoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_sub_grupo createEntity(EntityManager em) {
        Tbc_sub_grupo tbc_sub_grupo = new Tbc_sub_grupo()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .sigla(DEFAULT_SIGLA)
                .removido(DEFAULT_REMOVIDO);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_sub_grupo.setTbc_instituicao(tbc_instituicao);
        return tbc_sub_grupo;
    }

    @Before
    public void initTest() {
        tbc_sub_grupoSearchRepository.deleteAll();
        tbc_sub_grupo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_sub_grupo() throws Exception {
        int databaseSizeBeforeCreate = tbc_sub_grupoRepository.findAll().size();

        // Create the Tbc_sub_grupo

        restTbc_sub_grupoMockMvc.perform(post("/api/tbc-sub-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_sub_grupo)))
            .andExpect(status().isCreated());

        // Validate the Tbc_sub_grupo in the database
        List<Tbc_sub_grupo> tbc_sub_grupoList = tbc_sub_grupoRepository.findAll();
        assertThat(tbc_sub_grupoList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_sub_grupo testTbc_sub_grupo = tbc_sub_grupoList.get(tbc_sub_grupoList.size() - 1);
        assertThat(testTbc_sub_grupo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_sub_grupo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_sub_grupo.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testTbc_sub_grupo.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);

        // Validate the Tbc_sub_grupo in ElasticSearch
        Tbc_sub_grupo tbc_sub_grupoEs = tbc_sub_grupoSearchRepository.findOne(testTbc_sub_grupo.getId());
        assertThat(tbc_sub_grupoEs).isEqualToComparingFieldByField(testTbc_sub_grupo);
    }

    @Test
    @Transactional
    public void createTbc_sub_grupoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_sub_grupoRepository.findAll().size();

        // Create the Tbc_sub_grupo with an existing ID
        Tbc_sub_grupo existingTbc_sub_grupo = new Tbc_sub_grupo();
        existingTbc_sub_grupo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_sub_grupoMockMvc.perform(post("/api/tbc-sub-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_sub_grupo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_sub_grupo> tbc_sub_grupoList = tbc_sub_grupoRepository.findAll();
        assertThat(tbc_sub_grupoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_sub_grupoRepository.findAll().size();
        // set the field null
        tbc_sub_grupo.setNome(null);

        // Create the Tbc_sub_grupo, which fails.

        restTbc_sub_grupoMockMvc.perform(post("/api/tbc-sub-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_sub_grupo)))
            .andExpect(status().isBadRequest());

        List<Tbc_sub_grupo> tbc_sub_grupoList = tbc_sub_grupoRepository.findAll();
        assertThat(tbc_sub_grupoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_sub_grupoRepository.findAll().size();
        // set the field null
        tbc_sub_grupo.setSigla(null);

        // Create the Tbc_sub_grupo, which fails.

        restTbc_sub_grupoMockMvc.perform(post("/api/tbc-sub-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_sub_grupo)))
            .andExpect(status().isBadRequest());

        List<Tbc_sub_grupo> tbc_sub_grupoList = tbc_sub_grupoRepository.findAll();
        assertThat(tbc_sub_grupoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_sub_grupos() throws Exception {
        // Initialize the database
        tbc_sub_grupoRepository.saveAndFlush(tbc_sub_grupo);

        // Get all the tbc_sub_grupoList
        restTbc_sub_grupoMockMvc.perform(get("/api/tbc-sub-grupos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_sub_grupo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTbc_sub_grupo() throws Exception {
        // Initialize the database
        tbc_sub_grupoRepository.saveAndFlush(tbc_sub_grupo);

        // Get the tbc_sub_grupo
        restTbc_sub_grupoMockMvc.perform(get("/api/tbc-sub-grupos/{id}", tbc_sub_grupo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_sub_grupo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_sub_grupo() throws Exception {
        // Get the tbc_sub_grupo
        restTbc_sub_grupoMockMvc.perform(get("/api/tbc-sub-grupos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_sub_grupo() throws Exception {
        // Initialize the database
        tbc_sub_grupoService.save(tbc_sub_grupo);

        int databaseSizeBeforeUpdate = tbc_sub_grupoRepository.findAll().size();

        // Update the tbc_sub_grupo
        Tbc_sub_grupo updatedTbc_sub_grupo = tbc_sub_grupoRepository.findOne(tbc_sub_grupo.getId());
        updatedTbc_sub_grupo
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .sigla(UPDATED_SIGLA)
                .removido(UPDATED_REMOVIDO);

        restTbc_sub_grupoMockMvc.perform(put("/api/tbc-sub-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_sub_grupo)))
            .andExpect(status().isOk());

        // Validate the Tbc_sub_grupo in the database
        List<Tbc_sub_grupo> tbc_sub_grupoList = tbc_sub_grupoRepository.findAll();
        assertThat(tbc_sub_grupoList).hasSize(databaseSizeBeforeUpdate);
        Tbc_sub_grupo testTbc_sub_grupo = tbc_sub_grupoList.get(tbc_sub_grupoList.size() - 1);
        assertThat(testTbc_sub_grupo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_sub_grupo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_sub_grupo.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testTbc_sub_grupo.isRemovido()).isEqualTo(UPDATED_REMOVIDO);

        // Validate the Tbc_sub_grupo in ElasticSearch
        Tbc_sub_grupo tbc_sub_grupoEs = tbc_sub_grupoSearchRepository.findOne(testTbc_sub_grupo.getId());
        assertThat(tbc_sub_grupoEs).isEqualToComparingFieldByField(testTbc_sub_grupo);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_sub_grupo() throws Exception {
        int databaseSizeBeforeUpdate = tbc_sub_grupoRepository.findAll().size();

        // Create the Tbc_sub_grupo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_sub_grupoMockMvc.perform(put("/api/tbc-sub-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_sub_grupo)))
            .andExpect(status().isCreated());

        // Validate the Tbc_sub_grupo in the database
        List<Tbc_sub_grupo> tbc_sub_grupoList = tbc_sub_grupoRepository.findAll();
        assertThat(tbc_sub_grupoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_sub_grupo() throws Exception {
        // Initialize the database
        tbc_sub_grupoService.save(tbc_sub_grupo);

        int databaseSizeBeforeDelete = tbc_sub_grupoRepository.findAll().size();

        // Get the tbc_sub_grupo
        restTbc_sub_grupoMockMvc.perform(delete("/api/tbc-sub-grupos/{id}", tbc_sub_grupo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_sub_grupoExistsInEs = tbc_sub_grupoSearchRepository.exists(tbc_sub_grupo.getId());
        assertThat(tbc_sub_grupoExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_sub_grupo> tbc_sub_grupoList = tbc_sub_grupoRepository.findAll();
        assertThat(tbc_sub_grupoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_sub_grupo() throws Exception {
        // Initialize the database
        tbc_sub_grupoService.save(tbc_sub_grupo);

        // Search the tbc_sub_grupo
        restTbc_sub_grupoMockMvc.perform(get("/api/_search/tbc-sub-grupos?query=id:" + tbc_sub_grupo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_sub_grupo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }
}
