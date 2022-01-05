/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.poshi.core.util;

import com.liferay.poshi.core.script.PoshiScriptParserException;
import com.liferay.poshi.core.script.UnbalancedCodeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Calum Ragan
 */
public class PoshiParserUtil {

	public static List<String> getMethodParameters(String content) {
		try {
			return getMethodParameters(content, null);
		}
		catch (PoshiScriptParserException poshiScriptParserException) {
			poshiScriptParserException.printStackTrace();

			return new ArrayList<>();
		}
	}

	public static List<String> getMethodParameters(
			String content, Pattern parameterPattern)
		throws PoshiScriptParserException {

		List<String> methodParameters = new ArrayList<>();

		if (content.length() == 0) {
			return methodParameters;
		}

		StringBuilder sb = new StringBuilder();

		String methodParameter = sb.toString();

		for (char c : content.toCharArray()) {
			if ((c == ',') && isBalancedPoshiScript(methodParameter)) {
				if (parameterPattern != null) {
					Matcher matcher = parameterPattern.matcher(methodParameter);

					if (!matcher.matches()) {
						sb.append(c);

						continue;
					}
				}

				methodParameters.add(methodParameter);

				sb.setLength(0);

				continue;
			}

			sb.append(c);

			methodParameter = sb.toString();
		}

		if (parameterPattern != null) {
			Matcher matcher = parameterPattern.matcher(methodParameter);

			if (!matcher.matches()) {
				throw new PoshiScriptParserException(
					"Invalid Poshi Script parameter syntax");
			}
		}

		methodParameters.add(methodParameter);

		return methodParameters;
	}

	public static boolean isBalancedPoshiScript(String poshiScript) {
		try {
			return isBalancedPoshiScript(poshiScript, false);
		}
		catch (Exception exception) {
			return false;
		}
	}

	public static boolean isBalancedPoshiScript(
			String poshiScript, boolean throwException)
		throws UnbalancedCodeException {

		poshiScript = _fixPoshiScript(poshiScript);

		Stack<Integer> stack = new Stack<>();

		for (int i = 0; i < poshiScript.length(); i++) {
			char c = poshiScript.charAt(i);

			if (!stack.isEmpty()) {
				int topIndex = stack.peek();

				Character topCodeBoundary = poshiScript.charAt(topIndex);

				if ((c == _codeBoundariesMap.get(topCodeBoundary)) && (i > 0) &&
					(poshiScript.charAt(i - 1) != '\\')) {

					stack.pop();

					continue;
				}

				if ((topCodeBoundary == '\"') || (topCodeBoundary == '\'')) {
					continue;
				}
			}

			if (_codeBoundariesMap.containsKey(c)) {
				stack.push(i);

				continue;
			}

			if (_codeBoundariesMap.containsValue(c)) {
				if (throwException) {
					throw new UnbalancedCodeException(
						"Unexpected closing boundary", i, poshiScript);
				}

				return false;
			}
		}

		boolean balanced = stack.isEmpty();

		if (!balanced && throwException) {
			throw new UnbalancedCodeException(
				"Unmatched opening boundary", stack.peek(), poshiScript);
		}

		return balanced;
	}

	private static String _fixPoshiScript(String poshiScript) {
		if (poshiScript.contains("/*") && poshiScript.contains("*/")) {
			poshiScript = poshiScript.replaceAll("(?s)/\\*.*?\\*/", "/\\*\\*/");
		}

		if (poshiScript.contains("'''")) {
			poshiScript = poshiScript.replaceAll(
				"(?s)\'\'\'.*?\'\'\'", "\'\'\'\'\'\'");
		}

		if (poshiScript.contains("//")) {
			poshiScript = poshiScript.replaceAll("(?m)\n[\\s]*//.*?$", "//\n");
		}

		return poshiScript.trim();
	}

	private static final Map<Character, Character> _codeBoundariesMap =
		new HashMap<Character, Character>() {
			{
				put('(', ')');
				put('[', ']');
				put('\"', '\"');
				put('\'', '\'');
				put('{', '}');
			}
		};

}