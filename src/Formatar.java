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
	private List<String> codigoFormatado = new ArrayList<>();
	private Map<String, Integer> labels = new HashMap<>(); // label => Posição
	private Map<Integer, String> branchs = new HashMap<>(); // numeroLinha => string linha
	private Map<Integer, String> jumps = new HashMap<>(); // numeroLinha => string linha

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
			
			this.verificarJump(linha);
			this.verificarBranch(linha);
			this.adicionarLinhaValida(linha);
		}

		Iterator<String> keyIterator = labels.keySet().iterator();
		while(keyIterator.hasNext()){
			String nextKey = keyIterator.next();
			System.out.println("Chave: "+ nextKey+" Valor: "+ labels.get(nextKey));
		}
		Iterator<Integer> chave = branchs.keySet().iterator();
		while(chave.hasNext()){
			Integer nextKey = chave.next();
			System.out.println("Chaves: "+ nextKey+" Valor: "+ branchs.get(nextKey));
		}
		Iterator<Integer> chaves = jumps.keySet().iterator();
		while(chaves.hasNext()){
			Integer nextKey = chaves.next();
			System.out.println("Chavex: "+ nextKey+" Valor: "+ jumps.get(nextKey));
		}
		this.trocarBranchsJumpsPorNumero();

		Files.write(this.arquivoFormatado, this.codigoFormatado);
	}

	// Se contem labels put(chave: nome da lavel, valor: numero da linha atual)
	private String pegaLabelRemoveLinha(String linha) {
		this.labels.put(linha.substring(0, linha.indexOf(":")).trim(), this.contador); 
		linha = ""; // Limpando a linha pois a label deve sair, é só uma marcação
		return linha;
	}

	// Somente se a linha for preenchida com código iremos adicionar 
	private void adicionarLinhaValida (String linha) {
		if(! linha.isBlank()) {
			this.codigo.add(linha);
			this.contador++;
		}
	}

	// O this.contador já pulou a linha, agr sim, verifico se era branch (sempre uma instrução à frente)
	// Se contem branchs put(chave: nome da label de destino, valor: numero linha atual)
	private void verificarBranch (String linha) {
		// this.branchs.put(this.contador, linha.trim());
		if(linha.contains("beq") || linha.contains("bne"))
			this.branchs.put(this.contador, linha.split(",")[2].trim());
	}

	// ! Adicionar trim para pegar laber msm se o jump tiver cheio de espaço
	private void verificarJump (String linha) {
		// this.jumps.put(this.contador, linha.trim());
		if(linha.contains("j "))
			this.jumps.put(this.contador, linha.split(" ")[1].trim());
	}

	private void trocarBranchsJumpsPorNumero () {
		String label = null;
		String metadeEsquerdaDaLabelBranch = null;
		this.contador = 0;
		int deslocamento = 0;
		for(String linha : this.codigo) {
			if(this.branchs.containsKey(this.contador)){
				label = this.branchs.get(this.contador).trim(); 
				metadeEsquerdaDaLabelBranch = linha.split(label)[0].trim();
				deslocamento = (this.labels.get(label.trim())) - this.contador; 
				linha = metadeEsquerdaDaLabelBranch + deslocamento;
			}
			else if (this.jumps.containsKey(this.contador)){
				linha = linha.split(" ")[0].trim();
				linha = linha + " " + this.labels.get(this.jumps.get(this.contador));
			}
			this.codigoFormatado.add(linha);
			this.contador++;
		}
	}



}
