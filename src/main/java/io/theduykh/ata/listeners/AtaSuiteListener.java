package io.theduykh.ata.listeners;

import io.theduykh.ata.utils.ConfigReader;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class AtaSuiteListener implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        String threads = ConfigReader.getString("thread");
        if (threads == null || threads.isEmpty()) {
            suite.getXmlSuite().setDataProviderThreadCount(1);
        } else {
            try {
                suite.getXmlSuite().setDataProviderThreadCount(Integer.parseInt(threads));
            } catch (NumberFormatException e) {
                suite.getXmlSuite().setDataProviderThreadCount(1);
            }

        }
    }
}
