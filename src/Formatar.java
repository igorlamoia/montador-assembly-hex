import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Formatar {
	public static void formatarArquivo(Path arquivoInicial, Path arquivoFormatado) throws IOException {
		List<String> codigo = new ArrayList<>(); // conjunto de linhas
		Map<String, Integer> labels = new HashMap<>(); // label => Posição

		int contador = -1;
		for (String linha : Files.readAllLines(arquivoInicial, StandardCharsets.UTF_8)) {
			contador++;
			linha = linha.split("#")[0].trim(); // tudo que vem antes do comentário

			if(linha.contains(":")) {
				labels.put(linha.substring(0, linha.indexOf(":")).trim(), contador+1);
				linha = "";
				contador++;
			}

			if(! linha.isBlank())
				codigo.add(linha);
			else
				contador--;
		}

		// Agora com Branchs bne ou beq
		contador = -1;
		for (String linha : codigo) {
			contador++;
			if(linha.contains("beq") || linha.contains("bne")) {
				labels.put("pula: "+linha.split(",")[2] + " ", labels.get(linha.split(",")[2].trim())-contador);
				linha = "";
				contador++;
			}
		}

		for (String key : labels.keySet())
			System.out.println("Chave: " + key + "=> Valor: " + labels.get(key));

		Files.write(arquivoFormatado, codigo);
		}
}
