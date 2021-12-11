import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Formatar {
	public static void formatarArquivo(Path arquivoInicial, Path arquivoFormatado) throws IOException {

        List<String> codigo = new ArrayList<>();
        Boolean formatar = false;
        String aux = "";
        int contador = -1;
        String label[] = new String[3];
        int numLabel[] = new int[3];
        int i = 0;

        for (String linha : Files.readAllLines(arquivoInicial, StandardCharsets.UTF_8)) {
            contador++;
       
            if (formatar) {
            	
            	if (linha.trim().startsWith("#") || linha.trim().isEmpty()) {
            		
            	}else {
            		formatar = false;
                	//codigo.add(aux.concat(" ".concat(linha)));
                	//codigo.add(aux.concat(linha)); OU ESSE PARA CONCATENAR SEM O ESPA�O DEPOIS DO ":"
            		codigo.add(linha);
            	}
            	
            } else if (linha.trim().startsWith("#") || linha.trim().isEmpty()) {
                
            } else if (linha.trim().contains(":")){
            	//aux = Integer.toString(contador);
            	aux= linha;
            	formatar = true;
            	label[i] = aux.substring(0, aux.indexOf(":"));
        		numLabel[i] = contador;
        		i++;
            	
   	
            } else {
            	codigo.add(linha);
            }
        }
        
        for (int j=0; j<numLabel.length ; j++) {
        	
        	for (String c : codigo) {
        		
        		if (c.trim().contains(label[j])) {
        			//c.replace(label[j], Integer.toString(numLabel[j]));
        			System.out.println(label[j]);
        		}
        		
        	}
        }
        
        Files.write(arquivoFormatado, codigo);
        
        //for (int j=0 ; j<3 ; j++) {
        	//System.out.println(label[j]+"-->"+numLabel[j]);
        //}
    }

}
