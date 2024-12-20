package Listeners;

import Pages.P02_HomePage;
import Utilities.LogsUtil;
import Utilities.Utility;
import io.qameta.allure.Allure;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static DriverFactory.DriverFactory.getDriver;
import static java.nio.file.Files.readString;

public class IInvokedMethodListenerClass implements IInvokedMethodListener {


    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        //Utility.takeFullScreenShotShutterBug(getDriver(),new P02_HomePage(getDriver()).getnumberOfProductsOnTheCart());
        File logFile = Utility.getLatestFile(LogsUtil.LOG_PATH);
        try {
            assert logFile!=null;
            Allure.addAttachment("logs.log", readString(Path.of(logFile.getPath())));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        if(testResult.getStatus()==ITestResult.FAILURE)
        {
            LogsUtil.info("Test Case " + testResult.getName() + " failed");
            Utility.takeScreenShot(getDriver(),testResult.getName());
        }
    }
}
