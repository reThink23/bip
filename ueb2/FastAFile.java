package ueb2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

public class FastAFile {

	// private static final String FILE_FORMAT_MSG = "The file format is not supported. The file should be a (gzipped) FASTA file but has the %s file format";  
	
	public static File download(String urlString, String filepath) throws URISyntaxException, IOException {
		File file = new File(filepath);
		if (file.exists()) return file;
		file.createNewFile();
		URL url = new URI(urlString).toURL(); // java.net.URL to easily stream file
		InputStream inpStream = url.openStream(); // java.io.InputStream to prepare writing file with FileOutputStram and transferTo method
		FileOutputStream outStream = new FileOutputStream(file); // java.io.FileOutputStream to write stream to file
		inpStream.transferTo(outStream);
		outStream.close();
		inpStream.close();
		return file;
	}

	private static BufferedReader readFile(String fileString) throws IOException {
		/* 	
			A2.1b:
			looks for gzip file extension and if present stream it through an additional GZIPInputStream, 
			returns eventually BufferedReader in both cases 
		*/
		InputStream fileStream = new FileInputStream(fileString);
		boolean isGZIP = fileString.endsWith(".gz") || fileString.endsWith(".gzip");
		Reader decoder = new InputStreamReader( !isGZIP ? fileStream : new GZIPInputStream(fileStream) );
		BufferedReader buffered = new BufferedReader(decoder);
		return buffered;
	}

	public static LinkedHashMap<String, String> mapFastA(String fastaFile) throws IOException {
		// if (!fastaFile.matches("\\.fa(\\.gz|\\.gzip)?$")) {// regex to match .fa, .fa.gz or .fa.gzip
		// 	throw new IOException(String.format(FILE_FORMAT_MSG, fastaFile.split("\\.", 2)[1]));
		// }

		/* A2.1b: get/put/containsKey in O(1), no need for sorting */
		LinkedHashMap<String,String> map = new LinkedHashMap<>();
		String line;
		int counter = 1;
		String sequence = "", ident = "";
		/* A2.1b: readFile method accepts (gzipped) fasta files  */
		BufferedReader reader = readFile(fastaFile);
		line = reader.readLine();
		while (line != null) {
			if (line.startsWith(">")) {
				if (!ident.isEmpty()) map.put(ident, sequence);
				ident = line.substring(1).split(" ")[0];
				// System.out.println("\tSequence "+ident);
				sequence = "";
			} else {
				sequence += line;
				System.out.print("Read lines: " + counter + "\r");
			}
			line = reader.readLine();
			counter++;
		}
		map.put(ident, sequence);
		reader.close();
		return map;
	}

	public static void saveToFile(LinkedHashMap<String,String> map, String filePath) throws IOException {
		String ident, sequence;
		FileWriter fw = new FileWriter(new File(filePath), true);
		Iterator<String> iter = map.keySet().iterator();
		fw.write("identifier\tsequence length\n");
		while (iter.hasNext()) {
			ident = iter.next();
			sequence = map.get(ident);
			fw.append(ident + "\t" + sequence.length() + "\n");
		}
		fw.close();
	}

	public static void main(String[] args) throws URISyntaxException, IOException {
		String url = "https://ftp.ensemblgenomes.ebi.ac.uk/pub/plants/release-57/fasta/arabidopsis_thaliana/dna/Arabidopsis_thaliana.TAIR10.dna.toplevel.fa.gz";
		System.out.println("Downloading...");
		File file = download(url, "Arabidopsis_thaliana.TAIR10.dna.toplevel.fa.gz");
		// File file = new File("./ueb2/test.fa");
		System.out.println("Mapping Sequences...");
		LinkedHashMap<String,String> map = mapFastA(file.getAbsolutePath());
		System.out.println("Saving...");
		saveToFile(map, "Arabidopsis_thaliana.tsv");
	}
}
