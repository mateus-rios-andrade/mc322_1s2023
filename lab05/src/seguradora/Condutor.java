package seguradora;

import java.time.LocalDate;
import java.util.List;

public class Condutor {
	private final String cpf;
	private String nome, telefone, endereco, email;
	private LocalDate dataNascimento;
	private List<Sinistro> sinistros;

	public Condutor(String cpf, String nome, String telefone, String endereco, String email, LocalDate dataNascimento,
			List<Sinistro> sinistros) {
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.sinistros = sinistros;
	}

	public boolean adicionarSinistro(Sinistro sinistro) {
		return sinistros.add(sinistro);
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
		return "Condutor(Nome:" + nome + ", CPF:" + cpf + ", Nº sinistros: " + sinistros.size() + ")";
	}
}
