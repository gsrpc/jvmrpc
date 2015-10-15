package com.gsrpc.test;

using gslang.Exception;

@Exception
table ResourceException{}

@Exception
table UnknownException{}


table IPV4 {
    byte[4]     Address;
}

// contract sample
contract DNSResolver {
    // parse domain name to ip address
    IPV4 Resolve(string domain) throws(UnknownException,ResourceException);
}
