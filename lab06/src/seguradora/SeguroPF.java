package seguradora;

import java.time.LocalDate;
import java.util.List;

/**
 * Representa um seguro de um Cliente PF.
 */
public final class SeguroPF extends Seguro {
	private ClientePF cliente;
	private Veiculo veiculo;

	public SeguroPF(int id, LocalDate dataInicio, LocalDate dataFim, Seguradora seguradora, List<Sinistro> sinistros,
			List<ICondutor> condutores, ClientePF cliente, Veiculo veiculo) {
		super(id, dataInicio, dataFim, seguradora, sinistros, condutores);
		this.cliente = cliente;
		this.veiculo = veiculo;
		setValorMensal(calcularValor());
	}

	
	@Override
	public double calcularValor() {
		long qtdVeiculos = getSeguradora().getSeguros().stream()
				.filter(seg -> seg instanceof SeguroPF s && !s.veiculo.equals(veiculo))
				.count();
		return CalcSeguro.VALOR_BASE.getValor()
				* CalcSeguro.deIdade(cliente.getIdade()).getValor()
				* (1 + 1 / (qtdVeiculos + 2))
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

	@Override
	public String mkString(String prefixo, String sep, String sufixo) {
		return super.mkString(prefixo, sep, sep) + "CPF cliente: " + cliente.getCpf() + sep + "Placa Veículo: " + veiculo.getPlaca() + sufixo;
	}

	@Override
	public String toString() {
		return "SeguroPF [cliente=" + cliente.getID() + ", veiculo=" + veiculo.getPlaca() + "]";
	}


	
}
