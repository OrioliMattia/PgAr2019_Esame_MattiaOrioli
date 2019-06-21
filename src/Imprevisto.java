import java.util.Random;

public class Imprevisto extends Speciale {
	
	Imprevisto(int id) {
		super(id);
	}

	private static final String FRASI_IMPREVISTI[]= {
			"Oggi è il compleanno di un tuo amico e devi fargli un regalo",
			"C'è da pagare la tassa universitaria",
			"Devi pagare il bollo della macchina",
			"E' ora di pagare l'abbonamento a Netflix",
			"Esce un nuovo videogioco",
			"Multa per eccesso di velocità"
			
	};
	
	void fraseI() {
		Random r = new Random();
		System.out.println(FRASI_IMPREVISTI[r.nextInt(FRASI_IMPREVISTI.length)]);
	}

}
