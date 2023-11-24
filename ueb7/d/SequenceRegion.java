package ueb7.d;

import ueb7.b.Sequence;

/**
 * Interface for a region within a {@link Sequence} with start and end position
 * 
 * @author Jan Grau
 *
 */
public interface SequenceRegion {

	/**
	 * Returns the start position of this region
	 * @return the start position
	 */
	public int getStart();
	
	/**
	 * Returns the end position of this region
	 * @return the end position (exclusive)
	 */
	public int getEnd();
	
	/**
	 * The strand of this region
	 * @return the strand
	 */
	public Strand getStrand();

	public Sequence extract(Sequence sequence);
	
}
