import java.util.TimerTask;

//KLASA POZWALAJĄCA WYKONYWAĆ KOLEJNE ITERACJE GRY W OKREŚLONYCH ODSTĘPACH CZASU
public class Zegar extends TimerTask {

    private Plansza plansza;
    private Silnik silnik;


    public Zegar(Plansza p){

        plansza=p;
        silnik=plansza.getSilnik();

    }

    //CZYNNOŚCI WYKONYWANE W KAŻDEJ ITERACJI
    @Override
    public void run() {
        silnik.start();
        plansza.repaint();
    }
}
