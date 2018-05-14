package tv.superawesomeadvertiser.test.demoapp;

import android.app.Application;
import android.content.Intent;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.json.JSONObject;

import tv.superawesome.lib.samodelspace.referral.SAReferral;
import tv.superawesome.sdk.advertiser.referral.SAReceiver;

public class SACPI_SAReferral_Tests extends ApplicationTestCase<Application> {

    public SACPI_SAReferral_Tests () {
        super(Application.class);
    }

    @SmallTest
    public void testSAReferral_Create1 () {

        SAReceiver referral = new SAReceiver(getContext());
        assertNotNull(referral);

    }

    @SmallTest
    public void testSAReferral_parseReferralResponse1_1 () {

        SAReceiver referral = new SAReceiver(getContext());
        assertNotNull(referral);

        String source = "utm_source=1&utm_campaign=231&utm_term=2091&utm_content=21&utm_medium=8921";
        SAReferral result = referral.parseReferralResponse(source);

        assertNotNull(result);
        assertEquals(1, result.configuration);
        assertEquals(231, result.campaignId);
        assertEquals(2091, result.lineItemId);
        assertEquals(21, result.creativeId);
        assertEquals(8921, result.placementId);
        assertTrue(result.isValid());

    }

    @SmallTest
    public void testSAReferral_parseReferralResponse1_2 () {

        SAReceiver referral = new SAReceiver(getContext());
        assertNotNull(referral);

        String source = "utm_source%3D1%26utm_campaign%3D231%26utm_term%3D2091%26utm_content%3D21%26utm_medium%3D8921";
        SAReferral result = referral.parseReferralResponse(source);

        assertNotNull(result);
        assertEquals(1, result.configuration);
        assertEquals(231, result.campaignId);
        assertEquals(2091, result.lineItemId);
        assertEquals(21, result.creativeId);
        assertEquals(8921, result.placementId);
        assertTrue(result.isValid());

    }

    @SmallTest
    public void testSAReferral_parseReferralResponse1_3 () {

        SAReceiver referral = new SAReceiver(getContext());
        assertNotNull(referral);

        String source = "utm_campaign=231&utm_content=21&utm_medium=8921";
        SAReferral result = referral.parseReferralResponse(source);

        assertNotNull(result);
        assertEquals(-1, result.configuration);
        assertEquals(231, result.campaignId);
        assertEquals(-1, result.lineItemId);
        assertEquals(21, result.creativeId);
        assertEquals(8921, result.placementId);
        assertFalse(result.isValid());

    }

    @SmallTest
    public void testSAReferral_parseReferralResponse1_4 () {

        SAReceiver referral = new SAReceiver(getContext());
        assertNotNull(referral);

        String source = "utm_sotm_c*8saasasaassaaa/\\ampaign\"%3D231%26utm_term%3D2091%26utm_medium%3D8921";
        SAReferral result = referral.parseReferralResponse(source);

        assertNotNull(result);
        assertEquals(-1, result.configuration);
        assertEquals(-1, result.campaignId);
        assertEquals(-1, result.lineItemId);
        assertEquals(-1, result.creativeId);
        assertEquals(-1, result.placementId);
        assertFalse(result.isValid());

    }

    @SmallTest
    public void testSAReferral_parseReferralResponse1_5 () {

        SAReceiver referral = new SAReceiver(getContext());
        assertNotNull(referral);

        SAReferral result = referral.parseReferralResponse((String)null);

        assertNotNull(result);
        assertEquals(-1, result.configuration);
        assertEquals(-1, result.campaignId);
        assertEquals(-1, result.lineItemId);
        assertEquals(-1, result.creativeId);
        assertEquals(-1, result.placementId);
        assertFalse(result.isValid());

    }

    @SmallTest
    public void testSAReferral_parseReferralResponse1_6 () {

        SAReceiver referral = new SAReceiver(getContext());
        assertNotNull(referral);

        Intent intent = new Intent();
        intent.putExtra("referrer", "utm_source=1&utm_campaign=231&utm_term=2091&utm_content=21&utm_medium=8921");

        SAReferral result = referral.parseReferralResponse(intent);

        assertNotNull(result);
        assertEquals(1, result.configuration);
        assertEquals(231, result.campaignId);
        assertEquals(2091, result.lineItemId);
        assertEquals(21, result.creativeId);
        assertEquals(8921, result.placementId);
        assertTrue(result.isValid());

    }

    @SmallTest
    public void testSAReferral_parseReferralResponse1_7 () {

        SAReceiver referral = new SAReceiver(getContext());
        assertNotNull(referral);

        Intent intent = new Intent();
        SAReferral result = referral.parseReferralResponse(intent);

        assertNotNull(result);
        assertEquals(-1, result.configuration);
        assertEquals(-1, result.campaignId);
        assertEquals(-1, result.lineItemId);
        assertEquals(-1, result.creativeId);
        assertEquals(-1, result.placementId);
        assertFalse(result.isValid());

    }

    @SmallTest
    public void testSAReferral_parseReferralResponse1_8 () {

        SAReceiver referral = new SAReceiver(getContext());
        assertNotNull(referral);

        SAReferral result = referral.parseReferralResponse((Intent)null);

        assertNotNull(result);
        assertEquals(-1, result.configuration);
        assertEquals(-1, result.campaignId);
        assertEquals(-1, result.lineItemId);
        assertEquals(-1, result.creativeId);
        assertEquals(-1, result.placementId);
        assertFalse(result.isValid());

    }

    @SmallTest
    public void testSAReferral_getReferralCustomData1_1 () {

        SAReceiver referral = new SAReceiver(getContext());
        assertNotNull(referral);

        SAReferral referralData = new SAReferral(
                1,      // config
                231,    // campaign
                2091,   // line item
                21,     // creative
                8921    // placement
        );
        JSONObject result = referral.getReferralCustomData(referralData);

        assertNotNull(result);
        assertTrue(result.has("ct"));
        assertTrue(result.has("rnd"));
        assertTrue(result.has("data"));
        assertNotNull(result.opt("data"));
        String data = result.optString("data");
        assertNotNull(data);
        assertTrue(data.contains("type%22%3A%22custom.referred_install"));
        assertTrue(data.contains("creative%22%3A21"));
        assertTrue(data.contains("line_item%22%3A2091"));
        assertTrue(data.contains("placement%22%3A8921"));

    }

    @SmallTest
    public void testSAReferral_getReferralCustomData1_2 () {

        SAReceiver referral = new SAReceiver(getContext());
        assertNotNull(referral);

        JSONObject result = referral.getReferralCustomData(null);

        assertNotNull(result);
        assertEquals("{}", result.toString());

    }

    @SmallTest
    public void testSAReferral_getReferralInstallSession1_1 () {

        SAReceiver referral = new SAReceiver(getContext());
        assertNotNull(referral);

        SAReferral referralData = new SAReferral(
                1,      // config
                231,    // campaign
                2091,   // line item
                21,     // creative
                8921    // placement
        );

        SASession session = referral.getReferralInstallSession(referralData);

        assertNotNull(session);
        assertEquals(SAConfiguration.STAGING, session.getConfiguration());
    }

    @SmallTest
    public void testSAReferral_getReferralInstallSession1_2 () {

        SAReceiver referral = new SAReceiver(getContext());
        assertNotNull(referral);

        SAReferral referralData = new SAReferral(
                0,      // config
                231,    // campaign
                2091,   // line item
                21,     // creative
                8921    // placement
        );

        SASession session = referral.getReferralInstallSession(referralData);

        assertNotNull(session);
        assertEquals(SAConfiguration.PRODUCTION, session.getConfiguration());
    }

    @SmallTest
    public void testSAReferral_getReferralInstallSession1_3 () {

        SAReceiver referral = new SAReceiver(getContext());
        assertNotNull(referral);

        SAReferral referralData = new SAReferral(
                4,      // wrong config - 4 is not value so it should be STAGING
                231,    // campaign
                2091,   // line item
                21,     // creative
                8921    // placement
        );

        SASession session = referral.getReferralInstallSession(referralData);

        assertNotNull(session);
        assertEquals(SAConfiguration.STAGING, session.getConfiguration());
    }

    @SmallTest
    public void testSAReferral_getReferralUrl1_1 () {

        SAReceiver referral = new SAReceiver(getContext());
        assertNotNull(referral);

        SAReferral referralData = new SAReferral(
                1,      // config
                231,    // campaign
                2091,   // line item
                21,     // creative
                8921    // placement
        );
        String result = referral.getReferralUrl(referralData);

        assertNotNull(result);
        assertTrue(result.contains("https://ads.staging.superawesome.tv/v2/event"));
        assertTrue(result.contains("ct="));
        assertTrue(result.contains("rnd="));
        assertTrue(result.contains("data="));
        assertTrue(result.contains("type%22%3A%22custom.referred_install"));
        assertTrue(result.contains("creative%22%3A21"));
        assertTrue(result.contains("line_item%22%3A2091"));
        assertTrue(result.contains("placement%22%3A8921"));
    }

    @SmallTest
    public void testSAReferral_getReferralUrl1_2 () {

        SAReceiver referral = new SAReceiver(getContext());
        assertNotNull(referral);

        String result = referral.getReferralUrl(null);

        assertNotNull(result);
        assertTrue(result.contains("https://ads.superawesome.tv/v2/event"));
        assertFalse(result.contains("ct="));
        assertFalse(result.contains("rnd="));
        assertFalse(result.contains("data="));
        assertFalse(result.contains("type%22%3A%22custom.referred_install"));
        assertFalse(result.contains("creative%22%3A21"));
        assertFalse(result.contains("line_item%22%3A2091"));
        assertFalse(result.contains("placement%22%3A8921"));
    }

    @SmallTest
    public void testSAReferral_Create2 () {

        SAReceiver referral = new SAReceiver(null);
        assertNotNull(referral);

    }

    @SmallTest
    public void testSAReferral_parseReferralResponse2_1 () {

        SAReceiver referral = new SAReceiver(null);
        assertNotNull(referral);

        String source = "utm_source=1&utm_campaign=231&utm_term=2091&utm_content=21&utm_medium=8921";
        SAReferral result = referral.parseReferralResponse(source);

        assertNotNull(result);
        assertEquals(1, result.configuration);
        assertEquals(231, result.campaignId);
        assertEquals(2091, result.lineItemId);
        assertEquals(21, result.creativeId);
        assertEquals(8921, result.placementId);
        assertTrue(result.isValid());

    }

    @SmallTest
    public void testSAReferral_getReferralCustomData2_1 () {

        SAReceiver referral = new SAReceiver(null);
        assertNotNull(referral);

        SAReferral referralData = new SAReferral(
                1,      // config
                231,    // campaign
                2091,   // line item
                21,     // creative
                8921    // placement
        );
        JSONObject result = referral.getReferralCustomData(referralData);

        assertNotNull(result);
        assertTrue(result.has("ct"));
        assertTrue(result.has("rnd"));
        assertTrue(result.has("data"));
        assertNotNull(result.opt("data"));
        String data = result.optString("data");
        assertNotNull(data);
        assertTrue(data.contains("type%22%3A%22custom.referred_install"));
        assertTrue(data.contains("creative%22%3A21"));
        assertTrue(data.contains("line_item%22%3A2091"));
        assertTrue(data.contains("placement%22%3A8921"));

    }

    @SmallTest
    public void testSAReferral_getReferralInstallSession2_1 () {

        SAReceiver referral = new SAReceiver(null);
        assertNotNull(referral);

        SAReferral referralData = new SAReferral(
                1,      // config
                231,    // campaign
                2091,   // line item
                21,     // creative
                8921    // placement
        );

        SASession session = referral.getReferralInstallSession(referralData);

        assertNotNull(session);
        assertEquals(SAConfiguration.STAGING, session.getConfiguration());
    }

    @SmallTest
    public void testSAReferral_getReferralUrl2_1 () {

        SAReceiver referral = new SAReceiver(null);
        assertNotNull(referral);

        SAReferral referralData = new SAReferral(
                1,      // config
                231,    // campaign
                2091,   // line item
                21,     // creative
                8921    // placement
        );
        String result = referral.getReferralUrl(referralData);

        assertNotNull(result);
        assertTrue(result.contains("https://ads.staging.superawesome.tv/v2/event"));
        assertTrue(result.contains("ct="));
        assertTrue(result.contains("rnd="));
        assertTrue(result.contains("data="));
        assertTrue(result.contains("type%22%3A%22custom.referred_install"));
        assertTrue(result.contains("creative%22%3A21"));
        assertTrue(result.contains("line_item%22%3A2091"));
        assertTrue(result.contains("placement%22%3A8921"));
    }
}
