package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbr_analise;
import com.labotech.lims.domain.Tbr_amostra;
import com.labotech.lims.domain.Tbc_status;
import com.labotech.lims.repository.Tbr_analiseRepository;
import com.labotech.lims.service.Tbr_analiseService;
import com.labotech.lims.repository.search.Tbr_analiseSearchRepository;

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
import java.time.LocalDate;
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
 * Test class for the Tbr_analiseResource REST controller.
 *
 * @see Tbr_analiseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbr_analiseResourceIntTest {

    private static final String DEFAULT_AUTORIZADO_POR = "AAAAAAAAAA";
    private static final String UPDATED_AUTORIZADO_POR = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLETADO_POR = "AAAAAAAAAA";
    private static final String UPDATED_COMPLETADO_POR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_COMPLETADO_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COMPLETADO_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_AUTORIZADO_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_AUTORIZADO_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CANCELADO_POR = "AAAAAAAAAA";
    private static final String UPDATED_CANCELADO_POR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CANCELADO_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CANCELADO_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_SUSPENSO_POR = "AAAAAAAAAA";
    private static final String UPDATED_SUSPENSO_POR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_SUSPENSO_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SUSPENSO_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_REJEITADO_POR = "AAAAAAAAAA";
    private static final String UPDATED_REJEITADO_POR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_REJEITADO_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REJEITADO_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DISPONIVEL_POR = "AAAAAAAAAA";
    private static final String UPDATED_DISPONIVEL_POR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DISPONIVEL_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DISPONIVEL_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final String DEFAULT_FORMA_CADASTRO = "AAAAAAAAAA";
    private static final String UPDATED_FORMA_CADASTRO = "BBBBBBBBBB";

    private static final String DEFAULT_ANOTACAO_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ANOTACAO_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DIRECTIVA_DATA_ATU = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DIRECTIVA_DATA_ATU = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private Tbr_analiseRepository tbr_analiseRepository;

    @Inject
    private Tbr_analiseService tbr_analiseService;

    @Inject
    private Tbr_analiseSearchRepository tbr_analiseSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbr_analiseMockMvc;

    private Tbr_analise tbr_analise;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbr_analiseResource tbr_analiseResource = new Tbr_analiseResource();
        ReflectionTestUtils.setField(tbr_analiseResource, "tbr_analiseService", tbr_analiseService);
        this.restTbr_analiseMockMvc = MockMvcBuilders.standaloneSetup(tbr_analiseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbr_analise createEntity(EntityManager em) {
        Tbr_analise tbr_analise = new Tbr_analise()
                .autorizado_por(DEFAULT_AUTORIZADO_POR)
                .completado_por(DEFAULT_COMPLETADO_POR)
                .completado_data(DEFAULT_COMPLETADO_DATA)
                .autorizado_data(DEFAULT_AUTORIZADO_DATA)
                .cancelado_por(DEFAULT_CANCELADO_POR)
                .cancelado_data(DEFAULT_CANCELADO_DATA)
                .suspenso_por(DEFAULT_SUSPENSO_POR)
                .suspenso_data(DEFAULT_SUSPENSO_DATA)
                .rejeitado_por(DEFAULT_REJEITADO_POR)
                .rejeitado_data(DEFAULT_REJEITADO_DATA)
                .disponivel_por(DEFAULT_DISPONIVEL_POR)
                .disponivel_data(DEFAULT_DISPONIVEL_DATA)
                .observacao(DEFAULT_OBSERVACAO)
                .forma_cadastro(DEFAULT_FORMA_CADASTRO)
                .anotacao_status(DEFAULT_ANOTACAO_STATUS)
                .directiva_data_atu(DEFAULT_DIRECTIVA_DATA_ATU);
        // Add required entity
        Tbr_amostra tbr_amostra = Tbr_amostraResourceIntTest.createEntity(em);
        em.persist(tbr_amostra);
        em.flush();
        tbr_analise.setTbr_amostra(tbr_amostra);
        // Add required entity
        Tbc_status tbc_status = Tbc_statusResourceIntTest.createEntity(em);
        em.persist(tbc_status);
        em.flush();
        tbr_analise.setTbc_status(tbc_status);
        return tbr_analise;
    }

    @Before
    public void initTest() {
        tbr_analiseSearchRepository.deleteAll();
        tbr_analise = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbr_analise() throws Exception {
        int databaseSizeBeforeCreate = tbr_analiseRepository.findAll().size();

        // Create the Tbr_analise

        restTbr_analiseMockMvc.perform(post("/api/tbr-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbr_analise)))
            .andExpect(status().isCreated());

        // Validate the Tbr_analise in the database
        List<Tbr_analise> tbr_analiseList = tbr_analiseRepository.findAll();
        assertThat(tbr_analiseList).hasSize(databaseSizeBeforeCreate + 1);
        Tbr_analise testTbr_analise = tbr_analiseList.get(tbr_analiseList.size() - 1);
        assertThat(testTbr_analise.getAutorizado_por()).isEqualTo(DEFAULT_AUTORIZADO_POR);
        assertThat(testTbr_analise.getCompletado_por()).isEqualTo(DEFAULT_COMPLETADO_POR);
        assertThat(testTbr_analise.getCompletado_data()).isEqualTo(DEFAULT_COMPLETADO_DATA);
        assertThat(testTbr_analise.getAutorizado_data()).isEqualTo(DEFAULT_AUTORIZADO_DATA);
        assertThat(testTbr_analise.getCancelado_por()).isEqualTo(DEFAULT_CANCELADO_POR);
        assertThat(testTbr_analise.getCancelado_data()).isEqualTo(DEFAULT_CANCELADO_DATA);
        assertThat(testTbr_analise.getSuspenso_por()).isEqualTo(DEFAULT_SUSPENSO_POR);
        assertThat(testTbr_analise.getSuspenso_data()).isEqualTo(DEFAULT_SUSPENSO_DATA);
        assertThat(testTbr_analise.getRejeitado_por()).isEqualTo(DEFAULT_REJEITADO_POR);
        assertThat(testTbr_analise.getRejeitado_data()).isEqualTo(DEFAULT_REJEITADO_DATA);
        assertThat(testTbr_analise.getDisponivel_por()).isEqualTo(DEFAULT_DISPONIVEL_POR);
        assertThat(testTbr_analise.getDisponivel_data()).isEqualTo(DEFAULT_DISPONIVEL_DATA);
        assertThat(testTbr_analise.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testTbr_analise.getForma_cadastro()).isEqualTo(DEFAULT_FORMA_CADASTRO);
        assertThat(testTbr_analise.getAnotacao_status()).isEqualTo(DEFAULT_ANOTACAO_STATUS);
        assertThat(testTbr_analise.getDirectiva_data_atu()).isEqualTo(DEFAULT_DIRECTIVA_DATA_ATU);

        // Validate the Tbr_analise in ElasticSearch
        Tbr_analise tbr_analiseEs = tbr_analiseSearchRepository.findOne(testTbr_analise.getId());
        assertThat(tbr_analiseEs).isEqualToComparingFieldByField(testTbr_analise);
    }

    @Test
    @Transactional
    public void createTbr_analiseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbr_analiseRepository.findAll().size();

        // Create the Tbr_analise with an existing ID
        Tbr_analise existingTbr_analise = new Tbr_analise();
        existingTbr_analise.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbr_analiseMockMvc.perform(post("/api/tbr-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbr_analise)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbr_analise> tbr_analiseList = tbr_analiseRepository.findAll();
        assertThat(tbr_analiseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTbr_analises() throws Exception {
        // Initialize the database
        tbr_analiseRepository.saveAndFlush(tbr_analise);

        // Get all the tbr_analiseList
        restTbr_analiseMockMvc.perform(get("/api/tbr-analises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbr_analise.getId().intValue())))
            .andExpect(jsonPath("$.[*].autorizado_por").value(hasItem(DEFAULT_AUTORIZADO_POR.toString())))
            .andExpect(jsonPath("$.[*].completado_por").value(hasItem(DEFAULT_COMPLETADO_POR.toString())))
            .andExpect(jsonPath("$.[*].completado_data").value(hasItem(sameInstant(DEFAULT_COMPLETADO_DATA))))
            .andExpect(jsonPath("$.[*].autorizado_data").value(hasItem(sameInstant(DEFAULT_AUTORIZADO_DATA))))
            .andExpect(jsonPath("$.[*].cancelado_por").value(hasItem(DEFAULT_CANCELADO_POR.toString())))
            .andExpect(jsonPath("$.[*].cancelado_data").value(hasItem(sameInstant(DEFAULT_CANCELADO_DATA))))
            .andExpect(jsonPath("$.[*].suspenso_por").value(hasItem(DEFAULT_SUSPENSO_POR.toString())))
            .andExpect(jsonPath("$.[*].suspenso_data").value(hasItem(sameInstant(DEFAULT_SUSPENSO_DATA))))
            .andExpect(jsonPath("$.[*].rejeitado_por").value(hasItem(DEFAULT_REJEITADO_POR.toString())))
            .andExpect(jsonPath("$.[*].rejeitado_data").value(hasItem(sameInstant(DEFAULT_REJEITADO_DATA))))
            .andExpect(jsonPath("$.[*].disponivel_por").value(hasItem(DEFAULT_DISPONIVEL_POR.toString())))
            .andExpect(jsonPath("$.[*].disponivel_data").value(hasItem(sameInstant(DEFAULT_DISPONIVEL_DATA))))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].forma_cadastro").value(hasItem(DEFAULT_FORMA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].anotacao_status").value(hasItem(DEFAULT_ANOTACAO_STATUS.toString())))
            .andExpect(jsonPath("$.[*].directiva_data_atu").value(hasItem(DEFAULT_DIRECTIVA_DATA_ATU.toString())));
    }

    @Test
    @Transactional
    public void getTbr_analise() throws Exception {
        // Initialize the database
        tbr_analiseRepository.saveAndFlush(tbr_analise);

        // Get the tbr_analise
        restTbr_analiseMockMvc.perform(get("/api/tbr-analises/{id}", tbr_analise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbr_analise.getId().intValue()))
            .andExpect(jsonPath("$.autorizado_por").value(DEFAULT_AUTORIZADO_POR.toString()))
            .andExpect(jsonPath("$.completado_por").value(DEFAULT_COMPLETADO_POR.toString()))
            .andExpect(jsonPath("$.completado_data").value(sameInstant(DEFAULT_COMPLETADO_DATA)))
            .andExpect(jsonPath("$.autorizado_data").value(sameInstant(DEFAULT_AUTORIZADO_DATA)))
            .andExpect(jsonPath("$.cancelado_por").value(DEFAULT_CANCELADO_POR.toString()))
            .andExpect(jsonPath("$.cancelado_data").value(sameInstant(DEFAULT_CANCELADO_DATA)))
            .andExpect(jsonPath("$.suspenso_por").value(DEFAULT_SUSPENSO_POR.toString()))
            .andExpect(jsonPath("$.suspenso_data").value(sameInstant(DEFAULT_SUSPENSO_DATA)))
            .andExpect(jsonPath("$.rejeitado_por").value(DEFAULT_REJEITADO_POR.toString()))
            .andExpect(jsonPath("$.rejeitado_data").value(sameInstant(DEFAULT_REJEITADO_DATA)))
            .andExpect(jsonPath("$.disponivel_por").value(DEFAULT_DISPONIVEL_POR.toString()))
            .andExpect(jsonPath("$.disponivel_data").value(sameInstant(DEFAULT_DISPONIVEL_DATA)))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()))
            .andExpect(jsonPath("$.forma_cadastro").value(DEFAULT_FORMA_CADASTRO.toString()))
            .andExpect(jsonPath("$.anotacao_status").value(DEFAULT_ANOTACAO_STATUS.toString()))
            .andExpect(jsonPath("$.directiva_data_atu").value(DEFAULT_DIRECTIVA_DATA_ATU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTbr_analise() throws Exception {
        // Get the tbr_analise
        restTbr_analiseMockMvc.perform(get("/api/tbr-analises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbr_analise() throws Exception {
        // Initialize the database
        tbr_analiseService.save(tbr_analise);

        int databaseSizeBeforeUpdate = tbr_analiseRepository.findAll().size();

        // Update the tbr_analise
        Tbr_analise updatedTbr_analise = tbr_analiseRepository.findOne(tbr_analise.getId());
        updatedTbr_analise
                .autorizado_por(UPDATED_AUTORIZADO_POR)
                .completado_por(UPDATED_COMPLETADO_POR)
                .completado_data(UPDATED_COMPLETADO_DATA)
                .autorizado_data(UPDATED_AUTORIZADO_DATA)
                .cancelado_por(UPDATED_CANCELADO_POR)
                .cancelado_data(UPDATED_CANCELADO_DATA)
                .suspenso_por(UPDATED_SUSPENSO_POR)
                .suspenso_data(UPDATED_SUSPENSO_DATA)
                .rejeitado_por(UPDATED_REJEITADO_POR)
                .rejeitado_data(UPDATED_REJEITADO_DATA)
                .disponivel_por(UPDATED_DISPONIVEL_POR)
                .disponivel_data(UPDATED_DISPONIVEL_DATA)
                .observacao(UPDATED_OBSERVACAO)
                .forma_cadastro(UPDATED_FORMA_CADASTRO)
                .anotacao_status(UPDATED_ANOTACAO_STATUS)
                .directiva_data_atu(UPDATED_DIRECTIVA_DATA_ATU);

        restTbr_analiseMockMvc.perform(put("/api/tbr-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbr_analise)))
            .andExpect(status().isOk());

        // Validate the Tbr_analise in the database
        List<Tbr_analise> tbr_analiseList = tbr_analiseRepository.findAll();
        assertThat(tbr_analiseList).hasSize(databaseSizeBeforeUpdate);
        Tbr_analise testTbr_analise = tbr_analiseList.get(tbr_analiseList.size() - 1);
        assertThat(testTbr_analise.getAutorizado_por()).isEqualTo(UPDATED_AUTORIZADO_POR);
        assertThat(testTbr_analise.getCompletado_por()).isEqualTo(UPDATED_COMPLETADO_POR);
        assertThat(testTbr_analise.getCompletado_data()).isEqualTo(UPDATED_COMPLETADO_DATA);
        assertThat(testTbr_analise.getAutorizado_data()).isEqualTo(UPDATED_AUTORIZADO_DATA);
        assertThat(testTbr_analise.getCancelado_por()).isEqualTo(UPDATED_CANCELADO_POR);
        assertThat(testTbr_analise.getCancelado_data()).isEqualTo(UPDATED_CANCELADO_DATA);
        assertThat(testTbr_analise.getSuspenso_por()).isEqualTo(UPDATED_SUSPENSO_POR);
        assertThat(testTbr_analise.getSuspenso_data()).isEqualTo(UPDATED_SUSPENSO_DATA);
        assertThat(testTbr_analise.getRejeitado_por()).isEqualTo(UPDATED_REJEITADO_POR);
        assertThat(testTbr_analise.getRejeitado_data()).isEqualTo(UPDATED_REJEITADO_DATA);
        assertThat(testTbr_analise.getDisponivel_por()).isEqualTo(UPDATED_DISPONIVEL_POR);
        assertThat(testTbr_analise.getDisponivel_data()).isEqualTo(UPDATED_DISPONIVEL_DATA);
        assertThat(testTbr_analise.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testTbr_analise.getForma_cadastro()).isEqualTo(UPDATED_FORMA_CADASTRO);
        assertThat(testTbr_analise.getAnotacao_status()).isEqualTo(UPDATED_ANOTACAO_STATUS);
        assertThat(testTbr_analise.getDirectiva_data_atu()).isEqualTo(UPDATED_DIRECTIVA_DATA_ATU);

        // Validate the Tbr_analise in ElasticSearch
        Tbr_analise tbr_analiseEs = tbr_analiseSearchRepository.findOne(testTbr_analise.getId());
        assertThat(tbr_analiseEs).isEqualToComparingFieldByField(testTbr_analise);
    }

    @Test
    @Transactional
    public void updateNonExistingTbr_analise() throws Exception {
        int databaseSizeBeforeUpdate = tbr_analiseRepository.findAll().size();

        // Create the Tbr_analise

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbr_analiseMockMvc.perform(put("/api/tbr-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbr_analise)))
            .andExpect(status().isCreated());

        // Validate the Tbr_analise in the database
        List<Tbr_analise> tbr_analiseList = tbr_analiseRepository.findAll();
        assertThat(tbr_analiseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbr_analise() throws Exception {
        // Initialize the database
        tbr_analiseService.save(tbr_analise);

        int databaseSizeBeforeDelete = tbr_analiseRepository.findAll().size();

        // Get the tbr_analise
        restTbr_analiseMockMvc.perform(delete("/api/tbr-analises/{id}", tbr_analise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbr_analiseExistsInEs = tbr_analiseSearchRepository.exists(tbr_analise.getId());
        assertThat(tbr_analiseExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbr_analise> tbr_analiseList = tbr_analiseRepository.findAll();
        assertThat(tbr_analiseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbr_analise() throws Exception {
        // Initialize the database
        tbr_analiseService.save(tbr_analise);

        // Search the tbr_analise
        restTbr_analiseMockMvc.perform(get("/api/_search/tbr-analises?query=id:" + tbr_analise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbr_analise.getId().intValue())))
            .andExpect(jsonPath("$.[*].autorizado_por").value(hasItem(DEFAULT_AUTORIZADO_POR.toString())))
            .andExpect(jsonPath("$.[*].completado_por").value(hasItem(DEFAULT_COMPLETADO_POR.toString())))
            .andExpect(jsonPath("$.[*].completado_data").value(hasItem(sameInstant(DEFAULT_COMPLETADO_DATA))))
            .andExpect(jsonPath("$.[*].autorizado_data").value(hasItem(sameInstant(DEFAULT_AUTORIZADO_DATA))))
            .andExpect(jsonPath("$.[*].cancelado_por").value(hasItem(DEFAULT_CANCELADO_POR.toString())))
            .andExpect(jsonPath("$.[*].cancelado_data").value(hasItem(sameInstant(DEFAULT_CANCELADO_DATA))))
            .andExpect(jsonPath("$.[*].suspenso_por").value(hasItem(DEFAULT_SUSPENSO_POR.toString())))
            .andExpect(jsonPath("$.[*].suspenso_data").value(hasItem(sameInstant(DEFAULT_SUSPENSO_DATA))))
            .andExpect(jsonPath("$.[*].rejeitado_por").value(hasItem(DEFAULT_REJEITADO_POR.toString())))
            .andExpect(jsonPath("$.[*].rejeitado_data").value(hasItem(sameInstant(DEFAULT_REJEITADO_DATA))))
            .andExpect(jsonPath("$.[*].disponivel_por").value(hasItem(DEFAULT_DISPONIVEL_POR.toString())))
            .andExpect(jsonPath("$.[*].disponivel_data").value(hasItem(sameInstant(DEFAULT_DISPONIVEL_DATA))))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].forma_cadastro").value(hasItem(DEFAULT_FORMA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].anotacao_status").value(hasItem(DEFAULT_ANOTACAO_STATUS.toString())))
            .andExpect(jsonPath("$.[*].directiva_data_atu").value(hasItem(DEFAULT_DIRECTIVA_DATA_ATU.toString())));
    }
}
