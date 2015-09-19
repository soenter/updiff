package com.sand.updiff.ui.ssh;

import com.jcraft.jsch.*;
import org.junit.Test;

import javax.swing.*;

/**
 * @author : sun.mt
 * @date : 2015/9/19 9:11
 * @since 1.0.5
 */
public class JschTest {
	String userName = "test";
	String userPwd = "test";
	String host = "127.0.0.1";
	int port = 22;

	/**
	 * http://www.jcraft.com/jsch/examples/
	 */
	@Test
	public void test_shell () {
		try {
			JSch jsch = new JSch();
			//jsch.setKnownHosts("/home/foo/.ssh/known_hosts");
			Session session = jsch.getSession(userName, host, 22);
			session.setPassword(userPwd);

			UserInfo ui = new MyUserInfo() {
				public void showMessage (String message) {
					JOptionPane.showMessageDialog(null, message);
				}

				public boolean promptYesNo (String message) {
					Object[] options = {"yes", "no"};
					int foo = JOptionPane.showOptionDialog(null,
							message,
							"Warning",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE,
							null, options, options[0]);
					return foo == 0;
				}

				// If password is not given before the invocation of Session#connect(),
				// implement also following methods,
				//   * UserInfo#getPassword(),
				//   * UserInfo#promptPassword(String message) and
				//   * UIKeyboardInteractive#promptKeyboardInteractive()

			};

			session.setUserInfo(ui);

			// It must not be recommended, but if you want to skip host-key check,
			// invoke following,
			// session.setConfig("StrictHostKeyChecking", "no");

			//session.connect();
			session.connect(30000);   // making a connection with timeout.

			Channel channel = session.openChannel("shell");

			// Enable agent-forwarding.
			//((ChannelShell)channel).setAgentForwarding(true);

			channel.setInputStream(System.in);
      /*
      // a hack for MS-DOS prompt on Windows.
      channel.setInputStream(new FilterInputStream(System.in){
          public int read(byte[] b, int off, int len)throws IOException{
            return in.read(b, off, (len>1024?1024:len));
          }
        });
       */

			channel.setOutputStream(System.out);

      /*
      // Choose the pty-type "vt102".
      ((ChannelShell)channel).setPtyType("vt102");
      */

      /*
      // Set environment variable "LANG" as "ja_JP.eucJP".
      ((ChannelShell)channel).setEnv("LANG", "ja_JP.eucJP");
      */

			//channel.connect();
			channel.connect(3 * 1000);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static abstract class MyUserInfo
			implements UserInfo, UIKeyboardInteractive {
		public String getPassword(){ return null; }
		public boolean promptYesNo(String str){ return false; }
		public String getPassphrase(){ return null; }
		public boolean promptPassphrase(String message){ return false; }
		public boolean promptPassword(String message){ return false; }
		public void showMessage(String message){ }
		public String[] promptKeyboardInteractive(String destination,
												  String name,
												  String instruction,
												  String[] prompt,
												  boolean[] echo){
			return null;
		}
	}
}