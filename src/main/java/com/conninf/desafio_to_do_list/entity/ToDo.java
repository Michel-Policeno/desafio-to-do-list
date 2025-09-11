package com.conninf.desafio_to_do_list.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "tarefas")
public class ToDo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "O nome da tarefa é obrigatória")
    private String nome;
    private String descricao;
    @NotNull(message = "A prioridade é obrigatória")
    @Min(value = 0, message = "A prioridade deve ser no mínimo 0")
    @Max(value = 2, message = "A prioridade deve ser no máximo 2")
    private int prioridade;
    private LocalDateTime dataCriacao;
    private boolean realizado;
    private LocalDateTime dataRealizado;
    private LocalDateTime ultimaModificacao;

    public ToDo() {

    }

    public ToDo(String nome, String descricao, int prioridade) {
        this.nome = nome;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.dataCriacao = LocalDateTime.now();
        this.realizado = false;
        this.dataRealizado = null;
        this.ultimaModificacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    public LocalDateTime getDataRealizado() {
        return dataRealizado;
    }

    public void setDataRealizado(LocalDateTime dataRealizado) {
        this.dataRealizado = dataRealizado;
    }

    public LocalDateTime getUltimaModificacao() {
        return ultimaModificacao;
    }

    public void setUltimaModificacao(LocalDateTime ultimaModificacao) {
        this.ultimaModificacao = ultimaModificacao;
    }
}
