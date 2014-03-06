/*******************************************************************************
 * Copyright Searchbox - http://www.searchbox.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.searchbox.core.ref;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

	public static String toSlug(String input) {
		String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
		String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
		String slug = NONLATIN.matcher(normalized).replaceAll("");
		return slug.toLowerCase(Locale.ENGLISH);
	}

	public static List<String> extractHitFields(String template) {
		List<String> fields = new ArrayList<String>();
		Pattern pattern = Pattern
				.compile("\\{hit.fieldValues\\['([^\\s^\\^\\'}]+)'\\]\\}");
		Matcher matcher = pattern.matcher(template);
		while (matcher.find()) {
			fields.add(matcher.group(1));
		}
		return fields;
	}

	public static void main(String... args) {
		extractHitFields("<a href=\"${hit.getUrl()}\"><h5 class=\"result-title\">${hit.getTitle()}</h5></a>"
				+ "<div>${hit.fieldValues['article-abstract']}</div>"
				+ "<div>${hit.fieldValues['article-year']}</div>");
		System.out.println("done");
	}
	

	private static Map<Class<?>, String> classToSlug = new HashMap<Class<?>, String>();
	private static Map<String, Class<?>> slugToClass = new HashMap<String, Class<?>>();
	static {
		classToSlug.put(String.class, "s");
		classToSlug.put(Integer.class, "i");
		classToSlug.put(Double.class, "d");
		classToSlug.put(Date.class, "dt");
		classToSlug.put(Float.class, "f");
		classToSlug.put(Boolean.class, "b");
		for(Entry<Class<?>,String> entry:classToSlug.entrySet()){
			slugToClass.put(entry.getValue(), entry.getKey());
		}
	}

	public static String ClassToSlug(Class<?> clazz) {
		return classToSlug.get(clazz);
	}

	public static Class<?> SlugToClass(String slug) {
		return slugToClass.get(slug);
	}
}
