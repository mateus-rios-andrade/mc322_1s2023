package seguradora;

import java.util.Map;
import java.util.Scanner;

public interface Menu {
    public int codigo();
    public void acao(Scanner sc, Map<String, Seguradora> seguradoras);
}
