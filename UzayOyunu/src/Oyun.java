
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates {
    private int x;
    private int y;

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    //ateşimiz her actionperfom metodu çalıştığında ileri gitmeye çalışacak
    //ateşimizin x ve y kordinatları olacak

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
    
    
}
public class Oyun extends JPanel implements KeyListener,ActionListener 
{//interfacein içindeki metodları implement etmemiz gerekiyo. override etmek için

    Timer timer = new Timer(5, this);//timer her çalıştığında actionperfom metodunu çalıştıracaz. Topu sağ kaydırıcaz
    private int gecen_sure = 0;
    private int harcanan_ates = 0;
    
    
    private  BufferedImage image; //image almak için BufferedImage sınıfından obje oluşturduk
    
    private ArrayList<Ates> atesler = new ArrayList<Ates>();
   //ateşlerimizi arraylistte tutucaz
    // ateşlerimiz sadece yukarı gidiyor. timer her çalışıtığında atşeler bi ileri gitmeli
    
    private int atesdirY = 1; //ateşimiz belli bir yerden oluşucak ileri gitmek için her actionperformda ateşimizin y kordinatina ekliycez. ateşimiz hareket etmiş olacak
    
    private int topX = 0; // topX topumuzun sağ sola gitmesini ayarlar
    // ilk önce 0,0 dan başlar. topX her actionperfom olduğunda topX bir arttırıcaz sağ sola gitmeyi ayarlıcak
    
    private int topdirX = 2; //topdirX i hep topX ekliycez topX sağa belli bir limitle çarpıtığında sola dönecek
    
    private int uzayGemisiX = 0; // uzay gemisinin nerden başlıycağını ayaralr
    // x kordinatı sıfır olsun tam alt soldan başalsın şeklinde ayarlıyoruz
    
    private int dirUzayX = 20; //klavyede sağ,sola bastığımda ekleyip çıkarıcam uzayGemisiX den uzay gemisi sağ sola kaymış olacak

    public boolean kontrolEt(){
        // ateşimiz topla çarpışırsa true dönücez
        
        for (Ates ates : atesler) {
            if(new Rectangle(ates.getX(),ates.getY(),10,20).intersects(new Rectangle(topX ,0,20,20)) )
            {
                //Rectangle classıyla iki dikdörtgenin çarpışmasına bakıyoruz. rectangle olarak tanımladık
                //intersects metoduyla başka bir rectangle ile çarpışmı diye bakıyoruz. dairede olsa rectangle olarak tanımladık
                // bu iki rectangle çarpışırsa intersects metodu true değer dönderecek if imize gireceğiz
                
                return true; // true değer döndürerek fonksiyonumuzu sonlandıracağız. bir tane ateş bile çarpışsa true dönecek

            }
        }
        // eğer hiç bir ateş çarpışmıyosa  for dan sonra return false dönecek
        return false;
        
        
    }
    
    
    public Oyun() {
        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")) );
            //imageimizi ImageIO sınıfı read fonksiyonuyla ekledik
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK); // jpanelimizin arka plan rengini constructorda belirliyoruz. backGround metoduyla
        
        //oyun constructorda başlar
        
        timer.start(); // timer başladığında içine gönderdiğimiz actionperformed metoduyla 5 saniyede bir çalışacak
        
        
        
        
    }
    //repaint java her repaint çağırdığında paintide çağrıyor 
    //repainti oyunlarda yazmamız gerekiyo . oyunda actionperformed yazdığımızda repainti en sonda çağrırarak paint işlemlerinin tekrar yapılmasını istiycez
    // repaint painti çağırırır ve şekillerimiz tekrar oluşur
    
    
    @Override
    public void repaint() {
        super.repaint(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    //şekillerimi jpanele çizmek için jcomponentin içindeki paint metodumu override ediyorum
    @Override
    public void paint(Graphics g) {
        super.paint(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        gecen_sure += 5; // paint metodu 5 ms de bir çalışıyo
        g.setColor(Color.red); //g.setColor un altındakiler artık belirlediğimi renkte olacak
        
        g.fillOval(topX, 0, 20, 20);
       
        g.drawImage(image, uzayGemisiX, 490, image.getWidth() / 10 , image.getHeight() / 10, this); //width genişlik demek , height yüksekliği verir
        //yüklediğimiz image ı programa eklemek için graphics deki drawImage metodunu kullandık
        //x i sıfır y yi sonda 490 verdik uzay gemimiz tam sol altta oldu
        //uzay gemimizin y kordinatı sabit , sadece sağ sola gidecek 
        // ilk değeri 0,490
        // jpanel in üzerinde oluşacagını söylemek için this kullanıyoruz yani jpanelin objesinde
        //drawImage de söylüyoruz
        
        for(Ates ates : atesler){
        
            if (ates.getY() < 0) {//ateşimiz jframeden çıktıysa ateşi ateşlerin içinden silmem gerekiyo
               
                atesler.remove(ates);
                
            }

        }
        g.setColor(Color.BLUE); // jframein içindeki ateşler, aşağısında çizilenler mavi olacak
        
        for(Ates ates : atesler){
           g.fillRect(ates.getX(), ates.getY(), 10, 20); //rect dikdörtgen demek fill.rect içi dolu dikdörtgen
            
        }
        if(kontrolEt())// eğer çarpışma olurs true dönecek. ife gireceke
        {
            timer.stop();
            String message = "Kazandınız...\n"+
                             "Harcanan Ateş : " + harcanan_ates + 
                             "\nGeçen Süre : " + (gecen_sure / 1000.0) + " saniye"; //tam bölme yapması için bini float cinsinden yazıyoruz
                     
            
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }

    
    
    
    
    @Override
    public void keyTyped(KeyEvent e) {
       
        
    }

    @Override
    public void keyPressed(KeyEvent e) {//klavye basışlarını keypressedde tetikler
         int c = e.getKeyCode();// getKeyCode sağ basarksak sağı sola basarsak solu verecek , bastığımız tuşları atadık

        if (c == KeyEvent.VK_LEFT) { // c == KeyEvent.VK_LEFt sola basıldımı kontrol eder
            if (uzayGemisiX <= 0) {
                uzayGemisiX = 0; // uzaygemsiinin 0. noktasını kontrol etmeliyiz yoksa frameden çıkar
                // direkt uzayGemisiX i sıfıra eşitledik
            }
            else{
                uzayGemisiX -= dirUzayX;
            }
            
        }
        else if (c == KeyEvent.VK_RIGHT) {// c == KeyEvent.VK_LEFt sağa basıldımı kontrol eder
            if (uzayGemisiX >= 750) {
                uzayGemisiX  = 750; // buradan sağ gidemezsin şeklinde uzay gemisini eşitledim
                
            }
            else {
                uzayGemisiX += dirUzayX;
                        
            }
        }
        
        else if (c == KeyEvent.VK_CONTROL) { //control tuşuna basılmışmı diye bakıyorum
    
                atesler.add(new Ates(uzayGemisiX+15,470));
                //ateşleri tam ucundan çıkmış gibi gözüksün diye uzaygemisinin konumuna +15 koyduk
                
                harcanan_ates++;
      
}
            
            
        }
        

    

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) { // kullanıcının yaptığı şeylere keypressed'de yazıyourz ama programın komutlarını actionperformed de yazıyoruz 
        for(Ates ates : atesler ){
            ates.setY(ates.getY() - atesdirY);// actionperformed metodu her 5 ms de bir çalışacak timer çalıştırır
        }
     topX += topdirX;
     
        if (topX >= 750) { //width genişliği 800 yaptık 
           topdirX = -topdirX;
           
        }
 
        if (topX <= 0 ) {
            topdirX = -topdirX;
            
        }
        //timer her çalıştığında actionperformed çalışacak her action performed çalıştığında
        //topX imzin konumu değişecek her değiştiğinde repaint i çalıştırnalıyız . repaint her çalıştığında paint metodunun güncel halini çalıştıracak
        // ve topumuz hareket etmiş olacak.
        repaint();
  
    }
    //KeyListener interface tuşa bastığımızda gerekli metodları kullanmamızı sağlıyor
    //toplarımızı ve uzay mekiğimizi hareket ettirmemiz için ActionListener interfaceni implemts etmemiz gerekiyo
    //actionlistener içindeki actionperfom metodunu kulllanıcaz. timer her çalıştığında actionperfom harekete geçecek topumuz hareket edecek

    
}
