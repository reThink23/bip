package ueb7.c;

import ueb7.a.Alphabet;
import ueb7.b.Sequence;
import ueb7.d.Exon;

public class CompositeSequence implements Sequence {
	private Exon[] exons;

	public CompositeSequence(Exon[] exons) {
		this.exons = exons;
	}

	public int getLength() {
		int length = 0;
		for (Exon exon : exons) {
			length += ((Sequence) exon).getLength();
		}
		return length;
	}

	public char getSymbolAt(int idx) {
		int offset = 0;
		for (Exon exon : exons) {
			int length = ((Sequence) exon).getLength();
			if (idx < offset + length) {
				return ((Sequence) exon).getSymbolAt(idx - offset);
			}
			offset += length;
		}
		throw new IndexOutOfBoundsException();
	}

	public Alphabet getAlphabet() {
		return ((Sequence) exons[0]).getAlphabet();
	}

	public Sequence getSubSequence(int start, int end) {
		if (start < 0 || end > getLength() || start > end) throw new IndexOutOfBoundsException();
		Exon[] subExons = new Exon[exons.length];
		int offset = 0;
		for (int i = 0; i < exons.length; i++) {
			int length = ((Sequence) exons[i]).getLength();
			if (start < offset + length) {
				subExons[i] = (Exon) ((Sequence) exons[i]).getSubSequence(start - offset, end - offset);
			}
			offset += length;
		}
		return new CompositeSequence(subExons);
	}

	public int getCodeAt(int idx) {
		int offset = 0;
		for (Exon exon : exons) {
			int length = ((Sequence) exon).getLength();
			if (idx < offset + length) {
				return ((Sequence) exon).getCodeAt(idx - offset);
			}
			offset += length;
		}
		throw new IndexOutOfBoundsException();
	}
}
