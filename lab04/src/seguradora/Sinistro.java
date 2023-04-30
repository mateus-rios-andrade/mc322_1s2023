package seguradora;
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

	public String mkString(String prefixo, String sep, String sufixo) {
		return prefixo + "ID: " + id + sep + "Data: " + data + sep + "Endereço: " + endereco + sep + "Seguradora: " + seguradora
		+ sep + "Veículo: " + veiculo + sep + "Cliente: " + cliente + sufixo;
	}

	@Override
	public String toString() {
		return mkString("Sinistro(\n\t", ",\n\t", "\n)");
	}
}
