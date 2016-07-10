import java.util.Map;
import java.util.Map.Entry;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

import com.microsoft.azure.storage.table.TableServiceEntity;

public class MapToTableServiceEntityFactory {
    public static TableServiceEntity create(Map<String, String> fieldMap) throws Exception {
        ClassPool pool = ClassPool.getDefault();
	CtClass cc = pool.makeClass("org.abc");

        // add this to define a super class to extend
        cc.setSuperclass(resolveCtClass(com.microsoft.azure.storage.table.TableServiceEntity.class));

        for (Entry<String, String> entry : fieldMap.entrySet()) {

            cc.addField(new CtField(resolveCtClass(java.lang.String.class), entry.getKey(), cc));
            
            // add getter
            cc.addMethod(generateGetter(cc, entry.getKey(), java.lang.String.class));

            // add setter
            cc.addMethod(generateSetter(cc, entry.getKey(), java.lang.String.class));
        }
        
        TableServiceEntity t = (TableServiceEntity) cc.toClass().newInstance();

        for (Entry<String, String> entry : fieldMap.entrySet()) {
            t.getClass().getMethod("set"
                                   + entry.getKey().substring(0,1).toUpperCase()
                                   + entry.getKey().substring(1),
                                   java.lang.String.class).invoke(t, entry.getValue());
        }

        return t;
    }

    private static CtMethod generateGetter(CtClass declaringClass, String fieldName, Class fieldClass)
        throws Exception {

        String getterName = "get" + fieldName.substring(0, 1).toUpperCase()
            + fieldName.substring(1);

        StringBuffer sb = new StringBuffer();
        sb.append("public ").append(fieldClass.getName()).append(" ")
            .append(getterName).append("(){").append("return this.")
            .append(fieldName).append(";").append("}");
        return CtMethod.make(sb.toString(), declaringClass);
    }

    private static CtMethod generateSetter(CtClass declaringClass, String fieldName, Class fieldClass)
        throws Exception {

        String setterName = "set" + fieldName.substring(0, 1).toUpperCase()
            + fieldName.substring(1);

        StringBuffer sb = new StringBuffer();
        sb.append("public void ").append(setterName).append("(")
            .append(fieldClass.getName()).append(" ").append(fieldName)
            .append(")").append("{").append("this.").append(fieldName)
            .append("=").append(fieldName).append(";").append("}");
        return CtMethod.make(sb.toString(), declaringClass);
    }
    
    private static CtClass resolveCtClass(Class clazz) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        return pool.get(clazz.getName());
    }
}
