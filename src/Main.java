import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
	public static void main(String args[]) throws IOException {
        //String path = "cod_asm.txt";
        //LeituraEscrita.escritor(path);
        //LeituraEscrita.leitor(path);
    	int qtde_linhas = 0;

    	Path arquivoInicial = Paths.get("cod_asm.txt");
        Path arquivoFormatado = Paths.get("cod_asm_formatado.txt");
        Path arquivoHexadecimal = Paths.get("cod_hexadecimal.txt");
        
        Formatar.formatarArquivo(arquivoInicial, arquivoFormatado);
        
        //Montagem.paraHexadecimal(arquivoFormatado, arquivoHexadecimal);
        
        
        
        //String hexa = Integer.toString(Integer.parseInt("1111", 2), 16); (Binario --> Hexa)
        //String bin = Integer.toString(Integer.parseInt("ff", 16), 2); (Hexa --> Binario)
     
        
    }

}
