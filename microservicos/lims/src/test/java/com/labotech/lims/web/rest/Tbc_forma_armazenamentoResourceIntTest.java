package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_forma_armazenamento;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.repository.Tbc_forma_armazenamentoRepository;
import com.labotech.lims.service.Tbc_forma_armazenamentoService;
import com.labotech.lims.repository.search.Tbc_forma_armazenamentoSearchRepository;

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
 * Test class for the Tbc_forma_armazenamentoResource REST controller.
 *
 * @see Tbc_forma_armazenamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_forma_armazenamentoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    @Inject
    private Tbc_forma_armazenamentoRepository tbc_forma_armazenamentoRepository;

    @Inject
    private Tbc_forma_armazenamentoService tbc_forma_armazenamentoService;

    @Inject
    private Tbc_forma_armazenamentoSearchRepository tbc_forma_armazenamentoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_forma_armazenamentoMockMvc;

    private Tbc_forma_armazenamento tbc_forma_armazenamento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_forma_armazenamentoResource tbc_forma_armazenamentoResource = new Tbc_forma_armazenamentoResource();
        ReflectionTestUtils.setField(tbc_forma_armazenamentoResource, "tbc_forma_armazenamentoService", tbc_forma_armazenamentoService);
        this.restTbc_forma_armazenamentoMockMvc = MockMvcBuilders.standaloneSetup(tbc_forma_armazenamentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_forma_armazenamento createEntity(EntityManager em) {
        Tbc_forma_armazenamento tbc_forma_armazenamento = new Tbc_forma_armazenamento()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .removido(DEFAULT_REMOVIDO);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_forma_armazenamento.setTbc_instituicao(tbc_instituicao);
        return tbc_forma_armazenamento;
    }

    @Before
    public void initTest() {
        tbc_forma_armazenamentoSearchRepository.deleteAll();
        tbc_forma_armazenamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_forma_armazenamento() throws Exception {
        int databaseSizeBeforeCreate = tbc_forma_armazenamentoRepository.findAll().size();

        // Create the Tbc_forma_armazenamento

        restTbc_forma_armazenamentoMockMvc.perform(post("/api/tbc-forma-armazenamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_forma_armazenamento)))
            .andExpect(status().isCreated());

        // Validate the Tbc_forma_armazenamento in the database
        List<Tbc_forma_armazenamento> tbc_forma_armazenamentoList = tbc_forma_armazenamentoRepository.findAll();
        assertThat(tbc_forma_armazenamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_forma_armazenamento testTbc_forma_armazenamento = tbc_forma_armazenamentoList.get(tbc_forma_armazenamentoList.size() - 1);
        assertThat(testTbc_forma_armazenamento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_forma_armazenamento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_forma_armazenamento.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);

        // Validate the Tbc_forma_armazenamento in ElasticSearch
        Tbc_forma_armazenamento tbc_forma_armazenamentoEs = tbc_forma_armazenamentoSearchRepository.findOne(testTbc_forma_armazenamento.getId());
        assertThat(tbc_forma_armazenamentoEs).isEqualToComparingFieldByField(testTbc_forma_armazenamento);
    }

    @Test
    @Transactional
    public void createTbc_forma_armazenamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_forma_armazenamentoRepository.findAll().size();

        // Create the Tbc_forma_armazenamento with an existing ID
        Tbc_forma_armazenamento existingTbc_forma_armazenamento = new Tbc_forma_armazenamento();
        existingTbc_forma_armazenamento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_forma_armazenamentoMockMvc.perform(post("/api/tbc-forma-armazenamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_forma_armazenamento)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_forma_armazenamento> tbc_forma_armazenamentoList = tbc_forma_armazenamentoRepository.findAll();
        assertThat(tbc_forma_armazenamentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_forma_armazenamentoRepository.findAll().size();
        // set the field null
        tbc_forma_armazenamento.setNome(null);

        // Create the Tbc_forma_armazenamento, which fails.

        restTbc_forma_armazenamentoMockMvc.perform(post("/api/tbc-forma-armazenamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_forma_armazenamento)))
            .andExpect(status().isBadRequest());

        List<Tbc_forma_armazenamento> tbc_forma_armazenamentoList = tbc_forma_armazenamentoRepository.findAll();
        assertThat(tbc_forma_armazenamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_forma_armazenamentos() throws Exception {
        // Initialize the database
        tbc_forma_armazenamentoRepository.saveAndFlush(tbc_forma_armazenamento);

        // Get all the tbc_forma_armazenamentoList
        restTbc_forma_armazenamentoMockMvc.perform(get("/api/tbc-forma-armazenamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_forma_armazenamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTbc_forma_armazenamento() throws Exception {
        // Initialize the database
        tbc_forma_armazenamentoRepository.saveAndFlush(tbc_forma_armazenamento);

        // Get the tbc_forma_armazenamento
        restTbc_forma_armazenamentoMockMvc.perform(get("/api/tbc-forma-armazenamentos/{id}", tbc_forma_armazenamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_forma_armazenamento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_forma_armazenamento() throws Exception {
        // Get the tbc_forma_armazenamento
        restTbc_forma_armazenamentoMockMvc.perform(get("/api/tbc-forma-armazenamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_forma_armazenamento() throws Exception {
        // Initialize the database
        tbc_forma_armazenamentoService.save(tbc_forma_armazenamento);

        int databaseSizeBeforeUpdate = tbc_forma_armazenamentoRepository.findAll().size();

        // Update the tbc_forma_armazenamento
        Tbc_forma_armazenamento updatedTbc_forma_armazenamento = tbc_forma_armazenamentoRepository.findOne(tbc_forma_armazenamento.getId());
        updatedTbc_forma_armazenamento
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .removido(UPDATED_REMOVIDO);

        restTbc_forma_armazenamentoMockMvc.perform(put("/api/tbc-forma-armazenamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_forma_armazenamento)))
            .andExpect(status().isOk());

        // Validate the Tbc_forma_armazenamento in the database
        List<Tbc_forma_armazenamento> tbc_forma_armazenamentoList = tbc_forma_armazenamentoRepository.findAll();
        assertThat(tbc_forma_armazenamentoList).hasSize(databaseSizeBeforeUpdate);
        Tbc_forma_armazenamento testTbc_forma_armazenamento = tbc_forma_armazenamentoList.get(tbc_forma_armazenamentoList.size() - 1);
        assertThat(testTbc_forma_armazenamento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_forma_armazenamento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_forma_armazenamento.isRemovido()).isEqualTo(UPDATED_REMOVIDO);

        // Validate the Tbc_forma_armazenamento in ElasticSearch
        Tbc_forma_armazenamento tbc_forma_armazenamentoEs = tbc_forma_armazenamentoSearchRepository.findOne(testTbc_forma_armazenamento.getId());
        assertThat(tbc_forma_armazenamentoEs).isEqualToComparingFieldByField(testTbc_forma_armazenamento);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_forma_armazenamento() throws Exception {
        int databaseSizeBeforeUpdate = tbc_forma_armazenamentoRepository.findAll().size();

        // Create the Tbc_forma_armazenamento

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_forma_armazenamentoMockMvc.perform(put("/api/tbc-forma-armazenamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_forma_armazenamento)))
            .andExpect(status().isCreated());

        // Validate the Tbc_forma_armazenamento in the database
        List<Tbc_forma_armazenamento> tbc_forma_armazenamentoList = tbc_forma_armazenamentoRepository.findAll();
        assertThat(tbc_forma_armazenamentoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_forma_armazenamento() throws Exception {
        // Initialize the database
        tbc_forma_armazenamentoService.save(tbc_forma_armazenamento);

        int databaseSizeBeforeDelete = tbc_forma_armazenamentoRepository.findAll().size();

        // Get the tbc_forma_armazenamento
        restTbc_forma_armazenamentoMockMvc.perform(delete("/api/tbc-forma-armazenamentos/{id}", tbc_forma_armazenamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_forma_armazenamentoExistsInEs = tbc_forma_armazenamentoSearchRepository.exists(tbc_forma_armazenamento.getId());
        assertThat(tbc_forma_armazenamentoExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_forma_armazenamento> tbc_forma_armazenamentoList = tbc_forma_armazenamentoRepository.findAll();
        assertThat(tbc_forma_armazenamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_forma_armazenamento() throws Exception {
        // Initialize the database
        tbc_forma_armazenamentoService.save(tbc_forma_armazenamento);

        // Search the tbc_forma_armazenamento
        restTbc_forma_armazenamentoMockMvc.perform(get("/api/_search/tbc-forma-armazenamentos?query=id:" + tbc_forma_armazenamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_forma_armazenamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }
}
