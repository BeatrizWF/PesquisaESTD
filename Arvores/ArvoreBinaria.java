import java.util.function.Consumer;

public class ArvoreBinaria<T extends Comparable<T>> {
    class Elemento {
        Elemento elementopai;
        Elemento elementoesquerda;
        Elemento elementodireita;
        T valor;
    
        public Elemento(T valor) {
            this.valor = valor;
        }
    }
  
    private Elemento raiz;

    public boolean isVazia() { return raiz == null;
    }

    public Elemento adicionar(T valor) {
        Elemento e = new Elemento(valor);
        Elemento elementopai = this.raiz;

        while (elementopai != null) {
            if (valor.compareTo(elementopai.valor) < 0) {
                if (elementopai.elementoesquerda == null) {
                    e.elementopai = elementopai;
                    elementopai.elementoesquerda = e;
                    return e;
                } else {
                    elementopai = elementopai.elementoesquerda;
                }
            } else {
                if (elementopai.elementodireita == null) {
                    e.elementopai = elementopai;
                    elementopai.elementodireita = e;
                    return e;
                } else {
                    elementopai = elementopai.elementodireita;
                }
            }
        }

        this.raiz = e;
        return e;
    }
    
    public Elemento adicionar(Elemento elementopai, T valor) {
        Elemento e = new Elemento(valor);
        e.elementopai = elementopai;
         
        if (elementopai == null) { raiz = e;
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

    public void percorrer(Elemento e, Consumer<T> callback) {
        if (e != null) {
            if (e.valor == (Integer) 5) {
                System.out.println("");
            }
            
            callback.accept(e.valor);
            percorrer(e.elementoesquerda, callback);
            percorrer(e.elementodireita, callback);
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

    public int caminho(Elemento e) {
        int contador = 1;

        while (e.elementopai != null) { //  ANOTAÇÃO -> Enquanto não alcançamos a raiz
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
        Fila<ArvoreBinaria<T>.Elemento> fila = new Fila<>();

        fila.adicionar(raiz);

        while (!fila.isVazia()) {
            ArvoreBinaria<T>.Elemento e = fila.remover();

            // ANOTAÇÃO -> visitando o valor do elemento atual
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
        Pilha<ArvoreBinaria<T>.Elemento> pilha = new Pilha<>();

        pilha.adicionar(raiz);

        while (!pilha.isVazia()) {
            ArvoreBinaria<T>.Elemento e = pilha.remover();

            if (e.valor == (Integer) 5) {
                System.out.println("");
            }

            // ANOTAÇÃO -> visitando o valor do elemento atual
            callback.accept(e.valor); 

            if (e.elementodireita != null) {
                pilha.adicionar(e.elementodireita);
            }

            if (e.elementoesquerda != null) {
                pilha.adicionar(e.elementoesquerda);
            }
        }
    }

    public static void main(String args[]) {
        ArvoreBinaria<Integer> a = new ArvoreBinaria<>();
        
        // for (int i = 1; i <= 9; i++) {
        //     a.adicionar(i);
        // }

        a.adicionar(4);
        a.adicionar(2);
        a.adicionar(8);
        a.adicionar(1);
        a.adicionar(3);
        a.adicionar(6);
        a.adicionar(9);
        a.adicionar(5);
        a.adicionar(7);

        /*
        ArvoreBinaria<Integer>.Elemento n4 = a.adicionar(null, 4);
        ArvoreBinaria<Integer>.Elemento n2 = a.adicionar(n4, 2);
        ArvoreBinaria<Integer>.Elemento n8 = a.adicionar(n4, 8);
        ArvoreBinaria<Integer>.Elemento n1 = a.adicionar(n2, 1);
        ArvoreBinaria<Integer>.Elemento n3 = a.adicionar(n2, 3);
        ArvoreBinaria<Integer>.Elemento n6 = a.adicionar(n8, 6);
        ArvoreBinaria<Integer>.Elemento n9 = a.adicionar(n8, 9);
        ArvoreBinaria<Integer>.Elemento n5 = a.adicionar(n6, 5);
        ArvoreBinaria<Integer>.Elemento n7 = a.adicionar(n6, 7);

        n4.elementoesquerda = n2;
        n4.elementodireita = n8;

        n2.elementoesquerda = n1;
        n2.elementodireita = n3;

        n8.elementoesquerda = n6;
        n8.elementodireita = n9;

        n6.elementoesquerda = n5;
        n6.elementodireita = n7;
        */

        ArvoreBinaria<Integer>.Elemento e = a.pesquisar(a.raiz, 7);

        a.percorrer(v -> System.out.println(v));

        if (e != null) {
            System.out.println("Encontrou: " + a.caminho(e));
        } else {
            System.out.println("Não encontrou");
        }
    }
}