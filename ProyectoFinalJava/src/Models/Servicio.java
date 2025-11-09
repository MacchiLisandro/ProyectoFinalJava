package Models;

public class Servicio extends ItemTaller{
    private int tiempoEstimado;
    private String descripcion;

    public Servicio(String nombre, double precio, int tiempoEstimado, String descripcion) {
        super(nombre, precio);
        this.tiempoEstimado = tiempoEstimado;
        this.descripcion = descripcion;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(int tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
