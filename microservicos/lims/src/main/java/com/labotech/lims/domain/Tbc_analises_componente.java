package com.labotech.lims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Tbc_analises_componente.
 */
@Entity
@Table(name = "tbc_analises_componente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbc_analises_componente")
public class Tbc_analises_componente extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "linha")
    private Integer linha;

    @Column(name = "unidade_medida")
    private String unidade_medida;

    @Lob
    @Column(name = "valor_padrao")
    private String valor_padrao;

    @Lob
    @Column(name = "configuracao")
    private String configuracao;

    @ManyToOne
    @NotNull
    private Tbc_instituicao tbc_instituicao;

    @ManyToOne
    @NotNull
    private Tbc_tipo_campo tbc_tipo_campo;

    @ManyToOne
    @NotNull
    private Tbc_analises tbc_analises;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Tbc_analises_componente nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Tbc_analises_componente descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getLinha() {
        return linha;
    }

    public Tbc_analises_componente linha(Integer linha) {
        this.linha = linha;
        return this;
    }

    public void setLinha(Integer linha) {
        this.linha = linha;
    }

    public String getUnidade_medida() {
        return unidade_medida;
    }

    public Tbc_analises_componente unidade_medida(String unidade_medida) {
        this.unidade_medida = unidade_medida;
        return this;
    }

    public void setUnidade_medida(String unidade_medida) {
        this.unidade_medida = unidade_medida;
    }

    public String getValor_padrao() {
        return valor_padrao;
    }

    public Tbc_analises_componente valor_padrao(String valor_padrao) {
        this.valor_padrao = valor_padrao;
        return this;
    }

    public void setValor_padrao(String valor_padrao) {
        this.valor_padrao = valor_padrao;
    }

    public String getConfiguracao() {
        return configuracao;
    }

    public Tbc_analises_componente configuracao(String configuracao) {
        this.configuracao = configuracao;
        return this;
    }

    public void setConfiguracao(String configuracao) {
        this.configuracao = configuracao;
    }

    public Tbc_instituicao getTbc_instituicao() {
        return tbc_instituicao;
    }

    public Tbc_analises_componente tbc_instituicao(Tbc_instituicao tbc_instituicao) {
        this.tbc_instituicao = tbc_instituicao;
        return this;
    }

    public void setTbc_instituicao(Tbc_instituicao tbc_instituicao) {
        this.tbc_instituicao = tbc_instituicao;
    }

    public Tbc_tipo_campo getTbc_tipo_campo() {
        return tbc_tipo_campo;
    }

    public Tbc_analises_componente tbc_tipo_campo(Tbc_tipo_campo tbc_tipo_campo) {
        this.tbc_tipo_campo = tbc_tipo_campo;
        return this;
    }

    public void setTbc_tipo_campo(Tbc_tipo_campo tbc_tipo_campo) {
        this.tbc_tipo_campo = tbc_tipo_campo;
    }

    public Tbc_analises getTbc_analises() {
        return tbc_analises;
    }

    public Tbc_analises_componente tbc_analises(Tbc_analises tbc_analises) {
        this.tbc_analises = tbc_analises;
        return this;
    }

    public void setTbc_analises(Tbc_analises tbc_analises) {
        this.tbc_analises = tbc_analises;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tbc_analises_componente tbc_analises_componente = (Tbc_analises_componente) o;
        if (tbc_analises_componente.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tbc_analises_componente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tbc_analises_componente{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", descricao='" + descricao + "'" +
            ", linha='" + linha + "'" +
            ", unidade_medida='" + unidade_medida + "'" +
            ", valor_padrao='" + valor_padrao + "'" +
            ", configuracao='" + configuracao + "'" +
            '}';
    }
}
