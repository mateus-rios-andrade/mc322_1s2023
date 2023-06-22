package seguradora;

import java.util.List;

import csv.*;

public class ArquivoSeguroPJ implements Arquivo<SeguroPJ> {
	private final String nome;
	private List<SeguroPJ> objetos;
	private CSV csv;

	public ArquivoSeguroPJ(String nome, List<SeguroPJ> objetos) {
		this.nome = nome;
		this.objetos = objetos;
		csv = CSV.deArquivo(nome, true);
	}

	@Override
	public boolean gerarArquivo(boolean append) {
		try {
			csv = CSV.deDados(
					objetos.stream()
							.map(seg -> List.of(
									"PJ",
									Integer.toString(seg.getId()),
									seg.getDataInicio().format(Utils.formatoPadrao),
									seg.getDataFim().format(Utils.formatoPadrao),
									seg.getSeguradora().getNome(),
									Utils.iterToString(seg.getSinistros().stream().map(Sinistro::getId)::iterator),
									Utils.iterToString(seg.getCondutores().stream().map(ICondutor::getCpf)::iterator),
									seg.getCliente().getID(),
									seg.getFrota().getCode(),
									Double.toString(seg.calcularValor())
							)).toList(),
					csv.getHeader());
			csv.gravarEm(nome, append);
			return true;
		} catch (WriteCSVException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	@Override
	public List<SeguroPJ> lerArquivo() {
		return null;
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public List<SeguroPJ> getObjetos() {
		return objetos;
	}

}
