package util.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by USER on 4/18/2018.
 */
public class SystemUtils {

    private static final String TASKLIST = "tasklist";
    private static final String KILL = "taskkill /F /IM";
    private static final String chromePath = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
    private static HashMap hashMap = new HashMap();

    private static boolean IsProcessRunning(String serviceName) throws IOException {

        Process process = Runtime.getRuntime().exec(TASKLIST);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;

        do {
            if ((line = bufferedReader.readLine()) == null) {
                return false;
            }
        } while (!line.contains(serviceName));

        return true;
    }

    public static void killProcess(String serviceName) throws IOException {
        if (IsProcessRunning(serviceName)) {
            Runtime.getRuntime().exec(String.format(KILL + "  %s", serviceName));
        }
    }

    public static void openReportInChrome() throws IOException {
//        String reportName = String.valueOf(SystemU().get("reportName"));
        String path = System.getProperty("user.dir") + File.separator + "target" + File.separator + "reportName";
        Runtime.getRuntime().exec(String.format(chromePath + "  %s", path));
    }



    public static void addScreenshotToReport() {
        String imgPath = Util.getInstance().takeScreenShot();
    }



}
