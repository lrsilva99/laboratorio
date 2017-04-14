package com.labotech.lims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Tbr_analise.
 */
@Entity
@Table(name = "tbr_analise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbr_analise")
public class Tbr_analise extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "autorizado_por")
    private String autorizado_por;

    @Column(name = "completado_por")
    private String completado_por;

    @Column(name = "completado_data")
    private ZonedDateTime completado_data;

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

    @Lob
    @Column(name = "observacao")
    private String observacao;

    @Column(name = "forma_cadastro")
    private String forma_cadastro;

    @Lob
    @Column(name = "anotacao_status")
    private String anotacao_status;

    @Column(name = "directiva_data_atu")
    private LocalDate directiva_data_atu;

    @ManyToOne
    @NotNull
    private Tbr_amostra tbr_amostra;

    @ManyToOne
    @NotNull
    private Tbc_status tbc_status;

    @ManyToOne
    private Tbc_analises tbc_analises;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutorizado_por() {
        return autorizado_por;
    }

    public Tbr_analise autorizado_por(String autorizado_por) {
        this.autorizado_por = autorizado_por;
        return this;
    }

    public void setAutorizado_por(String autorizado_por) {
        this.autorizado_por = autorizado_por;
    }

    public String getCompletado_por() {
        return completado_por;
    }

    public Tbr_analise completado_por(String completado_por) {
        this.completado_por = completado_por;
        return this;
    }

    public void setCompletado_por(String completado_por) {
        this.completado_por = completado_por;
    }

    public ZonedDateTime getCompletado_data() {
        return completado_data;
    }

    public Tbr_analise completado_data(ZonedDateTime completado_data) {
        this.completado_data = completado_data;
        return this;
    }

    public void setCompletado_data(ZonedDateTime completado_data) {
        this.completado_data = completado_data;
    }

    public ZonedDateTime getAutorizado_data() {
        return autorizado_data;
    }

    public Tbr_analise autorizado_data(ZonedDateTime autorizado_data) {
        this.autorizado_data = autorizado_data;
        return this;
    }

    public void setAutorizado_data(ZonedDateTime autorizado_data) {
        this.autorizado_data = autorizado_data;
    }

    public String getCancelado_por() {
        return cancelado_por;
    }

    public Tbr_analise cancelado_por(String cancelado_por) {
        this.cancelado_por = cancelado_por;
        return this;
    }

    public void setCancelado_por(String cancelado_por) {
        this.cancelado_por = cancelado_por;
    }

    public ZonedDateTime getCancelado_data() {
        return cancelado_data;
    }

    public Tbr_analise cancelado_data(ZonedDateTime cancelado_data) {
        this.cancelado_data = cancelado_data;
        return this;
    }

    public void setCancelado_data(ZonedDateTime cancelado_data) {
        this.cancelado_data = cancelado_data;
    }

    public String getSuspenso_por() {
        return suspenso_por;
    }

    public Tbr_analise suspenso_por(String suspenso_por) {
        this.suspenso_por = suspenso_por;
        return this;
    }

    public void setSuspenso_por(String suspenso_por) {
        this.suspenso_por = suspenso_por;
    }

    public ZonedDateTime getSuspenso_data() {
        return suspenso_data;
    }

    public Tbr_analise suspenso_data(ZonedDateTime suspenso_data) {
        this.suspenso_data = suspenso_data;
        return this;
    }

    public void setSuspenso_data(ZonedDateTime suspenso_data) {
        this.suspenso_data = suspenso_data;
    }

    public String getRejeitado_por() {
        return rejeitado_por;
    }

    public Tbr_analise rejeitado_por(String rejeitado_por) {
        this.rejeitado_por = rejeitado_por;
        return this;
    }

    public void setRejeitado_por(String rejeitado_por) {
        this.rejeitado_por = rejeitado_por;
    }

    public ZonedDateTime getRejeitado_data() {
        return rejeitado_data;
    }

    public Tbr_analise rejeitado_data(ZonedDateTime rejeitado_data) {
        this.rejeitado_data = rejeitado_data;
        return this;
    }

    public void setRejeitado_data(ZonedDateTime rejeitado_data) {
        this.rejeitado_data = rejeitado_data;
    }

    public String getDisponivel_por() {
        return disponivel_por;
    }

    public Tbr_analise disponivel_por(String disponivel_por) {
        this.disponivel_por = disponivel_por;
        return this;
    }

    public void setDisponivel_por(String disponivel_por) {
        this.disponivel_por = disponivel_por;
    }

    public ZonedDateTime getDisponivel_data() {
        return disponivel_data;
    }

    public Tbr_analise disponivel_data(ZonedDateTime disponivel_data) {
        this.disponivel_data = disponivel_data;
        return this;
    }

    public void setDisponivel_data(ZonedDateTime disponivel_data) {
        this.disponivel_data = disponivel_data;
    }

    public String getObservacao() {
        return observacao;
    }

    public Tbr_analise observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getForma_cadastro() {
        return forma_cadastro;
    }

    public Tbr_analise forma_cadastro(String forma_cadastro) {
        this.forma_cadastro = forma_cadastro;
        return this;
    }

    public void setForma_cadastro(String forma_cadastro) {
        this.forma_cadastro = forma_cadastro;
    }

    public String getAnotacao_status() {
        return anotacao_status;
    }

    public Tbr_analise anotacao_status(String anotacao_status) {
        this.anotacao_status = anotacao_status;
        return this;
    }

    public void setAnotacao_status(String anotacao_status) {
        this.anotacao_status = anotacao_status;
    }

    public LocalDate getDirectiva_data_atu() {
        return directiva_data_atu;
    }

    public Tbr_analise directiva_data_atu(LocalDate directiva_data_atu) {
        this.directiva_data_atu = directiva_data_atu;
        return this;
    }

    public void setDirectiva_data_atu(LocalDate directiva_data_atu) {
        this.directiva_data_atu = directiva_data_atu;
    }

    public Tbr_amostra getTbr_amostra() {
        return tbr_amostra;
    }

    public Tbr_analise tbr_amostra(Tbr_amostra tbr_amostra) {
        this.tbr_amostra = tbr_amostra;
        return this;
    }

    public void setTbr_amostra(Tbr_amostra tbr_amostra) {
        this.tbr_amostra = tbr_amostra;
    }

    public Tbc_status getTbc_status() {
        return tbc_status;
    }

    public Tbr_analise tbc_status(Tbc_status tbc_status) {
        this.tbc_status = tbc_status;
        return this;
    }

    public void setTbc_status(Tbc_status tbc_status) {
        this.tbc_status = tbc_status;
    }

    public Tbc_analises getTbc_analises() {
        return tbc_analises;
    }

    public Tbr_analise tbc_analises(Tbc_analises tbc_analises) {
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
        Tbr_analise tbr_analise = (Tbr_analise) o;
        if (tbr_analise.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tbr_analise.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tbr_analise{" +
            "id=" + id +
            ", autorizado_por='" + autorizado_por + "'" +
            ", completado_por='" + completado_por + "'" +
            ", completado_data='" + completado_data + "'" +
            ", autorizado_data='" + autorizado_data + "'" +
            ", cancelado_por='" + cancelado_por + "'" +
            ", cancelado_data='" + cancelado_data + "'" +
            ", suspenso_por='" + suspenso_por + "'" +
            ", suspenso_data='" + suspenso_data + "'" +
            ", rejeitado_por='" + rejeitado_por + "'" +
            ", rejeitado_data='" + rejeitado_data + "'" +
            ", disponivel_por='" + disponivel_por + "'" +
            ", disponivel_data='" + disponivel_data + "'" +
            ", observacao='" + observacao + "'" +
            ", forma_cadastro='" + forma_cadastro + "'" +
            ", anotacao_status='" + anotacao_status + "'" +
            ", directiva_data_atu='" + directiva_data_atu + "'" +
            '}';
    }
}
