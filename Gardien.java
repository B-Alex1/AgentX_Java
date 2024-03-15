//BAKALOV Alexander et DIMANACHKI Alexander groupe 9

public class Gardien extends Contenu{
    private int pointsDeVie;

    public Gardien(String type, int q){
        super(type, q);
        pointsDeVie = 50;
    }

    public Gardien(){
        this("Gardien", 1);
        pointsDeVie = 50;
    }

    public int getPDV(){
        return pointsDeVie;
    }

    public void retirerPDV(int f){
        this.pointsDeVie -= f;
    }
}
