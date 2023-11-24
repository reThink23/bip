package ueb7.b;

import ueb7.a.Alphabet;

/**
 * Class of a sequence with alphabet and symbols from that alphabet.
 * Symbols are encoded as contiguous integer numbers to allow for
 * indexing arrays.
 * 
 * @author Jan Grau
 *
 */
public abstract class AbstractSequence implements Sequence {

	/**
	 * The Alphabet of this sequence
	 */
	protected Alphabet alphabet;
	
	/**
	 * Only constructor, only needs an {@link Alphabet}.
	 * @param alphabet the alphabet
	 */
	protected AbstractSequence(Alphabet alphabet) {
		this.alphabet = alphabet;
		
	}
	
	@Override
	public char getSymbolAt(int index) {
		return alphabet.getSymbol(this.getCodeAt(index));
	}
	
	
	@Override
	public Alphabet getAlphabet() {
		return alphabet;
	}
	
	@Override
	public Sequence getSubSequence(int start, int end) {
		return new SubSequence(start,end);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<this.getLength();i++) {
			sb.append(this.getSymbolAt(i));
		}
		return sb.toString();
	}
	
	/**
	 * Class for a sub-sequence of a given sequence.
	 * Implemented as an internal class to consistently re-use the alphabet of the
	 * original {@link Sequence} and the {@link Sequence} object itself.
	 * @author dev
	 *
	 */
	//diese Klasse ist *nicht* static implementiert; Objekte können daher nur innerhalb eines existierenden AbstractSequence-Objektes erzeugt werden
	public class SubSequence implements Sequence{

		private int start;
		private int end;
		
		/**
		 * Sub-sequence from start to end (exclusive) of the enclosing {@link Sequence}.
		 * Used internally by {@link AbstractSequence#getSubSequence(int, int)}.
		 * @param start start
		 * @param end end (exclusive)
		 */
		private SubSequence(int start, int end) {
			if(start < 0) {
				throw new ArrayIndexOutOfBoundsException("Index "+start+" out of bounds"); 
			}
			if(end > AbstractSequence.this.getLength()) {
				throw new ArrayIndexOutOfBoundsException("Index "+end+" out of bounds for length "+AbstractSequence.this.getLength());
			}
			this.start = start;
			this.end = end;
		}
		
		@Override
		public int getCodeAt(int index) {
			if(index >= end-start) {
				throw new ArrayIndexOutOfBoundsException("Index "+index+" out of bounds for length "+(end-start));
			}
			//mit AbstractSequence.this können wir auf die umgebene Instanz von Sequence zugreifen
			return AbstractSequence.this.getCodeAt(start+index);
		}

		@Override
		public char getSymbolAt(int index) {
			return alphabet.getSymbol(this.getCodeAt(index));
		}

		@Override
		public Alphabet getAlphabet() {
			return alphabet;
		}

		@Override
		public int getLength() {
			return end-start;
		}

		@Override
		public Sequence getSubSequence(int start, int end) {
			return new SubSequence(this.start+start,this.start+end);
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<this.getLength();i++) {
				sb.append(alphabet.getSymbol(AbstractSequence.this.getCodeAt(start+i)));
			}
			return sb.toString();
		}
		
	}
	
	
	
}
