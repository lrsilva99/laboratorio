package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbr_analise_resultado;
import com.labotech.lims.domain.Tbc_status;
import com.labotech.lims.repository.Tbr_analise_resultadoRepository;
import com.labotech.lims.repository.search.Tbr_analise_resultadoSearchRepository;

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
 * Test class for the Tbr_analise_resultadoResource REST controller.
 *
 * @see Tbr_analise_resultadoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbr_analise_resultadoResourceIntTest {

    private static final String DEFAULT_ANALISES_COMPONENTE = "AAAAAAAAAA";
    private static final String UPDATED_ANALISES_COMPONENTE = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTADO = "AAAAAAAAAA";
    private static final String UPDATED_RESULTADO = "BBBBBBBBBB";

    private static final String DEFAULT_AUTORIZADO_POR = "AAAAAAAAAA";
    private static final String UPDATED_AUTORIZADO_POR = "BBBBBBBBBB";

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

    private static final String DEFAULT_RESULTADO_DIGITADO = "AAAAAAAAAA";
    private static final String UPDATED_RESULTADO_DIGITADO = "BBBBBBBBBB";

    private static final String DEFAULT_UNIDADE_MEDIDA = "AAAAAAAAAA";
    private static final String UPDATED_UNIDADE_MEDIDA = "BBBBBBBBBB";

    private static final Long DEFAULT_TBR_ANALISE_ID = 1L;
    private static final Long UPDATED_TBR_ANALISE_ID = 2L;

    private static final String DEFAULT_RESULTADO_TEXTO = "AAAAAAAAAA";
    private static final String UPDATED_RESULTADO_TEXTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REPETIDO = false;
    private static final Boolean UPDATED_REPETIDO = true;

    private static final Long DEFAULT_TBR_AMOSTRA_ID = 1L;
    private static final Long UPDATED_TBR_AMOSTRA_ID = 2L;

    private static final String DEFAULT_COMPLETADO_POR = "AAAAAAAAAA";
    private static final String UPDATED_COMPLETADO_POR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_COMPLETADO_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COMPLETADO_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private Tbr_analise_resultadoRepository tbr_analise_resultadoRepository;

    @Inject
    private Tbr_analise_resultadoSearchRepository tbr_analise_resultadoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbr_analise_resultadoMockMvc;

    private Tbr_analise_resultado tbr_analise_resultado;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbr_analise_resultadoResource tbr_analise_resultadoResource = new Tbr_analise_resultadoResource();
        ReflectionTestUtils.setField(tbr_analise_resultadoResource, "tbr_analise_resultadoSearchRepository", tbr_analise_resultadoSearchRepository);
        ReflectionTestUtils.setField(tbr_analise_resultadoResource, "tbr_analise_resultadoRepository", tbr_analise_resultadoRepository);
        this.restTbr_analise_resultadoMockMvc = MockMvcBuilders.standaloneSetup(tbr_analise_resultadoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbr_analise_resultado createEntity(EntityManager em) {
        Tbr_analise_resultado tbr_analise_resultado = new Tbr_analise_resultado()
                .analises_componente(DEFAULT_ANALISES_COMPONENTE)
                .resultado(DEFAULT_RESULTADO)
                .autorizado_por(DEFAULT_AUTORIZADO_POR)
                .autorizado_data(DEFAULT_AUTORIZADO_DATA)
                .cancelado_por(DEFAULT_CANCELADO_POR)
                .cancelado_data(DEFAULT_CANCELADO_DATA)
                .suspenso_por(DEFAULT_SUSPENSO_POR)
                .suspenso_data(DEFAULT_SUSPENSO_DATA)
                .rejeitado_por(DEFAULT_REJEITADO_POR)
                .rejeitado_data(DEFAULT_REJEITADO_DATA)
                .disponivel_por(DEFAULT_DISPONIVEL_POR)
                .disponivel_data(DEFAULT_DISPONIVEL_DATA)
                .resultado_digitado(DEFAULT_RESULTADO_DIGITADO)
                .unidade_medida(DEFAULT_UNIDADE_MEDIDA)
                .tbr_analise_id(DEFAULT_TBR_ANALISE_ID)
                .resultado_texto(DEFAULT_RESULTADO_TEXTO)
                .repetido(DEFAULT_REPETIDO)
                .tbr_amostra_id(DEFAULT_TBR_AMOSTRA_ID)
                .completado_por(DEFAULT_COMPLETADO_POR)
                .completado_data(DEFAULT_COMPLETADO_DATA);
        // Add required entity
        Tbc_status tbc_status = Tbc_statusResourceIntTest.createEntity(em);
        em.persist(tbc_status);
        em.flush();
        tbr_analise_resultado.setTbc_status(tbc_status);
        return tbr_analise_resultado;
    }

    @Before
    public void initTest() {
        tbr_analise_resultadoSearchRepository.deleteAll();
        tbr_analise_resultado = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbr_analise_resultado() throws Exception {
        int databaseSizeBeforeCreate = tbr_analise_resultadoRepository.findAll().size();

        // Create the Tbr_analise_resultado

        restTbr_analise_resultadoMockMvc.perform(post("/api/tbr-analise-resultados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbr_analise_resultado)))
            .andExpect(status().isCreated());

        // Validate the Tbr_analise_resultado in the database
        List<Tbr_analise_resultado> tbr_analise_resultadoList = tbr_analise_resultadoRepository.findAll();
        assertThat(tbr_analise_resultadoList).hasSize(databaseSizeBeforeCreate + 1);
        Tbr_analise_resultado testTbr_analise_resultado = tbr_analise_resultadoList.get(tbr_analise_resultadoList.size() - 1);
        assertThat(testTbr_analise_resultado.getAnalises_componente()).isEqualTo(DEFAULT_ANALISES_COMPONENTE);
        assertThat(testTbr_analise_resultado.getResultado()).isEqualTo(DEFAULT_RESULTADO);
        assertThat(testTbr_analise_resultado.getAutorizado_por()).isEqualTo(DEFAULT_AUTORIZADO_POR);
        assertThat(testTbr_analise_resultado.getAutorizado_data()).isEqualTo(DEFAULT_AUTORIZADO_DATA);
        assertThat(testTbr_analise_resultado.getCancelado_por()).isEqualTo(DEFAULT_CANCELADO_POR);
        assertThat(testTbr_analise_resultado.getCancelado_data()).isEqualTo(DEFAULT_CANCELADO_DATA);
        assertThat(testTbr_analise_resultado.getSuspenso_por()).isEqualTo(DEFAULT_SUSPENSO_POR);
        assertThat(testTbr_analise_resultado.getSuspenso_data()).isEqualTo(DEFAULT_SUSPENSO_DATA);
        assertThat(testTbr_analise_resultado.getRejeitado_por()).isEqualTo(DEFAULT_REJEITADO_POR);
        assertThat(testTbr_analise_resultado.getRejeitado_data()).isEqualTo(DEFAULT_REJEITADO_DATA);
        assertThat(testTbr_analise_resultado.getDisponivel_por()).isEqualTo(DEFAULT_DISPONIVEL_POR);
        assertThat(testTbr_analise_resultado.getDisponivel_data()).isEqualTo(DEFAULT_DISPONIVEL_DATA);
        assertThat(testTbr_analise_resultado.getResultado_digitado()).isEqualTo(DEFAULT_RESULTADO_DIGITADO);
        assertThat(testTbr_analise_resultado.getUnidade_medida()).isEqualTo(DEFAULT_UNIDADE_MEDIDA);
        assertThat(testTbr_analise_resultado.getTbr_analise_id()).isEqualTo(DEFAULT_TBR_ANALISE_ID);
        assertThat(testTbr_analise_resultado.getResultado_texto()).isEqualTo(DEFAULT_RESULTADO_TEXTO);
        assertThat(testTbr_analise_resultado.isRepetido()).isEqualTo(DEFAULT_REPETIDO);
        assertThat(testTbr_analise_resultado.getTbr_amostra_id()).isEqualTo(DEFAULT_TBR_AMOSTRA_ID);
        assertThat(testTbr_analise_resultado.getCompletado_por()).isEqualTo(DEFAULT_COMPLETADO_POR);
        assertThat(testTbr_analise_resultado.getCompletado_data()).isEqualTo(DEFAULT_COMPLETADO_DATA);

        // Validate the Tbr_analise_resultado in ElasticSearch
        Tbr_analise_resultado tbr_analise_resultadoEs = tbr_analise_resultadoSearchRepository.findOne(testTbr_analise_resultado.getId());
        assertThat(tbr_analise_resultadoEs).isEqualToComparingFieldByField(testTbr_analise_resultado);
    }

    @Test
    @Transactional
    public void createTbr_analise_resultadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbr_analise_resultadoRepository.findAll().size();

        // Create the Tbr_analise_resultado with an existing ID
        Tbr_analise_resultado existingTbr_analise_resultado = new Tbr_analise_resultado();
        existingTbr_analise_resultado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbr_analise_resultadoMockMvc.perform(post("/api/tbr-analise-resultados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbr_analise_resultado)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbr_analise_resultado> tbr_analise_resultadoList = tbr_analise_resultadoRepository.findAll();
        assertThat(tbr_analise_resultadoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAnalises_componenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbr_analise_resultadoRepository.findAll().size();
        // set the field null
        tbr_analise_resultado.setAnalises_componente(null);

        // Create the Tbr_analise_resultado, which fails.

        restTbr_analise_resultadoMockMvc.perform(post("/api/tbr-analise-resultados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbr_analise_resultado)))
            .andExpect(status().isBadRequest());

        List<Tbr_analise_resultado> tbr_analise_resultadoList = tbr_analise_resultadoRepository.findAll();
        assertThat(tbr_analise_resultadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResultadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbr_analise_resultadoRepository.findAll().size();
        // set the field null
        tbr_analise_resultado.setResultado(null);

        // Create the Tbr_analise_resultado, which fails.

        restTbr_analise_resultadoMockMvc.perform(post("/api/tbr-analise-resultados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbr_analise_resultado)))
            .andExpect(status().isBadRequest());

        List<Tbr_analise_resultado> tbr_analise_resultadoList = tbr_analise_resultadoRepository.findAll();
        assertThat(tbr_analise_resultadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTbr_analise_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbr_analise_resultadoRepository.findAll().size();
        // set the field null
        tbr_analise_resultado.setTbr_analise_id(null);

        // Create the Tbr_analise_resultado, which fails.

        restTbr_analise_resultadoMockMvc.perform(post("/api/tbr-analise-resultados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbr_analise_resultado)))
            .andExpect(status().isBadRequest());

        List<Tbr_analise_resultado> tbr_analise_resultadoList = tbr_analise_resultadoRepository.findAll();
        assertThat(tbr_analise_resultadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResultado_textoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbr_analise_resultadoRepository.findAll().size();
        // set the field null
        tbr_analise_resultado.setResultado_texto(null);

        // Create the Tbr_analise_resultado, which fails.

        restTbr_analise_resultadoMockMvc.perform(post("/api/tbr-analise-resultados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbr_analise_resultado)))
            .andExpect(status().isBadRequest());

        List<Tbr_analise_resultado> tbr_analise_resultadoList = tbr_analise_resultadoRepository.findAll();
        assertThat(tbr_analise_resultadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTbr_amostra_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbr_analise_resultadoRepository.findAll().size();
        // set the field null
        tbr_analise_resultado.setTbr_amostra_id(null);

        // Create the Tbr_analise_resultado, which fails.

        restTbr_analise_resultadoMockMvc.perform(post("/api/tbr-analise-resultados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbr_analise_resultado)))
            .andExpect(status().isBadRequest());

        List<Tbr_analise_resultado> tbr_analise_resultadoList = tbr_analise_resultadoRepository.findAll();
        assertThat(tbr_analise_resultadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbr_analise_resultados() throws Exception {
        // Initialize the database
        tbr_analise_resultadoRepository.saveAndFlush(tbr_analise_resultado);

        // Get all the tbr_analise_resultadoList
        restTbr_analise_resultadoMockMvc.perform(get("/api/tbr-analise-resultados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbr_analise_resultado.getId().intValue())))
            .andExpect(jsonPath("$.[*].analises_componente").value(hasItem(DEFAULT_ANALISES_COMPONENTE.toString())))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO.toString())))
            .andExpect(jsonPath("$.[*].autorizado_por").value(hasItem(DEFAULT_AUTORIZADO_POR.toString())))
            .andExpect(jsonPath("$.[*].autorizado_data").value(hasItem(sameInstant(DEFAULT_AUTORIZADO_DATA))))
            .andExpect(jsonPath("$.[*].cancelado_por").value(hasItem(DEFAULT_CANCELADO_POR.toString())))
            .andExpect(jsonPath("$.[*].cancelado_data").value(hasItem(sameInstant(DEFAULT_CANCELADO_DATA))))
            .andExpect(jsonPath("$.[*].suspenso_por").value(hasItem(DEFAULT_SUSPENSO_POR.toString())))
            .andExpect(jsonPath("$.[*].suspenso_data").value(hasItem(sameInstant(DEFAULT_SUSPENSO_DATA))))
            .andExpect(jsonPath("$.[*].rejeitado_por").value(hasItem(DEFAULT_REJEITADO_POR.toString())))
            .andExpect(jsonPath("$.[*].rejeitado_data").value(hasItem(sameInstant(DEFAULT_REJEITADO_DATA))))
            .andExpect(jsonPath("$.[*].disponivel_por").value(hasItem(DEFAULT_DISPONIVEL_POR.toString())))
            .andExpect(jsonPath("$.[*].disponivel_data").value(hasItem(sameInstant(DEFAULT_DISPONIVEL_DATA))))
            .andExpect(jsonPath("$.[*].resultado_digitado").value(hasItem(DEFAULT_RESULTADO_DIGITADO.toString())))
            .andExpect(jsonPath("$.[*].unidade_medida").value(hasItem(DEFAULT_UNIDADE_MEDIDA.toString())))
            .andExpect(jsonPath("$.[*].tbr_analise_id").value(hasItem(DEFAULT_TBR_ANALISE_ID.intValue())))
            .andExpect(jsonPath("$.[*].resultado_texto").value(hasItem(DEFAULT_RESULTADO_TEXTO.toString())))
            .andExpect(jsonPath("$.[*].repetido").value(hasItem(DEFAULT_REPETIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].tbr_amostra_id").value(hasItem(DEFAULT_TBR_AMOSTRA_ID.intValue())))
            .andExpect(jsonPath("$.[*].completado_por").value(hasItem(DEFAULT_COMPLETADO_POR.toString())))
            .andExpect(jsonPath("$.[*].completado_data").value(hasItem(sameInstant(DEFAULT_COMPLETADO_DATA))));
    }

    @Test
    @Transactional
    public void getTbr_analise_resultado() throws Exception {
        // Initialize the database
        tbr_analise_resultadoRepository.saveAndFlush(tbr_analise_resultado);

        // Get the tbr_analise_resultado
        restTbr_analise_resultadoMockMvc.perform(get("/api/tbr-analise-resultados/{id}", tbr_analise_resultado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbr_analise_resultado.getId().intValue()))
            .andExpect(jsonPath("$.analises_componente").value(DEFAULT_ANALISES_COMPONENTE.toString()))
            .andExpect(jsonPath("$.resultado").value(DEFAULT_RESULTADO.toString()))
            .andExpect(jsonPath("$.autorizado_por").value(DEFAULT_AUTORIZADO_POR.toString()))
            .andExpect(jsonPath("$.autorizado_data").value(sameInstant(DEFAULT_AUTORIZADO_DATA)))
            .andExpect(jsonPath("$.cancelado_por").value(DEFAULT_CANCELADO_POR.toString()))
            .andExpect(jsonPath("$.cancelado_data").value(sameInstant(DEFAULT_CANCELADO_DATA)))
            .andExpect(jsonPath("$.suspenso_por").value(DEFAULT_SUSPENSO_POR.toString()))
            .andExpect(jsonPath("$.suspenso_data").value(sameInstant(DEFAULT_SUSPENSO_DATA)))
            .andExpect(jsonPath("$.rejeitado_por").value(DEFAULT_REJEITADO_POR.toString()))
            .andExpect(jsonPath("$.rejeitado_data").value(sameInstant(DEFAULT_REJEITADO_DATA)))
            .andExpect(jsonPath("$.disponivel_por").value(DEFAULT_DISPONIVEL_POR.toString()))
            .andExpect(jsonPath("$.disponivel_data").value(sameInstant(DEFAULT_DISPONIVEL_DATA)))
            .andExpect(jsonPath("$.resultado_digitado").value(DEFAULT_RESULTADO_DIGITADO.toString()))
            .andExpect(jsonPath("$.unidade_medida").value(DEFAULT_UNIDADE_MEDIDA.toString()))
            .andExpect(jsonPath("$.tbr_analise_id").value(DEFAULT_TBR_ANALISE_ID.intValue()))
            .andExpect(jsonPath("$.resultado_texto").value(DEFAULT_RESULTADO_TEXTO.toString()))
            .andExpect(jsonPath("$.repetido").value(DEFAULT_REPETIDO.booleanValue()))
            .andExpect(jsonPath("$.tbr_amostra_id").value(DEFAULT_TBR_AMOSTRA_ID.intValue()))
            .andExpect(jsonPath("$.completado_por").value(DEFAULT_COMPLETADO_POR.toString()))
            .andExpect(jsonPath("$.completado_data").value(sameInstant(DEFAULT_COMPLETADO_DATA)));
    }

    @Test
    @Transactional
    public void getNonExistingTbr_analise_resultado() throws Exception {
        // Get the tbr_analise_resultado
        restTbr_analise_resultadoMockMvc.perform(get("/api/tbr-analise-resultados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbr_analise_resultado() throws Exception {
        // Initialize the database
        tbr_analise_resultadoRepository.saveAndFlush(tbr_analise_resultado);
        tbr_analise_resultadoSearchRepository.save(tbr_analise_resultado);
        int databaseSizeBeforeUpdate = tbr_analise_resultadoRepository.findAll().size();

        // Update the tbr_analise_resultado
        Tbr_analise_resultado updatedTbr_analise_resultado = tbr_analise_resultadoRepository.findOne(tbr_analise_resultado.getId());
        updatedTbr_analise_resultado
                .analises_componente(UPDATED_ANALISES_COMPONENTE)
                .resultado(UPDATED_RESULTADO)
                .autorizado_por(UPDATED_AUTORIZADO_POR)
                .autorizado_data(UPDATED_AUTORIZADO_DATA)
                .cancelado_por(UPDATED_CANCELADO_POR)
                .cancelado_data(UPDATED_CANCELADO_DATA)
                .suspenso_por(UPDATED_SUSPENSO_POR)
                .suspenso_data(UPDATED_SUSPENSO_DATA)
                .rejeitado_por(UPDATED_REJEITADO_POR)
                .rejeitado_data(UPDATED_REJEITADO_DATA)
                .disponivel_por(UPDATED_DISPONIVEL_POR)
                .disponivel_data(UPDATED_DISPONIVEL_DATA)
                .resultado_digitado(UPDATED_RESULTADO_DIGITADO)
                .unidade_medida(UPDATED_UNIDADE_MEDIDA)
                .tbr_analise_id(UPDATED_TBR_ANALISE_ID)
                .resultado_texto(UPDATED_RESULTADO_TEXTO)
                .repetido(UPDATED_REPETIDO)
                .tbr_amostra_id(UPDATED_TBR_AMOSTRA_ID)
                .completado_por(UPDATED_COMPLETADO_POR)
                .completado_data(UPDATED_COMPLETADO_DATA);

        restTbr_analise_resultadoMockMvc.perform(put("/api/tbr-analise-resultados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbr_analise_resultado)))
            .andExpect(status().isOk());

        // Validate the Tbr_analise_resultado in the database
        List<Tbr_analise_resultado> tbr_analise_resultadoList = tbr_analise_resultadoRepository.findAll();
        assertThat(tbr_analise_resultadoList).hasSize(databaseSizeBeforeUpdate);
        Tbr_analise_resultado testTbr_analise_resultado = tbr_analise_resultadoList.get(tbr_analise_resultadoList.size() - 1);
        assertThat(testTbr_analise_resultado.getAnalises_componente()).isEqualTo(UPDATED_ANALISES_COMPONENTE);
        assertThat(testTbr_analise_resultado.getResultado()).isEqualTo(UPDATED_RESULTADO);
        assertThat(testTbr_analise_resultado.getAutorizado_por()).isEqualTo(UPDATED_AUTORIZADO_POR);
        assertThat(testTbr_analise_resultado.getAutorizado_data()).isEqualTo(UPDATED_AUTORIZADO_DATA);
        assertThat(testTbr_analise_resultado.getCancelado_por()).isEqualTo(UPDATED_CANCELADO_POR);
        assertThat(testTbr_analise_resultado.getCancelado_data()).isEqualTo(UPDATED_CANCELADO_DATA);
        assertThat(testTbr_analise_resultado.getSuspenso_por()).isEqualTo(UPDATED_SUSPENSO_POR);
        assertThat(testTbr_analise_resultado.getSuspenso_data()).isEqualTo(UPDATED_SUSPENSO_DATA);
        assertThat(testTbr_analise_resultado.getRejeitado_por()).isEqualTo(UPDATED_REJEITADO_POR);
        assertThat(testTbr_analise_resultado.getRejeitado_data()).isEqualTo(UPDATED_REJEITADO_DATA);
        assertThat(testTbr_analise_resultado.getDisponivel_por()).isEqualTo(UPDATED_DISPONIVEL_POR);
        assertThat(testTbr_analise_resultado.getDisponivel_data()).isEqualTo(UPDATED_DISPONIVEL_DATA);
        assertThat(testTbr_analise_resultado.getResultado_digitado()).isEqualTo(UPDATED_RESULTADO_DIGITADO);
        assertThat(testTbr_analise_resultado.getUnidade_medida()).isEqualTo(UPDATED_UNIDADE_MEDIDA);
        assertThat(testTbr_analise_resultado.getTbr_analise_id()).isEqualTo(UPDATED_TBR_ANALISE_ID);
        assertThat(testTbr_analise_resultado.getResultado_texto()).isEqualTo(UPDATED_RESULTADO_TEXTO);
        assertThat(testTbr_analise_resultado.isRepetido()).isEqualTo(UPDATED_REPETIDO);
        assertThat(testTbr_analise_resultado.getTbr_amostra_id()).isEqualTo(UPDATED_TBR_AMOSTRA_ID);
        assertThat(testTbr_analise_resultado.getCompletado_por()).isEqualTo(UPDATED_COMPLETADO_POR);
        assertThat(testTbr_analise_resultado.getCompletado_data()).isEqualTo(UPDATED_COMPLETADO_DATA);

        // Validate the Tbr_analise_resultado in ElasticSearch
        Tbr_analise_resultado tbr_analise_resultadoEs = tbr_analise_resultadoSearchRepository.findOne(testTbr_analise_resultado.getId());
        assertThat(tbr_analise_resultadoEs).isEqualToComparingFieldByField(testTbr_analise_resultado);
    }

    @Test
    @Transactional
    public void updateNonExistingTbr_analise_resultado() throws Exception {
        int databaseSizeBeforeUpdate = tbr_analise_resultadoRepository.findAll().size();

        // Create the Tbr_analise_resultado

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbr_analise_resultadoMockMvc.perform(put("/api/tbr-analise-resultados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbr_analise_resultado)))
            .andExpect(status().isCreated());

        // Validate the Tbr_analise_resultado in the database
        List<Tbr_analise_resultado> tbr_analise_resultadoList = tbr_analise_resultadoRepository.findAll();
        assertThat(tbr_analise_resultadoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbr_analise_resultado() throws Exception {
        // Initialize the database
        tbr_analise_resultadoRepository.saveAndFlush(tbr_analise_resultado);
        tbr_analise_resultadoSearchRepository.save(tbr_analise_resultado);
        int databaseSizeBeforeDelete = tbr_analise_resultadoRepository.findAll().size();

        // Get the tbr_analise_resultado
        restTbr_analise_resultadoMockMvc.perform(delete("/api/tbr-analise-resultados/{id}", tbr_analise_resultado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbr_analise_resultadoExistsInEs = tbr_analise_resultadoSearchRepository.exists(tbr_analise_resultado.getId());
        assertThat(tbr_analise_resultadoExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbr_analise_resultado> tbr_analise_resultadoList = tbr_analise_resultadoRepository.findAll();
        assertThat(tbr_analise_resultadoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbr_analise_resultado() throws Exception {
        // Initialize the database
        tbr_analise_resultadoRepository.saveAndFlush(tbr_analise_resultado);
        tbr_analise_resultadoSearchRepository.save(tbr_analise_resultado);

        // Search the tbr_analise_resultado
        restTbr_analise_resultadoMockMvc.perform(get("/api/_search/tbr-analise-resultados?query=id:" + tbr_analise_resultado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbr_analise_resultado.getId().intValue())))
            .andExpect(jsonPath("$.[*].analises_componente").value(hasItem(DEFAULT_ANALISES_COMPONENTE.toString())))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO.toString())))
            .andExpect(jsonPath("$.[*].autorizado_por").value(hasItem(DEFAULT_AUTORIZADO_POR.toString())))
            .andExpect(jsonPath("$.[*].autorizado_data").value(hasItem(sameInstant(DEFAULT_AUTORIZADO_DATA))))
            .andExpect(jsonPath("$.[*].cancelado_por").value(hasItem(DEFAULT_CANCELADO_POR.toString())))
            .andExpect(jsonPath("$.[*].cancelado_data").value(hasItem(sameInstant(DEFAULT_CANCELADO_DATA))))
            .andExpect(jsonPath("$.[*].suspenso_por").value(hasItem(DEFAULT_SUSPENSO_POR.toString())))
            .andExpect(jsonPath("$.[*].suspenso_data").value(hasItem(sameInstant(DEFAULT_SUSPENSO_DATA))))
            .andExpect(jsonPath("$.[*].rejeitado_por").value(hasItem(DEFAULT_REJEITADO_POR.toString())))
            .andExpect(jsonPath("$.[*].rejeitado_data").value(hasItem(sameInstant(DEFAULT_REJEITADO_DATA))))
            .andExpect(jsonPath("$.[*].disponivel_por").value(hasItem(DEFAULT_DISPONIVEL_POR.toString())))
            .andExpect(jsonPath("$.[*].disponivel_data").value(hasItem(sameInstant(DEFAULT_DISPONIVEL_DATA))))
            .andExpect(jsonPath("$.[*].resultado_digitado").value(hasItem(DEFAULT_RESULTADO_DIGITADO.toString())))
            .andExpect(jsonPath("$.[*].unidade_medida").value(hasItem(DEFAULT_UNIDADE_MEDIDA.toString())))
            .andExpect(jsonPath("$.[*].tbr_analise_id").value(hasItem(DEFAULT_TBR_ANALISE_ID.intValue())))
            .andExpect(jsonPath("$.[*].resultado_texto").value(hasItem(DEFAULT_RESULTADO_TEXTO.toString())))
            .andExpect(jsonPath("$.[*].repetido").value(hasItem(DEFAULT_REPETIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].tbr_amostra_id").value(hasItem(DEFAULT_TBR_AMOSTRA_ID.intValue())))
            .andExpect(jsonPath("$.[*].completado_por").value(hasItem(DEFAULT_COMPLETADO_POR.toString())))
            .andExpect(jsonPath("$.[*].completado_data").value(hasItem(sameInstant(DEFAULT_COMPLETADO_DATA))));
    }
}
