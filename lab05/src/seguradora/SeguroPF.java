package seguradora;

import java.time.LocalDate;
import java.util.List;

public final class SeguroPF extends Seguro {
	private ClientePF cliente;
	private Veiculo veiculo;
	private int qtdVeiculosAnteriores;
	public SeguroPF(int id, LocalDate dataInicio, LocalDate dataFim, Seguradora seguradora, List<Sinistro> sinistros,
			List<Condutor> condutores, ClientePF cliente, Veiculo veiculo, int qtdVeiculosAnteriores) {
		super(id, dataInicio, dataFim, seguradora, sinistros, condutores);
		this.cliente = cliente;
		this.veiculo = veiculo;
		this.qtdVeiculosAnteriores = qtdVeiculosAnteriores;
		setValorMensal(calcularValor());
	}

	@Override
	public double calcularValor() {
		return CalcSeguro.VALOR_BASE.getValor()
				* CalcSeguro.deIdade(cliente.getIdade()).getValor()
				* (1 + 1 / (qtdVeiculosAnteriores + 2))
				* (2 + getSeguradora().getSinistrosPorCliente(cliente).size() / 10)
				* (5 + getQtdSinistrosCondutor() / 10);
	}

	@Override
	public ClientePF getCliente() {
		return cliente;
	}

	public void setCliente(ClientePF cliente) {
		this.cliente = cliente;
		setValorMensal(calcularValor());
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
		setValorMensal(calcularValor());
	}

	public int getQtdVeiculosAnteriores() {
		return qtdVeiculosAnteriores;
	}

	public void setQtdVeiculosAnteriores(int qtdVeiculosAnteriores) {
		this.qtdVeiculosAnteriores = qtdVeiculosAnteriores;
		setValorMensal(calcularValor());
	}

	@Override
	public String toString() {
		return "SeguroPF [cliente=" + cliente.getID() + ", veiculo=" + veiculo.getPlaca() + ", qtdVeiculosAnteriores="
				+ qtdVeiculosAnteriores + "]";
	}

	
}
