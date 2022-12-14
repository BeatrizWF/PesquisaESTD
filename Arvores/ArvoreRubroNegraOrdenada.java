import java.util.function.Consumer;

public class ArvoreRubroNegraOrdenada<T extends Comparable<T>> {
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

    public ArvoreRubroNegraOrdenada() {
        nulo = new Elemento(null);
        nulo.cor = Cor.Preto;

        raiz = nulo;
    }
    
    private Elemento raiz;
    private Elemento nulo;

    public static int contador;

    public boolean isVazia() {
        return raiz == nulo;
    }

    public Elemento adicionar(T valor) {

        Elemento e = new Elemento(valor);
        e.cor = Cor.Vermelho;
        e.elementoesquerda = nulo;
        e.elementodireita = nulo;
        e.elementopai = nulo;
        
        Elemento elementopai = this.raiz;

        //System.out.println("Adicionando " + valor);

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
        while (e.elementopai.cor == Cor.Vermelho) {  //   ANOTA????O -> Garante que todos os n??veis foram balanceados 
            
            contador ++; 

            Elemento elementopai = e.elementopai;
            Elemento avo = elementopai.elementopai;

            if (elementopai == avo.elementoesquerda) {  //   ANOTA????O -> Identifica o lado (elementoesquerda ou elementodireita)
                Elemento tio = avo.elementodireita;
                        
                if (tio.cor == Cor.Vermelho) {
                    tio.cor = Cor.Preto;  //   ANOTA????O -> Resolve o caso 2
                    elementopai.cor = Cor.Preto; 
                    avo.cor = Cor.Vermelho;
                    e = avo;  //   ANOTA????O -> Vai para o n??vel anterior (av??)
                } else {
                    if (e == elementopai.elementodireita) {
                        e = elementopai;  //   ANOTA????O -> Vai para o n??vel anterior
                        rse(e);  //   ANOTA????O -> Resolve o caso 3
                    } else {
                        elementopai.cor = Cor.Preto;  //   ANOTA????O -> Resolve o caso 4
                        avo.cor = Cor.Vermelho;
                        rsd(avo);
                    }
                }
            } else {
                Elemento tio = avo.elementoesquerda;
                        
                if (tio.cor == Cor.Vermelho) {
                    tio.cor = Cor.Preto;  //   ANOTA????O -> Resolve o caso 2
                    elementopai.cor = Cor.Preto; 
                    avo.cor = Cor.Vermelho;
                    e = avo;  //   ANOTA????O -> Vai para o n??vel anterior (av??)
                } else {
                    if (e == elementopai.elementoesquerda) {
                        e = elementopai;  //   ANOTA????O -> Vai para o n??vel anterior
                        rsd(e);  //   ANOTA????O -> Resolve o caso 3
                    } else {
                        elementopai.cor = Cor.Preto;  //   ANOTA????O -> Resolve o caso 4
                        avo.cor = Cor.Vermelho;
                        rse(avo);
                    }
                }
            }
        }
        
        raiz.cor = Cor.Preto;  //   ANOTA????O -> Resolve caso 1
    }

    private void rse(Elemento e) {

        contador ++;

        Elemento elementodireita = e.elementodireita;
        e.elementodireita = elementodireita.elementoesquerda; 
          
        if (elementodireita.elementoesquerda != nulo) {
          elementodireita.elementoesquerda.elementopai = e;
        }
          
        elementodireita.elementopai = e.elementopai;  //   ANOTA????O -> Se houver filho ?? elementoesquerda em elementodireita, ele ser?? elementopai do n??
              
        if (e.elementopai == nulo) {
            raiz = elementodireita;  //   ANOTA????O -> Se n?? for raiz, o n?? elementodireita ser?? a nova raiz da ??rvore
        } else if (e == e.elementopai.elementoesquerda) {
            e.elementopai.elementoesquerda = elementodireita;  //   ANOTA????O -> Corrige rela????o elementopai-filho do novo elementopai (elementoesquerda)
        } else {
            e.elementopai.elementodireita = elementodireita;  //   ANOTA????O -> Corrige rela????o elementopai-filho do novo elementopai (elementodireita)
        }
          
        elementodireita.elementoesquerda = e;  //   ANOTA????O -> Corrige rela????o elementopai-filho entre o n?? piv?? e o n?? ?? elementodireita
        e.elementopai = elementodireita;
    }

    private void rsd(Elemento e) {

        contador ++; 

        Elemento elementoesquerda = e.elementoesquerda;
        e.elementoesquerda = elementoesquerda.elementodireita;
              
        if (elementoesquerda.elementodireita != nulo) {
            elementoesquerda.elementodireita.elementopai = e;  //   ANOTA????O -> Se houver filho ?? elementodireita em elementoesquerda, ele ser?? elementopai do n??
        }
              
        elementoesquerda.elementopai = e.elementopai;  //   ANOTA????O -> Ajusta no elementopai do n?? ?? elementoesquerda
              
        if (e.elementopai == nulo) {
            raiz = elementoesquerda;  //   ANOTA????O -> Se n?? for raiz, o n?? elementoesquerda ser?? a nova raiz da ??rvore
        } else if (e == e.elementopai.elementoesquerda) {
            e.elementopai.elementoesquerda = elementoesquerda;  //   ANOTA????O -> Corrige rela????o elementopai-filho do novo elementopai (elementoesquerda)
        } else {
            e.elementopai.elementodireita = elementoesquerda;  //   ANOTA????O -> Corrige rela????o elementopai-filho do novo elementopai (elementodireita)
        }
              
        elementoesquerda.elementodireita = e;  //   ANOTA????O -> Corrige rela????o elementopai-filho entre o n?? piv?? e o n?? ?? elementoesquerda
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

        while (e.elementopai != nulo) { //   ANOTA????O ->  Enquanto n??o alcan??amos a raiz faz-se:
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
        Fila<ArvoreRubroNegraOrdenada<T>.Elemento> fila = new Fila<>();

        fila.adicionar(raiz);

        while (!fila.isVazia()) {
            ArvoreRubroNegraOrdenada<T>.Elemento e = fila.remover();

            //visitando o valor do elemento atual
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
        Pilha<ArvoreRubroNegraOrdenada<T>.Elemento> pilha = new Pilha<>();

        pilha.adicionar(raiz);

        while (!pilha.isVazia()) {
            ArvoreRubroNegraOrdenada<T>.Elemento e = pilha.remover();

            //  ANOTA????O -> visitando o valor do elemento atual
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
        ArvoreRubroNegraOrdenada<Integer> a = new ArvoreRubroNegraOrdenada<>();
        for (int i = 0; i < n; i++) {
            a.adicionar(i);
        } 
    }

    public static void vetorAleatorio(int n) {
        ArvoreRubroNegraOrdenada<Integer> a = new ArvoreRubroNegraOrdenada<>();
        for (int i = 0; i < n; i++) {
            a.adicionar((int) (Math.random() * n * 1000));
        }
    }

}