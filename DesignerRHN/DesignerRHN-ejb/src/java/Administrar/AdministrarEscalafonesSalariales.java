/**
 * Documentación a cargo de Andres Pineda
 */
package Administrar;

import Entidades.EscalafonesSalariales;
import Entidades.GruposSalariales;
import Entidades.TiposTrabajadores;
import InterfaceAdministrar.AdministrarEscalafonesSalarialesInterface;
import InterfacePersistencia.PersistenciaEscalafonesSalarialesInterface;
import InterfacePersistencia.PersistenciaGruposSalarialesInterface;
import InterfacePersistencia.PersistenciaTiposTrabajadoresInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'EscalafonSalarial'.
 *
 * @author AndresPineda
 */
@Stateful
public class AdministrarEscalafonesSalariales implements AdministrarEscalafonesSalarialesInterface {

    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaEscalafonesSalariales'.
     */
    @EJB
    PersistenciaEscalafonesSalarialesInterface persistenciaEscalafonesSalariales;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaGruposSalariales'.
     */
    @EJB
    PersistenciaGruposSalarialesInterface persistenciaGruposSalariales;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaGruposSalariales'.
     */
    @EJB
    PersistenciaTiposTrabajadoresInterface persistenciaTiposTrabajadores;

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------    
    @Override
    public List<EscalafonesSalariales> listaEscalafonesSalariales() {
        try {
            List<EscalafonesSalariales> lista = persistenciaEscalafonesSalariales.buscarEscalafones();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listaEscalafonesSalariales Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearEscalafonesSalariales(List<EscalafonesSalariales> listaES) {
        try {
            for (int i = 0; i < listaES.size(); i++) {
                persistenciaEscalafonesSalariales.crear(listaES.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearEscalafonesSalariales Admi : " + e.toString());
        }
    }

    @Override
    public void editarEscalafonesSalariales(List<EscalafonesSalariales> listaES) {
        try {
            for (int i = 0; i < listaES.size(); i++) {
                persistenciaEscalafonesSalariales.editar(listaES.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarEscalafonesSalariales Admi : " + e.toString());
        }
    }

    @Override
    public void borrarEscalafonesSalariales(List<EscalafonesSalariales> listaES) {
        try {
            for (int i = 0; i < listaES.size(); i++) {
                persistenciaEscalafonesSalariales.borrar(listaES.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarEscalafonesSalariales Admi : " + e.toString());
        }
    }

    @Override
    public List<GruposSalariales> listaGruposSalarialesParaEscalafonSalarial(BigInteger secEscalafon) {
        try {
            List<GruposSalariales> lista = persistenciaGruposSalariales.buscarGruposSalarialesParaEscalafonSalarial(secEscalafon);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listaGruposSalarialesParaEscalafonSalarial Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearGruposSalariales(List<GruposSalariales> listaGS) {
        try {
            for (int i = 0; i < listaGS.size(); i++) {
                persistenciaGruposSalariales.crear(listaGS.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearGruposSalariales Admi : " + e.toString());
        }
    }

    @Override
    public void editarGruposSalariales(List<GruposSalariales> listaGS) {
        try {
            for (int i = 0; i < listaGS.size(); i++) {
                persistenciaGruposSalariales.editar(listaGS.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarGruposSalariales Admi : " + e.toString());
        }
    }

    @Override
    public void borrarGruposSalariales(List<GruposSalariales> listaGS) {
        try {
            for (int i = 0; i < listaGS.size(); i++) {
                persistenciaGruposSalariales.borrar(listaGS.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error persistenciaEscalafonesSalariales Admi : " + e.toString());
        }
    }

    @Override
    public List<TiposTrabajadores> lovTiposTrabajadores() {
        try {
            List<TiposTrabajadores> lista = persistenciaTiposTrabajadores.buscarTiposTrabajadores();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovTiposTrabajadores Admi : " + e.toString());
            return null;
        }

    }

}
