import java.util.Random;

public class Probabilit� extends Speciale {

	Probabilit�(int id) {
		super(id);
	}

	private static final String FRASI_PROBABILITA[]= {
			"Oggi � il tuo compleanno",
			"Vinci la lotteria",
			"Hai trovato dei soldi per strada",
			"Hai ricevuto la paga a lavoro",
			
	};
	
	public void fraseP() {
		Random r = new Random();
		System.out.println(FRASI_PROBABILITA[r.nextInt(FRASI_PROBABILITA.length)]);
	}
	
}
