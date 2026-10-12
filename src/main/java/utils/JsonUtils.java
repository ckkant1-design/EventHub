package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtils {

	public static String readJson(String fileName) throws IOException {

		return Files.readString(Paths.get("src/test/resources/testdata/" + fileName));
	}

}
