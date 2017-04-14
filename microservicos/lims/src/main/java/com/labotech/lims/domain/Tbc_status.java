package com.labotech.lims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Tbc_status.
 */
@Entity
@Table(name = "tbc_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbc_status")
public class Tbc_status extends AbstractAuditingEntity implements Serializable {

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

    @Column(name = "removido")
    private Boolean removido;

    @Column(name = "icone")
    private String icone;

    @Column(name = "cor")
    private String cor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Tbc_status nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Tbc_status descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isRemovido() {
        return removido;
    }

    public Tbc_status removido(Boolean removido) {
        this.removido = removido;
        return this;
    }

    public void setRemovido(Boolean removido) {
        this.removido = removido;
    }

    public String getIcone() {
        return icone;
    }

    public Tbc_status icone(String icone) {
        this.icone = icone;
        return this;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getCor() {
        return cor;
    }

    public Tbc_status cor(String cor) {
        this.cor = cor;
        return this;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tbc_status tbc_status = (Tbc_status) o;
        if (tbc_status.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tbc_status.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tbc_status{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", descricao='" + descricao + "'" +
            ", removido='" + removido + "'" +
            ", icone='" + icone + "'" +
            ", cor='" + cor + "'" +
            '}';
    }


}
