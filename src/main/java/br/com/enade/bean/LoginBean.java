package br.com.enade.bean;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.enade.dao.UsuarioDao;
import br.com.enade.model.Tbusuario;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Tbusuario usuario;

    @Inject
    private UsuarioDao dao;

    @Inject
    private FacesContext context;

    private Tbusuario usuarioLogado;

    public Tbusuario getUsuario() {
        return usuario;
    }

    public String efetuaLogin() {
        if (usuario == null || dao == null || context == null) {
            throw new IllegalStateException("Injection failure");
        }

        log("Attempting login for user: " + usuario.getEmailUsuario());
        
        if (dao.existe(usuario)) {
            usuarioLogado = dao.recuperarUsuario(usuario);
            addToSession(usuarioLogado);
            return "index?faces-redirect=true";
        } else {
            addMessage("Usuario nao encontrado");
            return "login?faces-redirect=true";
        }
    }

    private void log(String message) {
        // Utilize um framework de logging adequado aqui
        System.out.println(message); // Exemplo, substituir por Logger
    }

    private void addToSession(Tbusuario user) {
        context.getExternalContext().getSessionMap().put("usuarioLogado", user);
    }

    private void addMessage(String detail) {
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(detail));
    }

    public String deslogar() {
        context.getExternalContext().getSessionMap().remove("usuarioLogado");
        return "login?faces-redirect=true";
    }

    public Tbusuario getUsuarioLogado() {
        return usuarioLogado;
    }
}
