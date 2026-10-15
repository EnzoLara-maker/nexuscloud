import java.util.Objects;

/**
 * Classe de domínio (POJO) que representa um Cliente da NexusCloud.
 * Um Cliente é a "chave" do banco de dados do CloudManager, por isso a
 * identidade dele (CPF) é protegida e não pode ser alterada. (RA: [SEU RA])
 */
public class Cliente {

    // CPF: dado lógico de negócio que identifica o cliente; privado por causa do encapsulamento. (RA: [SEU RA])
    private final String cpf;

    // Nome do cliente (ex.: razão social); privado por causa do encapsulamento. (RA: [SEU RA])
    private String nome;

    // Construtor: obriga a informar CPF e nome na criação, evitando objetos "incompletos". (RA: [SEU RA])
    public Cliente(String cpf, String nome) {
        // Guarda o CPF recebido no atributo da própria instância. (RA: [SEU RA])
        this.cpf = cpf;
        // Guarda o nome recebido no atributo da própria instância. (RA: [SEU RA])
        this.nome = nome;
    }

    // Getter do CPF: permite apenas LER a identidade. Não existe setCpf de propósito, pois mudar o CPF
    // depois de o objeto estar dentro de um HashMap/HashSet quebraria a busca por hash. (RA: [SEU RA])
    public String getCpf() {
        return cpf;
    }

    // Getter do nome: devolve o nome atual do cliente. (RA: [SEU RA])
    public String getNome() {
        return nome;
    }

    // Setter do nome: o nome não é identidade, então pode ser alterado com segurança. (RA: [SEU RA])
    public void setNome(String nome) {
        this.nome = nome;
    }

    // RN01 - equals: dois Clientes são iguais se (e somente se) tiverem o mesmo CPF. (RA: [SEU RA])
    @Override
    public boolean equals(Object o) {
        // Mesma referência na memória: obviamente iguais (atalho de performance). (RA: [SEU RA])
        if (this == o) return true;
        // Nulo ou de outra classe nunca é igual a um Cliente. (RA: [SEU RA])
        if (o == null || getClass() != o.getClass()) return false;
        // Converte (cast) o Object para Cliente, já que a verificação acima garantiu o tipo. (RA: [SEU RA])
        Cliente outro = (Cliente) o;
        // A comparação usa SOMENTE o CPF; Objects.equals já trata CPF nulo sem NullPointerException. (RA: [SEU RA])
        return Objects.equals(cpf, outro.cpf);
    }

    // RN01 - hashCode: precisa usar o MESMO campo do equals (CPF); senão o contrato Java é violado e o
    // HashMap deixaria de encontrar o cliente. (RA: [SEU RA])
    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    // toString: facilita imprimir o cliente no console durante os testes. (RA: [SEU RA])
    @Override
    public String toString() {
        return "Cliente{cpf='" + cpf + "', nome='" + nome + "'}";
    }
}
