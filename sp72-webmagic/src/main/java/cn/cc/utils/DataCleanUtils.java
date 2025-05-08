package cn.cc.utils;

public class DataCleanUtils {

    public static String cleanData(String data) {
        if (data == null) {
            return "";
        }

        data = data
                // 去除 script 标签及其内容
                .replaceAll("<script[^>]*>.*?</script>", "")

                // 去除 style 标签及其内容
                .replaceAll("<style[^>]*>.*?</style>", "")

                // 去除注释
                .replaceAll("<!--.*?-->", "")

                // 将 br 替换为换行符 \n
                .replaceAll("<br\\s*/?>", "\n")

                // 将 p 标签替换为换行
                .replaceAll("<p[^>]*>", "\n")
                .replaceAll("</p>", "\n")

                // 删除所有 HTML 标签（包括 div、span 等）
                .replaceAll("<[^>]+>", "")

                // 处理空白字符：压缩多个空格为一个
                .replaceAll("[ \\t\\x0B\\f]+", " ")

                // 合并多个换行为最多两个
                .replaceAll("\n\\s*\n", "\n\n");

        return data.trim();
    }




}
