package ueb7.d;

import ueb7.b.Sequence;

/**
 * Class representing an exon on a DNA sequence.
 * 
 * @author Jan Grau
 *
 */
public class Exon implements SequenceRegion {

	private int start;
	private int end;
	private Strand strand;
	
	/**
	 * Constructs an {@link Exon} object with the given coordinates
	 * @param start the start position
	 * @param end the end position (exclusive)
	 * @param strand the strand
	 */
	public Exon(int start, int end, Strand strand) {
		if(start>end) {
			throw new IllegalArgumentException();
		}
		this.start = start;
		this.end = end;
		this.strand = strand;
	}
	
	@Override
	public int getStart() {
		return start;
	}

	@Override
	public int getEnd() {
		return end;
	}

	@Override
	public Strand getStrand() {
		return strand;
	}

	@Override
	public Sequence extract(Sequence sequence) {
		return sequence.getSubSequence(start, end);
	}

	public String toString() {
		return "Exon: " + start + "-" + end + " " + strand;
	}

}
