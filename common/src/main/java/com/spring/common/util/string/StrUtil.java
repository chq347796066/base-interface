package com.spring.common.util.string;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spring.common.util.date.OutInteger;
import com.spring.common.util.regex.RegexUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.Collator;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年3月21日 上午9:36:54
 * @Desc类说明:字符串工具类
 */
public class StrUtil {
    public static final String DEFAULT_PATH_SEPARATOR = ",";
    /**
     * 纯数字
     */
    public static final int TYPE_NUM = 0;
    /**
     * 纯字母
     */
    public static final int TYPE_LETTER = 1;
    /**
     * 数字+字母
     */
    public static final int TYPE_NUM_AND_LETTER = 2;

    /**
     * 从一个字符串中分解出以指定的分隔符号分隔的各个子字符串
     *
     * @param src   来源字符串
     * @param space 分隔字符串
     * @param start 查找的起始位置.如果这个值为-1则返回null 在函数返回后，这个数值将指向下一个数据的起始位置
     *              当没有更多的数据可以被读取的时候，这个数值将被设置为 -1
     * @return 返回的字符串
     */
    public static String getStrToken(String src, String space, OutInteger start) {
        int srclen = src.length();
        int startpos = start.getValue();
        if (startpos == -1) {
            return null;
        }
        int endpos = src.indexOf(space, startpos);
        if (endpos == -1) {
            endpos = srclen;
            start.setValue(-1);
        } else {
            start.setValue(endpos + space.length());
        }
        return endpos > startpos ? src.substring(startpos, endpos) : "";
    }

    public static String getStrToken(String src, char c, OutInteger start) {
        int startpos = start.getValue();
        if (startpos == -1) {
            return null;
        }
        int endpos = src.indexOf(c, startpos);
        if (endpos == -1) {
            endpos = src.length();
            start.setValue(-1);
        } else {
            start.setValue(endpos + 1);
        }
        return endpos > startpos ? src.substring(startpos, endpos) : "";
    }

    /**
     * 从一个字符串的指定位置开始,取出一个整数
     *
     * @param src   来源字符串
     * @param space 分隔字符串
     * @param start 查找的起始位置. 在函数返回后，这个数值将指向下一个数据的起始位置 当没有更多的数据可以被读取的时候，这个数值将被设置为
     *              -1
     * @return 返回的整数
     */
    public static int getIntToken(String src, String space, OutInteger start) {
        return Integer.parseInt(getStrToken(src, space, start));
    }

    public static int getIntToken(String src, char space, OutInteger start) {
        return Integer.parseInt(getStrToken(src, space, start));
    }

    public static float getFloatToken(String src, char space, OutInteger start) {
        return Float.parseFloat(getStrToken(src, space, start));
    }

    // /**
    // * 从一个字符串的指定位置开始,取出一个布尔类型的数值
    // * @param src 来源字符串
    // * @param space 分隔字符串
    // * @param start 查找的起始位置.
    // * 在函数返回后，这个数值将指向下一个数据的起始位置
    // * 当没有更多的数据可以被读取的时候，这个数值将被设置为 -1
    // * @return 返回的布尔类型的数值
    // */
    // public static boolean getBoolToken(String src, String space, OutInteger
    // start)
    // {
    // return HYBool.parseBool(getStrToken(src, space, start));
    // }
    //
    // public static boolean getBoolToken(String src, char space, OutInteger
    // start)
    // {
    // return HYBool.parseBool(getStrToken(src, space, start));
    // }

    /**
     * 从一个字符串的指定位置开始,取出一个长整数类型的数值
     *
     * @param src   来源字符串
     * @param space 分隔字符串
     * @param start 查找的起始位置. 在函数返回后，这个数值将指向下一个数据的起始位置 当没有更多的数据可以被读取的时候，这个数值将被设置为
     *              -1
     * @return 返回的整数类型的数值
     */
    public static long getLongToken(String src, String space, OutInteger start) {
        return Long.parseLong(getStrToken(src, space, start));
    }

    public static long getLongToken(String src, char space, OutInteger start) {
        return Long.parseLong(getStrToken(src, space, start));
    }

    /**
     * 检查一个字符串是否全部由数字构成
     *
     * @param str 要被检查的字符串
     * @return false: str==null,str.length() == 0,包含非数字字符;true: 全部由数字组成
     */
    public static boolean isDigitStr(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }

        return true;
    }

    /**
     * 对一个字符串形式的浮点数的小数位数进行调整,仅保留指定位数的小数
     * <p>
     * 例如,对: String org = "114.0493551"; 执行: String ret = truncDecimal(org, 6);
     * 则结果为: ret = "114.049355";
     * </p>
     *
     * @param original 原始的浮点数字符串
     * @param decimals 需要最多保留的小数位数
     * @return 做小数截短处理后的结果字符串
     */
    public static String truncDecimals(String original, int decimals) {
        if (original == null) {
            return null;
        }

        int dotPos = original.indexOf('.');
        if (dotPos == -1 || dotPos + decimals + 1 >= original.length()) {
            return original;
        }
        return original.substring(0, dotPos + decimals + 1);
    }

    public static String replaceStr(String srcStr, char orgChar, String dstStr) {
        int index = srcStr.indexOf(orgChar);
        if (index == -1) {
            return srcStr;
        }

        StringBuffer sb = new StringBuffer(srcStr.length() + 10);
        int start = 0;
        for (; ; ) {
            sb.append(srcStr, start, index).append(dstStr);
            start = index + 1;
            if ((index = srcStr.indexOf(orgChar, start)) == -1) {
                break;
            }
        }
        if (start < srcStr.length()) {
            sb.append(srcStr.substring(start));
        }
        return sb.toString();
    }

    public static String replaceStr(String srcStr, String orgStr, char dstChar) {
        int index = srcStr.indexOf(orgStr);
        if (index == -1) {
            return srcStr;
        }

        int orgStrLen = orgStr.length();
        StringBuffer sb = new StringBuffer(srcStr.length());
        int start = 0;
        for (; ; ) {
            sb.append(srcStr, start, index).append(dstChar);
            start = index + orgStrLen;
            if ((index = srcStr.indexOf(orgStr, start)) == -1) {
                break;
            }
        }
        if (start < srcStr.length()) {
            sb.append(srcStr.substring(start));
        }
        return sb.toString();
    }

    /**
     * 字符串替换，将一个源字符串中的指定字符串替换成另一个字符串
     *
     * @param srcStr 来源字符串
     * @param orgStr 要替换掉的字符串
     * @param dstStr 要替换成的字符串
     * @return 替换后的字符串
     */
    public static String replaceStr(String srcStr, String orgStr, String dstStr) {
        String retStr = srcStr;
        int start = 0, end = 0, len = orgStr.length();
        end = retStr.indexOf(orgStr);

        if (end == -1) {
            return srcStr;
        }

        StringBuffer strbuf = new StringBuffer();
        do {
            strbuf.append(srcStr, start, end).append(dstStr);
            start = end + len;
            end = srcStr.indexOf(orgStr, start);
        } while (end != -1);
        strbuf.append(srcStr.substring(start));

        return strbuf.toString();
    }

    /**
     * 反转字符串,以字符数组的方式返回结果
     *
     * @param src 源字符串
     * @return 结果字符数组
     */
    public static char[] reverseAsArray(String src) {
        char[] chars = src.toCharArray();
        int maxIndex = chars.length - 1;
        int times = chars.length / 2;
        for (int i = 0; i < times; i++) {
            char temp = chars[i];
            chars[i] = chars[maxIndex - i];
            chars[maxIndex - i] = temp;
        }
        return chars;
    }

    /**
     * 反转一个字符串
     *
     * @param src 源字符串
     * @return 结果字符串
     */
    public static String reverseStr(String src) {
        char[] chars = reverseAsArray(src);
        return new String(chars);
    }

    /**
     * 将一个字符串中的一些特殊字符进行转换,变成可以被正确解析的字符串
     * <p>
     * 例如: 对一个原始文本: he said: "hello!" 将被转换为: he said: \"hello!\"
     * </p>
     *
     * @param src 原始字符串
     * @return 转换后的结果字符串
     */
    public static String escapeStr(String src) {
        String result = src;
        result = replaceStr(result, '\\', "\\\\");
        result = replaceStr(result, '\"', "\\\"");
        return result;
    }

    /**
     * 将一个被转义了的字符串还原为原始字符串
     * <p>
     * 这个过程相当于@code escapeStr(String)的反向过程.
     * </p>
     *
     * @param str 一个被转义了的字符串
     * @return 还原后获得的原始字符串
     */
    public static String discapeStr(String str) {
        String result = str;
        result = replaceStr(result, "\\\"", '\"');
        result = replaceStr(result, "\\\\", '\\');
        return result;
    }

    /**
     * 从一个以指定字符串(space)分隔的长字符串(src)中去掉一个指定的字符串(token)
     *
     * @param src   原来的长字符串
     * @param token 要被去掉的字符串
     * @param space 长字符串的分隔符号
     * @return 去掉指定的字符串后得到的字符串
     */
    public static String removeToken(String src, String token, String space) {
        String tempStr = space + src + space;
        String searchStr = space + token + space;
        int index = tempStr.indexOf(searchStr);
        // 如果没有找到,就直接返回原来的字符串
        if (index == -1) {
            return src;
        }

        int searchLen = searchStr.length();
        int tempLen = tempStr.length();

        // 如果当前来源字符串的内容就是要替换的字符串,那么返回""
        if (searchLen == tempLen) {
            return "";
        }

        int spaceLen = space.length();
        if (index == 0)
        {
            return tempStr.substring(searchLen, tempLen - spaceLen);
        } else if (index == tempLen - searchLen)
        {
            return tempStr.substring(spaceLen, index);
        } else
        {
            String leftStr = tempStr.substring(spaceLen, index);
            String rightStr = tempStr.substring(index + searchLen, tempLen - spaceLen);
            return new StringBuffer(leftStr).append(space).append(rightStr).toString();
        }
    }

    /**
     * 取一个字符串中的夹在两个限定字符串中间的字符串, 例如：GetMidStr("This is test", "this ",
     * " test")将返回"is"
     *
     * @param src      来源字符串
     * @param leftStr  左边的字符串
     * @param rightStr 右边的字符串
     * @return 中间的字符串，当其中一个限定字符串没有找到时返回null
     */
    public static String getMidStr(String src, String leftStr, String rightStr) {
        int sindex = 0, eindex = 0;
        sindex = src.indexOf(leftStr);
        eindex = src.indexOf(rightStr, sindex + leftStr.length());
        if (sindex == -1 || eindex == -1) {
            return null;
        }
        return src.substring(sindex + leftStr.length(), eindex);
    }

    /**
     * 从指定的位置开始，取一个字符串中的夹在两个限定字符串中间的字符串。
     * <p>
     * 查找的开始位置由pos参数指出，同时当函数返回的时候，指向的是右分割符号后面的一个字符； 如果超过字符串长度，则pos的数值为-1.
     * <p>
     * 例如：GetMidStr("This is test", "this ", " test")将返回"is"
     *
     * @param src      来源字符串
     * @param leftStr  左边的字符串
     * @param rightStr 右边的字符串
     * @param pos      查找的开始位置，当函数返回的时候，指向的是右分割符号后面的一个字符； 如果超过字符串长度，则数值为-1
     * @return 中间的字符串，当其中一个限定字符串没有找到时返回null
     */
    public static String getMidStr(String src, String leftStr, String rightStr, OutInteger pos) {
        int lsLen = leftStr.length();
        int rsLen = rightStr.length();
        int sindex = src.indexOf(leftStr, pos.getValue());
        if (sindex == -1) {
            pos.setValue(-1);
            return null;
        }
        // 查找右边的字符串
        int eindex = src.indexOf(rightStr, sindex + lsLen);
        if (eindex == -1) {
            pos.setValue(-1);
            return null;
        }
        // 取中间的字符串
        String result = src.substring(sindex + lsLen, eindex);

        // 设置下一个查找的开始位置，如果超出源字符串的长度，就设置为-1
        int nextPos = eindex + rsLen;
        pos.setValue(nextPos < src.length() ? eindex + rsLen : -1);

        return result;
    }

    /**
     * 取一个字符串中的中间字符串,从指定位置开始到有变得指定字符串除线的位置结束 例如：GetMidStr("This is test", 5,
     * " test")将返回"is"
     *
     * @param src        来源字符串
     * @param startIndex 左边的起始位置
     * @param rightStr   右边的字符串
     * @return 中间的字符串，当右边限定字符串没有找到时返回右边的全部子串
     */
    public static String getMidStr(String src, int startIndex, String rightStr) {
        int eindex = src.indexOf(rightStr, startIndex + 1);
        if (eindex == -1) {
            return src.substring(startIndex);
        }
        return src.substring(startIndex, eindex);
    }

    /**
     * 取一个字符串中的中间字符串,从指定位置开始到右边的指定字符串除线的位置结束.
     * <p>
     * 在函数返回的时候,在startIndex中将指向右边的限定字符串后面的一个字符的位置 例如：GetMidStr("This is test",
     * 5, " test")将返回"is"
     *
     * @param src        来源字符串
     * @param startIndex 左边的起始位置
     * @param rightStr   右边的字符串
     * @return 中间的字符串，当右边限定字符串没有找到时返回右边的全部子串
     */
    public static String getMidStr(String src, OutInteger startIndex, String rightStr) {
        int sindex = startIndex.getValue();
        int eindex = src.indexOf(rightStr, sindex + 1);
        if (eindex == -1) {
            startIndex.setValue(-1);
            return src.substring(startIndex.getValue());
        } else {
            String result = src.substring(sindex, eindex);
            startIndex.setValue(eindex + rightStr.length());
            return result;
        }
    }

    /**
     * 取出从某一个指定的标志字符串结束位置开始的count个字符,形成结果字符串
     * <p>
     * 如果没有找到标志字符串,返回NULL;
     * <p>
     * 如果源字符串长度不足,返回标志字符串后面的所有字符串
     *
     * @param src     来源字符串
     * @param leftStr 标志字符串
     * @param count   准备取得的长度
     * @return 中间的字符串
     */
    public static String getMidStr(String src, String leftStr, int count) {
        int sindex = src.indexOf(leftStr);
        if (sindex == -1) {
            return null;
        }
        sindex += leftStr.length();
        if (sindex + count > src.length()) {
            return src.substring(sindex);
        } else {
            return src.substring(sindex, sindex + count);
        }
    }

    /**
     * 取一个字符串的左边的字符串。 例如：GetLeftStr("This is test", " is ")将返回"this"
     *
     * @param src      来源字符串
     * @param rightStr 右边的起始标志字符串
     * @return 字符串的左边的字符串，当起始标志字符串没有找到时，返回null
     */
    public static String getLeftStr(String src, String rightStr) {
        int eIndex = src.indexOf(rightStr);
        return eIndex == -1 ? src : src.substring(0, eIndex);
    }

    /**
     * 取得左边的几个字符串
     *
     * @param src   来源字符串
     * @param count 要取得的字符的数量
     * @return 来源字符串左边的指定数量字符的字符串, 如果count > src.length 则返回整个字符串
     */
    public static String getLeftStr(String src, int count) {
        return src.substring(0, count);
    }

    /**
     * 取一个字符串的右边的字符串。 例如：GetRightStr("This is test", " is ")将返回"test"
     *
     * @param src     来源字符串
     * @param leftStr 左边的起始标志字符串
     * @return 字符串的右边的字符串，当起始标志字符串没有找到时，返回null
     */
    public static String getRightStr(String src, String leftStr) {
        int eIndex = src.indexOf(leftStr);
        return eIndex == -1 ? null : src.substring(eIndex + leftStr.length());
    }

    /**
     * 取一个字符串右边的几个字符
     *
     * @param src   来源字符串
     * @param count 取得字符的数量
     * @return 右边的字符串
     */
    public static String getRightStr(String src, int count) {
        return src.substring(src.length() - count);
    }

    /**
     * 取得一个最右边的标记的右边的字符串
     *
     * @param src    来源字符串
     * @param tagStr 标记字符串
     * @return 最右边的标记的右边的字符串，当没有找到标记时返回null
     */
    public static String getLastRightOf(String src, String tagStr) {
        int index = src.lastIndexOf(tagStr);
        if (index == -1) {
            return null;
        }
        return src.substring(index + tagStr.length());
    }

    /**
     * 取得一个最右边的标记的左边的字符串
     *
     * @param src    来源字符串
     * @param tagStr 标记字符串
     * @return 最右边的标记的左边的字符串，当没有找到标记时返回来源字符串
     */
    public static String getLastLeftOf(String src, String tagStr) {
        int index = src.lastIndexOf(tagStr);
        // System.out.println("src=" + src + "; tagStr=" + tagStr + ";index=" +
        // index);
        if (index == -1) {
            return src;
        }
        return src.substring(0, index);
    }

    /**
     * 取得一个最右边的标记的右边的字符串
     *
     * @param src     来源字符串
     * @param tagChar 标记字符
     * @return 最右边的标记的右边的字符串，当没有找到标记时返回null
     */
    public static String getLastRightOf(String src, char tagChar) {
        int index = src.lastIndexOf(tagChar);
        if (index == -1) {
            return null;
        }
        return src.substring(index + 1);
    }

    /**
     * 取得一个最右边的标记的左边的字符串
     *
     * @param src     来源字符串
     * @param tagChar 标记字符
     * @return 最右边的标记的左边的字符串，当没有找到标记时返回来源字符串
     */
    public static String getLastLeftOf(String src, char tagChar) {
        int index = src.lastIndexOf(tagChar);
        // System.out.println("src=" + src + "; tagStr=" + tagStr + ";index=" +
        // index);
        if (index == -1) {
            return src;
        }
        return src.substring(0, index);
    }

    /**
     * 将一个字符串的左边开始几个字符转换为大写形式
     *
     * @param orgStr 原来的字符串
     * @param count  要转换的字符的数量
     * @return 转换后的结果字符串
     */
    public static String upperCaseLeft(String orgStr, int count) {
        String left = orgStr.substring(0, count).toUpperCase();
        StringBuffer result = new StringBuffer();
        result.append(left).append(orgStr.substring(count));
        return result.toString();
    }

    /**
     * 将一个字符串的左边开始几个字符转换为小写形式
     *
     * @param orgStr 原来的字符串
     * @param count  要转换的字符的数量
     * @return 转换后的结果字符串
     */
    public static String lowerCaseLeft(String orgStr, int count) {
        String left = orgStr.substring(0, count).toLowerCase();
        StringBuffer result = new StringBuffer();
        result.append(left).append(orgStr.substring(count));
        return result.toString();
    }

    /**
     * 将一个用逗号分隔的字符串分割成一个字符串数组，每一个子字符串的首尾空格都被去掉
     *
     * @param srcStr 原来的一个以逗号分隔的长字符串
     * @param space  用于分割的字符串
     * @return 返回的字符串数组
     */
    public static String[] getArray(String srcStr, String space) {
        return getArray(srcStr, space, false);
    }

    /**
     * 将一个用逗号分隔的字符串分割成一个整数数组，
     *
     * @param srcStr 原来的一个以逗号分隔的长字符串
     * @param space  用于分割的字符串
     * @return 返回的整数数组
     */
    public static int[] getIntArray(String srcStr, String space) {
        if (srcStr == null) {
            return null;
        }
        if ("".equals(srcStr)) {
            return new int[]{};
        }

        ArrayList list = getStrList(srcStr, space, false);
        int[] result = new int[list.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt((String) list.get(i));
        }
        return result;
    }

    /**
     * 将一个以指定分隔符号分隔的字符串分割成一个字节数组
     *
     * @param srcStr 以指定分隔符号分隔的字符串
     * @param space  用于分割的字符
     * @return 返回的字节数组
     */
    public static byte[] getByteArray(String srcStr, char space) {
        List<String> list = getStrList(srcStr, space, true);
        byte[] result = new byte[list.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = Byte.parseByte(list.get(i));
        }
        return result;
    }

    /**
     * 将一个用指定分隔符号分隔的字符串分解成字符串列表
     *
     * @param srcStr 原来的一个以逗号分隔的长字符串
     * @param space  用于分割的字符串
     * @param trim   是否去除字符串的首尾的空格
     * @return 返回的字符串列表, 如果字符串为空或者长度为0则返回0长度字符串
     */
    public static ArrayList getStrList(String srcStr, String space, boolean trim) {
        ArrayList list = new ArrayList();
        if (srcStr == null || "".equals(srcStr)) {
            return list;
        }

        int spaceLen = space.length();

        int startPos = 0;
        int endPos = srcStr.indexOf(space);
        String tmpStr;
        while (endPos != -1) {
            tmpStr = srcStr.substring(startPos, endPos);
            list.add(trim ? tmpStr.trim() : tmpStr);
            startPos = endPos + spaceLen;
            endPos = srcStr.indexOf(space, startPos);
        }
        tmpStr = srcStr.substring(startPos);
        list.add(trim ? tmpStr.trim() : tmpStr);
        return list;
    }

    /**
     * 将一个用指定分隔符号分隔的字符串分解成字符串列表
     *
     * @param srcStr 原来的一个以逗号分隔的长字符串
     * @param space  用于分割的字符
     * @param trim   是否去除字符串的首尾的空格
     * @return 返回的字符串列表, 如果字符串为空或者长度为0则返回0长度字符串
     */
    public static List<String> getStrList(String srcStr, char space, boolean trim) {
        List<String> list = new ArrayList();
        if (srcStr == null || "".equals(srcStr)) {
            return list;
        }

        int startPos = 0;
        int endPos = srcStr.indexOf(space);
        String tmpStr;
        while (endPos != -1) {
            tmpStr = srcStr.substring(startPos, endPos);
            list.add(trim ? tmpStr.trim() : tmpStr);
            startPos = endPos + 1;
            endPos = srcStr.indexOf(space, startPos);
        }
        tmpStr = srcStr.substring(startPos);
        list.add(trim ? tmpStr.trim() : tmpStr);
        return list;
    }

    public static void getStrList(String src, int start, char space, boolean trim, List<String> list) {
        if (src == null || src.length() < start) {
            throw new IllegalArgumentException("Invalid source string: " + src);
        }

        String tmp;
        int endPos = src.indexOf(start, space);
        while (endPos != -1) {
            tmp = src.substring(start, endPos);
            list.add(trim ? tmp.trim() : tmp);
            start = endPos + 1;
            endPos = src.indexOf(space, start);
        }
        tmp = src.substring(start);
        list.add(trim ? tmp.trim() : tmp);
    }

    /**
     * 将一个用逗号分隔的字符串分割成一个字符串数组，每一个子字符串的首尾空格都被去掉
     *
     * @param srcStr 原来的一个以逗号分隔的长字符串
     * @param space  用于分割的字符串
     * @param trim   是否去除字符串的首尾的空格
     * @return 返回的字符串数组
     */
    public static String[] getArray(String srcStr, String space, boolean trim) {
        ArrayList list = getStrList(srcStr, space, trim);
        return (String[]) list.toArray(new String[list.size()]);
    }

    public static String[] getArray(String srcStr, char space, boolean trim) {
        List<String> list = getStrList(srcStr, space, trim);
        return list.toArray(new String[list.size()]);
    }

    /**
     * 将一个字符串列表的字符串用指定的分隔符号连接成一个整体字符串
     *
     * @param list  字符串列表
     * @param space 用于分割的字符串
     * @return 用指定的分隔符号连接成的一个整体字符串
     */
    public static String listToStr(List list, String space) {
        StringBuffer sb = new StringBuffer(128);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(space);
            }
            sb.append((String) list.get(i));
        }
        return sb.toString();
    }

    public static String listToStr(List list, char space) {
        StringBuffer sb = new StringBuffer(128);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(space);
            }
            sb.append((String) list.get(i));
        }
        return sb.toString();
    }

    /**
     * <p>
     * 将一个字符串数组转换成一个用指定符号分隔的字符串
     * </p>
     * <p>
     * 例如：arrayToStr(new String[]{"12", "34"}, ";") = "12;34"
     * </p>
     *
     * @param vArr   字符串数组
     * @param vSpace 分隔符号
     * @return 结果字符串
     */
    public static String arrayToStr(String[] vArr, String vSpace) {
        if (vArr == null || vSpace == null) {
            return null;
        }

        StringBuffer strb = new StringBuffer();
        for (int i = 0; i < vArr.length; i++) {
            if (i > 0) {
                strb.append(vSpace);
            }
            strb.append(vArr[i]);
        }
        return strb.toString();
    }

    /**
     * <p>
     * 将一个字符串数组转换成一个用指定符号分隔的字符串
     * </p>
     * <p>
     * 例如：arrayToStr(new String[]{"12", "34"}, ";") = "12;34"
     * </p>
     *
     * @param vArr   字符串数组
     * @param vSpace 分隔符号
     * @return 结果字符串
     */
    public static String arrayToStr(String[] vArr, char vSpace) {
        if (vArr == null) {
            return null;
        }

        StringBuffer strb = new StringBuffer();
        for (int i = 0; i < vArr.length; i++) {
            if (i > 0) {
                strb.append(vSpace);
            }
            strb.append(vArr[i]);
        }
        return strb.toString();
    }

    /**
     * 将一个形如：key1=value1,key2=value2,key3=value3 的字符串分解成一个属性表
     *
     * @param srcStr 原来的一个以分隔符号分隔的长字符串
     * @param space  用于分割的字符串
     * @return 返回的属性集合，当srcStr是一个无效的字符串或null时，返回一个0长度的属性集
     */
    public static Properties getProperties(String srcStr, String space) {
        Properties props = new Properties();
        String[] list = getArray(srcStr, space);

        if (list == null) {
            return props;
        }

        for (int i = 0; i < list.length; i++) {
            String keyvalue = list[i];
            int equalPos = keyvalue.indexOf("=");

            if (equalPos == -1) {
                return props;
            }

            String k = keyvalue.substring(0, equalPos).trim();
            String v = keyvalue.substring(equalPos + 1).trim();
            props.put(k, v);
        }
        return props;

    }

    /**
     * 将一个属性集格式化成一个字符串，属性和属性值将用指定的分隔符号分隔
     *
     * @param props 属性集
     * @param space 分隔符号
     * @return 格式化成的一个字符串
     */
    public static String formatToStr(Properties props, String space) {
        StringBuffer strb = new StringBuffer();
        int count = 0;
        Enumeration keys = props.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String val = props.getProperty(key);
            if (count > 0) {
                strb.append(space);
            }
            strb.append(key).append("=").append(val);
            count++;
        }
        return strb.toString();
    }

    /**
     * 将一个字符串整理到指定长度。 当长度补足时，补充空格到满足指定长度；当长度超过时，截除右边多余部分
     *
     * @param str    原字符串
     * @param length 处理后的长度
     * @return 处理后的字符串
     */
    public static String fixToLength(String str, int length) {
        if (str == null) {
            str = "";
        }

        int len = str.length();

        if (len == length) {
            return str;
        }

        if (len < length) {
            StringBuffer buff = new StringBuffer(str);
            for (int i = len; i < length; i++) {
                buff.append(" ");
            }
            return buff.toString();
        } else {
            return str.substring(0, length);
        }
    }

    /**
     * 建立一个指定长度的由字符spaceChar构成的字符串
     * <p>
     * 例如: buildCharStr("a", 5) -> "aaaaa", buildCharStr("a", 0) -> ""
     * </p>
     *
     * @param spaceChar 构成字符串的字符
     * @param count     字符数量
     * @return 一个字符串, 长度为count, 内容为连续的spaceChar
     */
    public static String buildCharStr(char spaceChar, int count) {
        if (count < 1) {
            return "";
        }

        StringBuffer sb = new StringBuffer(count);
        for (int i = 0; i < count; i++) {
            sb.append(spaceChar);
        }
        return sb.toString();
    }

    /**
     * 整理字符串到指定长度 对一个字符串的左边或者右边补指定的字符,当长度超过时，截除左边或者右边多余部分
     *
     * @param str    原字符串
     * @param space  用于补白的字符
     * @param length 处理后的长度
     * @param atLeft true->在左边进行操作, false->在右边执行操作
     * @return 处理后的字符串
     */
    public static String fixToLength(String str, int length, char space, boolean atLeft) {
        if (str == null) {
            str = " ";
        }
        int orgLen = str.length();
        if (orgLen == length) {
            return str;
        } else if (orgLen < length) {
            String spaceStr = buildCharStr(space, length - orgLen);
            StringBuffer buff = new StringBuffer(length);
            if (atLeft) {
                buff.append(spaceStr).append(str);
            } else {
                buff.append(str).append(spaceStr);
            }
            return buff.toString();
        } else {
            if (atLeft) {
                return str.substring(orgLen - length);
            } else {
                return str.substring(0, length);
            }
        }
    }

    /**
     * 将一个整理成指定长度字符串。 当长度补足时，补充空格到满足指定长度；当长度超过时，截除右边多余部分
     *
     * @param number 原来的整数
     * @param length 处理后的长度
     * @return 处理后的字符串
     */
    public static String fixToLength(int number, int length) {
        return fixToLength("" + number, length);
    }

    /**
     * 合并两个字符串数组的数据，当两个数组中包含相同的数据时，输出数组中只包含一个。 例如：当：a={"aaa", "bbb", "ccc"};
     * b={"ccc", "ddd", "eee"}, 则：mergeStrings(a, b) = {"aaa", "bbb", "ccc",
     * "ddd", "eee"};
     *
     * @param arr1 第一个数组
     * @param arr2 第二个数组
     * @return 合并后的数组
     */
    public static String[] mergeStrArray(String[] arr1, String[] arr2) {
        int length = arr1.length;
        boolean[] alreadyIn = new boolean[arr2.length];
        for (int i = 0; i < arr2.length; i++) {
            if (isInArray(arr1, arr2[i])) {
                alreadyIn[i] = true;
            } else {
                alreadyIn[i] = false;
                length++;
            }
        }
        String[] result = new String[length];
        for (int i = 0; i < arr1.length; i++) {
            result[i] = arr1[i];
        }
        int pos = arr1.length;
        for (int i = 0; i < arr2.length; i++) {
            if (alreadyIn[i] == false) {
                result[pos] = arr2[i];
                pos++;
            }
        }
        return result;
    }

    /**
     * 检查一个字符串数组中是否包含了某个指定的字符串
     *
     * @param arr         字符串数组
     * @param 检查是否包含了的字符串
     * @return true 包含了；false 没有包含
     */
    public static boolean isInArray(String[] arr, String str) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInArrayIgnoreCase(String[] strs, String str) {
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInArray(List<String> list, String str) {
        for (String item : list) {
            if (item.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查一个字符数组是否包含了指定字符
     *
     * @param arr 字符数组
     * @param c   字符
     * @return 是否包含
     */
    public static boolean isInArray(char[] arr, char c) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == c) {
                return true;
            }
        }
        return false;
    }

    /**
     * 向一个字符串数组中添加一个字符串
     *
     * @param arr 字符串数组
     * @param str 待添加的字符串
     * @return 新的字符串数组
     */
    public static String[] addStr(String[] arr, String str) {
        String[] tmp = new String[arr.length + 1];
        System.arraycopy(arr, 0, tmp, 0, arr.length);
        tmp[arr.length] = str;
        arr = tmp;

        return tmp;
    }

    /**
     * 将一个字符传输组的内容添加到一个矢量中
     *
     * @param target 保存字符串的矢量
     * @param arr    字符串数组
     */
    public static void addStrArray(Vector target, String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            target.addElement(arr[i]);
        }
    }

    /**
     * 替换数组中的null为指定的字符串
     *
     * @param arr 需要执行替换的字符串数组
     * @param str 要替换成的字符串
     * @return void
     */
    public static void replaceNull(String[] arr, String str) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                arr[i] = str;
            }
        }
    }

    /**
     * 替换数组中的null为指定的字符串
     *
     * @param arr 需要执行替换的字符串数组
     * @param str 要替换成的字符串
     * @return void
     */
    public static void replaceNull(String[][] arr, String str) {
        for (int i = 0; i < arr.length; i++) {
            replaceNull(arr, str);
        }
    }

    /**
     * 将一个字符串首尾的一些指定字符去掉
     *
     * @param orgStr   原来的字符串
     * @param chars    要去掉的字符数组
     * @param trimType 驱除的方式: -1 只对左边处理; 0 - 对首尾都处理; 1 - 对右边处理
     */
    public static String trimStr(String orgStr, char[] chars, int trimType) {
        int strLen = orgStr.length();
        int startIndex = 0, endIndex = strLen;

        // 先整理字符串开始部分的内容
        if (trimType <= 0) {
            startIndex = strLen;
            for (int i = 0; i < strLen; i++) {
                if (!isInArray(chars, orgStr.charAt(i))) {
                    startIndex = i;
                    break;
                }
            }
        }

        // 如果全部是空白字符,那么直接返回
        if (startIndex == strLen) {
            return "";
        }

        // 在整理右边
        if (trimType >= 0) {
            endIndex = 0;
            for (int i = strLen - 1; i >= startIndex; i--) {
                if (!isInArray(chars, orgStr.charAt(i))) {
                    endIndex = i;
                    break;
                }
            }
        }

        // 如果全部是空白字符,那么直接返回
        if (endIndex == 0) {
            return "";
        }

        // 否则返回有效字符串
        return orgStr.substring(startIndex, endIndex);
    }

    /**
     * Get a distinct string array from source string array
     *
     * @param src A vector contains source strings
     * @return A string array has no duplicate
     */
    public static String[] getDistinctArray(Vector src) {
        int count = src.size();
        String[] values = new String[count];
        for (int i = 0; i < count; i++) {
            values[i] = (String) src.elementAt(i);
        }
        return getDistinctArray(values);
    }

    /**
     * Get a distinct string array from source string array
     *
     * @param src An arrayList contains source strings
     * @return A string array has no duplicate
     */
    public static String[] getDistinctArray(List<String> src) {
        int count = src.size();
        String[] values = new String[count];
        for (int i = 0; i < count; i++) {
            values[i] = src.get(i);
        }
        return getDistinctArray(values);
    }

    /**
     * Get a distinct string array from source string array
     *
     * @param vec A vector contains source strings
     * @return A string array has no duplicate
     */
    public static String[] getDistinctArray(String[] src) {
        int count = src.length;
        int ignoreCount = 0;
        for (int i = 0; i < count - 1; i++) {
            if (src[i] == null) {
                continue;
            }

            for (int j = i + 1; j < count; j++) {
                if (src[j] == null) {
                    continue;
                }
                if (src[j].equals(src[i])) {
                    src[j] = null;
                    ignoreCount++;
                }
            }
        }

        if (ignoreCount == 0) {
            return src;
        }

        String[] result = new String[count - ignoreCount];
        int index = 0;
        for (int i = 0; i < count; i++) {
            if (src[i] != null) {
                result[index++] = src[i];
            }
        }
        return result;
    }

    /**
     * 将一个矢量中的字符串数据转换成为一个字符串数组
     *
     * @param v 矢量，其中存储的数据必须都是字符串对象
     * @return 字符串数组
     */
    public static String[] strVectorToArray(Vector v) {
        String[] vals = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            vals[i] = (String) v.elementAt(i);
        }
        return vals;
    }

    /**
     * 将一个内容元素是字符串列表对象转换为一个字符串数组
     *
     * @param list 列表
     * @return 字符串数组
     */
    public static String[] strListToArray(ArrayList list) {
        String[] vals = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            vals[i] = (String) list.get(i);
        }
        return vals;
    }

    /**
     * 从一个给定的字符串序列中查找一个字符
     *
     * @param chars  一个给定的字符序列
     * @param start  查找的开始位置，0表示从序列的char[0]开始
     * @param flag   要查找的字符
     * @param escape 脱字符，0表示没有脱字检查
     * @return 要查找的内容第一次出现的位置，-1表示没找到
     */
    public static int searchChar(CharSequence chars, int start, char flag, char escape) {
        if (escape == 0) {
            for (int i = start; i < chars.length(); i++) {
                if (chars.charAt(i) == flag) {
                    return i;
                }
            }
            return -1;
        } else {
            for (int i = start; i < chars.length(); i++) {
                char c = chars.charAt(i);
                // 如果碰到一个脱字符，则跳过紧随其后的一个字符，继续搜索
                if (c == escape) {
                    i++;
                    continue;
                }
                if (c == flag) {
                    return i;
                }
            }
            return -1;
        }
    }

    /**
     * 从一个给定的字符串序列中查找一个字符串内容
     *
     * @param chars  一个给定的字符序列
     * @param start  查找的开始位置，0表示从序列的char[0]开始
     * @param flag   要查找的内容
     * @param escape 脱字符，0表示没有脱字检查
     * @return 要查找的内容第一次出现的位置，-1表示没找到
     */
    public static int searchString(CharSequence chars, int start, CharSequence flag, char escape) {
        // 在来源串中查找目标串第一个字符出现的位置
        char fc = flag.charAt(0);
        int firstCharPos = searchChar(chars, start, fc, escape);
        if (firstCharPos == -1) {
            return -1;
        }
        int charslen = chars.length(), flaglen = flag.length();
        while (true) {
            // 首先校验来源串长度，以确定是否有执行后续核对的必要
            if (firstCharPos + flaglen > charslen) {
                return -1;
            }

            boolean match = true;
            for (int i = 1; i < flaglen; i++) {
                if (flag.charAt(i) != chars.charAt(firstCharPos + i)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return firstCharPos;
            } // 有匹配到，返回

            // 否则继续往后检查,规则同前
            start++;
            firstCharPos = searchChar(chars, start, fc, escape);
            if (firstCharPos == -1) {
                return -1;
            } // 第一个字符都没有，直接返回
        }
    }

    /**
     * 从字符串序列中查找一段内容，这段内容被一个给定的左标识和一个右标识所标定。
     *
     * @param chars  一个给定的字符序列
     * @param start  查找的开始位置，0表示从序列的char[0]开始
     * @param limit  查找的限制范围,即左标识最大允许出现的位置,-1表示无限查找
     * @param left   要查找的内容的左标识
     * @param right  要查找的内容的右标识
     * @param escape 脱字符，0表示没有脱字检查
     * @return 长度为2，分别表示左标识和右标识的开始位置。NULL表示没找到
     * @throws IllegalArgumentException 如果只找到一个左标识，却没有右标识。
     */
    public static int[] searchContent(CharSequence chars, int start, int limit, CharSequence left, CharSequence right, char escape) {
        // 查找左边的标志，如果没有找到，直接返回
        int leftStart = searchString(chars, start, left, escape);
        boolean isTrue=leftStart == -1 || (limit != -1 && leftStart >= limit);
        if (isTrue) {
            return null;
        }

        // 查找右边的位置，如果没有找到，并且无限查找,则抛出异常
        int rightStart = searchString(chars, leftStart + left.length(), right, escape);
        if (rightStart == -1) {
            if (limit == -1) {
                System.out.printf("在位置(%d)发现左边界(%s),没找到右边界(%s)\r\n", leftStart, left, right);
                return null;
            }

            String desc = "没有找到和{" + left + "}匹配的{" + right + "},位置：" + leftStart;
            throw new IllegalArgumentException(desc);
        }

        return new int[]{leftStart, rightStart};
    }

    /**
     * <p>
     * 假定有一类字符串内容,它由一左一右两个特定字符串标识,例如<%= %>, 我们称其为子串,其左右标识分别用left和right表示.
     * </p>
     * <p>
     * 在一个给定的字符串上(称为来源字符串,用chars表示), 从任一位置(用start表示)开始,都可能会存在着0个或者多个这样的子串;
     * 可以保证的是,在来源串中,从所给出的"任一位置(start)"开始,子串的左标识一定先出现;
     * </p>
     * <p>
     * 本函数的作用是:校验一个指定的值area所表示的区间是否落在一个子串的范围中;
     * 其中area[0]表示区间的开始字符序号;area[1]表示区间的结束序号, 区间长度为area[1]-area[0]个字符.
     * 如果落在某个子串中,则返回这个子串的在来源串中的左右标识的开始位置.
     * </p>
     * 这左右两个字符串总是会覆盖一个区域 从指定位置开始连续检索,以校验
     *
     * @param chars  来源字符串
     * @param start  一个指定的开始检索位置
     * @param area   要校验是否被覆盖的位置
     * @param left   用于覆盖检查的子串的左标识
     * @param right  用于覆盖检查的子串的右标识
     * @param escape 脱字检查的标志字符,'\0'表示无脱字检查
     * @return 覆盖参数pos的子串的左右标识的开始位置, NULL表示没有覆盖.
     */
    public static int[] getAreaCovered(CharSequence chars, int start, int[] area, CharSequence left, CharSequence right, char escape) {
        while (true) {
            int[] range = searchContent(chars, start, area[1], left, right, escape);
            if (range == null || range[0] >= area[1]) {
                return null;
            }
            if (range[1] + right.length() >= area[0]) {
                return range;
            }

            start = range[1] + right.length();
        }
    }
    /**
     * 检查一个输入字符串从一个指定位置开始是否匹配一个标志字符串
     *
     * @param input 输入字符串
     * @param limit 匹配的最大允许结束位置
     * @param flag  要匹配的字符串
     * @param start 匹配的开始位置
     * @return
     */
    public static boolean isMatchStart(CharSequence input, int limit, CharSequence flag, int start) {
        if (start + flag.length() > limit) {
            return false;
        }

        int length = flag.length();
        for (int i = 0; i < length; i++) {
            if (input.charAt(start + i) != flag.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 从一个指定位置开始，从一个来源字符序列中检索一个标志字符串
     * <p>
     * 从一个输入字符串的指定位置开始,在一个指定的范围内,检索一个目标字符串; 检索过程中需要跳过一些由指定的开始符号和结束符号所标定的区域,
     * 同时,需要跳过由脱字符所标注的字符
     * </p>
     * <p>
     * 例如: <code>
     * String s ="<%? (params[Age] > <%= limitAge %>)? "Good": "!Young!" %>";
     * CharSequence[][] skips = { {"\"", "\""}, {"<%=", "%>"} };
     * int i = searchTag(s, "%>", "<%?".length(), skips, '\0');
     * </code> i将返回s中最有一个"%>"所出现的位置。
     * </p>
     *
     * @param input  输入字符串
     * @param limit  查找最大许可的结束位置
     * @param target 要查找的字符串
     * @param start  查找的开始位置
     * @param skips  需要跳过的区域的标志数组,每个元素包括一个开始和结束符号
     * @param escape 查找过程中需要跳过的转义字符
     * @return 目标字符串第一次出现的位置,-1表示没找到
     */
    public static int searchString(CharSequence input, int limit, CharSequence target, int start, CharSequence[][] skips, char escape) {
        while (start < limit) {
            char c = input.charAt(start);
            // 如果是脱字符,跳过脱字符本身和紧跟其后的一个字符
            if (c == escape) {
                start += 2;
                continue;
            }

            // 判断从当前位置开始的连续几个字符是否匹配目标字符串
            if (isMatchStart(input, limit, target, start)) {
                return start;
            }

            // 检查是否为一个可能的需要跳过的字符区域
            CharSequence[] foundSkip = null;
            for (CharSequence[] skip : skips) {
                if (isMatchStart(input, limit, skip[0], start)) {
                    foundSkip = skip;
                    break;
                }
            }
            if (foundSkip != null) {
                int skipEnd = searchString(input, limit, foundSkip[1], start + foundSkip[0].length(), skips, escape);
                if (skipEnd == -1) {
                    return -1;
                }
                start = skipEnd + foundSkip[1].length();
            } else {
                start++;
            }
        }
        return -1;
    }

    public static void main(String[] args) throws Exception {
        String s = "<%? (params[Age] > <%= limitAge %>)? \"Good\": \"!Young!\" \\%%>";
        System.out.println("string length=" + s.length());
        CharSequence[][] skips = {{"\"", "\""}, {"<%=", "%>"}};
        int i = searchString(s, s.length(), "%>", "<%?".length(), skips, '\\');
        System.out.println("i=" + i);
    }

    /**
     * 判断数组是否有效
     */
    public static boolean isValid(Object[] arr) {
        return arr != null && arr.length != 0;
    }

    /**
     * 【连接字符串】(这里用一句话描述这个方法的作用)
     *
     * @param list
     * @param s
     * @return
     */
    public static String join(Collection list, String s) {
        StringBuffer sb = new StringBuffer();
        if (null == list) {
            return null;
        }
        for (Object object : list) {
            if (null == object) {
                continue;
            }
            if (sb.toString().length() > 0) {
                sb.append(s);
            }
            sb.append(object.toString());
        }
        return sb.toString();
    }

    /**
     * 【添加前缀 和 后缀】(如果已经存在则不添加)
     *
     * @param strs
     * @param s
     * @return
     */
    public static String prdfixAndSuffix(String strs, char s) {
        String str = new String(new char[]{s});
        if (!StrUtil.isEmpty(strs)) {
            StringBuffer sb = new StringBuffer();
            String one = strs.substring(0, 1);
            String two = strs.substring(strs.length() - 1);
            if (!str.equals(one)) {
                sb.append(str);
            }
            sb.append(strs);
            if (!str.equals(two)) {
                sb.append(str);
            }
            strs = sb.toString();
        }
        return strs;
    }

    public static String appendByLength(String str, int length, char c) {
        if (str == null || c == 0 || str.length() >= length) {
            return str;
        }
        StringBuffer sb = new StringBuffer(str);
        for (int i = 0; i < (length - str.length()); i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 将数组变换成字符串,使用","号分割
     */
    public static String arr2Str(Object[] arr) {
        StringBuilder temp=new StringBuilder();
        if (isValid(arr)) {
            for (Object s : arr) {
                temp= temp.append(s).append(",");
            }
            return temp.substring(0, temp.length() - 1);
        }
        return temp.toString();
    }

    public static int compare(String s1, String s2) {
        if ((s2 == null) && (s1 == null)) {
            return 0;
        }
        if (s2 == null) {
            return 1;
        }
        if (s1 == null) {
            return -1;
        }
        Collator cnCollator = Collator.getInstance(Locale.getDefault());
        return cnCollator.compare(s1, s2);
    }

    public static String decodeFromAscii(String s) {
        if ((s == null) || (s.length() == 0) || (s.length() % 2 != 0)) {
            return null;
        }
        String result = "";
        for (int i = 0; i < s.length(); i += 2) {
            String str = s.substring(i, i + 2);
            result = result + getValueFromAscii(str);
        }
        return result;
    }

    public static StringBuilder delLastChar(StringBuilder str, char ch) {
        if (str.length() < 1) {
            return str;
        }
        if (str.charAt(str.length() - 1) == ch) {
            str.deleteCharAt(str.length() - 1);
        }
        return str;
    }

    public static StringBuilder delFirstChar(StringBuilder str, char ch) {
        if (str.length() < 1) {
            return str;
        }
        if (str.charAt(0) == ch) {
            str.deleteCharAt(0);
        }
        return str;
    }

    public static String encodeToAscii(String s) {
        if ((s == null) || (s.length() == 0)) {
            return null;
        }
        String result = "";
        for (int i = 0; i < s.length(); ++i) {
            result = result + getAsc(s.charAt(i));
        }
        return result;
    }

    private static String getAsc(char c) {
        String hexString = Integer.toHexString(c);
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }

    private static String getValueFromAscii(String s) {
        char c = (char) Integer.parseInt(s, 16);
        return String.valueOf(c);
    }

    public static boolean isDigit(String str) {
        return (str != null) && (str.matches("\\d+"));
    }

    public static boolean isEmpty(Object str) {
        if (str instanceof String) {
            return (null == str) || (str.toString().trim().length() == 0);
        } else if (str instanceof JSONObject) {
            return (null == str) || (str.toString().length() == 0);
        } else if (str instanceof Collection) {
            return (null == str) || (((Collection) str).size() == 0);
        } else if (str instanceof Map) {
            return (null == str) || (((Map) str).size() == 0);
        } else if (str instanceof Vector) {
            return (null == str) || (((Vector) str).size() == 0);
        } else {
            return null == str;
        }
    }

    /**
     * 【对象转字符串】(这里用一句话描述这个方法的作用)
     *
     * @param str
     * @return
     */
    public static String objToStr(Object obj) {
        return (String) (obj = null == obj ? null : obj.toString());
    }

    /**
     * 【字符串转integer】(这里用一句话描述这个方法的作用)
     *
     * @param str
     * @return
     */
    public static Integer strToInteger(String str) {
        return null != str && RegexUtil.isNumber(str) ? Integer.parseInt(str) : null;
    }

    /**
     * 【字符串转long】(这里用一句话描述这个方法的作用)
     *
     * @param str
     * @return
     */
    public static Long strToLong(String str) {
        return null != str && RegexUtil.isNumber(str) ? Long.parseLong(str) : null;
    }

    /**
     * 【字符串转Double】(这里用一句话描述这个方法的作用)
     *
     * @param str
     * @return
     */
    public static Double strToDouble(String str) {
        return null != str && RegexUtil.isNumber(str) ? Double.parseDouble(str) : null;
    }

    public static String killNull(Object str) {
        return killNull(str, "");
    }

    public static String killNull(Object str, String dflt) {
        if (isEmpty(str)) {
            return dflt;
        }
        return str.toString().trim();
    }

    public static List<String> split(String str, String delim) {
        List splitList = null;
        StringTokenizer st = null;
        if (str == null) {
            return splitList;
        }
        if (delim != null) {
            st = new StringTokenizer(str, delim);
        } else {
            st = new StringTokenizer(str);
        }
        if (st.hasMoreTokens()) {
            splitList = new ArrayList();
            while (st.hasMoreTokens()) {
                splitList.add(st.nextToken());
            }
        }
        return splitList;
    }

    public static List<Integer> toIntList(String str) {
        if (str == null) {
            return Collections.EMPTY_LIST;
        }
        String[] strs = str.split("\\D+");
        List list = new ArrayList(strs.length);
        for (String s : strs) {
            if (s.length() > 0) {
                list.add(Integer.valueOf(s));
            }
        }
        return list;
    }

    public static String getRegexByStr(String str) {
        StringBuffer regex = new StringBuffer(".*");
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == '*') {
                regex.append("\\");
            }
            regex.append(str.charAt(i));
            regex.append(".*");
        }
        return regex.toString();
    }

    public static boolean matchString(String str1, String str2) {
        return (str1.matches(getRegexByStr(str2))) || (str2.matches(getRegexByStr(str1)));
    }

    public static String replaceNewlineChar(String str) {
        return str.replace("\r\n", "").replace("\r", "").replace("\n", "");
    }

    /**
     * 判断一个字符串中是否包含另一个字符串
     *
     * @param parentStr 父串
     * @param subStr    子串
     * @return 如果父串包含子串的内容返回真，否则返回假
     */
    public static boolean isInclude(String parentStr, String subStr) {
        return parentStr.indexOf(subStr) >= 0;
    }

    /**
     * 判断一个字符串中是否包含另一个字符串数组的任何一个
     *
     * @param parentStr 父串
     * @param subStrs   子串集合(以逗号分隔的字符串)
     * @return 如果父串包含子串的内容返回真，否则返回假
     */
    public static boolean isIncludes(String parentStr, String subStrs) {
        String[] subStrArray = strToStrArray(subStrs);
        for (int j = 0; j < subStrArray.length; j++) {
            String subStr = subStrArray[j];
            if (isInclude(parentStr, subStr)) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    /**
     * 将一个中间带逗号分隔符的字符串转换为字符型数组对象
     *
     * @param str 待转换的符串对象
     * @return 字符型数组
     */
    public static String[] strToStrArray(String str) {
        return strToStrArrayManager(str, DEFAULT_PATH_SEPARATOR);
    }

    /**
     * 将字符串对象按给定的分隔符separator转象为字符型数组对象
     *
     * @param str       待转换的符串对象
     * @param separator 字符型分隔符
     * @return 字符型数组
     */
    public static String[] strToStrArray(String str, String separator) {
        return strToStrArrayManager(str, separator);
    }

    /**
     * 根据一条完整的目录路径截取末节点
     *
     * @param path
     * @return String
     * @Title: getLastmenupathByPath
     * @author xsolr
     * @date 2013-4-26 下午01:57:09
     * @version V1.0
     */
    public static String getLastmenupathByPath(String path) {
        String menupath = null;
        // 截取目录标题路径前后斜杠/
        if (path != null && path.trim().length() > 0) {
            if (path.indexOf("/") < 0) {
                menupath = path;
            } else {
                String[] menupaths = path.split("/");
                menupath = menupaths[menupaths.length - 1];
            }
        }
        return menupath;
    }

    private static String[] strToStrArrayManager(String str, String separator) {

        StringTokenizer strTokens = new StringTokenizer(str, separator);
        String[] strArray = new String[strTokens.countTokens()];
        int i = 0;

        while (strTokens.hasMoreTokens()) {
            strArray[i] = strTokens.nextToken().trim();
            i++;
        }

        return strArray;
    }

    /**
     * 根据阿拉伯数字得到汉字
     *
     * @param number
     * @return String
     * @Title: getCNByNumber
     * @author xsolr
     * @date 2013-5-25 下午07:27:14
     * @version V1.0
     */
    public static String getNumber(int number) {
        String cn = "";
        switch (number) {
            case 1:
                cn = "一";
                break;
            case 2:
                cn = "二";
                break;
            case 3:
                cn = "三";
                break;
            case 4:
                cn = "四";
                break;
            case 5:
                cn = "五";
                break;
            case 6:
                cn = "六";
                break;
            case 7:
                cn = "七";
                break;
            case 8:
                cn = "八";
                break;
            case 9:
                cn = "九";
                break;
            case 10:
                cn = "十";
                break;
            case 11:
                cn = "十一";
                break;
            case 12:
                cn = "十二";
                break;
            default:
        }

        return cn;
    }

    /**
     * 【获取Long类型集合】(info)
     *
     * @param atts 必须是JsonArray 或者 逗号分隔的
     * @return
     * @author xsolr
     */
    public static List<Long> getAttList(String atts) {
        if (null == atts) {
            return new ArrayList();
        }
        try {
            List<Long> ja = JSON.parseArray(atts, Long.class);
            return ja;
        } catch (Exception e) {
            List<Long> res = new ArrayList();
            String[] arr = atts.split(",");
            for (String s : arr) {
                if (RegexUtil.isInteger(s)) {
                    res.add(Long.parseLong(s));
                }
            }
            return res;
        }
    }

    /***
     * 将一个字符串list按照指定的链接字符串 拼接成字符串返回
     *
     * @Title: strListToJoinStr
     * @param strList
     * @param joinstr
     * @return String
     * @author xsolr
     * @date 2013-5-30 下午08:21:47
     * @version V1.0
     */
    public static String strListToJoinStr(List<? extends Object> strList, String joinstr) {
        if (isEmpty(strList)) {
            return null;
        }
        StringBuilder ressb = new StringBuilder();
        for (int i = 0; i < strList.size(); i++) {
            if (null == strList.get(i) || "null".equalsIgnoreCase(strList.get(i).toString())) {
                continue;
            }

            if (i > 0) {
                ressb.append(joinstr);
            }
            ressb.append(strList.get(i));
        }
        return ressb.toString();
    }

    /**
     * 统计字符串中汉字个数
     *
     * @param nickname
     * @return int
     * @Title: getLenOfString
     * @author xsolr
     * @date 2013-9-3 下午08:39:20
     * @version V1.0
     */
    public static int getStringCount(String nickname) {
        // 汉字个数
        int chCnt = 0;
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        java.util.regex.Matcher m = p.matcher(nickname);
        while (m.find()) {
            chCnt++;
        }
        return chCnt;
    }

    /**
     * 【把null替换成empty】(这里用一句话描述这个方法的作用)
     *
     * @param s
     * @return
     */
    public static String nullToEmpty(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

    /**
     * 【把null替换成empty】(这里用一句话描述这个方法的作用)
     *
     * @param s
     * @return
     */
    public static Object nullToEmpty(Object s) {
        if (s == null) {
            return new Object();
        }
        return s;
    }

    /**
     * 【把null替换成empty】(这里用一句话描述这个方法的作用)
     *
     * @param s
     * @return
     */
    public static List nullToEmpty(List s) {
        if (s == null) {
            return new ArrayList();
        }
        return s;
    }

    /**
     * 【把null替换成empty】(这里用一句话描述这个方法的作用)
     *
     * @param s
     * @return
     */
    public static JSONObject nullToEmpty(JSONObject s) {
        if (s == null) {
            return new JSONObject();
        }
        return s;
    }

    /**
     * 【把null替换成empty】(这里用一句话描述这个方法的作用)
     *
     * @param s
     * @return
     */
    public static JSONArray nullToEmpty(JSONArray s) {
        if (s == null) {
            return new JSONArray();
        }
        return s;
    }

    /**
     * 【把null替换成empty】(这里用一句话描述这个方法的作用)
     *
     * @param s
     * @return
     */
    public static Number nullToEmpty(Number s) {
        if (s == null) {
            return 0;
        }
        return s;
    }

    /**
     * 【判断字符串是否符合指定格式】(这里用一句话描述这个方法的作用)
     *
     * @param type
     * @param s
     * @return
     */
    public static boolean isAccord(int type, String s) {
        boolean isAccord = false;
        boolean isTrue=TYPE_NUM_AND_LETTER == type && (RegexUtil.isInteger(s) || RegexUtil.isEnglish(s));
        // 数字
        if (TYPE_NUM == type && RegexUtil.isInteger(s)) {
            isAccord = true;
        }// 字母
        else if (TYPE_LETTER == type && RegexUtil.isEnglish(s)) {
            isAccord = true;
        }
        else if (isTrue) {
            isAccord = true;
        }
        return isAccord;
    }
    /**
     * 【获得图片服务器缩略图url】(这里用一句话描述这个方法的作用)
     *
     * @param url 原始图片url
     * @param w   宽度
     * @param h   高度
     * @return 缩略图url
     */
    public static String getServerThromUrl(String url, int w, int h) {
        if (url.endsWith("src")) {
            url = url.substring(0, url.length() - 3);
        }
        url += String.format("%sx%s", w, h);
        return url;
    }

    /**
     * 【取得文件的后缀名】
     *
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String fileName) {
        if (null == fileName || fileName.trim().length() == 0) {
            return null;
        }
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return suffix;
    }

    public static Map<String, String> parseHttpGetParams(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(url.indexOf("?") + 1);
        }
        String[] arr = url.split("&");
        Map<String, String> map = new HashMap<String, String>(16);
        for (int i = 0; arr != null && i < arr.length; i++) {
            String param = arr[i];
            if (param.indexOf("=") < 0) {
                continue;
            }
            String[] paramArr = param.split("=");
            map.put(paramArr[0], paramArr[1]);
        }
        return map;
    }

    /**
     * @return
     * @Author Liu Pinghui
     * @Description 根据指定字符截取字符串，获得一个List
     * @Date 2019/5/31 9:26
     * @Param
     **/
    public static List<String> strToList(String inputStr, String str) {

        if (StringUtils.isEmpty(inputStr)) {
            return null;
        }

        String[] strArray = inputStr.split(str);
        List<String> list = new ArrayList<String>();
        for (String s : strArray) {
            if (StringUtils.isNotEmpty(s)) {
                list.add(s);
            }
        }
        return list;
    }

    /**
     * @return
     * @Author Liu Pinghui
     * @Description 根据指定字符截取字符串，获得一个List
     * @Date 2019/5/31 9:26
     * @Param
     **/
    public static String listToStr(String[] array, String str) {

        if (array == null || array.length <= 0) {
            return null;
        }
        String result = "";
        for (String s : array) {
            if (StringUtils.isNotEmpty(s)) {
                result = result + str;
            }
        }

        if(StringUtils.isNotEmpty(result)){
            return result.substring(0, result.length()-1);
        }
        return null;
    }

    /**
     * @Author Liu Pinghui
     * @Description 获取指定位的随机数
     * @Date 2019/6/11 14:37
     * @Param
     * @return
     **/
    public static String getRandom(Integer num){
        String random = "";
        Random rand = new Random();
        for(int i=0; i<num; i++) {
            random += rand.nextInt(10);
        }
        return random;
    }

    /**
     *  首字母转大写
     * @param str
     * @return
     */
    public static String firstLetterCapitalized(String str){
        // 效率高的方法
        char[] chars = str.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] = (char)(chars[0] - 32);
        }
        return new String(chars);
    }

    /**
     * @return
     * @Author Liu Pinghui
     * @Description 去重
     * A,A,B,B,C,C 返回 A,B,C
     * @Date 2019/8/9 16:16
     * @Param
     **/
    public static String removeRepeat(String str) {
        if (StringUtils.isNotEmpty(str)) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>(16);
            String[] array = str.split(",");
            for (String s : array) {
                if (StringUtils.isNotEmpty(s)) {
                    hashMap.put(s, s);
                }
            }
            if (!hashMap.isEmpty()) {
                String result = "";
                for (Object val: hashMap.values()){
                    result += val + ",";
                }
                if(StringUtils.isNotEmpty(result)){
                    return result.substring(0, result.length() -1);
                }
            }
        }
        return null;
    }
}
