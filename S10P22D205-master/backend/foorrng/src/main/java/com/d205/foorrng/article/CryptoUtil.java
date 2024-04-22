package com.d205.foorrng.article;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CryptoUtil {
    private static final Logger LOGGER = Logger.getLogger(CryptoUtil.class.getName());
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final byte[] KEY = "MySuperSecretKey".getBytes(); // Use a secure way to generate/store key

    public static String encrypt(String value) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM); //AES 알고리즘을 사용하고 사이즈는 128 로 들어간다.
        keyGenerator.init(128);

        SecretKey secretKey = new SecretKeySpec(KEY, ALGORITHM); //시크릿 키에 대해 생성자를 생성한다.
        //이 키는 보안되어야 한다.

        Cipher cipher = Cipher.getInstance(TRANSFORMATION); //Cipher 클래스에서 블록을 나누고 처음은 iv 백터로 xor연산 후에는 전의 블록과 xor연산을 진행한다.
        byte[] iv = new byte[cipher.getBlockSize()];
        new SecureRandom().nextBytes(iv); //iv 배열을 채우는 임의의 바이트 시퀀스를 생성한다.
        IvParameterSpec ivParams = new IvParameterSpec(iv); //동일 텍스트에 대해 동일한 암호문으로 암호화되지 않는다.
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams); // 이 부분에서 ivParam과 xor 연산을 실행한다. 이때 secretKey로 암호화를 한 번 더 진행한다.

        byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8")); //여기서 ivParam에 대한 값을 Value 바이트 배열에 저장한다.
        byte[] encryptedValueWithIv = new byte[iv.length + encryptedByteValue.length]; //복호화를 위한 WithIV값을 DB 테이블 저장 맨 앞에다가 둔다.
        System.arraycopy(iv, 0, encryptedValueWithIv, 0, iv.length);//앞쪽엔 IV를
        System.arraycopy(encryptedByteValue, 0, encryptedValueWithIv, iv.length, encryptedByteValue.length);//뒤쪽엔 안호화된 value를 붙인디.
        //최종결과인 encryptedValueWithIv를 리턴한다.
        return Base64.getEncoder().encodeToString(encryptedValueWithIv);
    }

    public static String decrypt(String value) throws Exception {

        byte[] decodedValue = Base64.getDecoder().decode(value);
        //인코딩된 문지열을 바이트 배열로 디코딩한다.

        SecretKey secretKey = new SecretKeySpec(KEY, ALGORITHM);
        //시크릿키와 알고리즘을 통해 시크릿키 객체를 생성한다.

        Cipher cipher = Cipher.getInstance(TRANSFORMATION); //CBC 모드를 사용하고 PKCS#5 패딩을 적용하여 cipher를 초기화한다.

        byte[] iv = new byte[cipher.getBlockSize()];
        //TRANSFORMATION에 대한 CIPHER 블록 사이즈를 빼온다.

        System.arraycopy(decodedValue, 0, iv, 0, iv.length);//iv 배열로 16바이트 만큼 새 바이트 배열을 생성한다.
        //정확히는 iv.length만큼

        IvParameterSpec ivParams = new IvParameterSpec(iv); // 추출된 iv를 이용하여 ParameterSpec을 생성한다.
        //이 친구는 복호화 과정에서 Cipher 객체를 초기화할 때 사용된다.

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);//복호화를 위한 cipher 초기화

        byte[] decryptedValue = cipher.doFinal(decodedValue, iv.length, decodedValue.length - iv.length);
        //디코딩된 전체 배열, iv를 제외한 데이터 시작 위치, 암호화된 데이터의 길이 지정
        //-> 복호화된 바이트 배열을 반환.

        return new String(decryptedValue, "utf-8");
    }
}
//    private static final String ALGORITHM = "AES";
//    private static final byte[] KEY = "MySuperSecretKey".getBytes(); // Use a secure way to generate/store key
//
//    public static String encrypt(String value) throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
//        keyGenerator.init(128);
//        SecretKey secretKey = new SecretKeySpec(KEY, ALGORITHM);
//
//        Cipher cipher = Cipher.getInstance(ALGORITHM);
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//
//        byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
//        return Base64.getEncoder().encodeToString(encryptedByteValue);
//    }
//
//    public static String decrypt(String value) throws Exception {
//        SecretKey secretKey = new SecretKeySpec(KEY, ALGORITHM);
//
//        Cipher cipher = Cipher.getInstance(ALGORITHM);
//        cipher.init(Cipher.DECRYPT_MODE, secretKey);
//
//        byte[] decryptedValue64 = Base64.getDecoder().decode(value);
//        byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);
//        return new String(decryptedByteValue, "utf-8");
//    }

//    public static String decrypt(String value) throws Exception {
//        LOGGER.log(Level.INFO, "Starting decryption");
//
//        byte[] decodedValue = Base64.getDecoder().decode(value);
//        LOGGER.log(Level.INFO, decodedValue+"Decoded value from Base64");
//
//        LOGGER.log(Level.INFO, Arrays.toString(decodedValue)+" ");
//
//
//        SecretKey secretKey = new SecretKeySpec(KEY, ALGORITHM);
//        LOGGER.log(Level.INFO, secretKey+"Secret key created");
//
//        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
//        LOGGER.log(Level.INFO, cipher+"Cipher instance created with transformation: " + TRANSFORMATION);
//
//        byte[] iv = new byte[cipher.getBlockSize()];
//        System.arraycopy(decodedValue, 0, iv, 0, iv.length);
//
//        System.out.println("z");
//        LOGGER.log(Level.INFO, Arrays.toString(iv)+" ");
//
//        System.out.println("z");
//
//        IvParameterSpec ivParams = new IvParameterSpec(iv);
//        LOGGER.log(Level.INFO, ivParams.toString());
//
//        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);
//        LOGGER.log(Level.INFO, "Cipher initialized for decryption");
//
//        byte[] decryptedValue = cipher.doFinal(decodedValue, iv.length, decodedValue.length - iv.length);
//        LOGGER.log(Level.INFO, "Decryption completed");
//
//        String result = new String(decryptedValue, "utf-8");
//        LOGGER.log(Level.INFO, "Decrypted string: " + result);
//
//        return result;
//    }
//}
