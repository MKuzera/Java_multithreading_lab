package lab1;

public class WatekRunnable implements Runnable {
    Obraz obraz;
    int id;
    int num_threads;
    public WatekRunnable(Obraz obraz,int id,int num_threads){
        this.id = id;
        this.obraz = obraz;
        this.num_threads = num_threads;
    }
    @Override
    public void run() {
            obraz.calculate_histogram_with_id(id,num_threads,obraz.sym_num);

    }
}
