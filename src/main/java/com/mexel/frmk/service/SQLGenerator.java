package com.mexel.frmk.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;

import com.mexel.frmk.db.DbInvoker.NULLNumber;

/*
 * To use Dynamic sql use following syntax
 * String cond = "{(NameOfParamterInParams)?\"SQL Condition \":}";
 * for ex: I have condition when name is present then add name condition in following sql
 * Select * From emp where empId=${empId} And Name = ${name}
 * to use dynamic sql for this query syntax will be
 * 
 * Select * From emp where empId=${empId} 
 * {(name)?\" And Name = ${name} \":\" ElseCondition Or empty \ }
 * 
 * */

public class SQLGenerator {

	protected final static String REGEX = "(?:(?<=[^\\$]{1})\\{|^\\{)(.+)\\}";

	protected final static String SUB_REGEX = "\\(([\\w(!=)|=%]*((\\ *(\\|{2}|\\&{2})\\ *)[\\w(!=)|=%]*)*)\\)\\?(.*):(.*)";

	public String buildSql(String sql, Map<String, Object> params) {
		String str = getAndReplace(params, sql);
		LogFactory.getLog(getClass().getName()).debug(" **  "+params.get("SERVICE_NAME")+"****" + str + " [ "+params+"]");
		return str;
	}

	protected String getAndReplace(Map<String, Object> params, String originalQuery) {
		return removeBlankLines(parseReplaceFromSetup(params, originalQuery)
				.toString());
	}
	
	public static void main1(String[] args) {
		String expressionNameToUse = "\" And item_id= ${itemId}\"";
		
		System.out.println(expressionNameToUse.startsWith("\""));
	}

	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("itemId", 1);

		// {(pLbrEligible)?"AND inx_client_info.C_LBR_ELIGIBLE IN ${pLbrEligible}":}

		String cond = "{(pUserInCharge)?\"AND "
				+ "inx_client_info.C_WHO_IS_IN_CHARGE_OF_PROBLEM = ${pUserInCharge}\":} \n ";

//		String sql = "Select * From emp where empId= ( ${empId} {(allmpl)?\" OR u.managerid = ${userid} OR u.roleid=1 ) \":\" ) \"} ";
		String sql = "Select _id, template,item_id From rx_item_tmpl where 1=1 \n"+ 
	"{(itemId=3)?\" And item_id= ${itemId}\":} \n"+
	"{(_id)?\" And _id= ${_id}\":} \n {test}";

		SQLGenerator g = new SQLGenerator();
		System.out.println(" SQL = " + sql);
		System.out.println("Without Name parameter ****");
		params.put("test", " And company_id= ${companyId}");
		params.put("_id", new NULLNumber());
		params.put("itemId", 3);
		System.out.println(g.buildSql(sql, params));


	}

	protected Object findFromSetup(Map<String,Object> params, String expressionName) {
		Pattern p = Pattern.compile(SUB_REGEX);
		Matcher m = p.matcher(expressionName);
		String expressionNameToUse = expressionName;
		if (m.matches()) {
			String condition = m.group(1);
			String caseIf = m.group(m.groupCount() - 1);
			String caseElse = m.group(m.groupCount());
			if (evaluateCondition(condition, params)) {
				expressionNameToUse = caseIf;
			} else {
				expressionNameToUse = caseElse;
			}
		}

		expressionNameToUse = StringUtils.trim(expressionNameToUse);

		if ((expressionNameToUse == null)
				|| (expressionNameToUse.length() == 0)) {
			return "";
		}

		if (expressionNameToUse.startsWith("\"")
				&& expressionNameToUse.endsWith("\"")) {
			return expressionNameToUse.substring(1, expressionNameToUse
					.length() - 1);
		}
		if (params.containsKey(expressionNameToUse) && !(params.get(expressionNameToUse) instanceof NULLNumber)) {
			// the expression to use can itself contain other expressions
			return parseReplaceFromSetup(params, (String) params
					.get(expressionNameToUse));
		}
		return null;
	}

	protected boolean evaluateCondition(String condition, Map<String,Object> params) {
		if (condition.length() == 0) {
			return false;
		}

		String cleanCondition = StringUtils.deleteWhitespace(condition);

		int orCondition = cleanCondition.indexOf('|');
		if (orCondition >= 0) {
			// we have a or
			return evaluateCondition(cleanCondition.substring(0, orCondition),
					params)
					|| evaluateCondition(cleanCondition.substring(
							orCondition + 2, cleanCondition.length()), params);
		}

		// Beware that the && and || are processed on the same level (&& has no
		// priority over || as it should)
		int andCondition = cleanCondition.indexOf('&');
		if (andCondition >= 0) {
			// we have a and boolean
			return evaluateCondition(cleanCondition.substring(0, andCondition),
					params)
					&& evaluateCondition(cleanCondition.substring(
							andCondition + 2, cleanCondition.length()), params);
		}

		int equalSign = cleanCondition.indexOf('=');
		if (equalSign == -1) {
			return params.containsKey(cleanCondition) && !(params.get(cleanCondition) instanceof NULLNumber);
		}

		int notEqualSign = StringUtils.indexOf(cleanCondition, "!=");

		String conditionLeft = cleanCondition.substring(0,
				notEqualSign > 0 ? notEqualSign : equalSign);
		String conditionRight = cleanCondition.substring(equalSign + 1,
				cleanCondition.length());

		if (!params.containsKey(conditionLeft)) {
			return notEqualSign > -1;
		}

		String evalLeft = params.get(conditionLeft).toString();

		// we can put a Collection (multi-selector), in this case we have a [
		// and ]
		evalLeft = StringUtils.remove(evalLeft, '[');
		evalLeft = StringUtils.remove(evalLeft, ']');

		boolean isEqual = evalLeft.equals(conditionRight);

		return notEqualSign > -1 ? !isEqual : isEqual;
	}

	protected static String replaceDollarBrace(String replacement) {
		return replacement.replaceAll("\\$\\{", "\\\\\\$\\{");
	}

	protected StringBuffer parseReplaceFromSetup(Map<String,Object> params, String toParse) {
		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(toParse);

		StringBuffer result = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(result, replaceDollarBrace(findFromSetup(
					params, m.group(m.groupCount())).toString()));
		}
		m.appendTail(result);
		return result;
	}

	/**
	 * Remove the blank lines from the input String
	 * 
	 * @param original
	 * @return
	 */
	public static final String removeBlankLines(String original) {
		// we transform original into an array of lines
		String[] lines = StringUtils.split(original, '\n');
		StringBuffer buffer = new StringBuffer(original.length());

		// if the line is not blank, then we append it
		for (int i = 0; i < lines.length; i++) {
			if (StringUtils.isNotBlank(lines[i])) {
				buffer.append(lines[i]).append('\n');
			}
		}

		return buffer.toString();
	}

}
