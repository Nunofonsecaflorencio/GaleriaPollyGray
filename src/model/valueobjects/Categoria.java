package model.valueobjects;

public class Categoria {
    private int idCategoria;
    private String nome;
    private int quantidadeArtes;

    public Categoria(int idCategoria, String nome, int quantidadeArtes) {
        this.idCategoria = idCategoria;
        this.nome = nome;
        this.quantidadeArtes = quantidadeArtes;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidadeArtes() {
        return quantidadeArtes;
    }

    public void setQuantidadeArtes(int quantidadeArtes) {
        this.quantidadeArtes = quantidadeArtes;
    }

    @Override
    public String toString() {
        return nome + " (" + quantidadeArtes + ")";
    }
}
