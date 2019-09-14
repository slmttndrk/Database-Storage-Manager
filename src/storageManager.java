import java.io.*;
import java.util.StringTokenizer;

public class storageManager {
	public static String outputname;
	public static void main(String args[]) throws Exception {
		String[] tokenss = new String[20];
		String[][] splitted = new String[20][20];
		String filename = args[0];
		outputname = args[1];
		//String filename = args[0] + ".txt";
		//outputname = args[1] + ".txt";
		
		int i = 0;

		try {
			RandomAccessFile file = new RandomAccessFile(filename, "r");
			String str;
			while ((str = file.readLine()) != null) {
				tokenss[i] = str;
				i++;
			}
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int t = 0; t < i; t++) {
			StringTokenizer st = new StringTokenizer(tokenss[t]);
			int z = 0;
			while (st.hasMoreElements()) {
				String token = st.nextElement().toString();
				splitted[t][z] = token;
				z++;
			}
		}
		
		int d = 0;
		while (splitted[d][0] != null) {
			String[] ref = new String[20];
			if (splitted[d][0].contains("create")) {
				if (splitted[d][1].contains("type")) {
					int c = 0;
					while (splitted[d][c] != null) {
						ref[c] = splitted[d][c];
						c++;
					}
					CreateType(ref);
				} else {
					int c = 0;
					while (splitted[d][c] != null) {
						ref[c] = splitted[d][c];
						c++;
					}
					CreateRecord(ref);
				}

			} else if (splitted[d][0].contains("delete")) {
				if (splitted[d][1].contains("type")) {
					int c = 0;
					while (splitted[d][c] != null) {
						ref[c] = splitted[d][c];
						c++;
					}
					DeleteType(ref);
				} else {
					int c = 0;
					while (splitted[d][c] != null) {
						ref[c] = splitted[d][c];
						c++;
					}
					DeleteRecord(ref);
				}

			} else if (splitted[d][0].contains("list")) {
				if (splitted[d][1].contains("type")) {
					ListType(outputname);
				} else {
					int c = 0;
					while (splitted[d][c] != null) {
						ref[c] = splitted[d][c];
						c++;
					}
					ListRecord(ref,outputname);
				}
					
			} else if (splitted[d][0].contains("search")) {
				int c = 0;
				while (splitted[d][c] != null) {
					ref[c] = splitted[d][c];
					c++;
				}
				SearchRecord(ref,outputname);
			} else if (splitted[d][0].contains("update")) {
				int c = 0;
				while (splitted[d][c] != null) {
					ref[c] = splitted[d][c];
					c++;
				}
				UpdateRecord(ref);
			}

			d++;
			ref = null;
		}
	}

	public static void CreateType(String[] referenced) {
		TypesAndRecords type = new TypesAndRecords();
		type.CreateType(referenced);
		type = null;
	}

	public static void DeleteType(String[] referenced) {
		TypesAndRecords type = new TypesAndRecords();
		type.DeleteType(referenced);
		type = null;
	}

	public static void ListType(String outputname) {
		TypesAndRecords type = new TypesAndRecords();
		type.ListType(outputname);
		type = null;
	}

	public static void CreateRecord(String[] referenced) {
		TypesAndRecords type = new TypesAndRecords();
		type.CreateRecord(referenced);
		type = null;
	}

	public static void DeleteRecord(String[] referenced) {
		TypesAndRecords type = new TypesAndRecords();
		type.DeleteRecord(referenced);
		type = null;
	}

	public static void ListRecord(String[] referenced,String outputname) {
		TypesAndRecords type = new TypesAndRecords();
		type.ListRecord(referenced,outputname);
		type = null;
	}

	public static void SearchRecord(String[] referenced,String outputname) {
		TypesAndRecords type = new TypesAndRecords();
		type.SearchRecord(referenced,outputname);
		type = null;
	}

	public static void UpdateRecord(String[] referenced) {
		TypesAndRecords type = new TypesAndRecords();
		type.UpdateRecord(referenced);
		type = null;
	}
}
