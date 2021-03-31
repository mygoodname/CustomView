package com.example.myapplication.cipher;

import android.util.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Aes加密工具类
 * 使用：
 *          val value = AESUtil.encrypt("mazaiting", "123456789")
 Log.e("MainActivity", value)
 Log.e("MainActivity", AESUtil.decrypt("mazaiting", value))
 *
 * Created by mazaiting on 2018/6/21.
 */

public class AESUtil {
    /**16进制数*/
    private final static String HEX = "0123456789ABCDEF";
    /**密钥长度*/
    private static final int KEY_LENGTH = 16;
    /**默认填充位数*/
    private static final String DEFAULT_VALUE = "0";
    /**加密方式**/
    private static final String KEY_ALGORITHM="AES";
    /**字符编码**/
    private static final String CHARSETNAME="UTF-8";
    /**加解密算法/模式/填充方式**/
    private static final String algorithmStr = "AES/ECB/PKCS7Padding";

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
        byte[] result = getBytes(rawKey, src.getBytes(CHARSETNAME),KEY_ALGORITHM, Cipher.ENCRYPT_MODE);
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
        byte[] result = getBytes(rawKey, enc,KEY_ALGORITHM, Cipher.DECRYPT_MODE);
        // 对解密后的二进制数组进行Base64解码
        result = Base64.decode(result, Base64.DEFAULT);
        // 返回字符串
        return new String(result, CHARSETNAME);
    }

    /**
     * 加密
     * @param key 密钥
     * @param src 加密文本
     * @return 加密后的数据
     * @throws Exception
     */
    public static String encrypt2Java(String key, String src) throws Exception {
        // /src = Base64.encodeToString(src.getBytes(), Base64.DEFAULT);
        byte[] rawKey = toMakeKey(key, KEY_LENGTH, DEFAULT_VALUE).getBytes();// key.getBytes();
//    byte[] result = encrypt2Java(rawKey, src.getBytes("utf-8"));
        byte[] result = getBytes(rawKey, src.getBytes("utf-8"), "AES/CBC/PKCS5Padding", Cipher.ENCRYPT_MODE);
        // result = Base64.encode(result, Base64.DEFAULT);
        return toHex(result);
    }

    /**
     * 加密
     * @param key 密钥
     * @param src 加密文本
     * @return 加密后的数据
     * @throws Exception
     */
    public static String decrypt2Java(String key, String src) throws Exception {
        // /src = Base64.encodeToString(src.getBytes(), Base64.DEFAULT);
        byte[] rawKey = toMakeKey(key, KEY_LENGTH, DEFAULT_VALUE).getBytes();// key.getBytes();
        byte[] result = getBytes(rawKey, src.getBytes("utf-8"), "AES/CBC/PKCS5Padding", Cipher.DECRYPT_MODE);
        // result = Base64.encode(result, Base64.DEFAULT);
        return toHex(result);
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
    private static byte[] getBytes(byte[] key, byte[] src,String transformation, int mode) throws
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        // 密钥规格
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, KEY_ALGORITHM);
        // 密钥实例
        Cipher cipher = Cipher.getInstance(transformation);
        //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        if (algorithmStr.contains("CBC")) {
            // 初始化密钥模式
            cipher.init(mode, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        }
        // 加密数据
        return cipher.doFinal(src);
    }

    /**获取16进制字符串*/
    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }
    /**将16进制字符串转换为未编码后的数据*/
    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    /**
     * 把16进制转化为字节数组
     * @param hexString 16进制字符串
     * @return 加密后的字节数组
     */
    private static byte[] toByte(String hexString) {
        // 获取源数据长度
        int len = hexString.length() / 2;
        // 创建字节数组
        byte[] result = new byte[len];
        // 遍历
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        // 返回二进制字节数组
        return result;
    }

    /**
     * 二进制转字符,转成了16进制
     * 0123456789abcdef
     * @param bytes 字节组数
     * @return 16进制编码的字符串
     */
    private static String toHex(byte[] bytes) {
        // 判断二进制数组长度是否小于0
        if (bytes.length <= 0) return "";
        // 创建字符串连接对象
        StringBuilder builder = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            // 拼接字符
            builder.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
        }
        // 返回字符串
        return builder.toString();
    }

    /**
     * 对文件进行AES加密
     * @param sourceFile 待加密文件
     * @param toFile 加密后的文件
     * @param dir 文件存储路径
     * @param key 密钥
     * @return 加密后的文件
     */
    public static File encryptFile(File sourceFile, String toFile, String dir, String key) {
        // 新建临时加密文件
        File encryptFile = null;
        // 输入流
        InputStream inputStream = null;
        // 输出流
        OutputStream outputStream = null;
        try {
            // 读取源文件，创建文件输入流
            inputStream = new FileInputStream(sourceFile);
            // 创建加密后的文件
            encryptFile = new File(dir + toFile);
            // 根据文件创建输出流
            outputStream = new FileOutputStream(encryptFile);
            // 初始化 Cipher
            Cipher cipher = initAESCipher(key, Cipher.ENCRYPT_MODE);
            // 以加密流写入文件
            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            // 创建缓存字节数组
            byte[] cache = new byte[1024];
            // 读取
            int len;
            // 读取加密并写入文件
            while ((len = cipherInputStream.read(cache)) != -1) {
                outputStream.write(cache, 0, len);
                outputStream.flush();
            }
            // 关闭加密输入流
            cipherInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(inputStream);
            closeStream(outputStream);
        }
        return encryptFile;
    }

    /**
     * AES方式解密文件
     * @param sourceFile 源文件
     * @param toFile 目标文件
     * @param dir 文件存储路径
     * @param key 密钥
     * @return
     */
    public static File decryptFile(File sourceFile, String toFile, String dir, String key) {
        // 解密文件
        File decryptFile = null;
        // 文件输入流
        InputStream inputStream = null;
        // 文件输出流
        OutputStream outputStream = null;
        try {
            // 创建解密文件
            decryptFile = new File(dir + toFile);
            // 初始化Cipher
            Cipher cipher = initAESCipher(key, Cipher.DECRYPT_MODE);
            // 根据源文件创建输入流
            inputStream = new FileInputStream(sourceFile);
            // 创建输出流
            outputStream = new FileOutputStream(decryptFile);
            // 获取解密输出流
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
            // 创建缓冲字节数组
            byte[] buffer = new byte[1024*2];
            int len;
            // 读取解密并写入
            while ((len = inputStream.read(buffer)) >= 0) {
                cipherOutputStream.write(buffer, 0, len);
                cipherOutputStream.flush();
            }
            // 关闭流
            cipherOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(inputStream);
            closeStream(outputStream);
        }
        return decryptFile;
    }

    /**
     * 初始化 AES Cipher
     * @param key 密钥
     * @param cipherMode 加密模式
     * @return 密钥
     */
    private static Cipher initAESCipher(String key, int cipherMode) {
        Cipher cipher = null;
        try {
            // 将KEY进行修正
            byte[] rawKey = toMakeKey(key, KEY_LENGTH, DEFAULT_VALUE).getBytes();
            // 创建密钥规格
            SecretKeySpec secretKeySpec = new SecretKeySpec(rawKey, KEY_ALGORITHM);
            if(algorithmStr.contains("PKCS7Padding")){
                // 初始化
                Security.addProvider(new BouncyCastleProvider());
                // 获取密钥
                cipher = Cipher.getInstance(algorithmStr,"BC");
            }else
            // 获取密钥
            cipher = Cipher.getInstance(KEY_ALGORITHM);
            if(algorithmStr.contains("CBC")){
                // 初始化
                cipher.init(cipherMode, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            }else//ECB模式
                cipher.init(cipherMode, secretKeySpec);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return cipher;
    }

    /**
     * 关闭流
     * @param closeable 实现Closeable接口
     */
    private static void closeStream(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
