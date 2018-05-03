package be.unamur.hermes.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.font.FontProvider;

public class PDFCreator {

    public void createPDF(String htmlInput, Path dest) throws IOException {
	WriterProperties writerProperties = new WriterProperties();
	// Add metadata
	writerProperties.addXmpMetadata();

	try (//
		OutputStream out = Files.newOutputStream(dest, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
		PdfWriter pdfWriter = new PdfWriter(out, writerProperties);
		PdfDocument pdfDoc = new PdfDocument(pdfWriter);
		InputStream is = new ByteArrayInputStream(htmlInput.getBytes());) {
	    ConverterProperties props = new ConverterProperties();
	    FontProvider fp = new FontProvider();
	    fp.addStandardPdfFonts();
	    props.setFontProvider(fp);
	    HtmlConverter.convertToPdf(is, pdfDoc, props);
	} catch (IOException e) {
	    e.printStackTrace();
	    return;
	}
    }

    public static InputStream createPDF(String htmlContents) throws IOException {
	WriterProperties writerProperties = new WriterProperties();
	// Add metadata
	writerProperties.addXmpMetadata();
	Path temp = Files.createTempFile("hermes_", "_doc");
	try (//
		InputStream is = new ByteArrayInputStream(htmlContents.getBytes(StandardCharsets.UTF_8));
		OutputStream out = Files.newOutputStream(temp, StandardOpenOption.WRITE, StandardOpenOption.CREATE);) {
	    PdfWriter pdfWriter = new PdfWriter(out, writerProperties);
	    PdfDocument pdfDoc = new PdfDocument(pdfWriter);
	    ConverterProperties props = new ConverterProperties();
	    FontProvider fp = new FontProvider();
	    fp.addStandardPdfFonts();
	    props.setFontProvider(fp);
	    HtmlConverter.convertToPdf(htmlContents, pdfDoc, props);
	}
	return Files.newInputStream(temp, StandardOpenOption.READ);
    }

    // debugging
    public static void main(String[] args) throws IOException {
	Path output = Paths.get("C:\\Users\\Thomas_Elskens\\Documents\\test", "test.pdf");
	Path inputPath = Paths.get("C:\\Users\\Thomas_Elskens\\Documents\\test", "input.txt");
	String input = Files.newBufferedReader(inputPath).lines().collect(Collectors.joining());
	PDFCreator creator = new PDFCreator();
	creator.createPDF(input, output);
    }
}
