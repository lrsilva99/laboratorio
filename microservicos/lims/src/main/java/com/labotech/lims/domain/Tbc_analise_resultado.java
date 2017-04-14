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
 * A Tbc_analise_resultado.
 */
@Entity
@Table(name = "tbc_analise_resultado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbc_analise_resultado")
public class Tbc_analise_resultado extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "analises_componente", nullable = false)
    private String analises_componente;

    @NotNull
    @Column(name = "resultado", nullable = false)
    private String resultado;

    @Column(name = "autorizado_por")
    private String autorizado_por;

    @Column(name = "autorizado_data")
    private ZonedDateTime autorizado_data;

    @Column(name = "cancelado_por")
    private String cancelado_por;

    @Column(name = "cancelado_data")
    private ZonedDateTime cancelado_data;

    @Column(name = "suspenso_por")
    private String suspenso_por;

    @Column(name = "suspenso_data")
    private ZonedDateTime suspenso_data;

    @Column(name = "rejeitado_por")
    private String rejeitado_por;

    @Column(name = "rejeitado_data")
    private ZonedDateTime rejeitado_data;

    @Column(name = "disponivel_por")
    private String disponivel_por;

    @Column(name = "disponivel_data")
    private ZonedDateTime disponivel_data;

    @Column(name = "resultado_digitado")
    private String resultado_digitado;

    @Column(name = "unidade_medida")
    private String unidade_medida;

    @NotNull
    @Column(name = "tbr_analise_id", nullable = false)
    private Long tbr_analise_id;

    @NotNull
    @Lob
    @Column(name = "resultado_texto", nullable = false)
    private String resultado_texto;

    @Column(name = "repetido")
    private Boolean repetido;

    @NotNull
    @Column(name = "tbc_amostra_id", nullable = false)
    private Long tbc_amostra_id;

    @ManyToOne
    @NotNull
    private Tbc_status tbc_status;

    @ManyToOne
    private Tbc_status tbc_status_ultimo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnalises_componente() {
        return analises_componente;
    }

    public Tbc_analise_resultado analises_componente(String analises_componente) {
        this.analises_componente = analises_componente;
        return this;
    }

    public void setAnalises_componente(String analises_componente) {
        this.analises_componente = analises_componente;
    }

    public String getResultado() {
        return resultado;
    }

    public Tbc_analise_resultado resultado(String resultado) {
        this.resultado = resultado;
        return this;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getAutorizado_por() {
        return autorizado_por;
    }

    public Tbc_analise_resultado autorizado_por(String autorizado_por) {
        this.autorizado_por = autorizado_por;
        return this;
    }

    public void setAutorizado_por(String autorizado_por) {
        this.autorizado_por = autorizado_por;
    }

    public ZonedDateTime getAutorizado_data() {
        return autorizado_data;
    }

    public Tbc_analise_resultado autorizado_data(ZonedDateTime autorizado_data) {
        this.autorizado_data = autorizado_data;
        return this;
    }

    public void setAutorizado_data(ZonedDateTime autorizado_data) {
        this.autorizado_data = autorizado_data;
    }

    public String getCancelado_por() {
        return cancelado_por;
    }

    public Tbc_analise_resultado cancelado_por(String cancelado_por) {
        this.cancelado_por = cancelado_por;
        return this;
    }

    public void setCancelado_por(String cancelado_por) {
        this.cancelado_por = cancelado_por;
    }

    public ZonedDateTime getCancelado_data() {
        return cancelado_data;
    }

    public Tbc_analise_resultado cancelado_data(ZonedDateTime cancelado_data) {
        this.cancelado_data = cancelado_data;
        return this;
    }

    public void setCancelado_data(ZonedDateTime cancelado_data) {
        this.cancelado_data = cancelado_data;
    }

    public String getSuspenso_por() {
        return suspenso_por;
    }

    public Tbc_analise_resultado suspenso_por(String suspenso_por) {
        this.suspenso_por = suspenso_por;
        return this;
    }

    public void setSuspenso_por(String suspenso_por) {
        this.suspenso_por = suspenso_por;
    }

    public ZonedDateTime getSuspenso_data() {
        return suspenso_data;
    }

    public Tbc_analise_resultado suspenso_data(ZonedDateTime suspenso_data) {
        this.suspenso_data = suspenso_data;
        return this;
    }

    public void setSuspenso_data(ZonedDateTime suspenso_data) {
        this.suspenso_data = suspenso_data;
    }

    public String getRejeitado_por() {
        return rejeitado_por;
    }

    public Tbc_analise_resultado rejeitado_por(String rejeitado_por) {
        this.rejeitado_por = rejeitado_por;
        return this;
    }

    public void setRejeitado_por(String rejeitado_por) {
        this.rejeitado_por = rejeitado_por;
    }

    public ZonedDateTime getRejeitado_data() {
        return rejeitado_data;
    }

    public Tbc_analise_resultado rejeitado_data(ZonedDateTime rejeitado_data) {
        this.rejeitado_data = rejeitado_data;
        return this;
    }

    public void setRejeitado_data(ZonedDateTime rejeitado_data) {
        this.rejeitado_data = rejeitado_data;
    }

    public String getDisponivel_por() {
        return disponivel_por;
    }

    public Tbc_analise_resultado disponivel_por(String disponivel_por) {
        this.disponivel_por = disponivel_por;
        return this;
    }

    public void setDisponivel_por(String disponivel_por) {
        this.disponivel_por = disponivel_por;
    }

    public ZonedDateTime getDisponivel_data() {
        return disponivel_data;
    }

    public Tbc_analise_resultado disponivel_data(ZonedDateTime disponivel_data) {
        this.disponivel_data = disponivel_data;
        return this;
    }

    public void setDisponivel_data(ZonedDateTime disponivel_data) {
        this.disponivel_data = disponivel_data;
    }

    public String getResultado_digitado() {
        return resultado_digitado;
    }

    public Tbc_analise_resultado resultado_digitado(String resultado_digitado) {
        this.resultado_digitado = resultado_digitado;
        return this;
    }

    public void setResultado_digitado(String resultado_digitado) {
        this.resultado_digitado = resultado_digitado;
    }

    public String getUnidade_medida() {
        return unidade_medida;
    }

    public Tbc_analise_resultado unidade_medida(String unidade_medida) {
        this.unidade_medida = unidade_medida;
        return this;
    }

    public void setUnidade_medida(String unidade_medida) {
        this.unidade_medida = unidade_medida;
    }

    public Long getTbr_analise_id() {
        return tbr_analise_id;
    }

    public Tbc_analise_resultado tbr_analise_id(Long tbr_analise_id) {
        this.tbr_analise_id = tbr_analise_id;
        return this;
    }

    public void setTbr_analise_id(Long tbr_analise_id) {
        this.tbr_analise_id = tbr_analise_id;
    }

    public String getResultado_texto() {
        return resultado_texto;
    }

    public Tbc_analise_resultado resultado_texto(String resultado_texto) {
        this.resultado_texto = resultado_texto;
        return this;
    }

    public void setResultado_texto(String resultado_texto) {
        this.resultado_texto = resultado_texto;
    }

    public Boolean isRepetido() {
        return repetido;
    }

    public Tbc_analise_resultado repetido(Boolean repetido) {
        this.repetido = repetido;
        return this;
    }

    public void setRepetido(Boolean repetido) {
        this.repetido = repetido;
    }

    public Long getTbc_amostra_id() {
        return tbc_amostra_id;
    }

    public Tbc_analise_resultado tbc_amostra_id(Long tbc_amostra_id) {
        this.tbc_amostra_id = tbc_amostra_id;
        return this;
    }

    public void setTbc_amostra_id(Long tbc_amostra_id) {
        this.tbc_amostra_id = tbc_amostra_id;
    }

    public Tbc_status getTbc_status() {
        return tbc_status;
    }

    public Tbc_analise_resultado tbc_status(Tbc_status tbc_status) {
        this.tbc_status = tbc_status;
        return this;
    }

    public void setTbc_status(Tbc_status tbc_status) {
        this.tbc_status = tbc_status;
    }

    public Tbc_status getTbc_status_ultimo() {
        return tbc_status_ultimo;
    }

    public Tbc_analise_resultado tbc_status_ultimo(Tbc_status tbc_status) {
        this.tbc_status_ultimo = tbc_status;
        return this;
    }

    public void setTbc_status_ultimo(Tbc_status tbc_status) {
        this.tbc_status_ultimo = tbc_status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tbc_analise_resultado tbc_analise_resultado = (Tbc_analise_resultado) o;
        if (tbc_analise_resultado.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tbc_analise_resultado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tbc_analise_resultado{" +
            "id=" + id +
            ", analises_componente='" + analises_componente + "'" +
            ", resultado='" + resultado + "'" +
            ", autorizado_por='" + autorizado_por + "'" +
            ", autorizado_data='" + autorizado_data + "'" +
            ", cancelado_por='" + cancelado_por + "'" +
            ", cancelado_data='" + cancelado_data + "'" +
            ", suspenso_por='" + suspenso_por + "'" +
            ", suspenso_data='" + suspenso_data + "'" +
            ", rejeitado_por='" + rejeitado_por + "'" +
            ", rejeitado_data='" + rejeitado_data + "'" +
            ", disponivel_por='" + disponivel_por + "'" +
            ", disponivel_data='" + disponivel_data + "'" +
            ", resultado_digitado='" + resultado_digitado + "'" +
            ", unidade_medida='" + unidade_medida + "'" +
            ", tbr_analise_id='" + tbr_analise_id + "'" +
            ", resultado_texto='" + resultado_texto + "'" +
            ", repetido='" + repetido + "'" +
            ", tbc_amostra_id='" + tbc_amostra_id + "'" +
            '}';
    }
}
