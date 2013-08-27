package Validaciones;

import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.metadata.BeanDescriptor;
import org.primefaces.component.inputtext.InputText;
@ManagedBean
@FacesValidator("validar")
public class Validar implements Validator {

    public Validar() {
    }


    @Override
    public <T> Set<ConstraintViolation<T>> validate(T arg0, Class<?>... arg1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateProperty(T arg0, String arg1, Class<?>... arg2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateValue(Class<T> arg0, String arg1, Object arg2, Class<?>... arg3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T unwrap(Class<T> arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
