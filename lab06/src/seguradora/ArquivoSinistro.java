package seguradora;

import java.util.List;
import csv.*;

public class ArquivoSinistro implements Arquivo<Sinistro> {
	private final String nome;
	private List<Sinistro> objetos;
	private CSV csv;

	public ArquivoSinistro(String nome, List<Sinistro> objetos) {
		this.nome = nome;
		this.objetos = objetos;
		csv = CSV.deArquivo(nome, true);
	}

	@Override
	public boolean gerarArquivo(boolean append) {
		try {
			csv = CSV.deDados(
					objetos.stream()
							.map(s -> List.of(
									Integer.toString(s.getId()),
									s.getData().format(Utils.formatoPadrao),
									s.getEndereco(),
									s.getCondutor().getCpf(),
									Integer.toString(s.getSeguro().getId())
							))
							.toList(),
					csv.getHeader());
			csv.gravarEm(nome, append);
			return true;
		} catch (WriteCSVException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	@Override
	public List<Sinistro> lerArquivo() {
		return null;
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public List<Sinistro> getObjetos() {
		return objetos;
	}

}
