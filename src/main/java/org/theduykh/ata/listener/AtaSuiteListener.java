package org.theduykh.ata.listener;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class AtaSuiteListener implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        String threads = System.getProperty("threads");
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
