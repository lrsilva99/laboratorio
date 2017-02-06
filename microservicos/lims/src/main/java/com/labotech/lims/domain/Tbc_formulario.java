package com.labotech.lims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Tbc_formulario.
 */
@Entity
@Table(name = "tbc_formulario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbc_formulario")
public class Tbc_formulario extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "metodo", nullable = false)
    private String metodo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "diasliberacao")
    private Integer diasliberacao;

    @Column(name = "removido")
    private Boolean removido;

    @ManyToOne
    @NotNull
    private Tbc_instituicao tbc_instituicao;

    @ManyToOne
    @NotNull
    private Tbc_sub_grupo tbc_sub_grupo;

    @ManyToOne
    @NotNull
    private Tbc_grupo_analise tbc_grupo_analise;

    @ManyToOne
    private Tbc_tipo_cadastro tbc_tipo_cadastro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Tbc_formulario nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMetodo() {
        return metodo;
    }

    public Tbc_formulario metodo(String metodo) {
        this.metodo = metodo;
        return this;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Tbc_formulario descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDiasliberacao() {
        return diasliberacao;
    }

    public Tbc_formulario diasliberacao(Integer diasliberacao) {
        this.diasliberacao = diasliberacao;
        return this;
    }

    public void setDiasliberacao(Integer diasliberacao) {
        this.diasliberacao = diasliberacao;
    }

    public Boolean isRemovido() {
        return removido;
    }

    public Tbc_formulario removido(Boolean removido) {
        this.removido = removido;
        return this;
    }

    public void setRemovido(Boolean removido) {
        this.removido = removido;
    }

    public Tbc_instituicao getTbc_instituicao() {
        return tbc_instituicao;
    }

    public Tbc_formulario tbc_instituicao(Tbc_instituicao tbc_instituicao) {
        this.tbc_instituicao = tbc_instituicao;
        return this;
    }

    public void setTbc_instituicao(Tbc_instituicao tbc_instituicao) {
        this.tbc_instituicao = tbc_instituicao;
    }

    public Tbc_sub_grupo getTbc_sub_grupo() {
        return tbc_sub_grupo;
    }

    public Tbc_formulario tbc_sub_grupo(Tbc_sub_grupo tbc_sub_grupo) {
        this.tbc_sub_grupo = tbc_sub_grupo;
        return this;
    }

    public void setTbc_sub_grupo(Tbc_sub_grupo tbc_sub_grupo) {
        this.tbc_sub_grupo = tbc_sub_grupo;
    }

    public Tbc_grupo_analise getTbc_grupo_analise() {
        return tbc_grupo_analise;
    }

    public Tbc_formulario tbc_grupo_analise(Tbc_grupo_analise tbc_grupo_analise) {
        this.tbc_grupo_analise = tbc_grupo_analise;
        return this;
    }

    public void setTbc_grupo_analise(Tbc_grupo_analise tbc_grupo_analise) {
        this.tbc_grupo_analise = tbc_grupo_analise;
    }

    public Tbc_tipo_cadastro getTbc_tipo_cadastro() {
        return tbc_tipo_cadastro;
    }

    public Tbc_formulario tbc_tipo_cadastro(Tbc_tipo_cadastro tbc_tipo_cadastro) {
        this.tbc_tipo_cadastro = tbc_tipo_cadastro;
        return this;
    }

    public void setTbc_tipo_cadastro(Tbc_tipo_cadastro tbc_tipo_cadastro) {
        this.tbc_tipo_cadastro = tbc_tipo_cadastro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tbc_formulario tbc_formulario = (Tbc_formulario) o;
        if (tbc_formulario.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tbc_formulario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tbc_formulario{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", metodo='" + metodo + "'" +
            ", descricao='" + descricao + "'" +
            ", diasliberacao='" + diasliberacao + "'" +
            ", removido='" + removido + "'" +
            '}';
    }
}
