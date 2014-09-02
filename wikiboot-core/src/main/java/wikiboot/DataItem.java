package wikiboot;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.ui.ModelMap;

import javax.persistence.Transient;

/**
 * Represents a data item in a {@link wikiboot.DataSet}.
 *
 * @author Bjørn Erik Pedersen
 */
public class DataItem {
    private final String title;
    private final ModelMap model;

    public DataItem(String title, ModelMap model) {
        this.title = title;
        this.model = model;
    }

    public String getTitle() {
        return title;
    }

    public ModelMap getModel() {
        return model;
    }

    @Transient
    public Object getNestedObjectFromModel(String key, String propertyName) {
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(getModel().get(key));
        return wrapper.getPropertyValue(propertyName);
    }
}
