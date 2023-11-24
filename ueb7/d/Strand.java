package ueb7.d;

/**
 * Enum for the strand of a DNA sequence
 * @author Jan Grau
 *
 */
public enum Strand{
	PLUS,
	MINUS,
	UNDEF;
	
	/**
	 * Parses the strand from a {@link String} representation
	 * @param str the string
	 * @return the strand
	 */
	public static Strand parse(String str) {
		Strand strand = UNDEF;
		if("+".equals(str)) {
			strand = PLUS;
		}else if("-".equals(str)) {
			strand = MINUS;
		}
		return strand;
	}
	
	@Override
	public String toString() {
		if(this == UNDEF) {
			return ".";
		}else if(this == PLUS) {
			return "+";
		}else {
			return "-";
		}
	}
	
}