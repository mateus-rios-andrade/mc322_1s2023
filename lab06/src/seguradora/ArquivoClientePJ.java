package seguradora;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
							List.of(frotas.get(l.get(6))),
							l.get(0),
							LocalDate.parse(l.get(5), Utils.formatoPadrao),
							new Random().nextInt() % 100))
					.filter(c -> Validacao.validarCNPJ(c.getCnpj()))
					.toList();
			return objetos;
		} catch (ReadCSVException e) {
			System.err.println(e.getMessage());
			return null;
		}
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
