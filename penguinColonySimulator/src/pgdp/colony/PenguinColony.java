package pgdp.colony;

import java.util.Comparator;
import java.util.List;

import static pgdp.MiniJava.*;

public class PenguinColony {
    // Constants
    static final int ADULT_AGE = 1;
    static final int DAYS_PER_YEAR = 5;
    private int time = 0;

    private PenguinList penguins = new PenguinList();

    public int getTime() {
        return time;
    }

    public Penguin createPenguin(Genome genome) {
        Penguin neu = new Penguin(genome, 5);
        this.penguins.add(neu);

        return neu;
    }

    public PenguinList getPopulation() {

        penguins.list.sort(new SortByAge());

        return penguins;
    }

    class SortByAge implements Comparator<Penguin> {

        @Override
        public int compare(Penguin o1, Penguin o2) {
            return o2.getAge() - o1.getAge();
        }
    }

    class SortByAgeReverse implements Comparator<Penguin> {

        @Override
        public int compare(Penguin o1, Penguin o2) {
            return o1.getAge() - o2.getAge();
        }
    }

    public void simulateDay() {
        this.time++;
        //getPopulation(); returns sorted population

        for (int i = 0; i < getPopulation().size(); i++) {
            getPopulation().get(i).hunt(this.penguins.size());
        }
        for (int i = 0; i < getPopulation().size(); i++) {
            getPopulation().list.get(i).eat();
        }
        this.penguins.list.sort(new SortByAgeReverse());

        PenguinList deadList = new PenguinList();
        for (Penguin p : this.penguins) {
            if (!p.sleep()) {
                deadList.add(p);
            }
        }
        deadList.list.sort(new SortByAgeReverse());
        for (Penguin y : deadList) {
            this.penguins.remove(y);
        }


        getPopulation().list.sort(new SortByAge());
        if (this.time % 5 == 0) {// mating day
            PenguinList pfw = new PenguinList(); //paarungsfahige weibchen
            PenguinList pfm = new PenguinList(); //mannchen
            //set them all in
            for (int i = 0; i < getPopulation().size(); i++) {
                if (!getPopulation().get(i).getGenome().isMale() && getPopulation().get(i).canMate()) {
                    pfw.add(getPopulation().get(i));
                }
            }
            for (int i = 0; i < getPopulation().size(); i++) {
                if (getPopulation().get(i).getGenome().isMale() && getPopulation().get(i).canMate()) {
                    pfm.add(getPopulation().get(i));
                }
            }
            if (!pfw.list.isEmpty() && !pfm.list.isEmpty()) {
                pfw.list.sort(new SortByAgeReverse()); //jungere vor alteren
                pfm.list.sort(new SortByAgeReverse());
                int[][] sim = new int[pfw.size()][pfm.size()]; //array to save similarities

                for (int i = 0; i < pfw.size(); i++) {
                    for (int j = 0; j < pfm.size(); j++) {
                        sim[i][j] = Genome.similarity(pfw.get(i).getGenome(), pfm.get(j).getGenome());
                    }
                }
                int indexofm = 0; //index of mating male in similarty array

                for (int i = 0; i < sim.length; i++) {
                    int geringste = sim[i][0]; // find lowest simlarity
                    for (int j = 0; j < sim[i].length; j++) {
                        if (sim[i][j] < geringste) {
                            geringste = sim[i][j];
                            indexofm = j;
                        }
                    }
                    geringste = 13; //max sim

                    for (int a = 0; a < sim[i].length; a++) { //find the owner of the index in list
                        if (pfm.list.get(a).canMate()) {
                            if (sim[i][a] < geringste) {
                                geringste = sim[i][a];
                                indexofm = a;
                            }
                        }
                    }
                    if (pfw.list.get(i).canMate() && pfm.list.get(indexofm).canMate()) { //mate them

                        getPopulation().list.add((pfw.list.get(i)).mateWith(pfm.get(indexofm)));
                    }

                }


            }


        }

    }


}



