package com.gsrpc.net;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class DHKey {

    private SecureRandom rand = new SecureRandom();

    private final BigInteger _G;
    private final BigInteger _P;

    private BigInteger _E;
    private BigInteger _R;

    public DHKey(BigInteger G, BigInteger P){

        _G = G;
        _P = P;
    }

    public BigInteger Exchange() {
        _R = new BigInteger(64, rand).modPow(new BigInteger("1"), _P);

        _E = _G.modPow(_R, _P);

        return _E;
    }

    public BigInteger SharedKey(BigInteger E) {
        return E.modPow(_R, _P);
    }

    @Override
    public String toString() {
        //return string.Format ("[DHKey]{G:{0},P:{1},R:{2},E:{3}}", _G, _P, _R, _E);
        return "[DHKey]{G:" + _G + ",P:" + _P + ",R:" + _R + ",E:" + _E + "}";
    }
}