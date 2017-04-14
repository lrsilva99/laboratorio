package com.labotech.lims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Tbc_formulario_componentes.
 */
@Entity
@Table(name = "tbc_formulario_componentes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbc_formulario_componentes")
public class Tbc_formulario_componentes extends AbstractAuditingEntity implements Serializable {

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
    @Column(name = "linha", nullable = false)
    private Integer linha;

    @NotNull
    @Column(name = "unidade_medida", nullable = false)
    private String unidade_medida;

    @NotNull
    @Lob
    @Column(name = "valor_padrao", nullable = false)
    private String valor_padrao;

    @NotNull
    @Lob
    @Column(name = "configuracao", nullable = false)
    private String configuracao;

    @Column(name = "obrigatorio")
    private Boolean obrigatorio;

    @NotNull
    @Column(name = "nome_campo_destino", nullable = false)
    private String nome_campo_destino;

    @Column(name = "visivel")
    private Boolean visivel;

    @Column(name = "bloqueado")
    private Boolean bloqueado;

    @NotNull
    @Column(name = "tamanho", nullable = false)
    private Integer tamanho;

    @ManyToOne
    @NotNull
    private Tbc_formulario tbc_formulario;

    @ManyToOne
    @NotNull
    private Tbc_tipo_campo tbc_tipo_campo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Tbc_formulario_componentes nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Tbc_formulario_componentes descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getLinha() {
        return linha;
    }

    public Tbc_formulario_componentes linha(Integer linha) {
        this.linha = linha;
        return this;
    }

    public void setLinha(Integer linha) {
        this.linha = linha;
    }

    public String getUnidade_medida() {
        return unidade_medida;
    }

    public Tbc_formulario_componentes unidade_medida(String unidade_medida) {
        this.unidade_medida = unidade_medida;
        return this;
    }

    public void setUnidade_medida(String unidade_medida) {
        this.unidade_medida = unidade_medida;
    }

    public String getValor_padrao() {
        return valor_padrao;
    }

    public Tbc_formulario_componentes valor_padrao(String valor_padrao) {
        this.valor_padrao = valor_padrao;
        return this;
    }

    public void setValor_padrao(String valor_padrao) {
        this.valor_padrao = valor_padrao;
    }

    public String getConfiguracao() {
        return configuracao;
    }

    public Tbc_formulario_componentes configuracao(String configuracao) {
        this.configuracao = configuracao;
        return this;
    }

    public void setConfiguracao(String configuracao) {
        this.configuracao = configuracao;
    }

    public Boolean isObrigatorio() {
        return obrigatorio;
    }

    public Tbc_formulario_componentes obrigatorio(Boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
        return this;
    }

    public void setObrigatorio(Boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    public String getNome_campo_destino() {
        return nome_campo_destino;
    }

    public Tbc_formulario_componentes nome_campo_destino(String nome_campo_destino) {
        this.nome_campo_destino = nome_campo_destino;
        return this;
    }

    public void setNome_campo_destino(String nome_campo_destino) {
        this.nome_campo_destino = nome_campo_destino;
    }

    public Boolean isVisivel() {
        return visivel;
    }

    public Tbc_formulario_componentes visivel(Boolean visivel) {
        this.visivel = visivel;
        return this;
    }

    public void setVisivel(Boolean visivel) {
        this.visivel = visivel;
    }

    public Boolean isBloqueado() {
        return bloqueado;
    }

    public Tbc_formulario_componentes bloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
        return this;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public Tbc_formulario_componentes tamanho(Integer tamanho) {
        this.tamanho = tamanho;
        return this;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public Tbc_formulario getTbc_formulario() {
        return tbc_formulario;
    }

    public Tbc_formulario_componentes tbc_formulario(Tbc_formulario tbc_formulario) {
        this.tbc_formulario = tbc_formulario;
        return this;
    }

    public void setTbc_formulario(Tbc_formulario tbc_formulario) {
        this.tbc_formulario = tbc_formulario;
    }

    public Tbc_tipo_campo getTbc_tipo_campo() {
        return tbc_tipo_campo;
    }

    public Tbc_formulario_componentes tbc_tipo_campo(Tbc_tipo_campo tbc_tipo_campo) {
        this.tbc_tipo_campo = tbc_tipo_campo;
        return this;
    }

    public void setTbc_tipo_campo(Tbc_tipo_campo tbc_tipo_campo) {
        this.tbc_tipo_campo = tbc_tipo_campo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tbc_formulario_componentes tbc_formulario_componentes = (Tbc_formulario_componentes) o;
        if (tbc_formulario_componentes.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tbc_formulario_componentes.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tbc_formulario_componentes{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", descricao='" + descricao + "'" +
            ", linha='" + linha + "'" +
            ", unidade_medida='" + unidade_medida + "'" +
            ", valor_padrao='" + valor_padrao + "'" +
            ", configuracao='" + configuracao + "'" +
            ", obrigatorio='" + obrigatorio + "'" +
            ", nome_campo_destino='" + nome_campo_destino + "'" +
            ", visivel='" + visivel + "'" +
            ", bloqueado='" + bloqueado + "'" +
            ", tamanho='" + tamanho + "'" +
            '}';
    }
}
