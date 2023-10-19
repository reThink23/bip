package ueb1;

public class Helper {

	public static String padding(String s, int length, String padChar, char direction) {
		int pad;
		switch (direction) {
			case 'c':
				pad = (length - s.length()) / 2;
				break;
			case 'r':
			case 'l':
			default:
				pad = length - s.length();
				break;
		}

		String padding = "";
		for (int i = 0; i < pad; i++) {
			padding += padChar;
		}
		String res;
		switch (direction) {
			case 'c':
				res = padding + s + padding;
				break;
			case 'l':
				res = padding + s;
				break;
			case 'r':
			default:
				res = s + padding;
				break;
		}
		return res.length() < length ? res + padChar : res;
	}

	public static String padding(String s, int length, String padChar) {
		return padding(s, length, padChar, 'c');
	}

	public static String padding(String s, int length) {
		return padding(s, length, " ", 'c');
	}

	public static String truncate(String s, int length) {
		if (s.length() > length) {
			return s.substring(0, length - 1) + "_";
		}
		return s;
	}

	public static String printTable(long[][] table) {
		int padLen = 7;
		String s = "";
		s += padding(truncate(" ", 4), padLen) + "|";
		for (int i = 0; i < table[0].length; i++) {
			s += padding(truncate(" ", 4), padLen) + "|";
		}
		s += "\n" + padding("", table[0].length * (padLen + 1) + 0, "-") + "\n";
		for (int i = 0; i < table.length; i++) {
			s += padding(truncate(" ", 4), padLen) + "|";
			for (int j = 0; j < table[i].length; j++) {
				s += padding(truncate(String.valueOf(table[i][j]), 4), padLen) + "|";
			}
			s += "\n";
		}
		return s;
	}

	public static String printTable(long[][] table, String[] header, String[] properties) {
		int padLen = 12;
		int truncateAt = 9;
		String s = "";
		s += padding(truncate(" ", truncateAt), padLen) + "|";
		for (int i = 0; i < table[0].length; i++) {
			s += padding(truncate(header[i], truncateAt), padLen) + "|";
		}
		s += "\n" + padding("", (table[0].length + 1) * (padLen + 1) + 0, "-") + "\n";
		for (int i = 0; i < table.length; i++) {
			s += padding(truncate(properties[i], truncateAt), padLen) + "|";
			for (int j = 0; j < table[i].length; j++) {
				s += padding(truncate(String.valueOf(table[i][j]), truncateAt), padLen) + "|";
			}
			s += "\n";
		}
		return s;
	}

	public static int maxCharWord(String[] strArr) {
		int max = 0;
		for (int i = 0; i < strArr.length; i++) {
			if (strArr[i].length() > max) {
				max = strArr[i].length();
			}
		}
		return max;
	}

	public static int maxCharWord(String[][] str2d) {
		int max = 0;
		for (int i = 0; i < str2d.length; i++) {
			for (int j = 0; j < str2d[i].length; j++) {
				if (str2d[i][j].length() > max) {
					max = str2d[i][j].length();
				}
			}
		}
		return max;
	}
}
