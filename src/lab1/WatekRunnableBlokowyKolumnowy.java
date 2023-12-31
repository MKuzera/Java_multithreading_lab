package lab1;

public class WatekRunnableBlokowyKolumnowy implements Runnable{
    Obraz obraz;
    int id;
    int threads_num;
    public WatekRunnableBlokowyKolumnowy(Obraz obraz,int id,int threads_num){
        this.id = id;
        this.obraz = obraz;
        this.threads_num=threads_num;
    }
    @Override
    public void run() {
        synchronized (obraz) {

            int cols = obraz.getSize_m();
            int block = (int) Math.ceil((double) cols / threads_num);
           // System.out.println("cols: " + cols + " threads: " + threads_num + " block: " + block);
            int start = id * block;
            int end = (id + 1) * block;
            if (end > cols) {
                end = cols;
            }

            for (int i = start; i < end; i++) {


                obraz.calculate_histogram_for_col(i);



            }
        }

    }


    static String getRownasie(int liczba){
        String temp = "";
        for (int i = 0; i <liczba; i++) {
            temp += "=";
        }
        return temp;
    }
}

