import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
	public static void main(String[] args) throws IOException {

        new Formatar(Paths.get("cod_asm.txt"), Paths.get("cod_asm_formatado.txt")); // (arquivo_inicial, arquivo_formatado)

        // Path arquivoHexadecimal = Paths.get("cod_hexadecimal.txt");
        // Montagem.paraHexadecimal(arquivoFormatado, arquivoHexadecimal);
        
        //String hexa = Integer.toString(Integer.parseInt("1111", 2), 16); (Binario --> Hexa)
        //String bin = Integer.toString(Integer.parseInt("ff", 16), 2); (Hexa --> Binario)
     
        
    }

}
