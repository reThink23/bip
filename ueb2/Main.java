package ueb2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	
	public static void download(String urlString, String filepath) throws URISyntaxException, IOException {
		File file = new File(filepath);
		if (file.exists()) return;
		file.createNewFile();
		URL url = new URI(urlString).toURL();
		InputStream inpStream = url.openStream();
		FileOutputStream outStream = new FileOutputStream(file, false);
		inpStream.transferTo(outStream);
		outStream.close();
	}

	public static HashMap<String, String> saveFastA(String fastaFile) throws IOException {
		HashMap<String,String> map = new HashMap<>();
		File file = new File(fastaFile);
		String line;
		String sequence = "", ident = "";
		Scanner fsc = new Scanner(file);
		while (fsc.hasNextLine()) {
			line = fsc.nextLine();
			if (line.startsWith(">")) {
				ident = line.substring(1).split(" ")[0];
			} else {
				sequence += line;
				map.put(ident, sequence);
			}
		}
		fsc.close();
		return map;
	}
}
