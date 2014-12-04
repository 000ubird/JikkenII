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
		//�X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
		super.onCreate(savedInstanceState);
		//���C�A�E�g�ݒ�t�@�C���̎w��
		setContentView(R.layout.activity_read_nfc);
		
        // NFC���������߂̃C���X�^���X���擾
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        // NFC�����ڂ���Ă��邩�`�F�b�N
        if (nfcAdapter != null) {
            // NFC�@�\���L���ɂȂ��Ă��邩�`�F�b�N
            if (!nfcAdapter.isEnabled()) {
                // NFC�@�\�������̏ꍇ�̓��[�U�[�֒ʒm
                Toast.makeText(getApplicationContext(),
                        "NFC�@�\���L���ɂȂ��Ă��܂���",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else {
            // NFC�񓋍ڂ̏ꍇ�̓��[�U�[�֒ʒm
            Toast.makeText(getApplicationContext(),
                    "NFC�@�\���񓋍ڂł�", Toast.LENGTH_SHORT)
                    .show();
        }
	}

	@Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            // �N������Activity���D��I��NFC���󂯎���悤�ݒ�
            Intent intent = new Intent(this, this.getClass())
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    getApplicationContext(), 0, intent, 0);
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null,
                    null);
        }
    }
	
    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            // Activity����\���ɂȂ�ۂɗD��I��NFC���󂯎��ݒ������
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            // IDm���擾
            byte[] idm = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
            Toast.makeText(getApplicationContext(),
                    getIdm(idm), Toast.LENGTH_SHORT)
                    .show();
        }
    }
    
	/**
	 * byte�^�ϐ���String�^�̕�����ɕϊ�����
	 * @param idm byte�^��idm�ϐ�
	 * @return String�^��idm������
	 */
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
