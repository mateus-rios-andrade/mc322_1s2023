package seguradora;

import java.util.List;
import java.util.Map;
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
	public boolean gerarArquivo() {
		try {
			csv = CSV.deDados(
					objetos.stream()
							.map(c -> List.of(
									c.getCnpj(),
									c.getNome(),
									c.getTelefone(),
									c.getEndereco(),
									c.getEmail(),
									c.getDataFundacao().format(Utils.formatoPadrao)))
							.toList(),
					csv.getHeader());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public List<ClientePJ> lerArquivo() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'lerArquivo'");
	}

	@Override
	public String getNome() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getNome'");
	}

	@Override
	public List<ClientePJ> getObjetos() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getObjetos'");
	}

}
