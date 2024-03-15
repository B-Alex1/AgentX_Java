//BAKALOV Alexander et DIMANACHKI Alexander groupe 9

public class TestSimulation{
    public static void main(String[] args){   
        Grille g = new Grille(5,5);
        Simulation sim = new Simulation(g, 15);

        sim.lance(15);
    }
}