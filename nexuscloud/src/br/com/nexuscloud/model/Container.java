import java.util.Objects;

/**
 * Classe de domínio (POJO) que representa um Container (aplicação) que roda dentro de um Servidor.
 * Cada container declara quanta memória RAM precisa para funcionar. (RA: [SEU RA])
 */
public class Container {

    // ID: dado lógico de negócio que identifica o container (ex.: "C-01"); privado e imutável. (RA: [SEU RA])
    private final String id;

    // Quantidade de RAM (em GB) que o container exige do servidor que o hospedar. (RA: [SEU RA])
    private double ramNecessaria;

    // Construtor: exige ID e RAM necessária na criação do container. (RA: [SEU RA])
    public Container(String id, double ramNecessaria) {
        // Atribui o ID recebido ao atributo da instância. (RA: [SEU RA])
        this.id = id;
        // Atribui a RAM recebida ao atributo da instância. (RA: [SEU RA])
        this.ramNecessaria = ramNecessaria;
    }

    // Getter do ID: somente leitura, pois o ID é a identidade (não há setId). (RA: [SEU RA])
    public String getId() {
        return id;
    }

    // Getter da RAM: usado pelo Servidor nas validações e pela Streams API na soma. (RA: [SEU RA])
    public double getRamNecessaria() {
        return ramNecessaria;
    }

    // Setter da RAM: a necessidade de memória pode mudar sem alterar quem o container é. (RA: [SEU RA])
    public void setRamNecessaria(double ramNecessaria) {
        this.ramNecessaria = ramNecessaria;
    }

    // RN01 - equals: dois Containers são iguais se tiverem o mesmo ID. (RA: [SEU RA])
    @Override
    public boolean equals(Object o) {
        // Mesma referência: iguais. (RA: [SEU RA])
        if (this == o) return true;
        // Nulo ou de outra classe: diferentes. (RA: [SEU RA])
        if (o == null || getClass() != o.getClass()) return false;
        // Cast seguro para Container. (RA: [SEU RA])
        Container outro = (Container) o;
        // Compara somente o ID (regra de identidade do negócio). (RA: [SEU RA])
        return Objects.equals(id, outro.id);
    }

    // RN01 - hashCode: coerente com o equals, calculado apenas a partir do ID. (RA: [SEU RA])
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString: representação textual do container para os prints. (RA: [SEU RA])
    @Override
    public String toString() {
        return "Container{id='" + id + "', ramNecessaria=" + ramNecessaria + "}";
    }
}
