import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//KLASA REPREZENTUJĄCA OKNO Z GRĄ (WŁĄCZANA Z POZIOMU MENU)
public class Gra extends JPanel implements ActionListener {

    private Okno okno;                      //POLE REPREZENTUJĄCE OKNO APLIKACJI
    private int wynik=0;                    //POLE PRZECHOWUJĄCE WYNIK PUNKTOWY
    private Plansza plansza;                //PLANSZA NA KTÓREJ RYSOWANA JEST GRA
    private JButton playPauza, menu;        //PRZYCISKI ZNAJDUJĄCE SIĘ W OKNIE
    private JLabel nick=new JLabel(),punkty;       //ETYKIETKA WYŚWIETLAJĄCA NAZWĘ GRACZA ORAZ PUNKTY
    private Box box2;                       //BOX PRZECHOWUJĄCY PLANSZĘ

    public Gra(Okno o){

        //INICJALIZACJA PÓL KLASY, USTAWIENIE LAYOUTU ORAZ KOLORU TŁA
        okno=o;
        setBackground(Color.green);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        //ORGANIZACJA ELEMENTÓW OKNA
        add(getRow1());                         //LINIA Z NAZWĄ GRACZA I WYNIKIEM
        add(getRow2());                         //LINIA Z PLANSZĄ GRY
        add(Box.createVerticalStrut(10));
        add(getRow3());                         //LINIA Z PRZYCISKAMI
        add(Box.createVerticalGlue());
    }

    //LINIA Z NAZWĄ GRACZA I WYNIKIEM
    public Component getRow1(){
        Box box=Box.createHorizontalBox();
        setPrefferedMaxAndMinSize(box,(int)okno.getWidth(),40);
        //BUDOWANIE BOXU
        box.add(Box.createHorizontalStrut((int)(okno.getWidth()*0.3)));
        setPrefferedMaxAndMinSize(nick,(int)(okno.getWidth()*0.3),40);
        box.add(nick);
        punkty=new JLabel(""+wynik);
        setPrefferedMaxAndMinSize(punkty,(int)(okno.getWidth()*0.2),40);
        box.add(punkty);
        box.add(Box.createHorizontalStrut((int)(okno.getWidth()*0.2)));
        return box;
    }
    //LINIA Z PLANSZĄ GRY
    public Component getRow2(){
        box2=Box.createHorizontalBox();
        setPrefferedMaxAndMinSize(box2,(int)okno.getWidth(),496);
        //BUDOWANIE BOXU
        plansza=new Plansza(okno);
        box2.add(plansza);
        plansza.setBackground(Color.white);
        return box2;
    }
    //LINIA Z PRZYCISKAMI
    public  Component getRow3(){
        Box box=Box.createHorizontalBox();
        setPrefferedMaxAndMinSize(box,(int)okno.getWidth(),50);
        //BUDOWANIE BOXU
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.2)));
        playPauza = new JButton("PLAY");
        playPauza.addActionListener(this);
        setPrefferedMaxAndMinSize(playPauza, (int) (okno.getWidth() * 0.2), 50);
        box.add(playPauza);
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.2)));
        menu = new JButton("MENU");
        menu.addActionListener(this);
        setPrefferedMaxAndMinSize(menu, (int) (okno.getWidth() * 0.2), 50);
        box.add(menu);
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.2)));
        return box;
    }

    //METODA UAKTUALNIAJĄCA LICZBĘ PUNKTÓW JAKA POWINNA ZOSTAĆ WYŚWIETLONA
    public void odśwież(){
        punkty.setText(""+wynik);
    }

    //METODA USTAWIAJĄCA GABARYTY ELEMENTÓW OKNA
    void setPrefferedMaxAndMinSize(Component c, int width, int height){
        Dimension rozmiar=new Dimension(width, height);
        c.setMaximumSize(rozmiar);
        c.setMinimumSize(rozmiar);
        c.setPreferredSize(rozmiar);
    }

    //GETTERY I SETTERY
    public int getWynik() {
        return wynik;
    }
    public Plansza getPlansza() {
        return plansza;
    }
    public void setWynik(int wynik) {
        this.wynik = wynik;
    }
    public JButton getPlayPauza() {
        return playPauza;
    }
    public JLabel getNick() {
        return nick;
    }

    //OBSŁUGA PRZYCISKÓW
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source=e.getSource();
        //POWRÓT DO MENU
        if(source==menu){
            setVisible(false);
            okno.startMenu();
        }else if(source==playPauza){
            //ZATRZYMANIE/WZNOWIENIE GRY
            if(playPauza.getText()=="PLAY"){
                plansza.czasStart();
                playPauza.setText("PAUZA");
            }else if(playPauza.getText()=="PAUZA"){
                plansza.getTimer().cancel();
                playPauza.setText("PLAY");
            }else if(playPauza.getText()=="NOWA GRA"){
                plansza.getSilnik().init();
                wynik=0;
                odśwież();
                plansza.czasStart();
                playPauza.setText("PAUZA");
            }
        }
    }
}
