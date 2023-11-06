package ueb3;

import java.util.Comparator;

public record BEDChrom (String chrom, Integer chromStart, Integer chromEnd, String name, Integer score, Strand strand) implements Comparator<BEDChrom>, Comparable<BEDChrom> {

	public BEDChrom(String chrom, Integer chromStart, Integer chromEnd, String name, Integer score, Strand strand) {
		if (chrom == null || chromStart == null || chromEnd == null || name == null || strand == null) 
			throw new IllegalArgumentException("Chrom, chromStart, chromEnd, name and strand have to be set");
		if (score < 0 || score > 1000 ) throw new IllegalArgumentException(score+" is not a score. The score should be between 0 and 1000");
		if (chromEnd <= chromStart ) 
			throw new IllegalArgumentException(String.format("chromStart (%d) has to be lower than chromStart (%d)", chromStart, chromEnd));
		
		this.chrom = chrom;
		this.chromStart = chromStart;
		this.chromEnd = chromEnd;
		this.name = name;
		this.score = score;
		this.strand = strand;

	}
	public BEDChrom(String chrom, String chromStart, String chromEnd, String name, String score, String strand) {
		this(chrom, Integer.valueOf(chromStart), Integer.valueOf(chromEnd), name, score.equals(".") ? null : Integer.valueOf(score), symbolToStrand(strand));
	}

	public enum Strand {
		POSITIVE("+"), NEGATIVE("-"), NONE(".");
		
		private final String symbol;

		Strand(String symbol) { this.symbol = symbol; }

		public static Strand toStrand(String symbol) { return symbolToStrand(symbol);}
		public String toString() { return (this == NONE) ? "." : (this == POSITIVE) ? "+" : "-";}
	}

	private static Strand symbolToStrand(String symbol) {
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

	@Override
	public int compare(BEDChrom obj1, BEDChrom obj2) {
		return obj1.compareTo(obj2);
	}

	@Override
	public int compareTo(BEDChrom o2) {
		if (this.chrom.compareTo(o2.chrom) < 0 ) return -1;
		if (this.chrom.compareTo(o2.chrom) > 0 ) return 1;
		
		if (this.chromStart < o2.chromStart) return -1;
		if (this.chromStart > o2.chromStart) return 1;
		
		if (this.chromEnd < o2.chromEnd) return -1;
		if (this.chromEnd > o2.chromEnd) return 1;

		return 0;
	}

	@Override
	public String toString() {
		return String.format("%s|%d|%d|%s|%d|%s", chrom, chromStart, chromEnd, name, score, String.valueOf(strand));
	}


}