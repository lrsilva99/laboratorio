package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbc_lab_tercerizado;
import com.labotech.lims.domain.Tbc_instituicao;
import com.labotech.lims.repository.Tbc_lab_tercerizadoRepository;
import com.labotech.lims.service.Tbc_lab_tercerizadoService;
import com.labotech.lims.repository.search.Tbc_lab_tercerizadoSearchRepository;

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
 * Test class for the Tbc_lab_tercerizadoResource REST controller.
 *
 * @see Tbc_lab_tercerizadoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbc_lab_tercerizadoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMOVIDO = false;
    private static final Boolean UPDATED_REMOVIDO = true;

    @Inject
    private Tbc_lab_tercerizadoRepository tbc_lab_tercerizadoRepository;

    @Inject
    private Tbc_lab_tercerizadoService tbc_lab_tercerizadoService;

    @Inject
    private Tbc_lab_tercerizadoSearchRepository tbc_lab_tercerizadoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbc_lab_tercerizadoMockMvc;

    private Tbc_lab_tercerizado tbc_lab_tercerizado;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbc_lab_tercerizadoResource tbc_lab_tercerizadoResource = new Tbc_lab_tercerizadoResource();
        ReflectionTestUtils.setField(tbc_lab_tercerizadoResource, "tbc_lab_tercerizadoService", tbc_lab_tercerizadoService);
        this.restTbc_lab_tercerizadoMockMvc = MockMvcBuilders.standaloneSetup(tbc_lab_tercerizadoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbc_lab_tercerizado createEntity(EntityManager em) {
        Tbc_lab_tercerizado tbc_lab_tercerizado = new Tbc_lab_tercerizado()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .removido(DEFAULT_REMOVIDO);
        // Add required entity
        Tbc_instituicao tbc_instituicao = Tbc_instituicaoResourceIntTest.createEntity(em);
        em.persist(tbc_instituicao);
        em.flush();
        tbc_lab_tercerizado.setTbc_instituicao(tbc_instituicao);
        return tbc_lab_tercerizado;
    }

    @Before
    public void initTest() {
        tbc_lab_tercerizadoSearchRepository.deleteAll();
        tbc_lab_tercerizado = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbc_lab_tercerizado() throws Exception {
        int databaseSizeBeforeCreate = tbc_lab_tercerizadoRepository.findAll().size();

        // Create the Tbc_lab_tercerizado

        restTbc_lab_tercerizadoMockMvc.perform(post("/api/tbc-lab-tercerizados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_lab_tercerizado)))
            .andExpect(status().isCreated());

        // Validate the Tbc_lab_tercerizado in the database
        List<Tbc_lab_tercerizado> tbc_lab_tercerizadoList = tbc_lab_tercerizadoRepository.findAll();
        assertThat(tbc_lab_tercerizadoList).hasSize(databaseSizeBeforeCreate + 1);
        Tbc_lab_tercerizado testTbc_lab_tercerizado = tbc_lab_tercerizadoList.get(tbc_lab_tercerizadoList.size() - 1);
        assertThat(testTbc_lab_tercerizado.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTbc_lab_tercerizado.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTbc_lab_tercerizado.isRemovido()).isEqualTo(DEFAULT_REMOVIDO);

        // Validate the Tbc_lab_tercerizado in ElasticSearch
        Tbc_lab_tercerizado tbc_lab_tercerizadoEs = tbc_lab_tercerizadoSearchRepository.findOne(testTbc_lab_tercerizado.getId());
        assertThat(tbc_lab_tercerizadoEs).isEqualToComparingFieldByField(testTbc_lab_tercerizado);
    }

    @Test
    @Transactional
    public void createTbc_lab_tercerizadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbc_lab_tercerizadoRepository.findAll().size();

        // Create the Tbc_lab_tercerizado with an existing ID
        Tbc_lab_tercerizado existingTbc_lab_tercerizado = new Tbc_lab_tercerizado();
        existingTbc_lab_tercerizado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbc_lab_tercerizadoMockMvc.perform(post("/api/tbc-lab-tercerizados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbc_lab_tercerizado)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbc_lab_tercerizado> tbc_lab_tercerizadoList = tbc_lab_tercerizadoRepository.findAll();
        assertThat(tbc_lab_tercerizadoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbc_lab_tercerizadoRepository.findAll().size();
        // set the field null
        tbc_lab_tercerizado.setNome(null);

        // Create the Tbc_lab_tercerizado, which fails.

        restTbc_lab_tercerizadoMockMvc.perform(post("/api/tbc-lab-tercerizados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_lab_tercerizado)))
            .andExpect(status().isBadRequest());

        List<Tbc_lab_tercerizado> tbc_lab_tercerizadoList = tbc_lab_tercerizadoRepository.findAll();
        assertThat(tbc_lab_tercerizadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbc_lab_tercerizados() throws Exception {
        // Initialize the database
        tbc_lab_tercerizadoRepository.saveAndFlush(tbc_lab_tercerizado);

        // Get all the tbc_lab_tercerizadoList
        restTbc_lab_tercerizadoMockMvc.perform(get("/api/tbc-lab-tercerizados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_lab_tercerizado.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTbc_lab_tercerizado() throws Exception {
        // Initialize the database
        tbc_lab_tercerizadoRepository.saveAndFlush(tbc_lab_tercerizado);

        // Get the tbc_lab_tercerizado
        restTbc_lab_tercerizadoMockMvc.perform(get("/api/tbc-lab-tercerizados/{id}", tbc_lab_tercerizado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbc_lab_tercerizado.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.removido").value(DEFAULT_REMOVIDO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTbc_lab_tercerizado() throws Exception {
        // Get the tbc_lab_tercerizado
        restTbc_lab_tercerizadoMockMvc.perform(get("/api/tbc-lab-tercerizados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbc_lab_tercerizado() throws Exception {
        // Initialize the database
        tbc_lab_tercerizadoService.save(tbc_lab_tercerizado);

        int databaseSizeBeforeUpdate = tbc_lab_tercerizadoRepository.findAll().size();

        // Update the tbc_lab_tercerizado
        Tbc_lab_tercerizado updatedTbc_lab_tercerizado = tbc_lab_tercerizadoRepository.findOne(tbc_lab_tercerizado.getId());
        updatedTbc_lab_tercerizado
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .removido(UPDATED_REMOVIDO);

        restTbc_lab_tercerizadoMockMvc.perform(put("/api/tbc-lab-tercerizados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbc_lab_tercerizado)))
            .andExpect(status().isOk());

        // Validate the Tbc_lab_tercerizado in the database
        List<Tbc_lab_tercerizado> tbc_lab_tercerizadoList = tbc_lab_tercerizadoRepository.findAll();
        assertThat(tbc_lab_tercerizadoList).hasSize(databaseSizeBeforeUpdate);
        Tbc_lab_tercerizado testTbc_lab_tercerizado = tbc_lab_tercerizadoList.get(tbc_lab_tercerizadoList.size() - 1);
        assertThat(testTbc_lab_tercerizado.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTbc_lab_tercerizado.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTbc_lab_tercerizado.isRemovido()).isEqualTo(UPDATED_REMOVIDO);

        // Validate the Tbc_lab_tercerizado in ElasticSearch
        Tbc_lab_tercerizado tbc_lab_tercerizadoEs = tbc_lab_tercerizadoSearchRepository.findOne(testTbc_lab_tercerizado.getId());
        assertThat(tbc_lab_tercerizadoEs).isEqualToComparingFieldByField(testTbc_lab_tercerizado);
    }

    @Test
    @Transactional
    public void updateNonExistingTbc_lab_tercerizado() throws Exception {
        int databaseSizeBeforeUpdate = tbc_lab_tercerizadoRepository.findAll().size();

        // Create the Tbc_lab_tercerizado

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbc_lab_tercerizadoMockMvc.perform(put("/api/tbc-lab-tercerizados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbc_lab_tercerizado)))
            .andExpect(status().isCreated());

        // Validate the Tbc_lab_tercerizado in the database
        List<Tbc_lab_tercerizado> tbc_lab_tercerizadoList = tbc_lab_tercerizadoRepository.findAll();
        assertThat(tbc_lab_tercerizadoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbc_lab_tercerizado() throws Exception {
        // Initialize the database
        tbc_lab_tercerizadoService.save(tbc_lab_tercerizado);

        int databaseSizeBeforeDelete = tbc_lab_tercerizadoRepository.findAll().size();

        // Get the tbc_lab_tercerizado
        restTbc_lab_tercerizadoMockMvc.perform(delete("/api/tbc-lab-tercerizados/{id}", tbc_lab_tercerizado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbc_lab_tercerizadoExistsInEs = tbc_lab_tercerizadoSearchRepository.exists(tbc_lab_tercerizado.getId());
        assertThat(tbc_lab_tercerizadoExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbc_lab_tercerizado> tbc_lab_tercerizadoList = tbc_lab_tercerizadoRepository.findAll();
        assertThat(tbc_lab_tercerizadoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbc_lab_tercerizado() throws Exception {
        // Initialize the database
        tbc_lab_tercerizadoService.save(tbc_lab_tercerizado);

        // Search the tbc_lab_tercerizado
        restTbc_lab_tercerizadoMockMvc.perform(get("/api/_search/tbc-lab-tercerizados?query=id:" + tbc_lab_tercerizado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbc_lab_tercerizado.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].removido").value(hasItem(DEFAULT_REMOVIDO.booleanValue())));
    }
}
