package com.zj.netty.utils;

import java.io.ByteArrayOutputStream;

public class HexStringUtils {

    private static final char[] DIGITS_HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    protected static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS_HEX[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_HEX[0x0F & data[i]];
        }
        return out;
    }

    protected static byte[] decodeHex(char[] data) {
        int len = data.length;
        if ((len & 0x01) != 0) {
            throw new RuntimeException("字符个数应该为偶数");
        }
        byte[] out = new byte[len >> 1];
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f |= toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }
        return out;
    }

    protected static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

    public static String toHexString(byte[] bs) {
        return new String(encodeHex(bs));
    }

    public static String toHexStringFormat(byte[] bs) {
        char[] chars = encodeHex(bs);
        String result = "";
        for (int i = 0; i < chars.length; i++) {
            result += chars[i];
            if (i % 2 != 0) {
                result += " ";
            }
        }
        return result;
    }

    public static String hexString2Bytes(String hex) {
        return new String(decodeHex(hex.toCharArray()));
    }

    public static byte[] chars2Bytes(char[] bs) {
        return decodeHex(bs);
    }

    public static void main(String[] args) {
        String s = "7E080123E8014778494869002F00270002DCE7159B7378F37032ABE95568A28A5029C052D145266826929CA8CC78156A2B6C0CB8AB0AA0714EA2968CD14946714504D2668CD19A426928CD141A4A334DCD2669334B485850A19BA0A9161F5E6A55403B53C2D2814EC52E28C714B8C519A4CD19A4CD04D377534BD34C94C2F9A6EE3499A28A2928A28A33494514525412739A80F1499A33451466928A28A28A28A28A28A28A28A28A28A28A28A5C53D6095C8DA8C73ED53A69B72FF00F2CF1C7526A74D1E43F7A40BFAD4E9A4C43EF3B1F6A952C6D90FDCE7D4D4CB1C29C855148658D7B8151B5DC6B9CB544D7F18E951BEA20745A900029C9D45599650460550B9BC58B2A3E67ACC965791B2E4934CA28A514A052D2D14941A4A50A58F02A78ADC93F3702ACAA05E9520A296814514519A4CD19A292905068A052504D213499A4CE4D1484D267D010669C118D489101DAA5094F0B4A052814B4514668CD2669375216A697A617A697A6E6909A2928A28A292968A4A28A28A4A0D40DD6A375E335151451451451451451451451451451451451454A96D3C98D913907A1C5594D2AE9BF8557D02A6ACA68BFF003D25FF00BE6A74D2ED5319524FB9A9D60B68B2563407D714A66893BA8A89AFA25EE3150BEA2BFC393F4A88DFB1E80D44F7929CF38A85A790F53519727A934D27341349D6B5F1934EC851542EAFB712911E3FBD5489A28C51462969734519A4CD2D1826A58E02DD7A5598E255ED5252D2D2D19A4A07BD29A4A4A3346690D14945277A33499C5266928A32281B8F414E58B3F7AA558C0ED520514EC52814B4B40E28A2933499A42DC534B530BF34D2E49A6E6933452514514514514514945140A29334521A89BAD34D44EB4CA28A28A28A28A28A28A28A28A28A912095CFCB1B1FC2AD45A55D3F55541FED1AB316887ACB28FA28AB29A5DA27552D8FEF1A9D12DE2FB91A2FD050D711A719A85AFD40C0E6A17BF3D00FD6ABBDF484F4FD6A16B891BAB1A899D88E49351EECD28623A528761DE8DC4D252514515AD24AB129663D2B36E6E9E727F857D2ABD2814BD293AD2E2969293346696940A9121663D2AC4702A8C9E4D4C07A52E2814B451451466933494668A4EF452034668CD349A33499A29334A159BDA9EB17AF26A50A314F0B4E029714B452D2668CD37349BA90BD30C82985E9B9A33499A28A28A28A3345251451476A2928A28A28A4A89BAD36908C8A89860D368A28A28A28A28A28A29402C70A093E8066ACC7A75DC9D222BFEF1C5598B4594F32CAABEA179356A3D1ED947CE5A4FAF1FCAAC25BDA41CA448A7D0171CD39AEA2418040FA542FA8463A1CFE1503EA2E7D02EA8155DEF266FE2351999DBAB1A4CD2519A630EF4DA2A33D68CD28C52D14945028A2595E57DCC734CA503D68FA514519A0D1CD284269EB0B1ED52A5B0E7E";
        String hex = toHexString(s.getBytes());
        String decode = hexString2Bytes(hex);
        System.out.println("原字符串:" + s);
        System.out.println("十六进制字符串:" + hex);
        System.out.println("还原:" + decode);
    }

    /**
     * @param hex
     * @return
     * @Description 十六进制字符串转字节数组
     * @Date 2018/6/23 17:42
     * @CreateBy wzj
     **/
    public static byte[] hexStr2Bytes(String hex) {
        if (hex == null || hex.trim().equals("")) {
            return new byte[0];
        }
        int l = hex.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = (byte) Integer
                    .valueOf(hex.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    /**
     * @param str
     * @return
     * @Description 十六进制转字节数组
     * @Date 2018/6/28 9:54
     * @CreateBy wzj
     **/
    public static byte[] hexStr2Bytes2(String str) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = str.getBytes();
        for (int i = 0; i < bytes.length; i += 2) {
            out.write(charToInt(bytes[i]) * 16 + charToInt(bytes[i + 1]));
        }

        return out.toByteArray();
    }

    private static int charToInt(byte ch) {
        int val = 0;
        if (ch >= 0x30 && ch <= 0x39) {
            val = ch - 0x30;
        } else if (ch >= 0x41 && ch <= 0x46) {
            val = ch - 0x41 + 10;
        }
        return val;
    }

}