package seguradora;

import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

/**
 * Representa uma seguradora no sistema.
 */
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

	public List<Condutor> lerDados() {
		List<Veiculo> veiculos = new ArquivoVeiculo("dados/veiculos.csv").lerArquivo();
		if (veiculos == null) {
			return null;
		}
		List<Condutor> condutores = new ArquivoCondutor("dados/condutores.csv").lerArquivo();
		if (condutores == null) {
			return null;
		}
		List<Frota> frotas = new ArquivoFrota("dados/frotas.csv", veiculos).lerArquivo();
		if (frotas == null) {
			return null;
		}
		List<ClientePF> clientesPF = new ArquivoClientePF("dados/clientesPF.csv", veiculos).lerArquivo();
		if (clientesPF == null) {
			return null;
		}
		List<ClientePJ> clientesPJ = new ArquivoClientePJ("dados/clientesPJ.csv", frotas).lerArquivo();
		if (clientesPJ == null) {
			return null;
		}
		clientes.clear();
		seguros.clear();
		clientes.addAll(clientesPF);
		clientes.addAll(clientesPJ);
		return condutores;
	}

	public void gravarDados() {
		List<Sinistro> sinistros = listarSinistros();
		var arquivoSinistros = new ArquivoSinistro("dados/sinistros.csv", sinistros);
		arquivoSinistros.gerarArquivo(false);
		List<SeguroPF> segurosPF = new ArrayList<>();
		List<SeguroPJ> segurosPJ = new ArrayList<>();
		for (Seguro seguro : seguros) {
			if (seguro instanceof SeguroPF s) {
				segurosPF.add(s);
			} else {
				segurosPJ.add((SeguroPJ)seguro);
			}
		}
		var arquivoSegurosPF = new ArquivoSeguroPF("dados/seguros.csv", segurosPF);
		var arquivoSegurosPJ = new ArquivoSeguroPJ("dados/seguros.csv", segurosPJ);
		arquivoSegurosPF.gerarArquivo(false);
		arquivoSegurosPJ.gerarArquivo(true);
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

	/**
	 * Cadastra e valida um cliente.
	 */
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

	/**
	 * Remove um cliente e mantém o estado da seguradora consistente.
	 */
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

	/**
	 * Troca todos os seguros de um cliente para outro.
	 */
	public void trocarCliente(Cliente clienteAntigo, Cliente clienteNovo) {
		if (cadastrarCliente(clienteNovo) && clientes.remove(clienteAntigo)) {
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

	/**
	 * Printa na tela todos os sinistros associados aos seguros associados a um
	 * cliente.
	 * Equivalente a getSinistrosPorCliente(cliente).forEach(System.out::println).
	 */
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
