package garages;

public class Main {

	public static void main(String[] args) throws Exception {
		Voiture v1 = new Voiture("123 1BC 31");
		Garage g1 = new Garage("Castres");
		Garage g2 = new Garage("Albi");
		v1.entreAuGarage(g1);
		v1.sortDuGarage();
		v1.entreAuGarage(g2);
		v1.sortDuGarage();
		v1.entreAuGarage(g1);
		v1.imprimeStationnements(System.out);
	}

}
