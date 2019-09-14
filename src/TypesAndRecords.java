import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypesAndRecords {

	String typeName;
	int NumOfFields;
	int NumOfRecordsEachPage;
	int RecordSize;
	int NumOfRecords;
	int NumOfPages;
	String PrimaryKey;
	long FirstRecordLoc;
	long LastRecordLoc;
	String[] fieldNames = new String[10];
	int[] fieldLengths;
	boolean foundRecord;
	long location;
	String[] fields;
	int counter = 0;
	InputStreamReader input = new InputStreamReader(System.in);
	BufferedReader in = new BufferedReader(input);

	public void CreateType(String[] features) {

		typeName = features[2];
		NumOfFields = Integer.parseInt(features[3]);
		for (int i = 0; i < NumOfFields; i++) {
			fieldNames[i] = features[i + 4];
		}

		RandomAccessFile file;
		try {
			file = new RandomAccessFile("systemCatalogue.data", "rw");
			long filesize = file.length();
			file.seek(filesize);
			file.writeBytes(typeName);
			for (int i = 0; i < 8 - typeName.length(); i++) {
				file.writeByte(0);
			}
			file.writeBytes(features[3]);
			for (int i = 0; i < 8 - features[3].length(); i++) {
				file.writeByte(0);
			}
			for (int i = 0; i < NumOfFields; i++) {
				file.writeBytes(fieldNames[i]);
				for (int j = 0; j < 8 - fieldNames[i].length(); j++) {
					file.writeByte(0);
				}
			}
			for (int i = 0; i < 10 - NumOfFields; i++) {
				for (int j = 0; j < 8; j++) {
					file.writeByte(0);
				}
			}

			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void CreateRecord(String[] features) {
		try {
			typeName = features[2];
			for (int i = 0; i < features.length; i++) {
				if (features[i + 3] == null) {
					break;
				}
				fieldNames[i] = features[i + 3];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		PrimaryKey = features[3];

		RandomAccessFile file;
		try {
			file = new RandomAccessFile(typeName + ".data", "rw");
			long filesize = file.length();
			if (filesize == 0) {
				file.seek(0);
				int sayac = 0;
				for (int i = 0; i < fieldNames.length; i++) {
					if (fieldNames[i] == null) {
						break;
					}
					file.writeBytes(fieldNames[i]);
					for (int j = 0; j < 8 - fieldNames[i].length(); j++) {
						file.writeByte(0);
					}
					sayac++;
				}
				for (int i = 0; i < 10 - sayac; i++) {
					for (int j = 0; j < 8; j++) {
						file.writeByte(0);
					}
				}
			} else {
				long filesize2 = file.length();
				long cursor = (filesize2 / 80);
				int s = (int) (cursor);
				boolean isFounded = false;
				long k = 0;
				for (int i = 0; i < s; i++) {
					file.seek(i * 80);
					String typnm = "";
					for (int g = 0; g < 8; g++) {
						typnm += (char) (file.readByte());
					}
					if (PrimaryKey == typnm) {
						isFounded = true;
						k = (i * 80);
						break;
					}
				}

				if (isFounded) {
					file.seek(k);
					int sayac = 0;
					for (int i = 0; i < fieldNames.length; i++) {
						if (fieldNames[i] == null) {
							break;
						}
						file.writeBytes(fieldNames[i]);
						for (int j = 0; j < 8 - fieldNames[i].length(); j++) {
							file.writeByte(0);
						}
						sayac++;
					}
					for (int i = 0; i < 10 - sayac; i++) {
						for (int j = 0; j < 8; j++) {
							file.writeByte(0);
						}
					}
				} else {
					file.seek(filesize2);
					int sayac = 0;
					for (int i = 0; i < fieldNames.length; i++) {
						if (fieldNames[i] == null) {
							break;
						}
						file.writeBytes(fieldNames[i]);
						for (int j = 0; j < 8 - fieldNames[i].length(); j++) {
							file.writeByte(0);
						}
						sayac++;
					}
					for (int i = 0; i < 10 - sayac; i++) {
						for (int j = 0; j < 8; j++) {
							file.writeByte(0);
						}
					}
				}

			}

			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void DeleteType(String[] features) {
		typeName = features[2];
		PrimaryKey = typeName;

		RandomAccessFile file;
		try {
			file = new RandomAccessFile("systemCatalogue.data", "rw");
			long filesize = file.length();
			long cursor = (filesize / 96);
			int s = (int) (cursor);
			boolean isFounded = false;
			long k = 0;
			for (int i = 0; i < s; i++) {
				file.seek(i * 96);
				String typnm = "";
				for (int g = 0; g < PrimaryKey.length(); g++) {
					typnm += (char) (file.readByte());
				}
				if (PrimaryKey.contains(typnm)) {
					isFounded = true;
					k = (i * 96);
					break;
				}
			}

			if (isFounded) {
				long aha = k + 96;

				if (aha != filesize) {
					file.seek(aha);
					String typnm = "";
					while (aha < filesize) {
						typnm += (char) (file.readByte());
						aha++;
					}
					file.seek(k);
					file.writeBytes(typnm);
					file.setLength(filesize - 96);
				} else if (aha == filesize) {
					file.setLength(k);
				}

			}

			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		File file2;
		try {
			file2 = new File(typeName + ".data");
			file2.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void DeleteRecord(String[] features) {

		typeName = features[2];
		PrimaryKey = features[3];

		RandomAccessFile file;
		try {
			file = new RandomAccessFile(typeName + ".data", "rw");
			long filesize = file.length();
			long cursor = (filesize / 80);
			int s = (int) (cursor);
			boolean isFounded = false;
			long k = 0;
			for (int i = 0; i < s; i++) {
				file.seek(i * 80);
				String typnm = "";
				for (int g = 0; g < PrimaryKey.length(); g++) {
					typnm += (char) (file.readByte());
				}
				if (PrimaryKey.contains(typnm)) {
					isFounded = true;
					k = (i * 80);
					break;
				}
			}

			if (isFounded) {
				long aha = k + 80;

				if (aha != filesize) {
					file.seek(aha);
					String typnm = "";
					while (aha < filesize) {
						typnm += (char) (file.readByte());
						aha++;
					}
					file.seek(k);
					file.writeBytes(typnm);
					file.setLength(filesize - 80);
				} else if (aha == filesize) {
					file.setLength(k);
				}

			}

			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void ListType(String outputname) {

		RandomAccessFile file;
		try {
			file = new RandomAccessFile("systemCatalogue.data", "rw");
			long filesize = file.length();
			long cursor = (filesize / 96);
			int s = (int) (cursor);
			String[] types = new String[20];
			for (int i = 0; i < s; i++) {
				file.seek(i * 96);
				String typnm = "";
				for (int g = 0; g < 8; g++) {
					typnm += (char) (file.readByte());
				}
				types[i] = typnm;
			}

			file.close();
			//
			List<String> values = new ArrayList<String>();
			for (String data : types) {
				if (data != null) {
					values.add(data);
				}
			}
			String[] target = values.toArray(new String[values.size()]);
			Arrays.sort(target);
			//
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputname), true));
				for (String data : target) {
					bw.write(data);
					bw.newLine();
				}

				bw.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

			types = null;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void ListRecord(String[] features,String outputname) {
		typeName = features[2];

		RandomAccessFile file;
		try {
			file = new RandomAccessFile(typeName + ".data", "rw");
			long filesize = file.length();
			long cursor = (filesize / 80);
			int s = (int) (cursor);
			String[] recs = new String[20];
			for (int i = 0; i < s; i++) {
				file.seek(i * 80);
				String typnm = "";
				for (int g = 0; g < 80; g++) {
					typnm += (char) (file.readByte());
				}
				recs[i] = typnm;
			}

			file.close();
			//
			List<String> values = new ArrayList<String>();
			for (String data : recs) {
				if (data != null) {
					values.add(data);
				}
			}
			String[] target = values.toArray(new String[values.size()]);
			Arrays.sort(target);
			//
			
			
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputname), true));
				for (String data : target) {
					bw.write(data);
					bw.newLine();
				}
				
				bw.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

			recs = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void SearchRecord(String[] features,String outputname) {
		typeName = features[2];
		PrimaryKey = features[3];

		RandomAccessFile file;
		try {
			file = new RandomAccessFile(typeName + ".data", "rw");
			long filesize = file.length();
			long cursor = (filesize / 80);
			int s = (int) (cursor);
			long k = 0;
			String srecord = "";
			for (int i = 0; i < s; i++) {
				file.seek(i * 80);
				String typnm = "";
				for (int g = 0; g < PrimaryKey.length(); g++) {
					typnm += (char) (file.readByte());
				}
				if (PrimaryKey.contains(typnm)) {
					k = (i * 80);
					file.seek(k);
					for (int z = 0; z < 80; z++) {
						srecord += (char) (file.readByte());
					}
					break;
				}
			}

			file.close();

			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputname), true));
				bw.write(srecord);
				bw.newLine();
				bw.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

			srecord = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void UpdateRecord(String[] features) {
		typeName = features[2];
		PrimaryKey = features[3];
		for (int i = 0; i < features.length; i++) {
			if (features[i + 3] == null) {
				break;
			}
			fieldNames[i] = features[i + 3];
		}

		RandomAccessFile file;
		try {
			file = new RandomAccessFile(typeName + ".data", "rw");
			long filesize = file.length();
			long cursor = (filesize / 80);
			int s = (int) (cursor);
			long k = 0;
			for (int i = 0; i < s; i++) {
				file.seek(i * 80);
				String typnm = "";
				for (int g = 0; g < PrimaryKey.length(); g++) {
					typnm += (char) (file.readByte());
				}
				if (PrimaryKey.contains(typnm)) {
					k = (i * 80);
					file.seek(k);
					int sayac = 0;
					for (int f = 0; f < fieldNames.length; f++) {
						if (fieldNames[f] == null) {
							break;
						}
						file.writeBytes(fieldNames[f]);
						for (int j = 0; j < 8 - fieldNames[f].length(); j++) {
							file.writeByte(0);
						}
						sayac++;
					}
					for (int f = 0; f < 10 - sayac; f++) {
						for (int j = 0; j < 8; j++) {
							file.writeByte(0);
						}
					}

					break;
				}
			}

			file.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
