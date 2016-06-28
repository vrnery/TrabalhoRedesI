/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apache64;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cstads
 */
public class Converter {
    public static ArrayList<String> resultados = new ArrayList<>();
    public static ArrayList<String> sqldados = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        File resultado = new File("/home/cstads/Downloads/context.txt");
        FileReader frResultado = new FileReader(resultado);
        BufferedReader brResultado = new BufferedReader(frResultado);

        File extracao = new File("/home/cstads/Downloads/contextP.txt");
        PrintWriter pwExtracao = new PrintWriter(extracao);

        try {
            int i = 0;
            while (brResultado.ready()) {
                resultados.add(brResultado.readLine());
            }
            boolean ifs = true;
            String linha1;
            for (String linha : resultados) {
                //texto += linha;
                int intlinha = linha.length();
                for (int j = 0; j < intlinha; j++) {
                    if ((linha.charAt(j) == ">".charAt(0)) && (j < intlinha-1)) {
                        j++;
                        linha1 = "";
                        do {
                            linha1 += "" + linha.charAt(j);
                            j++;
                        } while (linha.charAt(j) != "<".charAt(0));
                        j = intlinha;
                        if (ifs) {
                            ifs = false;
                            sqldados.add("if (fileName.endsWith(\"." + linha1 + "\")) {");
                        } else {
                            ifs = true;
                            sqldados.add("return \""+ linha1 + "\";");
                            sqldados.add("}");
                        }
                    }
                }
            }
        } catch (IOException e) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            brResultado.close();
            frResultado.close();
        }
        sqldados.stream().forEach((x) -> {
            pwExtracao.println(x);
            System.out.println(x);
        });
    }
}
