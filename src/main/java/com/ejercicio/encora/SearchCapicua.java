package com.ejercicio.encora;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

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

	public String find(InputStream input) {
		Gson gson = new Gson();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
		Input jsonInput = gson.fromJson(bufferedReader, Input.class);
		return findIntern(jsonInput.word, jsonInput.text);
	}

	private String findIntern(String word, String text) {

		int counter = 0;
		List<String> permFound = new ArrayList<>();

		/*
		 * Aqui voy a implementar la logica de negocio
		 */

		String[] words = text.split(" ");
		for (int i = 0; i < words.length; i++) {
			String _word = words[i];
			String _wordClean = removeChars(_word);
			if (isPerm(word, _wordClean)) {
				counter++;
				permFound.add(_wordClean);
			}
		}

		return "\n{" + "\nAmount of permutations found:" + counter + "," + "\nPermutations found:" + toString(permFound)
				+ "" + "\n}";
	}

	private String toString(List<String> permFound) {

		String arrayString = "";

		for (int i = 0; i < permFound.size(); i++) {
			String perm = permFound.get(i);
			arrayString += perm + (i < permFound.size() - 1 ? ", " : "");
		}

		return "[" + arrayString + "]";
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
		String newWord = "";

		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (Character.isLetter(c)) {
				newWord += c;
			}
		}

		return newWord;
	}

	public static void main(String[] args) {
		
		InputStream inputStream = SearchCapicua.class.
				getResourceAsStream("/com/ejercicio/encora/files/input.json");
		
		String result = new SearchCapicua().find(inputStream);
		System.out.println("Result: " + result);
	}

}
