import java.util.Random;

public class Puente {
    Object objecto = new Object();
    Object objecto2 = new Object();

    Random rd = new Random();
    private int numeroMaxCoches = 3;
    public int numeroCochesAct = 0;
    private int pesoMaximo = 5000;
    public int pesoActual = 0;

    public Puente() {
    }

    public int getNumeroMaxCoches() {
        return numeroMaxCoches;
    }

    public int getNumeroCochesAct() {
        return numeroCochesAct;
    }

    public void setNumeroCochesAct(int numeroCochesAct) {
        this.numeroCochesAct = numeroCochesAct;
    }

    public int getPesoMaximo() {
        return pesoMaximo;
    }

    public int getPesoActual() {
        return pesoActual;
    }

    public void setPesoActual(int pesoActual) {
        this.pesoActual = pesoActual;
    }

    public boolean sePermiteElPaso(int pesoI){
        boolean b = false;
        synchronized (objecto) {
            b = prueba(pesoI);
        }
        try {
            if(b) {
                int cantidadAEsperar = rd.nextInt(10, 21) * 1000;
                System.out.println("HEY QUE ENTRÉ, habee cuanto tardo... " + cantidadAEsperar);

                Thread.sleep(cantidadAEsperar);
                finalizarPaso(pesoI);

                System.out.println("------Acabo de salir------");

                caracteristicasActuales();
                return true;
            }else {
                System.out.println("mal");
            }

        } catch (InterruptedException e) {
            System.err.println(e);
        }
        return false;
    }

    private boolean prueba(int pesoI) {
        try {
                System.out.println("Voy a intentar meterme en el puente pipi!!");
                while ((getNumeroCochesAct() >= getNumeroMaxCoches() || getPesoActual() >= getPesoMaximo())) {
                    System.out.println("El puente está lleno, que guay... voy a esperar");
                    objecto.wait();
                }

                if (!(getNumeroCochesAct() >= getNumeroMaxCoches() || getPesoActual() >= getPesoMaximo())) {

                    setNumeroCochesAct(getNumeroCochesAct() + 1);
                    setPesoActual(getPesoActual() + pesoI);

                    caracteristicasActuales();
                    return true;
                }
            }catch (InterruptedException e){
                System.err.println(e);
            }
        return false;
    }

    private void finalizarPaso(int pesoF){
                synchronized (objecto){
                setNumeroCochesAct(getNumeroCochesAct() - 1);
                setPesoActual(getPesoActual() - pesoF);
                System.out.println("Ya salí del puente, notificando...");
                objecto.notifyAll();
        }
    }

    private void caracteristicasActuales(){
        StringBuilder sb = new StringBuilder();

        sb.append("-------------------\n").
                append("Estado del puente\n")
                .append("Peso actual: ").append(getPesoActual()).append("\n")
                .append("Cantidad de coches: ").append(getNumeroCochesAct()).append("\n")
                .append("---------------------\n");

        System.out.println(sb.toString());
    }
}