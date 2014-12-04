package com.jikken2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class ReadNfcActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_nfc);
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
