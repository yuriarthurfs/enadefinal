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

import br.com.enade.dao.TipoUsuarioDao;
import br.com.enade.dao.UsuarioDao;
import br.com.enade.model.Tbtipousuario;
import br.com.enade.model.Tbusuario;
import br.com.enade.tx.Transacional;
import br.com.enade.util.LoggingUtil;
import br.com.enade.util.Relatorio;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Tbusuario usuario;

    @Inject
    private UsuarioDao dao;

    @Inject
    private TipoUsuarioDao tipoUsuarioDao;

    @Inject
    private LoginBean login;

    private List<Tbusuario> usuarios;
    private Long usuarioId;
    private Long tipoUsuarioId;

    public void carregarUsuarioId() {
        usuario = dao.buscarPorId(usuarioId);
    }

    public void gravarTipoUsuario() {
        Tbtipousuario tipoUsuario = tipoUsuarioDao.buscarPorId(tipoUsuarioId);
        usuario.setTbTipoUsuarioidTipoUsuario(tipoUsuario);
    }

    @Transacional
    public void gravar() {
        if (usuario.getIdUsuario() == null) {
            dao.adiciona(usuario);
        } else {
            gravarTipoUsuario();
            dao.atualiza(usuario);
        }
        usuarios = dao.listaTodos();
        usuario = new Tbusuario();
        LoggingUtil.log("Gravando usuario: " + usuario.getNomeUsuario());
    }

    @Transacional
    public void remover(Tbusuario usuario) {
        dao.remove(usuario);
        usuarios = dao.listaTodos();
        LoggingUtil.log("Removendo usuario: " + usuario.getNomeUsuario());
    }

    // Omitted setters/getters for brevity

    public void preProcessPDF(Object document) throws IOException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logoPath = servletContext.getRealPath("/resources/demo/images/prime_logo.png");
        pdf.add(Image.getInstance(logoPath));
    }

    public void gerarRelatorio() throws IOException {
        Relatorio relatorio = new Relatorio();
        usuarios = dao.listaTodos();
        relatorio.getRelatorioAluno(usuarios);
    }

    public String cadastroAluno() {
        return "cadastroAluno?faces-redirect=true";
    }

    public String voltar() {
        return "login?faces-redirect=true";
    }

    public void novo() {
        usuario = new Tbusuario();
    }
}
