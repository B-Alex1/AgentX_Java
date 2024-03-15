//BAKALOV Alexander et DIMANACHKI Alexander groupe 9

public class SuperGardien extends Gardien implements Teleportable{
    public SuperGardien(){
        super("SuperGardien", 1);
    }

    public void seTeleporter(int xnew, int ynew, Grille grille) throws CoordonneesIncorrectesException{
        try{
            grille.videCase(this.getX(), this.getY());
            grille.setCase(xnew, ynew, this);
        }catch (CaseNonPleineException e){
            System.out.println(e.getMessage());
        }
    }

    public String toString(){
        return "SuperGardien à la case ["+this.getX()+" ; "+this.getY()+"]";
    }
}
