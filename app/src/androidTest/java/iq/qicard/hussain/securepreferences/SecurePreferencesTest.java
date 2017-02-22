package iq.qicard.hussain.securepreferences;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;

@RunWith(MockitoJUnitRunner.class)
public class SecurePreferencesTest {

    private static final String FILE_NAME = "securefile";
    private static final String PASSWORD = "!@#password#@!";

    @Mock
    Context mContext;

    @Test
    public void testInitialization() throws Exception{
        SecurePreferences mPreferences = SecurePreferences.getInstance(mContext, FILE_NAME, PASSWORD);
        Assert.assertNotNull(mPreferences);
    }

}
