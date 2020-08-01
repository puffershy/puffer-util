package com.puffer.util.lang;

import com.google.common.base.CaseFormat;

/**
 * 字符格式转换工具类
 *
 * @author buyi
 * @date 2017年4月2日下午8:02:50
 * @since 1.0.0
 */
public class CaseFormatUtil {

    /**
     * 将驼峰命名法的字符转换成下滑线命名法，并强制转换成小写<br>
     *
     * <pre>
     * "className" --> "class_name"
     * "ClassName" --> "class_name"
     *
     * 特殊场景：
     * "ClassName_public" --> "class_name_public"
     * "ClassName_Public" --> "class_name__public"
     * "Class.Name" --> "class._name"
     * "class.name" --> "class.name"
     * </pre>
     *
     * @param str
     * @return
     * @author buyi
     * @date 2017年4月2日下午8:11:46
     * @since 1.0.0
     */
    public static String camelToLowerUnderScore(String str) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str);
    }

    /**
     * 将驼峰命名法的字符转换成下滑线命名法，并强制转换成大写<br>
     *
     * <pre>
     * "className" --> "CLASS_NAME"
     * "ClassName" --> "CLASS_NAME"
     * </pre>
     *
     * @param str
     * @return
     * @author buyi
     * @date 2017年4月2日下午8:14:26
     * @since 1.0.0
     */
    public static String camelToUpperUnderScore(String str) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, str);
    }


    /**
     * 将下滑线命名法的字符转换成驼峰命名法，并且首字母强制转换成小写<br>
     *
     * <pre>
     * class_name --> className
     * class_Name --> className
     * Class_name --> className
     * Class_Name --> className
     * CLASS_NAME --> className
     * </pre>
     *
     * @param str
     * @return
     * @author buyi
     * @date 2017年4月2日下午8:21:22
     * @since 1.0.0
     */
    public static String underScoreToLowerCamel(String str) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str);
    }

    /**
     * 将下滑线命名法的字符转换成驼峰命名法，并且首字母强制转换成大写<br>
     *
     * <pre>
     * class_name --> ClassName
     * class_Name --> ClassName
     * Class_name --> ClassName
     * Class_Name --> ClassName
     * CLASS_NAME --> ClassName
     * </pre>
     *
     * @param str
     * @return
     * @author buyi
     * @date 2017年4月2日下午8:24:14
     * @since 1.0.0
     */
    public static String underScoreToUpperCamel(String str) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str);
    }

    /**
     * 空格 首字符大写
     *
     * @param str
     * @return java.lang.String
     * @author puffer
     * @date 2020年08月02日 00:06:59
     * @since 1.0.0
     */
    public static String spaceToUpperFirstChar(String str) {
        return CaseFormat.UPPER_SPACE_SPEPARATOR.to(CaseFormat.UPPER_SPACE, str);
    }
}
