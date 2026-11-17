import java.util.Random;

public class SimulacionPuente {
    public static void main(String[] args) throws InterruptedException {
        Random rd = new Random();
        Puente puente = new Puente();
        for (int i = 0; i < 10; i++) {
            Thread thA = new Coche(puente);
            System.out.println("HILO " + i);
            System.err.println("Lance un coche");
            int valor = rd.nextInt(0, 10) * 1000;
            System.out.println("Cantidad de segundos a esperar: " + valor);
            System.out.println();
            Thread.sleep(valor);
            thA.start();
        }
    }
}
