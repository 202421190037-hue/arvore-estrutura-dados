# Sistema de Organização de Municípios e Regiões

## Integrantes

Herbert Duarte (202311190025@ifba.edu.br)
Guilherme France (202511190039@ifba.edu.br)
Fábio Alves (202511190044@ifba.edu.br)
Erica Menezes (202511190030@ifba.edu.br)

## Descrição

Sistema Java para gerenciar divisões territoriais de forma hierárquica usando estrutura de árvore N-ária. O usuário interage com o sistema através de uma interface por linha de comando (CLI).

## Funcionalidades

O sistema implementa as 10 operações solicitadas:

1. **Inserir Região** - Adiciona uma nova região ao sistema com nome, tipo e população
2. **Associar Sub-Região** - Estabelece relação hierárquica entre duas regiões (pai-filho)
3. **Alterar Informações** - Modifica dados de uma região existente
4. **Remover Região** - Remove uma região e todas as suas subdivisões
5. **Exibir Estrutura Territorial** - Mostra toda a hierarquia com detalhes de população
6. **Buscar Região** - Localiza uma região pelo nome na árvore
7. **Exibir Caminho Completo** - Mostra o caminho da raiz até uma região (ex: Brasil > Bahia > Vitória da Conquista)
8. **Listar Folhas** - Exibe todas as regiões sem subdivisões
9. **Contar Total** - Informa a quantidade total de regiões cadastradas
10. **Exibir Árvore Simplificada** - Visualiza a estrutura hierárquica de forma visual

## Exemplo de Uso

### Estrutura de Dados Típica:
```
Brasil (País) - População: 215.000.000
├── Bahia (Estado) - População: 15.000.000
│   ├── Vitória da Conquista (Cidade) - População: 350.000
│   │   ├── Bairro Candeias (Bairro) - População: 45.000
│   │   └── Bairro Recreio (Bairro) - População: 38.000
│   └── Salvador (Cidade) - População: 2.900.000
└── Minas Gerais (Estado) - População: 21.000.000
    └── Belo Horizonte (Cidade) - População: 2.500.000
```

## Compilação e Execução

### Compilar:
```bash
javac -encoding UTF-8 -d target/classes src/main/java/org/example/*.java
```

### Executar:
```bash
java -cp target/classes org.example.Main
```

## Estrutura do Projeto

```
src/main/java/org/example/
├── Main.java                      # Ponto de entrada
├── Regiao.java                    # Classe que representa um nó da árvore
├── SistemaOrganizacional.java    # Gerenciador da estrutura (10 operações)
├── CLI.java                       # Interface de linha de comando
└── Utils.java                     # Funções utilitárias
```

## Características Técnicas

- **Estrutura de Dados**: Árvore N-ária com nós bidirecionais (pai-filho)
- **Collections**: `ArrayList<Regiao>`, `List<Regiao>`
- **Generics**: Type-safe com `List<T>`
- **Algoritmos**: Recursivos para busca, contagem, navegação
- **Interface**: CLI interativa com menu numerado
- **Compatibilidade**: Java 8 ou superior
- **Encoding**: UTF-8 para suporte a caracteres especiais

## Classes Principais

### Regiao.java
Representa um nó na árvore hierárquica com propriedades:
- Nome da região
- Tipo (País, Estado, Cidade, Bairro, etc)
- População
- Referência ao pai
- Lista de filhos (sub-regiões)

### SistemaOrganizacional.java
Gerenciador da árvore que implementa as 10 operações com métodos recursivos para:
- Inserção e busca
- Navegação e exibição
- Contagem e relatórios

### CLI.java
Interface interativa que:
- Exibe menu numerado
- Processa entrada do usuário
- Valida dados
- Trata exceções

## Validação de Entrada

- Nomes não podem ser vazios
- População deve ser um número inteiro não-negativo
- Confirmação antes de remover regiões
- Busca case-insensitive

## Notas Importantes

- A raiz da árvore é definida automaticamente na primeira inserção
- Ao remover uma região, todas as suas subdivisões são removidas
- As operações de busca e contagem utilizam recursão para percorrer a árvore
- A interface CLI mantém o sistema ativo até que o usuário escolha "Sair"

## Autor

Desenvolvido como projeto de Estrutura de Dados (Grupo 2)

---
