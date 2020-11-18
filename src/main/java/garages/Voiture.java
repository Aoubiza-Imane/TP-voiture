package garages;

import java.io.PrintStream;
import java.util.*;

public class Voiture {

    private final String immatriculation;
    private final List<Stationnement> myStationnements = new LinkedList<>();

    public Voiture(String i) {
        if (i == null) {
            throw new IllegalArgumentException("Une voiture doit avoir une immatriculation");
        }
        immatriculation = i;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    /**
     * Fait rentrer la voiture au garage 
     * Précondition : la voiture ne doit pas être déjà dans un garage
     * @param grg le garage où la voiture va stationner
     * @throws java.lang.Exception si déjà dans un garage
     */
    public void entreAuGarage(Garage grg) throws Exception {
        // Et si on avait la voiture déjà dans un garage ? 
        if (!this.estDansUnGarage()) {
            Stationnement s = new Stationnement(this, grg); // la voiture rentre dans un nouveau garage
            myStationnements.add(s);
        } else {
            //le véhicule est déjà dans un garage
            throw new Exception("La voiture est déjà dans un garage.");
        }
    }

    /**
     * Fait sortir la voiture du garage 
     * Précondition : la voiture doit être dans un garage
     * @throws java.lang.Exception si la voiture n'est pas dans un garage
     */
    public void sortDuGarage() throws Exception {
        if (this.estDansUnGarage()) {
            LinkedList<Stationnement> s;
            s = (LinkedList) myStationnements;
            // pollLast supprime et renvoie le dernier élément de la liste
            Stationnement sLast = s.pollLast();
            // On sort du garage
            sLast.terminer();
            s.addLast(sLast);

        } else {
            //la voiture n'est pas dans un garage
            throw new Exception("La voiture n'est pas dans un garage.");
        }
        // Trouver le dernier stationnement de la voiture
        // Terminer ce stationnement
    }

    /**
     * @return l'ensemble des garages visités par cette voiture
     */
    public Set<Garage> garagesVisites() {
        // On crée une collection qui contiendra les garages visités
        Set<Garage> garagesVisites;
        garagesVisites = new HashSet<>();
        // On parcourt la liste des stationnements qui contient les garages visités
        this.myStationnements.forEach(s -> {
            // On ajoute dans le set crée tous les garages
            garagesVisites.add(s.getGarage());
        });
        return garagesVisites;
    }

    /**
     * @return vrai si la voiture est dans un garage, faux si ce n'est pas le cas
     */
    public boolean estDansUnGarage() {
        if (!this.myStationnements.isEmpty()) {
            LinkedList<Stationnement> s;
            s = (LinkedList) this.myStationnements;
            Stationnement sLast = s.peekLast();
            // Vrai si le dernier stationnement est en cours
            return sLast.estEnCours();
        }
        return false;

    }

    /**
     * Pour chaque garage visité, imprime le nom de ce garage suivi de la liste
     * des dates d'entrée / sortie dans ce garage
     * <br>Exemple :
     * <pre>
     * Garage Castres:
     *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
     *		Stationnement{ entree=28/01/2019, en cours }
     *  Garage Albi:
     *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
     * </pre>
     *
     * @param out l'endroit où imprimer (ex: System.out)
     */
    public void imprimeStationnements(PrintStream out) {
        // Cette implémentation n'est pas optimale
        // On repasse plusieurs fois par stationnement
        for (Garage g : this.garagesVisites()) {
            out.println(g + ":");
            for (Stationnement s : this.myStationnements) {
                if (g.equals(s.getGarage())) {
                   out.println(s);
                }
            }
        }
    }

}
