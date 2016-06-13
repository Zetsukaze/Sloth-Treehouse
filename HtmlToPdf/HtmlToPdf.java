import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.List;

public class HtmlToPdf {

  public static void main(String [] args) {
    Document document = null;
    StringBuilder stringBuilder = null;

    for (int i = 0; i < args.length; i++) {
      String filePath = args[0];
      // stringBuilder = new StringBuilder();
      // try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
      //   System.out.println("Decoding: " + filePath);
      //   String currentLine;
      //   while ((currentLine = bufferedReader.readLine()) != null) {
      //     stringBuilder.append(currentLine);
      //   }
      // } catch (IOException e) {
      //   e.printStackTrace();
      // }

      try {
        document = new Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("output" + i + ".pdf"));
        // String inputString = stringBuilder.toString();
        // System.out.println("Length of input: " + inputString.length());
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, new FileInputStream(filePath));
        // document.add(new Chunk(""));
        // HTMLWorker htmlWorker = new HTMLWorker(document);
        // htmlWorker.parse(new StringReader(inputString));
      } catch (DocumentException e) {
        e.printStackTrace();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        document.close();
      }
    }
    List<InputStream> outputFileList = new ArrayList<InputStream>();
    for (int i = 0; i < args.length; i++) {
      String filePath = "output" + i + ".pdf";
      try {
        outputFileList.add(new FileInputStream(new File(filePath)));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    Document mergedDocument = new Document();
    try {
      FileOutputStream fileOutputStream = new FileOutputStream("merged.pdf");
      PdfWriter pdfWriter = PdfWriter.getInstance(mergedDocument, fileOutputStream);
      mergedDocument.open();
      PdfContentByte pdfContentByte = pdfWriter.getDirectContent();

      for (InputStream inputStream: outputFileList) {
        PdfReader pdfReader = new PdfReader(inputStream);
        int inputStreamNum = 1;
        System.out.println("InputStream: " + inputStreamNum + ", Number of Pages: " + pdfReader.getNumberOfPages());
        inputStreamNum++;
        for (int i = 0; i < pdfReader.getNumberOfPages(); i++) {
          mergedDocument.newPage();
          PdfImportedPage page = pdfWriter.getImportedPage(pdfReader, i + 1);
          pdfContentByte.addTemplate(page, 0, 0);
        }
      }
      fileOutputStream.flush();
      mergedDocument.close();
      fileOutputStream.close();
    } catch (DocumentException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
