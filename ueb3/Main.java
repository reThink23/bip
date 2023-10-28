package ueb3;

import java.beans.beancontext.BeanContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public class Main {

	public void readBED(String path) throws IOException, FileFormatException {
		ArrayList<BEDChrom> rows = new ArrayList<>();
		File file = new File(path);
		if (!file.exists()) throw new FileNotFoundException();

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
	}

	public ArrayList<BEDChrom> sortBED(ArrayList<BEDChrom> rows) {
		rows.sort(new Comparator<BEDChrom>() {
			@Override
			public int compare(BEDChrom o1, BEDChrom o2) {
				return o1.compare(o1, o2);
			}
		});
		return rows;
	}

	public ArrayList<BEDChrom> filterByName(ArrayList<BEDChrom> rows, String regex) {
		ArrayList<BEDChrom> filtered = new ArrayList<>();
		for (BEDChrom chrom : rows) {
			if (chrom.getName().matches(regex))
				filtered.add(chrom);
		}
		return filtered;
	}

	private String wrap(String text, int maxlength) {
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

	public void extractSequence(HashMap<String, String> map, ArrayList<BEDChrom> rows, String filePath) throws IOException {
		FileWriter fw = new FileWriter(new File(filePath));
		ArrayList<BEDChrom> sortedRows = sortBED(rows);
		String oldId = "";
		for (BEDChrom bedChrom : sortedRows) {
			String id = bedChrom.getChrom();
			if (id.equals(oldId)) continue;
			
			int start = bedChrom.getChromStart();
			int end = bedChrom.getChromEnd();
			String subseq = map.get(id).substring(start, end);
			fw.append(">" + id + "-" + bedChrom.getName() + "\n");
			fw.append(wrap(subseq, 80) + "\n");
			oldId = id;
		}
		fw.close();
	}
}