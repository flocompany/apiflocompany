import java.security.NoSuchAlgorithmException;

import com.flocompany.util.SecurityUtil;


public class SecurityTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			String hex = SecurityUtil.hash256("Salut");
			System.out.println("SHAAAAA" + hex);

			System.out.println("SHAAAAA" + SecurityUtil.bytesToHex(hex.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
