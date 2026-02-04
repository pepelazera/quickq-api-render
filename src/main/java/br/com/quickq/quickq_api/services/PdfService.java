package br.com.quickq.quickq_api.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PdfService {

    /**
     * Recebe o arquivo PDF enviado pelo usuário e extrai todo o texto dele.
     * @param arquivo O arquivo PDF (MultipartFile)
     * @return O texto contido no PDF
     */
    public String extrairTextoDoPdf(MultipartFile arquivo) {
        try {
            // 1. Carrega o documento PDF usando a biblioteca PDFBox
            PDDocument documento = PDDocument.load(arquivo.getInputStream());

            // 2. Cria o extrator de texto
            PDFTextStripper extrator = new PDFTextStripper();

            // 3. Extrai o texto
            String texto = extrator.getText(documento);

            // 4. Fecha o documento (muito importante para liberar memória!)
            documento.close();

            return texto;

        } catch (IOException e) {
            e.printStackTrace();
            return "Erro ao ler o PDF: " + e.getMessage();
        }
    }
}