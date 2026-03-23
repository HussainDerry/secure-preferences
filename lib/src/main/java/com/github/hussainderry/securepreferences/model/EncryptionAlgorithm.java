package com.github.hussainderry.securepreferences.model;

import java.util.Arrays;

/**
 * Encryption algorithms supported by the {@link com.github.hussainderry.securepreferences.crypto.CipherService}
 * @author Hussain Al-Derry <hussain.derry@gmail.com>
 * */
public enum EncryptionAlgorithm {

    AES(new int[]{128, 196, 256}), TripleDES(new int[]{128, 192});

    private final int[] keySizesInBits;

    EncryptionAlgorithm(int[] keySizes) {
        this.keySizesInBits = Arrays.copyOf(keySizes, keySizes.length);
    }

    public int[] getKeySizes() {
        return Arrays.copyOf(keySizesInBits, keySizesInBits.length);
    }
}
