package org.example;

/**
 * Classe utilitária para funções comuns
 */
public class Utils {
    /**
     * Repete uma string um número de vezes
     * Compatível com Java 8+
     * @param str String a repetir
     * @param count Número de repetições
     * @return String repetida
     */
    public static String repeat(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
}
