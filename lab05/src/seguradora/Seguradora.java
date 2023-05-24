package seguradora;

import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDate;
import java.util.List;

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
		return getSegurosPorCliente(cliente).stream().mapToDouble(s -> s.getValorMensal()).sum();
	}

	public double calcularReceita() {
		return seguros.stream().mapToDouble(s -> s.getValorMensal()).sum();
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

	public void trocarCliente(Cliente clienteAntigo, Cliente clienteNovo) {
		// clientes.remove(clienteAntigo);
		// clientes.add(clienteNovo);
		// sinistros.stream()
		// .filter(s -> s.getCliente().equals(clienteAntigo))
		// .forEach(s -> s.setCliente(clienteNovo));
	}

	public List<Cliente> listarClientes(Cliente.Tipo tipoCliente) {
		return switch (tipoCliente) {
			case PF -> clientes.stream().filter(cliente -> cliente instanceof ClientePF).toList();
			case PJ -> clientes.stream().filter(cliente -> cliente instanceof ClientePJ).toList();
		};
	}

	public List<Sinistro> listarSinistros() {
		return seguros.stream()
				.flatMap(s -> s.getSinistros().stream())
				.toList();
	}

	public boolean gerarSeguro(ClientePF cliente, Veiculo veiculo, List<Condutor> condutores, LocalDate dataInicio,
			LocalDate dataFim) {
		return seguros.add(new SeguroPF(
				genId(),
				dataInicio,
				dataFim,
				this,
				Collections.emptyList(),
				condutores,
				cliente,
				veiculo));
	}

	public boolean gerarSeguro(ClientePJ cliente, Frota frota, List<Condutor> condutores, LocalDate dataInicio,
			LocalDate dataFim) {
		return seguros.add(new SeguroPJ(
				genId(),
				dataInicio,
				dataFim,
				this,
				Collections.emptyList(),
				condutores,
				cliente,
				frota));
	}

	public void visualizarSinistro(Cliente cliente) {
		// sinistros.stream()
		// .filter(sinistro -> sinistro.getCliente().equals(cliente))
		// .forEach(System.out::println);
	}

	public List<Seguro> getSegurosPorCliente(Cliente cliente) {
		return seguros.stream().filter(s -> s.getCliente().equals(cliente)).toList();
	}

	public List<Sinistro> getSinistrosPorCliente(Cliente cliente) {
		// var sinistros = new ArrayList<Sinistro>();
		// for (var seguro : seguros) {
		// if (seguro.getCliente().equals(cliente)) {
		// sinistros.addAll(seguro.getSinistros());
		// }
		// }
		// return sinistros;
		return seguros.stream()
				.filter(s -> s.getCliente().equals(cliente))
				.flatMap(s -> s.getSinistros().stream())
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
