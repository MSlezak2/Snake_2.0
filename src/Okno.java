import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//KLASA REPREZENTUJĄCA OKNO, W KTÓREJ UMIESZCZANE SĄ POSZCZEGÓLNE ELEMENTY PROGRAMU
public class Okno extends JFrame{

    private Menu menu;                  //INSTANCJA KLASY REPREZENTUJĄCEJ MENU APLIKACJI
    private Gra gra;                    //INSTANCJA KLASY REPREZENTUJĄCEJ OKNO Z GRĄ
    private Ranking ranking;            //INSTANCJA KLASY REPREZENTUJĄCEJ RANKING NAJLEPSZYCH WYNIKÓW
    private int width=800, height=650;  //WYMIARY OKNA

    public Okno() {

        //NADANIE TYTUŁU OKNA, JEGO WYMIARÓW ORAZ LAYOUTU
        setTitle("SNAKE");
        setBounds(100, 100, width,height);
        getContentPane().setBackground(Color.blue);

        //INICJALIZACJA PÓL I WŁĄCZENIE MENU APLIKACJI
        menu = new Menu(this);
        ranking = new Ranking(this);
        gra = new Gra(this);
        startMenu();

        //USTAWIENIE WIDOCZNOŚCI I USTALENIE DZIAŁAŃ WYWOŁANYCH PO ZAMKNIĘCIU OKNA
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ranking.getDbc().rozłączenie();
                System.exit(0);
            }
        });
    }
    //METODA URUCHAMIAJĄCA MENU APLIKACJI
    public void startMenu(){
        getContentPane().add(menu);
        menu.setVisible(true);
    }
    //METODA URUCHAMIAJĄCA RANKING
    public void startRanking(){
        getContentPane().add(ranking);
        ranking.setVisible(true);
        ranking.aktualizacja();
    }
    //METODA URUCHAMIAJĄCA OKNO GRY
    public void startGra(){
        gra.getNick().setText(menu.getNick().getText());
        getContentPane().add(gra);
        gra.setVisible(true);
    }
    //GETTERY
    public Ranking getRanking() {
        return ranking;
    }
    public Gra getGra() {
        return gra;
    }

    public static void main(String[] args){
        Okno okno=new Okno();
    }

}
