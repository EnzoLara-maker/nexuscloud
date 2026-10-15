import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Classe de domínio (POJO) que representa um Servidor virtual da NexusCloud.
 * É o "coração" do sistema: valida e armazena os containers alocados nele. (RA: [SEU RA])
 */
public class Servidor {

    // IP: identidade do servidor (dado lógico de negócio); privado e imutável. (RA: [SEU RA])
    private final String ip;

    // Memória RAM total (em GB) disponível no servidor. (RA: [SEU RA])
    private double ramTotal;

    // Preço cobrado por hora de uso do servidor (em R$). (RA: [SEU RA])
    private double precoPorHora;

    // Estado do servidor; por padrão false, ou seja, todo servidor "nasce desligado". (RA: [SEU RA])
    private boolean statusLigado = false;

    // RN02 - Lista cronológica de containers (ArrayList mantém a ordem de inserção). Já é inicializada
    // na declaração, assim nunca vale null e evita NullPointerException. (RA: [SEU RA])
    private List<Container> containers = new ArrayList<>();

    // Construtor: recebe os dados de configuração do servidor; o status e a lista usam os valores padrão acima. (RA: [SEU RA])
    public Servidor(String ip, double ramTotal, double precoPorHora) {
        // Guarda o IP (identidade) na instância. (RA: [SEU RA])
        this.ip = ip;
        // Guarda a RAM total na instância. (RA: [SEU RA])
        this.ramTotal = ramTotal;
        // Guarda o preço por hora na instância. (RA: [SEU RA])
        this.precoPorHora = precoPorHora;
    }

    // Getter do IP: somente leitura (identidade não pode mudar). (RA: [SEU RA])
    public String getIp() {
        return ip;
    }

    // Getter da RAM total. (RA: [SEU RA])
    public double getRamTotal() {
        return ramTotal;
    }

    // Setter da RAM total: permite, por exemplo, um upgrade de memória do servidor. (RA: [SEU RA])
    public void setRamTotal(double ramTotal) {
        this.ramTotal = ramTotal;
    }

    // Getter do preço por hora: usado pelo CloudManager no cálculo da fatura. (RA: [SEU RA])
    public double getPrecoPorHora() {
        return precoPorHora;
    }

    // Setter do preço por hora: permite reajustar a tarifa. (RA: [SEU RA])
    public void setPrecoPorHora(double precoPorHora) {
        this.precoPorHora = precoPorHora;
    }

    // Getter booleano do status (padrão "is"): true = ligado, false = desligado. (RA: [SEU RA])
    public boolean isStatusLigado() {
        return statusLigado;
    }

    // Getter dos containers: devolve uma visão SOMENTE LEITURA da lista; quem quiser adicionar um container
    // precisa passar por alocarContainer(), onde as regras são validadas (encapsulamento rigoroso). (RA: [SEU RA])
    public List<Container> getContainers() {
        return Collections.unmodifiableList(containers);
    }

    // Liga o servidor: muda o status para true, liberando a alocação de containers. (RA: [SEU RA])
    public void ligar() {
        this.statusLigado = true;
    }

    // Desliga o servidor: muda o status para false, bloqueando novas alocações. (RA: [SEU RA])
    public void desligar() {
        this.statusLigado = false;
    }

    /**
     * RN03 - Motor de alocação com política Fail-Fast: primeiro valida todas as regras e só depois
     * adiciona o container na lista; se qualquer regra falhar, lança a exceção e nada é alterado.
     * "throws" avisa que a CapacidadeExcedidaException (checked) pode ser lançada por este método. (RA: [SEU RA])
     */
    public void alocarContainer(Container c) throws CapacidadeExcedidaException {

        // Regra 1 (Status): servidor desligado não aceita containers. (RA: [SEU RA])
        if (!statusLigado) {
            // Lança a exceção UNCHECKED com a mensagem exigida; interrompe o método imediatamente. (RA: [SEU RA])
            throw new ServidorOfflineException("Erro: Não é possível alocar containers em um servidor desligado.");
        }

        // Regra 2 (Capacidade): RAM já em uso (via Streams) + RAM do novo container não pode passar da RAM total. (RA: [SEU RA])
        if (calcularRamEmUso() + c.getRamNecessaria() > ramTotal) {
            // Lança a exceção CHECKED com a mensagem exigida, incluindo o IP deste servidor. (RA: [SEU RA])
            throw new CapacidadeExcedidaException("Falha Crítica: Memória RAM insuficiente no servidor " + ip + ".");
        }

        // Só chega aqui se passou nas duas validações: adiciona o container no fim da lista (ordem cronológica). (RA: [SEU RA])
        containers.add(c);
    }

    /**
     * RN04 - Soma a RAM de todos os containers alocados usando Streams (sem for/while/if):
     * stream() abre o fluxo, mapToDouble extrai a ramNecessaria de cada Container e sum() soma tudo.
     * Lista vazia devolve 0.0 automaticamente. (RA: [SEU RA])
     */
    public double calcularRamEmUso() {
        return containers.stream()
                .mapToDouble(Container::getRamNecessaria)
                .sum();
    }

    // RN01 - equals: dois Servidores são iguais se tiverem o mesmo IP. (RA: [SEU RA])
    @Override
    public boolean equals(Object o) {
        // Mesma referência: iguais. (RA: [SEU RA])
        if (this == o) return true;
        // Nulo ou de outra classe: diferentes. (RA: [SEU RA])
        if (o == null || getClass() != o.getClass()) return false;
        // Cast seguro para Servidor. (RA: [SEU RA])
        Servidor outro = (Servidor) o;
        // Compara somente o IP (regra de identidade do negócio). (RA: [SEU RA])
        return Objects.equals(ip, outro.ip);
    }

    // RN01 - hashCode: coerente com o equals, calculado apenas a partir do IP. Isso faz o HashSet<Servidor>
    // do CloudManager rejeitar servidores duplicados. (RA: [SEU RA])
    @Override
    public int hashCode() {
        return Objects.hash(ip);
    }

    // toString: representação textual do servidor para os prints. (RA: [SEU RA])
    @Override
    public String toString() {
        return "Servidor{ip='" + ip + "', ramTotal=" + ramTotal + ", precoPorHora=" + precoPorHora
                + ", ligado=" + statusLigado + ", containers=" + containers.size() + "}";
    }
}