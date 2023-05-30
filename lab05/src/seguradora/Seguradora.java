package seguradora;

import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

public class Seguradora {
	private String nome, telefone, email, endereco;
	private final List<Cliente> clientes;
	private final List<Seguro> seguros;

	private static int proxId = 0;

	private static int genId() {
		return proxId++;
	}

	public Seguradora(String nome, String telefone, String email, String endereco) {
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
		clientes = new ArrayList<>();
		seguros = new ArrayList<>();
	}

	public int nClientes() {
		return clientes.size();
	}

	public double calcularPrecoSeguro(Cliente cliente) {
		return getSegurosPorCliente(cliente).stream().mapToDouble(Seguro::getValorMensal).sum();
	}

	public double calcularReceita() {
		return seguros.stream().mapToDouble(Seguro::getValorMensal).sum();
	}

	public boolean cadastrarCliente(Cliente cliente) {
		if (!Validacao.validarNome(cliente.getNome())
				|| cliente instanceof ClientePF c && (c.getIdade() < 18 || c.getIdade() > 90)) {
			System.out.println("Cliente com dados inválidos.");
			return false;
		}

		if (clientes.contains(cliente))
			return false;
		return clientes.add(cliente);
	}

	public boolean removerCliente(Cliente cliente) {
		seguros.removeIf(s -> s.getCliente().equals(cliente));
		return clientes.remove(cliente);
	}

	private void finalizarTroca(ClientePF clienteAntigo, ClientePF clienteNovo) {
		seguros.stream()
				.filter(s -> s.getCliente().equals(clienteAntigo))
				.map(s -> (SeguroPF) s)
				.forEach(s -> s.setCliente(clienteNovo));
	}

	private void finalizarTroca(ClientePJ clienteAntigo, ClientePJ clienteNovo) {
		seguros.stream()
				.filter(s -> s.getCliente().equals(clienteAntigo))
				.forEach(s -> ((SeguroPJ) s).setCliente(clienteNovo));
	}

	public void trocarCliente(Cliente clienteAntigo, Cliente clienteNovo) {
		if (cadastrarCliente(clienteNovo)) {
			clientes.remove(clienteAntigo);
			if (clienteAntigo instanceof ClientePF) {
				finalizarTroca((ClientePF) clienteAntigo, (ClientePF) clienteNovo);
			} else {
				finalizarTroca((ClientePJ) clienteAntigo, (ClientePJ) clienteNovo);
			}

		}
	}

	public List<Cliente> listarClientes(Cliente.Tipo tipoCliente) {
		Predicate<Cliente> p = switch (tipoCliente) {
			case PF -> (cliente -> cliente instanceof ClientePF);
			case PJ -> (cliente -> cliente instanceof ClientePJ);
		};
		return clientes.stream().filter(p).toList();
	}

	public List<Sinistro> listarSinistros() {
		return seguros.stream()
				.flatMap(s -> s.getSinistros().stream())
				.toList();
	}

	public SeguroPF gerarSeguro(ClientePF cliente, Veiculo veiculo, List<ICondutor> condutores, LocalDate dataInicio,
			LocalDate dataFim) {
		var s = new SeguroPF(
				genId(),
				dataInicio,
				dataFim,
				this,
				Collections.emptyList(),
				condutores,
				cliente,
				veiculo);
		seguros.add(s);
		return s;
	}

	public SeguroPJ gerarSeguro(ClientePJ cliente, Frota frota, List<ICondutor> condutores, LocalDate dataInicio,
			LocalDate dataFim) {
		var s = new SeguroPJ(
				genId(),
				dataInicio,
				dataFim,
				this,
				Collections.emptyList(),
				condutores,
				cliente,
				frota);
		seguros.add(s);
		return s;
	}

	public boolean gerarSinistro(Seguro seguro, ICondutor condutor, LocalDate data, String endereço) {
		Sinistro sinistro = new Sinistro(data, endereço, this, seguro, condutor);
		return seguro.adicionarSinistro(sinistro, condutor);
	}

	public void visualizarSinistro(Cliente cliente) {
		seguros.stream()
				.filter(sinistro -> sinistro.getCliente().equals(cliente))
				.flatMap(s -> s.getSinistros().stream())
				.forEach(System.out::println);
	}

	public List<Seguro> getSegurosPorCliente(Cliente cliente) {
		return seguros.stream().filter(s -> s.getCliente().equals(cliente)).toList();
	}

	public List<Sinistro> getSinistrosPorCliente(Cliente cliente) {
		return seguros.stream()
				.filter(s -> s.getCliente().equals(cliente))
				.flatMap(s -> s.getSinistros().stream().filter(sin -> sin.getSeguradora() == this))
				.toList();
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public List<Seguro> getSeguros() {
		return seguros;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "Seguradora [nome=" + nome + ", telefone=" + telefone + ", email=" + email + ", endereco=" + endereco
				+ "]";
	}
}
