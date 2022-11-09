package model.entity;

public class Artista {
    private int idArtista;
    private String nome;
    private String dataNascimento;
    private String contactos;
    private String biografia;
    private String imagem;


    public Artista(int idArtista, String nome, String dataNascimento, String contactos, String biografia, String imagem) {
        this.idArtista = idArtista;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.contactos = contactos;
        this.biografia = biografia;
        this.imagem = imagem;
    }

    public Artista() {
    }

    public Artista(String nome) {
        this.nome = nome;
    }

    public int getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(int idArtista) {
        this.idArtista = idArtista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getContactos() {
        return contactos;
    }

    public void setContactos(String contactos) {
        this.contactos = contactos;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
