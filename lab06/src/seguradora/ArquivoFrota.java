package seguradora;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import csv.*;

public class ArquivoFrota implements Arquivo<Frota> {
	private final String nome;
	private List<Frota> objetos;
	private Map<String, Veiculo> veiculos;
	private CSV csv;

	ArquivoFrota(String nome, List<Veiculo> veiculos) {
		this.nome = nome;
		this.veiculos = veiculos.stream().collect(Collectors.toMap(Veiculo::getPlaca, Function.identity()));
		csv = CSV.deArquivo(nome, true);
	}

	private List<List<String>> serializar() {
		List<List<String>> lista = new ArrayList<>(objetos.size());
		for (Frota frota : objetos) {
			List<String> linha = new ArrayList<>();
			linha.add(frota.getCode());
			linha.addAll(frota.getVeiculos().stream().map(Veiculo::getPlaca).toList());
			lista.add(linha);
		}
		return lista;
	}

	private List<Frota> deserializar(List<List<String>> dados) {
		List<Frota> lista = new ArrayList<>(dados.size());
		for (List<String> linha : dados) {
			String code = linha.get(0);
			List<Veiculo> vs = linha.subList(1, linha.size()).stream()
					.map(veiculos::get)
					.toList();
			lista.add(new Frota(code, vs));
		}
		return lista;
	}

	@Override
	public boolean gerarArquivo() {
		try {
			csv = CSV.deDados(serializar(), csv.getHeader());
			csv.gravarEm(nome);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public List<Frota> lerArquivo() {
		try {
			csv = CSV.deArquivo(nome, true);
			objetos = deserializar(csv.getDados());
			return objetos;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public List<Frota> getObjetos() {
		return objetos;
	}

}
