package com.redsea.common;

/**
 * sql查询条件
 * 
 * @author Administrator
 *
 */
public class Filter{
    
    public static final String OPERATOR_LIKE = "LIKE";
    
    public static final String OPERATOR_NOT_LIKE = "NOT_LIKE";

    public static final String OPERATOR_EQ = "=";

    public static final String OPERATOR_NOT_EQ = "<>";
    

    public static final String OPERATOR_GREATER_THAN = ">";

    public static final String OPERATOR_LESS_THEN = "<";

    public static final String OPERATOR_GREATER_EQ = ">=";

    public static final String OPERATOR_LESS_EQ = "<=";

    public static final String OPERATOR_NULL = "NULL";
    
    public static final String OPERATOR_NOT_NULL = "NOTNULL";
    
    public static final String RELATION_AND = "AND";

    public static final String RELATION_OR = "OR";

    public static final String RELATION_NOT = "NOT";
    
    String relation;
    
    public Filter() {
        this.relation = RELATION_AND;
    }

    public Filter(String relation) {
        this.relation = relation;
    }

}

