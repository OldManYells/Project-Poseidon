package com.legacyminecraft.poseidon.api;

public interface Convertable {

    boolean isConvertable(String s);

    boolean convert(String s, IProgressUpdate iprogressupdate);
}
