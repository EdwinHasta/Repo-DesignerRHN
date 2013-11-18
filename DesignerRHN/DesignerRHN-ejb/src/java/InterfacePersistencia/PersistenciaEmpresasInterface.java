package InterfacePersistencia;

import Entidades.Empresas;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;


public interface PersistenciaEmpresasInterface {
    
    public void crear(Empresas empresas);
    public void editar(Empresas empresas);
    public void borrar(Empresas empresas);
    public Empresas buscarEmpresa(Object id);
    public List<Empresas> buscarEmpresas();
    public Empresas buscarEmpresasSecuencia(BigInteger secuencia);
    public String estadoConsultaDatos(BigInteger secuenciaEmpresa);
    public String nombreEmpresa(EntityManager entity);
    
}
