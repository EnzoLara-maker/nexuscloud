/**
 * Exceção UNCHECKED (não verificada): estende RuntimeException, então o compilador NÃO obriga
 * o uso de try-catch nem de "throws". Representa uma falha de uso/programação: tentar alocar
 * container num servidor desligado. (RA: [SEU RA])
 */
public class ServidorOfflineException extends RuntimeException {

    // Versão de serialização exigida pelo Java para exceções (elas são Serializable); sem isso o VS Code mostra um aviso amarelo. (RA: [SEU RA])
    private static final long serialVersionUID = 1L;

    // Construtor que repassa a mensagem de erro para a classe pai (RuntimeException). (RA: [SEU RA])
    public ServidorOfflineException(String mensagem) {
        super(mensagem);
    }
}