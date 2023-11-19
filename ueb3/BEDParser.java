package ueb3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// import blatt2.IOAufgabe;

public class BEDParser {

	public enum Strand{
		PLUS,
		MINUS,
		UNDEF;
		
		public static Strand parse(String str) {
			Strand strand = UNDEF;
			if("+".equals(str)) {
				strand = PLUS;
			}else if("-".equals(str)) {
				strand = MINUS;
			}
			return strand;
		}
		
		public String toString() {
			if(this == UNDEF) {
				return ".";
			}else if(this == PLUS) {
				return "+";
			}else {
				return "-";
			}
		}
		
	}
	
	public static class BEDLine{
		
		private String chrom;
		private int start;
		private int end;
		private String name;
		private String score;
		private Strand strand;
		
		public BEDLine(String line) {
			
			String[] parts = line.split("\t");
			
			chrom = parts[0];
			start = Integer.parseInt(parts[1]);
			end = Integer.parseInt(parts[2]);
			name = parts[3];
			score = parts[4];
			strand = Strand.parse(parts[5]);
			
		}

		public String getChrom() {
			return chrom;
		}

		public int getStart() {
			return start;
		}

		public int getEnd() {
			return end;
		}

		public String getName() {
			return name;
		}

		public String getScore() {
			return score;
		}

		public Strand getStrand() {
			return strand;
		}
		
		public String toString() {
			return chrom+"\t"+start+"\t"+end+"\t"+name+"\t"+score+"\t"+strand+"\n";
		}
		
	}
	
	
	public static List<BEDLine> parseBED(String path) throws IOException{
		
		BufferedReader reader = new BufferedReader(new FileReader(path));
		
		String str = null;
		
		ArrayList<BEDLine> list = new ArrayList<>();
		
		while( (str = reader.readLine()) != null ) {
			BEDLine line = new BEDLine(str);
			list.add(line);
		}
		reader.close();
		
		return list;
		
	}
	
	public static void sortLines(List<BEDLine> list, Comparator<BEDLine> comp){
		
		Collections.sort(list, comp);
		
	}
	
	public static List<BEDLine> filter(List<BEDLine> list, String regEx){
		Pattern p = Pattern.compile(regEx);
		ArrayList<BEDLine> res = new ArrayList<>();
		for(BEDLine line : list) {
			String name = line.getName();
			Matcher m = p.matcher(name);
			if(m.find()) {
				res.add(line);
			}
		}
		return res;
	}
	
	
	private static String rc(String seq) {
		char[] chars = new char[seq.length()];
		for(int i=seq.length()-1;i>=0;i--) {
			char c;
			switch(seq.charAt(i)) {
			case 'a','A': c = 'T'; break;
			case 'c','C': c = 'G'; break;
			case 'g','G': c = 'C'; break;
			case 't','T': c = 'A'; break;
			case 'n','N': c = 'N'; break;
			default: throw new IllegalArgumentException("Only characters A,C,G,T,N allowed");
			}
			chars[seq.length()-1-i] = c;					
		}
		return new String(chars);
	}
	
	
	public static void extract(List<BEDLine> lines, Map<String,String> seqs, PrintWriter wr) {
		
		String currKey = "";
		String seq = null;
		
		for(BEDLine line: lines) {
			String key = line.getChrom();
			if(!currKey.equals(key)) {
				seq = seqs.get(key);
			}
			String sub = seq.substring(line.getStart(), line.getEnd());
			if(line.strand == Strand.MINUS) {
				sub = rc(sub);
			}
			wr.println(">"+key+"_"+line.getName());
			wr.println(sub);
		}
		
	}
	
	
	public static void main(String[] args) throws IOException {
		
		List<BEDLine> lines = null;
		try {
			lines = parseBED(args[0]);
		}catch(FileNotFoundException ex) {
			System.out.println("File + "+args[0]+" not found");
			System.exit(1);
		}catch(IOException ex) {
			System.out.println("File + "+args[0]+" could not be read");
			System.exit(1);
		}
		
		Comparator<BEDLine> comp = new Comparator<BEDLine>() {

			@Override
			public int compare(BEDLine o1, BEDLine o2) {
				int comp = o1.getChrom().compareTo(o2.getChrom());
				if(comp == 0) {
					comp = Integer.compare(o1.getStart(), o2.getStart());
				}
				if(comp == 0) {
					comp = Integer.compare(o1.getEnd(), o2.getEnd());
				}
				return comp;
			}
			
		};
		
		sortLines(lines,comp);
		
		lines = filter(lines,".*\\.1");
		
		
		Map<String,String> seqs = null;
		// try{
		// 	seqs = IOAufgabe.parseGenome(args[1]);
		// }catch(FileNotFoundException ex) {
		// 	System.out.println("File + "+args[1]+" not found");
		// 	System.exit(1);
		// }catch(IOException ex) {
		// 	System.out.println("File + "+args[1]+" could not be read");
		// 	System.exit(1);
		// }
		
		File out = new File(args[2]);
		out.getParentFile().mkdirs();
		
		PrintWriter wr = new PrintWriter(out);
		extract(lines, seqs, wr);
		wr.close();
		
	}
	
	
}
