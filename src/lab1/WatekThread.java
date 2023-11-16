package zad1;

public class WatekThread extends Thread{
    Obraz obraz;
    int id;

    WatekThread(Obraz obraz,int id){
        this.obraz = obraz;
        this.id =id;
    }

    @Override
    public void run() {
        obraz.calculate_histogram_with_id(id);
    }
}
