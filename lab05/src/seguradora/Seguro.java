package seguradora;

import java.time.LocalDate;
import java.util.List;

public sealed abstract class Seguro permits SeguroPF, SeguroPJ {
	private final int id;
	private LocalDate dataInicio, dataFim;
	private Seguradora seguradora;
	private List<Sinistro> sinistros;
	private List<Condutor> condutores;
	private double valorMensal;

	public Seguro(int id, LocalDate dataInicio, LocalDate dataFim, Seguradora seguradora, List<Sinistro> sinistros,
			List<Condutor> condutores) {
		this.id = id;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.seguradora = seguradora;
		this.sinistros = sinistros;
		this.condutores = condutores;
	}

	public boolean desautorizarCondutor(Condutor condutor) {
		return condutores.remove(condutor);
	}

	public boolean autorizarCondutor(Condutor condutor) {
		return condutores.add(condutor);
	}

	public int getQtdSinistrosCondutor() {
		return getCondutores().stream().mapToInt(c -> c.getSinistros().size()).sum();
	}

	public abstract double calcularValor();

	public abstract Cliente getCliente();

	public int getId() {
		return id;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public Seguradora getSeguradora() {
		return seguradora;
	}

	public void setSeguradora(Seguradora seguradora) {
		this.seguradora = seguradora;
	}

	public List<Sinistro> getSinistros() {
		return sinistros;
	}

	public List<Condutor> getCondutores() {
		return condutores;
	}

	public double getValorMensal() {
		return valorMensal;
	}

	public void setValorMensal(double valorMensal) {
		this.valorMensal = valorMensal;
	}
}
