package InterfacePersistencia;

import Entidades.Direcciones;
import Entidades.Encargaturas;
import Entidades.HVHojasDeVida;
import Entidades.InformacionesAdicionales;
import Entidades.Telefonos;
import Entidades.VigenciasEstadosCiviles;
import Entidades.VigenciasFormales;
import java.math.BigInteger;

public interface AdministrarEmpleadoIndividualInterface {
    public HVHojasDeVida hvHojaDeVidaPersona(BigInteger secPersona);
    public Telefonos primerTelefonoPersona(BigInteger secEmpleado);
    public Direcciones primeraDireccionPersona(BigInteger secEmpleado);
    public VigenciasEstadosCiviles estadoCivilPersona(BigInteger secEmpleado);
    public InformacionesAdicionales informacionAdicionalPersona(BigInteger secEmpleado);
    public Encargaturas reemplazoPersona(BigInteger secEmpleado);
    public VigenciasFormales educacionPersona(BigInteger secPersona);
}
