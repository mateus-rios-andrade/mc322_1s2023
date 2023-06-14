package csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSV {
	private final List<List<String>> dados;

	private CSV(List<List<String>> dados) {
		this.dados = dados;
	}

	public static void main(String[] args) {
		
	}

	public static CSV deArquivo(String nomeArquivo) {
		try (FileReader fr = new FileReader(nomeArquivo);
				BufferedReader br = new BufferedReader(fr)) {
			String l;
			boolean primeiraLinha = true;
			var dados = new ArrayList<List<String>>();
			while ((l = br.readLine()) != null) {
				if (primeiraLinha) {
					primeiraLinha = false;
				} else {
					List<String> a = new ArrayList<>();
					dados.add(a);
					List<Character> chars = new ArrayList<>();
					boolean aspas = false;
					boolean contrabarra = false;
					var linha = l.toCharArray();
					System.out.println();
					for (char chr : linha) {
						if (aspas) {
							if (contrabarra) {
								chars.add(chr);
							} else if (chr == '"') {
								aspas = false;
							} else if (chr == '\\') {
								contrabarra = true;
							} else {
								chars.add(chr);
							}
						} else {
							if (chr == '"') {
								aspas = true;
							} else if (chr == ',') {
								var sb = new StringBuilder(chars.size());
								for (char c : chars) {
									sb.append(c);
								}
								a.add(sb.toString());
								chars.clear();
							} else {
								chars.add(chr);
							}
						}
					}
				}
			}
			return new CSV(dados);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return null; // não
	}

	public List<List<String>> getDados() {
		return dados;
	}
}
