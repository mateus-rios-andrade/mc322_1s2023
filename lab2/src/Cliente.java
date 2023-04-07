public class Cliente {
	private String nome, cpf, dataNascimento, endereço;
	private int idade;

	public static boolean validarCPF(String cpf) {
		var digitos = cpfToArray(cpf);
		if (digitos == null)
			return false;
		int soma = 0;
		for (int m = 10, i = 0; m >= 2; m--, i++) {
			soma += m * digitos[i];
		}
		int primeiroDigito = 11 - (soma % 11);
		if (primeiroDigito == 10)
			primeiroDigito = 0;
		if (primeiroDigito != digitos[9])
			return false;
	
		soma = 0;
		for (int m = 10, i = 1; m >= 2; m--, i++) {
			soma += m * digitos[i];
		}
		int segundoDigito = 11 - (soma % 11);
		if (segundoDigito == 10)
			segundoDigito = 0;
		if (segundoDigito != digitos[10])
			return false;
		
		return true;
	}

	private static int[] cpfToArray(String cpf) {
		cpf = cpf.replaceAll("[^0-9]", "");
		if (cpf.length() != 11) {
			return null;
		}
		var array = new int[11];
		for (int i = 0; i < 11; i++) {
			array[i] = cpf.charAt(i) - '0';
		}
		return array;
	}

	public Cliente(String nome, String cpf, String dataNascimento, String endereço, int idade) {
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.endereço = endereço;
		this.idade = idade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEndereço() {
		return endereço;
	}

	public void setEndereço(String endereço) {
		this.endereço = endereço;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	@Override
	public String toString() {
		return "Cliente [nome=" + nome + ", cpf=" + cpf + ", dataNascimento=" + dataNascimento + ", endereço="
				+ endereço + ", idade=" + idade + "]";
	}
}
