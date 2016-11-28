package configuration;

import com.weitaomi.systemconfig.fileManage.UpYun;
import com.weitaomi.systemconfig.util.DateUtils;
import com.weitaomi.systemconfig.util.StreamUtils;
import common.BaseContextCase;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/14.
 */
public class ImageUpLoadTest extends BaseContextCase {
    @Test
    public void testUpYun() throws IOException {
            File file = new File("C:\\Users\\Administrator\\Desktop\\822308633497096971.png");
            try {
                UpYun upYun = new UpYun("weitaomi", "weitaomi", "Weitaomi@Woyun");
                InputStream fileInputStream = new FileInputStream(file);
                byte[] bytes = StreamUtils.InputStreamTOByte(fileInputStream);
                String imageUrl = "/article/showImage/justForTest00001.png";
                Map map=new HashMap();
                map.put("x-gmkerl-exif-switch",true);
                map.put("x-gmkerl-exif-switch",true);
                boolean flag = upYun.writeFile(imageUrl, bytes);
                System.out.println("=============> Is success? " + imageUrl);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }
}
