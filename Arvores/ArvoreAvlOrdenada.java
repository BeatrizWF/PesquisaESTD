import java.util.function.Consumer;

public class ArvoreAvlOrdenada<T extends Comparable<T>> {
    class Elemento {
        Elemento elementopai;
        Elemento elementoesquerda;
        Elemento elementodireita;
        T valor;

        public Elemento(T valor) {
            this.valor = valor;
        }
    }

    public static int contador;
    private Elemento raiz;

    public boolean isVazia() { return raiz == null;
    }

    public Elemento adicionar(T valor) {
        Elemento e = new Elemento(valor);
        Elemento elementopai = this.raiz;
        contador++;

        // Não precisa -> System.out.println("Adicionando " + valor);

        while (elementopai != null) {
            contador++;

            if (valor.compareTo(elementopai.valor) < 0) {
                if (elementopai.elementoesquerda == null) {
                    e.elementopai = elementopai;
                    elementopai.elementoesquerda = e;
                    balanceamento(elementopai);

                    return e;
                } else {
                    elementopai = elementopai.elementoesquerda;
                }
            } else {
                if (elementopai.elementodireita == null) {
                    e.elementopai = elementopai;
                    elementopai.elementodireita = e;
                    balanceamento(elementopai);

                    return e;
                } else {
                    elementopai = elementopai.elementodireita;
                }
            }
        }

        this.raiz = e;
        return e;
    }

    public void balanceamento(Elemento elemento) {
        while (elemento != null) {

            int fator = fb(elemento);

            if (fator > 1) {
                //  ANOTAÇÃO ->  Arvore mais profunda para elementoesquerda, rotação para a elementodireita
                if (fb(elemento.elementoesquerda) > 0) {
                    // Não precisa -> System.out.println("RSD(" + elemento.valor + ")");
                    rsd(elemento);
                } else {
                    // Não precisa -> System.out.println("RDD(" + elemento.valor + ")");
                    rdd(elemento);
                }
            } else if (fator < -1) {
                //  ANOTAÇÃO ->  Arvore mais profunda para elementodireita, rotação para a elementoesquerda
                if (fb(elemento.elementodireita) < 0) {
                    // Não precisa -> System.out.println("RSE(" + elemento.valor + ")");
                    rse(elemento);
                } else {
                    // Não precisa -> System.out.println("RDE(" + elemento.valor + ")");
                    rde(elemento);
                }
            }

            elemento = elemento.elementopai;
        }
    }

    public Elemento adicionar(Elemento elementopai, T valor) {
        Elemento e = new Elemento(valor);

        e.elementopai = elementopai;

        if (elementopai == null) {
            raiz = e;
        }

        return e;
    }

    public void remover(Elemento e) {
        if (e.elementoesquerda != null)
            remover(e.elementoesquerda);

        if (e.elementodireita != null)
            remover(e.elementodireita);

        if (e.elementopai == null) {
            raiz = null;
        } else {
            if (e.elementopai.elementoesquerda == e) {
                e.elementopai.elementoesquerda = null;
            } else {
                e.elementopai.elementodireita = null;
            }
        }
    }



    public Elemento pesquisar(Elemento e, T valor) {
        while (e != null) {
            if (e.valor.equals(valor)) {
                return e;
            } else if (valor.compareTo(e.valor) > 0) {
                e = e.elementodireita;
            } else {
                e = e.elementoesquerda;
            }
        }

        return null;
    }

    public void percorrer(Elemento e, Consumer<T> callback) {
        if (e != null) {
            if (e.valor == (Integer) 5) {
                // Não precisa -> System.out.println("");
            }

            callback.accept(e.valor);
            percorrer(e.elementoesquerda, callback);
            percorrer(e.elementodireita, callback);
        }
    }

    public int caminho(Elemento e) {
        int contador = 1;

        while (e.elementopai != null) { //  ANOTAÇÃO ->  Enquanto não alcançamos a raiz faz-se:
            contador++;
            e = e.elementopai;
        }

        return contador;
    }

    public void percorrerInOrder(Elemento e, Consumer<T> callback) {
        if (e != null) {
            percorrerInOrder(e.elementoesquerda, callback);
            callback.accept(e.valor);
            percorrerInOrder(e.elementodireita, callback);
        }
    }

    public void percorrerPosOrder(Elemento e, Consumer<T> callback) {
        if (e != null) {
            percorrerPosOrder(e.elementoesquerda, callback);
            percorrerPosOrder(e.elementodireita, callback);
            callback.accept(e.valor);
        }
    }

    public void percorrer(Consumer<T> callback) {
        this.percorrer(raiz, callback);
    }

    public void percorrerInOrder(Consumer<T> callback) {
        this.percorrerInOrder(raiz, callback);
    }

    public void percorrerPosOrder(Consumer<T> callback) {
        this.percorrerPosOrder(raiz, callback);
    }

    public void percorrerLargura(Consumer<T> callback) {
        Fila<ArvoreAvlOrdenada<T>.Elemento> fila = new Fila<>();

        fila.adicionar(raiz);

        while (!fila.isVazia()) {
            ArvoreAvlOrdenada<T>.Elemento e = fila.remover();

            //  ANOTAÇÃO ->  visitando o valor do elemento atual
            callback.accept(e.valor);

            if (e.elementoesquerda != null) {
                fila.adicionar(e.elementoesquerda);
            }

            if (e.elementodireita != null) {
                fila.adicionar(e.elementodireita);
            }
        }
    }

    public void percorrerProfundidade(Consumer<T> callback) {
        Pilha<ArvoreAvlOrdenada<T>.Elemento> pilha = new Pilha<>();

        pilha.adicionar(raiz);

        while (!pilha.isVazia()) {
            ArvoreAvlOrdenada<T>.Elemento e = pilha.remover();

            //  ANOTAÇÃO ->  visitando o valor do elemento atual
            callback.accept(e.valor);

            if (e.elementodireita != null) {
                pilha.adicionar(e.elementodireita);
            }

            if (e.elementoesquerda != null) {
                pilha.adicionar(e.elementoesquerda);
            }
        }
    }

    private int altura(Elemento e) {

        // contador+=1;

        int elementoesquerda = 0, elementodireita = 0;

        if (e.elementoesquerda != null) {
            elementoesquerda = altura(e.elementoesquerda) + 1;
        }

        if (e.elementodireita != null) {
            elementodireita = altura(e.elementodireita) + 1;
        }

        return elementoesquerda > elementodireita ? elementoesquerda : elementodireita;
    }

    private int fb(Elemento e) {

        contador++;

        int elementoesquerda = 0, elementodireita = 0;

        if (e.elementoesquerda != null) {
            elementoesquerda = altura(e.elementoesquerda) + 1;
        }

        if (e.elementodireita != null) {
            elementodireita = altura(e.elementodireita) + 1;
        }

        return elementoesquerda - elementodireita;
    }

    private Elemento rse(Elemento e) {
        Elemento elementopai = e.elementopai;
        Elemento elementodireita = e.elementodireita;

        e.elementodireita = elementodireita.elementoesquerda;
        e.elementopai = elementodireita;

        elementodireita.elementoesquerda = e;
        elementodireita.elementopai = elementopai;

        if (elementodireita.elementopai == null) {
            this.raiz = elementodireita;
        } else {
            if (elementopai.elementoesquerda == e) {
                elementopai.elementoesquerda = elementodireita;
            } else {
                elementopai.elementodireita = elementodireita;
            }
        }

        return elementodireita;
    }

    private Elemento rsd(Elemento e) {
        Elemento elementopai = e.elementopai;
        Elemento elementoesquerda = e.elementoesquerda;

        e.elementoesquerda = elementoesquerda.elementodireita;
        e.elementopai = elementoesquerda;

        elementoesquerda.elementodireita = e;
        elementoesquerda.elementopai = elementopai;

        if (elementoesquerda.elementopai == null) {
            this.raiz = elementoesquerda;
        } else {
            if (elementopai.elementoesquerda == e) {
                elementopai.elementoesquerda = elementoesquerda;
            } else {
                elementopai.elementodireita = elementoesquerda;
            }
        }

        return elementoesquerda;
    }

    private Elemento rde(Elemento e) {
        e.elementodireita = rsd(e.elementodireita);
        return rse(e);
    }

    private Elemento rdd(Elemento e) {
        e.elementoesquerda = rse(e.elementoesquerda);
        return rsd(e);
    }

    public static void vetorOrdenado(int n) {
        ArvoreAvlOrdenada<Integer> a = new ArvoreAvlOrdenada<>();

        for (int i = 0; i < n; i++) {
            a.adicionar(i);
        }

    }

    public static void vetorAleatorio(int n) {

        ArvoreAvlOrdenada<Integer> a = new ArvoreAvlOrdenada<>();
        ArvoreAvlOrdenada<Integer>.Elemento elemento = null;
        int UmNumero;

        for (int i = 0; i < n; i++) {

            do {

                UmNumero = ArvoreAvlOrdenada.gerarUmNumeroAleatorio(n);
                elemento = a.pesquisar(a.raiz, UmNumero);

            } while (elemento != null);
            a.adicionar(UmNumero);
        }

    }

    public static int gerarUmNumeroAleatorio(int n) { int v = (int) (Math.random() * n * 1000);
        return v;
    }

}