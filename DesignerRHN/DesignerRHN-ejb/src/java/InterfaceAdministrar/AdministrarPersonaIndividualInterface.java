/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Cargos;
import Entidades.Ciudades;
import Entidades.Contratos;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.EstadosCiviles;
import Entidades.Estructuras;
import Entidades.JornadasLaborales;
import Entidades.MetodosPagos;
import Entidades.MotivosCambiosCargos;
import Entidades.MotivosCambiosSueldos;
import Entidades.MotivosContratos;
import Entidades.MotivosLocalizaciones;
import Entidades.NormasLaborales;
import Entidades.Papeles;
import Entidades.Periodicidades;
import Entidades.Personas;
import Entidades.ReformasLaborales;
import Entidades.Sucursales;
import Entidades.TercerosSucursales;
import Entidades.TiposContratos;
import Entidades.TiposDocumentos;
import Entidades.TiposSueldos;
import Entidades.TiposTelefonos;
import Entidades.TiposTrabajadores;
import Entidades.UbicacionesGeograficas;
import Entidades.Unidades;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface AdministrarPersonaIndividualInterface {

    public void obtenerConexion(String idSesion);

    public void crearPersona(Personas persona);

    public List<Empleados> lovEmpleados();

    public List<TiposTelefonos> lovTiposTelefonos();

    public List<EstadosCiviles> lovEstadosCiviles();

    public List<TercerosSucursales> lovTercerosSucursales();

    public List<MetodosPagos> lovMetodosPagos();

    public List<Sucursales> lovSucursales();

    public List<Periodicidades> lovPeriodicidades();

    public List<Unidades> lovUnidades();

    public List<TiposSueldos> lovTiposSueldos();

    public List<MotivosCambiosSueldos> lovMotivosCambiosSueldos();

    public List<Contratos> lovContratos();

    public List<NormasLaborales> lovNormasLaborales();

    public List<ReformasLaborales> lovReformasLaborales();

    public List<TiposTrabajadores> lovTiposTrabajadores();

    public List<TiposContratos> lovTiposContratos();

    public List<MotivosContratos> lovMotivosContratos();

    public List<Papeles> lovPapeles();
    
    public List<JornadasLaborales> lovJornadasLaborales();

    public List<UbicacionesGeograficas> lovUbicacionesGeograficas();

    public List<MotivosLocalizaciones> lovMotivosLocalizaciones();

    public List<Estructuras> lovEstructuras();

    public List<MotivosCambiosCargos> lovMotivosCambiosCargos();

    public List<Cargos> lovCargos();

    public List<Ciudades> lovCiudades();

    public List<TiposDocumentos> lovTiposDocumentos();

    public List<Empresas> lovEmpresas();

}
