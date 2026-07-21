package principal;

import java.io.IOException;
import java.util.Scanner;
import api.viaCEP;

public class Principal {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner leitura = new Scanner(System.in);
        viaCEP api = new viaCEP();
        String cep = "";
        while (cep.matches("")) {
            System.out.print("Digite o seu CEP: ");
            cep = leitura.nextLine();
            cep = api.tratarCEP(cep);
        }
        api.chamarAPI(cep);
        api.exibirCEP();
    }
}