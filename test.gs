package com.gsrpc.test;

using gslang.Exception;

@Exception
table ResourceException{}

@Exception
table UnknownException{}

@gslang.POD
table IPV4 {
    byte[4]     Address;
}

table V0 {
    int32   F1;
    float32 F2;
    byte[]  F3;
}

table V1 {
    int32       F1;
    float32     F2;
    byte[]      F3;
    string[]    F4;
    string[4]   F5;
}

table C0 {
    int32       C1;
    V0          C2;
    float32     C3;
}

table C1 {
    int32       C1;
    V1          C2;
    float32     C3;
    float64     C4;
}


// contract sample
contract DNSResolver {
    // parse domain name to ip address
    IPV4 Resolve(string domain) throws(UnknownException,ResourceException);
    @gslang.Async
    void AsyncResolve(string domain);
}

contract DNSListener {
    void Changed(string domain,IPV4 ip);
}
