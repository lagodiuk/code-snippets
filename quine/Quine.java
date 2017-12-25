package quine;
class Quine {
	static String s = "package quine;%nclass Quine {%n	static String s = \"%s\";%n	public static void main(String... args) {%n		int i = s.indexOf((char) 34);%n		char l = 92;%n		System.out.printf(s, new StringBuilder(s).insert(i, l).insert(i + 4, l));%n	}%n}";
	public static void main(String... args) {
		int i = s.indexOf((char) 34);
		char l = 92;
		System.out.printf(s, new StringBuilder(s).insert(i, l).insert(i + 4, l));
	}
}