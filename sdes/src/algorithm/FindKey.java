package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FindKey {

	public static List<String> findKeysByBruteForce(String plaintext, String ciphertext) {
		SDES_Algorithm sdes = new SDES_Algorithm();
		List<String> matchingKeys = new ArrayList<>();

		long startTime = System.currentTimeMillis();// 设置时间戳

		for (int i = 0; i < 1024; i++) {
			String key = Integer.toBinaryString(i);
			// 补足10位
			while (key.length() < 10) {
				key = "0" + key;
			}
			// System.out.print(key + "\n");
			String encryptedText = sdes.encryptString(plaintext, key);
			// System.out.print(encryptedText + "\n");
			if (encryptedText.equals(ciphertext)) {
				matchingKeys.add(key);
			}
		}

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("找出密钥所需的时间：" + totalTime + "ms");

		return matchingKeys;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("请输入明文：");
		String plaintext = scanner.nextLine();

		// 将二进制编码的plaintext转换为字符
		String decodedPlaintext = binaryToString(plaintext);

		plaintext = decodedPlaintext;

		System.out.print("请输入密文：");
		String ciphertext = scanner.nextLine();

		scanner.close();

		List<String> keys = findKeysByBruteForce(plaintext, ciphertext);

		if (keys.isEmpty()) {
			System.out.println("没有找到匹配的密钥。");
		} else {
			System.out.println("匹配的密钥有：");
			for (String key : keys) {
				System.out.println(key);
			}
		}
	}

	public static String binaryToString(String binaryText) {
		int decimalValue = Integer.parseInt(binaryText, 2);
		char character = (char) decimalValue;
		return Character.toString(character);
	}
}