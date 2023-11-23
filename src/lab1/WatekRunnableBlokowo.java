package lab1;

public class WatekRunnableBlokowo implements Runnable {
    Obraz obraz;
    int id;
    int threads_num;
    public WatekRunnableBlokowo(Obraz obraz,int threads_num,int id){
        this.id = id;
        this.obraz = obraz;
        this.threads_num = threads_num;
    }
    @Override
    public void run() {

            obraz.calculate_histogram_blokowo(threads_num,id);

    }
}
