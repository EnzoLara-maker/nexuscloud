import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Banco de dados principal da NexusCloud: associa cada Cliente ao conjunto de Servidores que ele possui.
 * Também é responsável pelo cálculo financeiro (fatura). (RA: [SEU RA])
 */
public class CloudManager {

    // RN02 - Map declarado pela INTERFACE (Map<>) e instanciado com HashMap, a implementação baseada em Hash
    // (busca por chave em tempo médio constante). Chave = Cliente; Valor = Set<Servidor>, que não aceita
    // duplicados nem garante ordem. Funciona porque Cliente e Servidor sobrescrevem equals() e hashCode(). (RA: [SEU RA])
    private Map<Cliente, Set<Servidor>> bancoDeDados = new HashMap<>();

    // Registra um cliente no banco de dados com um conjunto de servidores vazio.
    // putIfAbsent evita apagar os servidores de um cliente que já estava registrado (sem usar if). (RA: [SEU RA])
    public void registrarCliente(Cliente cliente) {
        bancoDeDados.putIfAbsent(cliente, new HashSet<>());
    }

    // Adiciona um servidor ao conjunto do cliente. computeIfAbsent devolve o conjunto existente (ou cria um
    // novo caso o cliente ainda não esteja registrado) e o add() ignora o servidor se o IP já existir. (RA: [SEU RA])
    public void adicionarServidor(Cliente cliente, Servidor servidor) {
        bancoDeDados.computeIfAbsent(cliente, chave -> new HashSet<>()).add(servidor);
    }

    // Getter do banco de dados: útil para consultas e depuração. (RA: [SEU RA])
    public Map<Cliente, Set<Servidor>> getBancoDeDados() {
        return bancoDeDados;
    }

    /**
     * RN04 - Fatura ativa do cliente, 100% com Streams (sem for/while/if):
     * 1) getOrDefault busca os servidores do cliente (ou um conjunto vazio se ele não existir, evitando null);
     * 2) stream() abre o fluxo de Servidores;
     * 3) filter() deixa passar APENAS os servidores ligados (servidor desligado não gera cobrança);
     * 4) mapToDouble extrai o precoPorHora de cada um;
     * 5) sum() devolve o total. (RA: [SEU RA])
     */
    public double calcularFaturaAtiva(Cliente c) {
        return bancoDeDados.getOrDefault(c, Set.of())
                .stream()
                .filter(Servidor::isStatusLigado)
                .mapToDouble(Servidor::getPrecoPorHora)
                .sum();
    }
}
