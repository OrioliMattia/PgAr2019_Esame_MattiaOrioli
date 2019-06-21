
public class Giocatore {
	
	private String nome;
	private int soldi= 1000;
	private int id_casella=0; //la casella su cui si trova
	private boolean bancarotta = false;
	

	public Giocatore(String nome){
		this.nome=nome;
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public int getCasella() {
		return id_casella;
	}

	public void setCasella(int id_casella) {
		this.id_casella = id_casella;
	}
	
	public int getSoldi() {
		return this.soldi;
	}
	
	public void perdi(int valore) {
		soldi -= valore;
	}
	
	public void guadagna(int valore) {
		soldi += valore;
	}
	
	public void setBancarotta() {
		bancarotta= true;
	}
	
	public boolean getBancarotta() {
		return bancarotta;
	}
	
	
}
