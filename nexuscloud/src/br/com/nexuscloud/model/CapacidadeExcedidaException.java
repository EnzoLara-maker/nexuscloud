/**
 * Exceção CHECKED (verificada): estende Exception (e NÃO RuntimeException), então o compilador
 * OBRIGA quem chama o método a tratar (try-catch) ou propagar (throws). Representa uma situação
 * de negócio previsível: o servidor ficou sem memória RAM. (RA: [SEU RA])
 */
public class CapacidadeExcedidaException extends Exception {

    // Versão de serialização exigida pelo Java para exceções (elas são Serializable); sem isso o VS Code mostra um aviso amarelo. (RA: [SEU RA])
    private static final long serialVersionUID = 1L;

    // Construtor que repassa a mensagem de erro para a classe pai (Exception). (RA: [SEU RA])
    public CapacidadeExcedidaException(String mensagem) {
        super(mensagem);
    }
}
