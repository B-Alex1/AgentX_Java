//BAKALOV Alexander et DIMANACHKI Alexander groupe 9
import java.util.ArrayList;

public class Agent9{
    private Grille grille;
    private ArrayList<Joyau> sacJoyau;
    private int x;
    private int y;

    public Agent9(Grille g){
        grille = g;
        sacJoyau = new ArrayList<Joyau>();
        x = 0;
        y = 0;
    }

    //Methodes deplacement
    //Se deplacer sans Force
    public void seDeplacer(int xnew, int ynew) throws CoordonneesIncorrectesException{
        this.x = xnew;
        this.y = ynew;

        Contenu c = grille.getCase(xnew, ynew);
        if (c instanceof Joyau){
            try{
                sacJoyau.add((Joyau)c);
                grille.videCase(xnew, ynew);
                System.out.println("Agent9 récupère le joyau: "+((Joyau)c).toString());
            }catch (CaseNonPleineException e){
                System.out.println(e.getMessage());
            }

        }else if(c instanceof Gardien){
            System.out.println("Agent9 rencontre un gardien");
            sacJoyau.clear();
            System.out.println("Agent9 perd tous ses joyaux");
        }
    }

    //Se deplacer avec Force
    public void seDeplacer(int xnew, int ynew, int f) throws CoordonneesIncorrectesException{
        this.x = xnew;
        this.y = ynew;

        Contenu c = grille.getCase(xnew, ynew);
        if (c instanceof Joyau){
            try{
                sacJoyau.add((Joyau)c);
                grille.videCase(xnew, ynew);
                System.out.println("Agent9 récupère le joyau: "+((Joyau)c).toString());
            }catch (CaseNonPleineException e){
                System.out.println(e.getMessage());
            }
        }else if(c instanceof Gardien){
            System.out.println("Agent9 rencontre un gardien qui a "+((Gardien)c).getPDV()+" pdv");
            if(((Gardien)c).getPDV() > f){
                sacJoyau.clear();
                ((Gardien)c).retirerPDV(f);
                System.out.println("Agent9 perd tous ses joyaux et le gardien a "+((Gardien)c).getPDV()+" pdv");
            }else{
                try{
                    grille.videCase(xnew, ynew);
                    System.out.println("Agent9 a battu le gardien!");
                }catch (CaseNonPleineException e){
                System.out.println(e.getMessage());
                }
            }
        }
    }

    //Methodes Sac
    public int fortune(){
        int prix = 0;
        for (Joyau j : sacJoyau){
            prix += j.getQuantite();
        }
        return prix;
    }

    public String contenuSac(){
        String s = "";
        for(Joyau j : sacJoyau){
            s+= j.toString()+"\n";
        }
        return s;
    }

    //Getteurs
    public int getX(){ return x;}
    public int getY(){ return y;}

    public String toString(){
        String s = "L'Agent9 est a la case ["+x+" ; "+y+"], son sac";
        if(this.fortune() != 0){
            s += " vaut "+this.fortune()+" or et il contient:\n"+this.contenuSac();
        }else{
            s += " est vide.";
        }
        return s;
    }
}
