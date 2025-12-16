package main.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ArquivoUtil {
    public static void gravarId(int i, String arq){
        try{
            PrintWriter pw = new PrintWriter(arq);
            pw.println(i);
            pw.flush();
            pw.close();
        } catch (Exception e){
            System.out.print(e.getMessage());
        }
    }
    public static int lerId(String arq)  {
        BufferedReader br;
        try{
            br = new BufferedReader(new FileReader(arq));
            int id = Integer.parseInt(br.readLine());
            br.close();
            return id;
        } catch (IOException | NumberFormatException e){
            System.err.println("Erro ao ler o id");
            e.printStackTrace();
        }
        return -1;
    }
}
