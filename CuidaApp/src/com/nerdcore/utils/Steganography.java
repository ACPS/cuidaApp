package com.nerdcore.utils;

import java.util.Vector;

import com.nerdcore.logs.Trace;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.Config;

/**
 * @author LUSTER
 * 
 */
public class Steganography {

	private static final int[] binary = { 16, 8, 0 };
	private static final byte[] andByte = { (byte) 0xC0, 0x30, 0x0C, 0x03 };
	private static final int[] toShift = { 6, 4, 2, 0 };
	private static final String END_MESSAGE_COSTANT = "#!@";
	private static final String START_MESSAGE_COSTANT = "@!#";

	public static Bitmap encondeMessage(Bitmap photo, String message) {

		int width = photo.getWidth();
		int height = photo.getHeight();

		int[] oneD = new int[width * height];
		photo.getPixels(oneD, 0, width, 0, 0, width, height);

		int density = photo.getDensity();
//		photo.recycle();

		byte[] byteImage = encodeMessage(oneD, width, height, message);

		oneD = null;
		photo = null;

		int[] oneDMod = byteArrayToIntArray(byteImage);
		byteImage = null;

		Bitmap destBitmap = Bitmap
				.createBitmap(width, height, Config.ARGB_8888);

		destBitmap.setDensity(density);

		int masterIndex = 0;

		for (int j = 0; j < height; j++)

			for (int i = 0; i < width; i++) {
				// The unique way to write correctly the sourceBitmap, android
				// bug!!!
				destBitmap.setPixel(i, j, Color.argb(0xFF,
						oneDMod[masterIndex] >> 16 & 0xFF,
						oneDMod[masterIndex] >> 8 & 0xFF,
						oneDMod[masterIndex++] & 0xFF));
			}

		System.gc();

		return destBitmap;

	}

	private static byte[] encodeMessage(int[] oneDPix, int imgCols,
			int imgRows, String str) {

		str += END_MESSAGE_COSTANT;
		str = START_MESSAGE_COSTANT + str;

		byte[] msg = str.getBytes();
		int channels = 3;
		int shiftIndex = 4;

		byte[] result = new byte[imgRows * imgCols * channels];

		int msgIndex = 0;
		int resultIndex = 0;
		boolean msgEnded = false;

		for (int i = 0; i < imgRows; i++) {
			for (int j = 0; j < imgCols; j++) {

				int element = i * imgCols + j;
				byte tmp = 0;

				for (int channelIndex = 0; channelIndex < channels; channelIndex++) {
					if (!msgEnded) {
						tmp = (byte) ((((oneDPix[element] >> binary[channelIndex]) & 0xFF) & 0xFC) | ((msg[msgIndex] >> toShift[(shiftIndex++)
								% toShift.length]) & 0x3));// 6
						if (shiftIndex % toShift.length == 0) {
							msgIndex++;
						}
						if (msgIndex == msg.length) {
							msgEnded = true;
						}
					} else {
						tmp = (byte) ((((oneDPix[element] >> binary[channelIndex]) & 0xFF)));
					}
					result[resultIndex++] = tmp;
				}

			}

		}
		return result;

	}

	public static String decodeMessage(Bitmap photo) {

		int[] pixels = new int[photo.getWidth() * photo.getHeight()];
		photo.getPixels(pixels, 0, photo.getWidth(), 0, 0, photo.getWidth(),
				photo.getHeight());

		byte[] b = null;

		try {

			b = convertArray(pixels);
			String message = decodeMessage(b, photo.getWidth(),
					photo.getHeight());
			return message;

		} catch (OutOfMemoryError er) {
			Trace.i("Steganography", "OutOfMemoryError");
		}

		return null;

	}

	private static String decodeMessage(byte[] oneDPix, int imgCols, int imgRows) {

		Vector<Byte> v = new Vector<Byte>();

		String builder = "";
		int shiftIndex = 4;
		byte tmp = 0x00;

		for (int i = 0; i < oneDPix.length; i++) {
			tmp = (byte) (tmp | ((oneDPix[i] << toShift[shiftIndex
					% toShift.length]) & andByte[shiftIndex++ % toShift.length]));
			if (shiftIndex % toShift.length == 0) {
				v.addElement(new Byte(tmp));
				byte[] nonso = { (v.elementAt(v.size() - 1)).byteValue() };
				String str = new String(nonso);

				if (builder.endsWith(END_MESSAGE_COSTANT)) {
					break;
				} else {
					builder = builder + str;
					if (builder.length() == START_MESSAGE_COSTANT.length()
							&& !START_MESSAGE_COSTANT.equals(builder)) {
						builder = null;
						break;
					}
				}

				tmp = 0x00;
			}

		}

		if (builder != null)
			builder = builder.substring(START_MESSAGE_COSTANT.length(),
					builder.length() - END_MESSAGE_COSTANT.length());
		return builder;
	}

	private static int[] byteArrayToIntArray(byte[] b) {

		int size = b.length / 3;

		// System.runFinalization();

		int[] result = new int[size];
		int off = 0;
		int index = 0;
		while (off < b.length) {
			result[index++] = byteArrayToInt(b, off);
			off = off + 3;
		}
		return result;
	}

	public static int byteArrayToInt(byte[] b) {
		return byteArrayToInt(b, 0);
	}

	private static int byteArrayToInt(byte[] b, int offset) {
		int value = 0x00000000;
		for (int i = 0; i < 3; i++) {
			int shift = (3 - 1 - i) * 8;
			value |= (b[i + offset] & 0x000000FF) << shift;
		}
		value = value & 0x00FFFFFF;
		return value;
	}

	private static byte[] convertArray(int[] array) {
		byte[] newarray = new byte[array.length * 3];
		for (int i = 0; i < array.length; i++) {
			newarray[i * 3] = (byte) ((array[i] >> 16) & 0xFF);
			newarray[i * 3 + 1] = (byte) ((array[i] >> 8) & 0xFF);
			newarray[i * 3 + 2] = (byte) ((array[i]) & 0xFF);
		}
		return newarray;
	}
}
