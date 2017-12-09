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

    public static void main(String[] args) {
	String prefix = args[0];
	int i = 1;
	String md5,five;
	String cc = prefix + Integer.toString(i);
	while (true) {
	    md5 = computeMD5(cc);
	    five = md5.substring(0,5);

	    if (!five.equals("00000")) {
		break;
	    } else {
		i++;
		cc = prefix + Integer.toString(i);
	    }
	}
	IO.print("Part 1: " + i);

    }
}
