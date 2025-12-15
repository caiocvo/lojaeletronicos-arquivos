package main.util;

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
}
