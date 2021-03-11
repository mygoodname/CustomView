package com.example.myapplication.utils;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.telephony.PhoneNumberUtils;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.Html.FROM_HTML_MODE_COMPACT;

/**
 * Created by liuhc on 2016/11/30.
 * 描述：
 * Version:1.0
 */
public class StringUtils {

    public static String brStr = "frontierbr";
    public static String splitStr = "frontiersplit";

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0 || s.equals("null");
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0 || s.equals("null"));
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static String null2Length0(String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    private static int[] pyValue = new int[]{-20319, -20317, -20304, -20295, -20292, -20283, -20265, -20257, -20242,
            -20230, -20051, -20036, -20032,
            -20026, -20002, -19990, -19986, -19982, -19976, -19805, -19784, -19775, -19774, -19763, -19756, -19751,
            -19746, -19741, -19739, -19728,
            -19725, -19715, -19540, -19531, -19525, -19515, -19500, -19484, -19479, -19467, -19289, -19288, -19281,
            -19275, -19270, -19263, -19261,
            -19249, -19243, -19242, -19238, -19235, -19227, -19224, -19218, -19212, -19038, -19023, -19018, -19006,
            -19003, -18996, -18977, -18961,
            -18952, -18783, -18774, -18773, -18763, -18756, -18741, -18735, -18731, -18722, -18710, -18697, -18696,
            -18526, -18518, -18501, -18490,
            -18478, -18463, -18448, -18447, -18446, -18239, -18237, -18231, -18220, -18211, -18201, -18184, -18183,
            -18181, -18012, -17997, -17988,
            -17970, -17964, -17961, -17950, -17947, -17931, -17928, -17922, -17759, -17752, -17733, -17730, -17721,
            -17703, -17701, -17697, -17692,
            -17683, -17676, -17496, -17487, -17482, -17468, -17454, -17433, -17427, -17417, -17202, -17185, -16983,
            -16970, -16942, -16915, -16733,
            -16708, -16706, -16689, -16664, -16657, -16647, -16474, -16470, -16465, -16459, -16452, -16448, -16433,
            -16429, -16427, -16423, -16419,
            -16412, -16407, -16403, -16401, -16393, -16220, -16216, -16212, -16205, -16202, -16187, -16180, -16171,
            -16169, -16158, -16155, -15959,
            -15958, -15944, -15933, -15920, -15915, -15903, -15889, -15878, -15707, -15701, -15681, -15667, -15661,
            -15659, -15652, -15640, -15631,
            -15625, -15454, -15448, -15436, -15435, -15419, -15416, -15408, -15394, -15385, -15377, -15375, -15369,
            -15363, -15362, -15183, -15180,
            -15165, -15158, -15153, -15150, -15149, -15144, -15143, -15141, -15140, -15139, -15128, -15121, -15119,
            -15117, -15110, -15109, -14941,
            -14937, -14933, -14930, -14929, -14928, -14926, -14922, -14921, -14914, -14908, -14902, -14894, -14889,
            -14882, -14873, -14871, -14857,
            -14678, -14674, -14670, -14668, -14663, -14654, -14645, -14630, -14594, -14429, -14407, -14399, -14384,
            -14379, -14368, -14355, -14353,
            -14345, -14170, -14159, -14151, -14149, -14145, -14140, -14137, -14135, -14125, -14123, -14122, -14112,
            -14109, -14099, -14097, -14094,
            -14092, -14090, -14087, -14083, -13917, -13914, -13910, -13907, -13906, -13905, -13896, -13894, -13878,
            -13870, -13859, -13847, -13831,
            -13658, -13611, -13601, -13406, -13404, -13400, -13398, -13395, -13391, -13387, -13383, -13367, -13359,
            -13356, -13343, -13340, -13329,
            -13326, -13318, -13147, -13138, -13120, -13107, -13096, -13095, -13091, -13076, -13068, -13063, -13060,
            -12888, -12875, -12871, -12860,
            -12858, -12852, -12849, -12838, -12831, -12829, -12812, -12802, -12607, -12597, -12594, -12585, -12556,
            -12359, -12346, -12320, -12300,
            -12120, -12099, -12089, -12074, -12067, -12058, -12039, -11867, -11861, -11847, -11831, -11798, -11781,
            -11604, -11589, -11536, -11358,
            -11340, -11339, -11324, -11303, -11097, -11077, -11067, -11055, -11052, -11045, -11041, -11038, -11024,
            -11020, -11019, -11018, -11014,
            -10838, -10832, -10815, -10800, -10790, -10780, -10764, -10587, -10544, -10533, -10519, -10331, -10329,
            -10328, -10322, -10315, -10309,
            -10307, -10296, -10281, -10274, -10270, -10262, -10260, -10256, -10254};

    private static String[] pyStr = new String[]{"a", "ai", "an", "ang", "ao", "ba", "bai", "ban", "bang", "bao",
            "bei", "ben", "beng", "bi", "bian",
            "biao", "bie", "bin", "bing", "bo", "bu", "ca", "cai", "can", "cang", "cao", "ce", "ceng", "cha", "chai",
            "chan", "chang", "chao", "che",
            "chen", "cheng", "chi", "chong", "chou", "chu", "chuai", "chuan", "chuang", "chui", "chun", "chuo", "ci",
            "cong", "cou", "cu", "cuan",
            "cui", "cun", "cuo", "da", "dai", "dan", "dang", "dao", "de", "deng", "di", "dian", "diao", "die",
            "ding", "diu", "dong", "dou", "du",
            "duan", "dui", "dun", "duo", "e", "en", "er", "fa", "fan", "fang", "fei", "fen", "feng", "fo", "fou",
            "fu", "ga", "gai", "gan", "gang",
            "gao", "ge", "gei", "gen", "geng", "gong", "gou", "gu", "gua", "guai", "guan", "guang", "gui", "gun",
            "guo", "ha", "hai", "han", "hang",
            "hao", "he", "hei", "hen", "heng", "hong", "hou", "hu", "hua", "huai", "huan", "huang", "hui", "hun",
            "huo", "ji", "jia", "jian",
            "jiang", "jiao", "jie", "jin", "jing", "jiong", "jiu", "ju", "juan", "jue", "jun", "ka", "kai", "kan",
            "kang", "kao", "ke", "ken",
            "keng", "kong", "kou", "ku", "kua", "kuai", "kuan", "kuang", "kui", "kun", "kuo", "la", "lai", "lan",
            "lang", "lao", "le", "lei", "leng",
            "li", "lia", "lian", "liang", "liao", "lie", "lin", "ling", "liu", "long", "lou", "lu", "lv", "luan",
            "lue", "lun", "luo", "ma", "mai",
            "man", "mang", "mao", "me", "mei", "men", "meng", "mi", "mian", "miao", "mie", "min", "ming", "miu",
            "mo", "mou", "mu", "na", "nai",
            "nan", "nang", "nao", "ne", "nei", "nen", "neng", "ni", "nian", "niang", "niao", "nie", "nin", "ning",
            "niu", "nong", "nu", "nv", "nuan",
            "nue", "nuo", "o", "ou", "pa", "pai", "pan", "pang", "pao", "pei", "pen", "peng", "pi", "pian", "piao",
            "pie", "pin", "ping", "po", "pu",
            "qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing", "qiong", "qiu", "qu", "quan", "que", "qun",
            "ran", "rang", "rao", "re",
            "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui", "run", "ruo", "sa", "sai", "san", "sang", "sao",
            "se", "sen", "seng", "sha",
            "shai", "shan", "shang", "shao", "she", "shen", "sheng", "shi", "shou", "shu", "shua", "shuai", "shuan",
            "shuang", "shui", "shun",
            "shuo", "si", "song", "sou", "su", "suan", "sui", "sun", "suo", "ta", "tai", "tan", "tang", "tao", "te",
            "teng", "ti", "tian", "tiao",
            "tie", "ting", "tong", "tou", "tu", "tuan", "tui", "tun", "tuo", "wa", "wai", "wan", "wang", "wei",
            "wen", "weng", "wo", "wu", "xi",
            "xia", "xian", "xiang", "xiao", "xie", "xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "ya",
            "yan", "yang", "yao", "ye", "yi",
            "yin", "ying", "yo", "yong", "you", "yu", "yuan", "yue", "yun", "za", "zai", "zan", "zang", "zao", "ze",
            "zei", "zen", "zeng", "zha",
            "zhai", "zhan", "zhang", "zhao", "zhe", "zhen", "zheng", "zhi", "zhong", "zhou", "zhu", "zhua", "zhuai",
            "zhuan", "zhuang", "zhui",
            "zhun", "zhuo", "zi", "zong", "zou", "zu", "zuan", "zui", "zun", "zuo"};

    /**
     * 单个汉字转成ASCII码
     *
     * @param s 单个汉字字符串
     * @return 如果字符串长度是1返回的是对应的ascii码，否则返回-1
     */
    private static int oneCn2ASCII(String s) {
        if (s.length() != 1) return -1;
        int ascii = 0;
        try {
            byte[] bytes = s.getBytes("GB2312");
            if (bytes.length == 1) {
                ascii = bytes[0];
            } else if (bytes.length == 2) {
                int highByte = 256 + bytes[0];
                int lowByte = 256 + bytes[1];
                ascii = (256 * highByte + lowByte) - 256 * 256;
            } else {
                throw new IllegalArgumentException("Illegal resource string");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ascii;
    }

    /**
     * 单个汉字转成拼音
     *
     * @param s 单个汉字字符串
     * @return 如果字符串长度是1返回的是对应的拼音，否则返回{@code null}
     */
    private static String oneCn2PY(String s) {
        int ascii = oneCn2ASCII(s);
        if (ascii == -1) return null;
        String ret = null;
        if (0 <= ascii && ascii <= 127) {
            ret = String.valueOf((char) ascii);
        } else {
            for (int i = pyValue.length - 1; i >= 0; i--) {
                if (pyValue[i] <= ascii) {
                    ret = pyStr[i];
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * 获得第一个汉字首字母
     *
     * @param s 单个汉字字符串
     * @return 拼音
     */
    public static String getPYFirstLetter(String s) {
        if (isSpace(s)) return "";
        String first, py;
        first = s.substring(0, 1);
        py = oneCn2PY(first);
        if (py == null) return null;
        return py.substring(0, 1);
    }

    /**
     * 中文转拼音
     *
     * @param s 汉字字符串
     * @return 拼音
     */
    public static String cn2PY(String s) {
        String hz, py;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            hz = s.substring(i, i + 1);
            py = oneCn2PY(hz);
            if (py == null) {
                py = "?";
            }
            sb.append(py);
        }
        return sb.toString();
    }

    public static String filterStr(String input) {
        if (TextUtils.isEmpty(input)) {
            return "";
        }
        if (input.contains(" ")) {
            input = input.replace(" ", "");
        }
        return input.replaceAll("[\\t\\n\\r]", "");
    }

    public static String filterStr2BrStr(String input) {
        if (TextUtils.isEmpty(input)) {
            return "";
        }
        if (input.contains(" ")) {
            input = input.replace(" ", "");
        }
        return input.replaceAll("[\\t\\n\\r]", brStr);
    }

    public static String filterStr2Br(String input) {
        if (TextUtils.isEmpty(input)) {
            return "";
        }
        if (input.contains(" ")) {
            input = input.replace(" ", "");
        }
        return input.replaceAll("[\\t\\n\\r]", "</br>");
    }

    /**
     * 正则表达式
     *
     * @param html
     * @return
     */
    public static String trimHtmlTag(String html) {
        html = html.replaceAll("\\<head>[\\s\\S]*?</head>(?i)", "");//去掉head
        html = html.replaceAll("\\<!--[\\s\\S]*?-->", "");//去掉注释
        html = html.replaceAll("\\<![\\s\\S]*?>", "");
        html = html.replaceAll("\\<style[^>]*>[\\s\\S]*?</style>(?i)", "");//去掉样式
        html = html.replaceAll("\\<script[^>]*>[\\s\\S]*?</script>(?i)", "");//去掉js
        html = html.replaceAll("\\<w:[^>]+>[\\s\\S]*?</w:[^>]+>(?i)", "");//去掉word标签
        html = html.replaceAll("\\<img>[\\s\\S]*?</img>(?i)", "");
        html = html.replaceAll("\\<audio>[\\s\\S]*?</audio>(?i)", "");
        html = html.replaceAll("\\<video>[\\s\\S]*?</video>(?i)", "");
        html = html.replaceAll("\\<a>[\\s\\S]*?</a>(?i)", "");
        html = html.replaceAll("\\<xml>[\\s\\S]*?</xml>(?i)", "");
        html = html.replaceAll("\\<html[^>]*>|<body[^>]*>|</html>|</body>(?i)", "");
        html = html.replaceAll("\\\r\n|\n|\r", "");//去掉换行
        html = html.replaceAll("\\<br[^>]*>(?i)", "");
        html = html.replaceAll("\\</p>(?i)", "\n");
        html = html.replaceAll("\\<[^>]+>", "");
        html = html.replaceAll("\\ ", "");
        return html.trim();
    }

    /**
     * 判断中文乱码
     *
     * @param strName
     * @return
     */
    public static boolean isMessyCode(String strName) {
        try {
            Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
            Matcher m = p.matcher(strName);
            String after = m.replaceAll("");
            String temp = after.replaceAll("\\p{P}", "");
            char[] ch = temp.trim().toCharArray();

            int length = (ch != null) ? ch.length : 0;
            for (int i = 0; i < length; i++) {
                char c = ch[i];
                if (!Character.isLetterOrDigit(c)) {
                    String str = "" + ch[i];
                    if (!str.matches("[\u4e00-\u9fa5]+")) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static SpannableStringBuilder toColorValue(int colorId, String before, String value, String after) {
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder();
        spanBuilder.append(before);
        spanBuilder.append(value);
        spanBuilder.append(after);
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(colorId);
        spanBuilder.setSpan(blueSpan, before.length(), before.length() + value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanBuilder;
    }

    public static String[] convertNumerals(String regin) {
        String num = "";
        String[] reginnum;

        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(regin);
        num = m.replaceAll("").trim();

        regin = regin.replaceAll(num, "");
        regin = regin.replaceAll("年", "");
        regin = regin.replaceAll(" ", "");
        regin.trim();
        Log.i("lijiameng", "convertNumerals: |" + regin + "|");
        reginnum = new String[]{regin, num};
        return reginnum;
    }

    @SuppressWarnings("unused")
    public static String[] chineseNumber2Int(String chineseNumber) {
        String[] reginnum;
        int result = 0;
        int temp = 1;//存放一个单位的数字如：十万
        int count = 0;//判断是否有chArr
        StringBuilder stringBuilder = new StringBuilder();
        char[] cnArr = new char[]{'一', '二', '三', '四', '五', '六', '七', '八', '九'};
        char[] chArr = new char[]{'十', '百', '千', '万', '亿'};
        char[] caArr = new char[]{'一', '二', '三', '四', '五', '六', '七', '八', '九', '十'};

        for (int i = 0; i < chineseNumber.length(); i++) {
            char c = chineseNumber.charAt(i);
            for (int j = 0; j < caArr.length; j++) {//非单位，即数字
                if (c == caArr[j]) {
                    stringBuilder.append(c);
                }
            }
        }
        for (int i = 0; i < chineseNumber.length(); i++) {
            boolean b = true;//判断是否是chArr
            char c = chineseNumber.charAt(i);
            for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
                if (c == cnArr[j]) {
                    if (0 != count) {//添加下一个单位之前，先把上一个单位值添加到结果中
                        result += temp;
                        temp = 1;
                        count = 0;
                    }
                    // 下标+1，就是对应的值
                    temp = j + 1;
                    b = false;
                    break;
                }
            }
            if (b) {//单位{'十','百','千','万','亿'}
                for (int j = 0; j < chArr.length; j++) {
                    if (c == chArr[j]) {
                        switch (j) {
                            case 0:
                                temp *= 10;
                                break;
                            case 1:
                                temp *= 100;
                                break;
                            case 2:
                                temp *= 1000;
                                break;
                            case 3:
                                temp *= 10000;
                                break;
                            case 4:
                                temp *= 100000000;
                                break;
                            default:
                                break;
                        }
                        count++;
                    }
                }
            }
            if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
                result += temp;
            }
        }

        chineseNumber = chineseNumber.replaceAll("年", "");
        chineseNumber = chineseNumber.replaceAll(stringBuilder.toString(), "");
        chineseNumber = chineseNumber.replaceAll(" ", "");
        chineseNumber.trim();
        if (!isEmpty(stringBuilder.toString())) {
            reginnum = new String[]{chineseNumber, result + ""};
        } else {
            reginnum = new String[]{chineseNumber, ""};
        }
        return reginnum;
    }

    /**
     * 两端对齐
     *
     * @param textView
     */
    public static void justify(final TextView textView) {

        // 标记是否已经进行过处理，因为 post 回调会在处理后继续触发
        final AtomicBoolean isJustify = new AtomicBoolean(false);

        // 处理前原始字符串
        final String textString = textView.getText().toString();

        // 用于测量文字宽度，计算分散对齐后的空格宽度
        final TextPaint textPaint = textView.getPaint();

        CharSequence textViewText = textView.getText();

        // 分散对齐后的文字
        final Spannable builder = textViewText instanceof Spannable ?
                (Spannable) textViewText :
                new SpannableString(textString);

        // 在 TextView 完成测量绘制之后执行
        textView.post(new Runnable() {
            @Override
            public void run() {

                // 判断是否已经处理过
                if (!isJustify.get()) {

                    // 获取原始布局总行数
                    final int lineCount = textView.getLineCount();
                    // 获取 textView 的宽度
                    final int textViewWidth = textView.getWidth();

                    for (int i = 0; i < lineCount; i++) {

                        // 获取行首字符位置和行尾字符位置来截取每行的文字
                        int lineStart = textView.getLayout().getLineStart(i);
                        int lineEnd = textView.getLayout().getLineEnd(i);

                        String lineString = textString.substring(lineStart, lineEnd);

                        // 最后一行不做处理
                        if (i == lineCount - 1) {
                            break;
                        }

                        // 行首行尾去掉空格以保证处理后的对齐效果
                        String trimSpaceText = lineString.trim();
                        String removeSpaceText = lineString.replaceAll(" ", "");

                        float removeSpaceWidth = textPaint.measureText(removeSpaceText);
                        float spaceCount = trimSpaceText.length() - removeSpaceText.length();

                        // 两端对齐时每个空格的重新计算的宽度
                        float eachSpaceWidth = (textViewWidth - removeSpaceWidth) / spaceCount;

                        // 两端空格需要单独处理
                        Set<Integer> endsSpace = spacePositionInEnds(lineString);
                        for (int j = 0; j < lineString.length(); j++) {
                            char c = lineString.charAt(j);

                            // 使用透明的 drawable 来填充空格部分
                            Drawable drawable = new ColorDrawable(0x00ffffff);

                            if (c == ' ') {
                                if (endsSpace.contains(j)) {
                                    // 如果是两端的空格，则宽度置为 0
                                    drawable.setBounds(0, 0, 0, 0);
                                } else {
                                    drawable.setBounds(0, 0, (int) eachSpaceWidth, 0);
                                }
                                ImageSpan span = new ImageSpan(drawable);
                                builder.setSpan(span, lineStart + j, lineStart + j + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }
                    }

                    textView.setText(builder);
                    // 标记处理完毕
                    isJustify.set(true);
                }
            }
        });
    }

    /**
     * 返回两端的空格坐标，例如字符串 " abc  "（前面一个空格，后面两个空格）就返回 [0, 5, 6]
     */
    private static Set<Integer> spacePositionInEnds(String string) {
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c == ' ') {
                result.add(i);
            } else {
                break;
            }
        }

        if (result.size() == string.length()) {
            return result;
        }

        for (int i = string.length() - 1; i > 0; i--) {
            char c = string.charAt(i);
            if (c == ' ') {
                result.add(i);
            } else {
                break;
            }
        }

        return result;
    }

    public static String formatDecimal1Retain2(double d) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }


    /**
     * 这里是将获取到得编码进行16 进制转换
     *
     * @param arr
     * @return
     */
    public static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);

        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1)
                h = "0" + h;
            if (l > 2)
                h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1))
                str.append(':');
        }
        return str.toString();
    }

    public static int formatDecimal1Retain0(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return 0;
        }
        BigDecimal bigDecimal1 = bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP);
        int i = bigDecimal1.intValue();
        return i;
    }

    public static double formatDecimal1Retain1(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return 0;
        }
        BigDecimal bigDecimal1 = bigDecimal.setScale(1, BigDecimal.ROUND_DOWN);
        double i = bigDecimal1.doubleValue();
        return i;
    }

    public static int valueOfDoubleToInteger(String str) {
        int value = 0;
        if (!isEmpty(str)) {
            try {
                double valueDouble = Double.valueOf(str);
                value = (int) valueDouble;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }


    public static String toCapital(String str) {
        if (TextUtils.isEmpty(str)) return "";
        String st = str;
        if (st.contains(".")) {
            st = st.replace(".", "");
        }
        return st.toLowerCase();
    }

    public static String disposeAbstracts(String str) {
        if (TextUtils.isEmpty(str)) return "";
        String st = str;
        st = st.replaceAll("<p>", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        st = st.replaceAll("</p>", "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            st = Html.fromHtml(st, FROM_HTML_MODE_COMPACT).toString().trim();//.replace("</p>", "")
        } else st = Html.fromHtml(st) + "";
        return st;
    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        if (isEmpty(source)) return false;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (isEmpty(source)) {
            return source;
        }
        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }
        }
        if (buf == null) {
            return source;
        } else {
            if (buf.length() == len) {
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    public static long optLong(String s) {
        long aLong = 0;
        if (!isEmpty(s)) {
            aLong = Long.valueOf(s);
        }
        return aLong;
    }

    public static byte valueOfByte(String str) {
        byte value = 0;
        if (!isEmpty(str)) {
            value = Byte.valueOf(str);
        }
        return value;
    }

    //byte,short,int,char
    public static int objectToInt(Object obj) {
        int i = -100;
        String str = "";
        if (obj != null) {
            String string = obj.toString().trim();
            if (!isEmpty(string)) {
                if (string.contains(".")) {
                    str = string.substring(0, string.indexOf("."));
                    i = Integer.valueOf(str);
                } else {
                    i = Integer.valueOf(string);
                }
            }
        }
        return i;
    }

    public static int valueOfInteger(String str) {
        int value = 0;
        if (!isEmpty(str)) {
            try {
                value = Integer.valueOf(str);
            } catch (Exception e) {
                value = 0;
                e.printStackTrace();
            }
        }
        return value;
    }

    public static long valueOfLong(String str) {
        long value = 0;
        if (!isEmpty(str)) {
            value = Long.valueOf(str);
        }
        return value;
    }

    public static BigDecimal valueOfBigDecimal(String str) {
        BigDecimal value = BigDecimal.valueOf(0);
        if (!isEmpty(str)) {
            value = new BigDecimal(str);
        }
        return value;
    }

    public static Float valueOfFloat(String str) {
        Float value = (float) 0.00;
        if (!isEmpty(str)) {
            value = Float.valueOf(str);
        }
        return value;
    }

    public static Float valueTwoFloat(float value) {
        // 设置位数
        int scale = 2;
        // 表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        int roundingMode = BigDecimal.ROUND_DOWN;
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        return bd.floatValue();
    }

    public static boolean valueOfBoolean(String str) {
        if (TextUtils.isEmpty(str)) return false;
        switch (str.toLowerCase()) {
            case "true":
            case "1":
                return true;
        }
        return false;
    }


    public static String valueOfString(String str, String defaultStr) {
        String value = defaultStr;
        if (!isEmpty(str)) {
            value = str;
        }
        return value;
    }

    public static String valueOfString(String str) {
        return valueOfString(str, "");
    }

    public static String endsWithContentReplaceAll(String content, String regex, String replacement) {
        if (!isEmpty(content) && content.endsWith(regex)) {
            content = content.replaceAll(regex, replacement).trim();
        }
        return content;
    }

    public static String contentReplaceAll(String content, String regex, String replacement) {
        if (!isEmpty(content) && content.contains(regex)) {
            content = content.replaceAll(regex, replacement).trim();
        }
        return content;
    }

    /**
     * @param content
     * @param endIndex 19
     * @return
     */
    public static String substringEndIndex(String content, int endIndex) {
        if (!isEmpty(content) && content.length() >= endIndex) {
            content = content.substring(0, endIndex);
        }
        return content;
    }

    /**
     * @param str
     * @return //使用正则表达式判断该字符串是否为数字，第一个\是转义符，\d+表示匹配1个或 //多个连续数字，"+"和"*"类似，"*"表示0个或多个
     */
    public static boolean canParseInt(String str) {
        if (isEmpty(str)) { //验证是否为空
            return false;
        }
        return str.matches("\\d+");   //使用正则表达式判断该字符串是否为数字，第一个\是转义符，\d+表示匹配1个或 //多个连续数字，"+"和"*"类似，"*"表示0个或多个
    }

    /**
     * 将2018-10-20格式转换成2018/10/20
     *
     * @param str
     * @return
     */
    public static String changeDataFormat(String str) {
        if (str == null || (str != null && str.isEmpty()))
            return "";
        String[] strings = str.split("-");
        return strings[0] + "/" + strings[1];
    }

    //字符串转指定格式时间
    public static String getMyDate(String str) {
        return StringToDate(str, "yyyy-MM", "yyyy-MM");
    }

    private static String StringToDate(String dateStr, String dateFormatStr, String formatStr) {
        if (dateStr == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat s = new SimpleDateFormat(formatStr);

        return s.format(date);
    }

    private static String trim = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？[-]1234567890[．]]";
    private static String trim2 = "[、][.][。][-]()（）：:[!][！][@][#][$][%][&][*][，]《》<>/[?]~";
    private static String trim3 = "[、][.][。][-]()（）：:";
    private static String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    /**
     * 去掉指定字符串的开头的指定字符
     *
     * @param stream 原始字符串
     * @return
     */
    public static String StringStartTrim(String stream) {
        // null或者空字符串的时候不处理
        if (stream == null || stream.length() == 0 || trim == null || trim.length() == 0) {
            return stream;
        }

        // 要删除的字符串结束位置
        int end;
        // 正规表达式
        String regPattern = trim + "*+";
        Pattern pattern = Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
        // 去掉原始字符串开头位置的指定字符
        Matcher matcher = pattern.matcher(stream);
        if (matcher.lookingAt()) {
            end = matcher.end();
            stream = stream.substring(end);
        }
        // 返回处理后的字符串
        return stream;
    }

    public static boolean hasContainString(String stream) {
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(stream);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static String removeString(String stream) {
        return stream.replaceAll(regEx, "");
    }

    public static String exChange(String str) {
        StringBuffer sb = new StringBuffer();
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (Character.isUpperCase(c)) {
                    sb.append(Character.toLowerCase(c));
                }/*else if(Character.isLowerCase(c)){
                    sb.append(Character.toUpperCase(c));
                }*/
            }
        }

        return sb.toString();
    }

    /**
     * 判断手机号是否符合规范
     *
     * @param phoneNo 输入的手机号
     * @return
     */
    public static boolean isPhoneNumber(String phoneNo) {
        if (TextUtils.isEmpty(phoneNo)) {
            return false;
        }
        if (phoneNo.length() == 11) {
            for (int i = 0; i < 11; i++) {
                if (!PhoneNumberUtils.isISODigit(phoneNo.charAt(i))) {
                    return false;
                }
            }
            Pattern p = Pattern.compile("^((13[^4,\\D])" + "|(134[^9,\\D])" +
                    "|(14[5,7])" +
                    "|(15[^4,\\D])" +
                    "|(17[3,6-8])" +
                    "|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(phoneNo);
            return m.matches();
        }
        return false;
    }

    //    /**
//     * 验证手机格式
//     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String num = "[1][358]\\d{9}";
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    //邮箱验证
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strPattern)) {
            return false;
        } else {
            return strEmail.matches(strPattern);
        }
    }
    public static String getStringValue(HashMap<String, String> map, String key) {
        if (map == null) {
            return "";
        }
        if (map != null && map.containsKey(key)) {
            return map.get(key);
        }
        return "";
    }
}