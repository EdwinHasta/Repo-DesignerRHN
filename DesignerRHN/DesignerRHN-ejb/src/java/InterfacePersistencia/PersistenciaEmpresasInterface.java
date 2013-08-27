package InterfacePersistencia;

import Entidades.Empresas;
import java.math.BigInteger;
import java.util.List;


public interface PersistenciaEmpresasInterface {
    
    public void crear(Empresas empresas);
    public void editar(Empresas empresas);
    public void borrar(Empresas empresas);
    public Empresas buscarEmpresa(Object id);
    public List<Empresas> buscarEmpresas();
    public Empresas buscarEmpresasSecuencia(BigInteger secuencia);
    
}
