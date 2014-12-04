package com.jikken2;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ReadNfcActivity extends ActionBarActivity {
	protected NfcAdapter nfcAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//ƒX[ƒp[ƒNƒ‰ƒX‚ÌonCreateƒƒ\ƒbƒhŒÄ‚Ño‚µ
		super.onCreate(savedInstanceState);
		//ƒŒƒCƒAƒEƒgÝ’èƒtƒ@ƒCƒ‹‚ÌŽw’è
		setContentView(R.layout.activity_read_nfc);
		
        // NFC‚ðˆµ‚¤‚½‚ß‚ÌƒCƒ“ƒXƒ^ƒ“ƒX‚ðŽæ“¾
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        // NFC‚ª“‹Ú‚³‚ê‚Ä‚¢‚é‚©ƒ`ƒFƒbƒN
        if (nfcAdapter != null) {
            // NFC‹@”\‚ª—LŒø‚É‚È‚Á‚Ä‚¢‚é‚©ƒ`ƒFƒbƒN
            if (!nfcAdapter.isEnabled()) {
                // NFC‹@”\‚ª–³Œø‚Ìê‡‚Íƒ†[ƒU[‚Ö’Ê’m
                Toast.makeText(getApplicationContext(),
                        "NFC‹@”\‚ª—LŒø‚É‚È‚Á‚Ä‚¢‚Ü‚¹‚ñ",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else {
            // NFC”ñ“‹Ú‚Ìê‡‚Íƒ†[ƒU[‚Ö’Ê’m
            Toast.makeText(getApplicationContext(),
                    "NFC‹@”\‚ª”ñ“‹Ú‚Å‚·", Toast.LENGTH_SHORT)
                    .show();
        }
	}
	
	/**
	 * byteŒ^•Ï”‚ðStringŒ^‚Ì•¶Žš—ñ‚É•ÏŠ·‚·‚é
	 * @param idm byteŒ^‚Ìidm•Ï”
	 * @return StringŒ^‚Ìidm•¶Žš—ñ
	 */
	@SuppressLint("InlinedApi")
	private String getIdm(byte[] idm) {
		String buf = null;
		StringBuffer idmByte = new StringBuffer();
		if (idm != null) {
			for (int i = 0; i < idm.length; i++) {
				idmByte.append(Integer.toHexString(idm[i] & 0xff));
			}
			buf = idmByte.toString();
		}
		return buf;
	}
}
