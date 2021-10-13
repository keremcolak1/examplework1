package pgdp.colony;

public class Test {
    public static void main(String[] args) {
        String keremdna = "TTTTCTTTTAATT";
        Genome kerem = new Genome(keremdna);

        System.out.println((kerem.skill()));

    }
}
