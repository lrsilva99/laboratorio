package com.labotech.lims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Tbc_analises.
 */
@Entity
@Table(name = "tbc_analises")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbc_analises")
public class Tbc_analises extends AbstractAuditingEntity implements Serializable {

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

    @NotNull
    @Column(name = "numerodias", nullable = false)
    private Integer numerodias;

    @NotNull
    @Column(name = "metpop", nullable = false)
    private String metpop;

    @Column(name = "removido")
    private Boolean removido;

    @Column(name = "tercerizado")
    private Boolean tercerizado;

    @Column(name = "directiva_data_atu")
    private ZonedDateTime directiva_data_atu;

    @ManyToOne
    @NotNull
    private Tbc_sub_grupo tbc_sub_grupo;

    @ManyToOne
    @NotNull
    private Tbc_grupo_analise tbc_grupo_analise;

    @ManyToOne
    @NotNull
    private Tbc_tipo_cadastro tbc_tipo_cadastro;

    @ManyToOne
    @NotNull
    private Tbc_instituicao tbc_instituicao;

    @ManyToOne
    private Tbc_report tbc_report;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Tbc_analises nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Tbc_analises descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getNumerodias() {
        return numerodias;
    }

    public Tbc_analises numerodias(Integer numerodias) {
        this.numerodias = numerodias;
        return this;
    }

    public void setNumerodias(Integer numerodias) {
        this.numerodias = numerodias;
    }

    public String getMetpop() {
        return metpop;
    }

    public Tbc_analises metpop(String metpop) {
        this.metpop = metpop;
        return this;
    }

    public void setMetpop(String metpop) {
        this.metpop = metpop;
    }

    public Boolean isRemovido() {
        return removido;
    }

    public Tbc_analises removido(Boolean removido) {
        this.removido = removido;
        return this;
    }

    public void setRemovido(Boolean removido) {
        this.removido = removido;
    }

    public Boolean isTercerizado() {
        return tercerizado;
    }

    public Tbc_analises tercerizado(Boolean tercerizado) {
        this.tercerizado = tercerizado;
        return this;
    }

    public void setTercerizado(Boolean tercerizado) {
        this.tercerizado = tercerizado;
    }

    public ZonedDateTime getDirectiva_data_atu() {
        return directiva_data_atu;
    }

    public Tbc_analises directiva_data_atu(ZonedDateTime directiva_data_atu) {
        this.directiva_data_atu = directiva_data_atu;
        return this;
    }

    public void setDirectiva_data_atu(ZonedDateTime directiva_data_atu) {
        this.directiva_data_atu = directiva_data_atu;
    }

    public Tbc_sub_grupo getTbc_sub_grupo() {
        return tbc_sub_grupo;
    }

    public Tbc_analises tbc_sub_grupo(Tbc_sub_grupo tbc_sub_grupo) {
        this.tbc_sub_grupo = tbc_sub_grupo;
        return this;
    }

    public void setTbc_sub_grupo(Tbc_sub_grupo tbc_sub_grupo) {
        this.tbc_sub_grupo = tbc_sub_grupo;
    }

    public Tbc_grupo_analise getTbc_grupo_analise() {
        return tbc_grupo_analise;
    }

    public Tbc_analises tbc_grupo_analise(Tbc_grupo_analise tbc_grupo_analise) {
        this.tbc_grupo_analise = tbc_grupo_analise;
        return this;
    }

    public void setTbc_grupo_analise(Tbc_grupo_analise tbc_grupo_analise) {
        this.tbc_grupo_analise = tbc_grupo_analise;
    }

    public Tbc_tipo_cadastro getTbc_tipo_cadastro() {
        return tbc_tipo_cadastro;
    }

    public Tbc_analises tbc_tipo_cadastro(Tbc_tipo_cadastro tbc_tipo_cadastro) {
        this.tbc_tipo_cadastro = tbc_tipo_cadastro;
        return this;
    }

    public void setTbc_tipo_cadastro(Tbc_tipo_cadastro tbc_tipo_cadastro) {
        this.tbc_tipo_cadastro = tbc_tipo_cadastro;
    }

    public Tbc_instituicao getTbc_instituicao() {
        return tbc_instituicao;
    }

    public Tbc_analises tbc_instituicao(Tbc_instituicao tbc_instituicao) {
        this.tbc_instituicao = tbc_instituicao;
        return this;
    }

    public void setTbc_instituicao(Tbc_instituicao tbc_instituicao) {
        this.tbc_instituicao = tbc_instituicao;
    }

    public Tbc_report getTbc_report() {
        return tbc_report;
    }

    public Tbc_analises tbc_report(Tbc_report tbc_report) {
        this.tbc_report = tbc_report;
        return this;
    }

    public void setTbc_report(Tbc_report tbc_report) {
        this.tbc_report = tbc_report;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tbc_analises tbc_analises = (Tbc_analises) o;
        if (tbc_analises.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tbc_analises.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tbc_analises{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", descricao='" + descricao + "'" +
            ", numerodias='" + numerodias + "'" +
            ", metpop='" + metpop + "'" +
            ", removido='" + removido + "'" +
            ", tercerizado='" + tercerizado + "'" +
            ", directiva_data_atu='" + directiva_data_atu + "'" +
            '}';
    }
}
