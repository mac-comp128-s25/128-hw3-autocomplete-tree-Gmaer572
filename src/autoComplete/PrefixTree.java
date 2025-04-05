package autoComplete;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A prefix tree used for autocompletion. The root of the tree just stores links to child nodes (up to 26, one per letter).
 * Each child node represents a letter. A path from a root's child node down to a node where isWord is true represents the sequence
 * of characters in a word.
 */
public class PrefixTree {
    private TreeNode root; 

    // Number of words contained in the tree
    private int size;

    public PrefixTree(){
        root = new TreeNode();
    }

    /**
     * Adds the word to the tree where each letter in sequence is added as a node
     * If the word, is already in the tree, then this has no effect.
     * @param word
     */
    public void add(String word){
        int index = 0;
        TreeNode currentParent = root;
        if (!contains(word)){
            while (index < word.length()){
                Character currentChar = word.charAt(index);
                if (!currentParent.children.containsKey(currentChar)){
                    TreeNode currentNode = new TreeNode();
                    currentNode.letter = currentChar;
                    currentParent.children.put(currentChar, currentNode);
                    currentParent = currentNode;
                    if (index == word.length()-1){
                        currentNode.isWord = true;
                    }     
                    
                }
                else {
                    currentParent = currentParent.children.get(currentChar);
                }
                index++;
            }
            size++;
        }
    }

    /**
     * Checks whether the word has been added to the tree
     * @param word
     * @return true if contained in the tree.
     */
    public boolean contains(String word){
        char currentLetter;
        TreeNode currentParent = root;
        boolean cancel = false;
        int index = 0;
        Set<Character> charSet = currentParent.children.keySet();
        Iterator<Character> it;
        if (charSet.size() > 0){
            while (cancel == false && index <= word.length()-1 ){
                currentLetter = word.charAt(index);
                charSet = currentParent.children.keySet();
                if (charSet.size() == 0){
                    return false;
                }
                it = charSet.iterator();
                cancel = true;
                while (it.hasNext()){
                    Character currentParentChar = it.next();
                    if (currentParentChar.equals(currentLetter)){
                        currentParent = currentParent.children.get(currentParentChar); 
                        cancel = false;
                        break;
                    }
                }
                Character currentParentCharCasted = (Character) currentParent.letter;
                if (currentParent.isWord == true && index == word.length()-1 && currentParentCharCasted.equals(currentLetter)){
                    return true;
                }
                index++;
            }
        }
        return false;
    }

    /**
     * Finds the words in the tree that start with prefix (including prefix if it is a word itself).
     * The order of the list can be arbitrary.
     * @param prefix
     * @return list of words with prefix
     */
    public ArrayList<String> getWordsForPrefix(String prefix){
        ArrayList<String> wordList = new ArrayList<String>();
        char currentLetter;
        TreeNode currentParent = root;
        TreeNode prefixParent = new TreeNode();
        boolean cancel = false;
        int index = 0;
        Set<Character> charSet = currentParent.children.keySet();
        Iterator<Character> it;
        if (charSet.size() > 0){
            while (cancel == false && index <= prefix.length()-1 ){
                currentLetter = prefix.charAt(index);
                charSet = currentParent.children.keySet();
                it = charSet.iterator();
                cancel = true;
                while (it.hasNext()){
                    Character currentParentChar = it.next();
                    if (currentParentChar.equals(currentLetter)){
                        currentParent = currentParent.children.get(currentParentChar); 
                        cancel = false;
                        break;
                    }
                }
                if (index == prefix.length()-1){
                    prefixParent = currentParent;
                }
                index++;
            }
        }
        return prefixTraversal(prefixParent, prefix, wordList, true);
    }

    /**
     * Helper method for getWordsForPrefix to traverse all the subtrees of the prefix.
     * @return a recursive call, or a list of words with the prefix.
     */
    private ArrayList<String> prefixTraversal(TreeNode localRoot, String currentPrefix, ArrayList<String> wordList, boolean baseCase){
        if (localRoot == null){
            return wordList;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(currentPrefix); 
        char currentLetter = localRoot.letter;
        if (!baseCase){
            sb.append(localRoot.letter);
        }
        Iterator<Character> it = localRoot.children.keySet().iterator();
        while (it.hasNext()){
            char currentChar = it.next();
            TreeNode currentChild = localRoot.children.get(currentChar);
            String currentPrefixForCall = sb.toString();
            prefixTraversal(currentChild, currentPrefixForCall, wordList, false);

        }
        if (localRoot.isWord){
            wordList.add(sb.toString());
        }
        if (baseCase){
            return wordList;
        }
        return null;

    }
    /**
     * @return the number of words in the tree
     */
    public int size(){
        return size;
    }
    
}
