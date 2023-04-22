import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	public int nClientes() {
		return clientes.size();
	}

	public boolean cadastrarCliente(Cliente cliente) {
		if (clientes.contains(cliente))
			return false;
		clientes.add(cliente);
		return true;
	}

	public boolean removerCliente(String nomeCliente) {
		return clientes.removeIf(cliente -> cliente.nome == nomeCliente);
	}
	
	public boolean removerCliente(Cliente cliente) {
		return clientes.remove(cliente);
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

	public boolean gerarSinistro(String endereco, Veiculo veiculo, Cliente cliente) {
		if (clientes.contains(cliente)) {
			var sinistro = new Sinistro(genId(), Date.from(Instant.now()).toString(), endereco, this, veiculo, cliente);
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
		sinistros.stream().filter(sinistro -> sinistro.getCliente() == cliente).forEach(System.out::println);
	}

	public List<Cliente> getClientes() {
		return clientes;
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
