package model.valueobjects;

public class Compra {
    private int idCliente;
    private  int idArte;
    private String date;
    private int unidades;
    private float precoTotal;

    public Compra(int idCliente, int idArte, String date, int unidades, float precoTotal) {
        this.idCliente = idCliente;
        this.idArte = idArte;
        this.date = date;
        this.unidades = unidades;
        this.precoTotal = precoTotal;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdArte() {
        return idArte;
    }

    public void setIdArte(int idArte) {
        this.idArte = idArte;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public float getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(float precoTotal) {
        this.precoTotal = precoTotal;
    }
}
