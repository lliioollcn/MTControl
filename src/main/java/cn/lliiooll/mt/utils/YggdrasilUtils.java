package cn.lliiooll.mt.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import cn.lliiooll.mt.beans.yggdrasil.TexturesBean;
import lombok.SneakyThrows;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.util.UUID;

public class YggdrasilUtils {

    public static final String URL = "http://localhost:8080";
    public static String publicKey = "";
    public static String privateKey = "";
    public static KeyPair pair = null;

    @SneakyThrows
    public static void init() {
        KeyPair pair = SecureUtil.generateKeyPair("RSA", 4096);
        YggdrasilUtils.pair = pair;
        ByteArrayOutputStream pubOut = new ByteArrayOutputStream();
        ByteArrayOutputStream priOut = new ByteArrayOutputStream();
        PemObject pubObj = new PemObject("PUBLIC KEY", pair.getPublic().getEncoded());
        PemObject priObj = new PemObject("PRIVATE KEY", pair.getPrivate().getEncoded());
        PemWriter pubW = new PemWriter(new OutputStreamWriter(pubOut));
        PemWriter priW = new PemWriter(new OutputStreamWriter(priOut));
        pubW.writeObject(pubObj);
        priW.writeObject(priObj);
        pubW.close();
        priW.close();
        publicKey = pubOut.toString();
        privateKey = priOut.toString();
        /*
        System.out.println("===========================================");
        System.out.println("pub: " + Arrays.toString(pubOut.toByteArray()));
        System.out.println("pri: " + Arrays.toString(priOut.toByteArray()));
        System.out.println("===========================================");
        System.out.println("pubEncoded: " + Arrays.toString(pair.getPublic().getEncoded()));
        System.out.println("priEncoded: " + Arrays.toString(pair.getPrivate().getEncoded()));
        System.out.println("===========================================");
        System.out.println("pubBase64: " + Base64.encode(pair.getPublic().getEncoded()));
        System.out.println("priBase64: " + Base64.encode(pair.getPrivate().getEncoded()));
        System.out.println("===========================================");
        System.out.println("");
        System.out.println(publicKey);
        System.out.println("");
        System.out.println("===========================================");
        System.out.println("");
        System.out.println(privateKey);
        System.out.println("");
         */
    }

    public static String genAccessToken(String username, String clientToken) {
        return SecureUtil.sha1(username + RandomUtil.randomString(5) + clientToken);
    }

    public static String genUuid(String username) {
        return UUID.nameUUIDFromBytes(("MTPlayers(" + username + ")@" + username).getBytes(StandardCharsets.UTF_8)).toString().replaceAll("-", "");
    }

    public static String genClientToken() {
        return SecureUtil.sha1(RandomUtil.randomString(10));
    }

    public static String sign(Object obj) {
        return Base64.encode(SecureUtil
                .sign(SignAlgorithm.SHA1withRSA, pair.getPrivate().getEncoded(), null)
                .sign(obj.toString());
    }

    public enum TexturesType {
        SKIN
    }

    public static String textureHash(BufferedImage img) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        int width = img.getWidth();
        int height = img.getHeight();
        byte[] buf = new byte[4096];

        putInt(buf, 0, width); // 0~3: width(big-endian)
        putInt(buf, 4, height); // 4~7: height(big-endian)
        int pos = 8;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // pos+0: alpha
                // pos+1: red
                // pos+2: green
                // pos+3: blue
                putInt(buf, pos, img.getRGB(x, y));
                if (buf[pos + 0] == 0) {
                    // the pixel is transparent
                    buf[pos + 1] = buf[pos + 2] = buf[pos + 3] = 0;
                }
                pos += 4;
                if (pos == buf.length) {
                    // buffer is full
                    pos = 0;
                    digest.update(buf, 0, buf.length);
                }
            }
        }
        if (pos > 0) {
            // flush
            digest.update(buf, 0, pos);
        }

        byte[] sha256 = digest.digest();
        return String.format("%0" + (sha256.length << 1) + "x", new BigInteger(1, sha256)); // to hex
    }

    // put an int into the array in big-endian
    private static void putInt(byte[] array, int offset, int x) {
        array[offset + 0] = (byte) (x >> 24 & 0xff);
        array[offset + 1] = (byte) (x >> 16 & 0xff);
        array[offset + 2] = (byte) (x >> 8 & 0xff);
        array[offset + 3] = (byte) (x >> 0 & 0xff);
    }
}
