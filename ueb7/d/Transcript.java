package ueb7.d;

import java.util.Arrays;

import ueb7.b.Sequence;
import ueb7.c.CompositeSequence;


/**
 * Class representing a transcript composed of {@link Exon}s
 * 
 * @author Jan Grau
 *
 */
public class Transcript implements SequenceRegion {

	private Exon[] exons;
	
	/**
	 * Constructs an {@link Transcript} object from the given exons.
	 * @param exons the exons
	 */
	public Transcript(Exon...exons) {
		this.exons = exons;
		Arrays.sort(this.exons,(e1,e2) -> Integer.compare(e1.getStart(), e2.getStart()));
		check();
	}

	//ueberprueft, dass exons konsistent sind
	private void check() {
		for(int i=1;i<exons.length;i++) {
			if(exons[i-1].getEnd()>exons[i].getStart()) {
				throw new IllegalArgumentException();
			}
			if(exons[i-1].getStrand() != exons[i].getStrand()) {
				throw new IllegalArgumentException();
			}
		}
	}

	@Override
	public int getStart() {
		return this.exons[0].getStart();
	}

	@Override
	public int getEnd() {
		return this.exons[exons.length-1].getEnd();
	}

	@Override
	public Strand getStrand() {
		return this.exons[0].getStrand();
	}
	
	/**
	 * Returns the number of exons of this transcript
	 * @return the exons
	 */
	public int getNumberOfExons() {
		return this.exons.length;
	}
	
	/**
	 * Returns the idx-th exon of this transcript
	 * @param idx the index of the exon
	 * @return the exon
	 */
	public Exon getExon(int idx) {
		return this.exons[idx];
	}

	@Override
	public Sequence extract(Sequence sequence) {
		return new CompositeSequence(Arrays.copyOf(exons, exons.length, Sequence[].class));
	}

	public String toString() {
		return "Transcript: " + Arrays.toString(exons);
	}
	
	
	
}
