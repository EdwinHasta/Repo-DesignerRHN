/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.DetallesEmpresas;
import InterfacePersistencia.PersistenciaDetallesEmpresasInterface;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'DetallesEmpresas'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaDetallesEmpresas implements PersistenciaDetallesEmpresasInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public DetallesEmpresas buscarDetalleEmpresa(Short codigoEmpresa) {
        DetallesEmpresas detallesEmpresas =null;
        try {
            Query query = em.createQuery("SELECT de FROM DetallesEmpresas de WHERE de.empresa.codigo = :codigoEmpresa");
            query.setParameter("codigoEmpresa", codigoEmpresa);
            detallesEmpresas = (DetallesEmpresas) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("PersistenciaDetallesEmpresas.buscarDetallesEmpresas.");
            System.out.println("Error consultando los detallesempresas.");
        }
        finally{
            return detallesEmpresas;
        }
    }
}
