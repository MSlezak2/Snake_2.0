import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//KLASA SŁUŻĄCA DO POŁĄCZENIA I OBSŁUGI BAZY DANYCH
public class DBC {
    private Connection baza;        //POLE SŁUŻĄCE DO KONTAKTOWANIA SIĘ Z BAZĄ DANYCH
    private static DBC instance;    //INSTANCJA KLASY (PROJEKT SINGLETON)

    private DBC(){
        try { //ŁĄCZENIE Z BAZĄ DANYCH
            baza = (Connection) DriverManager.getConnection("jdbc:mysql://mysql.agh.edu.pl:3306/slezak", "slezak", "wB1Jyvi5aNYDDo7N");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //TWORZENIE INSTANCJI KLASY
    public static DBC getInstance(){
        if(instance==null){
            instance=new DBC();
        }
        return instance;
    }

    //METODA WYSYŁAJĄCA ZAPYTANIE DO BAZY DANYCH (ZWRACAJĄCA DANE)
    public ResultSet zapytanie(String z) {
        ResultSet r=null;
        try {
            Statement s = baza.createStatement();
            r = s.executeQuery(z);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return r;
    }
    //METODA MODYFIKUJĄCA DANE W BAZIE
    public boolean modyfikacja(String m) {
        int i=0;
        try {
            Statement s = baza.createStatement();
            i = s.executeUpdate(m);
            if (i == 0) return false;
            else return true;
        }catch (SQLException e){
           e.printStackTrace();
           return false;
        }
    }
    //ROZŁĄCZENIE Z BAZĄ DANYCH
    public void rozłączenie(){
        try{
            baza.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //ZNISZCZENIE OBIEKTU RESULTSET
    public void zniszczenie(ResultSet r){
        try{
            r.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
