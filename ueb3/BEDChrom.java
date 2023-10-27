package ueb3;

import java.util.Comparator;

public class BEDChrom implements Comparator<BEDChrom> {

	private String chrom;
	private Integer chromStart;
	private Integer chromEnd;
	private String name;
	private int score;
	private Strand strand;

	public BEDChrom() {}

	public BEDChrom(String chrom, Integer chromEnd, Integer chromStart, String name, int score, Strand strand) { 
		setAll(chrom, chromEnd, chromStart, name, score, strand);
	}
	public BEDChrom(String chrom, String chromEnd, String chromStart, String name, String score, String strand) { 
		setAll(chrom, Integer.valueOf(chromStart), Integer.valueOf(chromEnd), name, Integer.valueOf(score), symbolToStrand(strand));
	}

	
	public void setChrom(String chrom) { this.chrom = chrom; }
	public void setName(String name) {this.name = name;}
	public void setStrand(Strand strand) {this.strand = strand;}
	
	public void setChromStart(Integer chromStart) {
		if (this.chromEnd != null) if (this.chromEnd < chromStart ) 
		throw new IllegalArgumentException(String.format("chromStart (%d) has to be lower than chromStart (%d)", chromStart, chromEnd));
		this.chromStart = chromStart;
	}
	public void setChromEnd(Integer chromEnd) {
		if (this.chromStart != null) if (this.chromStart > chromEnd ) 
		throw new IllegalArgumentException(String.format("chromEnd (%d) has to be higher than chromStart (%d)", chromEnd, chromStart));
		this.chromEnd = chromEnd;
	}
	
	public void setScore(int score) {
		if (score < 0 || score > 1000 ) throw new IllegalArgumentException(score+" is not a score. The score should be between 0 and 1000");
		this.score = score;
	}

	public void setAll(String chrom, Integer chromEnd, Integer chromStart, String name, int score, Strand strand) {
		setChrom(chrom);
		setChromStart(chromStart);
		setChromEnd(chromEnd);
		setName(name);
		setScore(score);
		setStrand(strand);
	}

	public String getChrom() {return chrom;}
	public int getChromStart() {return chromStart;}
	public int getChromEnd() {return chromEnd;}
	public String getName() {return name;}
	public int getScore() {return score;}
	public Strand getStrand() {return strand;}

	public enum Strand {
		POSITIVE("+"), NEGATIVE("-"), NONE(".");
		
		private final String symbol;
		
		Strand(String symbol) { this.symbol = symbol; }
		public String getSymbol() { return symbol;}
	}

	public static Strand symbolToStrand(String symbol) {
		switch (symbol) {
			case "+":
				return Strand.POSITIVE;
			case "-":
				return Strand.NEGATIVE;
			case ".":
				return Strand.NONE;
			default:
				throw new IllegalArgumentException(symbol+" is not a valid DNA strand orientation symbol. Valid Symbols are: +, -, . (if no strand)");

		}
	}

	// @Override
	public int compare(BEDChrom obj1, BEDChrom obj2) {
		if (obj1.chrom.compareTo(obj2.chrom) < 0 ) return 1;
		if (obj1.chrom.compareTo(obj2.chrom) > 0 ) return -1;
		
		if (obj1.chromStart < obj1.chromStart) return 1;
		if (obj1.chromStart > obj1.chromStart) return -1;
		
		if (obj1.chromEnd < obj1.chromEnd) return 1;
		if (obj1.chromEnd > obj1.chromEnd) return -1;

		return 0;
	}

}