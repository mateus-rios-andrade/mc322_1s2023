package seguradora;

import java.util.List;

import csv.*;

public class ArquivoSeguroPF implements Arquivo<SeguroPF> {
	private final String nome;
	private List<SeguroPF> objetos;
	private CSV csv;

	public ArquivoSeguroPF(String nome, List<SeguroPF> objetos) {
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
								"PF",
								Integer.toString(seg.getId()),
								seg.getDataInicio().format(Utils.formatoPadrao),
								seg.getDataFim().format(Utils.formatoPadrao),
								seg.getSeguradora().getNome(),
								Utils.iterToString(seg.getSinistros().stream().map(Sinistro::getId)::iterator),
								Utils.iterToString(seg.getCondutores().stream().map(ICondutor::getCpf)::iterator),
								seg.getCliente().getID(),
								seg.getVeiculo().getPlaca(),
								Double.toString(seg.calcularValor())
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
	public List<SeguroPF> lerArquivo() {
		return null;
	}

	@Override
	public String getNome() {
		return nome;
	}

	public List<SeguroPF> getObjetos() {
		return objetos;
	}

}
