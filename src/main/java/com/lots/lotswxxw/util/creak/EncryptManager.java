package com.lots.lotswxxw.util.creak;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

/**
 * @author lots
 */
public class EncryptManager {
    private static final String TAG = "EncryptManager";
    private static volatile EncryptManager instance;
    private String appKey = "scb37537f85scxpcm59f7e318b9epa51";
    private Cipher cipher;
    private String encryptKey;
    private boolean isDebug = false;
    private byte[][] key_iv;
    private SecretKeySpec skeySpec;
    private static String p1 = "7205a6c3883caf95b52db5b534e12ec3";
    private static String p2 = "81d7beac44a86f4337f534ec93328370";
    /*public static String key = "f5675cbb7e8887a705a4e6c94823d842bdea9abd51c84498c6484bc9592f41ff";
    public static String iv = "f4e540360b85e9cd70330f50955ef83f";*/
    public EncryptManager() {
        try {
            this.cipher = Cipher.getInstance("AES/CFB/NoPadding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            this.cipher = null;
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
            this.cipher = null;
        }
    }

    public static EncryptManager getInstance() {
        if (instance == null) {
            synchronized (EncryptManager.class) {
                if (instance == null) {
                    instance = new EncryptManager();
                    instance.init(p1, p2);
                }
            }
        }
        return instance;
    }

    public void init(String str, String str2) {
        this.encryptKey = str;
        this.appKey = str2;
        try {
            this.key_iv = AesCfbUtil.EVP_BytesToKey(32, 16, (byte[]) null, str.getBytes("UTF-8"), 0);
            this.skeySpec = new SecretKeySpec(this.key_iv[0], "AES");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            this.skeySpec = null;
        } catch (Exception e2) {
            e2.printStackTrace();
            this.skeySpec = null;
        }
    }

    public String encrypt(String str) {
        logInfo(str);
        if (StringUtil.isEmpty(str) || this.cipher == null || this.skeySpec == null) {
            return null;
        }
        try {
            this.cipher.init(1, this.skeySpec);
            try {
                return AesCfbUtil.byte2hex(AesCfbUtil.byteMerger(this.cipher.getIV(), this.cipher.doFinal(str.getBytes("UTF-8"))));
            } catch (BadPaddingException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalBlockSizeException e2) {
                e2.printStackTrace();
                return null;
            } catch (UnsupportedEncodingException e3) {
                e3.printStackTrace();
                return null;
            }
        } catch (InvalidKeyException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public String decrypt(String str) {
        logInfo(str);
        if (StringUtil.isEmpty(str) || this.cipher == null || this.skeySpec == null) {
            return null;
        }
        byte[] hex2byte = AesCfbUtil.hex2byte(str);
        byte[] copyOfRange = Arrays.copyOfRange(hex2byte, 0, 16);
        byte[] copyOfRange2 = Arrays.copyOfRange(hex2byte, 16, hex2byte.length);
        try {
            this.cipher.init(2, this.skeySpec, new IvParameterSpec(copyOfRange));
            String str2 = new String(this.cipher.doFinal(copyOfRange2), "UTF-8");
            logInfo(str2);
            return str2;
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e2) {
            e2.printStackTrace();
            return null;
        } catch (BadPaddingException e3) {
            e3.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e4) {
            e4.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    public String getRequestJson(String str){
        JSONObject jSONObject = new JSONObject();
        String secondTime = TimeUtil.getSecondTime();
        String encrypt = encrypt(str);
        String md5 = getMd5(AesCfbUtil.getSHA256StrJava("data=" + encrypt + "&timestamp=" + secondTime + this.appKey));
        jSONObject.put("timestamp", secondTime);
        jSONObject.put("data", encrypt);
        jSONObject.put("sign", md5);
        return jSONObject.toString();
    }

    public String getReusltJson(String str) {
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject == null) {
            return null;
        }
        String optString = jSONObject.optString("data");
        if (StringUtil.isEmpty(optString)) {
            return null;
        }
        return decrypt(optString);
    }

    private String getMd5(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        logInfo(str);
        return MD5Util.getMD5(str);
    }

    public boolean isCanEncrypt() {
        return !StringUtil.isEmpty(this.encryptKey) && !StringUtil.isEmpty(this.appKey);
    }

    private void logInfo(String str) {
//        PrintStream printStream = System.out;
//        printStream.print(TAG + " log=" + str);
    }

    public static void main(String[] args) {

        EncryptManager em= new EncryptManager();
        String data2 = "D3E55FD1566946297DF487D020FD6F9F5747798A040AF6C3D96E7F33CC7CFDA82A20A7E81783580BB2879F84EEFDE8F434AE4DAD4D6B519DDFD685AA02857925C0F8D7B804EC5E16A2F9D6653B4473B08396D96AB8D5324FE8EC69E2D0838E535F7748B23BF60297577F925D640EA93D1F565861A55EC53209F20FAB98895F603DEBEA57188F1B0934A53A5A5D0B90B9A2FDC4A24354D2FEE2B27D5F7330D3507F221FFDB22707EBFCEF509C473A5B7B0C71B5AD277A874A341C3F19BAD01EEAEA2BB2D323777CCF8F9B50801BF6F2FB95D5D26EA84C5BE3417A9EADCA15015583FBD0241FB9631C228CF47746324F5905B60B149D6564BA1AF09964A7FF414DE2ED6747BB02C5A00396C6FCB7167E4EBB5FA5D8CA503B98EA574D3C34073A8A1EDDFEE24BC5D1E3E15588543254949D522A169D4256133C272DED3749D840E34E6CE9156A62A4C23C0443126F3BD114B969B41D14DA9C58F2240334A48FCD25FE54D4A57C7C24431263562E4644D5D3A6589CB9869FF3FFAA2D2BECB645156040283642F60E4D895A880A1A868F448A79DE22D8A10112E82197663A63F3AB19B042815D018B883F11E61DB8F5F65F01C0B92423FC2603747AFE23BC265C82AE5A8A21E26648E1BB8E8A22D026426826391A8ED4A3FB012565146A4EF728CB3EF75DACEF96A6D7A95CEAFB4BA7D7891A7D93E88CA1941B43BC409D9C1A1EB8898C956BE4B86E055184CE9A12F89B67D837356576031CE35548B785912F1BC6F5227353F21D6A887424A14A64339E3B07BAD4D0D40620565B0437F9E4DD83BB4501BA506902DD181F0AC6991FC93494105A47678FFE8983D7B1B176AA331BAFA61DCA994246A8862AD14BAB5668FA9854938C337DC8B10C0B5684C60476FCF7BBF2A5B4F8378AEB36B4E59E7F7FB7DF300615338896BA29EF7E86DD42977DB813FED453D459DE02E13EF42A54C7828D681A698F7ED6C67B1F884BAC57D7B7BF53290E75D5C92B63E21AF7144E999DEF5AAC1BBB29768E89FBA11505B30B912FBD7B6790D1D7BF8C7D58955207C09348560CF6457F7B52FFBBDA10211794208AE0A75827C70D215E293CA169266FF784A5824A20FDEB2E4774CECD764DFE6F58DE6FD28D73C26C6C3B23FD70CEE90F9791EB13019287F12FF35EF0CB4A9BE34089A97EB4B864A37D57278526BEB58560586165B7C7F2F67E131D3B9E216BC022E42B04AFDD49ED1EDAE15AE7AECB914505FF6309561A11D82788C71A6EEC9DAB4B50905F145D9375DFD07C8A61F78EA12766A6CA857C572B73A50B97B427AD9E854C227780AD9226CED40F454931284F029D6113C26D192B3FF8104F2634A0E49BF0A4DB9B1E416CBB86A2C5A2984C8E1AC21D80AB9C38E8DDBBCF049AD51CA01570FE76E92B35906FC94E0C9F49FC51100BE76CD4CC5EE3D074633EF982E8C6673CE6F6398CA8D0300D7D53BBFF705BEAE71BA951D3B2741BAE0A8DAD8F35810B6FDC422254C0DC7B83094D59C9EFC5BCE719E34FAB0CBFB2770845FC01D8237098A4B9B87A1A027DFA1FBA9F13BF1D7C2815ED3B6C079F0E55E58F48D34131AB05AA8FDA90AE762C8B3C33FDEC31984420FB1E94462F4C566B483F4DB1176BFC773C51361DC23073B8D8362676C699B93C93AD06D645880D66A1261CE7A2A1CF52C0111D1E3EC23F1E263DE07DA26811519E753A4C1E1426BE545261B53518306EA1FAF37A17D810DC287FE7D9980741517DBAAA41B1A66F2A3D61FC19597E4A75631268B3746A50C31C63585762E201E2F16A248CB82FAB513885EA77AC266854289B3380E9FCB6CAA772CA924A42FC10F55BAB4DF9E4A46850E9E242B856CCD1D1BBF5AAC690C2CA58AF18CEE183CA6268460D8C351A88E7CF3AAF57CC9079686C1BD980489828837A5E0F61EE83584C18AAA22A0BED4B45B2E5F1450678AB365C857E7F837656E12C01C6006E6BBDEA8D6484C6E2FB091B1E7D7A516F8083511743997422CEA49E2E9B486D879F44F876DB304C5EC3869156F39E5894150727B5E09F8A2EBDE9013E31D0E1E9E0ED82";
        em.init(p1,p2);
        final String decrypt2 = em.decrypt(data2);
        System.out.println(decrypt2);
        System.out.println(em.getRequestJson(decrypt2));
    }
}