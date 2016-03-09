package com.cybersource.inappsdk;

import android.content.Intent;
import android.test.ServiceTestCase;

import com.cybersource.inappsdk.connectors.inapp.InAppConnectionService;

/**
 * Created by fzubair on 10/21/2015.
 */
public class InAppConnectionServiceTest extends ServiceTestCase<InAppConnectionService> {

    /**
     * Constructor
     *
     */
    public InAppConnectionServiceTest() {
        super(InAppConnectionService.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    public void testStartActionConnect() throws Exception {
        //InAppConnectionService.startActionConnect(getSystemContext(), null, null);
    }

    public void testOnHandleIntent() throws Exception {
        Intent intent = new Intent(getSystemContext(), InAppConnectionService.class);
        intent.setAction(InAppConnectionService.ACTION_CONNECT);
        //Bundle bundle = new Bundle();
        //bundle.putString(EXTRA_PARAM_ENVELOPE_ENCODING, envelope.getEncoding());
        //bundle.putParcelable(EXTRA_PARAM_CONNECTION_CALLBACK, callback);
        //intent.putExtras(bundle);
        startService(intent);

        assertNotNull(getService());
    }

    public void testOnPostHandleAction() throws Exception {

    }
}