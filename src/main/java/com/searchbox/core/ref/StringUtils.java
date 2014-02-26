package com.searchbox.core.ref;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

	public static List<String> extractHitFields(String template){
		List<String> fields = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\\{hit.fieldValues\\['([^\\s^\\^\\'}]+)'\\]\\}");
		Matcher matcher = pattern.matcher(template);
		while(matcher.find()) {
			fields.add(matcher.group(1));
		}
		return fields;
	}
	
	public static void main(String... args){
		extractHitFields("<a href=\"${hit.getUrl()}\"><h5 class=\"result-title\">${hit.getTitle()}</h5></a>"+
				"<div>${hit.fieldValues['article-abstract']}</div>"+
				"<div>${hit.fieldValues['article-year']}</div>");
		System.out.println("done");
	}
}
