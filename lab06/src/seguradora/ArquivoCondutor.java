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
	public boolean gerarArquivo() {
		try {
			csv = CSV.deDados(
					objetos.stream()
							.map(c -> List.of(
									c.getCpf(),
									c.getNome(),
									c.getTelefone(),
									c.getEndereco(),
									c.getEmail(),
									c.getDataNascimento().format(Utils.formatoPadrao)))
							.toList(),
					csv.getHeader());
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
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
					.toList();
			return objetos;
		} catch (DateTimeParseException e) {
			// TODO: handle exception
		} catch (ReadCSVException e) {
			
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
