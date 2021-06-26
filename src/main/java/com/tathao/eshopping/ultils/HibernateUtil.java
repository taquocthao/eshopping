package com.tathao.eshopping.ultils;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import java.util.*;

public class HibernateUtil {

    private static final String alias = " A";
    private static final String aliasDot = " A.";
    private static final String like = " like ";
    public static final String eq = " = ";
    public static final String noteq = " != ";
    public static final String notIn = " not in ";
    public static final String less = " < ";
    public static final String greater = " > ";
    public static final String in = " in ";
    private static final String and = " AND ";
    private static final String or = " OR ";
    private static final String from = " from ";
    private static final String where = " where ";
    private static final String fquote = " '";
    private static final String lquote = "'";
    private static final String percentQuote = "%'";

    public HibernateUtil() {
    }

    public static Criterion[] createCriterion(Map<String, String> propertyNameValues, boolean includeNull) {
        if (propertyNameValues == null) {
            return null;
        } else {
            Criterion[] criterion = new Criterion[propertyNameValues.size()];
            int i = 0;

            for(Iterator i$ = propertyNameValues.keySet().iterator(); i$.hasNext(); ++i) {
                String key = (String)i$.next();
                String value = (String)propertyNameValues.get(key);
                if (value == null && includeNull) {
                    criterion[i] = Restrictions.isNull(key);
                } else if (value != null) {
                    criterion[i] = Restrictions.ilike(key, value + "%");
                }
            }

            return criterion;
        }
    }

    private static String getNoDotProperty(String property) {
        if (property.indexOf("^$^") == 0) {
            property = property.substring("^$^".length());
        }

        return property.replace('.', 'X');
    }

    private static String getListValue(List values) {
        StringBuilder buf = new StringBuilder(20);
        int i = 0;

        for(Iterator i$ = values.iterator(); i$.hasNext(); ++i) {
            Object o = i$.next();
            if (i > 0) {
                buf.append(",");
            }

            if (o instanceof String) {
                buf.append("'");
                buf.append(o.toString());
                buf.append("'");
            } else {
                buf.append(o.toString());
            }
        }

        return buf.toString();
    }

    private static String getNameCriteria(HibernateUtil.PropertyType additionalTypeInfo, String property, Object[] value, boolean exactMatch, boolean ignoreCase) {
        StringBuilder buffer = new StringBuilder();
        if (value[1] instanceof List) {
            buffer.append(" A.").append(property);
            if (value[0] != null && " not in ".equalsIgnoreCase(value[0].toString())) {
                buffer.append(" not in ");
            } else {
                buffer.append(" in ");
            }

            buffer.append("(");
            buffer.append(getListValue((List)value[1]));
            buffer.append(")");
        } else if ((additionalTypeInfo == null || additionalTypeInfo != HibernateUtil.PropertyType.STRING) && !(value[1] instanceof String)) {
            buffer.append(" A.").append(property).append(value[0]).append(":").append(getNoDotProperty(property));
        } else if (exactMatch) {
            if (ignoreCase) {
                buffer.append(" upper(").append(" A.").append(property).append(")").append(value[0]).append(" upper(").append(":").append(getNoDotProperty(property)).append(")");
            } else {
                buffer.append(" A.").append(property).append(value[0]).append(":").append(getNoDotProperty(property));
            }
        } else if (value[0] != null && " not in ".equalsIgnoreCase(value[0].toString())) {
            buffer.append(" A.").append(property).append(" not in ").append("(:").append(getNoDotProperty(property)).append(")");
        } else if (value[0] != null && " in ".equalsIgnoreCase(value[0].toString())) {
            buffer.append(" A.").append(property).append(" in ").append("(:").append(getNoDotProperty(property)).append(")");
        } else if (property.indexOf("^$^") == 0) {
            property = property.substring("^$^".length());
            buffer.append(" upper(").append(" A.").append(property).append(") like upper(:").append(getNoDotProperty(property)).append(" ||'%')");
        } else {
            buffer.append(" upper(").append(" A.").append(property).append(") like upper('%'|| :").append(getNoDotProperty(property)).append(" ||'%')");
        }

        return buffer.toString();
    }

    private static String getNameCriteria(String property, Object[] value, Map<String, HibernateUtil.PropertyType> additionalTypeInfo, boolean exactMatch, boolean ignoreCase) {
        return additionalTypeInfo != null && additionalTypeInfo.get(property) != null ? getNameCriteria((HibernateUtil.PropertyType)additionalTypeInfo.get(property), property, value, exactMatch, ignoreCase) : getNameCriteria((HibernateUtil.PropertyType)null, (String)property, (Object[])value, exactMatch, ignoreCase);
    }

    public static Object[] buildNameQuery(Class clazz, Map<String, Object> propertyNameValues, Map<String, HibernateUtil.PropertyType> additionalTypeInfo, String orderBy, String orderDirection, boolean andSearch, boolean exactMatch, String whereClause, boolean ignoreCase) {
        StringBuilder buffer = new StringBuilder(100);
        StringBuilder orderByBuffer = new StringBuilder();
        buffer.append(" from ").append(clazz.getName()).append(" A");
        if (propertyNameValues.size() <= 0) {
            if (whereClause != null && whereClause.trim().length() > 0) {
                buffer.append(" where ");
                buffer.append(whereClause);
            }

            if (orderBy != null && !"".equals(orderBy)) {
                orderByBuffer.append(" order by A.");
                orderByBuffer.append(orderBy);
                if (orderDirection != null && !"".equals(orderDirection)) {
                    orderByBuffer.append(orderDirection.equals("2") ? " asc" : " desc");
                }
                orderByBuffer.append(" nulls last ");
            }

            return new Object[]{buffer.toString(), orderByBuffer.toString()};
        } else {
            buffer.append(" where ");
            if (whereClause != null && whereClause.trim().length() > 0) {
                buffer.append(whereClause);
                if (andSearch) {
                    buffer.append(" AND ");
                } else {
                    buffer.append(" OR ");
                }
            }

            int i = 0;
            StringBuilder paramNames = new StringBuilder();
            ArrayList<Object> values = new ArrayList();
            Map<String, Object> propertyNameValuesEx = new HashMap();
            Iterator i$ = propertyNameValues.keySet().iterator();

            while(true) {
                Object[] value;
                Object temp;
                String property;
                while(i$.hasNext()) {
                    String property2 = (String)i$.next();
                    property = property2;
                    temp = propertyNameValues.get(property2);
                    if (temp instanceof ArrayList) {
                        propertyNameValuesEx.put(property2, temp);
                    } else {
                        if (temp instanceof Object[]) {
                            value = (Object[])((Object[])temp);
                        } else {
                            value = new Object[]{" = ", temp};
                        }

                        if (i > 0) {
                            if (andSearch) {
                                buffer.append(" AND ");
                            } else {
                                buffer.append(" OR ");
                            }
                        }

                        property2 = getNameCriteria(property2, value, additionalTypeInfo, exactMatch, ignoreCase);
                        buffer.append(property2);
                        if (property2.indexOf(":") > 0) {
                            if (i > 0) {
                                paramNames.append(",");
                            }

                            paramNames.append(getNoDotProperty(property));
                            values.add(value[1]);
                            ++i;
                        } else if (value != null && value[0] != null && (" in ".equalsIgnoreCase(value[0].toString()) || " not in ".equalsIgnoreCase(value[0].toString()))) {
                            ++i;
                        }
                    }
                }

                Iterator i2$ = propertyNameValuesEx.keySet().iterator();

                while(true) {
                    do {
                        if (!i2$.hasNext()) {
                            if (orderBy != null && !"".equals(orderBy)) {
                                orderByBuffer.append(" order by A.");
                                orderByBuffer.append(orderBy);
                                if (orderDirection != null && !"".equals(orderDirection)) {
                                    orderByBuffer.append(" " + (orderDirection.equals("2") ? "asc" : "desc"));
                                }
                                orderByBuffer.append(" nulls last ");
                            }

                            if (values.size() > 0) {
                                return new Object[]{buffer.toString(), orderByBuffer.toString(), paramNames.toString().split("[,]"), values.toArray()};
                            }

                            return new Object[]{buffer.toString(), orderByBuffer.toString()};
                        }

                        property = (String)i2$.next();
                        temp = propertyNameValuesEx.get(property);
                    } while(!(temp instanceof ArrayList));

                    ArrayList propertyNameValueList = (ArrayList)temp;

                    for(int j = 0; j < propertyNameValueList.size(); ++j) {
                        temp = propertyNameValueList.get(j);
                        if (temp instanceof Object[]) {
                            value = (Object[])((Object[])temp);
                            if (i > 0) {
                                buffer.append(" AND ");
                            }

                            String str = getNameCriteria(property, value, j);
                            buffer.append(str);
                            if (str.indexOf(":") > 0) {
                                if (i > 0) {
                                    paramNames.append(",");
                                }

                                paramNames.append(getNoDotProperty(property + j));
                                values.add(value[1]);
                                ++i;
                            }
                        }
                    }
                }
            }
        }
    }

    public static Object[] buildNameQuery4Delete(Map<String, Object> propertyNameValues, Map<String, HibernateUtil.PropertyType> additionalTypeInfo, boolean andSearch, boolean exactMatch, String whereClause, boolean ignoreCase) {
        StringBuilder buffer = new StringBuilder(100);
        StringBuilder orderByBuffer = new StringBuilder();
        if (propertyNameValues.size() <= 0) {
            if (whereClause != null && whereClause.trim().length() > 0) {
                buffer.append(" where ");
                buffer.append(whereClause);
            }

            return new Object[]{buffer.toString(), orderByBuffer.toString()};
        } else {
            buffer.append(" where ");
            if (whereClause != null && whereClause.trim().length() > 0) {
                buffer.append(whereClause);
                if (andSearch) {
                    buffer.append(" AND ");
                } else {
                    buffer.append(" OR ");
                }
            }

            int i = 0;
            StringBuilder paramNames = new StringBuilder();
            ArrayList<Object> values = new ArrayList();
            Map<String, Object> propertyNameValuesEx = new HashMap();
            Iterator i$ = propertyNameValues.keySet().iterator();

            while(true) {
                Object[] value;
                Object temp;
                String property;
                while(i$.hasNext()) {
                    property = (String)i$.next();
                    temp = propertyNameValues.get(property);
                    if (temp instanceof ArrayList) {
                        propertyNameValuesEx.put(property, temp);
                    } else {
                        if (temp instanceof Object[]) {
                            value = (Object[])((Object[])temp);
                        } else {
                            value = new Object[]{" = ", temp};
                        }

                        if (i > 0) {
                            if (andSearch) {
                                buffer.append(" AND ");
                            } else {
                                buffer.append(" OR ");
                            }
                        }

                        property = getNameCriteria(property, value, additionalTypeInfo, exactMatch, ignoreCase);
                        buffer.append(property);
                        if (property.indexOf(":") > 0) {
                            if (i > 0) {
                                paramNames.append(",");
                            }

                            paramNames.append(getNoDotProperty(property));
                            values.add(value[1]);
                            ++i;
                        } else if (value != null && value[0] != null && (" in ".equalsIgnoreCase(value[0].toString()) || " not in ".equalsIgnoreCase(value[0].toString()))) {
                            ++i;
                        }
                    }
                }

                i$ = propertyNameValuesEx.keySet().iterator();

                while(true) {
                    do {
                        if (!i$.hasNext()) {
                            if (values.size() > 0) {
                                return new Object[]{buffer.toString(), orderByBuffer.toString(), paramNames.toString().split("[,]"), values.toArray()};
                            }

                            return new Object[]{buffer.toString(), orderByBuffer.toString()};
                        }

                        property = (String)i$.next();
                        temp = propertyNameValuesEx.get(property);
                    } while(!(temp instanceof ArrayList));

                    ArrayList propertyNameValueList = (ArrayList)temp;

                    for(int j = 0; j < propertyNameValueList.size(); ++j) {
                        temp = propertyNameValueList.get(j);
                        if (temp instanceof Object[]) {
                            value = (Object[])((Object[])temp);
                            if (i > 0) {
                                buffer.append(" AND ");
                            }

                            String str = getNameCriteria(property, value, j);
                            buffer.append(str);
                            if (str.indexOf(":") > 0) {
                                if (i > 0) {
                                    paramNames.append(",");
                                }

                                paramNames.append(getNoDotProperty(property + j));
                                values.add(value[1]);
                                ++i;
                            }
                        }
                    }
                }
            }
        }
    }

    private static String getNameCriteria(String property, Object[] value, int index) {
        return " A." + property + value[0] + ":" + getNoDotProperty(property) + index;
    }

    public static void populateProperty(String[] properties, Object persistentBean) throws Exception {
        Object o = null;

        for(int i = 0; i < properties.length; ++i) {
            if (properties[i].indexOf(46) > 0) {
                String[] p = properties[i].split("[.]");
                Object temp = persistentBean;

                for(int j = 0; j < p.length; ++j) {
                    o = PropertyUtils.getProperty(temp, p[j]);
                    if (o == null) {
                        break;
                    }

                    temp = o;
                }
            } else {
                o = PropertyUtils.getProperty(persistentBean, properties[i]);
            }

            if (o != null && (o instanceof Collection || o instanceof List)) {
                Collection var7 = (Collection)o;
            }
        }

    }

    public static Object[] buildNameQuery(Class clazz, Map<String, Object> propertyNameValues, Map<String, HibernateUtil.PropertyType> additionalTypeInfo, String orderBy, String orderDirection, boolean andSearch, boolean exactMatch, String whereClause, boolean ignoreCase, String selectFromClause) {
        StringBuilder buffer = new StringBuilder(100);
        if (!StringUtils.isEmpty(selectFromClause)) {
            buffer.append(selectFromClause);
            buffer.append(" from ").append(clazz.getName()).append(" A");
        } else {
            buffer.append(selectFromClause);
        }

        if (propertyNameValues.size() <= 0) {
            if (whereClause != null && whereClause.trim().length() > 0) {
                buffer.append(" where ").append(whereClause);
            }

            if (orderBy != null && !"".equals(orderBy)) {
                buffer.append(" order by A.").append(orderBy);
                if (orderDirection != null && !"".equals(orderDirection)) {
                    buffer.append(orderDirection.equals("2") ? " asc" : " desc");
                }
            }

            return new Object[]{buffer.toString()};
        } else {
            buffer.append(" where ");
            if (whereClause != null && whereClause.trim().length() > 0) {
                buffer.append(whereClause);
                if (andSearch) {
                    buffer.append(" AND ");
                } else {
                    buffer.append(" OR ");
                }
            }

            int i = 0;
            StringBuilder paramNames = new StringBuilder();
            ArrayList<Object> values = new ArrayList();
            Map<String, Object> propertyNameValuesEx = new HashMap();
            Iterator i$ = propertyNameValues.keySet().iterator();

            Object[] value;
            Object temp;
            String property;
            while(i$.hasNext()) {
                property = (String)i$.next();
                temp = propertyNameValues.get(property);
                if (temp instanceof ArrayList) {
                    propertyNameValuesEx.put(property, temp);
                } else {
                    if (temp instanceof Object[]) {
                        value = (Object[])((Object[])temp);
                    } else {
                        value = new Object[]{" = ", temp};
                    }

                    if (i > 0) {
                        if (andSearch) {
                            buffer.append(" AND ");
                        } else {
                            buffer.append(" OR ");
                        }
                    }

                    property = getNameCriteria(property, value, additionalTypeInfo, exactMatch, ignoreCase);
                    buffer.append(property);
                    if (property.indexOf(":") > 0) {
                        if (i > 0) {
                            paramNames.append(",");
                        }

                        paramNames.append(getNoDotProperty(property));
                        values.add(value[1]);
                        ++i;
                    }
                }
            }

            i$ = propertyNameValuesEx.keySet().iterator();

            while(true) {
                do {
                    if (!i$.hasNext()) {
                        if (orderBy != null && !"".equals(orderBy)) {
                            buffer.append(" order by A.").append(orderBy);
                            if (orderDirection != null && !"".equals(orderDirection)) {
                                buffer.append(orderDirection.equals("2") ? " asc" : " desc");
                            }
                        }

                        if (values.size() > 0) {
                            return new Object[]{buffer.toString(), paramNames.toString().split("[,]"), values.toArray()};
                        }

                        return new Object[]{buffer.toString()};
                    }

                    property = (String)i$.next();
                    temp = propertyNameValuesEx.get(property);
                } while(!(temp instanceof ArrayList));

                ArrayList propertyNameValueList = (ArrayList)temp;

                for(int j = 0; j < propertyNameValueList.size(); ++j) {
                    temp = propertyNameValueList.get(j);
                    if (temp instanceof Object[]) {
                        value = (Object[])((Object[])temp);
                        if (i > 0) {
                            buffer.append(" AND ");
                        }

                        String str = getNameCriteria(property, value, j);
                        buffer.append(str);
                        if (str.indexOf(":") > 0) {
                            if (i > 0) {
                                paramNames.append(",");
                            }

                            paramNames.append(getNoDotProperty(property + j));
                            values.add(value[1]);
                            ++i;
                        }
                    }
                }
            }
        }
    }

    static enum PropertyType {
        STRING,
        DATE,
        BOOLEAN,
        NUMBER;

        private PropertyType() {
        }
    }

}
