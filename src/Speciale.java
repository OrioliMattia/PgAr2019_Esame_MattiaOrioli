import java.util.Random;

public class Speciale extends Casella {

	private int valore;
	
	Speciale(int id) {
		super(id);

		valore = generaValore();
	}
	
	
	int generaValore() {
		Random r = new Random();
		return r.nextInt(1000000)+1;
	}
	
	int getValore() {
		return valore;
	}
}
