package ueb7.c;

import ueb7.a.Alphabet;
import ueb7.b.Sequence;

public class CompositeSequence implements Sequence {
	private Sequence[] sequences;
	private int length;

	public CompositeSequence(Sequence[] sequences) {
		this.sequences = sequences;
		int length = 0;
		for (Sequence seq : sequences) {
			length += seq.getLength();
		}
		this.length = length;
	}

	public int getLength() {
		return length;
	}

	public char getSymbolAt(int idx) {
		int offset = 0;
		for (Sequence seq : sequences) {
			int length = ((Sequence) seq).getLength();
			if (idx < offset + length) {
				return ((Sequence) seq).getSymbolAt(idx - offset);
			}
			offset += length;
		}
		throw new IndexOutOfBoundsException();
	}

	public Alphabet getAlphabet() {
		return sequences[0].getAlphabet();
	}

	public Sequence getSubSequence(int start, int end) {
		if (start < 0 || end > this.length || start > end) throw new IndexOutOfBoundsException();
		Sequence[] subSequences = new Sequence[sequences.length];
		int offset = 0;
		for (int i = 0; i < sequences.length; i++) {
			int length = sequences[i].getLength();
			if (start < offset + length) {
				int subStart = start - offset;
				int subEnd = end - offset;
				if (subEnd > length) subEnd = length;
				subSequences[i] = sequences[i].getSubSequence(subStart, subEnd);
			}
			offset += length;
		}
		return new CompositeSequence(subSequences);
	}

	public int getCodeAt(int idx) {
		int offset = 0;
		for (Sequence seq : sequences) {
			int length = seq.getLength();
			if (idx < offset + length) {
				return seq.getCodeAt(idx - offset);
			}
			offset += length;
		}
		throw new IndexOutOfBoundsException();
	}
}
