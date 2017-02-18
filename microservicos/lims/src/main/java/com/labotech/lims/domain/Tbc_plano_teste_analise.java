package com.labotech.lims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Tbc_plano_teste_analise.
 */
@Entity
@Table(name = "tbc_plano_teste_analise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbc_plano_teste_analise")
public class Tbc_plano_teste_analise extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "ordem", nullable = false)
    private Integer ordem;

    @ManyToOne
    @NotNull
    private Tbc_plano_teste tbc_plano_teste;

    @ManyToOne
    @NotNull
    private Tbc_analises tbc_analises;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public Tbc_plano_teste_analise ordem(Integer ordem) {
        this.ordem = ordem;
        return this;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Tbc_plano_teste getTbc_plano_teste() {
        return tbc_plano_teste;
    }

    public Tbc_plano_teste_analise tbc_plano_teste(Tbc_plano_teste tbc_plano_teste) {
        this.tbc_plano_teste = tbc_plano_teste;
        return this;
    }

    public void setTbc_plano_teste(Tbc_plano_teste tbc_plano_teste) {
        this.tbc_plano_teste = tbc_plano_teste;
    }

    public Tbc_analises getTbc_analises() {
        return tbc_analises;
    }

    public Tbc_plano_teste_analise tbc_analises(Tbc_analises tbc_analises) {
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
        Tbc_plano_teste_analise tbc_plano_teste_analise = (Tbc_plano_teste_analise) o;
        if (tbc_plano_teste_analise.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tbc_plano_teste_analise.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tbc_plano_teste_analise{" +
            "id=" + id +
            ", ordem='" + ordem + "'" +
            '}';
    }
}
