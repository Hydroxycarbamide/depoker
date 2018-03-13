/**
 * Created by Eric on 13/03/2018.
 */

public class Joueur {
    int idJoueur;
    String name;

    public Joueur(int idJoueur, String name){
        this.idJoueur = idJoueur;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdJoueur() {

        return idJoueur;
    }

    public void setIdJoueur(int idJoueur) {
        this.idJoueur = idJoueur;
    }
}
