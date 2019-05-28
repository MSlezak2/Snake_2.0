import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ranking extends JPanel implements ActionListener {

    private Object [][] treść=new Object [5][3];            //TABLICA Z ZAWARTOŚCIĄ TABELI WYNIKÓW
    private String[] tytuły={"MSCE","GRACZ","WYNIK"};       //TABLICA Z NAZWAMI KOLUMN TABELI WYNIKÓW
    private DBC dbc=DBC.getInstance();                      //POLE SŁUŻĄCE DO ŁĄCZENIA SIĘ Z BAZĄ DANYCH
    private JButton wyjscie;
    private Okno okno;

    public Ranking(Okno o) {

        //INICJALIZACJA PÓL KLASY, USTALENIE TŁA I LAYOUTU
        okno=o;
        setBackground(Color.green);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //ORGANIZACJA ELEMENTÓW OKNA
        add(Box.createVerticalStrut(25));
        add(getRow1());                 //LINIA Z TYTUŁEM
        add(Box.createVerticalStrut(25));
        add(getRow2());                 //LINIA Z TABELKĄ
        add(Box.createVerticalStrut(25));
        add(getRow3());                 //LINIA Z PRZYCISKAMI
        add(Box.createVerticalGlue());

    }

    //LINIA Z TYTUŁEM
    private Component getRow1() {
        //INICJOWANIE BOXU
        Box box = Box.createHorizontalBox();
        //BUDOWANIE BOXU (LINI Z TYTUŁEM)
        JLabel l=new JLabel("RANKING NAJLEPSZYCH WYNIKÓW");
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.add(l);
        return box;
    }

    //LINIA Z TABELKĄ
    private Component getRow2(){
        //INICJOWANIE BOXU
        Box box = Box.createHorizontalBox();
        // BUDOWANIE BOXU
        add(Box.createHorizontalStrut((int)(okno.getWidth()*0.3)));
            //DODANIE BOXU Z TABELĄ
        Box box2=Box.createVerticalBox();
        JTable tabela=new JTable(treść,tytuły){
                //UNIEMOŻLIWIENIE EDYTOWANIA TABELKI Z POZIOMU APLIKACJI
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //USTAWIENIE ROZMIARÓW KOLUMN
        TableColumn column = null;
        for (int i = 0; i < 2; i++) {
            column = tabela.getColumnModel().getColumn(i);
            if (i == 1) {
                column.setPreferredWidth((int)(okno.getWidth()*0.3));
            } else {
                column.setPreferredWidth((int)(okno.getWidth()*0.05));
            }
        }
                //AKTUALIZACJA ZAWARTOŚCI RANKINGU
        aktualizacja();
                //WSTAWIENIE NAGŁÓWKÓW KOLUMN
        box2.add(tabela.getTableHeader());
        box2.add(tabela);
        setPrefferedMaxAndMinSize(box2,(int)(okno.getWidth()*0.4),150);

        add(box2);
        add(Box.createHorizontalStrut((int)(okno.getWidth()*0.3)));
        return box;
    }

    //LINIA Z PRZYCISKAMI
    private Component getRow3(){
        //INICJOWANIE BOXU
        Box box = Box.createHorizontalBox();
        setPrefferedMaxAndMinSize(box, (int) okno.getWidth(), 50);
        //BUDOWANIE BOXU (LINI Z PRZYCISKIEM)
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.4)));
        wyjscie=new JButton("WYJŚCIE");
        wyjscie.addActionListener(this);
        setPrefferedMaxAndMinSize(wyjscie, (int) (okno.getWidth() * 0.2), 50);
        box.add(wyjscie);
        box.add(Box.createHorizontalStrut((int) (okno.getWidth() * 0.4)));
        return box;
    }

    //USTAWIENIE ROZMIARÓW POSZCZEGÓLNYCH KOMPONENTÓW
    void setPrefferedMaxAndMinSize(Component c, int width, int height) {
        Dimension rozmiar = new Dimension(width, height);
        c.setPreferredSize(rozmiar);
        c.setMaximumSize(rozmiar);
        c.setMinimumSize(rozmiar);
    }

    //METODA AKTUALIZUJĄCA ZAWARTOŚĆ TABELI NAJLEPSZYCH WYNIKÓW
    void aktualizacja(){
        dbc=DBC.getInstance();
        String zapytanie="SELECT * FROM `Ranking`";
        ResultSet rs=dbc.zapytanie(zapytanie);
        int i=0;
        if (rs != null) {
            try {
                while (rs.next()) {
                    treść[i][0] = rs.getInt(1);
                    treść[i][1] = rs.getString(2);
                    treść[i][2] = rs.getInt(3);
                    i++;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        dbc.zniszczenie(rs);
    }

    //METODA SPRAWDZAJĄCA CZY UZYSKANY WYNIK ZNAJDUJE SIĘ W PIERWSZEJ PIĄTCE NAJLEPSZYCH
    public void topScore(){
        aktualizacja();
        boolean top5=false;
        int j=4;
        for(int i=0; i<5; i++){
            if(top5==false && okno.getGra().getWynik()>(int)treść[i][2]){
                do{
                    if (j != i) {
                        for (int k = 1; k < 3; k++) {
                            treść[j][k] = treść[j - 1][k];
                        }
                        j--;
                    }
                    if (j == i) {
                        treść[j][1] = okno.getGra().getNick().getText();
                        treść[j][2] = okno.getGra().getWynik();
                    }
                }while(i!=j);
                top5=true;
            }
        }
        for(int i=0;i<5;i++){
            for(int k=0;k<3;k++) {
            }
        }
        zapiszWynik();
    }

    private void zapiszWynik() {
        String update1="UPDATE Ranking SET gracz='"+treść[0][1]+"',wynik="+treść[0][2]+" WHERE msce=1";
        String update2="UPDATE Ranking SET gracz='"+treść[1][1]+"',wynik="+treść[1][2]+" WHERE msce=2";
        String update3="UPDATE Ranking SET gracz='"+treść[2][1]+"',wynik="+treść[2][2]+" WHERE msce=3";
        String update4="UPDATE Ranking SET gracz='"+treść[3][1]+"',wynik="+treść[3][2]+" WHERE msce=4";
        String update5="UPDATE Ranking SET gracz='"+treść[4][1]+"',wynik="+treść[4][2]+" WHERE msce=5";
        dbc.modyfikacja(update1);
        dbc.modyfikacja(update2);
        dbc.modyfikacja(update3);
        dbc.modyfikacja(update4);
        dbc.modyfikacja(update5);
    }

    //GETTERY
    public DBC getDbc() {
        return dbc;
    }

    //OBSŁUGA PRZYCISKÓW
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source=e.getSource();
        //WCIŚNIĘCIE PRZYCISKU "WYLOGUJ"
        if(source==wyjscie){
            setVisible(false);
            okno.startMenu();
        }
    }
}