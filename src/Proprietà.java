
public class Proprietà extends Casella {
	

	private Giocatore proprietario;
	private int costo;
	private int rendita;
	
	Proprietà(int id, int costo) {
		super(id);
		this.costo=costo;
		this.rendita = costo/2; 
	}
	
	public Giocatore getProprietario() {
		return proprietario;
	}

	public void setProprietario(Giocatore proprietario) {
		this.proprietario = proprietario;
	}

	public int getCosto() {
		return costo;
	}

	public int getRendita() {
		return rendita;
	}

	
	
	
	
}
