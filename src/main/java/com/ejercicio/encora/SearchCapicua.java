package com.ejercicio.encora;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class SearchCapicua {

	/*
	 * Esta funcion tiene como finalidad buscar similitudes de permutaciones
	 */

	class Input {
		private String word;
		private String text;

		public String getWord() {
			return word;
		}

		public void setWord(String word) {
			this.word = word;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

	}

	class Output {
		@SerializedName("Amount of permutations found")
		private int amount;
		@SerializedName("Permutations found")
		private List<String> permutations;

		public Output(int amount, List<String> permutations) {
			this.amount = amount;
			this.permutations = permutations;
		}

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

		public List<String> getPermutations() {
			return permutations;
		}

		public void setPermutations(List<String> permutations) {
			this.permutations = permutations;
		}

	}

	private Gson gson = new Gson();
	private Gson parseObjPretty = gson.newBuilder().setPrettyPrinting().create();

	public String find(InputStream input) {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
		Input jsonInput = gson.fromJson(bufferedReader, Input.class);
		return findIntern(jsonInput.word, jsonInput.text);
	}

	private String findIntern(String word, String text) {

		/*
		 * Aqui voy a implementar la logica de negocio
		 */

		List<String> permFound = Arrays.asList(text.split(" ")).stream().map(_word -> removeChars(_word))
				.filter(_word -> isPerm(word, _word)).toList();

		return parseObjPretty.toJson(new Output(permFound.size(), permFound));
	}

	private boolean isPerm(String word, String _word) {

		boolean result = word.length() == _word.length();

		for (int i = 0; result && i < word.length(); i++) {
			char c = word.charAt(i);
			if (_word.indexOf(c) == -1) {
				result = false;
			}
		}
		return result;
	}

	private String removeChars(String word) {
		return word.chars().filter(c -> (Character.isLetter(c) || Character.isDigit(c))).mapToObj(c -> ("" + (char) c))
				.collect(Collectors.joining());
	}

	public static void main(String[] args) {

		InputStream inputStream = SearchCapicua.class.getResourceAsStream("/com/ejercicio/encora/files/input.json");

		String result = new SearchCapicua().find(inputStream);
		System.out.println("Result: " + result);
	}

}
