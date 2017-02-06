package com.labotech.lims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Tbc_frases.
 */
@Entity
@Table(name = "tbc_frases")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbc_frases")
public class Tbc_frases extends AbstractAuditingEntity implements Serializable {

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

    @ManyToOne
    @NotNull
    private Tbc_instituicao tbc_instituicao;

    @ManyToOne
    @NotNull
    private Tbc_sub_grupo tbc_sub_grupo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Tbc_frases nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Tbc_frases descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isRemovido() {
        return removido;
    }

    public Tbc_frases removido(Boolean removido) {
        this.removido = removido;
        return this;
    }

    public void setRemovido(Boolean removido) {
        this.removido = removido;
    }

    public Tbc_instituicao getTbc_instituicao() {
        return tbc_instituicao;
    }

    public Tbc_frases tbc_instituicao(Tbc_instituicao tbc_instituicao) {
        this.tbc_instituicao = tbc_instituicao;
        return this;
    }

    public void setTbc_instituicao(Tbc_instituicao tbc_instituicao) {
        this.tbc_instituicao = tbc_instituicao;
    }

    public Tbc_sub_grupo getTbc_sub_grupo() {
        return tbc_sub_grupo;
    }

    public Tbc_frases tbc_sub_grupo(Tbc_sub_grupo tbc_sub_grupo) {
        this.tbc_sub_grupo = tbc_sub_grupo;
        return this;
    }

    public void setTbc_sub_grupo(Tbc_sub_grupo tbc_sub_grupo) {
        this.tbc_sub_grupo = tbc_sub_grupo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tbc_frases tbc_frases = (Tbc_frases) o;
        if (tbc_frases.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tbc_frases.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tbc_frases{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", descricao='" + descricao + "'" +
            ", removido='" + removido + "'" +
            '}';
    }
}
