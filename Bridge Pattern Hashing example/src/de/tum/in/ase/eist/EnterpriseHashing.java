package de.tum.in.ase.eist;

public class EnterpriseHashing extends Hashing {
    //HashFunction implementation;

    public EnterpriseHashing() {
        super( new CryptoSecureHashAlgorithm()
        );
    }

    @Override
    public String hashDocument(String string) {
        return getImplementation().calculateHashCode(string);
    }
}
