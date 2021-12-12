import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Formatar {
	private Path arquivoInicial = null;
	private Path arquivoFormatado = null;
	private int contador = 0;
	private List<String> codigo = new ArrayList<>();
	private Map<String, Integer> labels = new HashMap<>(); // label => Posição
	private Map<String, Integer> branchs = new HashMap<>(); // numeroLinha => string linha
	private Map<String, Integer> jumps = new HashMap<>(); // numeroLinha => string linha

	public Formatar (Path arquivoInicial, Path arquivoFormatado) throws IOException {
		this.arquivoInicial = arquivoInicial;
		this.arquivoFormatado = arquivoFormatado;
		this.formatarArquivo();
	}

	private void formatarArquivo () throws IOException {
		for (String linha : Files.readAllLines(this.arquivoInicial, StandardCharsets.UTF_8)) {
			linha = linha.split("#")[0].trim(); // tudo que vem antes do comentário, ignorando o resto
			
			if(linha.contains(":"))
				linha = this.pegaLabelRemoveLinha(linha);
			
			this.adicionaLinhaValida(linha);
			this.verificaBranch(linha);
		}

		Iterator<String> keyIterator = labels.keySet().iterator();
		while(keyIterator.hasNext()){
			String nextKey = keyIterator.next();
			System.out.println("Chave: "+ nextKey+" Valor: "+ labels.get(nextKey));
		}

		Files.write(this.arquivoFormatado, this.codigo);
	}

	// Se contem labels put(chave: nome da lavel, valor: numero da linha atual)
	private String pegaLabelRemoveLinha(String linha) {
		this.labels.put(linha.substring(0, linha.indexOf(":")).trim(), this.contador); 
		linha = ""; // Limpando a linha pois a label deve sair, é só uma marcação
		return linha;
	}

	// O this.contador já pulou a linha, agr sim, verifico se era branch (sempre uma instrução à frente)
	// Se contem branchs put(chave: nome da label de destino, valor: numero linha atual)
	private void verificaBranch (String linha) {
		if(linha.contains("beq") || linha.contains("bne"))
			this.labels.put("Branch: "+linha.split(",")[2].trim(), this.contador);
	}

	// Somente se a linha for preenchida com código iremos adicionar 
	private void adicionaLinhaValida (String linha) {
		if(! linha.isBlank()) {
			this.codigo.add(linha);
			this.contador++;
		}
	}
}
