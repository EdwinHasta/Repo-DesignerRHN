package InterfacePersistencia;

import Entidades.Estructuras;
import java.math.BigInteger;
import java.util.List;


public interface PersistenciaEstructurasInterface {
    
    public void crear(Estructuras estructuras);
    public void editar(Estructuras estructuras);
    public void borrar(Estructuras estructuras);
    public List<Estructuras> estructuras();
    public Estructuras buscarEstructura(Object id);
    public List<Estructuras> buscarEstructurasPorOrganigrama(BigInteger secOrganigrama);
    public List<Estructuras> buscarlistaValores(String fechaVigencia);
    public List<Estructuras> estructuraPadre(BigInteger secOrg);
    public List<Estructuras> estructurasHijas(BigInteger secEstructuraPadre);
    public List<Estructuras> buscarEstructuras();
    public Estructuras buscarEstructuraSecuencia(BigInteger secuencia);
}
