/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesAyuda;

import javax.persistence.EntityManager;

/**
 *
 * @author Administrador
 */
public class SessionEntityManager {

    public String idSession;
    public EntityManager em;

    public SessionEntityManager(String idSession, EntityManager em) {
        this.idSession = idSession;
        this.em = em;
    }
    
    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
