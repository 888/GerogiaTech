public class Main {
	public static void main(String[] args) {
		SecretMission sm = new SecretMission("Secret","Mission",SecurityClearance.CONFIDENTIAL);

		try{
			sm.unlockInfo(SecurityClearance.TOP_SECRET);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}sm.setDescription("gah");
		System.out.println(sm.toString());
	}
}
