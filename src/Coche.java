import java.util.Random;

public class Coche extends Thread{
    Random rd = new Random();
    private int pesoCoche;
    private Puente puente;

    public Coche(Puente puente){
        this.puente = puente;
        this.pesoCoche = rd.nextInt(80,2001);
    }

    public int getPesoCoche() {
        return pesoCoche;
    }

    @Override
    public void run(){
        if(puente.sePermiteElPaso(getPesoCoche())){
            System.out.println("//Se ha reazlido el pase");
        }else {
            System.out.println("//NO se ha reazlido el pase");
        }
    }

}
