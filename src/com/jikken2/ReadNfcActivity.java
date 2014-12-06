package com.jikken2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ReadNfcActivity extends ActionBarActivity {
	protected NfcAdapter nfcAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//スーパークラスのonCreateメソッド呼び出し
		super.onCreate(savedInstanceState);
		//レイアウト設定ファイルの指定
		setContentView(R.layout.activity_read_nfc);
	}

	@Override
    protected void onResume() {
        super.onResume();
        
        // NFCを扱うためのインスタンスを取得
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        // NFCが搭載されているかチェック
        if (nfcAdapter != null) {
            // NFC機能が有効になっているかチェック
            if (!nfcAdapter.isEnabled()) {
                // NFC機能が無効の場合はダイヤログでユーザーへ通知
                new AlertDialog.Builder(ReadNfcActivity.this)
                .setTitle("NFC機能が有効になっていません\n設定画面に移行しますか？")
                .setPositiveButton(
                	"はい", 
                	new DialogInterface.OnClickListener() {
                	@Override
                	//はいがクリックされたら設定画面に遷移する
                	public void onClick(DialogInterface dialog, int which) {
                		startActivity(new Intent().setAction(Settings.ACTION_NFC_SETTINGS));
                	}
                })
                .setNegativeButton(
                	"いいえ", 
                	new DialogInterface.OnClickListener() {
                	@Override
                	public void onClick(DialogInterface dialog, int which){
                        new AlertDialog.Builder(ReadNfcActivity.this)
                        .setTitle("本アプリではNFC機能を使用します。アプリを終了します。")
                        .setNegativeButton(
                            	"はい", 
                            	new DialogInterface.OnClickListener() {
                            	@Override
                            	public void onClick(DialogInterface dialog, int which){
                            		finish();
                            	}
                            })
                            .show();
                	}
                })
                .show();
            }
        }
        else {
            // NFC非搭載の場合はユーザーへ通知
            Toast.makeText(getApplicationContext(),
                    "この端末はNFC機能が非搭載です", Toast.LENGTH_SHORT)
                    .show();
        }
        
        if (nfcAdapter != null) {
            // 起動中のActivityが優先的にNFCを受け取れるよう設定
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
            // Activityが非表示になる際に優先的にNFCを受け取る設定を解除
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

            // IDmを取得
            byte[] idm = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
            //IDm文字列をデータベースとのコネクションを行うアクティビティに渡して遷移
            Intent i = new Intent(ReadNfcActivity.this,ConnectionActivity.class);
            String id =  getIdm(idm);
            i.putExtra("ID",id);
            startActivity(i);
        }
    }
    
	/**
	 * byte型変数をString型の文字列に変換する
	 * @param idm byte型のidm変数
	 * @return String型のidm文字列
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
