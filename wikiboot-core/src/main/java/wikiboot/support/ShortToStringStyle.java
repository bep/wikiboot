package wikiboot.support;

import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

/**
 * {@link org.apache.commons.lang3.builder.ToStringStyle} used in this project.
 *
 * @author Bjørn Erik Pedersen
 */
public final class ShortToStringStyle extends ToStringStyle {

    public static final ShortToStringStyle SHORT_TO_STRING_STYLE = new ShortToStringStyle();

    private ShortToStringStyle() {
        this.setUseShortClassName(true);
        this.setUseIdentityHashCode(false);
        this.setDefaultFullDetail(false);
        this.setArrayContentDetail(false);
        this.setFieldSeparator(", ");
    }

    @Override
    public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {
        if(value != null && value instanceof String) {
            fullDetail = ((String) value).length() <= 50;
        }
        else {
            fullDetail = value == null || BeanUtils.isSimpleValueType(value.getClass());
        }
        super.append(buffer, fieldName, value, fullDetail);
    }
}
