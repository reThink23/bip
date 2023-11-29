package ueb7.e;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import ueb7.a.DNAAlphabet;
import ueb7.b.Sequence;
import ueb7.b.SimpleSequenceFactory;
import ueb7.c.CompositeSequence;
import ueb7.d.Exon;
import ueb7.d.Strand;
import ueb7.d.Transcript;

public class StructureMain {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		DNAAlphabet dnaAlphabet = DNAAlphabet.getInstance();
		List<Sequence> seqs = FastAParser.parseFastA(dnaAlphabet, "C:\\Users\\joela\\Downloads\\extracted.fa", new SimpleSequenceFactory());
		Sequence seq = seqs.get(0);
		Sequence subSequence = seq.getSubSequence(0, 100);
		Exon exon1 = new Exon(20, 50, Strand.PLUS);
		Exon exon2 = new Exon(70, 90, Strand.PLUS);
		Exon exon3 = new Exon(120, 150, Strand.PLUS);
		Sequence[] exons = { exon1.extract(subSequence), exon2.extract(subSequence), exon3.extract(subSequence)};
		Sequence compositeExon = new CompositeSequence(exons);
		Sequence extracted = compositeExon.getSubSequence(0, 80);
		Transcript transcript = new Transcript(new Exon[] {exon1, exon2, exon3});
		Sequence transciptedSeq = transcript.extract(subSequence);
		System.out.println("seq: "+seq);
		System.out.println("subsequence: "+subSequence);
		System.out.println("exons: "+exons);
		System.out.println("compositeExon: "+compositeExon);
		System.out.println("extracted"+extracted);
		System.out.println("transcript: "+transcript);
		System.out.println("transcriptedSeq: "+transciptedSeq);

	}
}
