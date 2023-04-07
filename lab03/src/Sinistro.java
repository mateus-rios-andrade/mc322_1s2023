public class Sinistro {
	private final int id;
	private String data, endereco;
	private Seguradora seguradora;
	private Veiculo veiculo;
	private Cliente cliente;

	public Sinistro(int id, String data, String endereco, Seguradora seguradora, Veiculo veiculo, Cliente cliente) {
		this.id = id;
		this.data = data;
		this.endereco = endereco;
		this.seguradora = seguradora;
		this.veiculo = veiculo;
		this.cliente = cliente;
	}

	public int getId() {
		return id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Seguradora getSeguradora() {
		return seguradora;
	}

	public void setSeguradora(Seguradora seguradora) {
		this.seguradora = seguradora;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Sinistro [id=" + id + ", data=" + data + ", endereco=" + endereco + ", seguradora=" + seguradora
				+ ", veiculo=" + veiculo + ", cliente=" + cliente + "]";
	}
}
