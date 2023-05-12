package seguradora;

import java.time.LocalDate;
import java.util.List;

public final class SeguroPJ extends Seguro {
	private ClientePJ cliente;
	private Frota frota;

	public SeguroPJ(int id, LocalDate dataInicio, LocalDate dataFim, Seguradora seguradora, List<Sinistro> sinistros,
			List<Condutor> condutores, int valorMensal, ClientePJ cliente, Frota frota) {
		super(id, dataInicio, dataFim, seguradora, sinistros, condutores, valorMensal);
		this.cliente = cliente;
		this.frota = frota;
	}

	@Override
	public double calcularValor() {
		throw new UnsupportedOperationException("Unimplemented method 'calcularValor'");
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
