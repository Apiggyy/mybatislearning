/*
 * Copyright (c) 2012-2013, Yunnan Yuan Xin technology Co., Ltd.
 * 
 * All rights reserved.
 */
package com.self.learning.mybatis;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * This plugin demonstrates overriding the initialized() method to rename the
 * generated example classes. Instead of xxxExample, the classes will be named
 * xxxCriteria
 * 
 * This plugin accepts two properties:
 * <ul>
 * <li><tt>searchString</tt> (required) the regular expression of the name
 * search.</li>
 * <li><tt>replaceString</tt> (required) the replacement String.</li>
 * </ul>
 * 
 * For example, to change the name of the generated Example classes from
 * xxxExample to xxxCriteria, specify the following:
 * 
 * <dl>
 * <dt>searchString</dt>
 * <dd>Example$</dd>
 * <dt>replaceString</dt>
 * <dd>Criteria</dd>
 * </dl>
 * 
 * 
 * @author Jeff Butler
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.1, Aug 20, 2012
 */
public final class RenameExampleClassPlugin extends PluginAdapter {

    private String searchString;

    private String replaceString;

    private Pattern pattern;

    /**
     * 
     */
    public RenameExampleClassPlugin() {
    }

    public boolean validate(List<String> warnings) {

        searchString = properties.getProperty("searchString"); //$NON-NLS-1$
        replaceString = properties.getProperty("replaceString"); //$NON-NLS-1$

        boolean valid = stringHasValue(searchString) && stringHasValue(replaceString);

        if (valid) {
            pattern = Pattern.compile(searchString);
        } else {
            if (!stringHasValue(searchString)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "RenameExampleClassPlugin", //$NON-NLS-1$
                        "searchString")); //$NON-NLS-1$
            }
            if (!stringHasValue(replaceString)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "RenameExampleClassPlugin", //$NON-NLS-1$
                        "replaceString")); //$NON-NLS-1$
            }
        }

        return valid;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        String type = introspectedTable.getExampleType();
        Matcher matcher = pattern.matcher(type);
        type = matcher.replaceAll(replaceString);

        introspectedTable.setExampleType(type);

        introspectedTable.setCountByExampleStatementId("countBy" + replaceString);
        introspectedTable.setDeleteByExampleStatementId("deleteBy" + replaceString);
        introspectedTable.setSelectByExampleStatementId("selectBy" + replaceString);
        introspectedTable.setSelectByExampleWithBLOBsStatementId("selectBy" + replaceString + "WithBLOBs");
        introspectedTable.setUpdateByExampleStatementId("updateBy" + replaceString);
        introspectedTable.setUpdateByExampleSelectiveStatementId("updateBy" + replaceString + "Selective");
        introspectedTable.setUpdateByExampleWithBLOBsStatementId("updateBy" + replaceString + "WithBLOBs");
        introspectedTable.setExampleWhereClauseId(replaceString + "_Where_Clause");
        introspectedTable.setMyBatis3UpdateByExampleWhereClauseId("Update_By_" + replaceString + "_Where_Clause");
    }
}
