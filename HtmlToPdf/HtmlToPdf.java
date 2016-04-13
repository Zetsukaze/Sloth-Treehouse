import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.IOException;

public class HtmlToPdf {

  public static void main(String [] args) {
    Document document = new Document();
    StringBuilder stringBuilder = new StringBuilder();
    String filePath = args[0];

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
      System.out.println("Decoding: " + filePath);
      String currentLine;
      while ((currentLine = bufferedReader.readLine()) != null) {
        stringBuilder.append(currentLine);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));
      InputStream inputStream = new ByteArrayInputStream(stringBuilder.toString().getBytes());

      document.open();
      XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, inputStream);
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
}
