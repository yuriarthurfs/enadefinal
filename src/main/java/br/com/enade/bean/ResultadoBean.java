package br.com.enade.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.enade.dao.ResultadoDao;
import br.com.enade.model.Tbresultado;
import br.com.enade.tx.Transacional;

@Named
@ViewScoped
public class ResultadoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Tbresultado resultado;
    private Long resultId;

    @Inject
    private ResultadoDao resultDao;
    private List<Tbresultado> listResultado;

    @Transacional
    public String gravar() {
        if (resultado.getIdResultado() == null) {
            resultDao.adiciona(resultado);
        } else {
            resultDao.atualiza(resultado);
        }
        listResultado = resultDao.listarTodos();
        resultado = new Tbresultado();
        return "resultado?faces-redirect=true";
    }

    @Transacional
    public void remover(Tbresultado result) {
        resultDao.remove(result);
        listResultado = resultDao.listarTodos();
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);
            cell.setCellStyle(cellStyle);
        }
    }

    public void preProcessPDF(Object document) throws IOException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logoPath = servletContext.getRealPath("/resources/demo/images/prime_logo.png");
        pdf.add(Image.getInstance(logoPath));
    }
}
