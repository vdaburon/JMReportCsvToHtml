package io.github.vdaburon.jmeter.utils;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * Reads a csv file from a jmeter report (summary, aggregated or synthesis) and generates an html div block as an html table with an integrated style sheet
 * the first column is left-aligned, the other columns right-aligned
 * odd lines are gray, even lines are white * @author Vincent Daburon
 */

public class ReportCsv2Html {
	public static void main(String[] args) {
		boolean bSort = false;
		if (args.length != 2 && args.length != 3) {
			usage();
			System.exit(1);
		}
		String fileIn = args[0];
		String fileOut = args[1];
		if (args.length == 3) {
			if ("sort".equalsIgnoreCase(args[2])) {
				bSort = true;
			}
		}
		try {
			creatHtmlFromCsv(fileIn, fileOut, bSort);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static void usage() {
		System.err.println("java -jar csv-report-to-html-<version>.jar fileCsvIn fileHtmlOut [sort]");
	}
	
	private static void creatHtmlFromCsv(String fileIn, String fileout, boolean bSort) throws IOException {
		Reader in = new FileReader(fileIn);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileout)));

		// read all
		Iterator<CSVRecord> iterator = records.iterator();

		List<CSVRecord> listRecordsBetweenFirstAndLast = new ArrayList();
		CSVRecord firstRecord = null;
		CSVRecord lastRecord = null;

		int i = 0;
		while(iterator.hasNext()) {
			i++;
			CSVRecord record = iterator.next();
			if (i == 1) {
				// save the Header line
				firstRecord = record;
			}
			else {
				listRecordsBetweenFirstAndLast.add(record);
				lastRecord = record;
			}
		}
		if (listRecordsBetweenFirstAndLast.size() >= 1) {
			// remove the footer line (Total)
			listRecordsBetweenFirstAndLast.remove(listRecordsBetweenFirstAndLast.size() -1);
		}
		if (bSort) {
			// and sort records (expect first line and last line)
			Collections.sort(listRecordsBetweenFirstAndLast, new CompareRecord());
		}

		in.close();

		out.println("<div>");
		out.println("<style>");
		out.println("table.table_jp {border-collapse: collapse;}");
		out.println("table.table_jp, table.table_jp th, table.table_jp tr, table.table_jp td {");
		out.println("border: 1px solid black;");
		out.println("text-align: right;");
		out.println("font-family: sans-serif;");
		out.println("font-size:small; }");
		out.println("table.table_jp th:{background-color: #f8f8f8;}");
		out.println("table.table_jp tr:nth-child(even) {background-color: #f2f2f2;}");
		out.println("table.table_jp td:nth-child(-n+1) { text-align: left; }");
		out.println("table.table_jp th:nth-child(-n+1) { text-align: left; }");
		out.println("</style>");
		out.println("<table class=\"table_jp\">");

		// the header
		int nbFirstColums = firstRecord.size();
		out.println("<tr>");
		for (int j = 0; j < nbFirstColums; j++) {
			out.println("<th>" + textToHtml(firstRecord.get(j)) + "</th>");
		}
		out.println("</tr>");

		for (CSVRecord record : listRecordsBetweenFirstAndLast) {
			int nbColums = record.size();
			out.println("<tr>");
			for (int j = 0; j < nbColums; j++) {
				out.println("<td>" + textToHtml(record.get(j)) + "</td>");
			}
			out.println("</tr>");
		}

		// the footer
		int nbLastColums = lastRecord.size();
		out.println("<tr>");
		for (int j = 0; j < nbLastColums; j++) {
			out.println("<td>" + textToHtml(lastRecord.get(j)) + "</td>");
		}
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>");
		out.close();
	}

	/** Converts a character string into strings readable by a web browser
	 (replacement of certain char like &amp; par &amp;amp;)
	 @param in the string to process
	 @return the processed string
	 */
	public static String textToHtml(String in) {
		char[] bin=in.toCharArray();
		StringBuffer bout = new StringBuffer(bin.length);
		char car;
		int iout=0;
		for(int i=0;i<bin.length;i++) {
			switch (car=bin[i]) {
				case '\"': bout.append("&quot;"); break;
				case '&': bout.append("&amp;"); break;
				case '\'': bout.append("&#39;"); break;
				case '<': bout.append("&lt;"); break;
				case '>': bout.append("&gt;"); break;
				case '^': bout.append("&circ;"); break;
				case '~': bout.append("&tilde;"); break;
				case '???': bout.append("&euro;"); break;
				case '??': bout.append("&Aegrave;"); break;
				case '??': bout.append("&Aacute;"); break;
				case '??': bout.append("&Acirc;"); break;

				case '??': bout.append("&Auml;"); break;
				case '??': bout.append("&Ccedil;"); break;
				case '??': bout.append("&Eegrave;"); break;
				case '??': bout.append("&Ecirc;"); break;
				case '??': bout.append("&Euml;"); break;

				case '??': bout.append("&Iacute;"); break;
				case '??': bout.append("&Oacute;"); break;
				case '??': bout.append("&Ocirc;"); break;

				case '??': bout.append("&Ouml;"); break;
				case '??': bout.append("&Uegrave;"); break;
				case '??': bout.append("&Uacute;"); break;
				case '??': bout.append("&Ucirc;"); break;
				case '??': bout.append("&Uuml;"); break;
				case '??': bout.append("&Yacute;"); break;
				case '??': bout.append("&szlig;"); break;
				case '??': bout.append("&agrave;"); break;
				case '??': bout.append("&acirc;"); break;

				case '??': bout.append("&auml;"); break;

				case '??': bout.append("&ccedil;"); break;
				case '??': bout.append("&egrave;"); break;
				case '??': bout.append("&eacute;"); break;
				case '??': bout.append("&ecirc;"); break;
				case '??': bout.append("&euml;"); break;
				case '??': bout.append("&icirc;"); break;
				case '??': bout.append("&iuml;"); break;
				case '??': bout.append("&oacute;"); break;
				case '??': bout.append("&ocirc;"); break;
				case '??': bout.append("&ouml;"); break;
				case '??': bout.append("&ugrave;"); break;
				case '??': bout.append("&uacute;"); break;
				case '??': bout.append("&ucirc;"); break;
				case '??': bout.append("&uuml;"); break;

				default : bout.append(car);
			}
		}
		return bout.toString();
	}
}
