
import java.awt.HeadlessException;
import javax.swing.JFrame;


public class OyunEkrani extends JFrame {
    public static void main(String[] args) {
        
        OyunEkrani ekran = new OyunEkrani("Uzay Oyunu"); //burada JFrame oluşturduk
        
        ekran.setResizable(false);//ekranı boyutunu değiştirmeyi engelliyo
        ekran.setFocusable(false); // bütün işlemlerin JPanel de olsun diye
        //JFrame de odaklanmasını engeller
        
        ekran.setSize(800, 600);
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // çarpıya bastığımızda tamamen kapansın diye
        
        Oyun oyun = new Oyun(); // JPanelden obje oluşturduk
        
        oyun.requestFocus();// klavye işlemlerini anlaması için odağı verir. ilk önce focusu istemimiz gerekiyo. request istek demek
        // sonra klavye odağını isteyip sonra setFocusable yi true yapmamız gerekiyo bunu yapmazsak bazen klavyeden input çalışmaz
       oyun.addKeyListener(oyun);//KeyListener adında interface implemnt edicez klavye işlemlerimizi anlmasını sağlar
       //addKeyListener yaptığımız zaman keylistenerden bir referans bekliyor ama implement eden sınıfı da kullanabiliriz
       oyun.setFocusable(true);//JFrame dan focusu çektik ve artık odağı JPanele verdik
       oyun.setFocusTraversalKeysEnabled(false); //klavye işlemlerini JPanelin anlaması için gerekli metod
       //setFocustraversalKeysEnabled metoduna JPanel den false dememiz gerekiyoki klavye işlemlerimizi anlasın
       
       ekran.add(oyun); // JPanelimizi JFrame ekranımıza eklemiş oluyoruz
       
      ekran.setVisible(true); // ekranı true yapıyoruzki JFramimiz JPanel eklendiği anda oluşsun diye
              //paneli ekledikten sonra ekranının(JFramein) görünürlüğünü true yaptık. Mantıken ekledikten sonra görünmesi gerekiyo
              
              
              
       
     
        
        
        
    }

    public OyunEkrani(String title) throws HeadlessException {
        super(title);
    }
    
}
