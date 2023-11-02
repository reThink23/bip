package ueb3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import ueb2.FastAFile;


// import ueb2.FastaFile;

public class BEDFile {

	public static ArrayList<BEDChrom> readBED(String path) throws IOException, FileFormatException {
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
			if (chrom.getName().matches(regex))
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

	public static File extractSequence(Map<String, String> map, ArrayList<BEDChrom> rows, String filePath) throws IOException {
		File file = new File(filePath);
		FileWriter fw = new FileWriter(file);
		ArrayList<BEDChrom> sortedRows = sortBED(rows);
		String oldId = "";
		for (BEDChrom bedChrom : sortedRows) {
			String id = bedChrom.getChrom() + "_" + bedChrom.getName();
			if (id.equals(oldId)) continue;
			
			int start = bedChrom.getChromStart();
			int end = bedChrom.getChromEnd();
			String elem = map.get(bedChrom.getChrom());
			String subseq = elem.substring(start, end);
			fw.append(">" + id + "-" + bedChrom.getName() + "\n");
			fw.append(wrap(subseq, 80) + "\n");
			oldId = id;
		}
		fw.close();
		return file;
	}

	public static void main(String[] args) throws FileFormatException, IOException {
		File bedFile = new File("ueb3/Ath_promoters.bed");
		File fastaFile = new File("ueb2/test.fa");
		File mergedFile = new File("ueb3/merged.fa");

		ArrayList<BEDChrom> arr = readBED(bedFile.getAbsolutePath());
		ArrayList<BEDChrom> sortedArr = sortBED(arr);
		ArrayList<BEDChrom> filteredArr = filterByName(sortedArr, ".*\\.1");
		Map<String, String> map = FastAFile.mapFastA(fastaFile.getAbsolutePath());
		extractSequence(map, filteredArr, mergedFile.getAbsolutePath());
		
		System.out.println(arr.get(23).toString());
		System.out.println(sortedArr.get(23).toString());
		System.out.println(filteredArr.get(5).toString());

	}
}