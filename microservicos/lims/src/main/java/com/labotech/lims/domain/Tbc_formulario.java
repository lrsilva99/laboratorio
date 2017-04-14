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

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "removido")
    private Boolean removido;

    @NotNull
    @Column(name = "tipo_identificacao", nullable = false)
    private String tipo_identificacao;

    @ManyToOne
    @NotNull
    private Tbc_instituicao tbc_instituicao;

    @ManyToOne
    @NotNull
    private Tbc_sub_grupo tbc_sub_grupo;

    @ManyToOne
    private Tbc_tipo_cadastro tbc_tipo_cadastro;

    @ManyToOne
    @NotNull
    private Tbc_status tbc_status;

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

    public String getTipo_identificacao() {
        return tipo_identificacao;
    }

    public Tbc_formulario tipo_identificacao(String tipo_identificacao) {
        this.tipo_identificacao = tipo_identificacao;
        return this;
    }

    public void setTipo_identificacao(String tipo_identificacao) {
        this.tipo_identificacao = tipo_identificacao;
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

    public Tbc_status getTbc_status() {
        return tbc_status;
    }

    public Tbc_formulario tbc_status(Tbc_status tbc_status) {
        this.tbc_status = tbc_status;
        return this;
    }

    public void setTbc_status(Tbc_status tbc_status) {
        this.tbc_status = tbc_status;
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
            ", descricao='" + descricao + "'" +
            ", removido='" + removido + "'" +
            ", tipo_identificacao='" + tipo_identificacao + "'" +
            '}';
    }
}
