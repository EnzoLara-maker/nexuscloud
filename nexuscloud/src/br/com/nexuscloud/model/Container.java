import java.util.Objects;

/**
 * Classe de domínio (POJO) que representa um Container (aplicação) que roda dentro de um Servidor.
 * Cada container declara quanta memória RAM precisa para funcionar. (RA: 25178394-2)
 */
public class Container {

    // ID: dado lógico de negócio que identifica o container (ex.: "C-01"); privado e imutável. (RA: 25178394-2)
    private final String id;

    // Quantidade de RAM (em GB) que o container exige do servidor que o hospedar. (RA: 25178394-2)
    private double ramNecessaria;

    // Construtor: exige ID e RAM necessária na criação do container. (RA: 25178394-2)
    public Container(String id, double ramNecessaria) {
        // Atribui o ID recebido ao atributo da instância. (RA: 25178394-2)
        this.id = id;
        // Atribui a RAM recebida ao atributo da instância. (RA: 25178394-2)
        this.ramNecessaria = ramNecessaria;
    }

    // Getter do ID: somente leitura, pois o ID é a identidade (não há setId). (RA: 25178394-2)
    public String getId() {
        return id;
    }

    // Getter da RAM: usado pelo Servidor nas validações e pela Streams API na soma. (RA: 25178394-2)
    public double getRamNecessaria() {
        return ramNecessaria;
    }

    // Setter da RAM: a necessidade de memória pode mudar sem alterar quem o container é. (RA: 25178394-2)
    public void setRamNecessaria(double ramNecessaria) {
        this.ramNecessaria = ramNecessaria;
    }

    // RN01 - equals: dois Containers são iguais se tiverem o mesmo ID. (RA: 25178394-2)
    @Override
    public boolean equals(Object o) {
        // Mesma referência: iguais. (RA: 25178394-2)
        if (this == o) return true;
        // Nulo ou de outra classe: diferentes. (RA: 25178394-2)
        if (o == null || getClass() != o.getClass()) return false;
        // Cast seguro para Container. (RA: 25178394-2)
        Container outro = (Container) o;
        // Compara somente o ID (regra de identidade do negócio). (RA: 25178394-2)
        return Objects.equals(id, outro.id);
    }

    // RN01 - hashCode: coerente com o equals, calculado apenas a partir do ID. (RA: 25178394-2)
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString: representação textual do container para os prints. (RA: 25178394-2)
    @Override
    public String toString() {
        return "Container{id='" + id + "', ramNecessaria=" + ramNecessaria + "}";
    }
}
