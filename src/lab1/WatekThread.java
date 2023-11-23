package lab1;

public class WatekThread extends Thread{
    Obraz obraz;
    int id;
    int num_threads;
    WatekThread(Obraz obraz,int id,int num_threads){
        this.obraz = obraz;
        this.id =id;
        this.num_threads = num_threads;
    }

    @Override
    public void run() {
            obraz.calculate_histogram_with_id(id,num_threads,obraz.sym_num);
    }
}
