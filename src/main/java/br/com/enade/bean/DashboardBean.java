package br.com.enade.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.PieChartModel;

import br.com.enade.dao.ResultadoDao;
import br.com.enade.model.Tbresultado;

@Named
@ViewScoped
public class DashboardBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private PieChartModel pieModel;
    private BarChartModel barModel;
    private List<Tbresultado> resultados;

    @Inject
    private ResultadoDao resdao;

    public DashboardBean() {
        resultados = resdao.listarTodos();
        initModels();
    }

    private void initModels() {
        pieModel = new PieChartModel();
        barModel = new BarChartModel();
    }

    public void listarResultado() {
        try {
            resultados = resdao.listarTodos();
            graficar(resultados);
        } catch (Exception e) {
            // Log the exception details here for better debugging
            throw new RuntimeException("Erro ao listar resultados: " + e.getMessage(), e);
        }
    }

    private void graficar(List<Tbresultado> listaResultado) {
        updatePieModel(listaResultado);
    }

    private void updatePieModel(List<Tbresultado> listaResultado) {
        for (Tbresultado resultado : listaResultado) {
            pieModel.set(resultado.getTbUsuarioidUsuario().getNomeUsuario(), resultado.getValorObtido());
        }
        pieModel.setTitle("Resultados dos Alunos");
        pieModel.setLegendPosition("e");
        pieModel.setFill(false);
        pieModel.setShowDataLabels(true);
        pieModel.setDiameter(150);
    }

    // Getters and Setters for pieModel and barModel

    public List<Tbresultado> getResultados() {
        return resultados;
    }

    public void setResultados(List<Tbresultado> resultados) {
        this.resultados = resultados;
    }
}
