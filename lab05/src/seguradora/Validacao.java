package seguradora;

import java.util.regex.Pattern;

public class Validacao {
	/**
	 * Esse método supõe que 'letra' significa qualquer caractere considerado como tal pelo
	 * grupo '\w' do regex e que um único espaço é permitido entre os caracteres.
	 */
	public static boolean validarNome(String nome) {
		return Pattern.compile("^\\w( \\w|\\w)*$", Pattern.UNICODE_CHARACTER_CLASS).matcher(nome).matches();
	}

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

	private static final int[] multiplicadores = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

	public static boolean validarCNPJ(String cnpj) {
		var digitos = cnpjToArray(cnpj);
		if (digitos == null)
			return false;
		int primeiroDigito = getDV(digitos, 1);
		if (primeiroDigito != digitos[12])
			return false;
		int segundoDigito = getDV(digitos, 0);
		if (segundoDigito != digitos[13])
			return false;
		return true;
	}

	private static int getDV(int[] digitos, int m) {
		int soma = 0;
		for (int i = 0; i < 13 - m; i++) {
			soma += multiplicadores[i + m] * digitos[i];
		}
		int dv = 11 - (soma % 11);
		return dv == 10 ? 0 : dv;
	}

	private static int[] cnpjToArray(String cnpj) {
		cnpj = cnpj.replaceAll("[^0-9]", "");
		if (cnpj.length() != 14) {
			return null;
		}
		var array = new int[14];
		for (int i = 0; i < 14; i++) {
			array[i] = cnpj.charAt(i) - '0';
		}
		return array;
	}
}
