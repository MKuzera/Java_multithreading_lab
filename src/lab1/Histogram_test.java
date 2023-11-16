package lab1;

import java.util.Scanner;


class Histogram_test {
	private static Scanner scanner;

	public static void main(String[] args) {

		scanner = new Scanner(System.in);

		System.out.println("Set image size: n (#rows), m(#kolumns)");
		int n = scanner.nextInt();
		int m = scanner.nextInt();

		System.out.println("Set number of threads (ilosc watkow = ilosc znakow)");
		int num_threads = scanner.nextInt();
		int sym_num = num_threads;

		Obraz obraz_1 = new Obraz(n, m, sym_num);

		obliczIWyswietlhistogram(obraz_1);

		WARIANT1_WatkiExtends1do1(num_threads, obraz_1);

		//	WatkiRunnable1do1(num_threads, obraz_1);

		WARIANT2_WatkiRunnableBlokowo(num_threads, obraz_1);

		WARIANT3_WatkiRunnableCyklicznyWierszowy(obraz_1, num_threads);

		WARIANT4_WatkiRunnableBlokowyKolumnowy(obraz_1, num_threads);

	}
	private static void WARIANT5_WatkiRunnable2D(Obraz obraz_1, int num_threads) {
		// wariant 5 (Runnable) 2D

		System.out.println("*5* Calc histogram with 2D (runnable): " + num_threads);
		obraz_1.clear_histogram();
		Thread[] threads = new Thread[num_threads];

		Watek2dRunnable[] NewThrRunnable = new Watek2dRunnable[num_threads];


		for (int i = 0; i < num_threads; i++) {
			NewThrRunnable[i] = new Watek2dRunnable(obraz_1, i,num_threads);
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
		// dokonczyc?
	}

	private static void WARIANT4_WatkiRunnableBlokowyKolumnowy(Obraz obraz_1, int num_threads) {
		// wariant 4 (Runnable) podział blokowy kolu,nowy po tablicy,
		// każdy z wątków zlicza wszystkie znaki w przydzielonym fragmencie tablicy (kolumnie) w sposob blokowy

		System.out.println("*4* Calc histogram with threads (blokowy kolumnowy) (runnable): " + num_threads);
		obraz_1.clear_histogram();
		Thread[] threads = new Thread[num_threads];

		WatekRunnableBlokowyKolumnowy[] NewThrRunnable = new WatekRunnableBlokowyKolumnowy[num_threads];


		for (int i = 0; i < num_threads; i++) {
			NewThrRunnable[i] = new WatekRunnableBlokowyKolumnowy(obraz_1, i,num_threads);
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

	private static void WARIANT3_WatkiRunnableCyklicznyWierszowy(Obraz obraz_1,int num_threads) {
		// wariant 3 (Runnable) podział cykliczny wierszowy po tablicy,
		// każdy z wątków zlicza wszystkie znaki w przydzielonym fragmencie tablicy


		System.out.println("*3* Calc histogram with threads (cyklicznie wierszowy) (runnable): " + num_threads);
		obraz_1.clear_histogram();
		Thread[] threads = new Thread[num_threads];

		WatekRunnableCyklicznieWierszowo[] NewThrRunnable = new WatekRunnableCyklicznieWierszowo[num_threads];


		for (int i = 0; i < num_threads; i++) {
			NewThrRunnable[i] = new WatekRunnableCyklicznieWierszowo(obraz_1, i,num_threads);
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




	private static void obliczIWyswietlhistogram(Obraz obraz_1) {
		System.out.println("Calc histogram with no threads:");
		obraz_1.clear_histogram();
		obraz_1.calculate_histogram();
		obraz_1.print_histogram();
	}

	private static void WARIANT1_WatkiExtends1do1(int num_threads, Obraz obraz_1) {
		// wariant 1 (Thread) jeden wątek zlicza jeden znak (dla mniejszej liczby wątków odpowiednio zmniejszamy liczbę znaków)

		System.out.println("*1* Calc histogram with threads (1 watek 1 znak) (extends): " + num_threads);
		obraz_1.clear_histogram();
		WatekThread[] NewThr = new WatekThread[num_threads];

		for (int i = 0; i < num_threads; i++) {
			NewThr[i] = new WatekThread(obraz_1, i);
			NewThr[i].start();
		}
		// oczekiwanie na zakonczenie procesow .join()
		for (int i = 0; i < num_threads; i++) {
			try {
				NewThr[i].join();
			} catch (InterruptedException e) {
				// interruptedException -> gdy watek jest przerwany podczas
				// wykonywania operacji blokowania? (podczas czekania na dostep do sekcji krytycznej)
			}
		}
		obraz_1.print_histogram();
	}

	private static void WatkiRunnable1do1(int num_threads, Obraz obraz_1) {
		System.out.println("Calc histogram with threads (1 watek 1 znak) (runnable): " + num_threads);
		obraz_1.clear_histogram();
		Thread[] threads = new Thread[num_threads];
		// tworze tablice watkow bo majac interfejs runnable nie moge wywolac
		// metody join bezposrednio na obiekcie (w przypadku extends tak moglem)
		// ale w przypadku runable musze przypisac te obiekty do tablicy Threads
		// i dopiero moge na nich wywolac runnable
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

	private static void WARIANT2_WatkiRunnableBlokowo(int num_threads, Obraz obraz_1) {
		//  wariant 2 (Runnable) podział 1D blokowy po znakach

		//System.out.println("ilosc znakow dla blokowo runnable");
		//int ilosc_znakow = scanner.nextInt();

		System.out.println("*2* Calc histogram with threads (blokowo) (runnable): " + num_threads);
		obraz_1.clear_histogram();
		Thread[] threadsBlokowo = new Thread[num_threads];
		// tworze tablice watkow bo majac interfejs runnable nie moge wywolac
		// metody join bezposrednio na obiekcie (w przypadku extends tak moglem)
		// ale w przypadku runable musze przypisac te obiekty do tablicy Threads
		// i dopiero moge na nich wywolac runnable
		WatekRunnableBlokowo[] NewThrRunnableBlokowo = new WatekRunnableBlokowo[num_threads];


		for (int i = 0; i < num_threads; i++) {
			NewThrRunnableBlokowo[i] = new WatekRunnableBlokowo(obraz_1, num_threads, i);
			threadsBlokowo[i] = new Thread(NewThrRunnableBlokowo[i]);
			threadsBlokowo[i].start();
		}

		for (int i = 0; i < num_threads; i++) {
			try {
				threadsBlokowo[i].join();
			} catch (InterruptedException e) {
			}
		}
		obraz_1.print_histogram();
	}


}



// roznice


// Runnable: klasy moga dziedziczyc oraz implementowac wiele interfejsow
// Thread:   klasy nie moga dziedziczyc i tez moga implementowac wiele interfejsow

// run a start

// run -> zawiera kod do wykonania w wątku. Wywolanie go oznacza wykonanie w biezacym watku
// bez tworzenia nowego watku (nie bedzie dzialal rownolegle)
// start -> rozpoczyna nowy watek i wykonuje kod z run w nowym watku