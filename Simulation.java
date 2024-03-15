//BAKALOV Alexander et DIMANACHKI Alexander groupe 9

import java.util.ArrayList;

public class Simulation{
    private Grille grille;
    private Agent9 agent;
    private ArrayList<Contenu> tab;
    private ArrayList<SuperGardien> supGardTab;

    //Constructeur
    public Simulation(Grille g, int m){
        grille = g;
        agent = new Agent9(grille);
        tab = new ArrayList<Contenu>();
        supGardTab = new ArrayList<SuperGardien>();
        
        //Placement de m objets sur la grille.
        int cpt=0;
        for(int i=0; i<grille.nbLignes; i++){
            for(int j=0; j<grille.nbColonnes; j++){
                if(i==0 && j==0) //On ne veut pas de contenu sur la case de départ de l'agent
                    continue;

                if(cpt >= m) //Si on a atteint limite de nombre d'objets
                    break;
                
                double rand = Math.random(); //Pour choisir s'il y aura un objet dans la case
                if(rand<0.7){ //70% de chance de placer quelquechose
                    cpt++;
                    try{
                        int chance = (int)(Math.random()*(100));

                        //50% de chance: opale
                        if(chance>=50){
                            AjoutJoyau(i, j, "Opale", 500);
                        //20% de chance: emeralde
                        }else if(chance>=30){
                            AjoutJoyau(i, j, "Emerald", 2000);
                        //20% de chance: gardien
                        }else if(chance>=10){
                            //50% de chance d'avoir un super gardien qui se téléporte
                            if(chance>20){
                                SuperGardien gard = new SuperGardien();
                                grille.setCase(i,j,gard);
                                tab.add(gard);
                                supGardTab.add(gard);
                                System.out.println("Un super gardien est sur la grille!");
                            }else{
                                Gardien gard = new Gardien();
                                grille.setCase(i,j,gard);
                                tab.add(gard);
                            }
                        //5% de chance: rubis
                        }else if(chance>=5){
                            AjoutJoyau(i, j, "Rubis", 4000);
                        //3% de chance: diamant
                        }else if(chance>=2){
                            AjoutJoyau(i, j, "Diamant", 8000);
                        //1% de chance: Alexandrite
                        }else if(chance>0){
                            AjoutJoyau(i, j, "Alexandrite", 9000);
                            System.out.println("Le joyau le plus précieux, l'Alexandrite, est sur la grille!");
                        }
                    }catch (CoordonneesIncorrectesException e){
                        System.out.println("Erreur création simulation: "+e.getMessage());
                    }
                }
            }
        }
    }

    //Fonction pour ajouter joyaux
    private void AjoutJoyau(int x, int y, String type, int prix) throws CoordonneesIncorrectesException{
        Joyau j = new Joyau(type, prix);
        grille.setCase(x, y, j);
        tab.add(j);
    }

    public String toString(){
        grille.affiche(12);
        return agent.toString();
    }

    //Fonction qui permet de simplifier l'obtention de valeur aléatoires
    public int randomize(int min, int max){
        return (int)(Math.random()*(max-min+1))+min;
    }

    //Lancement de la simulation
    public void lance(int nbEtapes){
        //Verification nbEtapes
        try{
            if(nbEtapes<0) throw new NbEtapesIncorrecteException("nbEtapes doit être supérieur a 0");
        }catch(NbEtapesIncorrecteException e){
            System.out.println("Erreur : "+e.getMessage());
        }

        //Affichage initial
        System.out.println("Positions initiales:");
        System.out.println(this.toString()+"\n----------------");

        //Lancement des etapes
        for(int i=1; i<=nbEtapes; i++){
            int xnew = agent.getX(), ynew = agent.getY();
            try{
                xnew += randomize(-1, 1);
                ynew += randomize(-1, 1);
                //Verification coordonnees (on veut que l'agent se déplace à chaque tour)
                if(!(grille.sontValides(xnew,ynew)) || (xnew == agent.getX() && ynew == agent.getY())){
                    throw new CoordonneesIncorrectesException("");
                }

                System.out.println("Tour "+i+":");

                //Teleportation superGardien
                for(SuperGardien gard : supGardTab){
                    int gxnew = randomize(0, grille.nbColonnes-1);
                    int gynew = randomize(0, grille.nbLignes-1);
                    if(Math.random()<0.3){ //30% de chance de se téléporter
                        try{
                            System.out.println("Le super gardien "+gard.toString()+" se téléporte vers la case ["+gxnew+" ; "+gynew+"]");
                            
                            //S'il y a du contenu dans la case (autre que le gardien meme), on le déplace à l'ancienne place du gardien
                            if(!(grille.caseEstVide(gxnew, gynew)) && (gard.getX())!=gxnew && (gard.getY())!=gynew){
                                int ancienx = gard.getX(), ancieny = gard.getY();
                                Contenu c = grille.getCase(gxnew, gynew);

                                grille.videCase(gxnew, gynew);
                                gard.seTeleporter(gxnew, gynew, grille);
                                grille.setCase(ancienx, ancieny, c);
                            }else{
                                gard.seTeleporter(gxnew, gynew, grille);
                            }
                        }catch (CoordonneesIncorrectesException e){
                            System.out.println("Erreur teleportation super gardien: "+e.getMessage());
                        }catch(CaseNonPleineException e){
                            System.out.println("Erreur vidage pour teleportation super gardien: "+e.getMessage());
                        }
                    }
                }

                //Deplacement agent
                if(Math.random()<0.3){ //30% de chance s'utiliser force
                    int force = randomize(10,100);
                    System.out.println("Agent9 se deplace vers ["+xnew+" ; "+ynew+"] avec une force de "+force);
                    agent.seDeplacer(xnew, ynew, force);
                }else{
                    System.out.println("Agent9 se deplace vers ["+xnew+" ; "+ynew+"]");
                    agent.seDeplacer(xnew, ynew);
                }

                //Affichage du resultat du tour
                System.out.println(this.toString()+"\n----------------");
            }catch(CoordonneesIncorrectesException e){
                i-=1; //Si nouvelles coordonnées non convenables, on relance le tour
            }
        }
    }
}
