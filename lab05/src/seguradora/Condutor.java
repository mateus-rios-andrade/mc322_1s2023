package seguradora;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Representa um condutor que não é cliente.
 */
public class Condutor implements ICondutor {
	private final String cpf;
	private String nome, telefone, endereco, email;
	private LocalDate dataNascimento;
	private List<Sinistro> sinistros = new ArrayList<>();

	public Condutor(String cpf, String nome, String telefone, String endereco, String email, LocalDate dataNascimento,
			Collection<Sinistro> sinistros) {
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.sinistros.addAll(sinistros);
	}

	public String getCpf() {
		return cpf;
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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<Sinistro> getSinistros() {
		return sinistros;
	}

	@Override
	public String toString() {
		return mkString("Condutor(", ", ", ")");
	}

	@Override
	public int hashCode() {
		return cpf.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof ICondutor other) {
			return cpf.equals(other.getCpf());
		}
		return false;
	}

}
