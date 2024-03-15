//BAKALOV Alexander et DIMANACHKI Alexander groupe 9

public class Joyau extends Contenu{
    private int prix;

    public Joyau(String type, int prix){
        super(type, prix);
        this.prix = prix;
    }

    public String toString(){
        return super.type+"\t"+this.prix;
    }
}