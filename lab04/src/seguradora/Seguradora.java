package seguradora;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class Seguradora {
	private String nome, telefone, email, endereco;
	private List<Sinistro> sinistros;
	private List<Cliente> clientes;
	private static int proxId = 0;

	private static int genId() {
		return proxId++;
	}

	public Seguradora(String nome, String telefone, String email, String endereco) {
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
		sinistros = new ArrayList<>();
		clientes = new ArrayList<>();
	}

	private Stream<Sinistro> sinistrosDe(Cliente cliente) {
		return sinistros.stream().filter(s -> s.getCliente().equals(cliente));
	}

	public int nClientes() {
		return clientes.size();
	}

	public double calcularPrecoSeguro(Cliente cliente) {
		return cliente.calcularScore() * (1 + sinistrosDe(cliente).count());
	}

	public double calcularReceita() {
		return clientes.stream().mapToDouble(this::calcularPrecoSeguro).sum();
	}

	public boolean cadastrarCliente(Cliente cliente) {
		if (!Validacao.validarNome(cliente.getNome()) ||cliente instanceof ClientePF c && (c.getIdade() < 18 || c.getIdade() > 90)) {
			System.out.println("Cliente com dados inválidos.");
			return false;
		}
		
		if (clientes.contains(cliente))
			return false;
		return clientes.add(cliente);
	}

	public boolean removerCliente(String nomeCliente) {
		return clientes.removeIf(cliente -> cliente.nome == nomeCliente);
	}

	public boolean removerCliente(Cliente cliente) {
		sinistros.removeIf(s -> s.getCliente().equals(cliente));
		return clientes.remove(cliente);
	}

	public void trocarCliente(Cliente clienteAntigo, Cliente clienteNovo) {
		clientes.remove(clienteAntigo);
		clientes.add(clienteNovo);
		sinistros.stream().filter(s -> s.getCliente().equals(clienteAntigo))
				.forEach(s -> s.setCliente(clienteNovo));
	}

	public List<Cliente> listarClientes(String tipoCliente) {
		switch (tipoCliente) {
			case "PF":
				return clientes.stream().filter(cliente -> cliente instanceof ClientePF).toList();
			case "PJ":
				return clientes.stream().filter(cliente -> cliente instanceof ClientePJ).toList();
			default:
				return null;
		}
	}

	public List<Sinistro> listarSinistros() {
		return sinistros;
	}

	public boolean gerarSinistro(LocalDate data, String endereco, Veiculo veiculo, Cliente cliente) {
		if (clientes.contains(cliente)) {
			var sinistro = new Sinistro(genId(), data, endereco, this, veiculo, cliente);
			return sinistros.add(sinistro);
		}
		return false;
	}

	public boolean visualizarSinistro(String nomeCliente) {
		var r = false;
		for (var sinistro : sinistros) {
			if (sinistro.getCliente().nome == nomeCliente) {
				r = true;
				System.out.println(sinistro);
			}
		}
		return r;
	}

	public void visualizarSinistro(Cliente cliente) {
		sinistros.stream().filter(sinistro -> sinistro.getCliente().equals(cliente)).forEach(System.out::println);
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public List<Sinistro> getSinistros() {
		return sinistros;
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
