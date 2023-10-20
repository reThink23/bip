package ueb2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.io.IOException;

public class Main {
	
	public static void download(String urlString, String filepath) throws URISyntaxException, IOException {
		File file = new File(filepath);
		if (file.exists()) return;
		file.createNewFile();
		URL url = new URI(urlString).toURL(); // java.net.URL to easily stream file
		InputStream inpStream = url.openStream(); // java.io.InputStream to prepare writing file with FileOutputStram and transferTo method
		FileOutputStream outStream = new FileOutputStream(file, false); // java.io.FileOutputStream to write stream to file
		inpStream.transferTo(outStream);
		outStream.close();
	}

	private static BufferedReader readFile(String fileString) throws IOException {
		InputStream fileStream = new FileInputStream(fileString);
		boolean isGZIP = fileString.endsWith(".gz") || fileString.endsWith(".gzip");
		Reader decoder = new InputStreamReader( !isGZIP ? fileStream : new GZIPInputStream(fileStream) );
		BufferedReader buffered = new BufferedReader(decoder);
		return buffered;
	}

	public static HashMap<String, String> saveFastA(String fastaFile) throws IOException {
		if (!fastaFile.matches("\\.fasta(\\.gz|\\.gzip)?$")) // regex to match .fasta, .fasta.gzip or .fasta.gzip
			throw new IOException("The file format is not supported. The file should be a (gzipped) FASTA file but has the "+fastaFile.split(fastaFile, 1)[1] + " file format");
		HashMap<String,String> map = new HashMap<>();
		String line;
		String sequence = "", ident = "";
		BufferedReader reader = readFile(fastaFile);
		while ((line = reader.readLine()) != null) {
			if (line.startsWith(">")) {
				ident = line.substring(1).split(" ")[0];
			} else {
				sequence += line;
				map.put(ident, sequence);
			}
		}
		reader.close();
		return map;
	}
}
