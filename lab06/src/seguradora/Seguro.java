package seguradora;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Representa um seguro qualquer no sistema.
 */
public sealed abstract class Seguro implements MkString permits SeguroPF, SeguroPJ {
	private final int id;
	private LocalDate dataInicio, dataFim;
	private Seguradora seguradora;
	private List<Sinistro> sinistros = new ArrayList<>();
	private List<ICondutor> condutores = new ArrayList<>();
	private double valorMensal;

	public Seguro(int id, LocalDate dataInicio, LocalDate dataFim, Seguradora seguradora,
			Collection<Sinistro> sinistros,
			Collection<ICondutor> condutores) {
		this.id = id;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.seguradora = seguradora;
		this.sinistros.addAll(sinistros);
		condutores.forEach(this::addSinistros);
		this.condutores.addAll(condutores);
	}

	private void addSinistros(ICondutor condutor) {
		for (Sinistro sinistro : condutor.getSinistros()) {
			if (!sinistros.contains(sinistro)) {
				sinistros.add(sinistro);
			}
		}
	}

	public boolean adicionarSinistro(Sinistro sinistro, ICondutor condutor) {
		if (!sinistros.contains(sinistro)) {
			sinistros.add(sinistro);
			return condutor.adicionarSinistro(sinistro);
		}
		return false;
	}

	public boolean autorizarCondutor(ICondutor condutor) {
		if (!condutores.contains(condutor)) {
			condutores.add(condutor);
			setValorMensal(calcularValor());
			return true;
		}
		return false;
	}

	public boolean desautorizarCondutor(ICondutor condutor) {
		boolean r = condutores.remove(condutor);
		setValorMensal(calcularValor());
		return r;
	}

	public int getQtdSinistrosCondutor() {
		return condutores.stream().mapToInt(c -> c.getSinistros().size()).sum();
	}

	/**
	 * Calcula o valor do seguro considerando os fatores dados.
	 */
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

	public List<ICondutor> getCondutores() {
		return condutores;
	}

	public double getValorMensal() {
		return valorMensal;
	}

	protected void setValorMensal(double valorMensal) {
		this.valorMensal = valorMensal;
	}

	@Override
	public String mkString(String prefixo, String sep, String sufixo) {
		var dtf = DateTimeFormatter.ofPattern("d/M/yyyy");
		return prefixo + "Id: " + id + sep + "Seguradora: " + seguradora.getNome() + sep + "ID Cliente: "
				+ getCliente().getID() + sep + "Nome Cliente: " + getCliente().getNome() + sep + "Data início: "
				+ dataInicio.format(dtf)
				+ sep + "Data fim: " + dataFim.format(dtf) + sep + "Valor Mensal: " + String.format("%.2f", valorMensal)
				+ sufixo;
	}

	@Override
	public String toString() {
		return "Seguro [id=" + id + ", dataInicio=" + dataInicio + ", dataFim=" + dataFim + ", seguradora="
				+ seguradora.getNome()
				+ ", nº sinistros=" + sinistros + ", condutores=" + condutores + ", valorMensal=" + valorMensal + "]";
	}
}
