package seguradora;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

import csv.*;

public class ArquivoCondutor implements Arquivo<Condutor> {
	private final String nome;
	private List<Condutor> objetos = Collections.emptyList();
	private CSV csv;

	ArquivoCondutor(String nome) {
		this.nome = nome;
		csv = CSV.deArquivo(nome, true);
	}

	@Override
	public boolean gerarArquivo(boolean append) {
		return false;
	}

	@Override
	public List<Condutor> lerArquivo() {
		try {
			csv = CSV.deArquivo(nome, true);
			objetos = csv.getDados().stream()
					.map(l -> new Condutor(
							l.get(0),
							l.get(1),
							l.get(2),
							l.get(3),
							l.get(4),
							LocalDate.parse(l.get(5), Utils.formatoPadrao),
							Collections.emptyList()))
					.filter(c -> Validacao.validarCPF(c.getCpf()))
					.filter(c -> Validacao.validarNome(c.getNome()))
					.toList();
			return objetos;
		} catch (DateTimeParseException e) {
			System.err.println("Data '" + e.getParsedString() + "' formatada incorretamente. ");
		} catch (ReadCSVException e) {
			System.err.println(e.getMessage());
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Uma linha possui menos entradas do que o necessário.");
		}
		return null;
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public List<Condutor> getObjetos() {
		return objetos;
	}

}
