/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.TiposDocumentos;
import InterfacePersistencia.PersistenciaTiposDocumentosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Administrator
 */
@Stateless
public class PersistenciaTiposDocumentos implements PersistenciaTiposDocumentosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<TiposDocumentos> tiposDocumentos() {
        try {
            Query query = em.createQuery("SELECT td FROM TiposDocumentos td ORDER BY td.nombrecorto");
            List<TiposDocumentos> listaTiposDocumentos = query.getResultList();
            return listaTiposDocumentos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposDocumentos.ciudades " + e);
            return null;
        }
    }
}
