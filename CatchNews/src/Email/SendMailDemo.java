package Email;

public class SendMailDemo {
	public static void main(String[] args) {
		SendMail sm = new SendMail("smtp.163.com");
		sm.setNamePass("jsxjgood123@163.com", "921004");
		sm.setSubject("测试,测试");
		sm.setFrom("jsxjgood123@163.com");
		sm.setTo("752377627@qq.com");
		// sm.addFileAffix("f:/adsl.txt");
		StringBuffer bs = new StringBuffer();
		bs.append("裴德万:\n");
		bs.append("       测试度奇珍异宝埼地在檌!!!!!!!!!!!");
		sm.setBody("DFSAAAAAAAAAAAAAAAAA");
		sm.setNeedAuth(true);
		boolean b = sm.setOut();
		if (b) {
			System.out.println("\n邮件发送成功!!!!!");
		} else {
			System.out.println("邮件发送失败!!!!");
		}
	}
}
