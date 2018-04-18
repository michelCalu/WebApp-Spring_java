package be.unamur.hermes.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    public void createPDF(String htmlInput) throws IOException {
	Path path = Paths.get("C:\\Users\\Thomas_Elskens\\Documents\\test", "test.pdf");
	WriterProperties writerProperties = new WriterProperties();
	// Add metadata
	writerProperties.addXmpMetadata();

	try (//
		OutputStream out = Files.newOutputStream(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
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

    public static void main(String[] args) throws IOException {
	Path inputPath = Paths.get("C:\\Users\\Thomas_Elskens\\Documents\\test", "input.txt");
	String input = Files.newBufferedReader(inputPath).lines().collect(Collectors.joining());
	PDFCreator creator = new PDFCreator();
	creator.createPDF(input);
    }

}
