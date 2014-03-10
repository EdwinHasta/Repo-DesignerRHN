/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.Departamentos;
import Entidades.Paises;
import InterfaceAdministrar.AdministrarDepartamentosInterface;
import InterfacePersistencia.PersistenciaDepartamentosInterface;
import InterfacePersistencia.PersistenciaPaisesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'Departamentos'.
 *
 * @author betelgeuse
 */
@Stateful
public class AdministrarDepartamentos implements AdministrarDepartamentosInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaDepartamentos'.
     */
    @EJB
    PersistenciaDepartamentosInterface persistenciaDepartamentos;
    @EJB
    PersistenciaPaisesInterface persistenciaPaises;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------

    public void modificarDepartamentos(List<Departamentos> listaDepartamentos) {
        for (int i = 0; i < listaDepartamentos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaDepartamentos.editar(listaDepartamentos.get(i));
        }
    }

    public void borrarDepartamentos(List<Departamentos> listaDepartamentos) {
        for (int i = 0; i < listaDepartamentos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaDepartamentos.borrar(listaDepartamentos.get(i));
        }
    }

    public void crearDepartamentos(List<Departamentos> listaDepartamentos) {
        for (int i = 0; i < listaDepartamentos.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaDepartamentos.crear(listaDepartamentos.get(i));
        }
    }

    public List<Departamentos> consultarDepartamentos() {
        List<Departamentos> listaDepartamentos;
        listaDepartamentos = persistenciaDepartamentos.consultarDepartamentos();
        return listaDepartamentos;
    }

    public Departamentos consultarTipoIndicador(BigInteger secMotivoDemanda) {
        Departamentos tiposIndicadores;
        tiposIndicadores = persistenciaDepartamentos.consultarDepartamento(secMotivoDemanda);
        return tiposIndicadores;
    }

    public List<Paises> consultarLOVPaises() {
        List<Paises> listLOVPaises;
        listLOVPaises = persistenciaPaises.consultarPaises();
        return listLOVPaises;
    }

    public BigInteger contarBienProgramacionesDepartamento(BigInteger secuenciaVigenciasIndicadores) {
        BigInteger contarBienProgramacionesDepartamento = null;

        try {
            contarBienProgramacionesDepartamento = persistenciaDepartamentos.contarBienProgramacionesDepartamento(secuenciaVigenciasIndicadores);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarDepartamenos contarBienProgramacionesDepartamento ERROR :" + e);
        } finally {
            return contarBienProgramacionesDepartamento;
        }
    }

    public BigInteger contarCapModulosDepartamento(BigInteger secuenciaVigenciasIndicadores) {
        BigInteger contarBienProgramacionesDepartamento = null;
        try {
            contarBienProgramacionesDepartamento = persistenciaDepartamentos.contarCapModulosDepartamento(secuenciaVigenciasIndicadores);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarDepartamenos contarCapModulosDepartamento ERROR :" + e);
        } finally {
            return contarBienProgramacionesDepartamento;
        }
    }

    public BigInteger contarSoAccidentesMedicosDepartamento(BigInteger secuenciaVigenciasIndicadores) {
        BigInteger contarBienProgramacionesDepartamento = null;
        try {
            contarBienProgramacionesDepartamento = persistenciaDepartamentos.contarSoAccidentesMedicosDepartamento(secuenciaVigenciasIndicadores);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarDepartamenos contarSoAccidentesMedicosDepartamento ERROR :" + e);
        } finally {
            return contarBienProgramacionesDepartamento;
        }
    }

    public BigInteger contarCiudadesDepartamento(BigInteger secuenciaVigenciasIndicadores) {
        BigInteger contarBienProgramacionesDepartamento = null;
        try {
            contarBienProgramacionesDepartamento = persistenciaDepartamentos.contarCiudadesDepartamento(secuenciaVigenciasIndicadores);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarDepartamenos contarCiudadesDepartamento ERROR :" + e);
        } finally {
            return contarBienProgramacionesDepartamento;
        }
    }

}
