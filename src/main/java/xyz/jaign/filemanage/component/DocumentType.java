package xyz.jaign.filemanage.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "jaign.config.document-type")
public class DocumentType {
    private List<String> pdf;
    private List<String> ppt;
    private List<String> word;
    private List<String> excel;

    public List<String> getPdf() {
        return pdf;
    }

    public void setPdf(List<String> pdf) {
        this.pdf = pdf;
    }

    public List<String> getPpt() {
        return ppt;
    }

    public void setPpt(List<String> ppt) {
        this.ppt = ppt;
    }

    public List<String> getWord() {
        return word;
    }

    public void setWord(List<String> word) {
        this.word = word;
    }

    public List<String> getExcel() {
        return excel;
    }

    public void setExcel(List<String> excel) {
        this.excel = excel;
    }

    public String typeOf(String ext){
        if (pdf.contains(ext.toLowerCase())){
            return "pdf";
        }
        if (word.contains(ext.toLowerCase())){
            return "word";
        }
        if (ppt.contains(ext.toLowerCase())){
            return "ppt";
        }
        if (excel.contains(ext.toLowerCase())){
            return "excel";
        }
        return "error";
    }
}
