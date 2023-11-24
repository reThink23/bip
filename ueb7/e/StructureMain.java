package ueb7.e;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import ueb7.a.DNAAlphabet;
import ueb7.b.Sequence;
import ueb7.b.SimpleSequenceFactory;
import ueb7.d.Exon;
import ueb7.d.SequenceRegion;
import ueb7.d.Strand;

public class StructureMain {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		DNAAlphabet dnaAlphabet = DNAAlphabet.getInstance();
		List<Sequence> seqs = FastAParser.parseFastA(dnaAlphabet, "C:\\Users\\joela\\Downloads\\extracted.fa", new SimpleSequenceFactory());
		Sequence seq = seqs.get(0);
		SequenceRegion region = (SequenceRegion) new Exon(1, 10, Strand.PLUS).extract(seq);

	}
}
