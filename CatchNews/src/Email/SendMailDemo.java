package Email;

public class SendMailDemo {
	public static void main(String[] args) {
		SendMail sm = new SendMail("smtp.163.com");
		sm.setNamePass("jsxjgood123@163.com", "921004");
		sm.setSubject("����,����");
		sm.setFrom("jsxjgood123@163.com");
		sm.setTo("752377627@qq.com");
		// sm.addFileAffix("f:/adsl.txt");
		StringBuffer bs = new StringBuffer();
		bs.append("�����:\n");
		bs.append("       ���Զ������챦�ε��ڙi!!!!!!!!!!!");
		sm.setBody("DFSAAAAAAAAAAAAAAAAA");
		sm.setNeedAuth(true);
		boolean b = sm.setOut();
		if (b) {
			System.out.println("\n�ʼ����ͳɹ�!!!!!");
		} else {
			System.out.println("�ʼ�����ʧ��!!!!");
		}
	}
}
