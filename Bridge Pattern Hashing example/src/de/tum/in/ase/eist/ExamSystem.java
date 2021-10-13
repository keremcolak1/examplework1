package de.tum.in.ase.eist;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ExamSystem {

    private static Hashing hashGenerator;

    private ExamSystem() {
    }

    public static String hashFile(String document) {
        return hashGenerator.hashDocument(document);
    }

    public static void main(String[] args) throws IOException {
        String file1 = readFile("exams/short_exam.txt");
        String file2 = readFile("exams/long_exam.txt");  //This file is too big for Preview Hashing

        hashGenerator = new PreviewHashing();

        System.out.println(hashFile(file1));
        try {
            System.out.println(hashFile(file2));
            throw new IllegalStateException("Hashing this file with preview hashing should not work!");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        hashGenerator = new EnterpriseHashing();

        System.out.println(hashFile(file1));
        System.out.println(hashFile(file2));
    }

    public static String readFile(String filepath) throws RuntimeException {
        Path path = Path.of(filepath);
        String content = new String();
        try{
        content= Files.readString(path);}
        catch (RuntimeException | IOException runtimeException){
            System.out.println("File is too large for Preview!");
        }
        return content;
    }

}
