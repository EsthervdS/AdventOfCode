import util.*;
import java.io.FileInputStream;
import java.security.*;

public class MD5 {

    private static String computeMD5(String str) {
	String res = "";
	try {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    byte[] dataBytes = str.getBytes();	    
	    md.update(dataBytes, 0, dataBytes.length);
	    byte[] mdbytes = md.digest();
	    
	    //convert the byte to hex format method 1
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < mdbytes.length; i++) {
		sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    res = sb.toString();

	} catch (NoSuchAlgorithmException e) {
	    System.err.println(e.getMessage());
	}
	return res;
    }

    private static int findAnswer(String prefix, int n) {
	int i = 1;
	String md5,zeroes;
	String cc = prefix + Integer.toString(i);
	String zeroStr = "";
	for (int j=0; j<n; j++) {
	    zeroStr += "0";
	}
	while (true) {
	    md5 = computeMD5(cc);
	    zeroes = md5.substring(0,n);
	    
	    if (zeroes.equals(zeroStr)) {
		break;
	    } else {
		i++;
		cc = prefix + Integer.toString(i);
	    }
	}
	return i;
    }
    public static void main(String[] args) {
	String secret = args[0];
	IO.print("Part 1: " + findAnswer(secret, 5));
	IO.print("Part 2: " + findAnswer(secret, 6));

    }
}
