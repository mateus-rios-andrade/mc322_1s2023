package seguradora;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import csv.*;

public class ArquivoClientePJ implements Arquivo<ClientePJ> {
	private final String nome;
	private CSV csv;
	private List<ClientePJ> objetos;
	private final Map<String, Frota> frotas;

	public ArquivoClientePJ(String nome, List<Frota> frotas) {
		this.nome = nome;
		this.frotas = frotas.stream().collect(Collectors.toMap(Frota::getCode, Function.identity()));
		lerArquivo();
	}

	@Override
	public boolean gerarArquivo(boolean append) {
		return false;
	}

	@Override
	public List<ClientePJ> lerArquivo() {
		try {
			csv = CSV.deArquivo(nome, true);
			objetos = csv.getDados().stream()
					.map(l -> new ClientePJ(
							l.get(1),
							l.get(2),
							l.get(3),
							l.get(4),
							Optional.ofNullable(frotas.get(l.get(6))).stream().toList(),
							l.get(0),
							LocalDate.parse(l.get(5), Utils.formatoPadrao),
							new Random().nextInt() % 100))
					.filter(c -> Validacao.validarCNPJ(c.getCnpj()))
					.toList();
			return objetos;
		} catch (ReadCSVException e) {
			System.err.println(e.getMessage());
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Uma linha possui menos entradas do que o necessário.");
		} catch (DateTimeParseException e) {
			System.err.printf("Erro: string %s não representa uma data válida.%n", e.getParsedString());
		} catch (NullPointerException e) {
			System.err.println("Arquivo possui entrada inválida na coluna");
		}
		return null;
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public List<ClientePJ> getObjetos() {
		return objetos;
	}

}
