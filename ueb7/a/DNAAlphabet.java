package ueb7.a;

public class DNAAlphabet extends Alphabet {
	
	private DNAAlphabet() {
		super(new char[]{'A','C','G','T'});
	}

	private static DNAAlphabet instance = null;

	public static DNAAlphabet getInstance() {
		if(instance == null) {
			instance = new DNAAlphabet();
		}
		return instance;
	}
}
