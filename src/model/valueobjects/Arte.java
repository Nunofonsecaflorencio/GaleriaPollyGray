package model.valueobjects;

public class Arte {
    private int idArte;
    private String titulo;
    private int unidades;
    private float preco;
    private String imagem;
    private String descricao;
    private String dataPublicacao;
    private boolean esgotado;

    public Arte(int idArte, String titulo, int unidades, float preco, String imagem, String descricao, String dataPublicacao, boolean esgotado) {
        this.idArte = idArte;
        this.titulo = titulo;
        this.unidades = unidades;
        this.preco = preco;
        this.imagem = imagem;
        this.descricao = descricao;
        this.dataPublicacao = dataPublicacao;
        this.esgotado = esgotado;
    }

    public Arte() {
    }

    public Arte(String imagem) {
        this.imagem = imagem;
    }

    public int getIdArte() {
        return idArte;
    }

    public void setIdArte(int idArte) {
        this.idArte = idArte;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public boolean isEsgotado() {
        return esgotado;
    }

    public void setEsgotado(boolean esgotado) {
        this.esgotado = esgotado;
    }

    @Override
    public String toString() {
        return "Arte{" +
                "idArte=" + idArte +
                ", titulo='" + titulo + '\'' +
                ", unidades=" + unidades +
                ", preco=" + preco +
                ", imagem='" + imagem + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataPublicacao='" + dataPublicacao + '\'' +
                ", esgotado=" + esgotado +
                '}';
    }
}
