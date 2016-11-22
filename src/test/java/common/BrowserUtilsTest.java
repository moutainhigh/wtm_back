package common;

import com.weitaomi.systemconfig.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */
public class BrowserUtilsTest extends BaseContextCase{
    @Test
    public void testBrowserUtils() throws IOException {
        Document doc = Jsoup.connect("http://mp.weixin.qq.com/s?__biz=MzI5NjUyODE5Mg==&mid=2247483656&idx=1&sn=97b1882168499f4534248b2033a14b3d&chksm=ec43b127db34383196c15b88bcf80658bd17a81e66884a3b3bc9654f8ccfff5af795991941fe&scene=0#wechat_redirect").get();
        Elements elements = doc.body().getElementsByTag("img");
        String certainUrl="";
        List<String> imgList=new ArrayList<>();
        if (!elements.isEmpty()) {
            for (Element element : elements) {
                String url = element.attr("data-src");
                Double rate = Double.valueOf(element.attr("data-ratio"));
                if (!StringUtil.isEmpty(url)) {
                    if (rate > 0.5 && rate < 1.5) {
                        imgList.add(url);
                    }
                }
                if (rate == 1.0) {
                    certainUrl = url;
                    break;
                }
            }
        }
        if (StringUtil.isEmpty(certainUrl)){
            if (imgList.isEmpty()){
                certainUrl="http://weitaomi.b0.upaiyun.com/article/showImage/common.png";
            } else {
                certainUrl=imgList.get(0);
            }
        }
        System.out.println(certainUrl);
    }

}
