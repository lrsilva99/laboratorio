package com.labotech.lims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * A Tbc_numeracao.
 */
@Entity
@Table(name = "tbc_numeracao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbc_numeracao")
public class Tbc_numeracao extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "ano", nullable = false)
    private Integer ano;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @NotNull
    @Column(name = "parametro", nullable = false)
    private String parametro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAno() {
        return ano;
    }

    public Tbc_numeracao ano(Integer ano) {
        this.ano = ano;
        return this;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getNumero() {
        return numero;
    }

    public Tbc_numeracao numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getParametro() {
        return parametro;
    }

    public Tbc_numeracao parametro(String parametro) {
        this.parametro = parametro;
        return this;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tbc_numeracao tbc_numeracao = (Tbc_numeracao) o;
        if (tbc_numeracao.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tbc_numeracao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tbc_numeracao{" +
            "id=" + id +
            ", ano='" + ano + "'" +
            ", numero='" + numero + "'" +
            ", parametro='" + parametro + "'" +
            '}';
    }
    public String getReq_Texto(){
        String formatNumero  = new DecimalFormat("00000").format(this.getNumero());
        return parametro + "-" + this.getAno() + "-"+ formatNumero;
    }
}
