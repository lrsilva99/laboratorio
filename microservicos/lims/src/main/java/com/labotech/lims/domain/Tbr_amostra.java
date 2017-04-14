package com.labotech.lims.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tbr_amostra.
 */
@Entity
@Table(name = "tbr_amostra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbr_amostra")
public class Tbr_amostra extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "urgencia")
    private Boolean urgencia;

    @Column(name = "proprietario")
    private String proprietario;

    @Column(name = "resp_colheita")
    private String resp_colheita;

    @Column(name = "req_texto")
    private String req_texto;

    @Lob
    @Column(name = "historico_clinico")
    private String historico_clinico;

    @Column(name = "numero_for")
    private String numero_for;

    @Column(name = "recebimento_rec_data")
    private ZonedDateTime recebimento_rec_data;

    @NotNull
    @Lob
    @Column(name = "suspeita_clinica", nullable = false)
    private String suspeita_clinica;

    @Column(name = "coleta_data")
    private ZonedDateTime coleta_data;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "raca")
    private String raca;

    @Column(name = "idade")
    private String idade;

    @Column(name = "identificao_amostra")
    private String identificao_amostra;

    @Column(name = "autorizado_por")
    private String autorizado_por;

    @Column(name = "autorizado_data")
    private ZonedDateTime autorizado_data;

    @Column(name = "cancelado_por")
    private String cancelado_por;

    @Column(name = "cancelado_data")
    private ZonedDateTime cancelado_data;

    @Column(name = "suspenso_por")
    private String suspenso_por;

    @Column(name = "suspenso_data")
    private ZonedDateTime suspenso_data;

    @Column(name = "rejeitado_por")
    private String rejeitado_por;

    @Column(name = "rejeitado_data")
    private ZonedDateTime rejeitado_data;

    @Column(name = "disponivel_por")
    private String disponivel_por;

    @Column(name = "disponivel_data")
    private ZonedDateTime disponivel_data;

    @Lob
    @Column(name = "plano_teste")
    private String plano_teste;

    @Column(name = "plano_teste_a")
    private String plano_teste_a;

    @Column(name = "plano_teste_b")
    private String plano_teste_b;

    @Column(name = "plano_teste_c")
    private String plano_teste_c;

    @Column(name = "plano_teste_d")
    private String plano_teste_d;

    @Column(name = "plano_teste_e")
    private String plano_teste_e;

    @Column(name = "plano_teste_f")
    private String plano_teste_f;

    @Column(name = "numero_etiqueta")
    private Integer numero_etiqueta;

    @ManyToOne
    private Tbc_especie tbc_especie;

    @ManyToOne
    private Tbc_sub_grupo tbc_sub_grupo;

    @ManyToOne
    private Tbc_status tbc_status;

    @ManyToOne
    @NotNull
    private Tbc_cliente tbc_cliente;

    @ManyToOne
    private Tbc_proprietario tbc_proprietario;

    @ManyToOne
    private Tbc_cooperativa tbc_cooperativa;

    @ManyToOne
    private Tbc_fazenda tbc_fazenda;

    @ManyToOne
    private Tbc_forma_armazenamento tbc_forma_armazenamento;

    @ManyToOne
    private Tbc_formulario tbc_formulario;

    @ManyToOne
    private Tbc_convenio tbc_convenio;

    @ManyToOne
    private Tbc_genero tbc_genero;

    @ManyToOne
    private Tbc_qualidade_amostra tbc_qualidade_amostra;

    @OneToMany(mappedBy = "tbr_amostra")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tbr_analise> tbr_analises = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isUrgencia() {
        return urgencia;
    }

    public Tbr_amostra urgencia(Boolean urgencia) {
        this.urgencia = urgencia;
        return this;
    }

    public void setUrgencia(Boolean urgencia) {
        this.urgencia = urgencia;
    }

    public String getProprietario() {
        return proprietario;
    }

    public Tbr_amostra proprietario(String proprietario) {
        this.proprietario = proprietario;
        return this;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getResp_colheita() {
        return resp_colheita;
    }

    public Tbr_amostra resp_colheita(String resp_colheita) {
        this.resp_colheita = resp_colheita;
        return this;
    }

    public void setResp_colheita(String resp_colheita) {
        this.resp_colheita = resp_colheita;
    }

    public String getReq_texto() {
        return req_texto;
    }

    public Tbr_amostra req_texto(String req_texto) {
        this.req_texto = req_texto;
        return this;
    }

    public void setReq_texto(String req_texto) {
        this.req_texto = req_texto;
    }

    public String getHistorico_clinico() {
        return historico_clinico;
    }

    public Tbr_amostra historico_clinico(String historico_clinico) {
        this.historico_clinico = historico_clinico;
        return this;
    }

    public void setHistorico_clinico(String historico_clinico) {
        this.historico_clinico = historico_clinico;
    }

    public String getNumero_for() {
        return numero_for;
    }

    public Tbr_amostra numero_for(String numero_for) {
        this.numero_for = numero_for;
        return this;
    }

    public void setNumero_for(String numero_for) {
        this.numero_for = numero_for;
    }

    public ZonedDateTime getRecebimento_rec_data() {
        return recebimento_rec_data;
    }

    public Tbr_amostra recebimento_rec_data(ZonedDateTime recebimento_rec_data) {
        this.recebimento_rec_data = recebimento_rec_data;
        return this;
    }

    public void setRecebimento_rec_data(ZonedDateTime recebimento_rec_data) {
        this.recebimento_rec_data = recebimento_rec_data;
    }

    public String getSuspeita_clinica() {
        return suspeita_clinica;
    }

    public Tbr_amostra suspeita_clinica(String suspeita_clinica) {
        this.suspeita_clinica = suspeita_clinica;
        return this;
    }

    public void setSuspeita_clinica(String suspeita_clinica) {
        this.suspeita_clinica = suspeita_clinica;
    }

    public ZonedDateTime getColeta_data() {
        return coleta_data;
    }

    public Tbr_amostra coleta_data(ZonedDateTime coleta_data) {
        this.coleta_data = coleta_data;
        return this;
    }

    public void setColeta_data(ZonedDateTime coleta_data) {
        this.coleta_data = coleta_data;
    }

    public String getSexo() {
        return sexo;
    }

    public Tbr_amostra sexo(String sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRaca() {
        return raca;
    }

    public Tbr_amostra raca(String raca) {
        this.raca = raca;
        return this;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getIdade() {
        return idade;
    }

    public Tbr_amostra idade(String idade) {
        this.idade = idade;
        return this;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getIdentificao_amostra() {
        return identificao_amostra;
    }

    public Tbr_amostra identificao_amostra(String identificao_amostra) {
        this.identificao_amostra = identificao_amostra;
        return this;
    }

    public void setIdentificao_amostra(String identificao_amostra) {
        this.identificao_amostra = identificao_amostra;
    }

    public String getAutorizado_por() {
        return autorizado_por;
    }

    public Tbr_amostra autorizado_por(String autorizado_por) {
        this.autorizado_por = autorizado_por;
        return this;
    }

    public void setAutorizado_por(String autorizado_por) {
        this.autorizado_por = autorizado_por;
    }

    public ZonedDateTime getAutorizado_data() {
        return autorizado_data;
    }

    public Tbr_amostra autorizado_data(ZonedDateTime autorizado_data) {
        this.autorizado_data = autorizado_data;
        return this;
    }

    public void setAutorizado_data(ZonedDateTime autorizado_data) {
        this.autorizado_data = autorizado_data;
    }

    public String getCancelado_por() {
        return cancelado_por;
    }

    public Tbr_amostra cancelado_por(String cancelado_por) {
        this.cancelado_por = cancelado_por;
        return this;
    }

    public void setCancelado_por(String cancelado_por) {
        this.cancelado_por = cancelado_por;
    }

    public ZonedDateTime getCancelado_data() {
        return cancelado_data;
    }

    public Tbr_amostra cancelado_data(ZonedDateTime cancelado_data) {
        this.cancelado_data = cancelado_data;
        return this;
    }

    public void setCancelado_data(ZonedDateTime cancelado_data) {
        this.cancelado_data = cancelado_data;
    }

    public String getSuspenso_por() {
        return suspenso_por;
    }

    public Tbr_amostra suspenso_por(String suspenso_por) {
        this.suspenso_por = suspenso_por;
        return this;
    }

    public void setSuspenso_por(String suspenso_por) {
        this.suspenso_por = suspenso_por;
    }

    public ZonedDateTime getSuspenso_data() {
        return suspenso_data;
    }

    public Tbr_amostra suspenso_data(ZonedDateTime suspenso_data) {
        this.suspenso_data = suspenso_data;
        return this;
    }

    public void setSuspenso_data(ZonedDateTime suspenso_data) {
        this.suspenso_data = suspenso_data;
    }

    public String getRejeitado_por() {
        return rejeitado_por;
    }

    public Tbr_amostra rejeitado_por(String rejeitado_por) {
        this.rejeitado_por = rejeitado_por;
        return this;
    }

    public void setRejeitado_por(String rejeitado_por) {
        this.rejeitado_por = rejeitado_por;
    }

    public ZonedDateTime getRejeitado_data() {
        return rejeitado_data;
    }

    public Tbr_amostra rejeitado_data(ZonedDateTime rejeitado_data) {
        this.rejeitado_data = rejeitado_data;
        return this;
    }

    public void setRejeitado_data(ZonedDateTime rejeitado_data) {
        this.rejeitado_data = rejeitado_data;
    }

    public String getDisponivel_por() {
        return disponivel_por;
    }

    public Tbr_amostra disponivel_por(String disponivel_por) {
        this.disponivel_por = disponivel_por;
        return this;
    }

    public void setDisponivel_por(String disponivel_por) {
        this.disponivel_por = disponivel_por;
    }

    public ZonedDateTime getDisponivel_data() {
        return disponivel_data;
    }

    public Tbr_amostra disponivel_data(ZonedDateTime disponivel_data) {
        this.disponivel_data = disponivel_data;
        return this;
    }

    public void setDisponivel_data(ZonedDateTime disponivel_data) {
        this.disponivel_data = disponivel_data;
    }

    public String getPlano_teste() {
        return plano_teste;
    }

    public Tbr_amostra plano_teste(String plano_teste) {
        this.plano_teste = plano_teste;
        return this;
    }

    public void setPlano_teste(String plano_teste) {
        this.plano_teste = plano_teste;
    }

    public String getPlano_teste_a() {
        return plano_teste_a;
    }

    public Tbr_amostra plano_teste_a(String plano_teste_a) {
        this.plano_teste_a = plano_teste_a;
        return this;
    }

    public void setPlano_teste_a(String plano_teste_a) {
        this.plano_teste_a = plano_teste_a;
    }

    public String getPlano_teste_b() {
        return plano_teste_b;
    }

    public Tbr_amostra plano_teste_b(String plano_teste_b) {
        this.plano_teste_b = plano_teste_b;
        return this;
    }

    public void setPlano_teste_b(String plano_teste_b) {
        this.plano_teste_b = plano_teste_b;
    }

    public String getPlano_teste_c() {
        return plano_teste_c;
    }

    public Tbr_amostra plano_teste_c(String plano_teste_c) {
        this.plano_teste_c = plano_teste_c;
        return this;
    }

    public void setPlano_teste_c(String plano_teste_c) {
        this.plano_teste_c = plano_teste_c;
    }

    public String getPlano_teste_d() {
        return plano_teste_d;
    }

    public Tbr_amostra plano_teste_d(String plano_teste_d) {
        this.plano_teste_d = plano_teste_d;
        return this;
    }

    public void setPlano_teste_d(String plano_teste_d) {
        this.plano_teste_d = plano_teste_d;
    }

    public String getPlano_teste_e() {
        return plano_teste_e;
    }

    public Tbr_amostra plano_teste_e(String plano_teste_e) {
        this.plano_teste_e = plano_teste_e;
        return this;
    }

    public void setPlano_teste_e(String plano_teste_e) {
        this.plano_teste_e = plano_teste_e;
    }

    public String getPlano_teste_f() {
        return plano_teste_f;
    }

    public Tbr_amostra plano_teste_f(String plano_teste_f) {
        this.plano_teste_f = plano_teste_f;
        return this;
    }

    public void setPlano_teste_f(String plano_teste_f) {
        this.plano_teste_f = plano_teste_f;
    }

    public Integer getNumero_etiqueta() {
        return numero_etiqueta;
    }

    public Tbr_amostra numero_etiqueta(Integer numero_etiqueta) {
        this.numero_etiqueta = numero_etiqueta;
        return this;
    }

    public void setNumero_etiqueta(Integer numero_etiqueta) {
        this.numero_etiqueta = numero_etiqueta;
    }

    public Tbc_especie getTbc_especie() {
        return tbc_especie;
    }

    public Tbr_amostra tbc_especie(Tbc_especie tbc_especie) {
        this.tbc_especie = tbc_especie;
        return this;
    }

    public void setTbc_especie(Tbc_especie tbc_especie) {
        this.tbc_especie = tbc_especie;
    }

    public Tbc_sub_grupo getTbc_sub_grupo() {
        return tbc_sub_grupo;
    }

    public Tbr_amostra tbc_sub_grupo(Tbc_sub_grupo tbc_sub_grupo) {
        this.tbc_sub_grupo = tbc_sub_grupo;
        return this;
    }

    public void setTbc_sub_grupo(Tbc_sub_grupo tbc_sub_grupo) {
        this.tbc_sub_grupo = tbc_sub_grupo;
    }

    public Tbc_status getTbc_status() {
        return tbc_status;
    }

    public Tbr_amostra tbc_status(Tbc_status tbc_status) {
        this.tbc_status = tbc_status;
        return this;
    }

    public void setTbc_status(Tbc_status tbc_status) {
        this.tbc_status = tbc_status;
    }

    public Tbc_cliente getTbc_cliente() {
        return tbc_cliente;
    }

    public Tbr_amostra tbc_cliente(Tbc_cliente tbc_cliente) {
        this.tbc_cliente = tbc_cliente;
        return this;
    }

    public void setTbc_cliente(Tbc_cliente tbc_cliente) {
        this.tbc_cliente = tbc_cliente;
    }

    public Tbc_proprietario getTbc_proprietario() {
        return tbc_proprietario;
    }

    public Tbr_amostra tbc_proprietario(Tbc_proprietario tbc_proprietario) {
        this.tbc_proprietario = tbc_proprietario;
        return this;
    }

    public void setTbc_proprietario(Tbc_proprietario tbc_proprietario) {
        this.tbc_proprietario = tbc_proprietario;
    }

    public Tbc_cooperativa getTbc_cooperativa() {
        return tbc_cooperativa;
    }

    public Tbr_amostra tbc_cooperativa(Tbc_cooperativa tbc_cooperativa) {
        this.tbc_cooperativa = tbc_cooperativa;
        return this;
    }

    public void setTbc_cooperativa(Tbc_cooperativa tbc_cooperativa) {
        this.tbc_cooperativa = tbc_cooperativa;
    }

    public Tbc_fazenda getTbc_fazenda() {
        return tbc_fazenda;
    }

    public Tbr_amostra tbc_fazenda(Tbc_fazenda tbc_fazenda) {
        this.tbc_fazenda = tbc_fazenda;
        return this;
    }

    public void setTbc_fazenda(Tbc_fazenda tbc_fazenda) {
        this.tbc_fazenda = tbc_fazenda;
    }

    public Tbc_forma_armazenamento getTbc_forma_armazenamento() {
        return tbc_forma_armazenamento;
    }

    public Tbr_amostra tbc_forma_armazenamento(Tbc_forma_armazenamento tbc_forma_armazenamento) {
        this.tbc_forma_armazenamento = tbc_forma_armazenamento;
        return this;
    }

    public void setTbc_forma_armazenamento(Tbc_forma_armazenamento tbc_forma_armazenamento) {
        this.tbc_forma_armazenamento = tbc_forma_armazenamento;
    }

    public Tbc_formulario getTbc_formulario() {
        return tbc_formulario;
    }

    public Tbr_amostra tbc_formulario(Tbc_formulario tbc_formulario) {
        this.tbc_formulario = tbc_formulario;
        return this;
    }

    public void setTbc_formulario(Tbc_formulario tbc_formulario) {
        this.tbc_formulario = tbc_formulario;
    }

    public Tbc_convenio getTbc_convenio() {
        return tbc_convenio;
    }

    public Tbr_amostra tbc_convenio(Tbc_convenio tbc_convenio) {
        this.tbc_convenio = tbc_convenio;
        return this;
    }

    public void setTbc_convenio(Tbc_convenio tbc_convenio) {
        this.tbc_convenio = tbc_convenio;
    }

    public Tbc_genero getTbc_genero() {
        return tbc_genero;
    }

    public Tbr_amostra tbc_genero(Tbc_genero tbc_genero) {
        this.tbc_genero = tbc_genero;
        return this;
    }

    public void setTbc_genero(Tbc_genero tbc_genero) {
        this.tbc_genero = tbc_genero;
    }

    public Tbc_qualidade_amostra getTbc_qualidade_amostra() {
        return tbc_qualidade_amostra;
    }

    public Tbr_amostra tbc_qualidade_amostra(Tbc_qualidade_amostra tbc_qualidade_amostra) {
        this.tbc_qualidade_amostra = tbc_qualidade_amostra;
        return this;
    }

    public void setTbc_qualidade_amostra(Tbc_qualidade_amostra tbc_qualidade_amostra) {
        this.tbc_qualidade_amostra = tbc_qualidade_amostra;
    }

    public Set<Tbr_analise> getTbr_analises() {
        return tbr_analises;
    }

    public Tbr_amostra tbr_analises(Set<Tbr_analise> tbr_analises) {
        this.tbr_analises = tbr_analises;
        return this;
    }

    public Tbr_amostra addTbr_analise(Tbr_analise tbr_analise) {
        tbr_analises.add(tbr_analise);
        tbr_analise.setTbr_amostra(this);
        return this;
    }

    public Tbr_amostra removeTbr_analise(Tbr_analise tbr_analise) {
        tbr_analises.remove(tbr_analise);
        tbr_analise.setTbr_amostra(null);
        return this;
    }

    public void setTbr_analises(Set<Tbr_analise> tbr_analises) {
        this.tbr_analises = tbr_analises;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tbr_amostra tbr_amostra = (Tbr_amostra) o;
        if (tbr_amostra.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tbr_amostra.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tbr_amostra{" +
            "id=" + id +
            ", urgencia='" + urgencia + "'" +
            ", proprietario='" + proprietario + "'" +
            ", resp_colheita='" + resp_colheita + "'" +
            ", req_texto='" + req_texto + "'" +
            ", historico_clinico='" + historico_clinico + "'" +
            ", numero_for='" + numero_for + "'" +
            ", recebimento_rec_data='" + recebimento_rec_data + "'" +
            ", suspeita_clinica='" + suspeita_clinica + "'" +
            ", coleta_data='" + coleta_data + "'" +
            ", sexo='" + sexo + "'" +
            ", raca='" + raca + "'" +
            ", idade='" + idade + "'" +
            ", identificao_amostra='" + identificao_amostra + "'" +
            ", autorizado_por='" + autorizado_por + "'" +
            ", autorizado_data='" + autorizado_data + "'" +
            ", cancelado_por='" + cancelado_por + "'" +
            ", cancelado_data='" + cancelado_data + "'" +
            ", suspenso_por='" + suspenso_por + "'" +
            ", suspenso_data='" + suspenso_data + "'" +
            ", rejeitado_por='" + rejeitado_por + "'" +
            ", rejeitado_data='" + rejeitado_data + "'" +
            ", disponivel_por='" + disponivel_por + "'" +
            ", disponivel_data='" + disponivel_data + "'" +
            ", plano_teste='" + plano_teste + "'" +
            ", plano_teste_a='" + plano_teste_a + "'" +
            ", plano_teste_b='" + plano_teste_b + "'" +
            ", plano_teste_c='" + plano_teste_c + "'" +
            ", plano_teste_d='" + plano_teste_d + "'" +
            ", plano_teste_e='" + plano_teste_e + "'" +
            ", plano_teste_f='" + plano_teste_f + "'" +
            ", numero_etiqueta='" + numero_etiqueta + "'" +
            '}';
    }
}
