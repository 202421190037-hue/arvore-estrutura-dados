package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Classe SistemaOrganizacional gerencia a árvore hierárquica de regiões.
 * Implementa as operações de CRUD e navegação na estrutura territorial.
 */
public class SistemaOrganizacional {
    private List<Regiao> raizes; // Lista de regiões raízes (sem pai)

    /**
     * Construtor do sistema organizacional
     */
    public SistemaOrganizacional() {
        this.raizes = new ArrayList<>();
    }

    /**
     * Operação 1: Insere uma nova região
     * @param nome Nome da região
     * @param tipo Tipo da região
     * @param populacao População da região
     * @return A região criada
     */
    public Regiao inserirRegiao(String nome, String tipo, long populacao) {
        Regiao novaRegiao = new Regiao(nome, tipo, populacao);
        raizes.add(novaRegiao); // Adiciona como raiz (sem pai)
        return novaRegiao;
    }

    /**
     * Operação 2: Associa uma região como sub-região de outra
     * @param pai Região que será a pai
     * @param filho Região que será adicionada como filha
     * @return true se conseguiu associar, false caso contrário
     */
    public boolean associarSubRegiao(Regiao pai, Regiao filho) {
        if (pai != null && filho != null && !pai.equals(filho)) {
            // Verifica se já não é filha
            if (!pai.getFilhos().contains(filho)) {
                pai.adicionarFilho(filho);
                // Remove da lista de raízes se estava lá
                raizes.remove(filho);
                return true;
            }
        }
        return false;
    }

    /**
     * Operação 3: Altera informações de uma região
     * @param regiao Região a ser alterada
     * @param novoNome Novo nome (ou null para não alterar)
     * @param novoTipo Novo tipo (ou null para não alterar)
     * @param novaPopulacao Nova população (ou -1 para não alterar)
     * @return true se conseguiu alterar, false caso contrário
     */
    public boolean alterarInformacoes(Regiao regiao, String novoNome, String novoTipo, long novaPopulacao) {
        if (regiao == null) {
            return false;
        }

        if (novoNome != null && !novoNome.trim().isEmpty()) {
            regiao.setNome(novoNome);
        }

        if (novoTipo != null && !novoTipo.trim().isEmpty()) {
            regiao.setTipo(novoTipo);
        }

        if (novaPopulacao >= 0) {
            regiao.setPopulacao(novaPopulacao);
        }

        return true;
    }

    /**
     * Operação 4: Remove uma região e todas as suas subdivisões
     * @param regiao Região a ser removida
     * @return true se conseguiu remover, false caso contrário
     */
    public boolean removerRegiao(Regiao regiao) {
        if (regiao == null) {
            return false;
        }

        // Se é uma raiz
        if (regiao.getPai() == null) {
            raizes.remove(regiao);
            regiao.getFilhos().clear();
            return true;
        }
        // Se tem pai
        Regiao pai = regiao.getPai();
        if (pai != null) {
            regiao.getFilhos().clear(); // Remove todos os filhos
            pai.removerFilho(regiao);
            return true;
        }

        return false;
    }

    /**
     * Operação 5: Exibe toda a estrutura territorial de forma visual
     */
    public void exibirEstruturaTerritorial() {
        if (raizes.isEmpty()) {
            System.out.println("\n[VAZIO] Nenhuma região cadastrada.");
            return;
        }

        System.out.println("\n" + Utils.repeat("=", 60));
        System.out.println("ESTRUTURA TERRITORIAL COMPLETA");
        System.out.println(Utils.repeat("=", 60));
        
        for (Regiao raiz : raizes) {
            exibirArvoreRecursivo(raiz, "", true);
        }
        
        System.out.println(Utils.repeat("=", 60));
    }

    /**
     * Método auxiliar recursivo para exibir a árvore de forma visual
     */
    private void exibirArvoreRecursivo(Regiao regiao, String prefixo, boolean ehUltimo) {
        String conector = ehUltimo ? "└── " : "├── ";
        System.out.println(prefixo + conector + String.format("[ID: %d] %s (%s) - Pop: %,d", 
                          regiao.getId(), regiao.getNome(), regiao.getTipo(), regiao.getPopulacao()));

        List<Regiao> filhos = regiao.getFilhos();
        for (int i = 0; i < filhos.size(); i++) {
            Regiao filho = filhos.get(i);
            boolean ehUltimoFilho = (i == filhos.size() - 1);
            String novoPrefixo = prefixo + (ehUltimo ? "    " : "│   ");
            exibirArvoreRecursivo(filho, novoPrefixo, ehUltimoFilho);
        }
    }

    /**
     * Busca uma região pelo ID
     * @param id ID da região a buscar
     * @return Optional contendo a região se encontrada, ou vazio
     */
    public Optional<Regiao> buscarPorId(int id) {
        for (Regiao raiz : raizes) {
            Optional<Regiao> resultado = buscarPorIdRecursivo(raiz, id);
            if (resultado.isPresent()) {
                return resultado;
            }
        }
        return Optional.empty();
    }

    /**
     * Método auxiliar recursivo para busca por ID
     */
    private Optional<Regiao> buscarPorIdRecursivo(Regiao regiao, int id) {
        if (regiao.getId() == id) {
            return Optional.of(regiao);
        }

        for (Regiao filho : regiao.getFilhos()) {
            Optional<Regiao> resultado = buscarPorIdRecursivo(filho, id);
            if (resultado.isPresent()) {
                return resultado;
            }
        }

        return Optional.empty();
    }

    /**
     * Lista todas as regiões cadastradas
     * @return Lista de todas as regiões
     */
    public List<Regiao> listarTodasRegioes() {
        List<Regiao> todasRegioes = new ArrayList<>();
        for (Regiao raiz : raizes) {
            coletarTodasRegiõesRecursivo(raiz, todasRegioes);
        }
        return todasRegioes;
    }

    /**
     * Método auxiliar recursivo para coletar todas as regiões
     */
    private void coletarTodasRegiõesRecursivo(Regiao regiao, List<Regiao> todasRegioes) {
        todasRegioes.add(regiao);
        for (Regiao filho : regiao.getFilhos()) {
            coletarTodasRegiõesRecursivo(filho, todasRegioes);
        }
    }

    /**
     * Operação 6: Busca uma região pelo nome
     * @param nome Nome da região a buscar
     * @return Optional contendo a região se encontrada, ou vazio
     */
    public Optional<Regiao> buscarPorNome(String nome) {
        if (raizes.isEmpty() || nome == null) {
            return Optional.empty();
        }
        
        for (Regiao raiz : raizes) {
            Optional<Regiao> resultado = buscarPorNomeRecursivo(raiz, nome);
            if (resultado.isPresent()) {
                return resultado;
            }
        }
        
        return Optional.empty();
    }

    /**
     * Método auxiliar recursivo para busca por nome
     */
    private Optional<Regiao> buscarPorNomeRecursivo(Regiao regiao, String nome) {
        if (regiao.getNome().equalsIgnoreCase(nome)) {
            return Optional.of(regiao);
        }

        for (Regiao filho : regiao.getFilhos()) {
            Optional<Regiao> resultado = buscarPorNomeRecursivo(filho, nome);
            if (resultado.isPresent()) {
                return resultado;
            }
        }

        return Optional.empty();
    }

    /**
     * Operação 7: Exibe o caminho completo de uma região
     * Exemplo: Brasil > Bahia > Vitória da Conquista > Bairro Candeias
     * @param regiao Região para exibir o caminho
     */
    public void exibirCaminhoCompleto(Regiao regiao) {
        if (regiao == null) {
            System.out.println("[ERRO] Região não encontrada.");
            return;
        }

        List<String> caminho = new ArrayList<>();
        Regiao atual = regiao;

        while (atual != null) {
            caminho.add(0, atual.getNome());
            atual = atual.getPai();
        }

        System.out.println("\n[CAMINHO COMPLETO]");
        System.out.println(String.join(" > ", caminho));
    }

    /**
     * Operação 8: Lista todas as regiões sem subdivisões (folhas)
     * @return Lista de regiões que são folhas
     */
    public List<Regiao> listarFolhas() {
        List<Regiao> folhas = new ArrayList<>();
        for (Regiao raiz : raizes) {
            coletarFolhasRecursivo(raiz, folhas);
        }
        return folhas;
    }

    /**
     * Método auxiliar recursivo para coletar folhas
     */
    private void coletarFolhasRecursivo(Regiao regiao, List<Regiao> folhas) {
        if (regiao.ehFolha()) {
            folhas.add(regiao);
        }

        for (Regiao filho : regiao.getFilhos()) {
            coletarFolhasRecursivo(filho, folhas);
        }
    }

    /**
     * Operação 9: Retorna a quantidade total de regiões cadastradas
     * @return Número total de regiões
     */
    public int contarTotalRegioes() {
        int total = 0;
        for (Regiao raiz : raizes) {
            total += contarRegiõesRecursivo(raiz);
        }
        return total;
    }

    /**
     * Método auxiliar recursivo para contar regiões
     */
    private int contarRegiõesRecursivo(Regiao regiao) {
        int total = 1; // Conta a própria região

        for (Regiao filho : regiao.getFilhos()) {
            total += contarRegiõesRecursivo(filho);
        }

        return total;
    }

    /**
     * Operação 10: Exibe a árvore de forma simplificada (apenas nomes)
     */
    public void exibirArvoreSimplificada() {
        if (raizes.isEmpty()) {
            System.out.println("\n[VAZIO] Nenhuma região cadastrada.");
            return;
        }

        System.out.println("\n" + Utils.repeat("=", 60));
        System.out.println("ÁRVORE HIERÁRQUICA SIMPLIFICADA");
        System.out.println(Utils.repeat("=", 60));
        
        for (Regiao raiz : raizes) {
            exibirArvoreSimplificadaRecursivo(raiz, "", true);
        }
        
        System.out.println(Utils.repeat("=", 60));
    }

    /**
     * Método auxiliar recursivo para exibir árvore simplificada
     */
    private void exibirArvoreSimplificadaRecursivo(Regiao regiao, String prefixo, boolean ehUltimo) {
        String conector = ehUltimo ? "└── " : "├── ";
        System.out.println(prefixo + conector + regiao.getNome());

        List<Regiao> filhos = regiao.getFilhos();
        for (int i = 0; i < filhos.size(); i++) {
            Regiao filho = filhos.get(i);
            boolean ehUltimoFilho = (i == filhos.size() - 1);
            String novoPrefixo = prefixo + (ehUltimo ? "    " : "│   ");
            exibirArvoreSimplificadaRecursivo(filho, novoPrefixo, ehUltimoFilho);
        }
    }

    /**
     * Retorna as raízes da árvore
     * @return Lista de regiões raízes
     */
    public List<Regiao> getRaizes() {
        return raizes;
    }
}
