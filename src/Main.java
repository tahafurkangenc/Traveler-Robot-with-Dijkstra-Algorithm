import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;
import javax.swing.Timer;

abstract class Izgara{
    int x;
    int y;
    int width;
    int height;
    Color color;
    JPanel panel;
    int dugumnumarasi=-1;
    JLabel sis = new JLabel("X");
    public Izgara() {
    }
    public Izgara(int x, int y, int width ,int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public void generateMethod(Frame frame){
        panel.setBackground(Color.green);
        panel.setBounds(x,y,width,height);
        frame.add(panel);
    }
    public void removesis()
    {
        panel.remove(sis);
    }
}

class Yol extends Izgara{
    public Yol(int x, int y, int width, int height, Frame frame) {
        super(x, y, width, height);
        panel=new JPanel();
        panel.setBackground(Color.CYAN);
        panel.setBounds(x,y,width,height);
        sis.setHorizontalAlignment(JLabel.CENTER);
        sis.setVerticalAlignment(JLabel.CENTER);
        panel.add(sis);
        frame.add(panel);

    }

    /*public void generateMethod(Frame frame){
       panel.setBackground(Color.green);
       panel.setBounds(x,y,width,height);
       frame.add(panel);
    }*/

    @Override
    public String toString() {
        return "Yol";
    }
}

class Engel extends Izgara{
    public Engel(int x, int y, int width, int height, Frame frame) {
        super(x, y, width, height);
        panel=new JPanel();
        panel.setBackground(Color.BLUE);
        panel.setBounds(x,y,width,height);
        sis.setHorizontalAlignment(JLabel.CENTER);
        sis.setVerticalAlignment(JLabel.CENTER);
        panel.add(sis);
        frame.add(panel);
    }

    @Override
    public String toString() {
        return "Engel";
    }
}

class Dijkstraclass {
    public static ArrayList<Integer> dijkstra(int [][] graf, int baslangicindis, int bitisindis){
        int[] uzaklik = new int[graf.length]; // En kısa mesafeleri tutar
        boolean[] ziyaretedildimi = new boolean[graf.length]; // Ziyaret edilen düğümleri tutar
        int[] oncekidugum = new int[graf.length]; //(yolu göstermek için) önceki düğümleri tutar

        // Diğer tüm düğümlerin mesafeleri sonsuz olarak ayarlanır
        //ziyaretedildimi false ayarlanır
        for (int i = 0; i < graf.length; i++) {
            uzaklik[i]=Integer.MAX_VALUE;
            ziyaretedildimi[i]=false;
        }


        // Başlangıç düğümüne mesafe 0 olarak ayarlanır
        uzaklik[baslangicindis]=0;

        // Tüm düğümleri ziyaret edene kadar döngü
        for (int i = 0; i < graf.length-1; i++) {
            // Henüz ziyaret edilmemiş en kısa mesafeli düğümü bul
            int minimumindex = minimumDugumBul(uzaklik, ziyaretedildimi);

            // Düğümü ziyaret et
            ziyaretedildimi[minimumindex] = true;

            // Düğümün tüm komşularını kontrol et
            for (int j = 0; j < graf.length; j++) {
                // Henüz ziyaret edilmemiş ve minimumindex'den j'ye olan mesafe pozitif ise
                if (!ziyaretedildimi[j] && graf[minimumindex][j] > 0) {
                    int denenecekuzaklik = uzaklik[minimumindex] + graf[minimumindex][j];
                    // Yeni mesafe, mevcut mesafeden daha kısa ise güncelle
                    if (denenecekuzaklik < uzaklik[j]) {
                        uzaklik[j] = denenecekuzaklik;
                        oncekidugum[j] = minimumindex;
                    }
                }
            }
        }
        for (int i = 0; i < graf.length; i++) {
            System.out.println( baslangicindis + ". indisten " + i + ". indise olan minimum uzaklik: " + uzaklik[i]);
        }

        // Güzergahları yazdır
        for (int i = 0; i < graf.length; i++) {
            if (i != baslangicindis) {
                ArrayList<Integer> yol = new ArrayList<Integer>();
                int j = i;
                while (j != baslangicindis) {
                    yol.add(j);
                    j = oncekidugum[j];
                }
                yol.add(baslangicindis);
                Collections.reverse(yol);
                System.out.println(baslangicindis + ". indisten " + i + ". indise olan minimum yol : " + yol);
            }
        }

        ArrayList <Integer> enkisayol = new ArrayList<Integer>();
        int j = bitisindis;
        while (j != baslangicindis) { // onceki dugumleri sira sira ekleyerek bir yol olusturuyor
            enkisayol.add(j);
            j = oncekidugum[j];
        }
        enkisayol.add(baslangicindis);
        Collections.reverse(enkisayol); // ters ceviriyoruz
        System.out.println(baslangicindis + ". indisten " + bitisindis + ". indise olan minimum yol : " + enkisayol);

        return enkisayol;


    }

    // Henüz ziyaret edilmemiş en kısa mesafeli düğümü bulur
    private static int minimumDugumBul(int[] uzaklik, boolean[] ziyaretedildimi) {
        int minimumindex = -1;
        int minimumuzaklik = Integer.MAX_VALUE;

        for (int i = 0; i < uzaklik.length; i++) {
            if (!ziyaretedildimi[i] && uzaklik[i] < minimumuzaklik) {
                minimumindex = i;
                minimumuzaklik = uzaklik[i];
            }
        }
        return minimumindex;
    }


}

class Mazemaker {

    private int[][] maze;
    private int satir;
    private int sutun;

    Random random = new Random();

    public Mazemaker(int satir, int sutun) { //boyutlari alir
        this.satir = satir;
        this.sutun = sutun;
        this.maze = new int[satir][sutun];
    }

    public void doldur(int baslangicX, int baslangicY) { //baslangic konumlarini alir
        Random random = new Random();

        // butun maze 1 ile doldurulur
        for (int i = 0; i < satir; i++) {
            for (int j = 0; j < sutun; j++) {
                maze[i][j] = 1;
            }
        }

        maze[baslangicX][baslangicY] = 0;//baslangici yol yapar
        //recursive baslatir
        mazeOlustur(baslangicX, baslangicY);
    }

    private void mazeOlustur(int x, int y) {

        int[] yonler = new int[]{1, 2, 3, 4};
        for (int i = yonler.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = yonler[index];
            yonler[index] = yonler[i];
            yonler[i] = temp;
        }//4 yone dogru olan hamleleri sirayla yapmak icin karistirir

        for (int i = 0; i < yonler.length; i++) {
            switch (yonler[i]) {
                case 1: // yukari
                    if (x - 2 <= 0) {
                        continue;
                    }
                    if (maze[x - 2][y] != 0) {//eğer 2 ileride duvar yoksa olusturur
                        maze[x - 2][y] = 0;   // bunun sebebi arada duvarlarin olusabilmesidir
                        maze[x - 1][y] = 0;
                        mazeOlustur(x - 2, y);
                    }
                    break;
                case 2: // sag
                    if (y + 2 >= sutun - 1) {
                        continue;
                    }
                    if (maze[x][y + 2] != 0) {
                        maze[x][y + 2] = 0;
                        maze[x][y + 1] = 0;
                        mazeOlustur(x, y + 2);
                    }
                    break;
                case 3: // asagi
                    if (x + 2 >= satir - 1) {
                        continue;
                    }
                    if (maze[x + 2][y] != 0) {
                        maze[x + 2][y] = 0;
                        maze[x + 1][y] = 0;
                        mazeOlustur(x + 2, y);
                    }
                    break;
                case 4: // sol
                    if (y - 2 <= 0) {
                        continue;
                    }
                    if (maze[x][y - 2] != 0) {
                        maze[x][y - 2] = 0;
                        maze[x][y - 1] = 0;
                        mazeOlustur(x, y - 2);
                    }
                    break;
            }
        }
    }

    public int[][] getmaze() {
        boolean oldumu=false;
        for (int i = maze.length-1; i > -1; i--) {
            if(oldumu==true)
                break;
            for (int j = maze[0].length-1; j > -1; j--) { // en sona hedefi koyar
                if(maze[i][j]==0){//buldugu ilk yol
                    maze[i][j]=9;

                    oldumu=true;
                    break;
                }

            }

        }
        return maze;
    }
}

class Point{
    int i;
    int j;

    public Point(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
}

class Robot{

    int robotunIkoordinati;
    int robotunJkoordinati;

    int labirentboyutu;
    int robotungecirdigisure;

    int[][] robothafiza;

    ArrayList <Point> robotpath = new  ArrayList <Point>();

    public Robot(int robotunIkoordinati, int robotunJkoordinati,int maze[][],Izgara[][] izgaralar) {
        this.robotunIkoordinati = robotunIkoordinati;
        this.robotunJkoordinati = robotunJkoordinati;
        int baslangicI= robotunIkoordinati;
        int baslangicJ= robotunJkoordinati;
        robothafiza=new int[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                robothafiza[i][j]=2;
            }
        }
        robothafiza[robotunIkoordinati][robotunJkoordinati]=0;
        izgaralar[robotunIkoordinati][robotunJkoordinati].removesis();
        robotungecirdigisure=0;
        robothareket(maze, izgaralar, robotunIkoordinati, robotunJkoordinati,robothafiza); // recursive baslatir
        JLabel baslangicisareti = new JLabel("Start");
        baslangicisareti.setVerticalAlignment(JLabel.CENTER); // label ortalanir
        baslangicisareti.setHorizontalAlignment(JLabel.CENTER);
        izgaralar[baslangicI][baslangicJ].panel.add(baslangicisareti);
        //dijkstra(grafOlustur(robothafiza,izgaralar),0,50);
        //grafOlustur(robothafiza,izgaralar);
        /*for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if(robothafiza[i][j]==3){
                    izgaralar[i][j].panel.setBackground(Color.MAGENTA);
                }
            }
        }*/

    }

    public int getRobotungecirdigisure() {
        return robotungecirdigisure;
    }

    public int[][] getRobothafiza() {
        return robothafiza;
    }

   /* public static void beklet(int sure)
    {
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //...Perform a task...

                System.out.println("Reading SMTP Info.");
            }
        };
        Timer timer = new Timer(100 ,taskPerformer);
        timer.setRepeats(false);
        timer.start();

        Thread.sleep(5000);
    }*/

    public ArrayList<Point> getRobotpath() {
        return robotpath;
    }

    public boolean robothareket(int[][] maze, Izgara[][] izgaralar, int robotunIkoordinati, int robotunJkoordinati, int[][]robothafiza)
    {
        robotungecirdigisure++;
       /* try
        {
            Thread.sleep(50);
        }
        catch(InterruptedException ex)
        {
            ex.printStackTrace();
        }*/


        if(maze[robotunIkoordinati][robotunJkoordinati]==9)
        {
            izgaralar[robotunIkoordinati][robotunJkoordinati].panel.setBackground(Color.MAGENTA);
            System.out.println("ROBOT HAFIZA");
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[0].length; j++) {
                    System.out.print(robothafiza[i][j]+" ");
                }
                System.out.println();
            }
            return true;

        }


        if(maze[robotunIkoordinati][robotunJkoordinati]==0)
        {

            maze[robotunIkoordinati][robotunJkoordinati]=3; // robotun gectigi yollar

            robothafiza[robotunIkoordinati][robotunJkoordinati]=maze[robotunIkoordinati][robotunJkoordinati]; // robothafizaya eklemeler

            robothafiza[robotunIkoordinati][robotunJkoordinati-1]=maze[robotunIkoordinati][robotunJkoordinati-1];
            //izgaralar[robotunIkoordinati][robotunJkoordinati-1].removesis();

            robothafiza[robotunIkoordinati][robotunJkoordinati+1]=maze[robotunIkoordinati][robotunJkoordinati+1];
            // izgaralar[robotunIkoordinati][robotunJkoordinati+1].removesis();

            robothafiza[robotunIkoordinati+1][robotunJkoordinati]=maze[robotunIkoordinati+1][robotunJkoordinati];
            //izgaralar[robotunIkoordinati+1][robotunJkoordinati].removesis();

            robothafiza[robotunIkoordinati-1][robotunJkoordinati]=maze[robotunIkoordinati-1][robotunJkoordinati];
            //izgaralar[robotunIkoordinati-1][robotunJkoordinati].removesis();

            // sis kaldirilir
            izgaralar[robotunIkoordinati][robotunJkoordinati-1].sis.setVisible(false);
            izgaralar[robotunIkoordinati][robotunJkoordinati+1].sis.setVisible(false);
            izgaralar[robotunIkoordinati+1][robotunJkoordinati].sis.setVisible(false);
            izgaralar[robotunIkoordinati-1][robotunJkoordinati].sis.setVisible(false);

            izgaralar[robotunIkoordinati][robotunJkoordinati].panel.setBackground(Color.GREEN);// oldugu yeri boyuyoruz
            robotpath.add(new Point(robotunIkoordinati,robotunJkoordinati)); // robotun yolunu ekliyoruz

            /*System.out.println();
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[0].length; j++) {
                    System.out.print(maze[i][j]+" ");
                }
                System.out.println();
            }*/

            if(maze[robotunIkoordinati+1][robotunJkoordinati]==9){
                izgaralar[robotunIkoordinati][robotunJkoordinati].panel.setBackground(Color.MAGENTA);
                izgaralar[robotunIkoordinati+1][robotunJkoordinati].panel.setBackground(Color.ORANGE);
                System.out.println("ROBOT HAFIZA");
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[0].length; j++) {
                        System.out.print(robothafiza[i][j]+" ");
                    }
                    System.out.println();
                }
                return true;
            }

            if(maze[robotunIkoordinati-1][robotunJkoordinati]==9){
                izgaralar[robotunIkoordinati][robotunJkoordinati].panel.setBackground(Color.MAGENTA);
                izgaralar[robotunIkoordinati-1][robotunJkoordinati].panel.setBackground(Color.ORANGE);
                System.out.println("ROBOT HAFIZA");
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[0].length; j++) {
                        System.out.print(robothafiza[i][j]+" ");
                    }
                    System.out.println();
                }
                return true;
            }

            if(maze[robotunIkoordinati][robotunJkoordinati-1]==9){
                izgaralar[robotunIkoordinati][robotunJkoordinati].panel.setBackground(Color.MAGENTA);
                izgaralar[robotunIkoordinati][robotunJkoordinati-1].panel.setBackground(Color.ORANGE);
                System.out.println("ROBOT HAFIZA");
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[0].length; j++) {
                        System.out.print(robothafiza[i][j]+" ");
                    }
                    System.out.println();
                }
                return true;
            }

            if(maze[robotunIkoordinati][robotunJkoordinati+1]==9){
                izgaralar[robotunIkoordinati][robotunJkoordinati].panel.setBackground(Color.MAGENTA);
                izgaralar[robotunIkoordinati][robotunJkoordinati+1].panel.setBackground(Color.ORANGE);
                System.out.println("ROBOT HAFIZA");
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[0].length; j++) {
                        System.out.print(robothafiza[i][j]+" ");
                    }
                    System.out.println();
                }
                return true;
            }

            //asagı
            boolean returndeger=robothareket(maze, izgaralar, robotunIkoordinati+1, robotunJkoordinati,robothafiza);
            //sol
            if(!returndeger){
                returndeger=robothareket(maze, izgaralar, robotunIkoordinati, robotunJkoordinati+1,robothafiza);
            }
            //yukari
            if(!returndeger){
                returndeger=robothareket(maze, izgaralar, robotunIkoordinati-1, robotunJkoordinati,robothafiza);
            }
            //sag
            if(!returndeger){
                returndeger=robothareket(maze, izgaralar, robotunIkoordinati, robotunJkoordinati-1,robothafiza);
            }

            if (returndeger){
                izgaralar[robotunIkoordinati][robotunJkoordinati].panel.setBackground(Color.MAGENTA);
            }

            return returndeger;



        }


        /*for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                System.out.print(robothafiza[i][j]+" ");
            }
            System.out.println();
        }*/


        return false;

    }

    public int[][] grafOlustur(int [][] robothafiza,Izgara[][] izgaras){
        int dugumsayisi=0; //dugumsayisi
        for (int i = 0; i < robothafiza.length; i++) {
            for (int j = 0; j < robothafiza[0].length; j++) {

                if(robothafiza[i][j]==3 || robothafiza[i][j]==0 || robothafiza[i][j]==9){ //eger yolsa dugum olur
                    izgaras[i][j].dugumnumarasi=dugumsayisi;
                    dugumsayisi++;
                }

               /*if (robothafiza[i][j]==3){

                    if(robothafiza[i+1][j]==0 || robothafiza[i][j+1]==0 || robothafiza[i-1][j]==0 || robothafiza[i][j-1]==0){
                        izgaras[i][j].dugumnumarasi=dugumsayisi;
                        dugumsayisi++;
                    }
                    else{
                        int boyaliyolsayisi=0;

                        if(robothafiza[i+1][j]==3){
                            boyaliyolsayisi++;
                        }
                        if(robothafiza[i][j+1]==3){
                            boyaliyolsayisi++;
                        }
                        if(robothafiza[i-1][j]==3){
                            boyaliyolsayisi++;
                        }
                        if(robothafiza[i][j-1]==3){
                            boyaliyolsayisi++;
                        }
                        if(boyaliyolsayisi>1){
                            izgaras[i][j].dugumnumarasi=dugumsayisi;
                            dugumsayisi++;
                        }

                    }
                }*/

              /*if(robothafiza[i][j]==0){
                   int geceryolsayisi=0;

                   if(robothafiza[i+1][j]==3 || robothafiza[i+1][j]==0){
                       geceryolsayisi++;
                   }
                   if(robothafiza[i][j+1]==3 || robothafiza[i][j+1]==0){
                       geceryolsayisi++;
                   }
                   if(robothafiza[i-1][j]==3 || robothafiza[i-1][j]==0){
                       geceryolsayisi++;
                   }
                   if(robothafiza[i][j-1]==3 || robothafiza[i][j-1]==0){
                       geceryolsayisi++;
                   }
                   if(geceryolsayisi>1){
                       izgaras[i][j].dugumnumarasi=dugumsayisi;
                       dugumsayisi++;
                   }


               }*/



            }

        }
        System.out.println("DUGUM HARITASI  "+dugumsayisi);
        for (int i = 0; i < robothafiza.length; i++) {
            for (int j = 0; j < robothafiza[0].length; j++) {
                if(izgaras[i][j].dugumnumarasi!=-1 && izgaras[i][j].dugumnumarasi<10)
                    System.out.print(izgaras[i][j].dugumnumarasi+"  ");
                else
                    System.out.print(izgaras[i][j].dugumnumarasi+" ");
            }
            System.out.println();
        }
        int[][] graph=new int[dugumsayisi][dugumsayisi];

        for (int i = 0; i < robothafiza.length; i++) {
            for (int j = 0; j < robothafiza[0].length; j++) {
                if(izgaras[i][j].dugumnumarasi!=-1){

                    //sağ kontrol
                    for(int sag=i+1;sag<robothafiza.length;sag++){
                        if(robothafiza[sag][j]==2 || robothafiza[sag][j]==1) {
                            break;
                        }
                        if(izgaras[sag][j].dugumnumarasi!=-1){
                            graph[izgaras[sag][j].dugumnumarasi][izgaras[i][j].dugumnumarasi]=sag-i;
                            break;
                        }
                    }

                    //sol kontrol
                    for(int sol=i-1;sol>0;sol--){
                        if(robothafiza[sol][j]==2 || robothafiza[sol][j]==1) {
                            break;
                        }
                        if(izgaras[sol][j].dugumnumarasi!=-1){
                            graph[izgaras[sol][j].dugumnumarasi][izgaras[i][j].dugumnumarasi]=i-sol;
                            break;
                        }
                    }

                    //aşağı kontrol
                    for(int asagi=j+1;asagi<robothafiza[0].length;asagi++){
                        if(robothafiza[i][asagi]==2 || robothafiza[i][asagi]==1) {
                            break;
                        }
                        if(izgaras[i][asagi].dugumnumarasi!=-1){
                            graph[izgaras[i][asagi].dugumnumarasi][izgaras[i][j].dugumnumarasi]=asagi-j;
                            break;
                        }
                    }

                    //yukari kontrol
                    for(int yukari=j-1;yukari>0;yukari--){
                        if(robothafiza[i][yukari]==2 || robothafiza[i][yukari]==1) {
                            break;
                        }
                        if(izgaras[i][yukari].dugumnumarasi!=-1){
                            graph[izgaras[i][yukari].dugumnumarasi][izgaras[i][j].dugumnumarasi]=j-yukari;
                            break;
                        }
                    }

                }

            }

        }

        System.out.println("Graf");
        System.out.print("  ");
        for (int i = 0; i < graph.length; i++) {
            if(i<10)
                System.out.print(i+"  ");
            else
                System.out.print(i+" ");
        }
        System.out.println();
        for (int i = 0; i < graph.length; i++) {
            if(i<10)
                System.out.print(i+" ");
            else
                System.out.print(i+"");
            for (int j = 0; j < graph[0].length; j++) {

                if(graph[i][j]!=-1 && graph[i][j]<10)
                    System.out.print(graph[i][j]+"  ");
                else
                    System.out.print(graph[i][j]+" ");
            }
            System.out.println();
        }
        return graph;
    }


}
public class Main {
    public static int[][] urldenLabirentYap(String txturl) throws Exception {
        Random random = new Random();

        int mazeboundsI=0;
        int mazeboundsJ=0;
        URL url = new URL(txturl);
        URLConnection conn = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        inputLine = reader.readLine();
        mazeboundsJ++;
        mazeboundsI=inputLine.length();
        System.out.println("MAZEBOUNDSI :"+mazeboundsI);
        while ((inputLine = reader.readLine()) != null) {
            // System.out.println(inputLine);
            mazeboundsJ++;
        }
        System.out.println("MAZEBOUNDSI: "+mazeboundsI+" MAZEBOUNDSJ: "+mazeboundsJ);
        reader.close();

        int[][] tmpmaze = new int[mazeboundsI][mazeboundsJ];

        URLConnection conn2 = url.openConnection();
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
        int s1=0;
        while ((inputLine = reader2.readLine()) != null) {
            System.out.println(inputLine);
            for (int s2 = 0; s2 < inputLine.length() ; s2++) {
                tmpmaze[s1][s2]=Integer.parseInt(String.valueOf(inputLine.charAt(s2)));
            }
            s1++;
        }
        reader2.close();

        for (int i = 0; i < tmpmaze.length; i++) {
            for (int j = 0; j < tmpmaze[0].length; j++) {
                System.out.print(tmpmaze[i][j]+" ");
            }
            System.out.println();

        }

        for (int i = 0; i < tmpmaze.length; i++) {
            for (int j = 0; j < tmpmaze[0].length; j++) {
                if(tmpmaze[i][j]==2)
                {
                    int rastgelesifirla=random.nextInt(5);
                    switch (rastgelesifirla){
                        case 0:
                            tmpmaze[i][j]=0;
                            tmpmaze[i][j+1]=1;
                            tmpmaze[i+1][j]=1;
                            tmpmaze[i+1][j+1]=1;
                            break;
                        case 1:
                            tmpmaze[i][j]=1;
                            tmpmaze[i][j+1]=0;
                            tmpmaze[i+1][j]=1;
                            tmpmaze[i+1][j+1]=1;
                            break;
                        case 2:
                            tmpmaze[i][j]=1;
                            tmpmaze[i][j+1]=1;
                            tmpmaze[i+1][j]=0;
                            tmpmaze[i+1][j+1]=1;
                            break;
                        case 3:
                            tmpmaze[i][j]=1;
                            tmpmaze[i][j+1]=1;
                            tmpmaze[i+1][j]=1;
                            tmpmaze[i+1][j+1]=0;
                            break;
                        case 4:
                            tmpmaze[i][j]=1;
                            tmpmaze[i][j+1]=1;
                            tmpmaze[i+1][j]=1;
                            tmpmaze[i+1][j+1]=1;
                            break;
                    }
                }


                if (tmpmaze[i][j]==3){

                    tmpmaze[i][j]=   1; tmpmaze[i+1][j]=   1; tmpmaze[i+2][j]=   1;
                    tmpmaze[i][j+1]= 1; tmpmaze[i+1][j+1]= 1; tmpmaze[i+2][j+1]= 1;
                    tmpmaze[i][j+2]= 1; tmpmaze[i+1][j+2]= 1; tmpmaze[i+2][j+2]= 1;

                    int rastgelesifirla=random.nextInt(4);
                    switch (rastgelesifirla){
                        case 0:
                            tmpmaze[i+ random.nextInt(3)][j+random.nextInt(3)]=0;
                            tmpmaze[i+1][j+1]=1;
                            break;
                        case 1:
                            tmpmaze[i+1][j+1]=0;
                            int rastgelesifirla2=random.nextInt(4);
                            switch (rastgelesifirla2){
                                case 0:
                                    tmpmaze[i+1][j]=0;
                                    break;
                                case 1:
                                    tmpmaze[i+1][j+2]=0;
                                    break;
                                case 2:
                                    tmpmaze[i][j+1]=0;
                                    break;
                                case 3:
                                    tmpmaze[i+2][j+1]=0;
                                    break;
                            }
                            break;
                        case 2:
                            tmpmaze[i+1][j]=0;
                            tmpmaze[i+1][j+2]= 0;
                            break;
                        case 3:
                            tmpmaze[i][j+1]=0;
                            tmpmaze[i+2][j+1]= 0;
                            break;
                    }

                }
            }
        }

        System.out.println("DEGİSKEN");

        for (int i = 0; i < tmpmaze.length; i++) {
            for (int j = 0; j < tmpmaze[0].length; j++) {
                System.out.print(tmpmaze[i][j]+" ");
            }
            System.out.println();
        }

        int acikliksayisi=0;
        int randmazeI;
        int randmazeJ;
        do {
            acikliksayisi=0;
            randmazeI= (random.nextInt(tmpmaze.length / 2) + tmpmaze.length / 2)-1;
            randmazeJ= (random.nextInt(tmpmaze[0].length / 2) + tmpmaze[0].length / 2)-1;
            if(tmpmaze[randmazeI+1][randmazeJ]==0)
                acikliksayisi++;
            if(tmpmaze[randmazeI][randmazeJ+1]==0)
                acikliksayisi++;
            if(tmpmaze[randmazeI-1][randmazeJ]==0)
                acikliksayisi++;
            if(tmpmaze[randmazeI][randmazeJ-1]==0)
                acikliksayisi++;
        }while (acikliksayisi<2 || tmpmaze[randmazeI][randmazeJ]!=0);//degistirdim

        tmpmaze[randmazeI][randmazeJ]=9;

        int[][] maze=new int[tmpmaze.length+2][tmpmaze[0].length+2];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if(i==0 || i==maze.length-1 || j==0 || j==maze[0].length-1)
                    maze[i][j]=1;
                else
                    maze[i][j]=tmpmaze[i-1][j-1];
            }

        }

        return maze;
    }
    public static void dijkstraislemleri (ArrayList<Integer> enkisayol,Izgara[][] izgaralar,int[][] maze,Robot robot) throws IOException {
        int[][] dijkstramatrisi=new int[maze.length][maze[0].length];
        for (int i = 0; i < dijkstramatrisi.length ; i++) {
            for (int j = 0; j < dijkstramatrisi[0].length ; j++) {
                dijkstramatrisi[i][j]=0;
            }
        }
        int dijkstraningittigiadimsayisi=0;
        for (int i = 0; i < dijkstramatrisi.length; i++) {
            for (int j = 0; j < dijkstramatrisi[0].length; j++) {
                for (int s = 0; s < enkisayol.size(); s++) {
                    if (izgaralar[i][j].dugumnumarasi == enkisayol.get(s)) {
                        dijkstramatrisi[i][j]=1;
                        izgaralar[i][j].panel.setBackground(Color.RED);
                        if(s!=enkisayol.size()-1) {
                            izgaralar[i][j].sis.setText(String.valueOf(s+1));
                            izgaralar[i][j].sis.setVisible(true);
                        }
                        //izgaralar[robot.getRobotpath().get(index).i][robot.getRobotpath().get(index).j].sis.setText(String.valueOf(index+1));
                        //izgaralar[robot.getRobotpath().get(index).i][robot.getRobotpath().get(index).j].sis.setVisible(true);
                        dijkstraningittigiadimsayisi++;
                        break;
                    }
                }
            }
        }
        int robotungittigiadimsayisi=1;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if(maze[i][j]==3)
                    robotungittigiadimsayisi++;
            }
        }

        JFrame sonucframe = new JFrame("Sonuclar");
        JLabel dijkstrasonucu = new JLabel("Dijkstra adım sayısı : "+dijkstraningittigiadimsayisi);
        JLabel robotunadimsonucu = new JLabel("Robotun adım sayısı : "+robotungittigiadimsayisi);
        JLabel gecensure = new JLabel("Geçen süre : "+ robot.robotungecirdigisure*0.05+" sn");

        //File file = new File("C:\\Users\\asus\\Desktop\\PROLAB II proje 1\\projeolusandosya\\dosya.txt");
        File file = new File("dosya.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file, false);
        BufferedWriter bWriter = new BufferedWriter(fileWriter);
        bWriter.write("Sonuclar \n");
        bWriter.write("Dijkstra adım sayısı : "+dijkstraningittigiadimsayisi);
        bWriter.write("\nRobotun adım sayısı : "+robotungittigiadimsayisi);
        bWriter.write("\nGeçen süre : "+ robot.robotungecirdigisure*0.05+ " sn\n\n");
        bWriter.write("Dijkstra - En kısa yol\n");
        for (int i = 0; i < dijkstramatrisi.length; i++) {
            for (int j = 0; j < dijkstramatrisi[0].length; j++) {
                bWriter.write(dijkstramatrisi[i][j]+" ");
            }
            bWriter.write("\n");
        }
        bWriter.close();



        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add( dijkstrasonucu );
        panel.add(robotunadimsonucu);
        panel.add(gecensure);

        sonucframe.getContentPane().add(panel);
        sonucframe.pack();

        sonucframe.setSize(300,200);
        sonucframe.setLayout(null);
        sonucframe.setVisible(true);

    }
    public static void labirentmain(int maze[][], boolean urldenmi,int labirentboyutu){
        Random random =new Random();
        JFrame frame = new JFrame("Labirent");
        //frame.setSize(1000, 1000);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);

        Izgara[][] izgaralar = new Izgara[maze.length][maze[0].length]; // nesne matrisi
        for (int i=0;i<maze.length;i++){
            for(int j=0;j<maze[0].length;j++){
                if(maze[i][j]==1)
                {
                    izgaralar[i][j]=new Engel(labirentboyutu*j,labirentboyutu*i,labirentboyutu,labirentboyutu,frame);
                }
                if(maze[i][j]==0)
                {
                    izgaralar[i][j]=new Yol(labirentboyutu*j,labirentboyutu*i,labirentboyutu,labirentboyutu,frame);
                }
                if(maze[i][j]==9)
                {
                    izgaralar[i][j]=new Yol(labirentboyutu*j,labirentboyutu*i,labirentboyutu,labirentboyutu,frame);
                    JLabel bitisisareti = new JLabel("Fnsh");
                    bitisisareti.setVerticalAlignment(JLabel.CENTER);
                    bitisisareti.setHorizontalAlignment(JLabel.CENTER);
                    izgaralar[i][j].panel.add(bitisisareti);
                    izgaralar[i][j].panel.setBackground(Color.ORANGE);
                }
            }
        }// labirenti gorsele aktardik

        frame.setSize(labirentboyutu*maze[0].length+250,labirentboyutu*maze.length+250);


        JButton kesifbutton = new JButton("Keşif yap");

        kesifbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Robot robot;
                int baslangicI;
                int baslangicJ;
                if(urldenmi==true) {

                    int acikliksayisi;
                    do {
                        acikliksayisi = 0;
                        baslangicI = (random.nextInt(maze.length / 2) + 1);
                        baslangicJ = (random.nextInt(maze[0].length / 2) + 1);
                        if (maze[baslangicI + 1][baslangicJ] == 0)
                            acikliksayisi++;
                        if (maze[baslangicI][baslangicJ + 1] == 0)
                            acikliksayisi++;
                        if (maze[baslangicI - 1][baslangicJ] == 0)
                            acikliksayisi++;
                        if (maze[baslangicI][baslangicJ - 1] == 0)
                            acikliksayisi++;
                    } while (acikliksayisi < 2 || maze[baslangicI][baslangicJ]!=0); // baslangici belirler
                    //izgaralar[baslangicI][baslangicJ].panel.setBackground(Color.ORANGE);
                    //if(acikliksayisi>1 && maze[baslangicI][baslangicJ]==0)
                    robot = new Robot(baslangicI, baslangicJ, maze, izgaralar);
                }
                else{
                    baslangicI=1;
                    baslangicJ=1;
                    robot = new Robot(baslangicI, baslangicJ, maze, izgaralar);
                }
                int graf[][]= robot.grafOlustur(robot.getRobothafiza(),izgaralar);
                int baslangicdugumnumarasi=izgaralar[baslangicI][baslangicJ].dugumnumarasi;


                frame.remove(kesifbutton); // iki kez tiklanmasin diye

                JButton dijkstrabutton = new JButton("Dijkstra yap");

                dijkstrabutton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int bitisdugumnumarasi=-1;
                        for (int i = 0; i < maze.length; i++) {
                            for (int j = 0; j < maze[0].length; j++) {
                                if(maze[i][j]==9){ // bitis dugumunu bulmak icin
                                    // ArrayList<Integer> enkisayol = new ArrayList<>();
                                    bitisdugumnumarasi=izgaralar[i][j].dugumnumarasi;
                                    System.out.println("Baslangic dugum :"+baslangicdugumnumarasi+ "Bitis dugum: " + bitisdugumnumarasi);
                                    try {
                                        dijkstraislemleri(Dijkstraclass.dijkstra(graf,baslangicdugumnumarasi,bitisdugumnumarasi),izgaralar,maze,robot);
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            }
                        }
                    }
                });
                dijkstrabutton.setBounds(labirentboyutu*maze[0].length+15,80,150,50);
                frame.add(dijkstrabutton);

                JButton animationbutton = new JButton("yolu göster");


                animationbutton.addActionListener(new ActionListener() {
                    int index =0;
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        izgaralar[robot.getRobotpath().get(index).i][robot.getRobotpath().get(index).j].panel.setBackground(Color.YELLOW);
                        animationbutton.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyPressed(KeyEvent e) {
                                super.keyPressed(e);
                                int keycode = e.getKeyCode(); // tiklanan tusu aliyoruz
                                if(keycode == KeyEvent.VK_RIGHT){ //sağ ise
                                    if(index<robot.getRobotpath().size()-1) {
                                        index++;
                                        izgaralar[robot.getRobotpath().get(index).i][robot.getRobotpath().get(index).j].panel.setBackground(Color.YELLOW);
                                        izgaralar[robot.getRobotpath().get(index).i][robot.getRobotpath().get(index).j].sis.setText(String.valueOf(index+1));
                                        izgaralar[robot.getRobotpath().get(index).i][robot.getRobotpath().get(index).j].sis.setVisible(true);

                                    }
                                }
                                if(keycode == KeyEvent.VK_LEFT){ // sol ise
                                    if(index>0) {
                                        izgaralar[robot.getRobotpath().get(index).i][robot.getRobotpath().get(index).j].sis.setVisible(false);
                                        index--;
                                    }
                                }
                            }
                        });


                    }
                });

                animationbutton.setBounds(labirentboyutu*maze[0].length+15,145,150,50);
                frame.add(animationbutton);

            }

        });


        kesifbutton.setBounds(labirentboyutu*maze[0].length+15,15,150,50);
        frame.add(kesifbutton);
        frame.setLayout(null);
        frame.setVisible(true);

    }


    public static void main(String[] args){
        //ArrayList<Integer> JarrayList = new ArrayList<Integer>();

        JFrame secimframe = new JFrame("Seçim ekranı");
        secimframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        secimframe.setLayout(new GridLayout(3,1,10,10));
        JButton url1button = new JButton("URL1");
        url1button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int maze[][]=urldenLabirentYap("http://bilgisayar.kocaeli.edu.tr/prolab2/url1.txt");//methodun yaptigi labirenti dondurur
                    labirentmain(maze,true,30);//Labirent islemlerini göndeririz
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        JButton url2button = new JButton("URL2");
        url2button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int maze[][]=urldenLabirentYap("http://bilgisayar.kocaeli.edu.tr/prolab2/url2.txt");
                    labirentmain(maze,true,30);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        JButton randommazebutton = new JButton("RANDOM");
        randommazebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int labirentboyutu=25;//eger labirent boyutu belirlenemezse default olarak 25 girilir
                do {
                    String labirentboyutstr = JOptionPane.showInputDialog(secimframe, "Labirent boyutu giriniz (5-100)"); //labirent boyutu alinir
                    labirentboyutu = Integer.parseInt(labirentboyutstr); // labirent boyutu integera cevrilir
                }while (!(labirentboyutu>5 && labirentboyutu<100)); //sinirlar dahilinde ise donguden cikariz
                int labirentboyutupxl=750/labirentboyutu; // labirentteki her panelin boyutu belirlenir
                Mazemaker mazemaker = new Mazemaker(labirentboyutu, labirentboyutu); // boyutlar verilir
                mazemaker.doldur(1,1); // baslangic koordinatlari verilir
                int[][]maze =mazemaker.getmaze(); //labirent alinir
                labirentmain(maze,false,labirentboyutupxl); // cozume gonderilir
            }
        });

        secimframe.add(url1button);
        secimframe.add(url2button);
        secimframe.add(randommazebutton);
        secimframe.setSize(300,400);
        secimframe.setVisible(true);


        //Dijkstraclass dijkstraclass = new Dijkstraclass();

        // Dijkstra algoritmasını uygula
        //enkisayol=dijkstraclass.dijkstra(graph, baslangicindis,bitisindis);



    }
}//GEZGİN ROBOT TOPLAMA DENEMESİ 27 mart 01:38
//SON HALI
//tüm tuş fonksiyonları eklendi
//disaridan rand labirent için değer alma özelliği girildi
//labirenti her değere uygun olacak şekilde oluşturuldu