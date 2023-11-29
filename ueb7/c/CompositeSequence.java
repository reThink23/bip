package ueb7.c;

import java.util.stream.Stream;

import ueb7.a.Alphabet;
import ueb7.b.Sequence;

public class CompositeSequence implements Sequence {
	private Sequence[] sequences;
	private int length;

	public CompositeSequence(Sequence[] sequences) {
		if (sequences == null || 
			sequences.length == 0 || 
			!Stream.of(sequences).allMatch(seq -> sequences[0].getAlphabet().equals(seq.getAlphabet())) 
		) throw new IllegalArgumentException();
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
		if (idx < 0 || idx >= this.length) throw new IndexOutOfBoundsException();
		int offset = 0;
		for (Sequence seq : sequences) {
			int length = seq.getLength();
			if (idx < offset + length) {
				return seq.getSymbolAt(idx - offset);
			}
			offset += length;
		}
		throw new IndexOutOfBoundsException();
	}

	public Alphabet getAlphabet() {
		return sequences[0].getAlphabet();
	}

	public Sequence getSubSequence(int start, int end) {
		if (start < 0 || end > this.length || start > end) throw new IndexOutOfBoundsException("start: " + start + ", end: " + end + ", length: " + this.length + "");
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
		if (idx < 0 || idx >= this.length) throw new IndexOutOfBoundsException();
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

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CompositeSequence("+length+"): ");
		for (Sequence seq : sequences) {
			sb.append(seq.toString());
		}
		return sb.toString();
	}
}
