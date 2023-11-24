package ueb7.a;

/**
 * Class for the alphabet of a sequence/string.
 * @author Jan Grau
 *
 */
public class Alphabet {

	// the symbols as chars
	private char[] symbols;
	
	//a map from integer values of chars to contiguous indexes
	private int[] map;
	
	/**
	 * Construct an {@link Alphabet} from its symbols
	 * @param symbols the symbols
	 */
	public Alphabet(char[] symbols) {
		this.symbols = symbols;
		
		//fuer die map wollen wir erstmal wissen,
		//was der maximale ASCII-Wert in symbols ist
		int max = 0;
		for(char c : symbols) {
			int temp = (int)c;
			if(temp > max) {
				max = temp;
			}
		}
		//mit der Groesse legen wir dann die map an
		this.map = new int[max+1];
		
		int i = 0;
		for(int j = 0;j<symbols.length;j++,i++) {
			//hier werden symbols implizit auf int gecastet
			map[symbols[j]] = i;
		}
	}
	
	
	/**
	 * Returns the integer code for the given symbol
	 * @param symbol the symbol
	 * @return the code
	 */
	public int getCode(char symbol) {
		return map[symbol];
	}
	
	/**
	 * Returns the symbol for the given integer code
	 * @param code the code
	 * @return the symbol
	 */
	public char getSymbol(int code) {
		return symbols[code];
	}
	
	/**
	 * Returns the number of symbols in this {@link Alphabet},
	 * which is also the length of arrays that can be
	 * indexed by {@link Alphabet#getCode(char)}
	 * @return the number of symbols
	 */
	public int getNumberOfSymbols() {
		return symbols.length;
	}
	
	@Override
	public boolean equals(Object o2) {
		if(o2 instanceof Alphabet a) {
			if(this.symbols.length != a.symbols.length) {
				return false;
			}
			for(int i=0;i<this.symbols.length;i++) {
				if(this.symbols[i] != a.symbols[i]) {
					return false;
				}
			}
			return true;
		}else {
			return false;
		}
	}
	
}
