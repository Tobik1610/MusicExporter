import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {

	public static byte[] Read(String file) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		AudioInputStream ais;
		try {
			ais = AudioSystem.getAudioInputStream(new File(file));
			int read;
			byte[] buffer = new byte[1024];
			while ((read = ais.read(buffer)) != -1) {
				baos.write(buffer, 0, read);
			}
			baos.flush();
			return baos.toByteArray();
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] MergeBuffers(byte[] bufferA, byte[] bufferB) {
		int size = 0;
		if (bufferA.length > bufferB.length)
			size = bufferA.length;
		else
			size = bufferB.length;
		byte[] bufferC = new byte[size];
		for (int i = 0; i < bufferC.length; i += 2) {
			if (i < bufferA.length && i < bufferB.length) {
				// Qualitätsaufbereitung
				short buf1A = bufferA[i + 1];
				short buf2A = bufferA[i];
				buf1A = (short) ((buf1A & 0xff) << 8);
				buf2A = (short) (buf2A & 0xff);

				short buf1B = bufferB[i + 1];
				short buf2B = bufferB[i];
				buf1B = (short) ((buf1B & 0xff) << 8);
				buf2B = (short) (buf2B & 0xff);

				short buf1C = (short) ((buf1A + buf1B) / 2);
				short buf2C = (short) ((buf2A + buf2B) / 2);

				short res = (short) (buf1C | buf2C);

				bufferC[i] = (byte) res;
				bufferC[i + 1] = (byte) (res >> 8);
			} else if (i < bufferA.length) {
				bufferC[i] = bufferA[i];
				bufferC[i + 1] = bufferA[i + 1];
			} else if (i < bufferB.length) {
				bufferC[i] = bufferB[i];
				bufferC[i + 1] = bufferB[i + 1];
			}
		}
		return bufferC;
	}

	public static byte[] concatBuffers(byte[] bufferA, byte[] bufferB) {
		byte[] buffer = new byte[bufferA.length + bufferB.length];
		int j = 0;
		for (int i = 0; i < buffer.length; i++) {
			if (i < bufferA.length)
				buffer[i] = bufferA[i];
			else
				buffer[i] = bufferB[j++];
		}
		return buffer;
	}

	public static void Write(byte[] buffer, String file, int maxLength) {
		AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
		AudioInputStream ais = new AudioInputStream(bais, format, 44100 * maxLength);
		try {
			AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
