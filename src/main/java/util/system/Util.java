package util.system;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import util.config.DriverManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rohit on 3/7/2018.
 */
public class Util {

    private static Util util;
    static Logger logger = Logger.getLogger(Util.class);


    private Util() {
    }

    public static Util getInstance() {
        if (util == null) {
            util = new Util();
        }
        return util;
    }

    public void waitForPageToLoad(ExpectedCondition pageLoadCondition, int timeSec) {
        Wait wait = new FluentWait(DriverManager.getDriver())
                .withTimeout(timeSec, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.MILLISECONDS);

        wait.until(pageLoadCondition);
    }

    public String takeScreenShot() {
        String imagePath = "";
        try {
            String start_time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss.SSS").format(new java.util.Date());
            File imageFolder = new File(System.getProperty("user.dir") + "/target/screenshots");
            if (!imageFolder.exists()) {
                imageFolder.mkdir();
            }
            imagePath = imageFolder.getAbsolutePath() + "/" + this.getClass().getSimpleName() + "_" + start_time + ".png";
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                imageFile.delete();
            }
            WebDriver driver = new Augmenter().augment(DriverManager.getDriver());
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, imageFile);
        } catch (IOException e) {
            throw new IllegalStateException("Screenshot path is not valid");
        }
        return imagePath;
    }

    public String captureScreen(File sourceFile) {

        FileInputStream fileInputStreamReader = null;
        String encodedBase64 = null;
        try {
            fileInputStreamReader = new FileInputStream(sourceFile);
            byte[] bytes = new byte[(int) sourceFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "data:image/png;base64," + encodedBase64;
    }
}
