package br.com.enade.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.enade.dao.ProvaDao;
import br.com.enade.dao.QuestaoDao;
import br.com.enade.dao.TipoQuestaoDao;
import br.com.enade.model.Tbprova;
import br.com.enade.model.Tbquestao;
import br.com.enade.model.Tbtipoquestao;
import br.com.enade.tx.Transacional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class QuestaoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(QuestaoBean.class);

    @Inject
    private Tbquestao questao;

    @Inject
    private ProvaDao provadao;

    @Inject
    private QuestaoDao questaoDao;

    @Inject
    private TipoQuestaoDao tipoQuestaoDao;

    private List<Tbquestao> questoes;
    private Long provaId;
    private Long questaoId;
    private Long tipoQuestaoId;

    @Transacional
    public void gravar() {
        logger.info("Gravando questão {}", questao.getIdQuestao());
        handleTipoQuestao();
        handleProvaAssignment();

        if (questao.getIdQuestao() == null) {
            questaoDao.adiciona(questao);
        } else {
            questaoDao.atualiza(questao);
        }

        questoes = questaoDao.listarTodos();
        questao = new Tbquestao();
    }

    private void handleProvaAssignment() {
        if (provaId != null) {
            Tbprova prova = provadao.buscarPorId(provaId);
            questao.gravarProva(prova);
        }
    }

    private void handleTipoQuestao() {
        if (tipoQuestaoId != null) {
            Tbtipoquestao tipoQuestao = tipoQuestaoDao.buscarPorId(tipoQuestaoId);
            questao.setTbTipoQuestaoidTipoQuestao(tipoQuestao);
        }
    }

    @Transacional
    public void remover(Tbquestao questao) {
        logger.info("Removendo questão {}", questao.getIdQuestao());
        questaoDao.remove(questao);
    }

    public void novo() {
        questao = new Tbquestao();
    }

    // Getters and setters omitted for brevity
}
