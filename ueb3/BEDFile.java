package ueb3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Stream;

import ueb2.FastAFile;
import ueb3.BEDChrom.Strand;


// import ueb2.FastaFile;

public class BEDFile {

	public static ArrayList<BEDChrom> parseBED(String path) throws IOException, FileFormatException {
		ArrayList<BEDChrom> rows = new ArrayList<>();
		// File file = new File(path);
		// if (!file.exists()) throw new FileNotFoundException("File " + path + " not found.");

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] cols = line.split("[ \\t]");
			if (cols.length < 6) throw new FileFormatException();
			try {
				BEDChrom chrom = new BEDChrom(cols[0], cols[1], cols[2], cols[3], cols[4], cols[5]);
				rows.add(chrom);
			} catch (IllegalArgumentException e) {
				br.close();
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		br.close();
		return rows;
	}

	public static ArrayList<BEDChrom> sortBED(ArrayList<BEDChrom> rows) {
		Collections.sort(rows, new Comparator<BEDChrom>() {
			@Override
			public int compare(BEDChrom o1, BEDChrom o2) {
				return o1.compareTo(o2);
			}
		});
		return rows;
	}

	public static ArrayList<BEDChrom> filterByName(ArrayList<BEDChrom> rows, String regex) {
		ArrayList<BEDChrom> filtered = new ArrayList<>();
		for (BEDChrom chrom : rows) {
			if (chrom.name().matches(regex))
				filtered.add(chrom);
		}
		return filtered;
	}

	private static String wrap(String text, int maxlength) {
		String out = "";
		int start = 0, end;
		for (; start < text.length()-maxlength; start += maxlength) {
			end = start + maxlength;
			// if (end > text.length()) end = text.length();
			out += text.substring(start, end) + "\n";
		}
		out += text.substring(start) + "\n";
		return out;
	}

	private static String reverseSeq(String seq) {
		String out = "";
		for (int i = seq.length()-1; i >= 0; i--) {
			out += switch (seq.charAt(i)) {
				case 'A' -> 'T';
				case 'T' -> 'A';
				case 'C' -> 'G';
				case 'G' -> 'C';
				default -> seq.charAt(i);
			};
		}
		return out;
	}

	private static String getSubSequence(BEDChrom chrom, String sequence) {
		return (chrom.strand() == Strand.NEGATIVE ? reverseSeq(sequence) : sequence).substring(chrom.chromStart(), chrom.chromEnd());
	}

	public static File extractSequence(Map<String, String> map, ArrayList<BEDChrom> rows, String filePath) throws IOException {
		File file = new File(filePath);
		FileWriter fw = new FileWriter(file);
		ArrayList<BEDChrom> sortedRows = sortBED(rows);
		Stream<BEDChrom> stream = sortedRows.stream();
		stream
			.filter(chrom -> map.get(chrom.chrom()) != null)
			.forEach(chrom -> {
				try {
					fw.append(">" + chrom.chrom() + "_" + chrom.name() + "\n");
					fw.append(wrap(chrom.createSubSeq(map.get(chrom.chrom())), 80) + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		// String oldId = "";
		// for (BEDChrom bedChrom : sortedRows) {
		// 	String id = bedChrom.chrom() + "_" + bedChrom.name();
		// 	if (oldId.equals(id)) continue;
			
		// 	int start = bedChrom.chromStart();
		// 	int end = bedChrom.chromEnd();
		// 	String sequence = map.get(bedChrom.chrom());
		// 	String subseq = (bedChrom.strand() == Strand.NEGATIVE ? reverseSeq(sequence) : sequence).substring(start, end);
		// 	fw.append(">" + id + "\n");
		// 	fw.append(wrap(subseq, 80) + "\n");
		// 	oldId = id;
		// }
		fw.close();
		return file;
	}

	public static void main(String[] args) throws FileFormatException, IOException, URISyntaxException {
		String url = "https://ftp.ensemblgenomes.ebi.ac.uk/pub/plants/release-57/fasta/arabidopsis_thaliana/dna/Arabidopsis_thaliana.TAIR10.dna.toplevel.fa.gz";

		File bedFile = new File("ueb3/Ath_promoters.bed");
		File fastaFile = FastAFile.download(url, "Arabidopsis_thaliana.TAIR10.dna.toplevel.fa.gz"); // new File("ueb2/test.fa");
		File mergedFile = new File("ueb3/merged.fa");

		ArrayList<BEDChrom> arr = parseBED(bedFile.getAbsolutePath());
		ArrayList<BEDChrom> sortedArr = sortBED(arr);
		ArrayList<BEDChrom> filteredArr = filterByName(sortedArr, ".*\\.1");
		Map<String, String> map = FastAFile.parseFastA(fastaFile.getAbsolutePath());
		extractSequence(map, filteredArr, mergedFile.getAbsolutePath());
		
		System.out.println(arr.get(23).toString());
		System.out.println(sortedArr.get(23).toString());
		System.out.println(filteredArr.get(5).toString());

	}
}