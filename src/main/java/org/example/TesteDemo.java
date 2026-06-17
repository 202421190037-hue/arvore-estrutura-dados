package org.example;

/**
 * Classe TesteDemo - Demonstra o funcionamento do sistema com dados pré-carregados
 * Cria uma estrutura territorial de exemplo: Brasil com Bahia e Minas Gerais
 */
public class TesteDemo {
    public static void main(String[] args) {
        // Criar sistema
        SistemaOrganizacional sistema = new SistemaOrganizacional();
        
        // Inserir regiões
        System.out.println("=== TESTE DE DEMONSTRAÇÃO ===\n");
        System.out.println("Inserindo regiões...\n");
        
        Regiao brasil = sistema.inserirRegiao("Brasil", "País", 215000000);
        Regiao bahia = sistema.inserirRegiao("Bahia", "Estado", 15000000);
        Regiao minasGerais = sistema.inserirRegiao("Minas Gerais", "Estado", 21000000);
        Regiao vitoriaDaConquista = sistema.inserirRegiao("Vitória da Conquista", "Cidade", 350000);
        Regiao salvador = sistema.inserirRegiao("Salvador", "Cidade", 2900000);
        Regiao beloHorizonte = sistema.inserirRegiao("Belo Horizonte", "Cidade", 2500000);
        Regiao bairroCandeias = sistema.inserirRegiao("Bairro Candeias", "Bairro", 45000);
        Regiao bairroRecreio = sistema.inserirRegiao("Bairro Recreio", "Bairro", 38000);
        
        // Associar regiões
        System.out.println("Associando regiões hierarquicamente...\n");
        
        sistema.associarSubRegiao(brasil, bahia);
        sistema.associarSubRegiao(brasil, minasGerais);
        sistema.associarSubRegiao(bahia, vitoriaDaConquista);
        sistema.associarSubRegiao(bahia, salvador);
        sistema.associarSubRegiao(minasGerais, beloHorizonte);
        sistema.associarSubRegiao(vitoriaDaConquista, bairroCandeias);
        sistema.associarSubRegiao(vitoriaDaConquista, bairroRecreio);
        
        // Exibir estrutura
        System.out.println("\n--- OPERAÇÃO 5: Exibir Estrutura Territorial ---");
        sistema.exibirEstruturaTerritorial();
        
        // Exibir árvore simplificada
        System.out.println("\n--- OPERAÇÃO 10: Exibir Árvore Simplificada ---");
        sistema.exibirArvoreSimplificada();
        
        // Buscar região
        System.out.println("\n--- OPERAÇÃO 6: Buscar Região ---");
        java.util.Optional<Regiao> resultado = sistema.buscarPorNome("Salvador");
        if (resultado.isPresent()) {
            System.out.println("Encontrado: " + resultado.get());
        }
        
        // Caminho completo
        System.out.println("\n--- OPERAÇÃO 7: Exibir Caminho Completo ---");
        sistema.exibirCaminhoCompleto(bairroCandeias);
        
        // Listar folhas
        System.out.println("\n--- OPERAÇÃO 8: Listar Folhas (sem subdivisões) ---");
        java.util.List<Regiao> folhas = sistema.listarFolhas();
        System.out.println("Total de folhas: " + folhas.size());
        for (Regiao folha : folhas) {
            System.out.println("  - " + folha.getNome());
        }
        
        // Contar total
        System.out.println("\n--- OPERAÇÃO 9: Contar Total de Regiões ---");
        int total = sistema.contarTotalRegioes();
        System.out.println("Total de regiões cadastradas: " + total);
        
        // Alterar informações
        System.out.println("\n--- OPERAÇÃO 3: Alterar Informações ---");
        sistema.alterarInformacoes(salvador, "Salvador (Atualizado)", "Metrópole", 3000000);
        System.out.println("Salvador atualizado: " + salvador);
        
        // Remover região
        System.out.println("\n--- OPERAÇÃO 4: Remover Região ---");
        System.out.println("Removendo 'Bairro Recreio'...");
        sistema.removerRegiao(bairroRecreio);
        System.out.println("✓ Região removida");
        
        // Exibir estrutura final
        System.out.println("\n--- Estrutura Final ---");
        sistema.exibirArvoreSimplificada();
        
        System.out.println("\n=== FIM DO TESTE ===");
    }
}
