package seguradora;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import csv.*;

public class ArquivoClientePF implements Arquivo<ClientePF> {
	private final String nome;
	private List<ClientePF> objetos = null;
	private final Map<String, Veiculo> veiculos;
	private CSV csv;

	ArquivoClientePF(String nome, List<Veiculo> veiculos) {
		this.nome = nome;
		Function.identity();
		this.veiculos = veiculos.stream().collect(Collectors.toMap(Veiculo::getPlaca, Function.identity()));
		csv = CSV.deArquivo(nome, true);
	}

	@Override
	public boolean gerarArquivo() {
		try {
			csv = CSV.deDados(
					objetos.stream()
							.map(c -> List.of(
									c.getCpf(), c.getNome(), c.getTelefone(), c.getEndereco(), c.getEmail(),
									c.getGenero(),
									c.getDataNascimento().format(Utils.formatoPadrao),
									c.getVeiculos().stream()
											.map(v -> v.getPlaca())
											.reduce((str1, str2) -> str1 + ";" + str2).orElse("")))
							.toList(),
					csv.getHeader());
			csv.gravarEm(nome);
			return true;
		} catch (CSVException e) {
			System.err.println("Erro ao gravar o arquivo " + nome + ".");
			return false;
		}
	}

	@Override
	public List<ClientePF> lerArquivo() {
		try {
			csv = CSV.deArquivo(nome, true);
			objetos = csv.getDados().stream()
					.map(l -> new ClientePF(
							l.get(1),
							l.get(2),
							l.get(3),
							l.get(4),
							Arrays.stream(l.get(8).split(";"))
									.map(veiculos::get)
									.toList(),
							l.get(6),
							l.get(5),
							"null",
							l.get(0),
							LocalDate.parse(l.get(7), Utils.formatoPadrao),
							LocalDate.of(2000, 1, 1),
							Collections.emptyList()))
					.toList();
			return objetos;
		} catch (DateTimeParseException e) {
			System.err.printf("Erro: string %s não representa uma data válida.%n", e.getParsedString());
			return null;
		}
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public List<ClientePF> getObjetos() {
		return objetos;
	}

}
