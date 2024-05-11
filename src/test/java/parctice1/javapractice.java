package parctice1;

public class javapractice {

	public static void main(String[] args) {

		String name = "Himanshu Gilhare";
		String sname = "himanshu gilhare";
		String rev = "";
		char cha = name.charAt(9);
		System.out.println(cha);

		int nl = name.length();
		for (int i = 0; i > 0; i++) {

			rev = rev + name.charAt(i);

		}
		System.out.println(rev);
		System.out.println(nl);
		System.out.println(sname.equalsIgnoreCase(name));
		String caps = sname.toUpperCase();
		System.out.println(caps);

		String s = "test automation";
		int len = s.length();
		for (int i = len - 1; i >= 0; i--) {

			System.out.println(s.charAt(i));
		}
		String repl = name.replace(" ", "");
		System.out.println(repl);
		String subs = name.substring(0, 8);
		System.out.println(subs);

	}

}
