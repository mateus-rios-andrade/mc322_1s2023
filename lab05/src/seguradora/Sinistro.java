package seguradora;

import java.time.LocalDate;

public class Sinistro implements MkString {
	private final int id;
	private LocalDate data;
	private String endereco;
	private Seguradora seguradora;
	private Seguro seguro;
	private ICondutor condutor;

	private static int proxId;

	private static int genId() {
		return proxId++;
	}

	public Sinistro(LocalDate data, String endereco, Seguradora seguradora, Seguro seguro, ICondutor condutor) {
		this.id = genId();
		this.data = data;
		this.endereco = endereco;
		this.seguradora = seguradora;
		this.seguro = seguro;
		this.condutor = condutor;
	}

	public int getId() {
		return id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
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

	public Seguro getSeguro() {
		return seguro;
	}

	public void setSeguro(Seguro seguro) {
		this.seguro = seguro;
	}

	public ICondutor getCondutor() {
		return condutor;
	}

	public void setCondutor(ICondutor condutor) {
		this.condutor = condutor;
	}

	public String mkString(String prefixo, String sep, String sufixo) {
		return prefixo + "ID: " + id + sep + "Data: " + data + sep + "Endereço: " + endereco + sep + "Seguradora: "
				+ seguradora
				+ sep + "Seguro: " + seguro + sep + "ICondutor: " + condutor + sufixo;
	}

	@Override
	public String toString() {
		return mkString("Sinistro(", ", ", ")");
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sinistro other = (Sinistro) obj;
		return id == other.id;
	}
}
