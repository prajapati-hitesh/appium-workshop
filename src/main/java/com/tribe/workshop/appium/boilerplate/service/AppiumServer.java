package com.tribe.workshop.appium.boilerplate.service;

import com.tribe.workshop.appium.helpers.DateHelper;
import com.tribe.workshop.appium.helpers.SystemHelper;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class AppiumServer {
    private static final Logger logger = LogManager.getLogger(AppiumServer.class.getName());
    private AppiumDriverLocalService appiumDriverLocalService;

    /***
     *
     * @param atIPAddress IP Address At Which you want to start
     *                    Appium Server
     * @return AppiumDriverLocalService
     */
    public AppiumDriverLocalService buildAppiumServerProcess(String atIPAddress) {
        try {
            String appiumServerLogFileName = SystemHelper.getUserDirectory()
                    + SystemHelper.getFileSeparator()
                    + "appium-server-logs"
                    + SystemHelper.getFileSeparator()
                    + DateHelper.getCurrentTimeStampWithFormatAs("dd-MM-yyyy")
                    + SystemHelper.getFileSeparator()
                    + "appium-server-logs-"
                    + DateHelper.getCurrentTimeStamp()
                    + ".log";

            AppiumServiceBuilder appiumServiceBuilder = getAppiumServiceBuilder(atIPAddress, appiumServerLogFileName);
            appiumServiceBuilder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
            appiumServiceBuilder.withArgument(GeneralServerFlag.LOCAL_TIMEZONE);
            //appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "warn");

            logger.info("Appium Server Log File Can be found here: " + appiumServerLogFileName);

            appiumDriverLocalService = AppiumDriverLocalService.buildService(appiumServiceBuilder);

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return appiumDriverLocalService;
    }

    private static AppiumServiceBuilder getAppiumServiceBuilder(String atIPAddress, String appiumServerLogFileName) {
        File appiumServerFile = new File(appiumServerLogFileName);

        if (!appiumServerFile.getParentFile().exists()) {
            appiumServerFile.getParentFile().mkdirs();
        }

        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
        appiumServiceBuilder.withIPAddress(atIPAddress);
        appiumServiceBuilder.usingAnyFreePort();
        appiumServiceBuilder.withLogFile(appiumServerFile); // Commenting this line for not to write logs to file
        return appiumServiceBuilder;
    }
}
