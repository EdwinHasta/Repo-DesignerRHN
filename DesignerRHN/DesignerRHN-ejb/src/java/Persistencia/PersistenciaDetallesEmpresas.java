package Persistencia;

import Entidades.DetallesEmpresas;
import InterfacePersistencia.PersistenciaDetallesEmpresasInterface;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Administrator
 */
@Stateless
public class PersistenciaDetallesEmpresas implements PersistenciaDetallesEmpresasInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public DetallesEmpresas buscarEmpresa() {

        try {
            Query query = em.createQuery("SELECT de FROM DetallesEmpresas de WHERE de.empresa.codigo=1");
            DetallesEmpresas detallesEmpresas = (DetallesEmpresas) query.getSingleResult();
            return detallesEmpresas;
        } catch (Exception e) {
            DetallesEmpresas detallesEmpresas = null;
            return detallesEmpresas;
        }


    }
}
