public class Pesquisa {
    public static int pesquisaSequencial(int chave, int v[]) {
        int contador = 1;
        
        for (int i = 0; i < v.length; i++) {
            contador += 2;
            
            if (v[i] == chave) {
                return contador;
            }
        }

        return contador;
    }

    public static int pesquisaSequencialSentinela(int chave, int v[]) {
        int contador = 1;
        int i = 0;
        int c[] = new int[v.length + 1];
        
        System.arraycopy(v, i, c, i, v.length);
        c[v.length] = chave;

        while (c[i] != chave) {
            contador++;
            i++;
        }

        contador++;
        
        if (i < v.length) {
            return contador;
        }
        
        return contador;
    }
    
    public static int pesquisaBinaria(int chave, int v[]) {
        int inicio = 0, fim = v.length - 1, meio;
        int contador = 1;

        while (inicio <= fim) {
            meio = (fim + inicio) / 2;
            
            contador += 2;

            if (chave == v[meio]) {
                return contador;
            }
            
            contador++;
            
            if (chave > v[meio]) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }

        return contador;
    }

    public static int pesquisaInterpolacao(int chave, int v[]) {
        int inicio = 0, fim = v.length - 1, meio;
        int contador = 1;

        while (inicio <= fim && chave >= v[inicio] && chave <= v[fim]) {
            meio = inicio + ((fim - inicio) * (chave - v[inicio])) / (v[fim] - v[inicio]);
            
            contador += 2;

            if (chave == v[meio]) {
                return contador;
            }
            
            contador++;

            if (chave > v[meio]) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }

        return contador;
    }

    public static int[] vetorOrdenado(int n) {
        int v[] = new int[n];

        for (int i = 0; i < v.length; i++) {
            v[i] = i + 1;
        }

        return v;
    }

    public static int[] vetorAleatorio(int n) {
        int v[] = new int[n];

        for (int i = 0; i < v.length; i++) {
            v[i] = (int) (Math.random() * n * 1000);
        }

        return v;
    }
}
