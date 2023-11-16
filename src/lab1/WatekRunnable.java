package lab1;

public class WatekRunnable implements Runnable {
    Obraz obraz;
    int id;
    public WatekRunnable(Obraz obraz,int id){
        this.id = id;
        this.obraz = obraz;
    }
    @Override
    public void run() {

            obraz.calculate_histogram_with_id(id);

    }
}
