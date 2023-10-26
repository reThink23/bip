package ueb3;

public class Main {

	public enum Strand {
		POSITIVE,
		NEGATIVE,
		NONE
	}

	public void readBED(String path) {
		File file = File(path);
		if (!file.exists()) throw new FileNotFoundException();

		BufferedReader br = new BufferedReader(new FInputStreamReader(new FileInputStream(path)));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] cols = line.split(" ");
		}
	}
}