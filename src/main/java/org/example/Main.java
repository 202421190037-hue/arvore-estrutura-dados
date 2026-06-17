package org.example;

/**
 * Classe Main - Ponto de entrada da aplicação
 * Sistema de Organização de Municípios e Regiões
 * 
 * Grupo 2 - Estrutura de Dados em Árvore
 */
public class Main {
    public static void main(String[] args) {
        // Cria instância do sistema
        SistemaOrganizacional sistema = new SistemaOrganizacional();
        
        Regiao brasil = sistema.inserirRegiao("Brasil", "País", 215000000);
        Regiao bahia = sistema.inserirRegiao("Bahia", "Estado", 15000000);
        Regiao minasGerais = sistema.inserirRegiao("Minas Gerais", "Estado", 21000000);
        Regiao vitoriaDaConquista = sistema.inserirRegiao("Vitória da Conquista", "Cidade", 350000);
        Regiao salvador = sistema.inserirRegiao("Salvador", "Cidade", 2900000);
        Regiao beloHorizonte = sistema.inserirRegiao("Belo Horizonte", "Cidade", 2500000);
        Regiao bairroCandeias = sistema.inserirRegiao("Bairro Candeias", "Bairro", 45000);
        Regiao bairroRecreio = sistema.inserirRegiao("Bairro Recreio", "Bairro", 38000);
        sistema.associarSubRegiao(brasil, bahia);
        sistema.associarSubRegiao(brasil, minasGerais);
        sistema.associarSubRegiao(bahia, vitoriaDaConquista);
        sistema.associarSubRegiao(bahia, salvador);
        sistema.associarSubRegiao(minasGerais, beloHorizonte);
        sistema.associarSubRegiao(vitoriaDaConquista, bairroCandeias);
        sistema.associarSubRegiao(vitoriaDaConquista, bairroRecreio);
        // Cria e inicia a CLI
        CLI cli = new CLI(sistema);
        cli.iniciar();
    }
}