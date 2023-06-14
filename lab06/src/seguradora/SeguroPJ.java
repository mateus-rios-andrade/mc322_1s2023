package seguradora;

import java.time.LocalDate;
import java.util.List;

/**
 * Representa um seguro de um cliente PJ
 */
public final class SeguroPJ extends Seguro {
	private ClientePJ cliente;
	private Frota frota;

	public SeguroPJ(int id, LocalDate dataInicio, LocalDate dataFim, Seguradora seguradora, List<Sinistro> sinistros,
			List<ICondutor> condutores, ClientePJ cliente, Frota frota) {
		super(id, dataInicio, dataFim, seguradora, sinistros, condutores);
		this.cliente = cliente;
		this.frota = frota;
		setValorMensal(calcularValor());
	}

	@Override
	public double calcularValor() {
		return CalcSeguro.VALOR_BASE.getValor()
				* (10 + (cliente.getQtdFuncionarios() / 10))
				* (1 + 1 / (frota.getVeiculos().size() + 2))
				* (1 + 1 / cliente.getDataFundacao().getYear() + 2)
				* (2 + getSeguradora().getSinistrosPorCliente(cliente).size() / 10)
				* (5 + getQtdSinistrosCondutor() / 10);
	}

	@Override
	public ClientePJ getCliente() {
		return cliente;
	}

	public void setCliente(ClientePJ cliente) {
		this.cliente = cliente;
		setValorMensal(calcularValor());
	}

	public Frota getFrota() {
		return frota;
	}

	public void setFrota(Frota frota) {
		this.frota = frota;
		setValorMensal(calcularValor());
	}

	@Override
	public String mkString(String prefixo, String sep, String sufixo) {
		return super.mkString(prefixo, sep, sep) + "CNPJ Cliente: " + getId() + sep + "Código Frota: " + frota.getCode() + sufixo;
	}

	@Override
	public String toString() {
		return "SeguroPJ [cliente=" + cliente.getID() + ", frota=" + frota.getCode() + "]";
	}
}
