package com.labotech.lims.web.rest;

import com.labotech.lims.LimsApp;

import com.labotech.lims.domain.Tbr_amostra;
import com.labotech.lims.domain.Tbc_cliente;
import com.labotech.lims.repository.Tbr_amostraRepository;
import com.labotech.lims.service.Tbr_amostraService;
import com.labotech.lims.repository.search.Tbr_amostraSearchRepository;

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
 * Test class for the Tbr_amostraResource REST controller.
 *
 * @see Tbr_amostraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimsApp.class)
public class Tbr_amostraResourceIntTest {

    private static final Boolean DEFAULT_URGENCIA = false;
    private static final Boolean UPDATED_URGENCIA = true;

    private static final String DEFAULT_PROPRIETARIO = "AAAAAAAAAA";
    private static final String UPDATED_PROPRIETARIO = "BBBBBBBBBB";

    private static final String DEFAULT_RESP_COLHEITA = "AAAAAAAAAA";
    private static final String UPDATED_RESP_COLHEITA = "BBBBBBBBBB";

    private static final String DEFAULT_REQ_TEXTO = "AAAAAAAAAA";
    private static final String UPDATED_REQ_TEXTO = "BBBBBBBBBB";

    private static final String DEFAULT_HISTORICO_CLINICO = "AAAAAAAAAA";
    private static final String UPDATED_HISTORICO_CLINICO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_FOR = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_FOR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_RECEBIMENTO_REC_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RECEBIMENTO_REC_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_SUSPEITA_CLINICA = "AAAAAAAAAA";
    private static final String UPDATED_SUSPEITA_CLINICA = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_COLETA_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COLETA_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final String DEFAULT_RACA = "AAAAAAAAAA";
    private static final String UPDATED_RACA = "BBBBBBBBBB";

    private static final String DEFAULT_IDADE = "AAAAAAAAAA";
    private static final String UPDATED_IDADE = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICAO_AMOSTRA = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICAO_AMOSTRA = "BBBBBBBBBB";

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

    private static final String DEFAULT_PLANO_TESTE = "AAAAAAAAAA";
    private static final String UPDATED_PLANO_TESTE = "BBBBBBBBBB";

    private static final String DEFAULT_PLANO_TESTE_A = "AAAAAAAAAA";
    private static final String UPDATED_PLANO_TESTE_A = "BBBBBBBBBB";

    private static final String DEFAULT_PLANO_TESTE_B = "AAAAAAAAAA";
    private static final String UPDATED_PLANO_TESTE_B = "BBBBBBBBBB";

    private static final String DEFAULT_PLANO_TESTE_C = "AAAAAAAAAA";
    private static final String UPDATED_PLANO_TESTE_C = "BBBBBBBBBB";

    private static final String DEFAULT_PLANO_TESTE_D = "AAAAAAAAAA";
    private static final String UPDATED_PLANO_TESTE_D = "BBBBBBBBBB";

    private static final String DEFAULT_PLANO_TESTE_E = "AAAAAAAAAA";
    private static final String UPDATED_PLANO_TESTE_E = "BBBBBBBBBB";

    private static final String DEFAULT_PLANO_TESTE_F = "AAAAAAAAAA";
    private static final String UPDATED_PLANO_TESTE_F = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_ETIQUETA = 1;
    private static final Integer UPDATED_NUMERO_ETIQUETA = 2;

    @Inject
    private Tbr_amostraRepository tbr_amostraRepository;

    @Inject
    private Tbr_amostraService tbr_amostraService;

    @Inject
    private Tbr_amostraSearchRepository tbr_amostraSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTbr_amostraMockMvc;

    private Tbr_amostra tbr_amostra;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tbr_amostraResource tbr_amostraResource = new Tbr_amostraResource();
        ReflectionTestUtils.setField(tbr_amostraResource, "tbr_amostraService", tbr_amostraService);
        this.restTbr_amostraMockMvc = MockMvcBuilders.standaloneSetup(tbr_amostraResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tbr_amostra createEntity(EntityManager em) {
        Tbr_amostra tbr_amostra = new Tbr_amostra()
                .urgencia(DEFAULT_URGENCIA)
                .proprietario(DEFAULT_PROPRIETARIO)
                .resp_colheita(DEFAULT_RESP_COLHEITA)
                .req_texto(DEFAULT_REQ_TEXTO)
                .historico_clinico(DEFAULT_HISTORICO_CLINICO)
                .numero_for(DEFAULT_NUMERO_FOR)
                .recebimento_rec_data(DEFAULT_RECEBIMENTO_REC_DATA)
                .suspeita_clinica(DEFAULT_SUSPEITA_CLINICA)
                .coleta_data(DEFAULT_COLETA_DATA)
                .sexo(DEFAULT_SEXO)
                .raca(DEFAULT_RACA)
                .idade(DEFAULT_IDADE)
                .identificao_amostra(DEFAULT_IDENTIFICAO_AMOSTRA)
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
                .plano_teste(DEFAULT_PLANO_TESTE)
                .plano_teste_a(DEFAULT_PLANO_TESTE_A)
                .plano_teste_b(DEFAULT_PLANO_TESTE_B)
                .plano_teste_c(DEFAULT_PLANO_TESTE_C)
                .plano_teste_d(DEFAULT_PLANO_TESTE_D)
                .plano_teste_e(DEFAULT_PLANO_TESTE_E)
                .plano_teste_f(DEFAULT_PLANO_TESTE_F)
                .numero_etiqueta(DEFAULT_NUMERO_ETIQUETA);
        // Add required entity
        Tbc_cliente tbc_cliente = Tbc_clienteResourceIntTest.createEntity(em);
        em.persist(tbc_cliente);
        em.flush();
        tbr_amostra.setTbc_cliente(tbc_cliente);
        return tbr_amostra;
    }

    @Before
    public void initTest() {
        tbr_amostraSearchRepository.deleteAll();
        tbr_amostra = createEntity(em);
    }

    @Test
    @Transactional
    public void createTbr_amostra() throws Exception {
        int databaseSizeBeforeCreate = tbr_amostraRepository.findAll().size();

        // Create the Tbr_amostra

        restTbr_amostraMockMvc.perform(post("/api/tbr-amostras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbr_amostra)))
            .andExpect(status().isCreated());

        // Validate the Tbr_amostra in the database
        List<Tbr_amostra> tbr_amostraList = tbr_amostraRepository.findAll();
        assertThat(tbr_amostraList).hasSize(databaseSizeBeforeCreate + 1);
        Tbr_amostra testTbr_amostra = tbr_amostraList.get(tbr_amostraList.size() - 1);
        assertThat(testTbr_amostra.isUrgencia()).isEqualTo(DEFAULT_URGENCIA);
        assertThat(testTbr_amostra.getProprietario()).isEqualTo(DEFAULT_PROPRIETARIO);
        assertThat(testTbr_amostra.getResp_colheita()).isEqualTo(DEFAULT_RESP_COLHEITA);
        assertThat(testTbr_amostra.getReq_texto()).isEqualTo(DEFAULT_REQ_TEXTO);
        assertThat(testTbr_amostra.getHistorico_clinico()).isEqualTo(DEFAULT_HISTORICO_CLINICO);
        assertThat(testTbr_amostra.getNumero_for()).isEqualTo(DEFAULT_NUMERO_FOR);
        assertThat(testTbr_amostra.getRecebimento_rec_data()).isEqualTo(DEFAULT_RECEBIMENTO_REC_DATA);
        assertThat(testTbr_amostra.getSuspeita_clinica()).isEqualTo(DEFAULT_SUSPEITA_CLINICA);
        assertThat(testTbr_amostra.getColeta_data()).isEqualTo(DEFAULT_COLETA_DATA);
        assertThat(testTbr_amostra.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testTbr_amostra.getRaca()).isEqualTo(DEFAULT_RACA);
        assertThat(testTbr_amostra.getIdade()).isEqualTo(DEFAULT_IDADE);
        assertThat(testTbr_amostra.getIdentificao_amostra()).isEqualTo(DEFAULT_IDENTIFICAO_AMOSTRA);
        assertThat(testTbr_amostra.getAutorizado_por()).isEqualTo(DEFAULT_AUTORIZADO_POR);
        assertThat(testTbr_amostra.getAutorizado_data()).isEqualTo(DEFAULT_AUTORIZADO_DATA);
        assertThat(testTbr_amostra.getCancelado_por()).isEqualTo(DEFAULT_CANCELADO_POR);
        assertThat(testTbr_amostra.getCancelado_data()).isEqualTo(DEFAULT_CANCELADO_DATA);
        assertThat(testTbr_amostra.getSuspenso_por()).isEqualTo(DEFAULT_SUSPENSO_POR);
        assertThat(testTbr_amostra.getSuspenso_data()).isEqualTo(DEFAULT_SUSPENSO_DATA);
        assertThat(testTbr_amostra.getRejeitado_por()).isEqualTo(DEFAULT_REJEITADO_POR);
        assertThat(testTbr_amostra.getRejeitado_data()).isEqualTo(DEFAULT_REJEITADO_DATA);
        assertThat(testTbr_amostra.getDisponivel_por()).isEqualTo(DEFAULT_DISPONIVEL_POR);
        assertThat(testTbr_amostra.getDisponivel_data()).isEqualTo(DEFAULT_DISPONIVEL_DATA);
        assertThat(testTbr_amostra.getPlano_teste()).isEqualTo(DEFAULT_PLANO_TESTE);
        assertThat(testTbr_amostra.getPlano_teste_a()).isEqualTo(DEFAULT_PLANO_TESTE_A);
        assertThat(testTbr_amostra.getPlano_teste_b()).isEqualTo(DEFAULT_PLANO_TESTE_B);
        assertThat(testTbr_amostra.getPlano_teste_c()).isEqualTo(DEFAULT_PLANO_TESTE_C);
        assertThat(testTbr_amostra.getPlano_teste_d()).isEqualTo(DEFAULT_PLANO_TESTE_D);
        assertThat(testTbr_amostra.getPlano_teste_e()).isEqualTo(DEFAULT_PLANO_TESTE_E);
        assertThat(testTbr_amostra.getPlano_teste_f()).isEqualTo(DEFAULT_PLANO_TESTE_F);
        assertThat(testTbr_amostra.getNumero_etiqueta()).isEqualTo(DEFAULT_NUMERO_ETIQUETA);

        // Validate the Tbr_amostra in ElasticSearch
        Tbr_amostra tbr_amostraEs = tbr_amostraSearchRepository.findOne(testTbr_amostra.getId());
        assertThat(tbr_amostraEs).isEqualToComparingFieldByField(testTbr_amostra);
    }

    @Test
    @Transactional
    public void createTbr_amostraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tbr_amostraRepository.findAll().size();

        // Create the Tbr_amostra with an existing ID
        Tbr_amostra existingTbr_amostra = new Tbr_amostra();
        existingTbr_amostra.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbr_amostraMockMvc.perform(post("/api/tbr-amostras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTbr_amostra)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tbr_amostra> tbr_amostraList = tbr_amostraRepository.findAll();
        assertThat(tbr_amostraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSuspeita_clinicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tbr_amostraRepository.findAll().size();
        // set the field null
        tbr_amostra.setSuspeita_clinica(null);

        // Create the Tbr_amostra, which fails.

        restTbr_amostraMockMvc.perform(post("/api/tbr-amostras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbr_amostra)))
            .andExpect(status().isBadRequest());

        List<Tbr_amostra> tbr_amostraList = tbr_amostraRepository.findAll();
        assertThat(tbr_amostraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTbr_amostras() throws Exception {
        // Initialize the database
        tbr_amostraRepository.saveAndFlush(tbr_amostra);

        // Get all the tbr_amostraList
        restTbr_amostraMockMvc.perform(get("/api/tbr-amostras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbr_amostra.getId().intValue())))
            .andExpect(jsonPath("$.[*].urgencia").value(hasItem(DEFAULT_URGENCIA.booleanValue())))
            .andExpect(jsonPath("$.[*].proprietario").value(hasItem(DEFAULT_PROPRIETARIO.toString())))
            .andExpect(jsonPath("$.[*].resp_colheita").value(hasItem(DEFAULT_RESP_COLHEITA.toString())))
            .andExpect(jsonPath("$.[*].req_texto").value(hasItem(DEFAULT_REQ_TEXTO.toString())))
            .andExpect(jsonPath("$.[*].historico_clinico").value(hasItem(DEFAULT_HISTORICO_CLINICO.toString())))
            .andExpect(jsonPath("$.[*].numero_for").value(hasItem(DEFAULT_NUMERO_FOR.toString())))
            .andExpect(jsonPath("$.[*].recebimento_rec_data").value(hasItem(sameInstant(DEFAULT_RECEBIMENTO_REC_DATA))))
            .andExpect(jsonPath("$.[*].suspeita_clinica").value(hasItem(DEFAULT_SUSPEITA_CLINICA.toString())))
            .andExpect(jsonPath("$.[*].coleta_data").value(hasItem(sameInstant(DEFAULT_COLETA_DATA))))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].raca").value(hasItem(DEFAULT_RACA.toString())))
            .andExpect(jsonPath("$.[*].idade").value(hasItem(DEFAULT_IDADE.toString())))
            .andExpect(jsonPath("$.[*].identificao_amostra").value(hasItem(DEFAULT_IDENTIFICAO_AMOSTRA.toString())))
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
            .andExpect(jsonPath("$.[*].plano_teste").value(hasItem(DEFAULT_PLANO_TESTE.toString())))
            .andExpect(jsonPath("$.[*].plano_teste_a").value(hasItem(DEFAULT_PLANO_TESTE_A.toString())))
            .andExpect(jsonPath("$.[*].plano_teste_b").value(hasItem(DEFAULT_PLANO_TESTE_B.toString())))
            .andExpect(jsonPath("$.[*].plano_teste_c").value(hasItem(DEFAULT_PLANO_TESTE_C.toString())))
            .andExpect(jsonPath("$.[*].plano_teste_d").value(hasItem(DEFAULT_PLANO_TESTE_D.toString())))
            .andExpect(jsonPath("$.[*].plano_teste_e").value(hasItem(DEFAULT_PLANO_TESTE_E.toString())))
            .andExpect(jsonPath("$.[*].plano_teste_f").value(hasItem(DEFAULT_PLANO_TESTE_F.toString())))
            .andExpect(jsonPath("$.[*].numero_etiqueta").value(hasItem(DEFAULT_NUMERO_ETIQUETA)));
    }

    @Test
    @Transactional
    public void getTbr_amostra() throws Exception {
        // Initialize the database
        tbr_amostraRepository.saveAndFlush(tbr_amostra);

        // Get the tbr_amostra
        restTbr_amostraMockMvc.perform(get("/api/tbr-amostras/{id}", tbr_amostra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tbr_amostra.getId().intValue()))
            .andExpect(jsonPath("$.urgencia").value(DEFAULT_URGENCIA.booleanValue()))
            .andExpect(jsonPath("$.proprietario").value(DEFAULT_PROPRIETARIO.toString()))
            .andExpect(jsonPath("$.resp_colheita").value(DEFAULT_RESP_COLHEITA.toString()))
            .andExpect(jsonPath("$.req_texto").value(DEFAULT_REQ_TEXTO.toString()))
            .andExpect(jsonPath("$.historico_clinico").value(DEFAULT_HISTORICO_CLINICO.toString()))
            .andExpect(jsonPath("$.numero_for").value(DEFAULT_NUMERO_FOR.toString()))
            .andExpect(jsonPath("$.recebimento_rec_data").value(sameInstant(DEFAULT_RECEBIMENTO_REC_DATA)))
            .andExpect(jsonPath("$.suspeita_clinica").value(DEFAULT_SUSPEITA_CLINICA.toString()))
            .andExpect(jsonPath("$.coleta_data").value(sameInstant(DEFAULT_COLETA_DATA)))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.raca").value(DEFAULT_RACA.toString()))
            .andExpect(jsonPath("$.idade").value(DEFAULT_IDADE.toString()))
            .andExpect(jsonPath("$.identificao_amostra").value(DEFAULT_IDENTIFICAO_AMOSTRA.toString()))
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
            .andExpect(jsonPath("$.plano_teste").value(DEFAULT_PLANO_TESTE.toString()))
            .andExpect(jsonPath("$.plano_teste_a").value(DEFAULT_PLANO_TESTE_A.toString()))
            .andExpect(jsonPath("$.plano_teste_b").value(DEFAULT_PLANO_TESTE_B.toString()))
            .andExpect(jsonPath("$.plano_teste_c").value(DEFAULT_PLANO_TESTE_C.toString()))
            .andExpect(jsonPath("$.plano_teste_d").value(DEFAULT_PLANO_TESTE_D.toString()))
            .andExpect(jsonPath("$.plano_teste_e").value(DEFAULT_PLANO_TESTE_E.toString()))
            .andExpect(jsonPath("$.plano_teste_f").value(DEFAULT_PLANO_TESTE_F.toString()))
            .andExpect(jsonPath("$.numero_etiqueta").value(DEFAULT_NUMERO_ETIQUETA));
    }

    @Test
    @Transactional
    public void getNonExistingTbr_amostra() throws Exception {
        // Get the tbr_amostra
        restTbr_amostraMockMvc.perform(get("/api/tbr-amostras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTbr_amostra() throws Exception {
        // Initialize the database
        tbr_amostraService.save(tbr_amostra);

        int databaseSizeBeforeUpdate = tbr_amostraRepository.findAll().size();

        // Update the tbr_amostra
        Tbr_amostra updatedTbr_amostra = tbr_amostraRepository.findOne(tbr_amostra.getId());
        updatedTbr_amostra
                .urgencia(UPDATED_URGENCIA)
                .proprietario(UPDATED_PROPRIETARIO)
                .resp_colheita(UPDATED_RESP_COLHEITA)
                .req_texto(UPDATED_REQ_TEXTO)
                .historico_clinico(UPDATED_HISTORICO_CLINICO)
                .numero_for(UPDATED_NUMERO_FOR)
                .recebimento_rec_data(UPDATED_RECEBIMENTO_REC_DATA)
                .suspeita_clinica(UPDATED_SUSPEITA_CLINICA)
                .coleta_data(UPDATED_COLETA_DATA)
                .sexo(UPDATED_SEXO)
                .raca(UPDATED_RACA)
                .idade(UPDATED_IDADE)
                .identificao_amostra(UPDATED_IDENTIFICAO_AMOSTRA)
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
                .plano_teste(UPDATED_PLANO_TESTE)
                .plano_teste_a(UPDATED_PLANO_TESTE_A)
                .plano_teste_b(UPDATED_PLANO_TESTE_B)
                .plano_teste_c(UPDATED_PLANO_TESTE_C)
                .plano_teste_d(UPDATED_PLANO_TESTE_D)
                .plano_teste_e(UPDATED_PLANO_TESTE_E)
                .plano_teste_f(UPDATED_PLANO_TESTE_F)
                .numero_etiqueta(UPDATED_NUMERO_ETIQUETA);

        restTbr_amostraMockMvc.perform(put("/api/tbr-amostras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTbr_amostra)))
            .andExpect(status().isOk());

        // Validate the Tbr_amostra in the database
        List<Tbr_amostra> tbr_amostraList = tbr_amostraRepository.findAll();
        assertThat(tbr_amostraList).hasSize(databaseSizeBeforeUpdate);
        Tbr_amostra testTbr_amostra = tbr_amostraList.get(tbr_amostraList.size() - 1);
        assertThat(testTbr_amostra.isUrgencia()).isEqualTo(UPDATED_URGENCIA);
        assertThat(testTbr_amostra.getProprietario()).isEqualTo(UPDATED_PROPRIETARIO);
        assertThat(testTbr_amostra.getResp_colheita()).isEqualTo(UPDATED_RESP_COLHEITA);
        assertThat(testTbr_amostra.getReq_texto()).isEqualTo(UPDATED_REQ_TEXTO);
        assertThat(testTbr_amostra.getHistorico_clinico()).isEqualTo(UPDATED_HISTORICO_CLINICO);
        assertThat(testTbr_amostra.getNumero_for()).isEqualTo(UPDATED_NUMERO_FOR);
        assertThat(testTbr_amostra.getRecebimento_rec_data()).isEqualTo(UPDATED_RECEBIMENTO_REC_DATA);
        assertThat(testTbr_amostra.getSuspeita_clinica()).isEqualTo(UPDATED_SUSPEITA_CLINICA);
        assertThat(testTbr_amostra.getColeta_data()).isEqualTo(UPDATED_COLETA_DATA);
        assertThat(testTbr_amostra.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testTbr_amostra.getRaca()).isEqualTo(UPDATED_RACA);
        assertThat(testTbr_amostra.getIdade()).isEqualTo(UPDATED_IDADE);
        assertThat(testTbr_amostra.getIdentificao_amostra()).isEqualTo(UPDATED_IDENTIFICAO_AMOSTRA);
        assertThat(testTbr_amostra.getAutorizado_por()).isEqualTo(UPDATED_AUTORIZADO_POR);
        assertThat(testTbr_amostra.getAutorizado_data()).isEqualTo(UPDATED_AUTORIZADO_DATA);
        assertThat(testTbr_amostra.getCancelado_por()).isEqualTo(UPDATED_CANCELADO_POR);
        assertThat(testTbr_amostra.getCancelado_data()).isEqualTo(UPDATED_CANCELADO_DATA);
        assertThat(testTbr_amostra.getSuspenso_por()).isEqualTo(UPDATED_SUSPENSO_POR);
        assertThat(testTbr_amostra.getSuspenso_data()).isEqualTo(UPDATED_SUSPENSO_DATA);
        assertThat(testTbr_amostra.getRejeitado_por()).isEqualTo(UPDATED_REJEITADO_POR);
        assertThat(testTbr_amostra.getRejeitado_data()).isEqualTo(UPDATED_REJEITADO_DATA);
        assertThat(testTbr_amostra.getDisponivel_por()).isEqualTo(UPDATED_DISPONIVEL_POR);
        assertThat(testTbr_amostra.getDisponivel_data()).isEqualTo(UPDATED_DISPONIVEL_DATA);
        assertThat(testTbr_amostra.getPlano_teste()).isEqualTo(UPDATED_PLANO_TESTE);
        assertThat(testTbr_amostra.getPlano_teste_a()).isEqualTo(UPDATED_PLANO_TESTE_A);
        assertThat(testTbr_amostra.getPlano_teste_b()).isEqualTo(UPDATED_PLANO_TESTE_B);
        assertThat(testTbr_amostra.getPlano_teste_c()).isEqualTo(UPDATED_PLANO_TESTE_C);
        assertThat(testTbr_amostra.getPlano_teste_d()).isEqualTo(UPDATED_PLANO_TESTE_D);
        assertThat(testTbr_amostra.getPlano_teste_e()).isEqualTo(UPDATED_PLANO_TESTE_E);
        assertThat(testTbr_amostra.getPlano_teste_f()).isEqualTo(UPDATED_PLANO_TESTE_F);
        assertThat(testTbr_amostra.getNumero_etiqueta()).isEqualTo(UPDATED_NUMERO_ETIQUETA);

        // Validate the Tbr_amostra in ElasticSearch
        Tbr_amostra tbr_amostraEs = tbr_amostraSearchRepository.findOne(testTbr_amostra.getId());
        assertThat(tbr_amostraEs).isEqualToComparingFieldByField(testTbr_amostra);
    }

    @Test
    @Transactional
    public void updateNonExistingTbr_amostra() throws Exception {
        int databaseSizeBeforeUpdate = tbr_amostraRepository.findAll().size();

        // Create the Tbr_amostra

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTbr_amostraMockMvc.perform(put("/api/tbr-amostras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tbr_amostra)))
            .andExpect(status().isCreated());

        // Validate the Tbr_amostra in the database
        List<Tbr_amostra> tbr_amostraList = tbr_amostraRepository.findAll();
        assertThat(tbr_amostraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTbr_amostra() throws Exception {
        // Initialize the database
        tbr_amostraService.save(tbr_amostra);

        int databaseSizeBeforeDelete = tbr_amostraRepository.findAll().size();

        // Get the tbr_amostra
        restTbr_amostraMockMvc.perform(delete("/api/tbr-amostras/{id}", tbr_amostra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean tbr_amostraExistsInEs = tbr_amostraSearchRepository.exists(tbr_amostra.getId());
        assertThat(tbr_amostraExistsInEs).isFalse();

        // Validate the database is empty
        List<Tbr_amostra> tbr_amostraList = tbr_amostraRepository.findAll();
        assertThat(tbr_amostraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTbr_amostra() throws Exception {
        // Initialize the database
        tbr_amostraService.save(tbr_amostra);

        // Search the tbr_amostra
        restTbr_amostraMockMvc.perform(get("/api/_search/tbr-amostras?query=id:" + tbr_amostra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbr_amostra.getId().intValue())))
            .andExpect(jsonPath("$.[*].urgencia").value(hasItem(DEFAULT_URGENCIA.booleanValue())))
            .andExpect(jsonPath("$.[*].proprietario").value(hasItem(DEFAULT_PROPRIETARIO.toString())))
            .andExpect(jsonPath("$.[*].resp_colheita").value(hasItem(DEFAULT_RESP_COLHEITA.toString())))
            .andExpect(jsonPath("$.[*].req_texto").value(hasItem(DEFAULT_REQ_TEXTO.toString())))
            .andExpect(jsonPath("$.[*].historico_clinico").value(hasItem(DEFAULT_HISTORICO_CLINICO.toString())))
            .andExpect(jsonPath("$.[*].numero_for").value(hasItem(DEFAULT_NUMERO_FOR.toString())))
            .andExpect(jsonPath("$.[*].recebimento_rec_data").value(hasItem(sameInstant(DEFAULT_RECEBIMENTO_REC_DATA))))
            .andExpect(jsonPath("$.[*].suspeita_clinica").value(hasItem(DEFAULT_SUSPEITA_CLINICA.toString())))
            .andExpect(jsonPath("$.[*].coleta_data").value(hasItem(sameInstant(DEFAULT_COLETA_DATA))))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].raca").value(hasItem(DEFAULT_RACA.toString())))
            .andExpect(jsonPath("$.[*].idade").value(hasItem(DEFAULT_IDADE.toString())))
            .andExpect(jsonPath("$.[*].identificao_amostra").value(hasItem(DEFAULT_IDENTIFICAO_AMOSTRA.toString())))
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
            .andExpect(jsonPath("$.[*].plano_teste").value(hasItem(DEFAULT_PLANO_TESTE.toString())))
            .andExpect(jsonPath("$.[*].plano_teste_a").value(hasItem(DEFAULT_PLANO_TESTE_A.toString())))
            .andExpect(jsonPath("$.[*].plano_teste_b").value(hasItem(DEFAULT_PLANO_TESTE_B.toString())))
            .andExpect(jsonPath("$.[*].plano_teste_c").value(hasItem(DEFAULT_PLANO_TESTE_C.toString())))
            .andExpect(jsonPath("$.[*].plano_teste_d").value(hasItem(DEFAULT_PLANO_TESTE_D.toString())))
            .andExpect(jsonPath("$.[*].plano_teste_e").value(hasItem(DEFAULT_PLANO_TESTE_E.toString())))
            .andExpect(jsonPath("$.[*].plano_teste_f").value(hasItem(DEFAULT_PLANO_TESTE_F.toString())))
            .andExpect(jsonPath("$.[*].numero_etiqueta").value(hasItem(DEFAULT_NUMERO_ETIQUETA)));
    }
}
