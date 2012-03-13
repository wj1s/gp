/**
 * 
 */
package gov.abrs.etms.common.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class FileNamesTool {
	public static String[] getNamesInClassPath(String packUrl) {
		return getNamesInClassPath(packUrl, null, null);
	}

	public static String[] getNamesInClassPath(String packUrl, String houzhui) {
		return getNamesInClassPath(packUrl, houzhui, null);
	}

	public static String[] getNamesInClassPath(String packUrl, String houzhui, FilenameFilter filter) {
		List<String> fileNames = new ArrayList<String>();
		try {
			Enumeration<URL> urls = ClassLoader.getSystemResources(packUrl);
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				File file = new File(url.getFile());
				recurse(file, packUrl, houzhui, fileNames, filter);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return fileNames.toArray(new String[0]);
	}

	private static void recurse(File dirfile, String basePackUrl, String houzhui, List<String> fileNames,
			FilenameFilter filter) {
		String[] contents = dirfile.list(filter);
		String packUrl = basePackUrl;
		for (int i = 0; i < contents.length; i++) {
			File childfile = new File(dirfile, contents[i]);
			if (childfile.isFile()) {
				if (houzhui == null) {
					String fileName = packUrl + contents[i];
					fileNames.add(fileName);
				} else if (childfile.getName().endsWith("." + houzhui)) {
					String fileName = packUrl + contents[i];
					fileNames.add(fileName);
				}
			} else {
				packUrl = basePackUrl + contents[i] + "/";
				recurse(childfile, packUrl, houzhui, fileNames, filter);
			}
		}
	}
}
