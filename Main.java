import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Main {

	// **********************
	// エントリポイント
	// **********************
	public static void main(String[] args) {
		Main thisClass = new Main();
	}

	// **********************
	// コンストラクタ
	// **********************
	public Main() {
		super();
		my_acton();
	}

	// **********************
	// 初期処理
	// **********************
	private void my_acton() {

		MyTool out = new MyTool(">>>");
		out.println("処理開始");

		// **********************
		// プロパティオブジェクトを作成
		// プロパティオブジェクトは、extends Hashtable(連想配列)
		// **********************
		Properties props = new Properties();
	
		// **********************
		// * 連想配列に送信用サーバのアドレスをセット
		// **********************

		// gmail の場合は、以下のリンクより、安全性の低いアプリの許可: 有効 に変更
		// https://myaccount.google.com/security?pli=1#connectedapps
		// props.put("mail.smtp.host","smtp.gmail.com");

		// 以下 Yahoo!
		props.put("mail.smtp.host","smtp.mail.yahoo.co.jp");

//		props.put("mail.smtp.port","587");	// サブミッションポート
		props.put("mail.smtp.auth", "true" );	// SMTP 認証を行う

		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.socketFactory.port", "465");

		// **********************
		// メール用のセッションを作成
		// **********************
		SimpleAuthenticator sa =
			new SimpleAuthenticator("ユーザID","パスワード");
		Session MailSession = 
			Session.getInstance( props, sa );
	
		try {
	
			// **********************
			// メール用のメッセージオブジェクトを作成
			// **********************
			MimeMessage msg = new MimeMessage(MailSession);
	
			// **********************
			// 宛先
			// **********************
			msg.setRecipients(
				Message.RecipientType.TO,
				"メールアドレス"
			);
	
			// **********************
			// 送信者
			// **********************
			msg.setFrom(
				new InternetAddress( "ユーザID@yahoo.co.jp", "私です", "ISO-2022-JP")
			);
	
			// **********************
			// 件名
			// **********************
			msg.setSubject(
				MimeUtility.encodeText(
					"日本語件名",
					"iso-2022-jp",
					"B"
				)
			);
	
			// **********************
			// 本文
			// **********************
			msg.setContent(
				"本文\n本文",
				"text/plain; charset=\"iso-2022-jp\""
			);
	
			// **********************
			// 送信
			// **********************
			Transport.send( msg );
	
		}
		catch (Exception e) {
			out.println("送信エラー");
			e.printStackTrace();
		}

		out.println("処理終了");

	}

	// **********************
	// 内部用クラス
	// **********************
	private class MyTool {

		private String mark = null;

		MyTool(String pm) {
			mark = pm;
		}

		public void println(String str) {
			StringBuilder sb = new StringBuilder();
			sb.append(this.mark);
			sb.append(str);
			String s = sb.toString();		
			System.out.println( s );
		}

	}

	// **************************
	// 認証用のプライベートクラス
	// **************************
	private class SimpleAuthenticator extends Authenticator {
	
		private String user_string = null;
		private String pass_string = null;
	
		public SimpleAuthenticator( String user_s, String pass_s ) {
			super();
			user_string = user_s;
			pass_string = pass_s;
		}
	
		protected PasswordAuthentication getPasswordAuthentication(){
			return new PasswordAuthentication( this.user_string, this.pass_string );
		}
	}

}
