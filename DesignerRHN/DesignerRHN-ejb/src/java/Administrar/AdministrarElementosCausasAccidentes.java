/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import InterfaceAdministrar.AdministrarElementosCausasAccidentesInterface;
import Entidades.ElementosCausasAccidentes;
import InterfacePersistencia.PersistenciaElementosCausasAccidentesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'ElementosCausasAccidentes'.
 *
 * @author betelgeuse
 */
@Stateful
public class AdministrarElementosCausasAccidentes implements AdministrarElementosCausasAccidentesInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaElementosCausasAccidentes'.
     */
    @EJB
    PersistenciaElementosCausasAccidentesInterface persistenciaElementosCausasAccidentes;

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public void modificarElementosCausasAccidentes(List<ElementosCausasAccidentes> listaElementosCausasAccidentes) {
        ElementosCausasAccidentes elementosCausasAccidentesSeleccionada;
        for (int i = 0; i < listaElementosCausasAccidentes.size(); i++) {
            System.out.println("Administrar Modificando...");
            elementosCausasAccidentesSeleccionada = listaElementosCausasAccidentes.get(i);
            persistenciaElementosCausasAccidentes.editar(elementosCausasAccidentesSeleccionada);
        }
    }

    @Override
    public void borrarElementosCausasAccidentes(List<ElementosCausasAccidentes> listaElementosCausasAccidentes) {
        for (int i = 0; i < listaElementosCausasAccidentes.size(); i++) {
            System.out.println("Borrando...");
            persistenciaElementosCausasAccidentes.borrar(listaElementosCausasAccidentes.get(i));
        }
    }

    @Override
    public void crearElementosCausasAccidentes(List<ElementosCausasAccidentes> listaElementosCausasAccidentes) {
        for (int i = 0; i < listaElementosCausasAccidentes.size(); i++) {
            System.out.println("Creando...");
            persistenciaElementosCausasAccidentes.crear(listaElementosCausasAccidentes.get(i));
        }
    }

    @Override
    public List<ElementosCausasAccidentes> consultarElementosCausasAccidentes() {
        List<ElementosCausasAccidentes> listElementosCausasAccidentes = persistenciaElementosCausasAccidentes.buscarElementosCausasAccidentes();
        return listElementosCausasAccidentes;
    }

    @Override
    public ElementosCausasAccidentes consultarElementoCausaAccidente(BigInteger secElementosCausasAccidentes) {
        ElementosCausasAccidentes elementosCausasAccidentes = persistenciaElementosCausasAccidentes.buscarElementoCausaAccidente(secElementosCausasAccidentes);
        return elementosCausasAccidentes;
    }

    @Override
    public BigInteger contarSoAccidentesCausa(BigInteger secTiposAuxilios) {
        BigInteger contadorSoAccidentes = null;
        try {
            contadorSoAccidentes = persistenciaElementosCausasAccidentes.contadorSoAccidentes(secTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARELEMENTOSCAUSASACCIDENTES contadorSoAccidentes ERROR :" + e);
        } finally {
            return contadorSoAccidentes;
        }
    }

    @Override
    public BigInteger contarSoAccidentesMedicosElementoCausaAccidente(BigInteger secTiposAuxilios) {
        BigInteger contadorSoAccidentesMedicos = null;
        try {
            contadorSoAccidentesMedicos = persistenciaElementosCausasAccidentes.contadorSoAccidentesMedicos(secTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARELEMENTOSCAUSASACCIDENTES contadorSoAccidentesMedicos ERROR :" + e);
        } finally {
            return contadorSoAccidentesMedicos;
        }
    }

    @Override
    public BigInteger contarSoIndicadoresFrElementoCausaAccidente(BigInteger secTiposAuxilios) {
        BigInteger contadorSoIndicadoresFr = null;
        try {
            contadorSoIndicadoresFr = persistenciaElementosCausasAccidentes.contadorSoIndicadoresFr(secTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARELEMENTOSCAUSASACCIDENTES contadorSoIndicadoresFr ERROR :" + e);
        } finally {
            return contadorSoIndicadoresFr;
        }
    }
}
