package ueb7.e;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import ueb7.a.Alphabet;
import ueb7.b.AbstractSequenceFactory;
import ueb7.b.Sequence;

/**
 * Class for parsing FastA files using a static method.
 * @author Jan Grau
 *
 */
public class FastAParser {

	/**
	 * Parse FastA from a file
	 * @param alphabet the {@link Alphabet} of the sequences in the FastA file
	 * @param path the path to the input file
	 * @param factory the Factory for creating {@link Sequence} objects from their {@link String} representation
	 * @return the sequences read
	 * @throws FileNotFoundException if the file at path could not be found
	 * @throws IOException if the file at path could not be read
	 */
	public static List<Sequence> parseFastA(Alphabet alphabet, String path, AbstractSequenceFactory factory) throws FileNotFoundException, IOException{
		BufferedReader r = null;
		if(path.endsWith(".gz")){
			r = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(path))));
		}else {
			r = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		}
		
		List<Sequence> seqs = new ArrayList<Sequence>();
		
		String str = null;
		String key = null;
		StringBuilder buf = null;
		
		while( (str = r.readLine()) != null ) {
			if(str.startsWith(">")) {
				if(key != null) {
					//hier benutzen wir die Factory
					seqs.add(factory.createSequence(alphabet,buf.toString()));
				}
				buf = new StringBuilder();
				key = str.substring(1).replaceFirst("\\s.*", "");
			}else {
				buf.append(str.toUpperCase());
			}
		}
		r.close();
		if(key != null) {
			//und hier auch
			seqs.add(factory.createSequence(alphabet,buf.toString()));
		}
		return seqs;
	}
	
}
