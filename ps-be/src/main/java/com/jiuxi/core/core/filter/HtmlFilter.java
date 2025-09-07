package com.jiuxi.core.core.filter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.springframework.web.util.HtmlUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * HtmlFilter
 * @author 杨攀
 */
public class HtmlFilter {

    /**
     * 默认使用relaxed()
     * 允许的标签: a, b, blockquote, br, caption, cite, code, col, colgroup, dd, dl, dt, em, h1, h2, h3, h4, h5, h6, i, img, li, ol, p, pre, q, small, strike, strong, sub, sup, table, tbody, td, tfoot, th, thead, tr, u, ul。结果不包含标签rel=nofollow ，如果需要可以手动添加。
     */
    private Safelist whiteList;


    /**
     * 配置过滤化参数,不对代码进行格式化
     */
    private Document.OutputSettings outputSettings;


    private HtmlFilter() {
    }

    /**
     * 静态创建HtmlFilter方法
     * @param whiteList 白名单标签
     * @param pretty 是否格式化
     * @return HtmlFilter
     */
    public static HtmlFilter create(Safelist whiteList, boolean pretty) {
        HtmlFilter filter = new HtmlFilter();
        if (whiteList == null) {
            filter.whiteList = Safelist.relaxed();
        }
        //将自定义标签添加进白名单，除开白名单之外的标签都会被过滤
        filter.whiteList.addAttributes("p", "style").addProtocols("img", "src", "data");
        // 这个配置的意思的过滤如果找不到成对的标签，就只过滤单个标签，而不用把后面所有的文本都进行过滤,当<script>标签只有一个时，会<script>标签后面的数据全部过滤
        // filter.whiteList.preserveRelativeLinks(true);

        filter.outputSettings = new Document.OutputSettings().prettyPrint(pretty);
        return filter;
    }

    /**
     * 静态创建HtmlFilter方法
     * @return HtmlFilter
     */
    public static HtmlFilter create() {
        return create(null, false);
    }

    /**
     * 静态创建HtmlFilter方法
     * @param whiteList 白名单标签
     * @return HtmlFilter
     */
    public static HtmlFilter create(Safelist whiteList) {
        return create(whiteList, false);
    }

    /**
     * 静态创建HtmlFilter方法
     * @param excludeTags 例外的特定标签
     * @param includeTags 需要过滤的特定标签
     * @param pretty      是否格式化
     * @return HtmlFilter
     */
    public static HtmlFilter create( List<String> excludeTags,List<String> includeTags, boolean pretty) {
        HtmlFilter filter = create(null, pretty);
        //要过滤的标签
        if (includeTags != null && !includeTags.isEmpty()) {
            String[] tags = (String[]) includeTags.toArray(new String[0]);
            filter.whiteList.removeTags(tags);
        }
        //例外标签
        if (excludeTags != null && !excludeTags.isEmpty()) {
            String[] tags = (String[]) excludeTags.toArray(new String[0]);
            filter.whiteList.addTags(tags);
        }
        return filter;
    }


    /**
     * 静态创建HtmlFilter方法
     * @param excludeTags 例外的特定标签
     * @param includeTags 需要过滤的特定标签
     * @return HtmlFilter
     */
    public static HtmlFilter create(List<String> excludeTags,List<String> includeTags) {
        return create( includeTags, excludeTags, false );
    }

    /**
     * @param content 需要过滤内容
     * @return 过滤后的String
     */
    public String clean(String content) {
        return Jsoup.clean(content, "", this.whiteList, this.outputSettings);

    }
}