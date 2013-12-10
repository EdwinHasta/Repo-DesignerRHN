/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Cargos;
import Entidades.Empresas;
import Entidades.Estructuras;
import Entidades.Organigramas;
import InterfaceAdministrar.AdministrarEstructurasInterface;
import InterfacePersistencia.PersistenciaCargosInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaEstructurasInterface;
import InterfacePersistencia.PersistenciaOrganigramasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Administrator
 */
@Stateless
public class AdministrarEstructuras implements AdministrarEstructurasInterface {

    //------------------------------------------------------------------------------------------
    //EJB
    //------------------------------------------------------------------------------------------
    @EJB
    PersistenciaEstructurasInterface persistenciaEstructuras;
    @EJB
    PersistenciaCargosInterface persistenciaCargos;
    @EJB
    PersistenciaOrganigramasInterface persistenciaOrganigramas;
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;

    //------------------------------------------------------------------------------------------
    //ATRIBUTOS
    //------------------------------------------------------------------------------------------
    //ESTRUCTURAS
    private List<Estructuras> estructuras;
    private List<Estructuras> estructurasLOV;
    private Estructuras estr;
    //CARGOS
    private List<Cargos> cargos;
    private Cargos cargo;
    private Organigramas org;
    //------------------------------------------------------------------------------------------
    //CONSTRUCTOR
    //------------------------------------------------------------------------------------------
/*    public AdministrarEstructuras() {
     //Estructuras
     persistenciaEstructuras = new PersistenciaEstructuras();
     estructurasLOV = new ArrayList<Estructuras>();
     //Cargos
     persistenciaCargos = new PersistenciaCargos();
     }
     */
    //------------------------------------------------------------------------------------------
    //METODOS DE LA INTERFACE
    //------------------------------------------------------------------------------------------
    //Estructuras--------------------------------------------------------

    @Override
    public List<Estructuras> consultarTodoEstructuras() {
        try {
            estructuras = persistenciaEstructuras.buscarEstructuras();
        } catch (Exception ex) {
            estructuras = null;
        }
        return estructuras;
    }

    @Override
    public Estructuras consultarEstructuraPorSecuencia(BigInteger secuenciaE) {
        try {
            estr = persistenciaEstructuras.buscarEstructura(secuenciaE);
        } catch (Exception ex) {
            estr = null;
        }
        return estr;
    }

    @Override
    public List<Estructuras> consultarNativeQueryEstructuras(String fechaVigencia) {
        try {
            estructurasLOV = persistenciaEstructuras.buscarlistaValores(fechaVigencia);
            return estructurasLOV;
        } catch (Exception ex) {
            System.out.println("Administrar: Fallo al consultar el nativeQuery");
            return estructurasLOV = null;
        }
    }
    //Cargos-----------------------------------------------------------

    @Override
    public List<Cargos> consultarTodoCargos() {
        try {
            cargos = persistenciaCargos.buscarCargos();
        } catch (Exception ex) {
            cargos = null;
        }
        return cargos;
    }

    @Override
    public Cargos consultarCargoPorSecuencia(BigInteger secuenciaC) {
        try {
            cargo = persistenciaCargos.buscarCargoSecuencia(secuenciaC);
        } catch (Exception ex) {
            cargo = null;
        }
        return cargo;
    }

    //PANTALLA ESTRUCTURAS
    @Override
    public List<Estructuras> estructuraPadre(short codigoOrg) {
        List<Estructuras> listaEstructurasPadre;
        Organigramas organigrama = persistenciaOrganigramas.organigramaBaseArbol(codigoOrg);
        if (organigrama != null) {
            listaEstructurasPadre = persistenciaEstructuras.estructuraPadre(organigrama.getSecuencia());
        } else {
            listaEstructurasPadre = null;
        }
        return listaEstructurasPadre;
    }

    public List<Estructuras> estructurasHijas(BigInteger secEstructuraPadre, Short codigoEmpresa) {
        List<Estructuras> listaEstructurasHijas;
        listaEstructurasHijas = persistenciaEstructuras.estructurasHijas(secEstructuraPadre, codigoEmpresa);
        return listaEstructurasHijas;
    }

    public List<Organigramas> obtenerOrganigramas() {
        List<Organigramas> listaOrganigramas;
        listaOrganigramas = persistenciaOrganigramas.buscarOrganigramas();
        return listaOrganigramas;
    }

    public List<Empresas> obtenerEmpresas() {
        List<Empresas> listaEmpresas;
        listaEmpresas = persistenciaEmpresas.buscarEmpresas();
        return listaEmpresas;
    }

    public void modificarOrganigrama(List<Organigramas> listOrganigramasModificados) {
        for (int i = 0; i < listOrganigramasModificados.size(); i++) {
            System.out.println("Modificando...");
            org = listOrganigramasModificados.get(i);
            persistenciaOrganigramas.editar(org);
        }
    }

    public void borrarOrganigrama(Organigramas organigrama) {
        persistenciaOrganigramas.borrar(organigrama);
    }

    public void crearOrganigrama(Organigramas organigrama) {
        persistenciaOrganigramas.crear(organigrama);
    }
    
    

    @Override
    public List<Estructuras> Estructuras() {
        List<Estructuras> listaEstructuras;
        listaEstructuras = persistenciaEstructuras.estructuras();
        return listaEstructuras;
    }

    @Override
    public List<Estructuras> lovEstructuras() {
        return persistenciaEstructuras.estructuras();
    }
}
