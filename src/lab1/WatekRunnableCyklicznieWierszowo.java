package lab1;

public class WatekRunnableCyklicznieWierszowo implements Runnable{
    Obraz obraz;
    int id;
    int threads_num;
    public WatekRunnableCyklicznieWierszowo(Obraz obraz,int id,int threads_num){
        this.id = id;
        this.obraz = obraz;
        this.threads_num=threads_num;
    }
    @Override
    public void run() {
        //synchronized (obraz) { kod }

            int rows = obraz.getSize_n();
            int start = id;

            // cyklicznie
            for(int i = start; i< rows; i+=threads_num){
                obraz.calculate_histogram_for_row(i);
            }


    }
    // synchronized sluzy do synchronizacji dostepu do obiektu obraz w metodzie run
    // w ten sposob tylko 1 watek moze wykonywac metode calc na obiekcie obraz jednoczesnie
}
