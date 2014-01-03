/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposTelefonos;
import InterfacePersistencia.PersistenciaTiposTelefonosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'TiposTelefonos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateful
public class PersistenciaTiposTelefonos implements PersistenciaTiposTelefonosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TiposTelefonos tiposTelefonos) {
        try {
            em.persist(tiposTelefonos);
        } catch (Exception e) {
            System.out.println("Error almacenar en persistencia tipos telefonos");
        }
    }

    @Override
    public void editar(TiposTelefonos tiposTelefonos) {
        em.merge(tiposTelefonos);
    }

    @Override
    public void borrar(TiposTelefonos tiposTelefonos) {
        try {
            em.remove(em.merge(tiposTelefonos));
        } catch (Exception e) {
            System.out.println("Error TiposTelefonos.borrar");
        }
    }

    @Override
    public TiposTelefonos buscarTipoTelefonos(BigInteger secuencia) {
        try {
            BigInteger sec = new BigInteger(secuencia.toString());            
            return em.find(TiposTelefonos.class, sec);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<TiposTelefonos> tiposTelefonos() {
        try {
            Query query = em.createQuery("SELECT tt FROM TiposTelefonos tt ORDER BY tt.codigo ASC");
            List<TiposTelefonos> listaTiposTelefonos = query.getResultList();
            return listaTiposTelefonos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposTelefonos.tiposTelefonos" + e);
            return null;
        }
    }
}
