import java.util.function.Consumer;

public class ArvoreRubroNegraAleatoria<T extends Comparable<T>> {
    enum Cor {
        Vermelho,
        Preto
    }
    
    class Elemento {
        Elemento elementopai;
        Elemento elementoesquerda;
        Elemento elementodireita;
        Cor cor;
        T valor;
    
        public Elemento(T valor) {
            this.valor = valor;
        }
    }

    public ArvoreRubroNegraAleatoria() {
        nulo = new Elemento(null);
        nulo.cor = Cor.Preto;
        raiz = nulo;
    }
    
    private Elemento raiz;
    private Elemento nulo;
    public static int contador;

    public boolean isVazia() { return raiz == nulo;
    }

    public Elemento adicionar(T valor) {
        Elemento e = new Elemento(valor);
        e.cor = Cor.Vermelho;
        e.elementoesquerda = nulo;
        e.elementodireita = nulo;
        e.elementopai = nulo;
        Elemento elementopai = this.raiz;

        // Não precisa ->  System.out.println("Adicionando " + valor);

        while (elementopai != nulo) {
            contador ++; 

            if (valor.compareTo(elementopai.valor) < 0) {
                if (elementopai.elementoesquerda == nulo) {
                    e.elementopai = elementopai;
                    elementopai.elementoesquerda = e;
                    balanceamento(e);
                    
                    return e;
                } else {
                    elementopai = elementopai.elementoesquerda;
                }
            } else {
                if (elementopai.elementodireita == nulo) {
                    e.elementopai = elementopai;
                    elementopai.elementodireita = e;
                    balanceamento(e);

                    return e;
                } else {
                    elementopai = elementopai.elementodireita;
                }
            }
        }

        this.raiz = e;
        balanceamento(e);
        
        return e;
    }

    public void balanceamento(Elemento e) 
    {
        while (e.elementopai.cor == Cor.Vermelho) {  //   ANOTAÇÃO -> Garante que todos os níveis foram balanceados 
            
            contador ++; 

            Elemento elementopai = e.elementopai;
            Elemento avo = elementopai.elementopai;

            if (elementopai == avo.elementoesquerda) {  //   ANOTAÇÃO -> Identifica o lado (elementoesquerda ou elementodireita)
                Elemento tio = avo.elementodireita;
                        
                if (tio.cor == Cor.Vermelho) {
                    tio.cor = Cor.Preto;  //   ANOTAÇÃO -> Resolve o caso 2
                    elementopai.cor = Cor.Preto; 
                    avo.cor = Cor.Vermelho;
                    e = avo;  //   ANOTAÇÃO -> Vai para o nível anterior (avô)
                } else {
                    if (e == elementopai.elementodireita) {
                        e = elementopai;  //   ANOTAÇÃO -> Vai para o nível anterior
                        rse(e);  //   ANOTAÇÃO -> Resolve o caso 3
                    } else {
                        elementopai.cor = Cor.Preto;  //   ANOTAÇÃO -> Resolve o caso 4
                        avo.cor = Cor.Vermelho;
                        rsd(avo);
                    }
                }
            } else {
                Elemento tio = avo.elementoesquerda;
                        
                if (tio.cor == Cor.Vermelho) {
                    tio.cor = Cor.Preto;  //   ANOTAÇÃO -> Resolve o caso 2
                    elementopai.cor = Cor.Preto; 
                    avo.cor = Cor.Vermelho;
                    e = avo;  //   ANOTAÇÃO -> Vai para o nível anterior (avô)
                } else {
                    if (e == elementopai.elementoesquerda) {
                        e = elementopai;  //   ANOTAÇÃO -> Vai para o nível anterior
                        rsd(e);  //   ANOTAÇÃO -> Resolve o caso 3
                    } else {
                        elementopai.cor = Cor.Preto;  //   ANOTAÇÃO -> Resolve o caso 4
                        avo.cor = Cor.Vermelho;
                        rse(avo);
                    }
                }
            }
        }
        
        raiz.cor = Cor.Preto;  //   ANOTAÇÃO -> Resolve caso 1
    }

    private void rse(Elemento e) {

        contador ++;

        Elemento elementodireita = e.elementodireita;
        e.elementodireita = elementodireita.elementoesquerda; 
          
        if (elementodireita.elementoesquerda != nulo) {
          elementodireita.elementoesquerda.elementopai = e;
        }
          
        elementodireita.elementopai = e.elementopai;  //   ANOTAÇÃO -> Se houver filho à elementoesquerda em elementodireita, ele será elementopai do nó
              
        if (e.elementopai == nulo) {
            raiz = elementodireita;  //   ANOTAÇÃO -> Se nó for raiz, o nó elementodireita será a nova raiz da árvore
        } else if (e == e.elementopai.elementoesquerda) {
            e.elementopai.elementoesquerda = elementodireita;  //   ANOTAÇÃO -> Corrige relação elementopai-filho do novo elementopai (elementoesquerda)
        } else {
            e.elementopai.elementodireita = elementodireita;  //   ANOTAÇÃO -> Corrige relação elementopai-filho do novo elementopai (elementodireita)
        }
          
        elementodireita.elementoesquerda = e;  //   ANOTAÇÃO -> Corrige relação elementopai-filho entre o nó pivô e o nó à elementodireita
        e.elementopai = elementodireita;
    }

    private void rsd(Elemento e) {

        contador ++; 

        Elemento elementoesquerda = e.elementoesquerda;
        e.elementoesquerda = elementoesquerda.elementodireita;
              
        if (elementoesquerda.elementodireita != nulo) {
            elementoesquerda.elementodireita.elementopai = e;  //   ANOTAÇÃO -> Se houver filho à elementodireita em elementoesquerda, ele será elementopai do nó
        }
              
        elementoesquerda.elementopai = e.elementopai;  //   ANOTAÇÃO -> Ajusta no elementopai do nó à elementoesquerda
              
        if (e.elementopai == nulo) {
            raiz = elementoesquerda;  //   ANOTAÇÃO -> Se nó for raiz, o nó elementoesquerda será a nova raiz da árvore
        } else if (e == e.elementopai.elementoesquerda) {
            e.elementopai.elementoesquerda = elementoesquerda;  //   ANOTAÇÃO -> Corrige relação elementopai-filho do novo elementopai (elementoesquerda)
        } else {
            e.elementopai.elementodireita = elementoesquerda;  //   ANOTAÇÃO -> Corrige relação elementopai-filho do novo elementopai (elementodireita)
        }
              
        elementoesquerda.elementodireita = e;  //   ANOTAÇÃO -> Corrige relação elementopai-filho entre o nó pivô e o nó à elementoesquerda
        e.elementopai = elementoesquerda;
    }
    
    public void percorrer(Elemento e, Consumer<T> callback) {
        if (e != nulo) {
            percorrer(e.elementoesquerda, callback);
            callback.accept(e.valor);
            percorrer(e.elementodireita, callback);
        }
    }

    public Elemento pesquisar(Elemento e, T valor) {
        while (e != nulo) {
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

        while (e.elementopai != nulo) { //Enquanto não alcançamos a raiz
            contador++;
            e = e.elementopai;
        }

        return contador;
    }

    public void percorrerInOrder(Elemento e, Consumer<T> callback) {
        if (e != nulo) {
            percorrerInOrder(e.elementoesquerda, callback);
            callback.accept(e.valor);
            percorrerInOrder(e.elementodireita, callback);
        }
    }

    public void percorrerPosOrder(Elemento e, Consumer<T> callback) {
        if (e != nulo) {
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
        Fila<ArvoreRubroNegraAleatoria<T>.Elemento> fila = new Fila<>();

        fila.adicionar(raiz);

        while (!fila.isVazia()) {
            ArvoreRubroNegraAleatoria<T>.Elemento e = fila.remover();

            //  ANOTAÇÃO -> visitando o valor do elemento atual
            callback.accept(e.valor); 

            if (e.elementoesquerda != nulo) {
                fila.adicionar(e.elementoesquerda);
            }

            if (e.elementodireita != nulo) {
                fila.adicionar(e.elementodireita);
            }
        }
    }

    public void percorrerProfundidade(Consumer<T> callback) {
        Pilha<ArvoreRubroNegraAleatoria<T>.Elemento> pilha = new Pilha<>();

        pilha.adicionar(raiz);

        while (!pilha.isVazia()) {
            ArvoreRubroNegraAleatoria<T>.Elemento e = pilha.remover();

            //  ANOTAÇÃO -> visitando o valor do elemento atual
            callback.accept(e.valor); 

            if (e.elementodireita != nulo) {
                pilha.adicionar(e.elementodireita);
            }

            if (e.elementoesquerda != nulo) {
                pilha.adicionar(e.elementoesquerda);
            }
        }
    }

    
    public static void vetorOrdenado(int n) {

        ArvoreRubroNegraAleatoria<Integer> a = new ArvoreRubroNegraAleatoria<>();

        for (int i = 0; i < n; i++) {
            a.adicionar(i);
        }

        
    }

    public static void vetorAleatorio(int n) {
        ArvoreRubroNegraAleatoria<Integer> a = new ArvoreRubroNegraAleatoria<>();
        for (int i = 0; i < n; i++) {
            a.adicionar((int) (Math.random() * n * 1000));
        }

    }

}