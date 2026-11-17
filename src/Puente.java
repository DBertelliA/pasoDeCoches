import java.util.Random;

public class Puente {
    Object objecto = new Object();

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

    /**
     * Este metodo tiene el objetivo de devolver un valor true o false en funcion de si ha realizado correctamente su funcion o no
     * Dicha funcion consiste en la sincronizacion de la entrada de los coches de manera constante
     * Una vez entrados, despues de pasar por una serie de condiciones que controla "control()" los hilos generan un numero del 10 al 20 convertido en segundos
     * para simular un tiempo de espera, el cual no afectará a los demas hilos requieran el objeto usado para sincronizar, puesto que este se encuentra fuera de ella
     * Una vez pasado el tiempo, se llama a siguiente funcion para modificar unos valores y devolver un true
     * PesoI es el peso pasado por los hilos de los coches
     *
     * @param pesoI
     * @return
     */
    public boolean sePermiteElPaso(int pesoI){
        boolean b = false;
        synchronized (objecto) {
            b = control(pesoI);
        }
        try {
            if(b) {
                caracteristicasActuales();

                int cantidadAEsperar = rd.nextInt(10, 21) * 1000;
                System.out.println("HEY QUE ENTRÉ, habee cuanto tardo... " + cantidadAEsperar);

                Thread.sleep(cantidadAEsperar);
                finalizarPaso(pesoI);

                System.out.println("------Acabo de salir------");

                return true;
            }else {
                System.out.println("mal");
            }

        } catch (InterruptedException e) {
            System.err.println(e);
        }
        return false;
    }

    /**
     * Esta funcion se encarga de permitir el paso a los coches si el puente no supera unos limites
     * En caso de superarlos, entrarán en un bucle que los dormirá constantemente si se siguen cumpliendo sus condiciones
     * Luego de despertar y revisar los parametros, se realiza una segunda medicion en caso de que otro hilo por si acaso cambia los valores
     * seguido de eso, una vez dentro del if se cambian los datos para añadir lo que ahora contiene el puente y delvolver un true indicando que se ha hecho con exito
     * en caso contrario se devolverá un false significando un fallo en el orden por mala suerte o por casualidades (Muy poco probable)
     * El pesoI es el peso de los coches
     *
     * @param pesoI
     * @return
     */

    private boolean control(int pesoI) {
        try {
                System.out.println("Voy a intentar meterme en el puente pipi!!");
                while ((getNumeroCochesAct() >= getNumeroMaxCoches() || getPesoActual() > getPesoMaximo()) || (getPesoActual() + pesoI) > getPesoMaximo()) {
                    System.out.println("El puente está lleno, que guay... voy a esperar");
                    objecto.wait();
                }

                if (!(getNumeroCochesAct() >= getNumeroMaxCoches() || getPesoActual() >= getPesoMaximo())) {

                    setNumeroCochesAct(getNumeroCochesAct() + 1);
                    setPesoActual(getPesoActual() + pesoI);
                    return true;
                }
            }catch (InterruptedException e){
                System.err.println(e);
            }
        return false;
    }

    /**
     * Esta funcion se encargar de cambiar los valores del puente una vez se pasa el sleep
     * y se resincroniza con el objeto para poder llamar y despertar a aquellos estan esperando
     * Esta funcion solo puede ser llamado por hilos que han completado su recorrido en el puente
     *
     * @param pesoF
     */

    private void finalizarPaso(int pesoF){
                synchronized (objecto){
                setNumeroCochesAct(getNumeroCochesAct() - 1);
                setPesoActual(getPesoActual() - pesoF);
                System.out.println("Ya salí del puente, notificando...");
                objecto.notifyAll();
        }
    }

    /**
     * EL proposito de esta funcion es el solo mostrar informacion para hacer pruebas
     */

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