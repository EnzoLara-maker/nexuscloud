import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Classe de domínio (POJO) que representa um Servidor virtual da NexusCloud.
 * É o "coração" do sistema: valida e armazena os containers alocados nele. (RA: 25178394-2)
 */
public class Servidor {

    // IP: identidade do servidor (dado lógico de negócio); privado e imutável. (RA: 25178394-2)
    private final String ip;

    // Memória RAM total (em GB) disponível no servidor. (RA: 25178394-2)
    private double ramTotal;

    // Preço cobrado por hora de uso do servidor (em R$). (RA: 25178394-2)
    private double precoPorHora;

    // Estado do servidor; por padrão false, ou seja, todo servidor "nasce desligado". (RA: 25178394-2)
    private boolean statusLigado = false;

    // RN02 - Lista cronológica de containers (ArrayList mantém a ordem de inserção). Já é inicializada
    // na declaração, assim nunca vale null e evita NullPointerException. (RA: 25178394-2)
    private List<Container> containers = new ArrayList<>();

    // Construtor: recebe os dados de configuração do servidor; o status e a lista usam os valores padrão acima. (RA: 25178394-2)
    public Servidor(String ip, double ramTotal, double precoPorHora) {
        // Guarda o IP (identidade) na instância. (RA: 25178394-2)
        this.ip = ip;
        // Guarda a RAM total na instância. (RA: 25178394-2)
        this.ramTotal = ramTotal;
        // Guarda o preço por hora na instância. (RA: 25178394-2)
        this.precoPorHora = precoPorHora;
    }

    // Getter do IP: somente leitura (identidade não pode mudar). (RA: 25178394-2)
    public String getIp() {
        return ip;
    }

    // Getter da RAM total. (RA: 25178394-2)
    public double getRamTotal() {
        return ramTotal;
    }

    // Setter da RAM total: permite, por exemplo, um upgrade de memória do servidor. (RA: 25178394-2)
    public void setRamTotal(double ramTotal) {
        this.ramTotal = ramTotal;
    }

    // Getter do preço por hora: usado pelo CloudManager no cálculo da fatura. (RA: 25178394-2)
    public double getPrecoPorHora() {
        return precoPorHora;
    }

    // Setter do preço por hora: permite reajustar a tarifa. (RA: 25178394-2)
    public void setPrecoPorHora(double precoPorHora) {
        this.precoPorHora = precoPorHora;
    }

    // Getter booleano do status (padrão "is"): true = ligado, false = desligado. (RA: 25178394-2)
    public boolean isStatusLigado() {
        return statusLigado;
    }

    // Getter dos containers: devolve uma visão SOMENTE LEITURA da lista; quem quiser adicionar um container
    // precisa passar por alocarContainer(), onde as regras são validadas (encapsulamento rigoroso). (RA: 25178394-2)
    public List<Container> getContainers() {
        return Collections.unmodifiableList(containers);
    }

    // Liga o servidor: muda o status para true, liberando a alocação de containers. (RA: 25178394-2)
    public void ligar() {
        this.statusLigado = true;
    }

    // Desliga o servidor: muda o status para false, bloqueando novas alocações. (RA: 25178394-2)
    public void desligar() {
        this.statusLigado = false;
    }

    /**
     * RN03 - Motor de alocação com política Fail-Fast: primeiro valida todas as regras e só depois
     * adiciona o container na lista; se qualquer regra falhar, lança a exceção e nada é alterado.
     * "throws" avisa que a CapacidadeExcedidaException (checked) pode ser lançada por este método. (RA: 25178394-2)
     */
    public void alocarContainer(Container c) throws CapacidadeExcedidaException {

        // Regra 1 (Status): servidor desligado não aceita containers. (RA: 25178394-2)
        if (!statusLigado) {
            // Lança a exceção UNCHECKED com a mensagem exigida; interrompe o método imediatamente. (RA: 25178394-2)
            throw new ServidorOfflineException("Erro: Não é possível alocar containers em um servidor desligado.");
        }

        // Regra 2 (Capacidade): RAM já em uso (via Streams) + RAM do novo container não pode passar da RAM total. (RA: 25178394-2)
        if (calcularRamEmUso() + c.getRamNecessaria() > ramTotal) {
            // Lança a exceção CHECKED com a mensagem exigida, incluindo o IP deste servidor. (RA: 25178394-2)
            throw new CapacidadeExcedidaException("Falha Crítica: Memória RAM insuficiente no servidor " + ip + ".");
        }

        // Só chega aqui se passou nas duas validações: adiciona o container no fim da lista (ordem cronológica). (RA: 25178394-2)
        containers.add(c);
    }

    /**
     * RN04 - Soma a RAM de todos os containers alocados usando Streams (sem for/while/if):
     * stream() abre o fluxo, mapToDouble extrai a ramNecessaria de cada Container e sum() soma tudo.
     * Lista vazia devolve 0.0 automaticamente. (RA: 25178394-2)
     */
    public double calcularRamEmUso() {
        return containers.stream()
                .mapToDouble(Container::getRamNecessaria)
                .sum();
    }

    // RN01 - equals: dois Servidores são iguais se tiverem o mesmo IP. (RA: 25178394-2)
    @Override
    public boolean equals(Object o) {
        // Mesma referência: iguais. (RA: 25178394-2)
        if (this == o) return true;
        // Nulo ou de outra classe: diferentes. (RA: 25178394-2)
        if (o == null || getClass() != o.getClass()) return false;
        // Cast seguro para Servidor. (RA: 25178394-2)
        Servidor outro = (Servidor) o;
        // Compara somente o IP (regra de identidade do negócio). (RA: 25178394-2)
        return Objects.equals(ip, outro.ip);
    }

    // RN01 - hashCode: coerente com o equals, calculado apenas a partir do IP. Isso faz o HashSet<Servidor>
    // do CloudManager rejeitar servidores duplicados. (RA: 25178394-2)
    @Override
    public int hashCode() {
        return Objects.hash(ip);
    }

    // toString: representação textual do servidor para os prints. (RA: 25178394-2)
    @Override
    public String toString() {
        return "Servidor{ip='" + ip + "', ramTotal=" + ramTotal + ", precoPorHora=" + precoPorHora
                + ", ligado=" + statusLigado + ", containers=" + containers.size() + "}";
    }
}
