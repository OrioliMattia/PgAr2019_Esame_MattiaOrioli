import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Unipoly {

	private static final int NUMERO_CASELLE= 20;

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {


		//creazione tavola di gioco

		ArrayList<Casella> mappa = new ArrayList<Casella>();
		ArrayList<Integer> id_stazioni = new ArrayList<Integer>();
		Random r = new Random();
		int numeroProbabilit�=0;
		int numeroImprevisti=0;

		for (int i=0; i<NUMERO_CASELLE; i++) {

			if (i==0) mappa.add(new Inizio());
			else {
				switch(r.nextInt(4)) {
				case 0: //stazione
					mappa.add(new Stazione(i));
					id_stazioni.add(i);
					break;
				case 1: //probabilit�
					mappa.add(new Probabilit�(i));
					numeroProbabilit�++;
					break;
				case 2: //imprevisto
					mappa.add(new Imprevisto(i));
					numeroImprevisti++;
					break;
				case 3: //propriet�
					boolean p = r.nextBoolean();
					if(p) mappa.add(new Casa(i, r.nextInt(250000))); //crea la casa
					else mappa.add(new Albergo(i, r.nextInt(250000)+250000)); // crea l'albergo con un costo maggiore alla casa
					break;
				}
			}

		}

		
		//controllo per far si che ci siano almeno 2 stazioni, 1 probabilit� e 1 imprevisto
		boolean controllo=true;
		do {

			if (id_stazioni.size()<2) {
				controllo=false;
				int nuovaStazione= r.nextInt(NUMERO_CASELLE-1)+1;
				mappa.set(nuovaStazione, new Stazione(nuovaStazione));
				id_stazioni.add(nuovaStazione);
			}
			if(numeroProbabilit�==0) {
				controllo=false;
				int nuovaProbabilit�= r.nextInt(NUMERO_CASELLE-1)+1;
				mappa.set(nuovaProbabilit�, new Probabilit�(nuovaProbabilit�));
			}
			if(numeroImprevisti==0) {
				controllo=false;
				int nuovoImprevisto= r.nextInt(NUMERO_CASELLE-1)+1;
				mappa.set(nuovoImprevisto, new Imprevisto(nuovoImprevisto));
			}
		}while(controllo = false);

		
		//scelta giocatori
		int numGiocatori = 0;
		do {
			try {
				System.out.print("In quanti siete a giocare?(da 2 a 4)");
				numGiocatori = Integer.parseInt(sc.nextLine());
				if(numGiocatori<2 || numGiocatori>4)System.out.println("numero non valido");
				else break;
			}catch(Exception e) {System.out.println("numero non valido");}
		}while(true);

		Giocatore[] giocatori= new Giocatore[numGiocatori];
		for(int i=0; i<numGiocatori; i++) {
				System.out.println("Giocatore "+(i+1)+" come ti chiami? ");
				String nuovoNome=sc.nextLine();
			for(int j=0; j<i; j++) {
				if (giocatori[j].getNome().equals(nuovoNome)) {
					nuovoNome = nuovoNome+"2";
				}
			}
			giocatori[i] = new Giocatore(nuovoNome);
		}

		//classifica per registrare l'ordine in cui i giocatori vanno in bancarotta
		ArrayList<String> classificaBancarotta= new ArrayList<String>();


		//inizio gioco

		while(!tuttiAlVerde(giocatori) && !abbiamoUnVincitore(giocatori)) { //controlla non siano tutti in bancarotta e che nessuno abbia vinto

			for(Giocatore g : giocatori) {

				if(g.getBancarotta()) continue; //escludo i giocatori in bancarotta

				System.out.println("_____________________________________________________________");
				
				System.out.println("\n"+g.getNome()+" al momento hai "+g.getSoldi()+" I���");
				System.out.println("premi invio per tirare i dadi");
				sc.nextLine();
				int d = tiradadi();
				System.out.println("� uscito "+d);

				if(g.getCasella()+d<NUMERO_CASELLE) g.setCasella(g.getCasella()+d);
				else g.setCasella(d-(NUMERO_CASELLE-g.getCasella()));

				Casella cg = mappa.get(g.getCasella());

				System.out.println(g.getNome()+" sei finito su una casella "+ cg.getClass().getName());

				if (cg instanceof Probabilit�) {
					((Probabilit�) cg).fraseP();
					g.guadagna(((Probabilit�) cg).getValore());
					System.out.println(g.getNome()+" guadagni "+ ((Probabilit�) cg).getValore() +" I���");

				}
				else if(cg instanceof Imprevisto) {
					((Imprevisto) cg).fraseI();
					g.perdi(((Imprevisto) cg).getValore());
					System.out.println(g.getNome()+" perdi "+ ((Imprevisto) cg).getValore() +" I���");
				}
				else if (cg instanceof Stazione) {
					System.out.println(g.getNome()+" vuoi viaggiare verso un'altra stazione?(si/no) ");
					String rispostaTreno = sc.nextLine();
					if(rispostaTreno.equalsIgnoreCase("no"));
					else if(rispostaTreno.equalsIgnoreCase("si")) {
						System.out.println("Lista Stazioni");
						for(int i : id_stazioni) {
							if(cg.getId()!=i)System.out.println("-  "+i);
						}
						while(true) {
							System.out.println("Inserisci l'id della tua destinazione : ");
							int destinazione= sc.nextInt();
							if(id_stazioni.contains(destinazione)) {
								g.setCasella(destinazione);
								break;
							}
							else System.out.println("Stazione non trovata");
						}
					}

				}
				else if (cg instanceof Propriet�) {
					if(((Propriet�) cg).getProprietario()==null) {
						System.out.println("Propriet� in vendita");
						int costoProprieta=((Propriet�) cg).getCosto();
						int renditaProprieta=((Propriet�) cg).getRendita();
						do {
							System.out.println(g.getNome()+" vuoi comprare la propriet� per un costo di "+ costoProprieta+" I��� e una rendita di "+ renditaProprieta+ " I��� ?(si/no)" );
							String risposta = sc.nextLine();
							if(risposta.equalsIgnoreCase("no")) break;
							else if(risposta.equalsIgnoreCase("si")) {
								((Propriet�) cg).setProprietario(g);
								System.out.println("ottimo acquisto");
								g.perdi(((Propriet�) cg).getCosto());
								break;
							}
							else System.out.println("risposta errata");
						}while(true);
					}
					else if(((Propriet�) cg).getProprietario().getNome().equals(g.getNome())) {
						g.guadagna(((Propriet�) cg).getRendita());
						System.out.println("Questa � una tua proprieta e guadagni " +((Propriet�) cg).getRendita()+ " I���");
					}
					else if(!((Propriet�) cg).getProprietario().getNome().equals(g.getNome())) {
						for(int i=0; i<giocatori.length;i++) {
							if(giocatori[i].getNome().equals(((Propriet�) cg).getProprietario().getNome()) && g.getBancarotta()==true) {
								giocatori[i].guadagna(((Propriet�) cg).getRendita());
								System.out.println("Questa propriet� non � tua, paghi "+((Propriet�) cg).getRendita()+" I��� a "+ giocatori[i].getNome());
								g.perdi(((Propriet�) cg).getRendita());
							}
							else if(giocatori[i].getNome().equals(((Propriet�) cg).getProprietario().getNome()) && g.getBancarotta()==false) {
								System.out.println("Questa propriet� non � al momento disponibile");
							}
						}
					}
					
					
					
				}

				// controllo se il giocatore ha vinto...
				if (g.getSoldi() >= 1000000) {
					System.out.println("HAI VINTO!!! Congratulazioni "+g.getNome()+ " sei milionario");
					break;
				}
				//... o se ha perso
				if (g.getSoldi() <=0) {
					System.out.println("HAI Perso!!! Mi dispiace "+g.getNome()+ " sei in bancarotta");
					g.setBancarotta();
					classificaBancarotta.add(g.getNome());
				}

			}
		}
		
		//mostra la classifica dei giocatori
		System.out.println("CLASSIFICA:");
		if(abbiamoUnVincitore(giocatori)) {
			classifica(giocatori);
		}
		if(tuttiAlVerde(giocatori)) {
			int pos=1;
			for(int i=classificaBancarotta.size()-1; i>=0 ;i--) {
				System.out.println(pos+"�     "+classificaBancarotta.get(i));
				pos++;
			}
		}
		
	}

	//metodo per tirare i 2 dadi da 6 facce l'uno
	static int tiradadi() {
		Random r = new Random();
		int n= r.nextInt(11)+2;
		return n;
	}

	//metodo per capire se tutti i giocatori sono in bancarotta
	static boolean tuttiAlVerde(Giocatore[] g) {  
		for(Giocatore gio : g) {
			if((gio.getBancarotta())==false)return false;
		}
		return true;
	}
	
	//metodo per capire se qualcuno ha vinto
	static boolean abbiamoUnVincitore(Giocatore[] g) {
		for(Giocatore gio : g) {
			if(gio.getSoldi()>= 1000000) return true;
		}
		return false;
	}
	
	//in caso di vittoria di un giocatore mostra la classifica in ordine decrescente
	static void classifica(Giocatore[] g) {
		for(int i=0; i<g.length;i++) {
			Giocatore best= new Giocatore("");
			int bestIndex=0;
			for(int j=0; j<g.length; j++) {
				if(g[j].getSoldi()>best.getSoldi()) {
					best=g[j];
					bestIndex = j;
				}
			}
			System.out.println((i+1)+"�    "+best.getNome()+"      "+ best.getSoldi());
			g[bestIndex].perdi(g[bestIndex].getSoldi());;
		}
	}



}
