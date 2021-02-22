package com.lots.lotswxxw.util;

import cn.hutool.core.codec.Base64;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;

public class HelloWorld {
    public static byte[] key = hexToByte("f5675cbb7e8887a705a4e6c94823d842bdea9abd51c84498c6484bc9592f41ff");

    public static byte[] iv = hexToByte("f4e540360b85e9cd70330f50955ef83f");

    public static String keyType= "AES/CFB/NoPadding";

    public static byte[] hexToByte(String hex) {//字节集_十六进制到字节集
        int m = 0, n = 0;
        int byteLen = hex.length() / 2;
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = Byte.valueOf((byte) intVal);
        }
        return ret;
    }

    public static String decry(String data) {

        try {
            Cipher instance = Cipher.getInstance(keyType);
            byte enc[] = hexToByte(data);
            instance.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            String ret = new String(instance.doFinal(enc));
            System.out.println(ret);
            return ret;
        } catch (Exception e) {
        }
        return "解密报错";
    }

    public static String encry(String data) {

        try {
            Cipher instance = Cipher.getInstance(keyType);

            instance.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            String b = new String (instance.doFinal(data.getBytes()));
            System.out.println(b);
        }catch (Exception e){
            //Base64、HEX等编解码
            System.out.println(e.getMessage());
        }
        return "加密错误";
    }

    /**
     * 将解密返回的数据转换成 String 类型
     * @return
     * @throws Exception
     */
    public static String decrypt(String data) throws Exception {
        // stringToBase64() 将 Base64编码的字符串转换成 byte[] !!!与base64ToString(）配套使用
        try {
            Cipher cipher = Cipher.getInstance(keyType);
            cipher.init(Cipher.DECRYPT_MODE,new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            byte[] result = cipher.doFinal(hexToByte(data));
            return new String(result);
        } catch (Exception e) {
            System.out.println("exception:"+e.toString());
        }
        return null;
    }


    public static void main(String[] args) throws Exception {
        String date = "f4e540360b85e9cd70330f50955ef83fac16d29a4e7d6784b976d643dcdbedb6d90e8a59661354f1daadc690ae9acd1ed8959fa0432d5ddb33e6cf5ef4b5000bce1cdb41d578f22b3acec849eab2cfa2f6949791cdf9603960bc63cf246716f58a1e78048516cd29b7eac05ad8b05aacb7400f0fb0223a69fd599c067be20a07404be741b8edae2cdaace70d0582fc4c4ac9cfbeb4b89a66e7e7e5d1233e0cd37432160d65cc0256b8108c3c9bb033aae2b04b219366b64e615484d5a6662530a5895c1c1cc559c1b373761f9aab801d450a65209e332e51be63db69f7c14351fce877dd6b608ece7710713014b86ad66e86cf673c476690b42d9283b4521ad59ab73e59bc8c0e11a9cb97f9f16ead01686ca20f0e6347da3e705b80f44ed080c7ae3d4c399a2c8f890e06f83ef80b3e2f7361a8cce7f01428d8b091b9af8449839cf26b24fbaa4c3c2bfc049d675a26f9b71d1ff9e1a70eebbc8d790ef5131bf698e0a6d04443bc48bfb1b1b6bdd0902289a5290cca4f5a797d4495386b1a448f41910e82d36099e128584ea5b73b4d31405f9d3f3cb70e2eb24041fc302c832d5c40153e781059c1e6e4932a3acfb107472e55091c6e9ca85f82ebf28d570c5c6bb5290987f8e5c73492f4a56ce6ebe17eb7f1e4ed742230143434c7dc4f6c412594ae20480177b00a7e73891ceed000df336b5b042c46ee9ecb746f067277a00f42b33308757ae94679a73dd53732c3ac0f3c45fc7b62b9b5533de582f2808b1e12df7651d499126bfb2968b04a2e23389a41bcb554183314acb5b4a94dfae9940902a5a6b09000a545fba5637bbfe586d1fe1e74c5d12d85de4fe917345387b50e270e77399ec12f51db8b8fd5cde3ae1023b4cdd76318b26e17dd6d8f93cf60f2ecc02ad659ccbb6d829a2822c18b5f6c8180980943b3957ebd13e4ae1e12d4866c378f75aaa52ef06bf486f6fc009cc65432c40d81b0418b908ee5ee9f4ea18205151f95234362ed18c0ef87b5d3ddb6623dc69b7c68272e7d5432ee2abf7984dfb3829865fe00ac7d944e55601541331c87b10090b61231f07cdfdb76e72dc7565899aee9eeead9befab21ec75fad725e7ac23d120cf4a2a3c70e665224b90f7524c0a899c5317d8ef415d39e778547485c9d8428c58a2d9ecf5189eb9f1bc15aa4ee0b34da111297583407255547f4e4dd6141aabbd5f2080ddac37c64dab8538df6dc122b3d1dc6e73e3b803e84177707debec22517be42a75882d73ca3f39ae1771f4e8e1486a251a16a02ebb7c8cc44e67c74e6a700e40dec539def9137a97e5675fb634ea2dc25d94353968f97163841b726e96f3d3124994e090196bb3ad1b0602d8359cc0fb73d9361c2f37610af294aadbd455e6956847e986d368962bbb07652ee9fbf91e719804c694520631b0b464d47f876a9531ad11afb42f25eee9694700624479d7fcc7f7901530564d35614a9d390b53b53703b6be41a539e6d2c88aa16fa90bfa8d4fc8119a7d9f97f3c56310618fb77fdb5978ad45a2865ae774176ebf523d03c66b85b32e1403e849020e68c4480c1b10687a9afaad08b307518beefa04f1eab71656ac174e9051d9dbde77a4dae68684e2e6a04896e92fcc9ce78335ddc1a7a1b9e8c784778d7aaaabfd9377c72f22e8cbf4be966007440bbc7eaf0b5d66e8db5f20721a7ddd13d453e4d9a214bdc164cc11a37d430763e01f8c6e8d32b8d3d85b156180b023c6abb80039e53f1737d570266deae0a0f6d89f2a4e85436364244a463ee7399ad9662850d625be194717507fdfccbac17d9b877c757e70673905dceb805d9f1f4ac191cb720309a28b6dbc9edb94b1d9fd4c9fef968c528bc0dc2949bb22fc98b0f2f691d45a2954faf399b672710b73dbd02d6cc406b853630323757f9b44548e7172c47a2e72527afd7a696d7300e51b56894eaaf2cdc628907315893986275b5cb73e3e2a9e4875532f320572333823d057b29e14f466509a80d155c108c7addc82caf98bad13d10c876cc9ef9673531bbde89265291bd58d83dcf710db3a9d23b1c283638fc8870e67787f4f92dc0a962fba85ecdfc723e245bae590e7af17aeaf2f76fdec2c97f550cb5653068c941122fee78f719b2f58b0c1197c675b354be11d2a1beea870d3b6011abe319ff8cd942be59867a54e5b6fc8a2496816298a340d680c537891552ea231a3a24497ee3f2933354fe14fbac50fd34ff2a293e7d4ca3a9489457cdcb3c72f03175441529d4b01a2137f6935f6908b948e9cc8e7007049175180ffbb6fbef96b57b3beff0fca49f90e372d82b1ecfd872e091806084cbf5e3e700f51ea2527121baea1812a209b10fe843afc0fcab5bc841f9f3f6699dcae2ac1d0cecded3ba752570d9791afeac34bf697c316bfcddc8f1be134ea9db8732812da9428bf5d210fb761b9b93d6052686acdde4e85b661cddd425727a5dc862779a2ca9858863a180dbdab76f66fd5b2aae745745145ae011ab8999e8def9b24baec063bfc05af61446f1c762e5660aa60e1bbad64525ac6927d65f67bf0192b8ff6fe6f55c593156221d5547e7388d65fbeafda359422467353f825692707fdce26fb5a69401f57e8f00837103916e4e86dfa0d7ded6e29fff7959579477e59bd88bb88b0b41fdf39fbe4c019466775f827b6ae224aec8caaa08cfd737d32ca64730d4fb6ea0e798be0fbe3da182e2d869b866c13475f808fe017f02d53d2115e7d434d9effa526a53f07f2bf76ffd910bad804bb7c8f6511e77a3053dc1ad3d605e397300169a81966ca412a5795adc2ed1a9cc50590d8296f8d5732b4047c350c5c32973605c28d9844268a2421879019bd11de6880e9c893d7a9f40c649b09a9b893bc7a14ec57b2a08d871b9038b0eda4e3cca7a52b87ae1b15743da6772a7bc58b67d1b1165e57635d718ca0be0f9de70baa82b63d9576e87156b0d1d6029b7a60674a5f5f51860fcce7ce5a81ca5e2b673389165916a5b6a789b14d6ff69428ca308918a2f5b4509feaf0f3a6de34aef959b23650b7e85de8628054f3cb98759649e7c9e781d8c9af59e1a23c5f51fbdb16dcb081bf09562aa146e361658a85e5498d9fb7175f785c5b5b73aa3ebd8382f7c0c3244518b76f141f0d0120e6cb9e3cff7cb6de6f318da3f821be4c376a7a57f446ca6c714dff882009b1d0800b0";
        final String decry = decry(date);
        System.out.println("-----------------------------");

        final String decrypt = decrypt(date);
        System.out.println(decrypt);

        System.out.println("-----------------------------");
        encry(decry);
    }
}