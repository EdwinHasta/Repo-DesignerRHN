package InterfacePersistencia;

import Entidades.Organigramas;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;


public interface PersistenciaOrganigramasInterface {
    
    public void crear(Organigramas organigramas);
    public void editar(Organigramas organigramas);
    public void borrar(Organigramas organigramas);
    public Organigramas buscarOrganigrama(Object id);
    public List<Organigramas> buscarOrganigramas();
    public Organigramas buscarOrganigramaToLOV(BigInteger secEmpresa, Date fechaVigencia);
    public Organigramas buscarOrganigramaToLOVRapido(BigInteger secEmpresaCentroCostoEstructura, Date fechaVigencia); 
    public Organigramas organigramaBaseArbol(short codigoOrg);
}
