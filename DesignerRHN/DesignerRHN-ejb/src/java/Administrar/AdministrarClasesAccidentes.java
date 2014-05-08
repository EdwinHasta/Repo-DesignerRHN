/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.ClasesAccidentes;
import InterfaceAdministrar.AdministrarClasesAccidentesInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaClasesAccidentesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'ClasesAccidentes'.
 *
 * @author betelgeuse
 */
@Stateful
public class AdministrarClasesAccidentes implements AdministrarClasesAccidentesInterface {

    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaClasesAccidentes'.
     */
    @EJB
    PersistenciaClasesAccidentesInterface persistenciaClasesAccidentes;

    /**
     * Enterprise JavaBean.<br>
     * Atributo que representa todo lo referente a la conexión del usuario que
     * está usando el aplicativo.
     */
    @EJB
    AdministrarSesionesInterface administrarSesiones;

    private EntityManager em;

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }

    @Override
    public void modificarClasesAccidentes(List<ClasesAccidentes> listClasesAccidentesModificada) {
        ClasesAccidentes clasesAccidentesSeleccionada;
        for (int i = 0; i < listClasesAccidentesModificada.size(); i++) {
            System.out.println("Administrar Modificando...");
            clasesAccidentesSeleccionada = listClasesAccidentesModificada.get(i);
            persistenciaClasesAccidentes.editar(em,clasesAccidentesSeleccionada);
        }
    }

    @Override
    public void borrarClasesAccidentes(List<ClasesAccidentes> listaClasesAccidentes) {
        for (int i = 0; i < listaClasesAccidentes.size(); i++) {
            System.out.println("Borrando...");
            persistenciaClasesAccidentes.borrar(em,listaClasesAccidentes.get(i));
        }
    }

    @Override
    public void crearClasesAccidentes(List<ClasesAccidentes> listaClasesAccidentes) {
        for (int i = 0; i < listaClasesAccidentes.size(); i++) {
            System.out.println("Creando...");
            persistenciaClasesAccidentes.crear(em,listaClasesAccidentes.get(i));
        }
    }

    @Override
    public List<ClasesAccidentes> consultarClasesAccidentes() {
        List<ClasesAccidentes> listClasesAccidentes = persistenciaClasesAccidentes.buscarClasesAccidentes(em);
        return listClasesAccidentes;
    }

    @Override
    public ClasesAccidentes consultarClaseAccidente(BigInteger secClasesAccidentes) {
        ClasesAccidentes clasesAccidentes = persistenciaClasesAccidentes.buscarClaseAccidente(em,secClasesAccidentes);
        return clasesAccidentes;
    }

    @Override
    public BigInteger verificarSoAccidentesMedicosClaseAccidente(BigInteger secuenciaElementos) {
        BigInteger verificarSoAccidtenesMedicos = null;
        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaElementos);
            verificarSoAccidtenesMedicos = persistenciaClasesAccidentes.contadorSoAccidentesMedicos(em,secuenciaElementos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarClasesAccidentes verificarSoAccidtenesMedicos ERROR :" + e);
        } finally {
            return verificarSoAccidtenesMedicos;
        }
    }
}
