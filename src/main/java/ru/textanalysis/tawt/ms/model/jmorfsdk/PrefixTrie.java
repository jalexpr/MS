package ru.textanalysis.tawt.ms.model.jmorfsdk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.textanalysis.tawt.ms.constant.Const.MAX_PREFIX_LENGTH;
import static ru.textanalysis.tawt.ms.constant.Const.MIN_WORD_ROOT_LENGTH;

public class PrefixTrie implements Trie {

	private final TrieNode root;

	public PrefixTrie() {
		root = new TrieNode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insert(String prefix) {
		TrieNode current = root;

		for (char l : prefix.toCharArray()) {
			current = current.getChildren().computeIfAbsent(l, c -> new TrieNode());
		}
		current.setEndOfWord(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(String prefix) {
		TrieNode current = root;
		for (int i = 0; i < prefix.length(); i++) {
			char ch = prefix.charAt(i);
			TrieNode node = current.getChildren().get(ch);
			if (node == null) {
				return false;
			}
			current = node;
		}
		return current.isEndOfWord();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String findLongest(String word) {
		String prefix = "";
		TrieNode current = root;
		int maxPrefixLength = word.length() >= MAX_PREFIX_LENGTH + MIN_WORD_ROOT_LENGTH ? MAX_PREFIX_LENGTH : word.length() - MIN_WORD_ROOT_LENGTH;

		for (int i = 0; i < word.length(); i++) {
			if (i > maxPrefixLength) {
				break;
			}
			char ch = word.charAt(i);
			TrieNode node = current.getChildren().get(ch);
			if (node == null) {
				break;
			}
			if (node.isEndOfWord()) {
				prefix = word.substring(0, i + 1);
			}
			current = node;
		}
		return prefix;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> findAll(String word) {
		List<String> prefixes = new ArrayList<>();
		TrieNode current = root;
		int maxPrefixLength = word.length() >= MAX_PREFIX_LENGTH + MIN_WORD_ROOT_LENGTH ? MAX_PREFIX_LENGTH : word.length() - MIN_WORD_ROOT_LENGTH;

		for (int i = 0; i < word.length(); i++) {
			if (i > maxPrefixLength) {
				break;
			}
			char ch = word.charAt(i);
			TrieNode node = current.getChildren().get(ch);
			if (node == null) {
				break;
			}
			if (node.isEndOfWord()) {
				prefixes.add(word.substring(0, i + 1));
			}
			current = node;
		}
		Collections.reverse(prefixes);
		return prefixes;
	}
}
