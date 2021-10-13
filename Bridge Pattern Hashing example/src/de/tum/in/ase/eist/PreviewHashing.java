package de.tum.in.ase.eist;

public class PreviewHashing extends Hashing {
    //HashFunction implementation;

    public PreviewHashing() {
        super(new SimpleHashAlgorithm());
    }

    @Override
    public String hashDocument(String string) {
        if(string.length()>1000){
            throw new IllegalArgumentException("Exam is too large for preview.");
        }
        return getImplementation().calculateHashCode(string);
    }
}
