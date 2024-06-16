package br.com.enade.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.enade.util.Relatorio;

@Named
@ViewScoped
public class RelatorioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String relatorio;
    private Map<String, String> tipos;

    @PostConstruct
    public void init() {
        tipos = new HashMap<>();
        initTiposRelatorio();
    }

    private void initTiposRelatorio() {
        tipos.put("Relatorio de Alunos", "ListaAlunos");
        tipos.put("Relatorio de Alunos X Notas", "AlunoXNota");
        tipos.put("Relatorio de Alunos sem Prova", "AlunosPendentesSemProva");
        tipos.put("Relatorio de Alunos com Prova Pendente", "AlunosPendentesComProva");
    }

    public String getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    public Map<String, String> getTipos() {
        return tipos;
    }

    public void setTipos(Map<String, String> tipos) {
        this.tipos = tipos;
    }

    public void gerarRelatorio() {
        try {
            Relatorio report = new Relatorio();
            report.getRelatorio(relatorio);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao gerar relatório", e.getMessage()));
        }
    }
}
