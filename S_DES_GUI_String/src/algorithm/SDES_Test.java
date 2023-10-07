package algorithm;

import java.util.List;

public class SDES_Test {

	public static void main(String[] args) {
		boolean foundDifferentKeys = true; // 用于标记是否找到不同密钥加密为相同密文的情况

		for (int plaintextInt = 0; plaintextInt < 256; plaintextInt++) {
			String plaintextBinary = String.format("%8s", Integer.toBinaryString(plaintextInt)).replace(' ', '0');// 整数转二进制，左补0

			for (int cyphertextInt = 0; cyphertextInt < 256; cyphertextInt++) {
				String cyphertextBinary = String.format("%8s", Integer.toBinaryString(cyphertextInt)).replace(' ', '0');// 整数转二进制，左补0

				List<String> possibleKeys = FindKey.findKeysByBruteForce(plaintextBinary, cyphertextBinary);// possibleKeys存储所有可能的密钥

				if (possibleKeys.size() > 1) {
					foundDifferentKeys = true;
					break; // 如果改明密文对存在多个密钥就结束内层循环
				}

				if (possibleKeys.size() == 1) {
					foundDifferentKeys = false;
					System.out.println(
							"明文 " + plaintextBinary + " 只能使用密钥 " + possibleKeys.get(0) + " 加密为 " + cyphertextBinary);// 若存在只有一个密钥则只用取possibleKeys的第一个元素，结束内层循环
					break;
				}
			}
			if (!foundDifferentKeys) {
				break;
			} // 存在明密文对满足唯一密钥，结束外层循环
		}
		if (!foundDifferentKeys) {
			System.out.println("对应明文空间存在给定的明文分组Pn，不会出现选择不同的密钥Ki!= Kj加密得到相同密文Cn的情况");
		} else {
			System.out.println("对应明文空间任意给定的明文分组Pn，会出现选择不同的密钥Ki!= Kj加密得到相同密文Cn的情况");
		}
	}
}