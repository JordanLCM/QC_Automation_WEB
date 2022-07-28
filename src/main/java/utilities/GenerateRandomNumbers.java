package utilities;

public class GenerateRandomNumbers {

	public int generateRandomNumbers() {
		int min = 100000;
		int max = 123456;

		int random_int = (int) Math.floor(Math.random() * (max - min));
		return random_int;
	}
}
