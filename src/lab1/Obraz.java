package lab1;

import  java.util.Random;


class Obraz {
    
    private int size_n;
    private int size_m;
    private char[][] tab;
    private char[] tab_symb;
    private int[] histogram;
	private int sym_num;

	public int getSize_n() {// rows
		return size_n;
	}

	public int getSize_m() { //cols
		return size_m;
	}

	public Obraz(int n, int m, int sym_num) {
	
	this.size_n = n;
	this.size_m = m;
	tab = new char[n][m];
	tab_symb = new char[sym_num];
	this.sym_num = sym_num;
	
	final Random random = new Random();
	
	// for general case where symbols could be not just integers
	for(int k=0;k<sym_num;k++) {
	    tab_symb[k] = (char)(k+33); // substitute symbols
	}

	for(int i=0;i<n;i++) {
	    for(int j=0;j<m;j++) {	
		tab[i][j] = tab_symb[random.nextInt(sym_num)];  // ascii 33-127
		//tab[i][j] = (char)(random.nextInt(94)+33);  // ascii 33-127
		System.out.print(tab[i][j]+" ");
	    }
	    System.out.print("\n");
	}
	System.out.print("\n\n"); 
	
	histogram = new int[sym_num];
   	clear_histogram();
    }
    
    public void clear_histogram(){

	for(int i=0;i<sym_num;i++) histogram[i]=0;

    }

	public int[] getHistogram() {
		return histogram;
	}

	public void calculate_histogram() {

		for (int i = 0; i < size_n; i++) {
			for (int j = 0; j < size_m; j++) {

				// optymalna wersja obliczania histogramu, wykorzystująca fakt, że symbole w tablicy
				// można przekształcić w indeksy tablicy histogramu
				// histogram[(int)tab[i][j]-33]++;

				// wersja bardziej ogólna dla tablicy symboli nie utożsamianych z indeksami
				// tylko dla tej wersji sensowne jest zrównoleglenie w dziedzinie zbioru znaków ASCII

				for (int k = 0; k < sym_num; k++) {
					if (tab[i][j] == tab_symb[k]) histogram[k]++;
					//if(tab[i][j] == (char)(k+33)) histogram[k]++;
				}
			}
		}
	}

		public void calculate_histogram_with_id(int id,int watkow){
			int blok = (int)Math.ceil(tab_symb.length/watkow);
			int startznak = id*blok;
			int endznak = (id+1)*blok;
			if(endznak>tab_symb.length){endznak=tab_symb.length;}


			for(int o = startznak;o < endznak; o++) {
				for (int i = 0; i < size_n; i++) {
					for (int j = 0; j < size_m; j++) {
						if (tab[i][j] == tab_symb[o]) {
							histogram[o]++;
						}
					}
				}
				System.out.println("watek id: " + id + " znak: " + tab_symb[o] + ": "+getRownasie(histogram[o]));
			}


    }

// uniwersalny wzorzec dla różnych wariantów zrównoleglenia - można go modyfikować dla
// różnych wersji dekompozycji albo stosować tak jak jest zapisane poniżej zmieniając tylko
// parametry wywołania w wątkach
//
//calculate_histogram_wzorzec(start_wiersz, end_wiersz, skok_wiersz,
//                            start_kol, end_kol, skok_kol,
//                            start_znak, end_znak, skok_znak){
//
//  for(int i=start_wiersz;i<end_wiersz;i+=skok_wiersz) {
//     for(int j=start_kol;j<end_kol;j+=skok_kol) {
//        for(int k=start_znak;k<end_znak;k+=skok_znak) {
//           if(tab[i][j] == tab_symb[k]) histogram[k]++;
//
		static String getRownasie(int liczba){
		String temp = "";
		for (int i = 0; i <liczba; i++) {
			temp += "=";
		}
		return temp;
	}

    public void print_histogram(){
	
	for(int i=0;i<sym_num;i++) {
	    System.out.print(tab_symb[i]+" "+histogram[i]+"\n");	    
	    //System.out.print((char)(i+33)+" "+histogram[i]+"\n");	    
	}
    }

	public synchronized void histogramAddToGlobal(int[] local){
		for(int i =0;i<histogram.length;i++){
			histogram[i] +=local[i];
		}
	}

	public void calculate_histogram_blokowo(int id, int threadsNum) {

		int[] localhistogram = new int[histogram.length];
		for (int i = 0; i < localhistogram.length; i++) {
			localhistogram[i] = 0;
		}


		int block = sym_num/threadsNum;
		int startPoint = id*block;
		int endPoint = (id+1)*block;
		if(endPoint > sym_num) {endPoint=sym_num;}

		for (int i = 0; i < size_n; i++) {
			for (int j = 0; j < size_m; j++) {
				for(int k =startPoint; k<endPoint;k++)
				if (tab[i][j] == tab_symb[k]) localhistogram[k]++;
			}
		}
		// synchronized
		histogramAddToGlobal(localhistogram);

		for(int i = startPoint; i<endPoint;i++) {
			System.out.println("watek id: " + id + " znak: " + tab_symb[i] + ": " + getRownasie(localhistogram[i]));
		}


	}


	public void calculate_histogram_for_row(int i) {

			for (int j = 0; j < size_m; j++) {
				for (int k = 0; k < sym_num; k++) {
				//	System.out.println("J:" + j + "i"+i);
					if (tab[i][j] == tab_symb[k]) histogram[k]++;
				}
			}

	}

	public void calculate_histogram_for_col(int i) {
		for (int j = 0; j < size_n; j++) {
			for (int k = 0; k < sym_num; k++) {
				if (tab[j][i] == tab_symb[k]) histogram[k]++;
			}
		}
	}

//	public void calculate_histogram_2d(int id, int threadsNum) {
//		int block_rows = size_n/threadsNum;
//		int startRow = id*block_rows;
//		int endRow = (id+1)*block_rows;
//
//		for (int j = 0; j < size_n; j++) {
//			for (int k = 0; k < sym_num; k++) {
//				if (tab[j][i] == tab_symb[k]) histogram[k]++;
//			}
//		}
//
//	}
}
