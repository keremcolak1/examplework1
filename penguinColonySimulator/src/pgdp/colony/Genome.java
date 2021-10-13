package pgdp.colony;

import org.w3c.dom.css.RGBColor;

import static pgdp.MiniJava.randomBoolean;
import static pgdp.MiniJava.randomInt;

public class Genome {
    // Constants
    private static final int LENGTH = 13; //length of the String dna
    private final String dna;

    public Genome(String dna) {
        this.dna = dna;
    }

    public String toString() {
        return this.dna;
    }

    public static int similarity(Genome a, Genome b) {
        int sim = 0;
        for (int i = 0; i < 13; i++) {
            if (a.dna.charAt(i) == b.dna.charAt(i)) {
                sim++;
            }
        }


        return sim;
    }

    public Genome() {
        StringBuilder Basen = new StringBuilder(LENGTH);
        StringBuilder Quarter = new StringBuilder(LENGTH);

        int s;
        char t;
        for (int i = 0; i < LENGTH; i++) {
            t = 'R';
            s = randomInt(0, 3);
            Quarter.append(s);
            if (s == 0) {
                t = 'A';
            }
            if (s == 1) {
                t = 'C';
            }
            if (s == 2) {
                t = 'G';
            }
            if (s == 3) {
                t = 'T';
            }
            Basen.append(t);

        }
        String dnanumber = Quarter.toString();
        dna = Basen.toString();

    }

    public static Genome combine(Genome mother, Genome father) {
        int s;
        char t;
        StringBuilder combineddna = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            t = 'R';
            if (randomBoolean(1000)) {
                s = randomInt(0, 3);
                if (s == 0) {
                    t = 'A';
                }
                if (s == 1) {
                    t = 'C';
                }
                if (s == 2) {
                    t = 'G';
                }
                if (s == 3) {
                    t = 'T';
                }
            } else if (randomBoolean()) {
                t = mother.dna.charAt(i);
            } else {
                t = father.dna.charAt(i);
            }
            combineddna.append(t);

        }

        Genome combined = new Genome(combineddna.toString());

        return combined;
    }

    public String getGene(int genePos, int geneLength) {
        StringBuilder wantedGene = new StringBuilder(geneLength);

        for (int i = genePos; i < geneLength + genePos; i++) {
            char t = 'R';
            t = dna.charAt(i);
            wantedGene.append(t);
        }

        return wantedGene.toString();
    }

    public static int interpretGene(String gene) {
        int wert = 0;
        int s = 0;
        for (int i = 0; i < gene.length(); i++) {
            char r = gene.charAt(i);
            if (r == 'A') {
                s = 0;
            }
            if (r == 'C') {
                s = 1;
            }
            if (r == 'G') {
                s = 2;
            }
            if (r == 'T') {
                s = 3;
            }
            wert = (int) (wert + s * Math.pow(4, gene.length() - 1 - i));


        }

        return wert;
    }

    public int lifespan() {
        return 8 + interpretGene(getGene(0, 2));
    }

    public int maxSize() {
        return 16 + interpretGene(getGene(2, 2));
    }

    public int skill() {
        return interpretGene(getGene(4, 5));
    }

    public byte color() {

        return (byte) interpretGene(getGene(9, 3));
    }

    public boolean isMale() {
        return interpretGene(getGene(12, 1)) % 2 == 0;
    }

}

