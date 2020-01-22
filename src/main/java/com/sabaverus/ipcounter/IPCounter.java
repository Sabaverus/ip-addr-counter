package com.sabaverus.ipcounter;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import com.sabaverus.ipcounter.methods.BitSetMethod;

public class IPCounter {
	
	public static final String zipPath = "large_zip.zip";
	public static final String txtPath = "ip_list.txt";
	
	public static void main(String[] args) {

//		generateZipWithALotIp(zipPath, txtPath, 100_000_000);
		long before = System.currentTimeMillis();

		BitSetMethod bitset = new BitSetMethod();
		try (
			ZipFile zipFile = new ZipFile(zipPath);
			InputStream in = getInputStream(zipFile);
			Scanner s = new Scanner(in);
		) {
			s.useDelimiter("\\n");
			while (s.hasNext()) {
				bitset.pushToSet(s.next());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		long count = bitset.getResult();

		System.out.println("Time for counting " + (System.currentTimeMillis() - before));
		System.out.println("Count is " + count);
	}

	private static InputStream getInputStream(ZipFile file) {

		AtomicReference<InputStream> ar = new AtomicReference<InputStream>();

		file.stream().filter(e -> e.getName().equals(txtPath)).forEach(e -> {
			try {
				InputStream is = file.getInputStream(e);
				ar.set(is);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		return ar.get();
	}

	public static void generateZipWithALotIp(String pathToSave, String txtPath, int randomCount) {

		String randomIP;
		Random r = new Random();
		BufferedWriter writer;

		try {
			writer = new BufferedWriter(new FileWriter(txtPath));

			for (int i = 0; i < randomCount; i++) {
				randomIP = r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
				writer.write(randomIP + "\n");
			}

			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathToSave));
				FileInputStream fis = new FileInputStream("./" + txtPath);) {
			ZipEntry entry1 = new ZipEntry(txtPath);
			zout.putNextEntry(entry1);

			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			zout.write(buffer);
			zout.closeEntry();

			zout.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
