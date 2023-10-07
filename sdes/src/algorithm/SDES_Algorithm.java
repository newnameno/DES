package algorithm;

import gui.SDES_GUI;

public class SDES_Algorithm {

	public static String key1;// 输出的密钥1
	public static String key2;// 输出的密钥2
	public static int[] IP = new int[] { 2, 6, 3, 1, 4, 8, 5, 7 };// 明文初始置换
	public static int[] EP = new int[] { 4, 1, 2, 3, 2, 3, 4, 1 };// E/P扩位置换
	public static int[] P10 = new int[] { 3, 5, 2, 7, 4, 10, 1, 9, 8, 6 };// 主密钥初始置换
	public static int[] P8 = new int[] { 6, 3, 7, 4, 8, 5, 10, 9 };// 子密钥初始置换
	public static int[] P4 = new int[] { 2, 4, 3, 1 };// 轮函数的最终P4置换
	public static int[] IP_1 = new int[] { 4, 1, 3, 5, 7, 2, 8, 6 };

	public static String[][] S1_box = new String[][] { { "01", "00", "11", "10" }, { "11", "10", "01", "00" },
			{ "00", "10", "01", "11" }, { "11", "01", "00", "10" } };
	public static String[][] S2_box = new String[][] { { "00", "01", "10", "11" }, { "10", "00", "01", "11" },
			{ "11", "10", "01", "00" }, { "10", "01", "00", "11" } };

	public static String substitue(String str, int[] P) { // 进行置换操作
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < P.length; i++) {
			sb.append(str.charAt((P[i]) - 1));
		}
		return new String(sb);
	}

	public static String xor(String str, String key) { // 进行异或操作
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == key.charAt(i)) {
				sb.append("0");
			} else {
				sb.append("1");
			}
		}
		return new String(sb);
	}

	public static String searchSbox(String str, int n) { // S盒的查找
		StringBuilder sb = new StringBuilder();
		sb.append(str.charAt(0));
		sb.append(str.charAt(3));
		String ret = new String(sb);
		StringBuilder sb1 = new StringBuilder();
		sb1.append(str.charAt(1));
		sb1.append(str.charAt(2));
		String ret1 = new String(sb1);
		String retu = new String();
		if (n == 1) {
			retu = S1_box[Integer.parseInt(ret, 2)][Integer.parseInt(ret1, 2)];
		} else {
			retu = S2_box[Integer.parseInt(ret, 2)][Integer.parseInt(ret1, 2)];
		}
		return retu;
	}

	public static void getkey(String key) { // 获得key1和key2
		String mainkey = key;
		mainkey = substitue(mainkey, P10);
		String Ls11 = mainkey.substring(0, 5);// substring包括起始不包括结束
		Ls11 = move(Ls11, 1);// 移位后
		String Ls12 = mainkey.substring(5, 10);
		Ls12 = move(Ls12, 1);// 移位后
		key1 = Ls11 + Ls12;
		key1 = substitue(key1, P8);
		// System.out.println("key1= " + key1);
		String Ls21 = move(Ls11, 1);
		String Ls22 = move(Ls12, 1);
		key2 = Ls21 + Ls22;
		key2 = substitue(key2, P8);
		// System.out.println("key2= " + key2);
	}

	public static String move(String str, int n) { // 左移n位，移出放后面
		char[] ch = str.toCharArray();
		char[] copy_ch = new char[5];
		for (int i = 0; i < ch.length; i++) {
			int a = ((i - n) % ch.length);
			if (a < 0) {
				if (n == 1) {
					copy_ch[ch.length - 1] = ch[i];
				}
				if (n == 2) {
					if (i == 0) {
						copy_ch[ch.length - 2] = ch[i];
					} else {
						copy_ch[ch.length - 1] = ch[i];
					}

				}
			} else {
				copy_ch[a] = ch[i];
			}
		}
		return new String(copy_ch);
	}

	// 加密
	public String[] encrypt(String plaintextTranslation, String plaintextBinary, String key) {
		String[] results = new String[2];
		// 并将结果保存在results数组中
		// results[0] = 加密后的"密文翻译"
		// results[1] = 加密后的"密文"
		getkey(key);
		String plaintext = plaintextBinary;
		plaintext = substitue(plaintext, IP);
		String L0 = plaintext.substring(0, 4);
		String R0 = plaintext.substring(4, 8);
		String R0E = substitue(R0, EP);
		R0E = xor(R0E, key1);
		String S1 = R0E.substring(0, 4);
		String S2 = R0E.substring(4, 8);
		S1 = searchSbox(S1, 1);
		S2 = searchSbox(S2, 2);
		String SS = S1 + S2;
		String f1 = substitue(SS, P4);
		String L1 = R0;
		String R1 = xor(f1, L0);
		// 这里求出L1,R1
		// -----------------第二轮-------------
		String R11 = substitue(R1, EP);
		R11 = xor(R11, key2);
		S1 = R11.substring(0, 4);
		S2 = R11.substring(4, 8);
		S1 = searchSbox(S1, 1);
		S2 = searchSbox(S2, 2);
		SS = S1 + S2;
		String f2 = substitue(SS, P4);
		String L2 = xor(f2, L1);
		String R2 = R1;
		// 这里求出L2,R2
		String ciphertext = L2 + R2;
		ciphertext = substitue(ciphertext, IP_1);
		// System.out.println("密文: " + ciphertext);

		results[0] = String.valueOf((char) Integer.parseInt(ciphertext, 2));// 加密后的密文翻译
		results[1] = ciphertext;// 加密后的密文
		return results;
	}

	// 解密
	public String[] decrypt(String ciphertextTranslation, String ciphertextBinary, String key) {
		String[] results = new String[2];
		// results[0] = 解密后的"明文翻译"
		// results[1] = 解密后的"明文"

		getkey(key);

		String plaintext = "";

		// 将密文进行初始置换IP
		String ciphertext = substitue(ciphertextBinary, IP);

		// 分割成左半部分L0和右半部分R0
		String L0 = ciphertext.substring(0, 4);
		String R0 = ciphertext.substring(4, 8);

		// 解密的第一轮
		String R0E = substitue(R0, EP);
		R0E = xor(R0E, key2);
		String S1 = R0E.substring(0, 4);
		String S2 = R0E.substring(4, 8);
		S1 = searchSbox(S1, 1);
		S2 = searchSbox(S2, 2);
		String SS = S1 + S2;
		String f1 = substitue(SS, P4);
		String L1 = R0;
		String R1 = xor(f1, L0);

		// 解密的第二轮
		String R11 = substitue(R1, EP);
		R11 = xor(R11, key1);
		S1 = R11.substring(0, 4);
		S2 = R11.substring(4, 8);
		S1 = searchSbox(S1, 1);
		S2 = searchSbox(S2, 2);
		SS = S1 + S2;
		String f2 = substitue(SS, P4);
		String L2 = xor(f2, L1);
		String R2 = R1;

		// 合并左半部分和右半部分并进行IP逆置换
		plaintext = L2 + R2;
		plaintext = substitue(plaintext, IP_1);

		results[0] = results[0] = String.valueOf((char) Integer.parseInt(plaintext, 2));
		;
		results[1] = plaintext;
		return results;
	}

	public String encryptString(String input, String key) {
		StringBuilder encryptedString = new StringBuilder();
		for (char c : input.toCharArray()) {
			String binaryString = Integer.toBinaryString(c);
			while (binaryString.length() < 8) {// 补足8位
				binaryString = "0" + binaryString;
			}
			String[] encrypted = encrypt("", binaryString, key);
			encryptedString.append(encrypted[1]);
		}
		return encryptedString.toString();
	}

	public String decryptString(String input, String key) {
		StringBuilder decryptedString = new StringBuilder();
		for (int i = 0; i < input.length(); i += 8) {
			String block = input.substring(i, i + 8);
			String[] decrypted = decrypt("", block, key);
			decryptedString.append((char) Integer.parseInt(decrypted[1], 2));
		}
		return decryptedString.toString();
	}

	public static void main(String[] args) {
		SDES_GUI gui = new SDES_GUI();
	}

	// 构造函数
	public SDES_Algorithm() {

	}

}
