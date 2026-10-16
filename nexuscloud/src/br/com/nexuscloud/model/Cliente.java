import java.util.Objects;

/**
 * Classe de domínio (POJO) que representa um Cliente da NexusCloud.
 * Um Cliente é a "chave" do banco de dados do CloudManager, por isso a
 * identidade dele (CPF) é protegida e não pode ser alterada. (RA: 25178394-2)
 */
public class Cliente {

    // CPF: dado lógico de negócio que identifica o cliente; privado por causa do encapsulamento. (RA: 25178394-2)
    private final String cpf;

    // Nome do cliente (ex.: razão social); privado por causa do encapsulamento. (RA: 25178394-2)
    private String nome;

    // Construtor: obriga a informar CPF e nome na criação, evitando objetos "incompletos". (RA: 25178394-2)
    public Cliente(String cpf, String nome) {
        // Guarda o CPF recebido no atributo da própria instância. (RA: 25178394-2)
        this.cpf = cpf;
        // Guarda o nome recebido no atributo da própria instância. (RA: 25178394-2)
        this.nome = nome;
    }

    // Getter do CPF: permite apenas LER a identidade. Não existe setCpf de propósito, pois mudar o CPF
    // depois de o objeto estar dentro de um HashMap/HashSet quebraria a busca por hash. (RA: 25178394-2)
    public String getCpf() {
        return cpf;
    }

    // Getter do nome: devolve o nome atual do cliente. (RA: 25178394-2)
    public String getNome() {
        return nome;
    }

    // Setter do nome: o nome não é identidade, então pode ser alterado com segurança. (RA: 25178394-2)
    public void setNome(String nome) {
        this.nome = nome;
    }

    // RN01 - equals: dois Clientes são iguais se (e somente se) tiverem o mesmo CPF. (RA: 25178394-2)
    @Override
    public boolean equals(Object o) {
        // Mesma referência na memória: obviamente iguais (atalho de performance). (RA: 25178394-2)
        if (this == o) return true;
        // Nulo ou de outra classe nunca é igual a um Cliente. (RA: 25178394-2)
        if (o == null || getClass() != o.getClass()) return false;
        // Converte (cast) o Object para Cliente, já que a verificação acima garantiu o tipo. (RA: 25178394-2)
        Cliente outro = (Cliente) o;
        // A comparação usa SOMENTE o CPF; Objects.equals já trata CPF nulo sem NullPointerException. (RA: 25178394-2)
        return Objects.equals(cpf, outro.cpf);
    }

    // RN01 - hashCode: precisa usar o MESMO campo do equals (CPF); senão o contrato Java é violado e o
    // HashMap deixaria de encontrar o cliente. (RA: 25178394-2)
    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    // toString: facilita imprimir o cliente no console durante os testes. (RA: 25178394-2)
    @Override
    public String toString() {
        return "Cliente{cpf='" + cpf + "', nome='" + nome + "'}";
    }
}
