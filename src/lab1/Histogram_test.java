package zad1;

import java.util.Scanner;


class Histogram_test {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Set image size: n (#rows), m(#kolumns)");
		int n = scanner.nextInt();
		int m = scanner.nextInt();

		System.out.println("Set number of threads (ilosc watkow = ilosc znakow)");
		int num_threads = scanner.nextInt();
		int sym_num = num_threads;

		Obraz obraz_1 = new Obraz(n, m, sym_num);

		System.out.println("Calc histogram with no threads:");
		obraz_1.clear_histogram();
		obraz_1.calculate_histogram();
		obraz_1.print_histogram();


		System.out.println("Calc histogram with threads (extends): " + num_threads);
		obraz_1.clear_histogram();
		WatekThread[] NewThr = new WatekThread[num_threads];

		for (int i = 0; i < num_threads; i++) {
			NewThr[i] = new WatekThread(obraz_1, i);
			NewThr[i].start();
		}

		for (int i = 0; i < num_threads; i++) {
			try {
				NewThr[i].join();
			} catch (InterruptedException e) {
			}
		}
		obraz_1.print_histogram();


		System.out.println("Calc histogram with threads (runnable): " + num_threads);
		obraz_1.clear_histogram();
		Thread[] threads = new Thread[num_threads];
		WatekRunnable[] NewThrRunnable = new WatekRunnable[num_threads];

		for (int i = 0; i < num_threads; i++) {
			NewThrRunnable[i] = new WatekRunnable(obraz_1, i);
			threads[i] = new Thread(NewThrRunnable[i]);
			threads[i].start();
		}

		for (int i = 0; i < num_threads; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
			}
		}
		obraz_1.print_histogram();
	}

}




