package Enums;

public enum MetodoDePago {
    EFECTIVO(0),
    TARJETA_DEBITO(0.1),
    TRANSFERENCIA(0.05);

    private double recargo;
    MetodoDePago (double recargo){
        this.recargo = recargo;
    }

    public double getRecargo(){
        return recargo;
    }
}
