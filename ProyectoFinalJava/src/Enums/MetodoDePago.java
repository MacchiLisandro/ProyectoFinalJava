package Enums;

public enum MetodoDePago {
    EFECTIVO(0),
    TARJETA_DEBITO(10),
    TRANSFERENCIA(5);

    private int recargo;
    MetodoDePago (int recargo){
        this.recargo = recargo;
    }

    public int getRecargo(){
        return recargo;
    }
}
