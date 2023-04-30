package seguradora;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		Map<String, Seguradora> seguradoras = new HashMap<>();
		interativo(seguradoras);
	}

	private static void interativo(Map<String, Seguradora> seguradoras) {
		System.out.println("Bem vindo ao menu interativo.");
		MenuOperacao.menuPrincipal(seguradoras);
		System.out.println("Tchau.");
	}

}