package seguradora;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public interface ICondutor extends MkString {
	default boolean adicionarSinistro(Sinistro sinistro) {
		if (getSinistros().stream().allMatch(s -> s.getId() != sinistro.getId())) {
			return getSinistros().add(sinistro);
		}
		return false;
	}

	String getCpf();

	String getNome();

	void setNome(String nome);

	String getTelefone();

	void setTelefone(String telefone);

	String getEndereco();

	void setEndereco(String endereco);

	String getEmail();

	void setEmail(String email);

	default int getIdade() {
		return Period.between(getDataNascimento(), LocalDate.now()).normalized().getYears();
	}

	LocalDate getDataNascimento();

	void setDataNascimento(LocalDate dataNascimento);

	List<Sinistro> getSinistros();

	@Override
	default String mkString(String prefixo, String sep, String sufixo) {
		return prefixo + "Nome: " + getNome() + sep + "CPF: " + getCpf() + sep + "Nº sinistros: "
				+ getSinistros().size() + sufixo;
	}
}
