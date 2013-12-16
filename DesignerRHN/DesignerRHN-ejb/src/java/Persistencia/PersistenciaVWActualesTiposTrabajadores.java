/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Empleados;
import Entidades.VWActualesTiposTrabajadores;
import InterfacePersistencia.PersistenciaVWActualesTiposTrabajadoresInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la vista 'VWActualesTiposTrabajadores'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
    public class PersistenciaVWActualesTiposTrabajadores implements PersistenciaVWActualesTiposTrabajadoresInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public VWActualesTiposTrabajadores buscarTipoTrabajador(BigInteger secuencia) {
        try {
            VWActualesTiposTrabajadores vwActualesTiposTrabajadores = (VWActualesTiposTrabajadores) em.createNamedQuery("VWActualesTiposTrabajadores.findByEmpleado")
                    .setParameter("empleado", secuencia)
                    .getSingleResult();
            return vwActualesTiposTrabajadores;
        } catch (Exception e) {
            VWActualesTiposTrabajadores vwActualesTiposTrabajadores = null;
            return vwActualesTiposTrabajadores;
        }
    }

    @Override
    public List<VWActualesTiposTrabajadores> FiltrarTipoTrabajador(String p_tipo) {
        try {
            if (!p_tipo.isEmpty()) {
                List<VWActualesTiposTrabajadores> vwActualesTiposTrabajadoresLista = (List<VWActualesTiposTrabajadores>) em.createNamedQuery("VWActualesTiposTrabajadores.findByTipoTrabajador")
                        .setParameter("tipotrabajador", p_tipo)
                        .getResultList();
                return vwActualesTiposTrabajadoresLista;
            } else {
                System.out.println("Error en PersistenciaVWActualesTiposTrabajadores.FiltrarTipoTrabajador. "
                        + "No recibió el parametro");
                List<VWActualesTiposTrabajadores> vwActualesTiposTrabajadores = null;
                return vwActualesTiposTrabajadores;
            }
        } catch (Exception e) {
            List<VWActualesTiposTrabajadores> vwActualesTiposTrabajadores = null;
            return vwActualesTiposTrabajadores;
        }
    }

    @Override
    public List<VWActualesTiposTrabajadores> busquedaRapidaTrabajadores() {
        try {
            List<VWActualesTiposTrabajadores> vwActualesTiposTrabajadoresLista = (List<VWActualesTiposTrabajadores>) em.createNamedQuery("VWActualesTiposTrabajadores.findAll")
                    .getResultList();
            return vwActualesTiposTrabajadoresLista;
        } catch (Exception e) {
            List<VWActualesTiposTrabajadores> vwActualesTiposTrabajadores = null;
            return vwActualesTiposTrabajadores;
        }
    }

    //VALIDACION ARCHIVO PLANO
    @Override
    public boolean verificarTipoTrabajador(Empleados empleado) {
        try {
            Query query = em.createQuery("SELECT vw.tipoTrabajador.tipo FROM VWActualesTiposTrabajadores vw WHERE vw.empleado.secuencia= :secuencia");
            query.setParameter("secuencia", empleado.getSecuencia());
            String tipoEmpleado = (String) query.getSingleResult();
            return tipoEmpleado.equalsIgnoreCase("ACTIVO");
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaVWActualesTiposTrabajadores.verificarTipoTrabajador");
            return false;
        }
    }
    
    @Override
    public List<VWActualesTiposTrabajadores> tipoTrabajadorEmpleado() {
        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesTiposTrabajadores vw where vw.tipoTrabajador.tipo IN ('ACTIVO','PENSIONADO','RETIRADO')");
            List<VWActualesTiposTrabajadores> tipoEmpleado = query.getResultList();
            System.out.println("Tiene: " + tipoEmpleado.size()+ " registros");
            return tipoEmpleado;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaVWActualesTiposTrabajadores.tipoTrabajadorEmpleado" + e);
            return null;
        }
    }
}
