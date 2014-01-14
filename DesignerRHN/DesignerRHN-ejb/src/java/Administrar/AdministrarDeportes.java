/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import InterfaceAdministrar.AdministrarDeportesInterface;
import Entidades.Deportes;
import InterfacePersistencia.PersistenciaDeportesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'Deportes'.
 *
 * @author betelgeuse
 */
@Stateful
public class AdministrarDeportes implements AdministrarDeportesInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaDeportes'.
     */
    @EJB
    PersistenciaDeportesInterface persistenciaDeportes;

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------  
    @Override
    public void modificarDeportes(List<Deportes> listDeportesModificadas) {
        for (int i = 0; i < listDeportesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            Deportes deporteSeleccionado = listDeportesModificadas.get(i);
            persistenciaDeportes.editar(deporteSeleccionado);
        }
    }

    @Override
    public void borrarDeportes(List<Deportes> listaDeportes) {
        for (int i = 0; i < listaDeportes.size(); i++) {
            System.out.println("Borrando...");
            persistenciaDeportes.borrar(listaDeportes.get(i));
        }
    }

    @Override
    public void crearDeportes(List<Deportes> listaDeportes) {
        for (int i = 0; i < listaDeportes.size(); i++) {
            System.out.println("Creando...");
            persistenciaDeportes.crear(listaDeportes.get(i));
        }
    }

    @Override
    public List<Deportes> mostrarDeportes() {
        List<Deportes> listDeportes = persistenciaDeportes.buscarDeportes();
        return listDeportes;
    }

    @Override
    public Deportes mostrarDeporte(BigInteger secDeportes) {
        Deportes deportes = persistenciaDeportes.buscarDeporte(secDeportes);
        return deportes;
    }

    @Override
    public BigInteger verificarBorradoVigenciasDeportes(BigInteger secDeporte) {
        BigInteger verificarBorradoVigenciasDeportes = null;
        try {
            verificarBorradoVigenciasDeportes = persistenciaDeportes.verificarBorradoVigenciasDeportes(secDeporte);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARDEPORTES verificarBorradoVigenciasDeportes ERROR :" + e);
        } finally {
            return verificarBorradoVigenciasDeportes;
        }
    }

    @Override
    public BigInteger contadorDeportesPersonas(BigInteger secDeporte) {
        BigInteger contadorDeportesPersonas = null;
        try {
            contadorDeportesPersonas = persistenciaDeportes.contadorDeportesPersonas(secDeporte);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARDEPORTES contadorDeportesPersonas ERROR :" + e);
        } finally {
            return contadorDeportesPersonas;
        }
    }

    @Override
    public BigInteger contadorParametrosInformes(BigInteger secDeporte) {
        BigInteger contadorParametrosInformes = null;
        try {
            contadorParametrosInformes = persistenciaDeportes.contadorParametrosInformes(secDeporte);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARDEPORTES contadorParametrosInformes ERROR :" + e);
        } finally {
            return contadorParametrosInformes;
        }
    }
}
