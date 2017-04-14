package com.labotech.lims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Tbc_qualidade_amostra.
 */
@Entity
@Table(name = "tbc_qualidade_amostra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbc_qualidade_amostra")
public class Tbc_qualidade_amostra extends AbstractAuditingEntity implements Serializable {

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

    @Column(name = "remover")
    private Boolean remover;

    @ManyToOne
    @NotNull
    private Tbc_instituicao tbc_instituicao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Tbc_qualidade_amostra nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Tbc_qualidade_amostra descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isRemover() {
        return remover;
    }

    public Tbc_qualidade_amostra remover(Boolean remover) {
        this.remover = remover;
        return this;
    }

    public void setRemover(Boolean remover) {
        this.remover = remover;
    }

    public Tbc_instituicao getTbc_instituicao() {
        return tbc_instituicao;
    }

    public Tbc_qualidade_amostra tbc_instituicao(Tbc_instituicao tbc_instituicao) {
        this.tbc_instituicao = tbc_instituicao;
        return this;
    }

    public void setTbc_instituicao(Tbc_instituicao tbc_instituicao) {
        this.tbc_instituicao = tbc_instituicao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tbc_qualidade_amostra tbc_qualidade_amostra = (Tbc_qualidade_amostra) o;
        if (tbc_qualidade_amostra.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tbc_qualidade_amostra.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tbc_qualidade_amostra{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", descricao='" + descricao + "'" +
            ", remover='" + remover + "'" +
            '}';
    }
}
