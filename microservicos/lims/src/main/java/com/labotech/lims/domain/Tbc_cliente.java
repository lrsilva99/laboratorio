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
 * A Tbc_cliente.
 */
@Entity
@Table(name = "tbc_cliente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbc_cliente")
public class Tbc_cliente extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "cep", nullable = false)
    private String cep;

    @NotNull
    @Lob
    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Lob
    @Column(name = "email")
    private String email;

    @Column(name = "imprimir")
    private Boolean imprimir;

    @Column(name = "enviar_email")
    private Boolean enviar_email;

    @Column(name = "directiva_data_atu")
    private ZonedDateTime directiva_data_atu;

    @NotNull
    @Column(name = "cpf_cnpj", nullable = false)
    private String cpf_cnpj;

    @NotNull
    @Column(name = "codigo_cliente", nullable = false)
    private String codigo_cliente;

    @Column(name = "removido")
    private Boolean removido;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "bloquear")
    private Boolean bloquear;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @NotNull
    private Tbc_instituicao tbc_instituicao;

    @ManyToOne
    @NotNull
    private Tbc_grupo_cliente tbc_grupo_cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Tbc_cliente nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public Tbc_cliente cep(String cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public Tbc_cliente endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public Tbc_cliente email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isImprimir() {
        return imprimir;
    }

    public Tbc_cliente imprimir(Boolean imprimir) {
        this.imprimir = imprimir;
        return this;
    }

    public void setImprimir(Boolean imprimir) {
        this.imprimir = imprimir;
    }

    public Boolean isEnviar_email() {
        return enviar_email;
    }

    public Tbc_cliente enviar_email(Boolean enviar_email) {
        this.enviar_email = enviar_email;
        return this;
    }

    public void setEnviar_email(Boolean enviar_email) {
        this.enviar_email = enviar_email;
    }

    public ZonedDateTime getDirectiva_data_atu() {
        return directiva_data_atu;
    }

    public Tbc_cliente directiva_data_atu(ZonedDateTime directiva_data_atu) {
        this.directiva_data_atu = directiva_data_atu;
        return this;
    }

    public void setDirectiva_data_atu(ZonedDateTime directiva_data_atu) {
        this.directiva_data_atu = directiva_data_atu;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public Tbc_cliente cpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
        return this;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getCodigo_cliente() {
        return codigo_cliente;
    }

    public Tbc_cliente codigo_cliente(String codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
        return this;
    }

    public void setCodigo_cliente(String codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public Boolean isRemovido() {
        return removido;
    }

    public Tbc_cliente removido(Boolean removido) {
        this.removido = removido;
        return this;
    }

    public void setRemovido(Boolean removido) {
        this.removido = removido;
    }

    public String getBairro() {
        return bairro;
    }

    public Tbc_cliente bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Boolean isBloquear() {
        return bloquear;
    }

    public Tbc_cliente bloquear(Boolean bloquear) {
        this.bloquear = bloquear;
        return this;
    }

    public void setBloquear(Boolean bloquear) {
        this.bloquear = bloquear;
    }

    public String getDescricao() {
        return descricao;
    }

    public Tbc_cliente descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Tbc_instituicao getTbc_instituicao() {
        return tbc_instituicao;
    }

    public Tbc_cliente tbc_instituicao(Tbc_instituicao tbc_instituicao) {
        this.tbc_instituicao = tbc_instituicao;
        return this;
    }

    public void setTbc_instituicao(Tbc_instituicao tbc_instituicao) {
        this.tbc_instituicao = tbc_instituicao;
    }

    public Tbc_grupo_cliente getTbc_grupo_cliente() {
        return tbc_grupo_cliente;
    }

    public Tbc_cliente tbc_grupo_cliente(Tbc_grupo_cliente tbc_grupo_cliente) {
        this.tbc_grupo_cliente = tbc_grupo_cliente;
        return this;
    }

    public void setTbc_grupo_cliente(Tbc_grupo_cliente tbc_grupo_cliente) {
        this.tbc_grupo_cliente = tbc_grupo_cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tbc_cliente tbc_cliente = (Tbc_cliente) o;
        if (tbc_cliente.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tbc_cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tbc_cliente{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", cep='" + cep + "'" +
            ", endereco='" + endereco + "'" +
            ", email='" + email + "'" +
            ", imprimir='" + imprimir + "'" +
            ", enviar_email='" + enviar_email + "'" +
            ", directiva_data_atu='" + directiva_data_atu + "'" +
            ", cpf_cnpj='" + cpf_cnpj + "'" +
            ", codigo_cliente='" + codigo_cliente + "'" +
            ", removido='" + removido + "'" +
            ", bairro='" + bairro + "'" +
            ", bloquear='" + bloquear + "'" +
            ", descricao='" + descricao + "'" +
            '}';
    }
}
