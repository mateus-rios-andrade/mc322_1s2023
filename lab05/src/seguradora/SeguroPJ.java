package seguradora;

import java.time.LocalDate;
import java.util.List;

public final class SeguroPJ extends Seguro {
	private ClientePJ cliente;
	private Frota frota;

	public SeguroPJ(int id, LocalDate dataInicio, LocalDate dataFim, Seguradora seguradora, List<Sinistro> sinistros,
			List<Condutor> condutores, ClientePJ cliente, Frota frota) {
		super(id, dataInicio, dataFim, seguradora, sinistros, condutores);
		this.cliente = cliente;
		this.frota = frota;
	}

	@Override
	public double calcularValor() {
		return CalcSeguro.VALOR_BASE.getValor()
			* (10 + (cliente.getQtdFuncionarios()/10))
			* (1 + 1/(frota.getVeiculos().size() + 2))
			* (1 + 1/cliente.getDataFundacao().getYear() + 2)
			* (2 + getSeguradora().getSinistrosPorCliente(cliente).size() / 10)
			* (5 + getQtdSinistrosCondutor() / 10);
	}

	@Override
	public ClientePJ getCliente() {
		return cliente;
	}

	public void setCliente(ClientePJ cliente) {
		this.cliente = cliente;
	}

	public Frota getFrota() {
		return frota;
	}

	public void setFrota(Frota frota) {
		this.frota = frota;
	}
}
