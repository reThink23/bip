package ueb7.b;

import ueb7.a.Alphabet;

public class SparseDNASequenceFactory extends AbstractSequenceFactory {
	public Sequence createSequence(Alphabet alphabet, String sequence) {
		return new SparseDNASequence(sequence);
	}
}
