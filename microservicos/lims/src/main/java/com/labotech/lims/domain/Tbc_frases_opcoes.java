package com.labotech.lims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Tbc_frases_opcoes.
 */
@Entity
@Table(name = "tbc_frases_opcoes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbc_frases_opcoes")
public class Tbc_frases_opcoes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "removido")
    private Boolean removido;

    @ManyToOne
    @NotNull
    private Tbc_frases tbc_frases;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Tbc_frases_opcoes nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Tbc_frases_opcoes descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isRemovido() {
        return removido;
    }

    public Tbc_frases_opcoes removido(Boolean removido) {
        this.removido = removido;
        return this;
    }

    public void setRemovido(Boolean removido) {
        this.removido = removido;
    }

    public Tbc_frases getTbc_frases() {
        return tbc_frases;
    }

    public Tbc_frases_opcoes tbc_frases(Tbc_frases tbc_frases) {
        this.tbc_frases = tbc_frases;
        return this;
    }

    public void setTbc_frases(Tbc_frases tbc_frases) {
        this.tbc_frases = tbc_frases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tbc_frases_opcoes tbc_frases_opcoes = (Tbc_frases_opcoes) o;
        if (tbc_frases_opcoes.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tbc_frases_opcoes.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tbc_frases_opcoes{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", descricao='" + descricao + "'" +
            ", removido='" + removido + "'" +
            '}';
    }
}
