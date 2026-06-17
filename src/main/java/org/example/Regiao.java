package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Regiao representa um nó na árvore hierárquica de municípios e regiões.
 * Cada região pode conter sub-regiões e armazena informações de identificação.
 */
public class Regiao {
    private static int proximoId = 1;
    private int id;
    private String nome;
    private String tipo; // Ex: País, Estado, Cidade, Bairro
    private long populacao;
    private Regiao pai;
    private List<Regiao> filhos;

    /**
     * Construtor da classe Regiao
     * @param nome Nome da região
     * @param tipo Tipo da região
     * @param populacao População da região
     */
    public Regiao(String nome, String tipo, long populacao) {
        this.id = proximoId++;
        this.nome = nome;
        this.tipo = tipo;
        this.populacao = populacao;
        this.pai = null;
        this.filhos = new ArrayList<>();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getPopulacao() {
        return populacao;
    }

    public void setPopulacao(long populacao) {
        this.populacao = populacao;
    }

    public Regiao getPai() {
        return pai;
    }

    public void setPai(Regiao pai) {
        this.pai = pai;
    }

    public List<Regiao> getFilhos() {
        return filhos;
    }

    /**
     * Adiciona uma sub-região como filha desta região
     * @param filho Região que será adicionada como filha
     */
    public void adicionarFilho(Regiao filho) {
        if (!filhos.contains(filho)) {
            filhos.add(filho);
            filho.setPai(this);
        }
    }

    /**
     * Remove uma sub-região desta região
     * @param filho Região a ser removida
     * @return true se removeu com sucesso, false caso contrário
     */
    public boolean removerFilho(Regiao filho) {
        if (filhos.remove(filho)) {
            filho.setPai(null);
            return true;
        }
        return false;
    }

    /**
     * Verifica se esta região é uma folha (não possui sub-regiões)
     * @return true se não possui filhos, false caso contrário
     */
    public boolean ehFolha() {
        return filhos.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("[ID: %d] %s (%s) - Pop: %,d", id, nome, tipo, populacao);
    }
}
