package com.example.myapplication.cipher;

import android.util.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * $desc$
 * 作   者 :彭付林
 * 邮   箱 :pengfl@kingchannels.com
 * 日   期 :2018/10/31
 * 描   述 :个人信息
 */
public class AESUtils {
    // 算法名称
    final String KEY_ALGORITHM = "AES";
    // 加解密算法/模式/填充方式
    final String algorithmStr = "AES/ECB/PKCS7Padding";
    /**密钥长度*/
    private static final int KEY_LENGTH = 16;
    /**默认填充位数*/
    private static final String DEFAULT_VALUE = "0";
    //
    private Key key;
    private Cipher cipher;
    boolean isInited = false;

    byte[] iv = {0x30, 0x31, 0x30, 0x32, 0x30, 0x33, 0x30, 0x34, 0x30, 0x35, 0x30, 0x36, 0x30, 0x37, 0x30, 0x38};

    public void init(byte[] keyBytes) {

        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int KEY_LENGTH = 16;
        if (keyBytes.length % KEY_LENGTH != 0) {
            int groups = keyBytes.length / KEY_LENGTH + (keyBytes.length % KEY_LENGTH != 0 ? 1 : 0);
            byte[] temp = new byte[groups * KEY_LENGTH];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            cipher = Cipher.getInstance(algorithmStr, "BC");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 加密方法
     *
     * @param content  要加密的字符串
     * @param keyBytes 加密密钥
     * @return
     */
    public byte[] encrypt(byte[] content, byte[] keyBytes) {
        byte[] encryptedText = null;
        init(keyBytes);
        System.out.println("IV：" + new String(iv));
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            encryptedText = cipher.doFinal(content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encryptedText;
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @return
     */
    public byte[] decrypt(byte[] encryptedData, byte[] keyBytes) {
        byte[] encryptedText = null;
        init(keyBytes);
//        System.out.println("IV：" + new String(iv));
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encryptedText;
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @return
     */
    public String decrypt(String encryptedData, byte[] keyBytes) {
        byte[] encryptedText = null;
        String originalString = null;
        init(keyBytes);
//        System.out.println("IV：" + new String(iv));
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encrypted1 = Base64.decode(encryptedData, Base64.DEFAULT);// 先用base64解密
            encryptedText = cipher.doFinal(encrypted1);
            originalString = new String(encryptedText, "utf-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return originalString;
    }

    /**
     * 解密方法
     *
     * @param path     要解密的文件路径
     * @param keyBytes 解密密钥
     * @return
     */
    public byte[] decryptWithPath(String path, byte[] keyBytes) {
        byte[] encryptedText = null;
        init(keyBytes);
//        System.out.println("IV：" + new String(iv));
        ByteArrayOutputStream out = null;
        FileInputStream in = null;
        CipherOutputStream cout = null;
        byte[] filecontent;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
//            File file = new File(path);
//            Long filelength = file.length();
            in = new FileInputStream(path);
            int fileSize = in.available();
            filecontent = new byte[fileSize];
            out = new ByteArrayOutputStream(fileSize);
            cout = new CipherOutputStream(out, cipher);
            in.read(filecontent, 0, filecontent.length);
            cout.write(filecontent, 0, fileSize);
            filecontent = out.toByteArray();
            return filecontent;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (cout != null) {
                    cout.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        try {
//            /*cipher.init(Cipher.DECRYPT_MODE, key);*/
////            encryptedText = cipher.doFinal(encryptedData);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return encryptedText;
    }

    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String MD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 加密
     * @param key 密钥
     * @param src 加密文本
     * @return 加密后的文本
     * @throws Exception
     */
    public static String encrypt(String key, String src) throws Exception {
        // 对源数据进行Base64编码
        src = Base64.encodeToString(src.getBytes(), Base64.DEFAULT);
        // 补全KEY为16位
        byte[] rawKey = toMakeKey(key, KEY_LENGTH, DEFAULT_VALUE).getBytes();
        // 获取加密后的字节数组
        byte[] result = getBytes(rawKey, src.getBytes("utf-8"), Cipher.ENCRYPT_MODE);
        // 对加密后的字节数组进行Base64编码
        result = Base64.encode(result, Base64.DEFAULT);
        // 返回字符串
        return new String(result, Charset.defaultCharset());
    }

    /**
     * 解密
     * @param key 密钥
     * @param encrypted 待解密文本
     * @return 返回解密后的数据
     * @throws Exception
     */
    public static String decrypt(String key, String encrypted) throws Exception {
        // 补全KEY为16位
        byte[] rawKey = toMakeKey(key, KEY_LENGTH, DEFAULT_VALUE).getBytes();
        // 获取加密后的二进制字节数组
        byte[] enc = encrypted.getBytes(Charset.defaultCharset());
        // 对二进制数组进行Base64解码
        enc = Base64.decode(enc, Base64.DEFAULT);
        // 获取解密后的二进制字节数组
        byte[] result = getBytes(rawKey, enc, Cipher.DECRYPT_MODE);
        // 对解密后的二进制数组进行Base64解码
        result = Base64.decode(result, Base64.DEFAULT);
        // 返回字符串
        return new String(result, "utf-8");
    }

    /**
     * 密钥key ,默认补的数字，补全16位数，以保证安全补全至少16位长度,android和ios对接通过
     * @param key 密钥key
     * @param length 密钥应有的长度
     * @param text 默认补的文本
     * @return 密钥
     */
    private static String toMakeKey(String key, int length, String text) {
        // 获取密钥长度
        int strLen = key.length();
        // 判断长度是否小于应有的长度
        if (strLen < length) {
            // 补全位数
            StringBuilder builder = new StringBuilder();
            // 将key添加至builder中
            builder.append(key);
            // 遍历添加默认文本
            for (int i = 0; i < length - strLen; i++) {
                builder.append(text);
            }
            // 赋值
            key = builder.toString();
        }
        return key;
    }

    /**
     * 加解密过程
     * 1. 通过密钥得到一个密钥专用的对象SecretKeySpec
     * 2. Cipher 加密算法，加密模式和填充方式三部分或指定加密算 (可以只用写算法然后用默认的其他方式)Cipher.getInstance("AES");
     * @param key 二进制密钥数组
     * @param src 加解密的源二进制数据
     * @param mode 模式，加密为：Cipher.ENCRYPT_MODE;解密为：Cipher.DECRYPT_MODE
     * @return 加解密后的二进制数组
     * @throws NoSuchAlgorithmException 无效算法
     * @throws NoSuchPaddingException 无效填充
     * @throws InvalidKeyException 无效KEY
     * @throws InvalidAlgorithmParameterException 无效密钥
     * @throws IllegalBlockSizeException 非法块字节
     * @throws BadPaddingException 坏数据
     */
    private static byte[] getBytes(byte[] key, byte[] src, int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        // 密钥规格
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        // 密钥实例
        Cipher cipher = Cipher.getInstance("AES");
        // 初始化密钥模式
        cipher.init(mode, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        // 加密数据
        return cipher.doFinal(src);
    }
}
