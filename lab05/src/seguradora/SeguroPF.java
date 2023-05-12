package seguradora;

import java.time.LocalDate;
import java.util.List;

public final class SeguroPF extends Seguro {
	private ClientePF cliente;
	private Veiculo veiculo;

	public SeguroPF(int id, LocalDate dataInicio, LocalDate dataFim, Seguradora seguradora, List<Sinistro> sinistros,
			List<Condutor> condutores, int valorMensal, ClientePF cliente, Veiculo veiculo) {
		super(id, dataInicio, dataFim, seguradora, sinistros, condutores, valorMensal);
		this.cliente = cliente;
		this.veiculo = veiculo;
	}

	@Override
	public double calcularValor() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'calcularValor'");
	}

	@Override
	public ClientePF getCliente() {
		return cliente;
	}

	public void setCliente(ClientePF cliente) {
		this.cliente = cliente;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

}
