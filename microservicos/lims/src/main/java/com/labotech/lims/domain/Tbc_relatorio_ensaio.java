package com.labotech.lims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Tbc_relatorio_ensaio.
 */
@Entity
@Table(name = "tbc_relatorio_ensaio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbc_relatorio_ensaio")
public class Tbc_relatorio_ensaio extends AbstractAuditingEntity implements Serializable {

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
    @Column(name = "caminho", nullable = false)
    private String caminho;

    @Column(name = "removido")
    private Boolean removido;

    @ManyToOne
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

    public Tbc_relatorio_ensaio nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Tbc_relatorio_ensaio descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCaminho() {
        return caminho;
    }

    public Tbc_relatorio_ensaio caminho(String caminho) {
        this.caminho = caminho;
        return this;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public Boolean isRemovido() {
        return removido;
    }

    public Tbc_relatorio_ensaio removido(Boolean removido) {
        this.removido = removido;
        return this;
    }

    public void setRemovido(Boolean removido) {
        this.removido = removido;
    }

    public Tbc_instituicao getTbc_instituicao() {
        return tbc_instituicao;
    }

    public Tbc_relatorio_ensaio tbc_instituicao(Tbc_instituicao tbc_instituicao) {
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
        Tbc_relatorio_ensaio tbc_relatorio_ensaio = (Tbc_relatorio_ensaio) o;
        if (tbc_relatorio_ensaio.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tbc_relatorio_ensaio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tbc_relatorio_ensaio{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", descricao='" + descricao + "'" +
            ", caminho='" + caminho + "'" +
            ", removido='" + removido + "'" +
            '}';
    }
}
