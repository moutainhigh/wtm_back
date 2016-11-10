package configuration;

import com.weitaomi.systemconfig.fileManage.UpYun;
import com.weitaomi.systemconfig.util.DateUtils;
import com.weitaomi.systemconfig.util.StreamUtils;
import common.BaseContextCase;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.junit.Test;

import java.io.*;

/**
 * Created by Administrator on 2016/8/14.
 */
public class ImageUpLoadTest extends BaseContextCase {
    @Test
    public void testUpYun() throws IOException {

        for (int i=2;i<7;i++) {
            File file = new File("D:\\Documents\\Tencent Files\\123803374\\FileRecv\\picture.png");
            try {
                UpYun upYun = new UpYun("weitaomi", "weitaomi", "Weitaomi@Woyun");
                InputStream fileInputStream = new FileInputStream(file);
                byte[] bytes = StreamUtils.InputStreamTOByte(fileInputStream);
                String imageUrl = "/article/showImage/common.png";
                boolean flag = upYun.writeFile(imageUrl, bytes);
                System.out.println("=============> Is success? " + imageUrl);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
