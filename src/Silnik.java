import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Silnik {

    private ArrayList<Point> snake;         //ŁAŃCUCH PUNKTÓW REPREZENTUJĄCY WĘŻA
    private ArrayList<Point> bufor;         //BUFOR POMAGAJĄCY ZAAMIĘTAĆ POPRZEDNIE POŁOŻENIE WĘŻA
    private Point cookie;                   //PUNKT REPREZENTUJĄCY CIASTKO
    private int kierunek=4,pkierunek=4;     //ZMIENNE PAMIĘTAJĄCE KIERUNEK OBECNY ORAZ OSTATNI
    private boolean zjedzono=false;         //ZMIENNA ZAPISUJĄCA CZY W DANEJ ITERACJI GRY ZOSTAŁO ZJEDZONE CIASTKO
    private Okno okno;

    public Silnik(Okno o){

        okno=o;
        //USTAWIENIE ZMIENNYCH (WĘŻA, BUFOR, CIASTKO) POD PIERWSZĄ KLATKĘ GRY
        init();
    }

    public void init() {
        //ZAINICJOWANIE DOMYŚLNEGO KIERUNKU POCZĄTKOWEGO
        kierunek=4;
        pkierunek=4;
        //ZAINICJOWANIE CZTEROPIKSELOWEGO WĘŻA
        snake=new ArrayList<Point>();
        addPoint(snake,21,15);
        addPoint(snake,20,15);
        addPoint(snake,19,15);
        addPoint(snake,18,15);
        //ZAINICJOWANIE BUFORA
        bufor=new ArrayList<Point>(snake.size());
        for(int i=0;i<4;i++){
            addPoint(bufor,snake.get(i).x,snake.get(i).y);
        }
        //WYLOSOWANIE CIASTECZKA
        losowanieCiastka();
    }

    //METODA DODAJĄCA PUNKT DO WĘŻA/BUFORA
    public void addPoint(ArrayList<Point> list,int x,int y){
        list.add(new Point(x,y));
    }

    //METODA LOSUJĄCA CIASTKO NA EKRAN
    public void losowanieCiastka(){
        Random generator = new Random();
        //SPRAWDZANIE CZY CIASTKO NIE POJAWIŁO SIĘ NA WĘŻU
        boolean spr=true;
        while(spr) {
            cookie = new Point(generator.nextInt(49)+1, generator.nextInt(31)+1);
            spr=false;
            for (int i = 0; i < snake.size(); i++) {
                Point p=new Point(snake.get(i).x, snake.get(i).y);
                if(p.x==cookie.x && p.y==cookie.y){
                    spr=true;
                }
            }
        }
    }

    //METODA WYKONUJĄCA KOLEJNE KLATKI GRY
    public void start(){
        //WZROST (O ILE ZJEDZONO CIASTKO)
        wzrost();
        //SKOPIOWANIE WĘŻA
        kopiaWęża();
        //PRZESUNIĘCIE GŁOWY WĘŻA (W ZALEŻNOŚCI OD WYBRANEGO NA KLAWIATURZE KIERUNKU)
        ruchGłowy();
        //PRZESUNIĘCIE RESZTY CIAŁA
        ruchReszty();
        //JEDZENIE
        jedzenie();
        //ZAPAMIĘTANIE JAKI BYŁ KIERUNEK RUCHU
        pkierunek=kierunek;
        //SPRAWDZENIE CZY NIE PRZEGRANO
        przegrana();
    }


    //WZROST I LOSOWANIE KOLEJNEGO CIASTKA
    private void wzrost() {
        if(zjedzono==true){
            snake.add(new Point(bufor.get(bufor.size()-1).x,bufor.get(bufor.size()-1).y));
            bufor.add(new Point(1,1));
            zjedzono=false;
            losowanieCiastka();
        }
    }

    //SKOPIOWANIE POŁOŻENIA WĘŻA DO BUFORA
    private void kopiaWęża() {
        for (int i=0;i<snake.size();i++) {
            bufor.set(i,new Point(snake.get(i).x,snake.get(i).y));
        }
    }

    //PRZESUNIĘCIE GŁOWY WĘŻA ZGODNIE Z KIERUNKIEM ZADANYM Z KLAWIATURY
    private void ruchGłowy() {
        switch (kierunek) {
            case 1:
                snake.get(0).y--;
                break;
            case 2:
                snake.get(0).y++;
                break;
            case 3:
                snake.get(0).x--;
                break;
            case 4:
                snake.get(0).x++;
                break;
        }
    }

    //PRZESUNIĘCIE RESZTY CIAŁA WĘŻA
    private void ruchReszty() {
        for (int i=0;i<bufor.size()-1;i++) {
            snake.set(i+1,new Point(bufor.get(i).x,bufor.get(i).y));
        }
    }

    //AKTUALIZACJA WYNIKU PO ZJEDZENIU CIASTKA
    private void jedzenie() {
        if(snake.get(0).x==cookie.x && snake.get(0).y==cookie.y){
            zjedzono=true;
            okno.getGra().setWynik(okno.getGra().getWynik()+1);
            okno.getGra().odśwież();
        }
    }

    //SPRAWDZENIE CZY NIE NASTĄPIŁA PRZEGRANA
    private void przegrana() {
        if(snake.get(0).x<=0 || snake.get(0).x>49 || snake.get(0).y<=0 || snake.get(0).y>31){
            okno.getRanking().topScore();
            okno.getGra().getPlansza().getTimer().cancel();
            okno.getGra().getPlayPauza().setText("NOWA GRA");
        }
        for(int i=1;i<snake.size();i++){
            if(snake.get(0).x==snake.get(i).x && snake.get(0).y==snake.get(i).y){
                okno.getRanking().topScore();
                okno.getGra().getPlansza().getTimer().cancel();
                okno.getGra().getPlayPauza().setText("NOWA GRA");
            }
        }
    }

    //GETTERY I SETTERY
    public Point getCookie() {
        return cookie;
    }
    public ArrayList<Point> getSnake() {
        return snake;
    }
    public int getPkierunek() {
        return pkierunek;
    }
    public void setKierunek(int kierunek) {
        this.kierunek = kierunek;
    }
}
