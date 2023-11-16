package lab1;

public class Watek2dRunnable implements Runnable{
    Obraz obraz;
    int id;
    int threads_num;
    public Watek2dRunnable(Obraz obraz,int threads_num,int id){
        this.id = id;
        this.obraz = obraz;
        this.threads_num = threads_num;
    }
    @Override
    public void run() {
       // obraz.calculate_histogram_2d(id,threads_num);
    }
}
