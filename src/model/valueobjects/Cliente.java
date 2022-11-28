package model.valueobjects;

public class Cliente {
    private int idCliente;
    private String nome;
    private String endereco;
    private String contacto;

    public Cliente(int idCliente, String nome, String endereco, String contacto) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.endereco = endereco;
        this.contacto = contacto;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
}
