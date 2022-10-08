import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Result.csv")));

        int numeral = 10;
        int QuantRepeticao = 2;
        writer.write("Numeral;ColunaDaArvoreAVL_Tipo_Ordenada;ColunaDaArvoreFlamenguista_Tipo_Ordenada\n");

        for (int a = 1; a <= numeral; a++) { 
            int agregaArvoreAVLOrdenada = 0;
            int agregaArvoreFlamenguistaOrdenada = 0;

            for (int b = 0; b < QuantRepeticao; b++) {
                ArvoreAvlOrdenada.vetorOrdenado(a);
                ArvoreRubroNegraOrdenada.vetorOrdenado(a);   
                agregaArvoreAVLOrdenada += ArvoreAvlOrdenada.contador;
                agregaArvoreFlamenguistaOrdenada += ArvoreRubroNegraOrdenada.contador;

            }        
            writer.write(a + ";" + (agregaArvoreAVLOrdenada / QuantRepeticao) + ";" + (agregaArvoreFlamenguistaOrdenada / QuantRepeticao) + ";" + "\n");
            ArvoreAvlOrdenada.contador = 0;
            ArvoreRubroNegraOrdenada.contador = 0;
        }

        
        writer.close();
    }
}
