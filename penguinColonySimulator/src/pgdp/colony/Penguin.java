package pgdp.colony;

import java.awt.*;

import static pgdp.MiniJava.*;

public class Penguin {
    //Constants
    static final int MAX_HEALTH = 50;
    private final String name;
    private final Genome genome;
    private int ageInDays;
    private int size;
    private int health;
    private int numFish;
    private Penguin child;
    private byte color;

    public String getName() {
        return name;
    }

    public Genome getGenome() {
        return genome;
    }

    public int getAge() {

        return (int) Math.floor(ageInDays / 5);
    }
    public int getAgeInDays(){
        return ageInDays;
    }

    public int getSize() {
        return size;
    }

    public int getHealth() {
        return health;
    }

    public int getNumFish() {
        return numFish;
    }

    public Penguin getChild() {
        if (child == null) {
            return null;
        }
        return child;
    }

    public void setChild(Penguin child) {
        this.child = child;
    }

    public Penguin(Penguin mother, Penguin father) {
        name = generateName();
        genome = Genome.combine(mother.genome, father.genome);
        health = MAX_HEALTH;
        ageInDays = 0;
        numFish = 0;
        size = (int) Math.floor(genome.maxSize() / 2);
        child = null;
    }

    public Penguin(Genome genome, int ageInDays) {
        name = generateName();
        health = MAX_HEALTH;
        this.ageInDays = ageInDays;
        this.genome = genome;
        numFish = 0;
        child = null;
        size = (int) Math.floor(genome.maxSize() / 2);


    }

    public boolean canMate() {
        return this.health > 25 && this.child == null && this.getAge() >= 1;
    }

    public int checkSimilarity(Penguin partner) {

        return Genome.similarity(this.genome, partner.genome);
    }

    public Penguin mateWith(Penguin male) {
        Penguin kind = new Penguin(this, male);
        this.child = kind;
        male.child = kind;

        return kind;
    }

    public int hunt(int competition) {
        int x = randomInt((int) Math.floor(genome.skill() / 2), genome.skill());
        int fish = (int) (Math.floor(this.size / 2) + Math.floor(x / competition));
        this.numFish = numFish + fish;


        return fish;
    }

    public void eat() {
        if (this.child != null) {
            this.child.numFish = (int) (this.child.numFish + Math.floor(this.numFish / 4));
            this.numFish = (int) (this.numFish - Math.floor(this.numFish / 4)); //remaining fish after child
        }
        if (this.numFish >= this.size) { //eat as much as its size
            this.numFish = this.numFish - this.size;
            while (this.health < 50) {
                if (this.numFish == 0) {
                    break;
                }
                this.health = this.health + 1;
                this.numFish--;
            }
            if (this.numFish > 0 && this.genome.maxSize() > this.size) {
                while (this.genome.maxSize() > this.size) { //eat until reached maxsize
                    if (this.numFish == 0) {
                        break;
                    }
                    this.size++;
                    this.numFish--;
                }
            }

        } else {
            this.health = this.health - (this.size - this.numFish); //lose health
            this.numFish = 0;

        }

    }

    public boolean sleep() {
        this.ageInDays++;


        if (this.child != null) {
            if (this.child.health <= 0 || this.child.ageInDays > this.child.genome.lifespan() || this.child.getAge() >= 1) {
                this.child = null;

            }
        }
        if(this.getAgeInDays() > this.genome.lifespan()){
            this.health=0;
        }

        if (this.getHealth()>0) {
            return true;
        }else
        return false;

    }

    public String toString() { //<name> (<age><M/F>): DNA=<DNA Sequence>; Health=<health>; Size=<size>/<maxSize>; Lifespan=<lifespan>; Skill=<skill>; Num Fish=<numFish>; Color=<color>; Child=<none/child name>;
        StringBuilder s = new StringBuilder();
        s.append(this.name + ' ');
        s.append("(" + this.getAge());
        if (this.genome.isMale()) {
            s.append('M' + "): ");
        } else s.append('F' + "): ");
        s.append("DNA=" + this.genome.toString() + "; ");
        s.append("Health=" + this.health + "; ");
        s.append("Size=" + this.size + "/" + this.genome.maxSize() + "; ");
        s.append("Lifespan=" + this.genome.lifespan() + "; ");
        s.append("Skill=" + this.genome.skill() + "; ");
        s.append("Num Fish=" + this.numFish + "; ");
        s.append("Color=" + this.genome.color() + "; ");
        if (this.child != null) {
            s.append("Child=" + this.child.name + ";");
        } else s.append("Child=" + "none" + ";");


        return s.toString();
    }

}
