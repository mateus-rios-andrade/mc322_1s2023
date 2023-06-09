package seguradora;

import java.util.List;
import csv.*;

public class ArquivoVeiculo implements Arquivo<Veiculo> {
	private final String nome;
	private List<Veiculo> objetos;
	private CSV csv;

	ArquivoVeiculo(String nome) {
		this.nome = nome;
		csv = CSV.deArquivo(nome, true);
	}

	@Override
	public boolean gerarArquivo(boolean append) {
		return false;
	}

	@Override
	public List<Veiculo> lerArquivo() {
		try {
			csv = CSV.deArquivo(nome, true);
			objetos = csv.getDados().stream()
					.map(l -> new Veiculo(l.get(0), l.get(1), l.get(2), Integer.parseInt(l.get(3))))
					.toList();
			return objetos;
		} catch (ReadCSVException e) {
			System.err.println(e.getMessage());
		} catch (NumberFormatException e) {
			System.err.println("Erro ao ler ano de fabricação. Msg: " + e.getMessage());
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Arquivo " + nome + " mal-formatado.");

		}
		return null;
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public List<Veiculo> getObjetos() {
		return objetos;
	}

}
