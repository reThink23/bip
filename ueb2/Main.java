package ueb2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;
import java.io.IOException;

public class Main {

	private static final String FILE_FORMAT_MSG = "The file format is not supported. The file should be a (gzipped) FASTA file but has the %s file format";  
	
	public static void download(String urlString, String filepath) throws URISyntaxException, IOException {
		File file = new File(filepath);
		if (file.exists()) return;
		file.createNewFile();
		URL url = new URI(urlString).toURL(); // java.net.URL to easily stream file
		InputStream inpStream = url.openStream(); // java.io.InputStream to prepare writing file with FileOutputStram and transferTo method
		FileOutputStream outStream = new FileOutputStream(file); // java.io.FileOutputStream to write stream to file
		inpStream.transferTo(outStream);
		outStream.close();
		inpStream.close();
	}

	private static BufferedReader readFile(String fileString) throws IOException {
		/* 	
			looks for gzip file extension and if present stream it through an additional GZIPInputStream, 
			returns eventually BufferedReader in both cases 
		*/
		InputStream fileStream = new FileInputStream(fileString);
		boolean isGZIP = fileString.endsWith(".gz") || fileString.endsWith(".gzip");
		Reader decoder = new InputStreamReader( !isGZIP ? fileStream : new GZIPInputStream(fileStream) );
		BufferedReader buffered = new BufferedReader(decoder);
		return buffered;
	}

	public static HashMap<String, String> saveFastA(String fastaFile) throws IOException {
		if (!fastaFile.matches("\\.fasta(\\.gz|\\.gzip)?$")) {// regex to match .fasta, .fasta.gzip or .fasta.gzip
			throw new IOException(String.format(FILE_FORMAT_MSG, fastaFile.split(fastaFile, 1)[1]));
		}
		/* Why Hashmap: get/put/containsKey in O(1), no need for sorting */
		HashMap<String,String> map = new HashMap<>();
		String line;
		String sequence = "", ident = "";
		/* readFile method accepts (gzipped) fasta files  */
		BufferedReader reader = readFile(fastaFile);
		line = reader.readLine();
		while (line != null) {
			if (line.startsWith(">")) {
				map.put(ident, sequence);
				ident = line.substring(1).split(" ")[0];
			} else {
				sequence += line;
			}
			line = reader.readLine();
		}
		reader.close();
		return map;
	}

	public static void saveToFile(HashMap<String,String> map, String filePath) throws IOException {
		FileWriter fout = new FileWriter(new File(filePath), true);
		fout.append(filePath);
	}
}
