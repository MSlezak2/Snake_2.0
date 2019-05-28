import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel implements ActionListener {

    private JButton ranking,wyjście,graj,latwy,sredni,trudny;     //PRZYCISKI MENU
    private Okno okno;                          //POLE REPREZENTUJĄCE OKNO APLIKACJI
    private JTextField nick;                    //POLE TEKSTOWE PRZECHOWUJĄCE NAZWĘ GRACZA

    public Menu(Okno o) {

        //INICJALIZACJA PÓL KLASY, USTAWIENIE LAYOUTU I KOLORU TŁA
        okno=o;
        setBackground(Color.green);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //ORGANIZACJA ELEMENTÓW MENU
        add(Box.createVerticalStrut(25));
        add(getRow1());     //LINIA Z POLEM TEKSTOWYM Z NAZWĄ UŻYTKOWNIKA
        add(Box.createVerticalStrut(25));
        add(getRow2());     //LINIA Z POZIOMEM TRUDNOŚCI
        add(Box.createVerticalStrut(100));
        add(getRow3());     //GRAJ
        add(Box.createVerticalStrut(40));
        add(getRow4());     //RANKING
        add(Box.createVerticalStrut(25));
        add(getRow5());     //WYJŚCIE
        add(Box.createVerticalGlue());
    }
    //LINIA Z POLEM TEKSTOWYM Z NAZWĄ UŻYTKOWNIKA
    private Component getRow1() {
        //INICJOWANIE BOXU
        Box box = Box.createHorizontalBox();
        setPrefferedMaxAndMinSize(box, (int) okno.getWidth(), 50);
        //BUDOWANIE BOXU (LINI ZE ZMIANĄ NICKU)
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.1)));
        JLabel label = new JLabel("TWÓJ NICK: ");
        setPrefferedMaxAndMinSize(label, (int) (okno.getWidth() * 0.4), 50);
        box.add(label);
        nick = new JTextField("Gracz_1");
        setPrefferedMaxAndMinSize(nick, (int) (okno.getWidth() * 0.4), 50);
        box.add(nick);
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.1)));
        return box;
    }
    //LINIA Z POZIOMEM TRUDNOŚCI
    private Component getRow2() {
        //INICJOWANIE BOXU
        Box box = Box.createHorizontalBox();
        setPrefferedMaxAndMinSize(box, (int) okno.getWidth(), 50);
        //BUDOWANIE BOXU (LINI ZE ZMIANĄ TRUDNOŚCI)
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.1)));
        JLabel label = new JLabel("POZIOM TRUDNOŚCI: ");
        setPrefferedMaxAndMinSize(label, (int) (okno.getWidth() * 0.25), 50);
        box.add(label);
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.05)));
        latwy = new JButton("ŁATWY");
        latwy.addActionListener(this);
        setPrefferedMaxAndMinSize(latwy, (int) (okno.getWidth() * 0.15), 50);
        box.add(latwy);
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.025)));
        sredni = new JButton("ŚREDNI");
        sredni.addActionListener(this);
        setPrefferedMaxAndMinSize(sredni, (int) (okno.getWidth() * 0.15), 50);
        box.add(sredni);
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.025)));
        trudny = new JButton("TRUDNY");
        trudny.addActionListener(this);
        setPrefferedMaxAndMinSize(trudny, (int) (okno.getWidth() * 0.15), 50);
        box.add(trudny);
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.1)));
        return box;
    }
    //LINIA Z PRZYCISKIEM "GRAJ"
    private Component getRow3() {
        //INICJOWANIE BOXU
        Box box = Box.createHorizontalBox();
        setPrefferedMaxAndMinSize(box, (int) okno.getWidth(), 120);
        //BUDOWANIE BOXU (LINI Z PRZYCISKIEM "GRAJ")
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.35)));
        graj=new JButton("GRAJ");
        graj.addActionListener(this);
        setPrefferedMaxAndMinSize(graj, (int) (okno.getWidth() * 0.3), 120);
        box.add(graj);
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.35)));
        return box;
    }
    //LINIA Z PRZYCISKIEM "RANKING"
    private Component getRow4() {
        //INICJOWANIE BOXU
        Box box = Box.createHorizontalBox();
        setPrefferedMaxAndMinSize(box, (int) okno.getWidth(), 90);
        //BUDOWANIE BOXU (LINI Z PRZYCISKIEM "RANKING")
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.375)));
        ranking=new JButton("RANKING");
        ranking.addActionListener(this);
        setPrefferedMaxAndMinSize(ranking, (int) (okno.getWidth() * 0.25), 90);
        box.add(ranking);
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.375)));
        return box;
    }
    //LINIA Z PRZYCISKIEM "WYJŚCIE"
    private Component getRow5() {
        //INICJOWANIE BOXU
        Box box = Box.createHorizontalBox();
        setPrefferedMaxAndMinSize(box, (int) okno.getWidth(), 60);
        //BUDOWANIE BOXU (LINI Z PRZYCISKIEM "WYJŚCIE")
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.4)));
        wyjście=new JButton("WYJŚCIE");
        wyjście.addActionListener(this);
        setPrefferedMaxAndMinSize(wyjście, (int) (okno.getWidth() * 0.2), 60);
        box.add(wyjście);
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.4)));
        return box;
    }

    //METODA USTALAJĄCA WYMIARY ELEMENTÓW OKNA
    void setPrefferedMaxAndMinSize(Component c, int width, int height) {
        Dimension rozmiar = new Dimension(width, height);
        c.setPreferredSize(rozmiar);
        c.setMaximumSize(rozmiar);
        c.setMinimumSize(rozmiar);
    }
    //GETTERY
    public JTextField getNick() {
        return nick;
    }
    //OBSŁUGA PRZYCISKÓW
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source=e.getSource();
        if(source==graj){
            setVisible(false);
            okno.startGra();
            okno.getGra().getPlansza().getSilnik().init();
            okno.getGra().setWynik(0);
            okno.getGra().odśwież();
        }
        else if(source==wyjście){
            okno.dispose();
            okno.getRanking().getDbc().rozłączenie();
        }
        else if(source==ranking){
            setVisible(false);
            okno.startRanking();
        }
        else if(source==latwy){
            okno.getGra().getPlansza().setTrudność(1);
        }
        else if(source==sredni){
            okno.getGra().getPlansza().setTrudność(2);
        }
        else if(source==trudny){
            okno.getGra().getPlansza().setTrudność(3);
        }
    }
}
