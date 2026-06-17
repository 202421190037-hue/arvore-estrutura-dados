package org.example;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Classe CLI implementa a interface de linha de comando para interação com o sistema.
 * Fornece um menu interativo com as 10 operações principais.
 */
public class CLI {
    private SistemaOrganizacional sistema;
    private Scanner scanner;

    /**
     * Construtor da CLI
     * @param sistema Instância do sistema organizacional
     */
    public CLI(SistemaOrganizacional sistema) {
        this.sistema = sistema;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Inicia o menu interativo da CLI
     */
    public void iniciar() {
        exibirBemVindo();
        boolean ativo = true;

        while (ativo) {
            exibirMenu();
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1":
                    operacao1_InserirRegiao();
                    break;
                case "2":
                    operacao2_AssociarSubRegiao();
                    break;
                case "3":
                    operacao3_AlterarInformacoes();
                    break;
                case "4":
                    operacao4_RemoverRegiao();
                    break;
                case "5":
                    operacao5_ExibirEstrutura();
                    break;
                case "6":
                    operacao6_BuscarRegiao();
                    break;
                case "7":
                    operacao7_ExibirCaminho();
                    break;
                case "8":
                    operacao8_ListarFolhas();
                    break;
                case "9":
                    operacao9_ContarRegioes();
                    break;
                case "10":
                    operacao10_ExibirArvore();
                    break;
                case "0":
                    ativo = false;
                    System.out.println("\n✓ Sistema encerrado. Até logo!");
                    break;
                default:
                    System.out.println("\n[ERRO] Opção inválida. Tente novamente.");
            }
        }
    }

    /**
     * Exibe mensagem de boas-vindas
     */
    private void exibirBemVindo() {
        System.out.println("\n" + Utils.repeat("=", 60));
        System.out.println("SISTEMA DE ORGANIZAÇÃO DE MUNICÍPIOS E REGIÕES");
        System.out.println("Grupo 2 - Estrutura de Dados em Árvore");
        System.out.println(Utils.repeat("=", 60));
    }

    /**
     * Exibe o menu principal
     */
    private void exibirMenu() {
        System.out.println("\n" + Utils.repeat("-", 60));
        System.out.println("MENU PRINCIPAL");
        System.out.println(Utils.repeat("-", 60));
        System.out.println("1. Inserir região");
        System.out.println("2. Associar região como sub-região");
        System.out.println("3. Alterar informações de uma região");
        System.out.println("4. Remover região e subdivisões");
        System.out.println("5. Exibir estrutura territorial completa");
        System.out.println("6. Buscar região pelo nome");
        System.out.println("7. Exibir caminho completo de uma região");
        System.out.println("8. Listar regiões sem subdivisões (folhas)");
        System.out.println("9. Informar quantidade total de regiões");
        System.out.println("10. Exibir árvore simplificada");
        System.out.println("0. Sair");
        System.out.println(Utils.repeat("-", 60));
        System.out.print("Escolha uma opção: ");
    }

    /**
     * Operação 1: Insere uma nova região
     */
    private void operacao1_InserirRegiao() {
        System.out.println("\n[INSERIR REGIÃO]");
        System.out.print("Nome da região: ");
        String nome = scanner.nextLine().trim();
        
        if (nome.isEmpty()) {
            System.out.println("[ERRO] Nome não pode ser vazio.");
            return;
        }

        System.out.print("Tipo (País/Estado/Cidade/Bairro/outro): ");
        String tipo = scanner.nextLine().trim();
        
        if (tipo.isEmpty()) {
            tipo = "Desconhecido";
        }

        System.out.print("População: ");
        long populacao = 0;
        try {
            populacao = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[ERRO] População deve ser um número válido.");
            return;
        }

        Regiao novaRegiao = sistema.inserirRegiao(nome, tipo, populacao);
        System.out.println("✓ Região inserida com sucesso: " + novaRegiao);
    }

    /**
     * Operação 2: Associa uma região como sub-região de outra
     */
    private void operacao2_AssociarSubRegiao() {
        System.out.println("\n[ASSOCIAR SUB-REGIÃO]");
        
        // Lista todas as regiões para facilitar a seleção
        List<Regiao> todasRegioes = sistema.listarTodasRegioes();
        
        if (todasRegioes.isEmpty()) {
            System.out.println("[ERRO] Nenhuma região cadastrada. Insira regiões primeiro.");
            return;
        }
        
        System.out.println("\n--- REGIÕES DISPONÍVEIS ---");
        for (Regiao r : todasRegioes) {
            System.out.println(r);
        }
        System.out.println("---------------------------\n");
        
        System.out.print("Digite o ID da região PAI: ");
        int idPai = 0;
        try {
            idPai = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[ERRO] ID deve ser um número válido.");
            return;
        }
        
        Optional<Regiao> pai = sistema.buscarPorId(idPai);
        if (!pai.isPresent()) {
            System.out.println("[ERRO] Região pai não encontrada.");
            return;
        }

        System.out.print("Digite o ID da região FILHA: ");
        int idFilho = 0;
        try {
            idFilho = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[ERRO] ID deve ser um número válido.");
            return;
        }
        
        Optional<Regiao> filho = sistema.buscarPorId(idFilho);
        if (!filho.isPresent()) {
            System.out.println("[ERRO] Região filha não encontrada.");
            return;
        }

        if (sistema.associarSubRegiao(pai.get(), filho.get())) {
            System.out.println("✓ Associação realizada com sucesso.");
        } else {
            System.out.println("[ERRO] Não foi possível associar as regiões.");
        }
    }

    /**
     * Operação 3: Altera informações de uma região
     */
    private void operacao3_AlterarInformacoes() {
        System.out.println("\n[ALTERAR INFORMAÇÕES]");
        
        // Lista todas as regiões
        List<Regiao> todasRegioes = sistema.listarTodasRegioes();
        
        if (todasRegioes.isEmpty()) {
            System.out.println("[ERRO] Nenhuma região cadastrada.");
            return;
        }
        
        System.out.println("\n--- REGIÕES DISPONÍVEIS ---");
        for (Regiao r : todasRegioes) {
            System.out.println(r);
        }
        System.out.println("---------------------------\n");
        
        System.out.print("Digite o ID da região a alterar: ");
        int id = 0;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[ERRO] ID deve ser um número válido.");
            return;
        }
        
        Optional<Regiao> regiao = sistema.buscarPorId(id);
        if (!regiao.isPresent()) {
            System.out.println("[ERRO] Região não encontrada.");
            return;
        }

        Regiao r = regiao.get();
        
        System.out.print("Novo nome (deixar em branco para manter): ");
        String novoNome = scanner.nextLine().trim();
        
        System.out.print("Novo tipo (deixar em branco para manter): ");
        String novoTipo = scanner.nextLine().trim();
        
        System.out.print("Nova população (deixar em branco para manter): ");
        long novaPopulacao = -1;
        String popInput = scanner.nextLine().trim();
        if (!popInput.isEmpty()) {
            try {
                novaPopulacao = Long.parseLong(popInput);
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] População deve ser um número válido.");
                return;
            }
        }

        if (sistema.alterarInformacoes(r, 
                                       novoNome.isEmpty() ? null : novoNome,
                                       novoTipo.isEmpty() ? null : novoTipo,
                                       novaPopulacao)) {
            System.out.println("✓ Informações alteradas com sucesso.");
            System.out.println("   Novo dados: " + r);
        }
    }

    /**
     * Operação 4: Remove uma região e todas as suas subdivisões
     */
    private void operacao4_RemoverRegiao() {
        System.out.println("\n[REMOVER REGIÃO]");
        
        // Lista todas as regiões
        List<Regiao> todasRegioes = sistema.listarTodasRegioes();
        
        if (todasRegioes.isEmpty()) {
            System.out.println("[ERRO] Nenhuma região cadastrada.");
            return;
        }
        
        System.out.println("\n--- REGIÕES DISPONÍVEIS ---");
        for (Regiao r : todasRegioes) {
            System.out.println(r);
        }
        System.out.println("---------------------------\n");
        
        System.out.print("Digite o ID da região a remover: ");
        int id = 0;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[ERRO] ID deve ser um número válido.");
            return;
        }
        
        Optional<Regiao> regiao = sistema.buscarPorId(id);
        if (!regiao.isPresent()) {
            System.out.println("[ERRO] Região não encontrada.");
            return;
        }

        System.out.print("Tem certeza que deseja remover? (S/N): ");
        String confirmacao = scanner.nextLine().trim().toUpperCase();
        
        if (confirmacao.equals("S")) {
            if (sistema.removerRegiao(regiao.get())) {
                System.out.println("✓ Região removida com sucesso (incluindo todas as subdivisões).");
            } else {
                System.out.println("[ERRO] Não foi possível remover a região.");
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    /**
     * Operação 5: Exibe a estrutura territorial completa
     */
    private void operacao5_ExibirEstrutura() {
        sistema.exibirEstruturaTerritorial();
    }

    /**
     * Operação 6: Busca uma região pelo nome
     */
    private void operacao6_BuscarRegiao() {
        System.out.println("\n[BUSCAR REGIÃO]");
        
        // Lista todas as regiões
        List<Regiao> todasRegioes = sistema.listarTodasRegioes();
        
        if (todasRegioes.isEmpty()) {
            System.out.println("[ERRO] Nenhuma região cadastrada.");
            return;
        }
        
        System.out.println("\n--- REGIÕES DISPONÍVEIS ---");
        for (Regiao r : todasRegioes) {
            System.out.println(r);
        }
        System.out.println("---------------------------\n");
        
        System.out.print("Digite o ID da região: ");
        int id = 0;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[ERRO] ID deve ser um número válido.");
            return;
        }
        
        Optional<Regiao> regiao = sistema.buscarPorId(id);
        
        if (regiao.isPresent()) {
            System.out.println("\n✓ Região encontrada:");
            System.out.println("   " + regiao.get());
            System.out.println("   Subdivisões: " + regiao.get().getFilhos().size());
        } else {
            System.out.println("[ERRO] Região não encontrada.");
        }
    }

    /**
     * Operação 7: Exibe o caminho completo de uma região
     */
    private void operacao7_ExibirCaminho() {
        System.out.println("\n[CAMINHO COMPLETO]");
        
        // Lista todas as regiões
        List<Regiao> todasRegioes = sistema.listarTodasRegioes();
        
        if (todasRegioes.isEmpty()) {
            System.out.println("[ERRO] Nenhuma região cadastrada.");
            return;
        }
        
        System.out.println("\n--- REGIÕES DISPONÍVEIS ---");
        for (Regiao r : todasRegioes) {
            System.out.println(r);
        }
        System.out.println("---------------------------\n");
        
        System.out.print("Digite o ID da região: ");
        int id = 0;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[ERRO] ID deve ser um número válido.");
            return;
        }
        
        Optional<Regiao> regiao = sistema.buscarPorId(id);
        
        if (regiao.isPresent()) {
            sistema.exibirCaminhoCompleto(regiao.get());
        } else {
            System.out.println("[ERRO] Região não encontrada.");
        }
    }

    /**
     * Operação 8: Lista todas as regiões sem subdivisões
     */
    private void operacao8_ListarFolhas() {
        System.out.println("\n" + Utils.repeat("=", 60));
        System.out.println("REGIÕES SEM SUBDIVISÕES (FOLHAS)");
        System.out.println(Utils.repeat("=", 60));
        
        List<Regiao> folhas = sistema.listarFolhas();
        
        if (folhas.isEmpty()) {
            System.out.println("[VAZIO] Nenhuma folha encontrada.");
        } else {
            for (int i = 0; i < folhas.size(); i++) {
                System.out.printf("%2d. %s\n", i + 1, folhas.get(i));
            }
        }
        System.out.println(Utils.repeat("=", 60));
    }

    /**
     * Operação 9: Informa a quantidade total de regiões cadastradas
     */
    private void operacao9_ContarRegioes() {
        int total = sistema.contarTotalRegioes();
        System.out.println("\n" + Utils.repeat("=", 60));
        System.out.println("QUANTIDADE TOTAL DE REGIÕES");
        System.out.println(Utils.repeat("=", 60));
        System.out.println("Total: " + total + " regiões cadastradas");
        System.out.println(Utils.repeat("=", 60));
    }

    /**
     * Operação 10: Exibe a árvore simplificada
     */
    private void operacao10_ExibirArvore() {
        sistema.exibirArvoreSimplificada();
    }
}
