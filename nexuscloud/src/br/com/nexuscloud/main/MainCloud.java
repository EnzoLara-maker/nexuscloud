import java.util.Locale;

/**
 * Nome: Enzo do Nascimento Lara | RA: 25178394-2
 *
 * "throws CapacidadeExcedidaException" na main é necessário porque alocarContainer() declara uma exceção
 * CHECKED, e a maioria das chamadas ficam fora de try-catch. (RA: 25178394-2)
 */
public class MainCloud {

    public static void main(String[] args) throws CapacidadeExcedidaException {

        // cria o banco de dados principal (CloudManager). (RA: 25178394-2)
        CloudManager manager = new CloudManager();

        // cria 1 Cliente com CPF e nome. (RA: 25178394-2)
        Cliente cliente = new Cliente("111.222.333-44", "Wayne Enterprises");

        // cria 1 Servidor com IP, RAM total de 16.0 GB e preço de R$ 5.50 por hora. O servidor nasce desligado. (RA: 25178394-2)
        Servidor servidor = new Servidor("192.168.0.1", 16.0, 5.50);

        // registra o cliente no banco de dados do Manager. (RA: 25178394-2)
        manager.registrarCliente(cliente);
        // vincula o servidor ao cliente (entra no Set<Servidor> dele). (RA: 25178394-2)
        manager.adicionarServidor(cliente, servidor);

        // cria o Container 1 (ID "C-01", precisa de 10.0 GB de RAM). (RA: 25178394-2)
        Container container1 = new Container("C-01", 10.0);
        // cria o Container 2 (ID "C-02", precisa de 8.0 GB de RAM). (RA: 25178394-2)
        Container container2 = new Container("C-02", 8.0);

        // com a linha ativa, o servidor ainda está desligado e a ServidorOfflineException aparece em vermelho no console, provando a regra.
        // Após o teste, a linha foi comentada para o programa poder prosseguir. (RA: 25178394-2)
        // servidor.alocarContainer(container1);

        // liga o servidor, liberando as alocações. (RA: 25178394-2)
        servidor.ligar();

        // aloca o Container 1 com sucesso (10.0 de 16.0 GB). (RA: 25178394-2)
        servidor.alocarContainer(container1);
        // Confirmação visual da alocação bem-sucedida. (RA: 25178394-2)
        System.out.println("Container " + container1.getId() + " alocado com sucesso. RAM em uso: "
                + servidor.calcularRamEmUso() + " GB");

        // try-catch obrigatório, pois 10.0 + 8.0 = 18.0 ultrapassa os 16.0 GB. (RA: 25178394-2)
        try {
            // Tentativa de alocar o Container 2; aqui a exceção será lançada. (RA: 25178394-2)
            servidor.alocarContainer(container2);
            // Esta linha NÃO executa, pois o lançamento da exceção pula direto para o catch. (RA: 25178394-2)
            System.out.println("Container " + container2.getId() + " alocado com sucesso.");
        } catch (CapacidadeExcedidaException e) {
            // Captura a exceção e imprime a mensagem de forma elegante, sem derrubar o programa. (RA: 25178394-2)
            System.out.println("[ALOCACAO RECUSADA] " + e.getMessage());
        }

        // imprime a fatura ativa do cliente (servidor ligado de R$ 5.50/h => 5.50).
        // Locale.US garante o ponto decimal, como no enunciado. (RA: 25178394-2)
        System.out.printf(Locale.US, "Fatura ativa de %s: R$ %.2f%n",
                cliente.getNome(), manager.calcularFaturaAtiva(cliente));
    }
}
