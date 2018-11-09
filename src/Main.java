
public class Main {

	public static void main(String[] args) {
		byte[] bufferA = Audio.Read("resources/sounds/track1.wav");
		byte[] bufferB = Audio.Read("resources/sounds/track2.wav");

		byte[] bufferC = Audio.MergeBuffers(bufferA, bufferB);

		Audio.Write(bufferC, "D:\\Download/mix.wav", 5);

		bufferA = Audio.Read("resources/sounds/hihi.wav");
		bufferB = Audio.Read("resources/sounds/haha.wav");

		bufferC = Audio.concatBuffers(bufferA, bufferB);

		Audio.Write(bufferC, "D:\\Download/mix1.wav", 10);
		
		bufferA = Audio.Read("resources/sounds/kicks.wav");
		bufferB = Audio.Read("resources/sounds/snares.wav");

		bufferC = Audio.MergeBuffers(bufferA, bufferB);

		Audio.Write(bufferC, "D:\\Download/mix2.wav", 9);

	}

}
