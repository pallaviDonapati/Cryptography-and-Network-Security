exp 8
import java.util.*; 
import java.math.BigInteger; 
class dsaAlg 
{ 
final static BigInteger one = new BigInteger("1"); 
final static BigInteger zero = new BigInteger("0"); 
/* incrementally tries for next prime */ 
public static BigInteger getNextPrime(String ans) 
{ 
BigInteger test = new BigInteger(ans); 
while (!test.isProbablePrime(99)) 
{ 
test = test.add(one); 
} 
return test; 
} 
/* finds largest prime factor of n */ 
public static BigInteger findQ(BigInteger n) 
{ 
BigInteger start = new BigInteger("2"); 
while (!n.isProbablePrime(99)) 
{ 
while (!((n.mod(start)).equals(zero))) 
{ 
start = start.add(one); 
} 

n = n.divide(start); 
} 
return n; 
} 
/* finds a generator mod p */ 
public static BigInteger getGen(BigInteger p, BigInteger q, Random r) 
{ 
BigInteger h = new BigInteger(p.bitLength(), r); 
h = h.mod(p); 
return h.modPow((p.subtract(one)).divide(q), p); 
} 
public static void main (String[] args) throws java.lang.Exception 
{ 
Random randObj = new Random(); 
/* establish the global public key components */ 
BigInteger p = getNextPrime("10600"); /* approximate prime */ 
BigInteger q = findQ(p.subtract(one)); 
BigInteger g = getGen(p,q,randObj); 
/* public key components */ 
System.out.println("Digital Signature Algorithm"); 
System.out.println("global public key components are:"); 
System.out.println("p is: " + p); 
System.out.println("q is: " + q); 
System.out.println("g is: " + g); 
/* find the private key */ 
BigInteger x = new BigInteger(q.bitLength(), randObj); 
x = x.mod(q); 
/* corresponding public key */ 
BigInteger y = g.modPow(x,p); 
/* random value message */ 
BigInteger k = new BigInteger(q.bitLength(), randObj); 
k = k.mod(q); 
/* randomly generated hash value and digital signature */ 
BigInteger r = (g.modPow(k,p)).mod(q); 
BigInteger hashVal = new BigInteger(p.bitLength(), randObj); 
BigInteger kInv = k.modInverse(q); 
BigInteger s = kInv.multiply(hashVal.add(x.multiply(r))); 
s = s.mod(q); 

/* secret information */ 
System.out.println("secret information are:"); 
System.out.println("x (private) is: " + x); 
System.out.println("k (secret) is: " + k); 
System.out.println("y (public) is: " + y); 
System.out.println("h (rndhash) is: " + hashVal); 
System.out.println("Generating digital signature:"); 
System.out.println("r is : " + r); 
System.out.println("s is : " + s); 
/*verify the digital signature */ 
BigInteger w = s.modInverse(q); 
BigInteger u1 = (hashVal.multiply(w)).mod(q); 
BigInteger u2 = (r.multiply(w)).mod(q); 
BigInteger v = (g.modPow(u1,p)).multiply(y.modPow(u2,p)); 
v = (v.mod(p)).mod(q); 
System.out.println("verifying digital signature (checkpoints):"); 
System.out.println("w is : " + w); 
System.out.println("u1 is : " + u1); 
System.out.println("u2 is : " + u2); 
System.out.println("v is : " + v); 
if (v.equals(r)) 
{ 
System.out.println("success: digital signature is verified! " + r); 
} 
else 
{ 
System.out.println("error: incorrect digital signature"); 
} 
} 
}