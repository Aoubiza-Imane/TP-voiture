package garages;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GarageTest {

	private Garage g1, g2;
	private Voiture v1;

	@BeforeEach
	public void setUp() throws Exception {
		v1 = new Voiture("123 XX 456");
		g1 = new Garage("ISIS Castres");
		g2 = new Garage("Universite Champollion Albi");
	}

	@Test
	public void testInitialisationVoiture() {
		// Au début, la voiture n'est pas dans un garage
		assertFalse(v1.estDansUnGarage());
		assertTrue(v1.garagesVisites().isEmpty());
	}

	@Test
	public void testEntreeGarage() throws Exception {
		// On fait entrer la voiture au garage g1
		v1.entreAuGarage(g1);
		// Elle doit être dans un garage
		assertTrue(v1.estDansUnGarage());
		// g1 fait partie des garages visités par la voiture
		assertTrue(v1.garagesVisites().contains(g1));
	}

	@Test
	public void testSortieGarage() throws Exception {
		v1.entreAuGarage(g1);
		v1.sortDuGarage();
		// Elle n'est plus dans un garage
		assertFalse(v1.estDansUnGarage());
		// g1 fait partie des garages visités par la voiture
		assertTrue(v1.garagesVisites().contains(g1));
	}

	@Test
	public void testDoubleSortie() throws Exception {
		v1.entreAuGarage(g1);
		v1.sortDuGarage(); // Ici il ne doit pas y avoir d'exception
		try {
			v1.sortDuGarage(); // Que doit-il se passer ?
			// Si on arrive ici, il n'y a pas eu d'exception, échec
			fail();
		} catch (Exception e) {
			// Si on arrive ici, il y a eu une exception, c'est ce qui est attendu
		}
	}

	@Test
	public void testDoubleEntree() throws Exception {
		v1.entreAuGarage(g1);
		try {
			v1.entreAuGarage(g2); // Que doit-il se passer ?
			// Si on arrive ici, il n'y a pas eu d'exception, échec
			fail();
		} catch (Exception e) {
			// Si on arrive ici, il y a eu une exception, c'est ce qui est attendu
		}
	}

	/**
	 * Exemple de test qui vérifie un format d'impression correct.<br>`
	 * La méthode "imprimeStationnements" est conçue pour être testable :<br>
	 * Elle prend un paramètre "PrintStream" qui indique où on doit imprimer
	 * (Injection de dépendance).<br>
	 * Dans le test, on imprime dans une chaîne de caractères au lieu d'imprimer
	 * directement dans la console (System.out)<br>
	 * On peut ensuite vérifier que le contenu de la chaîne générée est conforme au
	 * résultat attendu.
	 * @throws Exception 
	 */
	@Test
	public void testCorrectPrintFormat() throws Exception {
		v1.entreAuGarage(g1);
		v1.sortDuGarage();
		v1.entreAuGarage(g2);
		v1.sortDuGarage();
		v1.entreAuGarage(g1);

		// La méthode imprimeGarages va écrire dans une chaine de caractères
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		// On imprime dans os
		v1.imprimeStationnements(ps);
		
		// On récupère le résultat de l'impression
		String output = os.toString("UTF8");

		assertEquals(1, countSubstring(output, g1.toString()),
                         g1.toString() + " doit apparaître une fois");

		assertEquals(1,	countSubstring(output, g2.toString()),
                    g2.toString() + " doit apparaître une fois");

		assertEquals(3,	countSubstring(output, "Stationnement"),
                        "On doit imprimer trois stationnements"	);

		assertEquals(1,	countSubstring(output, "en cours"),
                        "Il doit y avoir un seul stationnement en cours");
	}

	/**
	 * Une méthode utilitaire pour le test ci-dessus
	 * Compter le nombre d'occurrences d'une sous-chaîne dans une chaîne
	 *
	 * @param string String to look for substring in.
	 * @param substring Sub-string to look for.
	 * @return Count of substrings in string.
	 */
	private int countSubstring(final String string, final String substring) {
		int count = 0;
		int idx = 0;

		while ((idx = string.indexOf(substring, idx)) != -1) {
			idx++;
			count++;
		}

		return count;
	}
}
