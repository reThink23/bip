package ueb7.b;

import ueb7.a.Alphabet;

public class SimpleSequenceFactory extends AbstractSequenceFactory {
	public Sequence createSequence(Alphabet alphabet, String sequence) {
		return new SimpleSequence(alphabet, sequence);
	}
}
