package br.com.enade.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.enade.dao.ProvaDao;
import br.com.enade.dao.QuestaoDao;
import br.com.enade.dao.ResultadoDao;
import br.com.enade.model.Tbprova;
import br.com.enade.model.Tbquestao;
import br.com.enade.tx.Transacional;

@Named
@ViewScoped
public class ProvaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Tbprova prova;

    @Inject
    private ProvaDao provadao;

    @Inject
    private QuestaoDao quedao;

    private Long provaId;
    private Long questaoId;
    private List<Tbprova> provas;

    @Inject
    private ResultadoDao resultadodao;
    private Long resultado;

    public List<Tbquestao> getQuestoes() {
        return quedao.listarTodos();
    }

    public List<Tbprova> getProvas() {
        if (provas == null) {
            refreshProvas();
        }
        return provas;
    }

    private void refreshProvas() {
        provas = provadao.listaTodos();
    }

    public void gravarQuestao() {
        Tbquestao questao = quedao.buscarPorId(questaoId);
        if (questao != null) {
            prova.gravarQuestao(questao);
        } else {
            // Log error or notify user
        }
    }

    @Transacional
    public String grava() {
        if (prova.getIdProva() == null) {
            provadao.adicionar(prova);
        } else {
            provadao.atualiza(prova);
        }
        refreshProvas();
        prova = new Tbprova();
        return "provas?faces-redirect=true";
    }

    @Transacional
    public void remover(Tbprova prova) {
        provadao.remove(prova);
        refreshProvas();
    }
}
