import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;

//KLASA REPREZENTUJĄCA PLANSZĘ (JPANEL) NA KTÓREJ RYSOWANA BĘDZIE GRA
public class Plansza extends JPanel implements KeyListener {

    private Graphics buforG;        //BUFOR GRAFIKI
    private Image buforI;
    private Silnik silnik;          //POLE REPREZENTUJĄCE SILNIK GRY
    private Timer timer;            //TIMER SŁUŻĄCY DO KONTROLI CZASU
    private Zegar zegar;            //INSTANCJA KLASY POZWALAJĄCA RYSOWAĆ KOLEJNE KLATKI GRY W OKREŚLONYCH ODSTĘPACH CZASU
    private int trudność=2;         //POZIOM TRUDNOŚCI (SZYBKOŚCI Z JAKĄ PORUSZA SIĘ WĄŻ
    Okno okno;

    public Plansza(Okno o) {
        super();
        okno=o;
        silnik=new Silnik(okno);

    }

    //METODA URUCHAMIAJĄCA WĘŻA Z SZYBKOŚCIĄ ZALEŻNĄ OD WYBRANEGO POZIOMU TRUDNOŚCI
    public void czasStart() {
        addKeyListener(this);
        requestFocus();

        zegar=new Zegar(this);
        timer=new Timer();
        switch(trudność){
            case 1:
                timer.scheduleAtFixedRate(zegar,1000,400);
                break;
            case 2:
                timer.scheduleAtFixedRate(zegar,1000,200);
                break;
            case 3:
                timer.scheduleAtFixedRate(zegar,1000,80);
                break;
        }
    }

    //METODA RYSUJĄCA KLATKĘ GRY
    public void paint(Graphics g) {
        buforI = createImage(getWidth(), getHeight());
        buforG = buforI.getGraphics();
        paintComponent(buforG);
        g.drawImage(buforI, getX(), getY(), getWidth(), getHeight(), null);

    }

    //METODA RYSUJĄCA POSZCZEGÓLNE ELEMENTY KLATKI GRY (WĄŻ I CIASTKO)
    public void paintComponent(Graphics g) {
        g.clearRect(getX(), getY(), getWidth(), getHeight());
        //buforG.drawLine(getX(),getY(),getWidth(),getHeight());
        //NARYSOWANIE CIASTKA
        buforG.setColor(Color.blue);
        buforG.fillRect((silnik.getCookie().x-1) * 16, (silnik.getCookie().y-1) * 16, 16, 16);
        //RYSOWANIE WĘŻA
        buforG.setColor(Color.BLACK);
        for (int i = 0; i < silnik.getSnake().size(); i++) {
            buforG.fillRect((silnik.getSnake().get(i).x-1) * 16, (silnik.getSnake().get(i).y-1) * 16, 16, 16);
        }
    }

    //GETTERY I SETTERY
    public Timer getTimer() {
        return timer;
    }
    public void setTrudność(int trudność) {
        this.trudność = trudność;
    }
    public Silnik getSilnik() {
        return silnik;
    }

    //OBSŁUGA KLAWIATURY
    @Override
    public void keyTyped(KeyEvent e) {
        char klawisz = e.getKeyChar();
        switch (klawisz) {
            case 'w':
                if (silnik.getPkierunek() !=2) {
                    silnik.setKierunek(1); }
                break;
            case 'W':
                if (silnik.getPkierunek() !=2) {
                    silnik.setKierunek(1); }
                break;
            case 'a':
                if (silnik.getPkierunek()!=4) {
                    silnik.setKierunek(3);
                }
                break;
            case 'A':
                if (silnik.getPkierunek()!=4) {
                    silnik.setKierunek(3);
                }
                break;
            case 's':
                if (silnik.getPkierunek()!=1) {
                    silnik.setKierunek(2);
                }
                break;
            case 'S':
                if (silnik.getPkierunek()!=1) {
                    silnik.setKierunek(2);
                }
                break;
            case 'd':
                if (silnik.getPkierunek()!=3) {
                    silnik.setKierunek(4);
                }
                break;
            case 'D':
                if (silnik.getPkierunek()!=3) {
                    silnik.setKierunek(4);
                }
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
