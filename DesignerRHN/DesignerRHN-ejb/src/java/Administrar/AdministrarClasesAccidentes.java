/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import InterfaceAdministrar.AdministrarClasesAccidentesInterface;
import Entidades.ClasesAccidentes;
import InterfacePersistencia.PersistenciaClasesAccidentesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

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

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public void modificarClasesAccidentes(List<ClasesAccidentes> listClasesAccidentesModificada) {
        ClasesAccidentes clasesAccidentesSeleccionada;
        for (int i = 0; i < listClasesAccidentesModificada.size(); i++) {
            System.out.println("Administrar Modificando...");
            clasesAccidentesSeleccionada = listClasesAccidentesModificada.get(i);
            persistenciaClasesAccidentes.editar(clasesAccidentesSeleccionada);
        }
    }

    @Override
    public void borrarClasesAccidentes(List<ClasesAccidentes> listaClasesAccidentes) {
        for (int i = 0; i < listaClasesAccidentes.size(); i++) {
            System.out.println("Borrando...");
            persistenciaClasesAccidentes.borrar(listaClasesAccidentes.get(i));
        }
    }

    @Override
    public void crearClasesAccidentes(List<ClasesAccidentes> listaClasesAccidentes) {
        for (int i = 0; i < listaClasesAccidentes.size(); i++) {
            System.out.println("Creando...");
            persistenciaClasesAccidentes.crear(listaClasesAccidentes.get(i));
        }
    }

    @Override
    public List<ClasesAccidentes> consultarClasesAccidentes() {
        List<ClasesAccidentes> listClasesAccidentes = persistenciaClasesAccidentes.buscarClasesAccidentes();
        return listClasesAccidentes;
    }

    @Override
    public ClasesAccidentes consultarClaseAccidente(BigInteger secClasesAccidentes) {
        ClasesAccidentes clasesAccidentes = persistenciaClasesAccidentes.buscarClaseAccidente(secClasesAccidentes);
        return clasesAccidentes;
    }

    @Override
    public BigInteger verificarSoAccidentesMedicos(BigInteger secuenciaElementos) {
        BigInteger verificarSoAccidtenesMedicos = null;
        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaElementos);
            verificarSoAccidtenesMedicos = persistenciaClasesAccidentes.contadorSoAccidentesMedicos(secuenciaElementos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarClasesAccidentes verificarSoAccidtenesMedicos ERROR :" + e);
        } finally {
            return verificarSoAccidtenesMedicos;
        }
    }
}
